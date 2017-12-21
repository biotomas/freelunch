/*******************************************************************************
 * Copyright (c) 2012 Tomas Balyo and Vojtech Bardiovsky
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

import freelunch.core.planning.sase.api.lifted.Condition;
import freelunch.core.planning.sase.api.lifted.LiftedAction;
import freelunch.core.planning.sase.api.lifted.LiftedStateVariable;
import freelunch.core.planning.sase.api.lifted.NamedParameter;
import freelunch.core.planning.sase.api.lifted.ObjectBinding;
import freelunch.core.planning.sase.api.lifted.ParameterBinding;
import freelunch.core.planning.sase.api.lifted.PlanningObject;
import freelunch.core.planning.sase.api.lifted.PlanningObjectClass;
import freelunch.core.planning.sase.api.lifted.PlanningObjectRelation;
import freelunch.core.planning.sase.api.lifted.PlanningObjectRelationClass;
import freelunch.core.planning.sase.api.lifted.PlanningProblemModellingException;
import junit.framework.TestCase;

public class LiftedModellingTest extends TestCase {
	
	public void testLogisticExample() {
		
		// Domain description
		try {
			
			PlanningObjectClass packageClass = new PlanningObjectClass("Package", null);
			PlanningObjectClass packageLocationClass = new PlanningObjectClass("Location", null);
			PlanningObjectClass vehicleClass = new PlanningObjectClass("Vehicle", packageLocationClass);
			PlanningObjectClass warehouseClass = new PlanningObjectClass("Warehouse", packageLocationClass);		
			
			LiftedStateVariable vehicleLocation = new LiftedStateVariable("Vehicle Location", vehicleClass, warehouseClass);
			LiftedStateVariable packageLocation = new LiftedStateVariable("Package Location", packageClass, packageLocationClass);
			
			PlanningObjectRelationClass adjacent = new PlanningObjectRelationClass(
					"adjacentRelation",
					new NamedParameter("loc1", warehouseClass),
					new NamedParameter("loc2", warehouseClass)
				);
			
			//LiftedAction moveVehicle = new LiftedAction(vehicleClass, warehouseClass, warehouseClass);
			LiftedAction moveVehicle = new LiftedAction(
					"move",
					new NamedParameter("vehicle", vehicleClass),
					new NamedParameter("from", warehouseClass),
					new NamedParameter("to", warehouseClass)
				);
			moveVehicle.addRequiredRelation(adjacent, new ParameterBinding("loc1s", "from"), new ParameterBinding("loc2", "to"));
			moveVehicle.addPrecondition(vehicleLocation, "vehicle", "from");
			moveVehicle.addEffect(vehicleLocation, "vehicle", "to");
			
			LiftedAction loadPackage = new LiftedAction(
					"load",
					new NamedParameter("vehicle", vehicleClass),
					new NamedParameter("where", warehouseClass),
					new NamedParameter("package", packageClass)
				);
			loadPackage.addPrevailCondition(vehicleLocation, "vehicle", "where");
			loadPackage.addPrecondition(packageLocation, "package", "where");
			loadPackage.addEffect(packageLocation, "package", "vehicle");
			
			LiftedAction unloadPackage = new LiftedAction(
					"unload",
					new NamedParameter("vehicle", vehicleClass),
					new NamedParameter("where", warehouseClass),
					new NamedParameter("package", packageClass)
				);
			unloadPackage.addPrevailCondition(vehicleLocation, "vehicle", "where");
			unloadPackage.addPrecondition(packageLocation, "package", "vehicle");
			unloadPackage.addEffect(packageLocation, "package", "where");
			

			// Problem description
			
			// Planning objects
			PlanningObject[] vehicles = new PlanningObject[3];
			vehicles[0] = new PlanningObject(vehicleClass, "vehicle1");
			vehicles[1] = new PlanningObject(vehicleClass, "vehicle2");
			vehicles[2] = new PlanningObject(vehicleClass, "vehicle3");
			
			PlanningObject[] warehouses = new PlanningObject[5];
			warehouses[0] = new PlanningObject(warehouseClass, "warehouse1");
			warehouses[1] = new PlanningObject(warehouseClass, "warehouse2");
			warehouses[2] = new PlanningObject(warehouseClass, "warehouse3");
			warehouses[3] = new PlanningObject(warehouseClass, "warehouse4");
			warehouses[4] = new PlanningObject(warehouseClass, "warehouse5");
			
			PlanningObject[] packages = new PlanningObject[5];
			packages[0] = new PlanningObject(packageClass, "package1");
			packages[1] = new PlanningObject(packageClass, "package2");
			packages[2] = new PlanningObject(packageClass, "package3");
			packages[3] = new PlanningObject(packageClass, "package4");
			packages[4] = new PlanningObject(packageClass, "package5");
			
			PlanningObjectRelation[] adj = new PlanningObjectRelation[6];
			adj[0] = new PlanningObjectRelation(adjacent, new ObjectBinding("loc1", warehouses[0]), new ObjectBinding("loc2", warehouses[1])); 
			adj[1] = new PlanningObjectRelation(adjacent, new ObjectBinding("loc1", warehouses[1]), new ObjectBinding("loc2", warehouses[2])); 
			adj[2] = new PlanningObjectRelation(adjacent, new ObjectBinding("loc1", warehouses[0]), new ObjectBinding("loc2", warehouses[3])); 
			adj[3] = new PlanningObjectRelation(adjacent, new ObjectBinding("loc1", warehouses[2]), new ObjectBinding("loc2", warehouses[4])); 
			adj[4] = new PlanningObjectRelation(adjacent, new ObjectBinding("loc1", warehouses[4]), new ObjectBinding("loc2", warehouses[1])); 
			adj[5] = new PlanningObjectRelation(adjacent, new ObjectBinding("loc1", warehouses[3]), new ObjectBinding("loc2", warehouses[1])); 
			
			Condition[] initialConds = new Condition[8];
			initialConds[0] = new Condition(packageLocation, packages[0], warehouses[4]);
			initialConds[1] = new Condition(packageLocation, packages[1], warehouses[3]);
			initialConds[2] = new Condition(packageLocation, packages[2], warehouses[2]);
			initialConds[3] = new Condition(packageLocation, packages[3], warehouses[1]);
			initialConds[4] = new Condition(packageLocation, packages[4], warehouses[0]);
			
			initialConds[5] = new Condition(vehicleLocation, vehicles[0], warehouses[0]);
			initialConds[6] = new Condition(vehicleLocation, vehicles[1], warehouses[2]);
			initialConds[7] = new Condition(vehicleLocation, vehicles[2], warehouses[4]);
			
			Condition[] goalConds = new Condition[5];
			goalConds[0] = new Condition(packageLocation, packages[0], warehouses[0]);
			goalConds[1] = new Condition(packageLocation, packages[1], warehouses[1]);
			goalConds[2] = new Condition(packageLocation, packages[2], warehouses[2]);
			goalConds[3] = new Condition(packageLocation, packages[3], warehouses[3]);
			goalConds[4] = new Condition(packageLocation, packages[4], warehouses[4]);
			
			//TODO spojenie do problemu
			
			
		} catch (PlanningProblemModellingException e) {
			e.printStackTrace();
		}
		
		
		
	}

}
