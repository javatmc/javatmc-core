package org.jtmc.core.scpi;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 * SCPICommandTest
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class SCPICommandTest {

	@Test
	public void testConstructorFromRaw() {
		SCPICommand command = new SCPICommand("CMD PARAM1,VAL1,PARAM2");
		assertEquals("CMD", command.getCommand());
		assertEquals(3, command.getParameters().size());
		assertEquals("CMD PARAM1,VAL1,PARAM2", command.getRaw());
		assertEquals("CMD PARAM1,VAL1,PARAM2", command.toString());
	}

	@Test
	public void testConstructorFromParams() {
		SCPICommand command = new SCPICommand("CMD", "PARAM1","VAL1","PARAM2");
		assertEquals("CMD", command.getCommand());
		assertEquals(3, command.getParameters().size());
		assertEquals("CMD PARAM1,VAL1,PARAM2", command.getRaw());
		assertEquals("CMD PARAM1,VAL1,PARAM2", command.toString());
	}

	@Test
	public void testNamedParameter() {
		SCPICommand command = new SCPICommand("CMD PARAM1,VAL1,PARAM2");
		assertEquals("CMD", command.getCommand());
		assertEquals("VAL1", command.getParameter("PARAM1").get());
		assertEquals(true, !command.getParameter("PARAM2").isPresent());
	}

	@Test
	public void testHasParameter() {
		SCPICommand command = new SCPICommand("CMD PARAM1,VAL1,PARAM2");
		assertEquals(true, command.hasParameter("PARAM1"));
		assertEquals(false, command.hasParameter("PARAM3"));
	}

	@Test
	public void testBuilderQuery() {
		SCPICommand command = SCPICommand.builder().query("CMD").build();
		assertEquals("CMD?", command.getCommand());
	}

	@Test
	public void testBuilderCommand() {
		SCPICommand command = SCPICommand.builder().command("CMD").with("PARAM1", "VAL1").with("PARAM2").build();
		assertEquals("CMD", command.getCommand());
		assertEquals(true, command.hasParameter("PARAM1"));
		assertEquals("VAL1", command.getParameter("PARAM1").get());
		assertEquals(false, command.hasParameter("PARAM3"));
	}
}