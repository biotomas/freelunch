package freelunch.sat.bce.tests;

import freelunch.sat.bce.utilities.Logger;
import freelunch.sat.bce.utilities.ParametersProcessor;
import junit.framework.TestCase;

public class ParamatersProcessorTest extends TestCase {
	
	public void testParamatersProcessor() {
		
		String[] params = new String[] {"-b", "file1.cnf", "file2.cnf", "-prop=NO", "-pure", "-miter=100"};
		
		ParametersProcessor pp = new ParametersProcessor(params);
		
		assertEquals("file1.cnf", pp.getParameter(0));
		assertEquals("file2.cnf", pp.getParameter(1));
		assertNull(pp.getParameter(2));
		assertNull(pp.getParameter(3));
		
		assertTrue(pp.isSet("b"));
		assertTrue(pp.isSet("pure"));
		assertFalse(pp.isSet("prop"));
		assertFalse(pp.isSet("miter"));
		assertFalse(pp.isSet("nonsense"));
		assertFalse(pp.isSet("-b"));
		assertFalse(pp.isSet("--pure"));
		
		assertEquals("NO", pp.getOptionValue("prop"));
		assertEquals("100", pp.getOptionValue("miter"));
		assertNull(pp.getOptionValue("lol"));
	}
	
	public void testLogger() {
		Logger.setVerbosity(1);
		Logger.print(1, "hello");
		Logger.print(2, "hello2");
		Logger.print(3, "hello3");
		Logger.print(4, "hello4");
		System.out.println("==========");
		Logger.setVerbosity(2);
		Logger.print(1, "hello");
		Logger.print(2, "hello2");
		Logger.print(3, "hello3");
		Logger.print(4, "hello4");
		System.out.println("==========");
		Logger.setVerbosity(3);
		Logger.print(1, "hello");
		Logger.print(2, "hello2");
		Logger.print(3, "hello3");
		Logger.print(4, "hello4");
		
	}

}
