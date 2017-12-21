/*
 * Author:  Filip Dvořák <filip.dvorak@runbox.com>
 *
 * Copyright (c) 2012 Filip Dvořák <filip.dvorak@runbox.com>, all rights reserved
 *
 * Publishing, providing further or using this program is prohibited
 * without previous written permission of the author. Publishing or providing
 * further the contents of this file is prohibited without previous written
 * permission of the author.
 */
package freelunch.core.pddlSupport.io.sas.out;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import freelunch.core.pddlSupport.fLib.utils.io.FileHandling;
import freelunch.core.pddlSupport.representations.SAS.SASTask;
import freelunch.core.pddlSupport.representations.SAS.SAction;
import freelunch.core.pddlSupport.representations.SAS.SAssignment;
import freelunch.core.pddlSupport.representations.SAS.STransition;
import freelunch.core.pddlSupport.representations.SAS.SValue;
import freelunch.core.pddlSupport.representations.SAS.SVariable;

/**
 *
 * @author FD
 */
public class HelmertSASv3Generator {

    private static Comparator<SAssignment> assignementComparator = new Comparator<SAssignment>() {
        @Override
        public int compare(SAssignment a, SAssignment b) {
            int o = a.var.GetMyIndex(), t = b.var.GetMyIndex();
            return o - t;
        }
    };

    public static void Output(SASTask t, String filePath) {
        StringBuilder s = new StringBuilder();

        //params
        s.append("begin_version\n3\nend_version\n");
        s.append("begin_metric\n1\nend_metric\n");

        //vars
        s.append(t.variables.size());
        s.append("\n");
        for (SVariable v : t.variables) {
            s.append("begin_variable");
            s.append("\n");
            s.append(v.mName);
            s.append("\n");
            s.append(-1);
            s.append("\n");
            s.append(v.domain.size());
            s.append("\n");
            for (SValue val : v.domain) {
                if(val.mName.contains("none of those")){
                    s.append("<none of those>");
                }else{
                    s.append("Atom ").append(val.mName);
                }
                s.append("\n");
            }
            s.append("end_variable");
            s.append("\n");
        }
        //mutex count
        s.append("0").append("\n");
        
        //init state
        s.append("begin_state");
        s.append("\n");
        Collections.sort(t.init, assignementComparator);
        int[] inic = new int[t.variables.size()];
        for(int i = 0; i < inic.length; i++){
            inic[i] = t.GetDummyValueForVariable(i);
        }
        for (SAssignment ass : t.init) {
            inic[ass.var.GetMyIndex()] = ass.var.GetIndexOfValue(ass.val);
        }
        for(int i:inic){
            s.append(i);s.append("\n");
        }
        s.append("end_state");
        s.append("\n");

        //goal state
        s.append("begin_goal");
        s.append("\n");
        s.append(t.goal.size());
        s.append("\n");
        for (SAssignment ass : t.goal) {
            s.append(ass.var.GetMyIndex());
            s.append(" ");
            s.append(ass.var.GetIndexOfValue(ass.val));
            s.append("\n");
        }
        s.append("end_goal");
        s.append("\n");

        //operators
        s.append(t.actions.size());
        s.append("\n");
        for (SAction ac : t.actions) {
            /*if(ac.toString().startsWith("fill-shot")){
                int xx = 0;
            }*/
            s.append("begin_operator").append("\n");
            s.append(ac.mName).append("\n");
            /**
             * transform the representation temporarily into prevailing
             * conditions and transitions
             */
            List<SAssignment> conds = new LinkedList<>();
            List<STransition> effs = new LinkedList<>();
            for (SAssignment ass : ac.effects) {
                STransition tr = new STransition();
                tr.var = ass.var;
                tr.to = ass.val;
                for (SAssignment ass2 : ac.preconditions) {
                    if (ass2.var.equals(ass.var)) {
                        tr.from = ass2.val;
                    }
                }
                effs.add(tr);
            }
            for (SAssignment ass : ac.preconditions) {
                boolean alreadyCovered = false;
                for (STransition tr : effs) {
                    if (tr.var.equals(ass.var)) {
                        alreadyCovered = true;
                    }
                }
                if(!alreadyCovered){
                    conds.add(ass);
                }
            }
            //number of prevailing conditions
            s.append(conds.size());
            s.append("\n");
            for (SAssignment ass : conds) {
                s.append(ass.var.GetMyIndex());
                s.append(" ");
                s.append(ass.var.GetIndexOfValue(ass.val));
                s.append("\n");
            }

            //number of transitions
            s.append(effs.size());
            s.append("\n");
            for (STransition tr : effs) {
                s.append("0 ");//dunno what this is
                s.append(tr.var.GetMyIndex());
                s.append(" ");
                if (tr.from == null) {
                    s.append(-1);
                } else {
                    s.append(tr.var.GetIndexOfValue(tr.from));
                }
                s.append(" ");
                s.append(tr.var.GetIndexOfValue(tr.to));
                s.append("\n");
            }
            //number of dunno what
            s.append(ac.mCost);
            s.append("\n");
            s.append("end_operator");
            s.append("\n");
        }
        
        //axiom count
        s.append("0").append("\n");
        
        FileHandling.writeFileOutput(filePath, s.toString());
    }
}
