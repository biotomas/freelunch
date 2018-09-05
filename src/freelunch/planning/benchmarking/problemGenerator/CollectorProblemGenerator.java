package freelunch.planning.benchmarking.problemGenerator;

import java.util.Arrays;

import freelunch.planning.model.Condition;
import freelunch.planning.model.ConditionalEffect;
import freelunch.planning.model.SasAction;
import freelunch.planning.model.SasProblem;
import freelunch.planning.model.StateVariable;
import freelunch.planning.model.StringActionInfo;
import freelunch.planning.planners.satplan.SasProblemBuilder;
import freelunch.utilities.ArrayUtils;

/**
 * In this problem a robot moves on a grid which has various
 * items to collect. When a robot visits a location it picks up
 * all the items on that location and the 4 neighboring locations.
 * 
 * The domain is designed to test planning with conditional effects.
 * 
 * @author balyo
 *
 */
public class CollectorProblemGenerator {
	
	public static SasProblem generateProblem(int gridSize, int items, long seed) {
		SasProblemBuilder spb = new SasProblemBuilder();
		
		StateVariable robotLocation = spb.newVariable("location", gridSize*gridSize);
		// Robot always starts at location 0,0 => 0
		spb.addInitialStateCondition(new Condition(robotLocation, 0));
		
		// Item are not collected in the beginning and must be collected in the end
		StateVariable[] itemCollected = new StateVariable[items];	
		for (int i = 0; i < items; i++) {
			itemCollected[i] = spb.newVariable("item"+i, 2);
			spb.addInitialStateCondition(new Condition(itemCollected[i], 0));
			spb.addGoalCondition(new Condition(itemCollected[i], 1));
		}
		
		// generate random item position
		int[] itemPosition = ArrayUtils.randUniqueArrayRange(items, 1, gridSize*gridSize, seed);
		
		// DEBUG print problem
		System.out.println(Arrays.toString(itemPosition));
		for (int x = 0; x < gridSize; x++) {
			nextCell:
			for (int y = 0; y < gridSize; y++) {
				for (int ipos : itemPosition) {
					if (ipos == x*gridSize+y) {
						System.out.print("* ");
						continue nextCell;
					}
				}
				System.out.print(". ");
			}
			System.out.println();
		}
		
		// the only action is move and collect
		for (int x = 0; x < gridSize; x++) {
			for (int y = 0; y < gridSize; y++) {
				for (int dx : new int[] {-1,0,1}) {
					for (int dy : new int[] {-1,0,1}) {
						if (Math.abs(dx) + Math.abs(dy) != 1) {
							continue;
						}
						int gx = x+dx;
						int gy = y+dy;

						// move from (x,y) to (gx,gy) if possible
						if (gx >= 0 && gx < gridSize && gy >= 0 && gy < gridSize) {
							SasAction a = spb.newAction(new StringActionInfo(String.format("move %d,%d->%d,%d", x,y,gx,gy)));
							a.getPreconditions().add(new Condition(robotLocation, x*gridSize+y));
							a.getEffects().add(new Condition(robotLocation, gx*gridSize+gy));
							
							// conditional effects for collecting nearby items
							for (int idx : new int[] {-1,0,1}) {
								for (int idy : new int[] {-1,0,1}) {
									int ix = gx+idx;
									int iy = gy+idy;
									if (ix >= 0 && ix < gridSize && iy >= 0 && iy < gridSize) {
										for (int i = 0; i < items; i++) {
											if (itemPosition[i] == ix*gridSize+iy) {
												ConditionalEffect ce = new ConditionalEffect(itemCollected[i], 1,a);
												ce.addEffectCondition(new Condition(itemCollected[i], 0));
												a.getConditionalEffects().add(ce);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		return spb.getSasProblem();
	}
	

}
