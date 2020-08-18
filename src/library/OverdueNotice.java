package library;

import java.util.ArrayList;
/**
 * this class is used to send an overdue notice to the patron.
 * @author Chen Lin and Xiao Hu
 *
 */
public class OverdueNotice {

	private Patron patron;
	private int todaysDate;
	private ArrayList<Book> list;
	/** 
	 * pass in the patron object and today's date.
	 * @param patron
	 * @param todaysDate
	 */
	public OverdueNotice(Patron patron, int todaysDate) {
		// TODO Auto-generated constructor stub
		this.patron = patron;
		this.todaysDate = todaysDate;
		this.list = this.patron.getBooks();
	}

	/**
	 * return a string that includes all the books currently checked out by the patron and the overdue notice.
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String s="The overdue notice and all books checked out by "+patron.getName()+" (day "+Integer.toString(todaysDate)+"):\n";
		for (int i = 0; i < list.size() ; i++) {
			s=s+Integer.toString(i+1)+". "+list.get(i).toString()+", due date: "+Integer.toString(list.get(i).getDueDate());
			if (todaysDate > list.get(i).getDueDate()) {
				s = s + "(ATTENTION: OVERDUE!).\n";
			} else {
				s = s + ".\n";
			} 
		}
		return s;
	}

}
