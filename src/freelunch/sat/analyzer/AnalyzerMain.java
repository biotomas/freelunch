package freelunch.sat.analyzer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import freelunch.sat.bce.utilities.Logger;
import freelunch.sat.satLifter.sat.DimacsParser;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class AnalyzerMain {

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

		
		Logger.setVerbosity(1);
		if (args.length < 3) {
			System.out.println("USAGE: formula.cnf output.cnf advice.adv");
			return;
		}
		BasicFormula f = DimacsParser.parseFromFile(args[0]);
		if (f == null) {
			Logger.print(0, "Formula too big, aborting");
			return;
		}

		HornAdvisor ha = new HornAdvisor();
		List<Integer> advice = ha.preprocessFormula(f, 100);
		
		try {
			// print the new formula
			FileWriter outf = new FileWriter(args[1]);
			f.printDimacsToFile(outf);
			outf.close();
			// print the advice
			FileWriter outa = new FileWriter(args[2]);
			outa.write(String.format("%d\n", advice.size()));
			for (int x : advice) {
				outa.write(String.format("%d\n", x));
			}
			outa.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finished = true;
	}

}
