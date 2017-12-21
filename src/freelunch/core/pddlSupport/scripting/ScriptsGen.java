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
import java.io.FileFilter;
import java.util.HashMap;
import java.util.LinkedList;

import freelunch.core.pddlSupport.fLib.utils.io.FileHandling;
import freelunch.core.pddlSupport.test.Test;

/**
 *
 * @author FD
 */
public class ScriptsGen {

    private static void getPathsRec(File uf, LinkedList<String> paths) {
        if (!uf.exists() || !uf.isDirectory()) {
            return;
        }

        File[] pddls = uf.listFiles(new FileFilter() {
            @Override
            public boolean accept(File fi) {
                return fi.getName().contains(".pddl") && !fi.isDirectory();
            }
        });

        File[] dirs = uf.listFiles(new FileFilter() {
            @Override
            public boolean accept(File fi) {
                return fi.isDirectory() && !fi.getAbsolutePath().contains("tempo-sat") && !fi.getAbsolutePath().contains("satellite") && !fi.getAbsolutePath().contains("market");//&& !fi.getName().contains("openstacks"); //openstacks do not type variables in action descriptions ...
            }
        });

        for (File f : pddls) {
            if (!f.getAbsolutePath().contains("domain")) {
                paths.add(f.getAbsolutePath());
            }
        }

        for (File f : dirs) {
            getPathsRec(f, paths);
        }
    }

    public static void helmert() {
        StringBuilder s = new StringBuilder();
        s.append("set path=%path%;C:\\BIN\\python33\n");
        LinkedList<String> paths = new LinkedList<>();
        getPathsRec(new File("C:/ROOT/PROJECTS/pddlParser/data/ipcSVN_2011/seq-opt"), paths);
        for(String path:paths){
            s.append("python C:/ROOT/TMP/fastDownward/src/translate/translate.py \"").append(path).append("\"\n");
            String[] parts = path.split("\\\\");
            String name = parts[parts.length-3] + "-" + parts[parts.length-1].split("\\.")[0];
            s.append("move output.sas \"C:\\ROOT\\PROJECTS\\pddlParser\\data\\helmertSAS\\").append(name).append(".sas\"\n");
        }
        s.append("pause\n");
        FileHandling.writeFileOutput("helmertGen.bat", s.toString());
    }
    
    public static void generateme(){
        LinkedList<String> paths = new LinkedList<>();
        getPathsRec(new File("C:/ROOT/PROJECTS/pddlParser/data/ipcSVN_2011/seq-opt"), paths);
        //just first three of each rule
        HashMap<String,Integer> mp = new HashMap<>();
        for(String path:paths){
            String[] parts = path.split("\\\\");
            String name = parts[parts.length-3] + "-" + parts[parts.length-1].split("\\.")[0];
            
            Integer in = mp.get(parts[parts.length-3]);
            if(in != null && in >= 10){
                continue;
            }else{
                if(in == null){
                    in = 0;
                }
                in++;
                mp.put(parts[parts.length-3], in);
            }
            
            String domName = path.replace(parts[parts.length-1], "domain.pddl");
            File domTest = new File(domName);
            if(!domTest.exists()){
                domName = path.replace(".pddl", "-domain.pddl");              
            }
            
            freelunch.core.pddlSupport.test.Test.PDDLToSAS(domName, path, "C:/ROOT/PROJECTS/pddlParser/data/mySAS/"+name+".sas");
        }
    }
    
    public static void main(String[] args) {
        if (args.length <3) {
            System.out.println("USAGE: transpddl.jar domain.pddl problem.pddl output.sas");
            return;
        }
        Test.PDDLToSAS(args[0], args[1], args[2]);
    }

    /*
    public static void main(String[] args) {
        //helmert();
        generateme();
    }
    */
}
