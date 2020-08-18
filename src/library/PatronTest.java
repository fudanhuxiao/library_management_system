package library;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
/**
 * @author huxiao
 */
public class PatronTest {
	private Patron dave;
	private Patron paula;
	private Patron joe;
	private Book book;
	private Book book2;
	private Book book3;
	private Book book4;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		dave = new Patron("Dave", null);
		paula = new Patron("Paula", null);
		joe = new Patron("Joe", null);
		book = new Book("Disappearing Nightly", "Laura Resnick");
		book2 = new Book("1984", "George Orwell");
		book3 = new Book("A Room With A View", "E.M. Forster");
		book4 = new Book("Beloved", "Toni Morrison");
	}

	/**
	 * Test method for {@link library.Patron#Patron(java.lang.String, library.Library)}.
	 */
	@Test
	public void testPatron() {
		Patron paula = new Patron("Paula", null);
		assertTrue(paula instanceof Patron);
		Patron dave = new Patron("Dave",null);
		assertTrue(dave instanceof Patron);
		Patron joe = new Patron("Joe", null);
		assertTrue(joe instanceof Patron);
	}

	/**
	 * Test method for {@link library.Patron#getName()}.
	 */
	@Test
	public void testGetName() {
		assertEquals("Dave", dave.getName());
		assertEquals("Paula", paula.getName());
		assertEquals("Joe", joe.getName());
	}

	/**
	 * Test method for {@link library.Patron#take(library.Book)}.
	 */
	@Test
	public void testTake() {
		paula.take(book);
		assertTrue(paula.getBooks().contains(book));
		assertFalse(dave.getBooks().contains(book));
		assertFalse(joe.getBooks().contains(book));
		dave.take(book);
		assertTrue(paula.getBooks().contains(book));
		assertTrue(dave.getBooks().contains(book));
		assertFalse(joe.getBooks().contains(book));
		joe.take(book);
		assertTrue(paula.getBooks().contains(book));
		assertTrue(dave.getBooks().contains(book));
		assertTrue(joe.getBooks().contains(book));
		paula.take(book2);
		assertTrue(paula.getBooks().contains(book));
		assertTrue(paula.getBooks().contains(book2));
		assertFalse(dave.getBooks().contains(book2));
		assertFalse(joe.getBooks().contains(book2));
		paula.take(book3);
		assertTrue(paula.getBooks().contains(book));
		assertTrue(paula.getBooks().contains(book2));
		assertTrue(paula.getBooks().contains(book3));
		assertFalse(dave.getBooks().contains(book2));
		assertFalse(joe.getBooks().contains(book2));
		assertFalse(dave.getBooks().contains(book3));
		assertFalse(joe.getBooks().contains(book3));
		paula.take(book4);
		assertFalse(paula.getBooks().contains(book4));
		assertFalse(dave.getBooks().contains(book2));
		assertFalse(joe.getBooks().contains(book2));
		assertFalse(dave.getBooks().contains(book3));
		assertFalse(joe.getBooks().contains(book3));
		assertFalse(dave.getBooks().contains(book4));
		assertFalse(joe.getBooks().contains(book4));
	}

	/**
	 * Test method for {@link library.Patron#giveBack(library.Book)}.
	 */
	@Test
	public void testGiveBack() {
		paula.take(book);
		assertTrue(paula.getBooks().contains(book));
		paula.giveBack(book);
		assertFalse(paula.getBooks().contains(book));
		dave.giveBack(book);
		assertFalse(dave.getBooks().contains(book));
		dave.take(book);
		dave.take(book2);
		dave.take(book3);
		assertTrue(dave.getBooks().contains(book));
		assertTrue(dave.getBooks().contains(book2));
		assertTrue(dave.getBooks().contains(book3));
		dave.giveBack(book);
		assertFalse(dave.getBooks().contains(book));
		assertTrue(dave.getBooks().contains(book2));
		assertTrue(dave.getBooks().contains(book3));
		dave.giveBack(book2);
		assertFalse(dave.getBooks().contains(book));
		assertFalse(dave.getBooks().contains(book2));
		assertTrue(dave.getBooks().contains(book3));
		dave.giveBack(book3);
		assertFalse(dave.getBooks().contains(book));
		assertFalse(dave.getBooks().contains(book2));
		assertFalse(dave.getBooks().contains(book3));
	}

	/**
	 * Test method for {@link library.Patron#getBooks()}.
	 */
	@Test
	public void testGetBooks() {
		dave.take(book);
		assertTrue(dave.getBooks().contains(book));
		assertEquals(1, dave.getBooks().size());
		dave.take(book2);
		assertTrue(dave.getBooks().contains(book));
		assertTrue(dave.getBooks().contains(book2));
		assertEquals(2, dave.getBooks().size());
		dave.take(book3);
		assertTrue(dave.getBooks().contains(book));
		assertTrue(dave.getBooks().contains(book2));
		assertTrue(dave.getBooks().contains(book3));
		assertEquals(3, dave.getBooks().size());
		dave.take(book4);
		assertTrue(dave.getBooks().contains(book));
		assertTrue(dave.getBooks().contains(book2));
		assertTrue(dave.getBooks().contains(book3));
		assertFalse(dave.getBooks().contains(book4));
		assertEquals(3, dave.getBooks().size());
	}

	/**
	 * Test method for {@link library.Patron#toString()}.
	 */
	@Test
	public void testToString() {
		assertEquals("Dave", dave.toString());
		assertEquals("Paula", paula.toString());
		assertEquals("Joe", joe.toString());
	}

}
