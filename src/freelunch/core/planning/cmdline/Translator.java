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
package freelunch.core.planning.cmdline;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.sase.preprocessing.DoubleActionCodec;
import freelunch.core.planning.sase.sasToMultiSat.MultiValuedCNF;
import freelunch.core.planning.sase.sasToMultiSat.SasToMVSat;
import freelunch.core.planning.sase.sasToSat.SasIO;
import freelunch.core.planning.sase.sasToSat.translator.ActionOrientedTranslator;
import freelunch.core.planning.sase.sasToSat.translator.BinaryReinforcedSaseTranslator;
import freelunch.core.planning.sase.sasToSat.translator.CompactDirect;
import freelunch.core.planning.sase.sasToSat.translator.CompactReinforcedSaseTranslator;
import freelunch.core.planning.sase.sasToSat.translator.DirectDoubleLinkedTranslator;
import freelunch.core.planning.sase.sasToSat.translator.DirectExistStepTranslator;
import freelunch.core.planning.sase.sasToSat.translator.DirectTranslator;
import freelunch.core.planning.sase.sasToSat.translator.DirectTranslatorSingleAction;
import freelunch.core.planning.sase.sasToSat.translator.DisertDirectTranslator;
import freelunch.core.planning.sase.sasToSat.translator.MiniBinTranslator;
import freelunch.core.planning.sase.sasToSat.translator.ReinforcedSaseTranslator;
import freelunch.core.planning.sase.sasToSat.translator.SasToSatTranslator;
import freelunch.core.planning.sase.sasToSat.translator.SaseTranslator;
import freelunch.core.planning.sase.sasToSat.translator.SaseTranslatorSettings;
import freelunch.core.planning.sase.sasToSat.translator.SelectiveTranslator;
import freelunch.core.satModelling.modelObjects.BasicSatFormula;
import freelunch.core.satSolving.FormulaAnalyzer;

public class Translator {
    
    public enum TranslationMethod {
        shortest, direct, sase, isase, action, linear, reinforced, exist, disertDirect, compactDirect, compactReinforced, binaryReinforced, selective, miniBin;
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("USAGE: java -jar translator.jar <problem.sas> <method> <makespan> [-m : multivalued sat output] [-a : add double actions]" +
            		"[-s : staticstics of formula");
            System.out.println("Methods: " + Arrays.toString(TranslationMethod.values()));
            return;
        }
        // input
        String problemName = args[0];
        TranslationMethod method = TranslationMethod.valueOf(args[1]);
        int makeSpan = Integer.parseInt(args[2]);
        boolean multiValued = contains(args, "-m");
        boolean doubleActions = contains(args, "-a");
        boolean statistics = contains(args, "-s");
        
        SasProblem problem = null;

        try {
            problem = SasIO.parse(problemName);
            
            if (doubleActions) {
                DoubleActionCodec dac = new DoubleActionCodec();
                List<SasAction> actionsPairs = dac.makeActionPairs(problem.getOperators());
                problem.getOperators().addAll(actionsPairs);
            }
            
            problem.setActionIDs();
            if (!multiValued) {
                SasToSatTranslator translator = makeTranslator(problem, method, 5);
                BasicSatFormula formula = translator.makeFormulaForMakespan(makeSpan);
                if (statistics) {
                    String csv = FormulaAnalyzer.analyzeFormula(formula).csv();
                    System.out.println(problemName + ";" + csv);
                } else {
                    formula.printFormula(System.out);
                }
            } else {
                SasToMVSat translator = new SasToMVSat();
                MultiValuedCNF cnf = translator.translate(problem, makeSpan);
                cnf.printNoGoodFormat(System.out);
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
	    case direct:
	        translator = new DirectTranslator(problem);
	        break;
	    case shortest:
	    	translator = new DirectTranslatorSingleAction(problem);
	    	break;
	    case sase:
	        SaseTranslatorSettings settings = new SaseTranslatorSettings();
	        settings.setUseOriginalGoalEncoding(true);
	        settings.setUseOriginalInitialStateEncoding(true);
	        translator = new SaseTranslator(problem, settings);
	        break;
	    case action:
	        translator = new ActionOrientedTranslator(problem);
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
	    case binaryReinforced:
	        translator = new BinaryReinforcedSaseTranslator(problem);
	        break;
	    case miniBin:
	        translator = new MiniBinTranslator(problem);
	        break;
	    case selective:
	        translator = new SelectiveTranslator(problem);
	        break;
	    }
	    return translator;
	}

}
