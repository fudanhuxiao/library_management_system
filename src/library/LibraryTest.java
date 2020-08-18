/**
 * 
 */
package library;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author David Matuszek, Chen Lin and Xiao Hu
 */
public class LibraryTest {
	private Book contact;
	private Book contact2;
	private Book equalRites;
	private Book sisters;
	private Book witches;
	private Book nightly;
	private Book time;
	private Book rings;
	private ArrayList<Book> books;
	private Library library;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		contact = new Book("Contact", "Carl Sagan");
		equalRites = new Book("Equal Rites", "Terry Pratchett");
		sisters = new Book("Weird Sisters", "Terry Pratchett");
		witches = new Book("Witches Abroad", "Terry Pratchett");
		nightly = new Book("Disappearing Nightly", "Laura Resnick");
		contact2 = new Book("Contact", "Carl Sagan");
		time = new Book("Nick of Time", "Ted Bell");
		rings = new Book("Lord of the rings", "J. R. R. Tolkien");
		books = new ArrayList<Book>();
		books.add(contact);
		books.add(witches);
		books.add(sisters);
		books.add(equalRites);
		books.add(nightly);
		books.add(contact2);
		books.add(time);
		books.add(rings);
		library = new Library(books);
		Patron dave1; 


	}

	/**
	 * Test method for {@link library.Library#open()}.
	 */
	@Test
	public void testOpen() {
		// The open() method doesn't make any change that we can
		// readily test, but we can at least make sure it returns
		// an ArrayList<OverdueNotice>.
		assertEquals(new ArrayList<OverdueNotice>(), library.open());
		// Check whether the library is open after calling open() method
		assertTrue(library.getIsOpen());
		// Check whether the calendar of the library advances after calling open() method
		assertEquals(1, library.getCalendar().getDate());
		library.open();
		assertEquals(2, library.getCalendar().getDate());
	}

	/**
	 * open the library, issue a library card to Dave and serve Dave
	 * @return the Patron Dave
	 */
	private Patron openAndServeDave() {
		library.open();
		Patron dave = library.issueCard("Dave");
		library.serve("Dave");
		return dave;
	}

	/**
	 * open the library, issue a library card to Mary and serve Mary
	 * @return the Patron Mary
	 */
	private Patron openAndServeMary() {
		library.open();
		Patron mary = library.issueCard("Mary");
		library.serve("Mary");
		return mary;
	}

	/**
	 * open the library, issue a library card to John and serve John
	 * @return the Patron John
	 */
	private Patron openAndServeJohn() {
		library.open();
		Patron john = library.issueCard("John");
		library.serve("John");
		return john;
	}

	/**
	 * Test method for {@link library.Library#createOverdueNotices()}.
	 */
	@Test
	public void testCreateOverdueNotices() {
		ArrayList<OverdueNotice> notices;
		openAndServeDave();
		ArrayList<Book> foundBooks = library.search("Equal Rites");
		assertEquals(equalRites, foundBooks.get(0));
		library.checkOut(1);

		int dueDate = 7;
		// Don't send an overdue notice during the next seven days
		for (int i = 0; i < dueDate; i++) {
			library.close();
			notices = library.open();
			assertTrue(notices.isEmpty());
			System.out.println(library.getCalendar().getDate());
		}
		System.out.println(library.getCalendar().getDate());
		library.close();
		for (int i=0;i<library.getCollection().size();i++){
			System.out.println(library.getCollection().get(i).getDueDate());}
		// Send a notice on the 8th day
		notices = library.open();
		assertEquals(1, notices.size());
		library.close();
		// Don't send another notice after that
		notices = library.open();
		assertTrue(notices.isEmpty());
	}

	/**
	 * Test method for {@link library.Library#issueCard(java.lang.String)}.
	 */
	@Test
	public void testIssueCard() {
		library.open();
		// For the patron named Dave
		Patron dave = library.issueCard("Dave");
		assertTrue(dave instanceof Patron);
		assertEquals("Dave", dave.getName());
		// For the patron named Mary
		Patron mary = library.issueCard("Mary");
		assertTrue(mary instanceof Patron);
		assertEquals("Mary", mary.getName());
	}

	/**
	 * Test method for {@link library.Library#serve(java.lang.String)}.
	 */
	@Test
	public void testServe() {
		// We can test if the correct Patron is returned, but not if
		// it's being saved. The tests for checkIn and checkOut can
		// determine this.
		library.open();
		Patron dave = library.issueCard("Dave");
		Patron paula = library.issueCard("Paula");
		// For the patron named Dave
		assertEquals(dave, library.serve("Dave"));
		// For the patron named Paula
		assertEquals(paula, library.serve("Paula"));
	}


	/**
	 * Test method for {@link library.Library#checkOut(int[])}.
	 */
	@Test
	public void testCheckOutOneBook() {
		// Dave checkout one book called Nick of Time
		Patron dave = openAndServeDave();
		library.search("Time");
		ArrayList<Book> booksCheckedOut = library.checkOut(1);
		assertTrue(dave.getBooks().contains(time));
		assertEquals(dave.getBooks(), booksCheckedOut);
		// Book shouldn't still be in library
		ArrayList<Book> booksFound = library.search("Time");
		assertTrue(booksFound.isEmpty());
		// Mary checkout one book called Lord of the rings
		Patron mary = openAndServeMary();
		library.search("rings");
		ArrayList<Book> booksCheckedOut2 = library.checkOut(1);
		assertTrue(mary.getBooks().contains(rings));
		assertEquals(mary.getBooks(), booksCheckedOut2);
		ArrayList<Book> booksFound2 = library.search("rings");
		assertTrue(booksFound2.isEmpty());
		// John want to checkout one book which is not in the library
		Patron john = openAndServeJohn();
		library.search("flower");
		ArrayList<Book> booksCheckedOut3 = library.checkOut(1);
		assertTrue(booksCheckedOut3.isEmpty());
	}

	/**
	 * Test method for {@link library.Library#checkOut(int[])}.
	 */
	@Test
	public void testCheckOutOneOfMultipleCopies() {
		Patron dave = openAndServeDave();
		ArrayList<Book> booksFound = library.search("Carl Sagan");
		library.checkOut(1);
		// There should still be another copy in the library
		booksFound = library.search("Carl Sagan");
		assertEquals(1, booksFound.size());
	}


	/**
	 * Test method for {@link library.Library#checkOut(int[])}.
	 */
	@Test
	public void testCheckOutBooksInRandomOrder() {
		// Dave checkout three different books separately 
		Patron dave = openAndServeDave();
		library.search("Terry Pratchett");
		library.checkOut(1);
		library.checkOut(3);
		library.checkOut(2);
		ArrayList<Book> davesBooks = dave.getBooks();
		assertTrue(davesBooks.contains(witches));
		assertTrue(davesBooks.contains(sisters));
		assertTrue(davesBooks.contains(equalRites));
		// Dave checkout three different books together
		int[] bookNumbers = {1, 2, 3};
		library.serve("Dave");
		library.checkIn(bookNumbers);
		library.serve("Dave");
		library.search("Terry Pratchett");
		library.checkOut(bookNumbers);
		ArrayList<Book> davesBooks2 = dave.getBooks();
		assertTrue(davesBooks2.contains(witches));
		assertTrue(davesBooks2.contains(sisters));
		assertTrue(davesBooks2.contains(equalRites));
		// Mary checkout two same books
		Patron mary = openAndServeMary();
		library.search("Contact");
		library.checkOut(1);
		library.search("Contact");
		library.checkOut(1);
		ArrayList<Book> marysBooks = mary.getBooks();
		assertTrue(marysBooks.contains(contact));	
		assertEquals(2, marysBooks.size());
	}

	/**
	 * Test method for {@link library.Library#checkIn(int[])}.
	 */
	@Test
	public void testCheckInOneBook() {
		// Check in one book from Dave called Disappearing Nightly
		Patron dave = openAndServeDave();
		ArrayList<Book> foundBooks = library.search("Disappearing Nightly");
		// Checking out a book moves it from the library to the patron
		library.checkOut(1);
		assertTrue(dave.getBooks().contains(nightly));
		assertTrue(library.search("Disappearing Nightly").isEmpty());
		// Checking in a book moves it back from the patron to the library
		library.serve("Dave");
		library.checkIn(1);
		assertFalse(library.search("Disappearing Nightly").isEmpty());
		// Check in one book from Mary called Equal Rites
		Patron mary = openAndServeMary();
		ArrayList<Book> foundBooks2 = library.search("Equal Rites");
		library.checkOut(1);
		assertTrue(mary.getBooks().contains(equalRites));
		assertTrue(library.search("Equal Rites").isEmpty());
		library.serve("Mary");
		library.checkIn(1);
		assertFalse(library.search("Equal Rites").isEmpty());
		// Check in one book which is not held by John
		Patron john = openAndServeJohn();
		ArrayList<Book> booksCheckedIn = library.checkIn(1);
		assertTrue(booksCheckedIn.isEmpty());
	}


	/**
	 * Test method for {@link library.Library#checkIn(int[])}.
	 */
	@Test
	public void testCheckInManyBooks() {
		openAndGiveBooksToDave();
		Patron dave = library.serve("Dave");
		ArrayList<Book> davesBooks = dave.getBooks();
		// Check the number of books Dave have got
		assertEquals(3, davesBooks.size());
		// Check whether one book held by Dave is still in the library or not
		Book someBook = davesBooks.get(1); // Can't be sure which book this is
		library.search(someBook.getTitle()).isEmpty();
		assertTrue(library.search(someBook.getTitle()).isEmpty());
		// Check whether another book held by Dave is still in the library or not
		Book anotherBook = davesBooks.get(2);
		assertTrue(library.search(anotherBook.getTitle()).isEmpty());
		int[] bookNumbers = {1, 2, 3};
		library.serve("Dave");
		library.checkIn(bookNumbers);
		assertEquals(0, davesBooks.size());
	}

	/**
	 * open the library, issue a library card to Dave, serve Dave, search books which match "Terry", and checkout three books
	 */
	private void openAndGiveBooksToDave() {
		Patron dave = openAndServeDave();
		library.search("Terry");
		library.checkOut(1);
		library.checkOut(2);
		library.checkOut(3);
	}

	/**
	 * Test method for {@link library.Library#search(java.lang.String)}.
	 */
	@Test
	public void testSearchTitle() {
		library.open();
		ArrayList<Book> foundBooks = library.search("appear");
		assertEquals(1, foundBooks.size());
		assertEquals("Disappearing Nightly", foundBooks.get(0).getTitle());
		foundBooks = library.search("xyzzy");
		assertEquals(0, foundBooks.size());
		foundBooks = library.search("Terry");
		assertEquals(3, foundBooks.size());
		assertTrue(foundBooks.contains(equalRites));
		assertTrue(foundBooks.contains(sisters));
		assertTrue(foundBooks.contains(witches));
	}

	/**
	 * Test method for {@link library.Library#search(java.lang.String)}.
	 */
	@Test
	public void testSearchAuthor() {
		library.open();
		ArrayList<Book> foundBooks = library.search("Resnick");
		assertEquals(1, foundBooks.size());
		assertEquals("Laura Resnick", foundBooks.get(0).getAuthor());
		foundBooks = library.search("flower");
		assertEquals(0, foundBooks.size());
		foundBooks = library.search("Terry");
		assertEquals(3, foundBooks.size());
	}

	/**
	 * Test method for {@link library.Library#search(java.lang.String)}.
	 */
	@Test
	public void testSearchWithMixedCase() {
		library.open();
		ArrayList<Book> foundBooks = library.search("laura");
		assertEquals(1, foundBooks.size());
		foundBooks = library.search("NIGHTLY");
		assertEquals(1, foundBooks.size());
		foundBooks = library.search("Nick");
		assertEquals(2, foundBooks.size());
		foundBooks = library.search("tErRy");
		assertEquals(3, foundBooks.size());
		foundBooks = library.search("eQuAL");
		assertEquals(1, foundBooks.size());
	}

	/**
	 * Test method for {@link library.Library#search(java.lang.String)}.
	 */
	@Test
	public void testSearchAndIgnoreDuplicates() {
		library.open();
		ArrayList<Book> foundBooks = library.search("Contact");
		assertEquals(1, foundBooks.size());
	}

	/**
	 * Test method for {@link library.Library#close()}
	 */
	@Test
	public void testClose() {
		openAndGiveBooksToDave();
		library.close();
		assertFalse(library.getIsOpen());
	}

	/**
	 * Test method for {@link library.Library#quit()}
	 */
	@Test
	public void testQuit() {
		library.quit();
		assertTrue(library.getDoesQuit());
	}

	/**
	 * Test method for {@link library.Library#processArg(String[])}
	 */
	@Test
	public void testProcessArg() {
		String[] command1 = {"serve", "Dave"};
		String arg1 = library.processArg(command1);
		assertEquals("Dave", arg1);
		String[] command2 = {"search", "Terry"};
		String arg2 = library.processArg(command2);
		assertEquals("Terry", arg2);
		String[] command3 = {"checkIn", "1,2,3"};
		String arg3 = library.processArg(command3);
		assertEquals("1,2,3", arg3);
		String[] command4 = {"checkIn", "1,", "2,", "3"};
		String arg4 = library.processArg(command3);
		assertEquals("1,2,3", arg4);
	}

	/**
	 * Test method for {@link library.Library#getCalendar()}
	 */
	@Test
	public void testGetCalendar() {
		assertEquals(0, library.getCalendar().getDate());
		library.open();
		assertEquals(1, library.getCalendar().getDate());
		library.open();
		assertEquals(2, library.getCalendar().getDate());
	}

	/**
	 * Test method for {@link library.Library#getCollection()}
	 */
	@Test
	public void testGetCollection() {
		assertEquals(8, library.getCollection().size());
		openAndGiveBooksToDave();
		assertEquals(5, library.getCollection().size());
		assertTrue(library.getCollection().contains(contact));
		assertTrue(library.getCollection().contains(nightly));
		assertFalse(library.getCollection().contains(sisters));
	}

	/**
	 * Test method for {@link library.Library#getIsOpen()}
	 */
	@Test
	public void testGetIsOpen() {
		library.open();
		assertTrue(library.getIsOpen());
		library.close();
		assertFalse(library.getIsOpen());
	}

	/**
	 * Test method for {@link library.Library#getDoesQuit()}
	 */
	@Test
	public void testGetDoesQuit() {
		library.quit();
		assertTrue(library.getDoesQuit());
	}
}
