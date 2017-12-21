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
package freelunch.core.planning.benchmarking.providers;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import freelunch.core.planning.benchmarking.BenchmarkProvider;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.sase.sasToSat.SasIO;

public class SasFilesBenchmarkProvider implements BenchmarkProvider {

    private File[] files;
    private int nextItemIndex;
    
    public SasFilesBenchmarkProvider(final String path, final String[] domains) {
        File benchDir = new File(path);
        files = benchDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                boolean acceptable = false;
                for (String s : domains) {
                    if (pathname.getAbsolutePath().contains(s)) {
                        acceptable = true;
                        break;
                    }
                }
                return acceptable && pathname.getAbsolutePath().endsWith("sas");
            }
        });
        sort(files);
        nextItemIndex = 0;
    }
    
    public SasFilesBenchmarkProvider(final String path) {
        
        File benchDir = new File(path);
        files = benchDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getAbsolutePath().endsWith("sas");
            }
        });
        sort(files);
        nextItemIndex = 0;
    }
    
    @Override
    public SasProblem getNext() {
        if (nextItemIndex >= files.length) {
            return null;
        } else {
            try {
                File f = files[nextItemIndex];
                SasProblem problem = SasIO.parse(f.getAbsolutePath());
                problem.setDescription(f.getName());
                nextItemIndex++;
                return problem;
            } catch (IOException e) {
                System.err.println(e.getMessage());
                return null;
            }
        }
    }

    private void sort(File[] files) {
        Arrays.sort(files, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
    }

}
