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
package freelunch.core.planning.tests;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.sase.optimizer.PlanLoader;
import freelunch.core.planning.sase.optimizer.PlanVerifier;
import freelunch.core.planning.sase.sasToSat.SasIO;


public class PlanLoaderTest extends TestCase {
    
    public void testLoader() throws IOException {
    	
		SasProblem prob = SasIO.parse("testfiles/blocks/barman1.sas");
		SasParallelPlan spp = PlanLoader.loadPlanFromFile("testfiles/blocks/barman1.plan", prob);
        boolean ok = PlanVerifier.verifyPlan(prob, spp);
        assertTrue(ok);
    }
    
    public void testSasWriter() throws IOException {
    	SasProblem problem = SasIO.parse("testfiles/benchmarks/elevators-p01.sas");
    	String tmpFile = "tmp-test.sas";
    	SasIO.saveToFile(problem, tmpFile);
    	SasProblem prob2 = SasIO.parse(tmpFile);
    	
    	// delete the tmp test file
    	File toDel = new File(tmpFile);
    	toDel.delete();
    	
    	assertTrue(problem.equals(prob2));
    }

}
