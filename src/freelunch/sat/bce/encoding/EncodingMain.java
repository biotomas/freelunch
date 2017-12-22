package freelunch.sat.bce.encoding;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.BitSet;

import freelunch.sat.bce.decomposers.CombinedDecomposer;
import freelunch.sat.bce.decomposers.FormulaDecomposer;
import freelunch.sat.bce.decomposers.PureDecomposer;
import freelunch.sat.bce.decomposers.UnitDecomposer;
import freelunch.sat.bce.decomposers.postprocessors.BlockableClauseMover;
import freelunch.sat.bce.decomposers.postprocessors.BlockedClauseMover;
import freelunch.sat.bce.decomposers.postprocessors.CombinedClauseMover;
import freelunch.sat.bce.decomposers.postprocessors.DecompositionPostprocessor;
import freelunch.sat.bce.decomposers.postprocessors.EagerBlockableClauseMover;
import freelunch.sat.bce.decomposers.postprocessors.QuickDecompose;
import freelunch.sat.bce.decomposers.postprocessors.UnitPropagationReducer;
import freelunch.sat.bce.encoding.aig.AigEncoder;
import freelunch.sat.bce.utilities.BCESimplifier;
import freelunch.sat.bce.utilities.FormulaAnalyzer;
import freelunch.sat.bce.utilities.LockedProvider;
import freelunch.sat.bce.utilities.Logger;
import freelunch.sat.bce.utilities.ParametersProcessor;
import freelunch.sat.satLifter.sat.DimacsParser;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class EncodingMain {
	
	public static final String version = "0.82";
	
	private static void usage() {
		System.out.println("Blocked Clause Elimination Encoding version " + version);
		System.out.println("USAGE: java -jar enc.jar input.cnf [-option | -option=value]");
		System.out.println("Options:\n" +
				" -h		print this help\n" +
				" -v=INT		verbosity level, default is 1, 0 is silent\n" +
				" -e=<encoding>	encoding type, one of the following:\n" +
				"	o:	 original (default)\n" +
				"	c:	 original with closed cycles\n" +
				"	s:	 selective\n" +
				"	b:	 butterfly (ribbon with cycles)\n" +
				"	r:	 ribbon (butterfly without cycles)\n" +
				" -d=<decomp>	decomposition algorithm, one of the following:\n" +
				"	p:	 pure decomposition after unit propagation (default)\n" +
				"	u:	 unit decomposition\n" +
				"	c:	 unit decomposition, if not succesfull then pure\n" +
				" -p=<postproc>	postprocessing after decomposition, one of the following:\n" +
				"	b:	 move blocked clauses\n" +
				"	bb:	 move blocked and blockable clauses\n" +
				"	e:	 eagerly move clauses (slow)\n" +
				"	c:	 combine eager and bb\n" +
				"	q:	 quick decompose - produce maximal large set (very slow)\n" +
				" -l=<lock>	variable locking policy, one of the following:\n" +
				"	n:	 none (default)\n" +
				"	r:	 all variables in the smaller blocked set\n" +
				"	b:	 blocking literals in the smaller blocked set\n" +
				"	c:	 common blocking literals\n" +
				" -f		negate the locking policy (flip locked vs not-locked vars)\n" +
				" -o=Filename	output filename (default is stdout)\n" +
				" -x		only do decomposition (if -o=file is set, then the output will be in file.bsl and file.bss)\n" +
				" -s		only do decomposition and output for sblitter (append small set)\n" +
				" -z		only do decomposition and output a solitaire decomposition formula\n" +
				" -a		generate an AIG and output the formula in the aiger format");
	}
	
	private static boolean finished = false;
	
	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				if (finished) {
					Logger.print(1, "c Encoding finished successfully");
				} else {
					Logger.print(1, "c Encoding terminated");
				}
			}
		});
		
		
		ParametersProcessor pp = new ParametersProcessor(args);
		if (pp.isSet("h") || pp.getParameter(0) == null) {
			usage();
			return;
		}
		if (pp.getOptionValue("v") != null) {
			Logger.setVerbosity(Integer.parseInt(pp.getOptionValue("v")));
		} else {
			Logger.setVerbosity(1);
		}
		Logger.resetWatch();
		Logger.print(1, String.format("c enc.jar version %s params: %s", version, Arrays.toString(args)));
		BasicFormula f = DimacsParser.parseFromFile(pp.getParameter(0));
		
		if (f == null) {
			System.err.println("Input file not found / cannot be opened");
			return;
		}
		Logger.print(1, String.format("c Input parsed, contains %d variables and %d clauses", f.variablesCount, f.clauses.size()));

		FormulaDecomposer decomposer;
		String decType = pp.getOptionValue("d");
		if (decType == null) {
			decType = "p";
		}
		if (decType.equals("p")) {
			decomposer = new PureDecomposer();
		} else if (decType.equals("u")) {
			decomposer = new UnitDecomposer();
		} else if (decType.equals("c")) {
			decomposer = new CombinedDecomposer();
		} else {
			System.err.println("Invalid decomposition algorithm type: " + decType);
			return;
		}
		
		BasicFormula large = new BasicFormula();
		BasicFormula small = new BasicFormula();
		Logger.print(1, String.format("c Starting %s on the input formula", decomposer.getClass().getName()));
		decomposer.decomposeFormula(f, large, small);
		Logger.print(1, String.format("c %s result: large: %d (%d%%), small %d", decomposer.getClass().getName(), 
				large.clauses.size(), (100*large.clauses.size()/f.clauses.size()), small.clauses.size()));
		
		String postProcessType = pp.getOptionValue("p");
		if (postProcessType != null) {
			// b or bb or e
			if (postProcessType.equals("b")) {
				DecompositionPostprocessor postp = new BlockedClauseMover();
				postp.moveToLarge(large, small);
			} else if (postProcessType.equals("bb")) {
				DecompositionPostprocessor postp = new BlockableClauseMover();
				postp.moveToLarge(large, small);
			} else if (postProcessType.equals("e")) {
				DecompositionPostprocessor postp = new BlockableClauseMover();
				postp.moveToLarge(large, small);
				postp = new EagerBlockableClauseMover();
				postp.moveToLarge(large, small);
			} else if (postProcessType.equals("c")) {
				DecompositionPostprocessor postp = new BlockableClauseMover();
				postp.moveToLarge(large, small);
				postp = new CombinedClauseMover();
				postp.moveToLarge(large, small);
			} else if (postProcessType.equals("q")) {
				DecompositionPostprocessor postp = new BlockableClauseMover();
				postp.moveToLarge(large, small);
				postp = new QuickDecompose();
				postp.moveToLarge(large, small);
			}
			UnitPropagationReducer.simplifyByUnitPropagation(large, small, false);
			Logger.print(1, String.format("c after post-processing: large: %d (%d%%), small %d",
					large.clauses.size(), (100*large.clauses.size()/f.clauses.size()), small.clauses.size()));
		}

		// only decompose
		if (pp.isSet("x") || pp.isSet("a") || pp.isSet("s") || pp.isSet("z")) {
			Logger.print(1, FormulaAnalyzer.compareFormulas(large, small));
			if (pp.getOptionValue("o") != null) {
				String outfile = pp.getOptionValue("o");
				try {
					if (pp.isSet("s")) {
						Logger.print(0, String.format("c enc.jar BLOCKED %d OUT OF %d CLAUSES", large.clauses.size(), f.clauses.size()));
						large.clauses.addAll(small.clauses);
						FileWriter writer = new FileWriter(outfile);
						large.printDimacsToFile(writer);
						writer.close();
					} else if (pp.isSet("a")) {
						BasicFormula unitAndBS = UnitAndBlockedSetEncoder.encode(large, small);
						AigEncoder aige = new AigEncoder();
						//ImprovedAigEncoder aige = new ImprovedAigEncoder();
						aige.encode(unitAndBS).printToAigerFile(outfile);
						Logger.print(1, String.format("c aiger file saved to %s", outfile));
					} else if (pp.isSet("z")) {
						// solitaire
						BasicFormula unitAndBS = UnitAndBlockedSetEncoder.encode(large, small);
						Logger.print(2, "c starting blocked clause elimination on the output formula");
						BCESimplifier.simplify(unitAndBS);
						Logger.print(2, "c starting to write the output file");
						FileWriter writer = new FileWriter(outfile);
						unitAndBS.printDimacsToFile(writer);
						writer.close();
						Logger.print(1, String.format("c solitaire saved to %s", outfile));
					} else {
						FileWriter writer1 = new FileWriter(outfile+".bsl");
						FileWriter writer2 = new FileWriter(outfile+".bss");
						large.printDimacsToFile(writer1);
						small.printDimacsToFile(writer2);
						writer1.close();
						writer2.close();
						Logger.print(1, String.format("c Large blocked saved to %s, small blocked set saved to %s", outfile+".bsl", outfile+".bss"));
					}
				} catch (FileNotFoundException e) {
					System.err.println("Blocked set files cannot be written: " + e.getMessage());
				} catch (IOException e) {
					System.err.println("Blocked set files cannot be written: " + e.getMessage());
				}
			} else {
				System.err.println("output file not specified");
				return;
			}
			finished = true;
			return;
		}
		
		String lockPolicy = pp.getOptionValue("l");
		if (lockPolicy == null) {
			lockPolicy = "n";
		}
		BitSet locked;
		if (lockPolicy.equals("n")) {
			locked = LockedProvider.lockNone(large.variablesCount);
		} else if (lockPolicy.equals("r")) {
			locked = LockedProvider.lockPresentVars(small);
			Logger.print(1, String.format("c locked all vars in the small set: %d of %d (%d%%)", locked.cardinality(), large.variablesCount, (100*locked.cardinality())/large.variablesCount));
		} else if (lockPolicy.equals("c")) {
			locked = LockedProvider.lockCommonBlits(large, small);
			Logger.print(1, String.format("c locked common blocking literals: %d of %d (%d%%)", locked.cardinality(), large.variablesCount, (100*locked.cardinality())/large.variablesCount));
		} else if (lockPolicy.equals("b")) {
			locked = LockedProvider.lockBlits(small);
			Logger.print(1, String.format("c locked all blocking vars in the small set: %d of %d (%d%%)", locked.cardinality(), large.variablesCount, (100*locked.cardinality())/large.variablesCount));
		} else {
			System.err.println("Invalid locking policy: " + lockPolicy);
			return;
		}
		
		if (pp.isSet("f")) {
			LockedProvider.flipAll(locked);
			Logger.print(1, String.format("c flipped the locked variables, now locked: %d of %d (%d%%)", locked.cardinality(), large.variablesCount, (100*locked.cardinality())/large.variablesCount));
		}
		
		BasicFormula result;
		String encType = pp.getOptionValue("e");
		if (encType == null) {
			encType = "o";
		}
		if (encType.equals("s")) {
			result = SelectiveEncoder.encodeReconstruction(large, small);
			Logger.print(1, "c Selective reencoding finished");
		} else if (encType.equals("b")) {
			result = ButterflyEncoder.encodeReconstruction(large, small, locked);
			Logger.print(1, "c Butterfly reencoding finished");
		} else if (encType.equals("r")) {
			result = RibbonEncoder.encodeReconstruction(large, small);
			Logger.print(1, "c Ribbon reencoding finished");
		} else if (encType.equals("o")) {
			result = ReconstructionEncoder.encodeReconstruction(large, small, false, locked);
			Logger.print(1, "c Original reencoding finished");
		} else if (encType.equals("c")) {
			result = ReconstructionEncoder.encodeReconstruction(large, small, true, locked);
			Logger.print(1, "c Original reencoding with closed cycles finished");
		} else {
			System.err.println("Invalid encoding type: " + encType);
			return;
		}
		Logger.print(1, String.format("c Reencoded formula has %d variables and %d clauses", result.variablesCount, result.clauses.size()));
		if (pp.getOptionValue("o") != null) {
			String outfile = pp.getOptionValue("o");
			try {
				FileWriter out = new FileWriter(outfile);
				result.printDimacsToFile(out);
				out.close();
			} catch (FileNotFoundException e) {
				System.err.println("Output cannot be written: " + e.getMessage());
				return;
			} catch (IOException e) {
				System.err.println("Output cannot be written: " + e.getMessage());
				return;
			}
		} else {
			result.printDimacs(System.out);
		}
		Logger.print(1, "c Output file was written");
		finished = true;
	}
}
