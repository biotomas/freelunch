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
package freelunch.core.pddlSupport.scripting;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import freelunch.core.pddlSupport.fLib.utils.io.FileHandling;

/**
 *
 * @author FD
 */
public class PrettyPrintPlans {

    public static void main(String[] str) {
        String param = "C:/ROOT/PROJECTS/pddlParser/_experiments/1/sasoc-results";

        File dir = new File(param);
        for (File f : dir.listFiles()) {
            proccessIfPossible(f, param + "-checked/" + f.getName());
        }
    }

    private static void proccessIfPossible(File f, String outPath) {
        String in = FileHandling.getFileContents(f);
        if (in.contains("Timeout in")) {
        } else {
            String plan = in.split("Plan:")[1];
            String[] ar = plan.split(":");
            List<String> acts = new LinkedList<>();
            for (int i = 1; i < ar.length; i++) {
                String st = ar[i].split("\n")[0].trim();
                acts.addAll(parseActions(st));
            }
            StringBuilder out = new StringBuilder();
            for (String st : acts) {
                out.append(st).append("\n");
            }
            FileHandling.writeFileOutput(outPath, out.toString());
        }
    }

    private static Collection<? extends String> parseActions(String st) {
        boolean actionsHaveTooManyParanthesis = nestedParantheses(st);
        if (!actionsHaveTooManyParanthesis) {
            List<String> acts = new LinkedList<>();
            int index = 0;
            int lastStart = -1;
            while (index < st.length()) {
                switch (st.charAt(index)) {
                    case '(':
                        lastStart = index;
                        break;
                    case ')':
                        acts.add(st.substring(lastStart, index + 1));
                        break;
                }
                index++;
            }
            return acts;
        } else {
            //split by actions
            String[] ar = st.split(" ");
            List<String> acts = new LinkedList<>();
            for (String s : ar) {
                String rewritten = "(";
                String pom = s.substring(1, s.length() - 1);
                rewritten += pom.split("\\(")[0] + "";
                String[] dn = null;
                try {
                    dn = pom.split("\\(")[1].split("\\)")[0].split(",");
                } catch (Exception e) {
                    dn = new String[]{};
                }
                for(String an:dn){
                    rewritten += " "+an;
                }
                rewritten += ")";
                acts.add(rewritten);
            }
            return acts;
        }
    }

    private static boolean nestedParantheses(String st) {
        int index = 1;
        while (true) {
            if (st.charAt(index) == '(') {
                return true;
            } else if (st.charAt(index) == ')') {
                return false;
            } else {
                index++;
            }
        }
    }
}
