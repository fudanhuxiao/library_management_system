package library;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
/**
 * set 4 different notice days before and after the due date to check whether the overdue notice performed correctly.
 * @author huxiao
 *
 */

public class OverdueNoticeTest {
	OverdueNotice noticeday1;
	OverdueNotice noticeday2;
	OverdueNotice noticeday3;
	OverdueNotice noticeday4;
	Patron patron;
	Book book;
	Book book2;

	/**
	 * the book was checked out at day 1, and book2 was checked out at day 2.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		book = new Book("The Magus", "John Fowles");
		book2 = new Book("Ulysses", "James Joyce");
		patron = new Patron("Joe",null);
		book.checkOut(1);
		patron.take(book);
		book2.checkOut(2);
		patron.take(book2);
		noticeday1 = new OverdueNotice(patron,7);
		noticeday2 = new OverdueNotice(patron,8);
		noticeday3 = new OverdueNotice(patron,9);
		noticeday4 = new OverdueNotice(patron,10);
	}

	/**
	 * test the constructor.
	 */
	@Test
	public void testOverdueNotice() {
		assertTrue(noticeday1 instanceof OverdueNotice);
		assertTrue(noticeday2 instanceof OverdueNotice);
		assertTrue(noticeday3 instanceof OverdueNotice);
		assertTrue(noticeday4 instanceof OverdueNotice);
	}

	/**
	 * this method can test the functioning of the whole OverdueNotice class and the overdue attention before and after the due date.
	 */
	@Test
	public void testToString() {
		assertEquals("The overdue notice and all books checked out by Joe (day 7):\n1. The Magus, by John Fowles, due date: 8.\n2. Ulysses, by James Joyce, due date: 9.\n",noticeday1.toString()); 
		assertEquals("The overdue notice and all books checked out by Joe (day 8):\n1. The Magus, by John Fowles, due date: 8.\n2. Ulysses, by James Joyce, due date: 9.\n",noticeday2.toString()); 
		assertEquals("The overdue notice and all books checked out by Joe (day 9):\n1. The Magus, by John Fowles, due date: 8(ATTENTION: OVERDUE!).\n2. Ulysses, by James Joyce, due date: 9.\n",noticeday3.toString());
		assertEquals("The overdue notice and all books checked out by Joe (day 10):\n1. The Magus, by John Fowles, due date: 8(ATTENTION: OVERDUE!).\n2. Ulysses, by James Joyce, due date: 9(ATTENTION: OVERDUE!).\n",noticeday4.toString());
	}

}
