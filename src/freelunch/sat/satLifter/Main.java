package freelunch.sat.satLifter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import freelunch.sat.satLifter.multiSat.MultiValuedCNF;
import freelunch.sat.satLifter.sat.DimacsParser;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;
import freelunch.sat.satLifter.translation.SatToMultiValuedSat;
import freelunch.sat.satLifter.translation.covering.CliqueCoverGenerator;
import freelunch.sat.satLifter.translation.covering.GreedyLeftistCliqueCoverGenerator;
import freelunch.sat.satLifter.translation.covering.NaiveRandomizedCliqueCoverGenerator;
import freelunch.sat.satLifter.translation.mutex.BasicMutexFinder;
import freelunch.sat.satLifter.translation.mutex.ImprovedMutexFinder;
import freelunch.sat.satLifter.translation.mutex.MutexFinder;
import freelunch.sat.satLifter.translation.mutex.MutexPair;
import freelunch.sat.satLifter.translation.nonMutexTranslation.graph.InteractionGraph;

public class Main {
    
    private static enum Method {
        basic_naive,
        basic_greedy,
        improved_naive,
        improved_greedy
    }
    
    public static void mainPartitioner(String[] args) {
		if (args.length < 3) {
			System.out.println("Usage: partitioner.jar <cnf-file> <partitions-file> <partition>" );
			return;
		}
		String cnfFile = args[0];
		String partsFile = args[1];
		int part = Integer.parseInt(args[2]);
		       
        Set<Integer> inPartition = readListForPartition(partsFile, part);
        Set<Integer> atBorder = new HashSet<Integer>();
               
        BasicFormula formula = DimacsParser.parseFromFile(cnfFile);
        BasicFormula result = new BasicFormula(formula.variablesCount);
        for (int[] c : formula.clauses) {
        	boolean inside = false;
        	for (int lit : c) {
        		int var = Math.abs(lit);
        		if (inPartition.contains(var)) {
        			inside = true;
        			break;
        		}
        	}
        	if (!inside) {
        		continue;
        	}
        	result.addClause(c);
        	for (int lit : c) {
        		int var = Math.abs(lit);
        		if (!inPartition.contains(var)) {
        			atBorder.add(var);
        		}
        	}
        }
        System.out.print("cvip");
        for (int x : atBorder) {
        	System.out.print(" " + x);
        }
        System.out.println();
        result.printDimacs(System.out);
    }
    
    private static Set<Integer> readListForPartition(String filename, int partition) {
        try {
            Set<Integer> list = new HashSet<Integer>();
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String line;
			int index = 1;
			while ((line = in.readLine()) != null) {
				if (partition == Integer.parseInt(line)) {
					list.add(index);
				}
				index++;
			}
			in.close();
			return list;
		} catch (Exception e) {
			System.out.println("Cannot read partitions file: " + e.getMessage());
		}
        return null;
    }
    
    
    public static void mainGraph(String[] args) {
		if (args.length < 1) {
			System.out.println("Usage: igraph.jar <cnf-file>" );
			return;
		}
        String filename = args[0];
        BasicFormula formula = DimacsParser.parseFromFile(filename);
        InteractionGraph g = new InteractionGraph(formula.variablesCount);
        for (int[] c : formula.clauses) {
        	g.addClause(c);
        }
        g.printGraphUnweighted(System.out);
    }

    public static void main(String[] args) {
		if (args.length < 6) {
			System.out.println("Usage: gen.jar <seed> <vars> <cls> <pl1> <pl2> <pl3>" );
			return;
		}
		long seed = Long.parseLong(args[0]);
		int vars = Integer.parseInt(args[1]);
		int cls = Integer.parseInt(args[2]);
		float pl1 = Float.parseFloat(args[3]);
		float pl2 = Float.parseFloat(args[4]);
		float pl3 = Float.parseFloat(args[5]);
		
		RandomFormulaGenerator rfg = new RandomFormulaGenerator(seed);
		System.out.println(String.format("c formula generated with seed %d, probabilty values %f %f %f and clause ration %f", 
				seed, pl1, pl2, pl3, cls/(float)vars));
		rfg.getRandomSatisfiableFormula(vars, cls, pl1, pl2, pl3).printDimacs(System.out);
	}

    public static void mainLifter(String[] args) {
        Stopwatch totalTime = new Stopwatch();
        
        if (args.length < 1) {
            System.out.println("Usage: lifter.jar <filename.cnf> [result] [-m <method>]");
            System.out.println("Methods: basic-naive     = basic mutex finder + naive cover");
            System.out.println("Methods: basic-greedy    = basic mutex finder + greedy cover");
            System.out.println("Methods: improved-naive  = improv. mutex finder + naive cover");
            System.out.println("Methods: improved-greedy = improv. mutex finder + greedy cover");
            return;
        }
        
        String filename = args[0];
        BasicFormula formula = DimacsParser.parseFromFile(filename);
        
        // the default method
        Method method = Method.basic_naive;
        
        if (args.length > 1 && args[1].equals("-m")) {
            method = Method.valueOf(args[2]);
        }
        
        // finding mutexes
        Stopwatch mutexTime = new Stopwatch();
        List<MutexPair> mutexes = null;
        
        switch (method) {
        case basic_naive:
        case basic_greedy:
            MutexFinder mutexFinder = new BasicMutexFinder();
            mutexes = mutexFinder.findMutexPairs(formula);
            break;
        case improved_greedy:
        case improved_naive:
            mutexFinder = new ImprovedMutexFinder();
            mutexes = mutexFinder.findMutexPairs(formula);
            break;
        }
        mutexTime.pause();
        
        // finding a clique cover
        Stopwatch coverTime = new Stopwatch();
        List<List<Integer>> cover = null;
        
        switch(method) {
        case basic_naive:
        case improved_naive:
            CliqueCoverGenerator generator = new NaiveRandomizedCliqueCoverGenerator(2013, formula.variablesCount, mutexes);
            cover = generator.generateCliqueCover();
            break;
        case basic_greedy:
        case improved_greedy:
            generator = new GreedyLeftistCliqueCoverGenerator(2013, formula.variablesCount, mutexes);
            cover = generator.generateCliqueCover();
            break;
        }
        coverTime.pause();
        
        // translating the formula
        Stopwatch translationTime = new Stopwatch();
        SatToMultiValuedSat translator = new SatToMultiValuedSat();
        MultiValuedCNF mvCnf = translator.translate(formula, cover);
        translationTime.pause();
        
        // print the formula
        mvCnf.printNoGoodFormat(System.out);
        
        // print the statistics
        System.err.println("head;input;method;vars;clauses;newVars;newClauses;totalTime;mutexTime;coverTime;translateTime");
        System.err.println(String.format("data;%s;%s;%d;%d;%d;%d;%s;%s;%s;%s", filename, method.toString(), 
                formula.variablesCount, formula.clauses.size(), mvCnf.variablesCount, mvCnf.formula.size(),
                totalTime.elapsedFormatedSeconds(), mutexTime.elapsedFormatedSeconds(), coverTime.elapsedFormatedSeconds(), translationTime.elapsedFormatedSeconds()));
    }

}
