/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package freelunch.core.pddlSupport.scripting;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;
import java.util.List;

import freelunch.core.pddlSupport.fLib.utils.io.FileHandling;

/**
 *
 * @author FD
 */
public class StatGen {

    private static class Task {

        String name;
        List<Integer> domSizes = new LinkedList<>();
        float getStateSpaceSize(){
            float ret = 1f;
            for(int i:domSizes){
                ret *= i;
            }
            return ret;
        }
        int actions = -1;
    }

    private static void getPathsRec(File uf, LinkedList<String> paths) {
        if (!uf.exists() || !uf.isDirectory()) {
            return;
        }

        File[] pddls = uf.listFiles(new FileFilter() {
            @Override
            public boolean accept(File fi) {
                return fi.getName().contains(".sas") && !fi.isDirectory();
            }
        });

        File[] dirs = uf.listFiles(new FileFilter() {
            @Override
            public boolean accept(File fi) {
                return fi.isDirectory();
            }
        });

        for (File f : pddls) {
            paths.add(f.getAbsolutePath());
        }

        for (File f : dirs) {
            getPathsRec(f, paths);
        }
    }

    public static void gen(String path, String out) {
        LinkedList<String> paths = new LinkedList<>();
        getPathsRec(new File(path), paths);
        List<Task> tasks = new LinkedList<>();
        for (String s : paths) {
            File f = new File(s);
            Task t = new Task();
            t.name = f.getAbsolutePath();
            String in = FileHandling.getFileContents(f);
            String[] ar = in.split("begin_variable");
            boolean first = true;
            for (String st : ar) {
                if (first) {
                    first = false;
                    continue;
                }
                String pt = st.split("\n")[3];
                t.domSizes.add(Integer.parseInt(pt.trim()));
            }
            t.actions = Integer.parseInt(in.split("end_goal")[1].trim().split("\n")[0].trim());
            tasks.add(t);
        }
        String buf = new String();
        for (Task t : tasks) {
            buf += t.name + "," + t.domSizes.size() + ","+ t.actions+ ","+ t.getStateSpaceSize() + "\n";
        }
        FileHandling.writeFileOutput(out, buf);
    }

    public static void main(String[] args) {
        gen("C:/ROOT/PROJECTS/pddlParser/data/", "domainStatistics.txt");
    }
}
