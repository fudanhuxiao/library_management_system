package library;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
/**
 * @author huxiao
 */
public class BookTest {

	private Book book;
	private Book book2;
	private Book book3;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		book = new Book("title", "author");
		book2 = new Book("Arrowsmith", "Sinclair Lewis");
		book3 = new Book("The Rainbow", "D.H. Lawrence");
	}

	@Test
	public void testBook() {
		Book book = new Book("title", "author");
		assertTrue(book instanceof Book);
		Book book2 = new Book("Arrowsmith", "Sinclair Lewis");
		assertTrue(book2 instanceof Book);
		Book book3 = new Book("The Rainbow", "D.H. Lawrence");
		assertTrue(book3 instanceof Book);

	}

	@Test
	public void testGetTitle() {
		assertEquals(book.getTitle(),"title");
		assertEquals(book2.getTitle(),"Arrowsmith");
		assertEquals(book3.getTitle(),"The Rainbow");
		assertNotEquals(book.getTitle(),"author");
	}

	@Test
	public void testGetAuthor() {
		assertEquals(book.getAuthor(),"author");
		assertEquals(book2.getAuthor(),"Sinclair Lewis");
		assertEquals(book3.getAuthor(),"D.H. Lawrence");
		assertNotEquals(book.getAuthor(),"title");
	}

	@Test
	public void testGetDueDate() {
		assertEquals(book.getDueDate(),-1);
		assertEquals(book2.getDueDate(),-1);
		assertEquals(book3.getDueDate(),-1);
		book.checkOut(1);
		assertEquals(book.getDueDate(),8);
		assertEquals(book2.getDueDate(),-1);
		assertEquals(book3.getDueDate(),-1);
		book2.checkOut(1);
		assertEquals(book.getDueDate(),8);
		assertEquals(book2.getDueDate(),8);
		assertEquals(book3.getDueDate(),-1);
		book3.checkOut(5);
		assertEquals(book.getDueDate(),8);
		assertEquals(book2.getDueDate(),8);
		assertEquals(book3.getDueDate(),12);
		book.checkIn();
		assertEquals(book.getDueDate(),-1);
		assertEquals(book2.getDueDate(),8);
		assertEquals(book3.getDueDate(),12);
	}

	@Test
	public void testCheckOut() {
		book.checkOut(1);
		assertEquals(book.getDueDate(),8);
		book2.checkOut(3);
		assertEquals(book2.getDueDate(),10);
		book3.checkOut(10);
		assertEquals(book3.getDueDate(),17);
	}

	@Test
	public void testCheckIn() {
		assertEquals(book.getDueDate(),-1);
		book.checkOut(2);
		assertEquals(book.getDueDate(),9);
		book.checkIn();
		assertEquals(book.getDueDate(),-1);

		assertEquals(book2.getDueDate(),-1);
		book2.checkOut(5);
		assertEquals(book2.getDueDate(),12);
		book2.checkIn();
		assertEquals(book2.getDueDate(),-1);

		assertEquals(book3.getDueDate(),-1);
		book3.checkOut(1);
		assertEquals(book3.getDueDate(),8);
		book3.checkIn();
		assertEquals(book3.getDueDate(),-1);
	}

	@Test
	public void testEqualsBook() {
		Book book4 = new Book("title","author");
		Book book5 = new Book("author","title");
		Book book6 = new Book("Arrowsmith", "Sinclair Lewis");
		assertTrue(book.equals(book4));
		assertTrue(book2.equals(book6));
		assertFalse(book.equals(book5));
		assertFalse(book.equals(book6));
		assertFalse(book3.equals(book4));
		assertFalse(book3.equals(book5));
		assertFalse(book3.equals(book6));
	}

	@Test
	public void testToString() {
		assertEquals("title, by author",book.toString());
		assertEquals("Arrowsmith, by Sinclair Lewis",book2.toString());
		assertEquals("The Rainbow, by D.H. Lawrence",book3.toString());
	}

}
