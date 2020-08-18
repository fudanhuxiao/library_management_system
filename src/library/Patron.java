package library;

import java.util.ArrayList;
/**
 * a class of patron's library card information
 * @author Chen Lin and Xiao Hu
 *
 */
public class Patron {

	private String name;
	private Library library;
	private ArrayList<Book> checkoutList;

	/**
	 * pass in the value of a patron's name and library he/she use.
	 * @param name
	 * @param library
	 */
	public Patron(String name, Library library) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.library = library;
		this.checkoutList = new ArrayList<Book>();
	}

	/**
	 * return this patron's name.
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * adds this book to the list of books checked out by this patron.
	 * no more than 3 books can be checked out by one patron.
	 * @param book
	 */
	public void take(Book book) {
		if (checkoutList.size()>2) {
			System.out.println("A patron with a card may have no more than 3 books at any time.");
		} else {
			checkoutList.add(book);
		}
	}

	/**
	 * removes this book object from the list of books checked out by this patron.
	 * print a message if the person didn't check out any book.
	 * @param book
	 */
	public void giveBack(Book book) {
		if (checkoutList.isEmpty()) {
			System.out.println("No books has been checked out by this person.");
		} else {
			checkoutList.remove(book);
		}
	}

	/**
	 * return the list of books currently checked out by this patron.
	 * @return
	 */
	public ArrayList<Book> getBooks() {
		return checkoutList;
	}

	/**
	 * return the name of the patron.
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}



}
