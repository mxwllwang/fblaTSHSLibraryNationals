package library;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/*
 * FBLA Nationals, Coding and Programming Tesla STEM School Library
 * Tesla STEM High School 
 * Author: Maxwell Wang 
 * Last Modified: 4/24/2018
 * 
 * Member Class - keeps track of a student or teacher and associated details
 * public static void - Book and Member subsystems testing
 */

public class Member implements Serializable, Comparable<Member> {

	private static final long serialVersionUID = 1L; // Serializable constant

	private boolean teacher; // True if member is a teacher (Student by default)
	private String firstName; // Member's First Name
	private String middleName; // Member's Middle Name
	private String lastName; // Member's Last Name (Required)
	private String notes; // Notes about member
	private Date dayAdded; // Date Member was added to the database
	private ArrayList<Book> books; // List of books the member has checked out

	// Returns true if member is a teacher, false if member is a student
	public boolean isTeacher() {
		return teacher;
	}

	// Returns member first name
	public String getFirstName() {
		return firstName;
	}

	// Returns member middle name
	public String getMiddleName() {
		return middleName;
	}

	// Returns member last name
	public String getLastName() {
		return lastName;
	}

	// Returns either "Teacher" or "Student" for member
	public String getStatus() {
		if (teacher) {
			return "Teacher";
		} else {
			return "Student";
		}
	}

	// Returns notes about member
	public String getNotes() {
		return notes;
	}

	// Day the member was added.
	public Date dayAdded() {
		return dayAdded;
	}

	// Returns array of books that the member has checked out.
	public ArrayList<Book> books() {
		return books;
	}

	// Amount of books the member has checked out
	public int getBookCount() {
		return books.size();
	}

	// Returns maximum amount of books allowed.
	public int getBookLimit() {
		if (teacher) {
			return TSHSLibrary.teacherBooksAllowed;
		} else {
			return TSHSLibrary.studentBooksAllowed;
		}
	}

	// Returns maximum number of days a book can be checked out for before overdue.
	public int getTimeLimit() {
		if (teacher) {
			return TSHSLibrary.teacherDaysAllowed;
		} else {
			return TSHSLibrary.studentDaysAllowed;
		}
	}

	// Constructor for a new member
	public Member(String firstName, String middleName, String lastName, boolean teacher, String notes) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.teacher = teacher;
		this.notes = notes;
		books = new ArrayList<Book>();
		dayAdded = Calendar.getInstance().getTime();
	}

	// Constructor for editing, dayAdded and books remains the same as the original member
	public Member(String firstName, String middleName, String lastName, boolean teacher, String notes, Date dayAdded,
			ArrayList<Book> b) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.teacher = teacher;
		this.notes = notes;
		books = b;
		this.dayAdded = dayAdded;
	}

	// Constructor used for binarySort - defines a member with just a dateAdded field
	public Member(Date dayAdded) {
		this.dayAdded = dayAdded;
	}

	// Checks out book b. TSHSLibrary makes sure that the book has not already been checked out.
	public void checkOut(Book b) {
		books.add(b);
		b.setCheckoutDate(Calendar.getInstance().getTime());
		b.checkedOut(this);
	}

	// Returns book b
	public void makeReturn(Book b) {
		books.remove(b);
		b.returned();
	}

	// For testing and general purposes, returns String representation of a member
	public String toString() {
		String member = "";
		if (teacher) {
			member += "Teacher: ";
		} else {
			member += "Student: ";
		}
		return member + getName();
	}

	// Returns the member's name as "Last, First M."
	public String getName() {
		if (middleName.length() > 0 && firstName.length() > 0) {
			return lastName + ", " + firstName + " " + middleName.charAt(0) + ".";
		} else if (firstName.length() > 0) {
			return lastName + ", " + firstName;
		} else {
			return lastName;
		}
	}

	// The database sorts members by dayAdded.
	@Override
	public int compareTo(Member arg0) {
		return this.dayAdded.compareTo(arg0.dayAdded);
	}

	/*
	 *  Systems testing
	 *  To perform systems testing, create members and books here, separate from the main program
	 *  Prints confirmations to console to ensure program is running smoothly
	 */
	public static void main(String[] args) {
		// Creates new member m1 with the following information
		Member m1 = new Member("First", "Middle", "Last", true, "Here are the notes");// is teacher
		System.out.println(m1.getName()); // Check name, should print ("Last, First M.")
		System.out.println(m1.getStatus() + " " + m1.getLastName() + " " + m1.getNotes()); // Testing individual getter functions
		// m1 member checks out 3 books with the following information
		m1.checkOut(new Book("Two", "Mary", "", "Shelley", "Periodical", "", m1.books, "", "these notes here")); // 0
		m1.checkOut(new Book("Two", "Mary", "", "Shelley", "Periodical", "", m1.books, "", "copy 2")); // 1
		m1.checkOut(new Book("One", "Percy", "", "Jackson", "Book", "", m1.books, "", "new book here")); // 2
		System.out.println("Book2 due date: " + m1.books.get(2).getDueDate()); // Due date should be 27 days from the day it was checked out
		// Tests the daysOverdue() function, important for fine calculations
		// If number is negative, Abs(daysOverdue()) is the amount of days until the dueDate
		// If number is positive, the book is overdue by that amount of days.
		System.out.println(m1.books.get(2) + "\nDAYSOVERDUE: " + m1.books.get(2).daysOverdue());
	}
}
