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
import java.util.LinkedList;
import java.util.List;

import freelunch.core.pddlSupport.fLib.utils.io.FileHandling;

/**
 *
 * @author FD
 */
public class CompareSASes {

    public static void main(String[] args) {
        compare("C:/ROOT/PROJECTS/pddlParser/data/miniTest/barman-pfile01-001.sas",
                "C:/ROOT/PROJECTS/pddlParser/data/miniTest/barman-pfile01-001.sas_greedy.sas");
    }

    private static void compare(String mePath, String helmertPath) {
        String me = FileHandling.getFileContents(new File(mePath));
        String helm = FileHandling.getFileContents(new File(mePath));

        
        List<String> h = new LinkedList<>(),m = new LinkedList<>();

        //parse me
        {
            String ar[] = me.split("begin_operator");
            for(int i = 1; i < ar.length; i++){
                String op = ar[i].trim().split("\n")[0].trim();
                m.add(op);
            }
        }
        
        //parse me
        {
            String ar[] = helm.split("begin_operator");
            for(int i = 1; i < ar.length; i++){
                String op = ar[i].trim().split("\n")[0].trim();
                h.add(op);
            }
        }
    }
}
