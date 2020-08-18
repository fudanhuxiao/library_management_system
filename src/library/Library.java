uipackage library;

import java.util.*;
import java.io.*;

/**
 * the Library class
 * 
 * @author Chen Lin and Xiao Hu
 *
 */
public class Library {
	private boolean okToPrint;
	private ArrayList<Book> collection;
	private HashMap<String, Patron> patronDict;
	private boolean isOpen;
	private boolean doesQuit;
	private Calendar calendar;
	private Patron patronInService;
	private ArrayList<Book> booksMatched;
	private HashMap<Integer, Book> bookDict;

	/**
	 * read in the books from a text file
	 * 
	 * @return a list of books
	 */
	private ArrayList<Book> readBookCollection() {
		File file = new File("books.txt");
		ArrayList<Book> collection = new ArrayList<Book>();
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			while (true) {
				String line = reader.readLine();
				if (line == null)
					break;
				line = line.trim();
				if (line.equals(""))
					continue; // ignore possible blank lines
				String[] bookInfo = line.split(" :: ");
				collection.add(new Book(bookInfo[0], bookInfo[1]));
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return collection;
	}

	/**
	 * the first constructor used by the main method
	 */
	public Library() {
		this.okToPrint = true;
		this.doesQuit = false;
		this.collection = this.readBookCollection();
		this.calendar = new Calendar();
		this.patronDict = new HashMap<String, Patron>();
		this.booksMatched = new ArrayList<Book>();
		this.bookDict = new HashMap<Integer, Book>();
	}

	/**
	 * the second constructor used by the test methods
	 * 
	 * @param collection
	 *            , the list of books read in
	 */
	public Library(ArrayList<Book> collection) {
		this.okToPrint = false;
		this.doesQuit = false;
		this.collection = collection;
		this.calendar = new Calendar();
		this.patronDict = new HashMap<String, Patron>();
		this.booksMatched = new ArrayList<Book>();
		this.bookDict = new HashMap<Integer, Book>();
	}

	/**
	 * print the message using System.out.print
	 * 
	 * @param message
	 *            , the message to be printed
	 */
	public void print(String message) {
		if (this.okToPrint == true) {
			System.out.print(message);
		}
	}

	/**
	 * print the message using System.out.println
	 * 
	 * @param message
	 *            , the message to be printed
	 */
	public void println(String message) {
		if (this.okToPrint == true) {
			System.out.println(message);
		}
	}

	/**
	 * open the library in the morning
	 * 
	 * @return the list of notices
	 */
	public ArrayList<OverdueNotice> open() {
		this.calendar.advance();
		this.isOpen = true;
		return this.createOverdueNotices();
	}

	/**
	 * if some patrons hold some books that were due yesterday, create a list of
	 * overdue notices for these patrons
	 * 
	 * @return a list of overdue notices
	 */
	public ArrayList<OverdueNotice> createOverdueNotices() {
		ArrayList<OverdueNotice> overdueList = new ArrayList<OverdueNotice>();
		for (Patron eachPatron : this.patronDict.values()) {
			for (Book book : eachPatron.getBooks()) {

				if (this.calendar.getDate() == book.getDueDate() + 1) {
					OverdueNotice noticeToBeAdded = new OverdueNotice(
							eachPatron, this.calendar.getDate());
					System.out.print(noticeToBeAdded);
					overdueList.add(noticeToBeAdded);
				}
			}
		}
		return overdueList;
	}

	/**
	 * Issue a library card to the patron
	 * 
	 * @param nameOfPatron
	 *            , the name of the patron
	 * @return the patron who is issued the library card
	 */
	public Patron issueCard(String nameOfPatron) {
		Patron patronIssued = new Patron(nameOfPatron, this);
		if (this.patronDict.containsValue(patronIssued)) {
			this.println(nameOfPatron
					+ "has already got a library card. No more library card is allowed!");
		} else {
			this.patronDict.put(nameOfPatron, patronIssued);
		}
		return patronIssued;
	}

	/**
	 * prepare to check out or in books for one patron and print out the books
	 * held by the patron now
	 * 
	 * @param nameOfPatron
	 *            , the name of patron to be served
	 * @return the patron to be served
	 */
	public Patron serve(String nameOfPatron) {
		int index = 1;
		this.patronInService = this.patronDict.get(nameOfPatron);
		if (this.patronInService.getBooks().size() == 0) {
			this.println(this.patronInService.getName()
					+ " has held no book now!");
		} else {
			this.println(this.patronInService.getName()
					+ " has held the following books:");
			for (Book booksToBeCheckedIn : this.patronInService.getBooks()) {
				this.println(index + ". " + booksToBeCheckedIn);
				index++;
			}
		}
		if (this.patronInService.getBooks().isEmpty()) {
			this.bookDict = new HashMap<Integer, Book>();
		} else {
			for (int num = 1; num <= this.patronInService.getBooks().size(); num++) {
				this.bookDict.put(num,
						this.patronInService.getBooks().get(num - 1));
			}
		}
		return this.patronInService;
	}

	/**
	 * check in the corresponding books held by the current patron
	 * 
	 * @param bookNumbers
	 *            , the selected book numbers to be checked in
	 * @return a list of books which are just checked in
	 */
	public ArrayList<Book> checkIn(int... bookNumbers) {
		ArrayList<Book> booksCheckedIn = new ArrayList<Book>();
		for (int index : bookNumbers) {
			if (!(this.bookDict.keySet().contains(index))) {
				this.println("The book number " + index
						+ " you want to check in is not valid!");
			} else {
				Book book = bookDict.get(index);
				this.collection.add(book);
				this.patronInService.giveBack(book);
				booksCheckedIn.add(book);
			}
		}
		return booksCheckedIn;
	}

	/**
	 * search the books and print out the books which match the string
	 * 
	 * @param part
	 *            , the string used for search
	 * @return a list of found books
	 */
	public ArrayList<Book> search(String part) {
		int index = 1;
		this.booksMatched = new ArrayList<Book>();
		if (part.length() < 4) {
			this.println("Your search string is too short! It should be at least four characters long.");
		} else {
			for (Book book : this.collection) {
				if ((book.getTitle().toLowerCase().contains(part.toLowerCase()))
						|| (book.getAuthor().toLowerCase().contains(part
								.toLowerCase()))) {
					boolean doesRepeat = false;
					for (Book bookAlreadyMatched : this.booksMatched) {
						if (bookAlreadyMatched.equals(book)) {
							doesRepeat = true;
						}
					}
					if (doesRepeat == false) {
						this.booksMatched.add(book);
					}
				}
			}
		}
		if (this.booksMatched.size() == 0) {
			this.println("No such book can be searched in the library now!");
		} else {
			for (Book bookFound : booksMatched) {
				this.println(index + ". " + bookFound);
				index++;
			}
		}

		if (this.booksMatched.isEmpty()) {
			this.bookDict = new HashMap<Integer, Book>();
		} else {
			for (int num = 1; num <= this.booksMatched.size(); num++) {
				this.bookDict.put(num, this.booksMatched.get(num - 1));
			}
		}
		return this.booksMatched;
	}

	/**
	 * check out the corresponding books based on the search results
	 * 
	 * @param bookNumbers
	 *            , the selected book numbers to be checked out
	 * @return a list of books which are just checked out
	 */
	public ArrayList<Book> checkOut(int... bookNumbers) {
		ArrayList<Book> booksCheckedOut = new ArrayList<Book>();
		for (int index : bookNumbers) {
			if (!(this.bookDict.keySet().contains(index))) {
				this.println("The book number " + index
						+ " you want to check out is not valid!");
			} else {
				Book book = bookDict.get(index);
				this.collection.remove(book);
				this.patronInService.take(book);
				book.checkOut(calendar.getDate());
				booksCheckedOut.add(book);
			}
		}
		return booksCheckedOut;
	}

	/**
	 * close the library in the evening
	 */
	public void close() {
		this.isOpen = false;
		this.patronInService = null;
	}

	/**
	 * end the program and close the library forever
	 */
	public void quit() {
		this.doesQuit = true;
	}

	/**
	 * assemble all the arguments into a single string
	 * 
	 * @param commandString
	 *            , a string array corresponding to the user's input command
	 * @return a string which can be used as the arguments of some methods
	 */
	public static String processArg(String[] commandString) {
		String argString = "";
		for (int index = 1; index < commandString.length - 1; index++) {
			argString += commandString[index];
		}
		return argString + commandString[commandString.length - 1];
	}

	/**
	 * read in the user's commands, call the corresponding methods and print
	 * some necessary messages
	 */
	public void start() {

		Scanner scanner;
		String command = "";
		scanner = new Scanner(System.in);
		this.println("Welcome to our library! Please enter the following commands.");
		this.println("Command: open; Function: in the morning open the library");
		this.println("Command: issueCard Patron's name; Function: issue a library card");
		this.println("Command: serve Patron's name; Function: prepare to check in or out books");
		this.println("Command: checkIn Book numbers; Function: check in books");
		this.println("Command: search search string; Function: search for books");
		this.println("Command: checkOut Book numbers; Function: check out books");
		this.println("Command close; Function: in the evening close the library");
		this.println("Command: quit; Function: exit this program");
		while ((doesQuit == false) && (scanner.hasNext())) {
			command = scanner.nextLine();
			String[] commandString = command.split(" ");
			if (commandString[0].equals("open")) {
				this.println("Open the library now");
				this.open();
			} else if ((commandString[0].equals("issueCard"))
					&& (this.isOpen == true)) {
				this.println("Issue a library card to "
						+ this.processArg(commandString));
				this.issueCard(this.processArg(commandString));
			} else if ((commandString[0].equals("serve"))
					&& (this.isOpen == true)) {
				this.println("Prepare to check in or out books for "
						+ this.processArg(commandString));
				this.serve(this.processArg(commandString));
			} else if ((commandString[0].equals("checkIn"))
					&& (this.isOpen == true)) {
				this.println("Check in books, the book numbers are "
						+ this.processArg(commandString));
				String[] bookNumStr = this.processArg(commandString).split(",");
				int[] bookNumInt = new int[bookNumStr.length];
				int index = 0;
				for (String numStr : bookNumStr) {
					bookNumInt[index] = Integer.parseInt(numStr);
					index++;
				}
				this.checkIn(bookNumInt);
			} else if ((commandString[0].equals("search"))
					&& (this.isOpen == true)) {
				this.println("Search for books containing the string of "
						+ this.processArg(commandString));
				this.search(this.processArg(commandString));
			} else if ((commandString[0].equals("checkOut"))
					&& (this.isOpen == true)) {
				this.println("Check out books, the book numbers are "
						+ this.processArg(commandString));
				String[] bookNumStr = this.processArg(commandString).split(",");
				int[] bookNumInt = new int[bookNumStr.length];
				int index = 0;
				for (String numStr : bookNumStr) {
					bookNumInt[index] = Integer.parseInt(numStr);
					index++;
				}
				this.checkOut(bookNumInt);
			} else if ((commandString[0].equals("close"))) {
				this.println("Close the library now");
				this.close();
			} else if ((commandString[0].equals("quit"))) {
				this.println("The library has no funding due to a budget crisis and is closed forever!");
				this.quit();
			} else {
				this.println("Invalid command input! Please enter again.");
			}
		}
		scanner.close();
	}

	/**
	 * get the object calendar in Library class
	 * 
	 * @return the object calendar
	 */
	public Calendar getCalendar() {
		return this.calendar;
	}

	/**
	 * get the book collection list stored in the library
	 * 
	 * @return the book collection list
	 */
	public ArrayList<Book> getCollection() {
		return this.collection;
	}

	/**
	 * check whether the library is open
	 * 
	 * @return true if the library is open, otherwise false
	 */
	public boolean getIsOpen() {
		return this.isOpen;
	}

	/**
	 * check whether the program is ended
	 * 
	 * @return true if the library is closed forever, otherwise false
	 */
	public boolean getDoesQuit() {
		return this.doesQuit;
	}

	public static void main(String[] args) {
		Library libraryCreated = new Library();
		libraryCreated.start();
	}
}
