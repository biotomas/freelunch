package freelunch.core.planning.sase.optimizer.potentialPlans;

import java.io.IOException;

import junit.framework.TestCase;
import freelunch.core.planning.NonexistentPlanException;
import freelunch.core.planning.Solver;
import freelunch.core.planning.TimeoutException;
import freelunch.core.planning.benchmarking.providers.LogisticsBenchmarkProvider;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.sase.optimizer.PlanLoader;
import freelunch.core.planning.sase.optimizer.PlanVerifier;
import freelunch.core.planning.sase.optimizer.blockDecomposition.BlockDecomposition;
import freelunch.core.planning.sase.sasToSat.SasIO;
import freelunch.core.planning.sase.sasToSat.iterative.IterativeSatBasedSolver;
import freelunch.core.planning.sase.sasToSat.translator.CompactDirect;
import freelunch.core.satSolving.Sat4JSolver;
import freelunch.core.utilities.Stopwatch;

public class PotentialPlanTests extends TestCase {
	
	public void testMaxsatOptimizer() throws IOException {
		/*
		SasProblem prob = sp.parse("testfiles/blocks/nomystery.sas");
		BlockDecomposition bd = new BlockDecomposition(prob, "testfiles/blocks/nomystery.bdp");
		SasParallelPlan spp = pl.loadPlanFromFile("testfiles/blocks/nomystery.plan", prob);
		/**/
		SasProblem prob = SasIO.parse("testfiles/blocks/barman1.sas");
		BlockDecomposition bd = new BlockDecomposition(prob, "testfiles/blocks/barman1.bdp");
		SasParallelPlan spp = PlanLoader.loadPlanFromFile("testfiles/blocks/barman1.plan", prob);
		/**/
		PotentialPlanMaker ppm = new BlockDecompositionBasedPotentialPlanMaker();
		PotentialPlan pp = ppm.makePotentialPlan(prob, null, bd);
		
		PotentialPlanToMaxsatOptimizer optimizer = new PotentialPlanToMaxsatOptimizer();
		SasParallelPlan nplan = optimizer.optimizePlan(prob, spp, pp);
		System.out.println(nplan);
		
		boolean ok = PlanVerifier.verifyPlan(prob, nplan);
		System.out.println("plan is valid: " + ok);
	}
	
	public void testBlockBasedPPMaker() throws IOException {
		SasProblem prob = SasIO.parse("testfiles/blocks/nomystery.sas");
		BlockDecomposition bd = new BlockDecomposition(prob, "testfiles/blocks/nomystery.bdp");

		//SasProblem prob = sp.parse("testfiles/blocks/barman1.sas");
		//BlockDecomposition bd = new BlockDecomposition(prob, "testfiles/blocks/barman1.bdp");
		
		PotentialPlanMaker ppm = new BlockDecompositionBasedPotentialPlanMaker();
		
		PotentialPlan pp = ppm.makePotentialPlan(prob, null, bd);
		System.out.println(pp);
		
		SasParallelPlan spp = PlanLoader.loadPlanFromFile("testfiles/blocks/nomystery.plan", prob);
		RecursivePotentialPlanMaker rppm = new RecursivePotentialPlanMaker();
		pp = rppm.makePotentialPlan(prob, spp);
		System.out.println(pp.toString());
		

	}
	
	public void testRecPPMaker() {
		LogisticsBenchmarkProvider lbp = new LogisticsBenchmarkProvider(2, 2, 10, 10, 10, 10);
		SasProblem prob = lbp.getNext();
        Solver s = new IterativeSatBasedSolver(new Sat4JSolver(), new CompactDirect(prob));

		try {
			SasParallelPlan plan = s.solve();
			
			boolean ok = PlanVerifier.verifyPlan(prob, plan);
			System.out.println(ok);
			
			System.out.println(plan.toString());
			
			RecursivePotentialPlanMaker ppm = new RecursivePotentialPlanMaker();
			Stopwatch watch = new Stopwatch();
			PotentialPlan pp = ppm.makePotentialPlan(prob, plan);
			System.out.println(pp.toString());
			System.out.println(watch.elapsedFormatedSeconds());
			
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (NonexistentPlanException e) {
			e.printStackTrace();
		}
		
	}

	public void testHeurPPMaker() {
		LogisticsBenchmarkProvider lbp = new LogisticsBenchmarkProvider(2, 2, 10, 10, 3, 10);
		SasProblem prob = lbp.getNext();
        Solver s = new IterativeSatBasedSolver(new Sat4JSolver(), new CompactDirect(prob));

		try {
			SasParallelPlan plan = s.solve();
			
			boolean ok = PlanVerifier.verifyPlan(prob, plan);
			System.out.println(ok);
			
			System.out.println(plan.toString());
			
			PotentialPlanMaker ppm = new HeuristicPotentialPlanMaker();
			Stopwatch watch = new Stopwatch();
			PotentialPlan pp = ppm.makePotentialPlan(prob, plan);
			System.out.println(pp.toString());
			System.out.println(watch.elapsedFormatedSeconds());
			
			ppm = new RecursivePotentialPlanMaker();
			watch.reset();
			pp = ppm.makePotentialPlan(prob, plan);
			System.out.println(pp.toString());
			System.out.println(watch.elapsedFormatedSeconds());
			
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (NonexistentPlanException e) {
			e.printStackTrace();
		}
		
	}
}
