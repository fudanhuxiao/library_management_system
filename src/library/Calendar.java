package library;

/**
 * the calendar class record the passage of time.
 * @author Chen Lin and Xiao Hu
 *
 */
public class Calendar {

	private int date;
	/**
	 * the date represent today's date, set it to be zero at first.
	 */
	public Calendar() {
		// constructor stub
		date = 0;
	}

	/**
	 * return the current date as an integer.
	 * @return
	 */
	public int getDate() {
		return date;
	}

	/**
	 * increment and move ahead to the next day.
	 */
	public void advance() {
		date++;
	}

}
