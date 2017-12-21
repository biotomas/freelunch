/*******************************************************************************
 * Copyright (c) 2012 Tomas Balyo and Vojtech Bardiovsky
 * 
 * This file is part of freeLunch
 * 
 * freeLunch is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, 
 * or (at your option) any later version.
 * 
 * freeLunch is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty 
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with freeLunch.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package freelunch.core.planning.sase.optimizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class ResultsAnalyzer {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String filename = args[0];
		
		FileReader fr = new FileReader(filename);
		BufferedReader reader = new BufferedReader(fr);
		
		if (args.length > 1) {
			winImprovementStatistics(reader);
		} else {
			filterData(reader);
		}
				
		reader.close();
	}
	
	private static final int MAXWIN = 2000;
	
	private static void winImprovementStatistics(BufferedReader reader) throws IOException {
		
		int[] unchanged = new int[MAXWIN];
		int[] improved = new int[MAXWIN];
		float[] unchangedTimes = new float[MAXWIN];
		float[] improvedTimes = new float[MAXWIN];
		
		Arrays.fill(unchanged, 0);
		Arrays.fill(improved, 0);
		Arrays.fill(unchangedTimes, 0);
		Arrays.fill(improvedTimes, 0);
		int maxImprovment = 0;
		
		float time = 0;
		
		String line = reader.readLine();
		while (line != null) {
			if (line.startsWith("PROBLEM")) {
				time = 0;
			} else if (Character.isDigit(line.charAt(0))) {
				String[] parts = line.split(" ");
				int size = Integer.parseInt(parts[2]);
				float rtime = Float.parseFloat(parts[4].replace(',','.'));
				float dtime = rtime - time;
				time = rtime;
				String msg = parts[5];
				maxImprovment = Math.max(maxImprovment, size);
				
				if (msg.equals("IMPROVED")) {
					improved[size]++;
					improvedTimes[size] += dtime;
				}
				if (msg.equals("UNCHANGED")) {
					unchanged[size]++;
					unchangedTimes[size] += dtime;
				}
			}
			line = reader.readLine();
			continue;
		}
		
		for (int i = 0; i <= maxImprovment; i++) {
			System.out.println(String.format("%d;%d;%d;%.3f;%.3f", i, unchanged[i], improved[i], unchangedTimes[i], improvedTimes[i]));
		}
	}
	
	private static void filterData(BufferedReader reader) throws IOException {
		int maxImprov = 0;
		String line = reader.readLine();
		while (line != null) {
			if (line.contains("directory")) {
				line = reader.readLine();
				continue;
			}
			if (line.startsWith("PROBLEM")) {
				maxImprov = 0;
				line = reader.readLine();
				continue;
			}
			if (Character.isDigit(line.charAt(0))) {
				String[] parts = line.split(" ");
				if (parts[5].equals("IMPROVED")) {
					int size = Integer.parseInt(parts[2]);
					maxImprov = Math.max(size, maxImprov);
				}
				line = reader.readLine();
				continue;
			}
			// clean the line
			line = line.replace("/afs/ms/u/b/balyt5am/BIG/planprobs/", "");
			line = line.replace(".sas", "");
			line = line.replace(".pddl", "");
			line = line.replace(";null;0;0;0;0;0;0;0;0;;0", ";;;;;;;;;;;");
			line = line + ";" + (maxImprov > 0 ? maxImprov : "");
			System.out.println(line);
			line = reader.readLine();
		}
	}
	
}
