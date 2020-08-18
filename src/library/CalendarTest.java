package library;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
/**
 * @author huxiao
 */
public class CalendarTest {
	Calendar cal;

	@Before
	public void setUp() throws Exception {
		cal = new Calendar();
	}

	@Test
	public void testCalendar() {
		assertEquals(0, cal.getDate());
		cal.advance();
		assertEquals(1, cal.getDate());
		cal.advance();
		assertEquals(2, cal.getDate());

	}

	@Test
	public void testGetDate() {
		assertEquals(0, cal.getDate());
		cal.advance();
		assertEquals(1, cal.getDate());
		cal.advance();
		assertEquals(2, cal.getDate());
	}

	@Test
	public void testAdvance() {
		assertEquals(0, cal.getDate());
		cal.advance();
		assertEquals(1, cal.getDate());
		cal.advance();
		assertEquals(2, cal.getDate());
	}

}
