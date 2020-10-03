package org.jtmc.core.signal;

import static org.junit.Assert.assertEquals;

import org.jtmc.core.signal.Signal.DataPoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 * SignalTest
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class SignalTest {

	@Test
	public void testDefaultConstructor() {
		Signal<Float> signal = new Signal<>("test");
		assertEquals("test", signal.getId());
		assertEquals(0, signal.getData().size());
	}

	@Test
	public void testAddDataPoint() {
		Signal<Float> signal = new Signal<>("test");
		signal.add(0.0f, 5.0f);

		assertEquals(1, signal.getData().size());

		signal.add(new DataPoint<Float>(0.1f, 0.0f));

		assertEquals(2, signal.getData().size());

		assertEquals(0.0f, signal.min(), 0.1f);
		assertEquals(5.0f, signal.max(), 0.1f);
	}

	@Test
	public void testCopyConstructor() {
		Signal<Float> signal = new Signal<>("test");
		signal.add(0.0f, 1.0f);
		signal.add(1.0f, 0.0f);

		Signal<Float> copy = new Signal<>(signal);
		assertEquals("test", copy.getId());
		assertEquals(2, copy.getData().size());
	}

	@Test
	public void testMappingConstructor() {
		Signal<Float> signal = new Signal<>("test");
		signal.add(0.0f, 1.0f);
		signal.add(1.0f, 0.0f);

		Signal<Boolean> copy = new Signal<>(signal, value -> value > 0.5f);
		assertEquals("test", copy.getId());
		assertEquals(2, copy.getData().size());
		assertEquals(false, copy.min());
		assertEquals(true, copy.max());
	}
	
}