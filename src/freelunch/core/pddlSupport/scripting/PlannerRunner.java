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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import freelunch.core.planning.cmdline.SasSolver;

/**
 *
 * @author FD
 */
public class PlannerRunner {

    private static class Runner implements Callable<String> {

        String filePath;

        Runner(String path) {
            filePath = path;
        }

        @Override
        public String call() throws Exception {
            try {
                SasSolver.main(new String[]{filePath, "-c"});
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "done";
        }
    }

    public static void run(String path, int timeLimit) throws InterruptedException, ExecutionException{
        File dir = new File(path);
        File[] problems = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getAbsolutePath().contains(".sas");
            }
        });
        ExecutorService executor = Executors.newFixedThreadPool(1);
        for (File f : problems) {
            Runner r = new Runner(f.getAbsolutePath());
            Future<String> future = executor.submit(r);
            try {
                System.out.println("["+f.getName()+"] Starting...");
                future.get(timeLimit, TimeUnit.SECONDS);
                //System.out.println("Done.");
            } catch (TimeoutException e) {
                System.out.println("Timeout.");
            }
        }
    }
    
    public static void main(String[] args) throws InterruptedException, ExecutionException, java.util.concurrent.TimeoutException {
        //run("C:/ROOT/PROJECTS/pddlParser/data/helmertSAS");
        //run("C:/ROOT/PROJECTS/pddlParser/data/mySAS");
        run("mySAS", 5*60);
        run("helmertSAS", 5*60);
    }
}
