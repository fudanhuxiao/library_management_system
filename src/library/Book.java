package library;
/**
 * the class of a book with title, author and dueDate.
 * @author Chen Lin and Xiao Hu
 *
 */
public class Book {

	private String title;
	private String author;
	private int dueDate;

	/**
	 * Save the title, author of the book and set the due date to be -1 while the book is not checked out.
	 * @param title
	 * @param author
	 */
	public Book(String title, String author) {
		// constructor stub
		this.title = title;
		this.author = author;
		this.dueDate = -1;
	}

	/**
	 * return the book's title.
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * return this book's author.
	 * @return
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * return the due date of this book as an integer.
	 * @return
	 */
	public int getDueDate() {
		return dueDate;
	}

	/**
	 * when checking out, set the due date to be the check out date + 7
	 * @param the date is the check out date
	 */
	public void checkOut(int date) {
		dueDate = date + 7;
	}

	/**
	 * when checking in, set the due date to be -1 (not checked out).
	 */
	public void checkIn() {
		dueDate = -1;
	}

	/**
	 * determined whether to books are the same by comparing the title and author.
	 * @param book
	 * @return true if two books are the same and false if not.
	 */
	public boolean equals(Book book) {
		if ((this.title.equals(book.title)) && (this.author.equals(book.author))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * return a string of the form title, by author.
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return title + ", by " + author;
	}

}
