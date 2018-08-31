/*******************************************************************************
 * Copyright (c) 2013 Tomas Balyo and Vojtech Bardiovsky
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
package freelunch.planning.cmdline;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import freelunch.planning.model.SasAction;
import freelunch.planning.model.SasIO;
import freelunch.planning.model.SasParallelPlan;
import freelunch.planning.model.SasProblem;
import freelunch.planning.optimizer.PlanVerifier;
import freelunch.planning.planners.satplan.translator.CompactDirect;
import freelunch.planning.planners.satplan.translator.CompactReinforcedSaseTranslator;
import freelunch.planning.planners.satplan.translator.DirectDoubleLinkedTranslator;
import freelunch.planning.planners.satplan.translator.DirectExistStepTranslator;
import freelunch.planning.planners.satplan.translator.DisertDirectTranslator;
import freelunch.planning.planners.satplan.translator.ReinforcedSaseTranslator;
import freelunch.planning.planners.satplan.translator.SasToSatTranslator;
import freelunch.planning.planners.satplan.translator.SaseTranslator;
import freelunch.planning.planners.satplan.translator.SaseTranslatorSettings;
import freelunch.planning.planners.satplan.translator.SelectiveTranslator;
import freelunch.planning.preprocessing.DoubleActionCodec;
import freelunch.sat.model.CnfSatFormula;
import freelunch.sat.model.FormulaAnalyzer;
import freelunch.sat.modelling.modelObjects.PseudoBooleanFormula;

public class Translator {
    
    public enum TranslationMethod {
        sase, isase, linear, reinforced, exist, disertDirect, compactDirect, compactReinforced, selective;
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("USAGE:\njava -jar translator.jar encode <problem.sas> <method> <makespan> OPTIONS\n-OR-\n"
            		+ "java -jar translator.jar decode <problem.sas> <method> <makespan> <sat-model.txt> OPTIONS\n"
            		+ "OPTIONS:\n -a : add double actions\n -p : pseudo Boolean output\n" +
            		" -s : staticstics of formula");
            System.out.println("Methods: " + Arrays.toString(TranslationMethod.values()));
            return;
        }
        // input
        boolean encode = true;
        if (args[0].equals("decode")) {
        	encode = false;
        }
        
        String problemName = args[1];
        TranslationMethod method = TranslationMethod.valueOf(args[2]);
        int makeSpan = Integer.parseInt(args[3]);
        String satModelFile = null;
        if (!encode) {
        	satModelFile = args[4];
        }
        boolean doubleActions = contains(args, "-a");
        boolean statistics = contains(args, "-s");
        boolean pseudoBoolean = contains(args, "-p");
        
        SasProblem problem = null;

        try {
            problem = SasIO.parse(problemName);
            
            if (doubleActions) {
                DoubleActionCodec dac = new DoubleActionCodec();
                List<SasAction> actionsPairs = dac.makeActionPairs(problem.getOperators());
                problem.getOperators().addAll(actionsPairs);
            }
            
            if (!encode) {
            	if (pseudoBoolean) {
                    SasToSatTranslator translator = makeTranslator(problem, method, 5);
                    PseudoBooleanFormula pbf = translator.makePseudoBooleanFormulaForMakespan(makeSpan);
            		int vars = pbf.getVariables(); 
            		int[] model = PseudoBooleanFormula.parseSolutionFromFile(satModelFile, vars);
            		SasParallelPlan plan = translator.decodePlan(model, makeSpan);
            		System.out.println(plan.getPlanIpcFormat());
            		if (PlanVerifier.verifyPlan(problem, plan)) {
            			System.out.println("Plan verified!");
            		}
            	} else {
                    SasToSatTranslator translator = makeTranslator(problem, method, 5);
                    CnfSatFormula formula = translator.makeFormulaForMakespan(makeSpan);
                    int vars = formula.variablesCount;
                    int[] model = CnfSatFormula.parseSolutionFromFile(satModelFile, vars);
            		SasParallelPlan plan = translator.decodePlan(model, makeSpan);
            		System.out.println(plan.getPlanIpcFormat());
            		if (PlanVerifier.verifyPlan(problem, plan)) {
            			System.out.println("Plan verified!");
            		}
            	}
            } else
                        
            if (pseudoBoolean) {
                SasToSatTranslator translator = makeTranslator(problem, method, 5);
                PseudoBooleanFormula pbf = translator.makePseudoBooleanFormulaForMakespan(makeSpan);
                pbf.printFormula(System.out);
            } else  {
                SasToSatTranslator translator = makeTranslator(problem, method, 5);
                CnfSatFormula formula = translator.makeFormulaForMakespan(makeSpan);
                if (statistics) {
                    String csv = FormulaAnalyzer.analyzeFormula(formula).csv();
                    System.out.println(problemName + ";" + csv);
                } else {
                    formula.printDimacs(System.out);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static boolean contains(String[] list, String item) {
        for (String s : list) {
            if (s.equals(item)) {
                return true;
            }
        }
        return false;
    }

	public static SasToSatTranslator makeTranslator(SasProblem problem, TranslationMethod translation, int ranking) {
	    SasToSatTranslator translator = null;
	    switch (translation) {
	    case isase:
	        translator = new SaseTranslator(problem);
	        break;
	    case sase:
	        SaseTranslatorSettings settings = new SaseTranslatorSettings();
	        settings.setUseOriginalGoalEncoding(true);
	        settings.setUseOriginalInitialStateEncoding(true);
	        translator = new SaseTranslator(problem, settings);
	        break;
	    case linear:
	        translator = new DirectDoubleLinkedTranslator(problem);
	        break;
	    case exist:
	        translator = new DirectExistStepTranslator(problem, ranking);
	        break;
	    case reinforced:
	        translator = new ReinforcedSaseTranslator(problem);
	        break;
	    case disertDirect:
	        translator = new DisertDirectTranslator(problem);
	        break;
	    case compactDirect:
	        translator = new CompactDirect(problem);
	        break;
	    case compactReinforced:
	        translator = new CompactReinforcedSaseTranslator(problem);
	        break;
	    case selective:
	        translator = new SelectiveTranslator(problem);
	        break;
	    }
	    return translator;
	}

}
