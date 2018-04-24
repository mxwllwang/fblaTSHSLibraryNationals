package library;

// Uses Java Swing and Standard Development Toolkit
// JRE 7, version 1.7.0_80

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;
import org.eclipse.wb.swing.FocusTraversalOnArray;

/*
 * PROGRAM: FBLA Nationals, Coding and Programming Tesla STEM School Library
 * Tesla STEM High School
 * AUTHOR: Maxwell Wang 
 * LAST MODIFIED: 4/24/2018
 * 
 * A management database to manage daily operations of a school library.
 * Controls library constants, database interface, as well as report generations.
 * 
 * See Also: SchoolLibrary.Book, SchoolLibrary.Member
 * For additional help with navigation, view the Textual Hierarchy text file
 * 
 * Organized by labels for TAB: BOOKS/MEMBERS/REPORT/HELP/SETTINGS
 * 
 */

public class TSHSLibrary {

	// FIELDS: Library information (constants)

	public static Calendar calendar = Calendar.getInstance(); // Unified calendar

	// Number of days a member can check out a book
	public static int teacherDaysAllowed = 28;
	public static int studentDaysAllowed = 21;
	// Max number of books a member can check out
	public static int teacherBooksAllowed = 10;
	public static int studentBooksAllowed = 5;

	// Daily incurred fines, in dollars for the four book types (book, periodical, reference, video)
	public static double bookDailyFine = 0.10;
	public static double periodicalDailyFine = 0.10;
	public static double referenceDailyFine = 0.25;
	public static double videoDailyFine = 0.25;

	public static double maxFine = 25.00; //maximum charge for an overdue item, in dollars

	private static ArrayList<Book> books; // Data repository for all books
	private static ArrayList<Member> members; // Data repository for all members
	private Member selectedMember; // Member that is selected by the user

	// FIELDS: Database Components

	private JFrame frmTshsLibrary; // Main frame (holds the tabbedPane)
	private JTabbedPane tabbedPane; // Manages separate tabs
	private JLabel lblTitle; // Database Description

	// TAB: BOOKS
	private JPanel panelBooks;
	private JLabel lblBookPanelTitle;

	private JPanel panelAddBook; // Holds fields and buttons for book creation
	// Fields for adding book
	private JTextField textFieldBookTitle;
	private JTextField textFieldAuthorFirstName;
	private JTextField textFieldAuthorMiddleName;
	private JTextField textFieldAuthorLastName;
	private JComboBox<String> comboBoxDewey1;
	private JComboBox<String> comboBoxDewey2;
	private JComboBox<String> comboBoxBookType;
	private JTextArea textAreaBookNotes;
	private JTextField textFieldBookISBN;
	private JSpinner spinnerCopies;
	// Field descriptor labels
	private JLabel lblBookTitle;
	private JLabel lblAuthorFirstName;
	private JLabel lblAuthorMiddleName;
	private JLabel lblAuthorLastName;
	private JLabel lblDewey;
	private JLabel lblAddABook;
	private JLabel lblBookType;
	private JLabel lblBookNotes;
	private JLabel lblCopies;
	private JLabel lblBookISBN;
	// Buttons
	private JButton btnCreateBook; // Creates a new book
	private JButton btnCancelBook; // Clears fields

	private JPanel panelBookInfo; // Book view pane
	private JLabel lblBookRequired;
	private JLabel lblBookViewTitle;
	// Book view pane display labels
	private JLabel lblBookViewBooktitle;
	private JLabel lblBookViewAuthor;
	private JLabel lblBookViewID;
	private JLabel lblBookViewTimesCheckedOut;
	private JLabel lblBookViewNotes;
	private JLabel lblBookViewDayAdded;
	private JLabel lblBookViewISBN;
	private JTextPane textPaneBookViewNotes;
	// Buttons
	private JButton btnBookEdit; // Allows the editing of a book
	private JButton btnBookDelete; // Removes a book

	private JPanel panelBookTable; // Panel holding the scrollPane
	private JPanel panelBookSearchSort; // Panel at the top of the table, can be used for future search function
	private JScrollPane scrollPaneBooks; // Allows the table to scroll
	private JTable tableBooks; // Table showing books
	private JLabel lblBookTableHeader; // Table label
	private DefaultTableModel dtmBooks; // dtm to manage tableBooks

	private JPanel panelBookFooter; // Shows total amount of books
	private JLabel lblpanelBooksFooter;

	// TAB: MEMBERS
	private JPanel panelMembers;
	private JLabel lblMemberpaneltitle;

	private JPanel panelAddMember; // Holds fields and buttons for member creation
	// Fields for adding member
	private JTextField textFieldMemberFirstName;
	private JTextField textFieldMemberMiddleName;
	private JTextField textFieldMemberLastName;
	private JComboBox<String> comboBoxMemberType;
	private JTextArea textAreaMemberNotes;
	// Field descriptor labels
	private JLabel lblMemberViewTitle;
	private JLabel lblRegisterNewMember;
	private JLabel lblMemberFirstName;
	private JLabel lblMemberMiddleName;
	private JLabel lblMemberLastName;
	private JLabel lblMemberNotes;
	// Buttons
	private JButton btnCreateMember; // Creates a new member
	private JButton btnCancelMember; // Clears fields

	private JPanel panelMemberInfo; // Member view pane
	private Label lblMemberRequired;
	// Member ViewPane display labels
	private JLabel lblMemberViewName;
	private JLabel lblMemberViewStatus;
	private JLabel lblMemberViewNotes;
	private JLabel lblMemberViewDateAdded;
	private JTextPane textPaneMemberViewNotes;
	// Buttons
	private JButton btnMemberEdit; // Allows the editing of a member
	private JButton btnMemberDelete; // Removes a member

	private JTable tableMembers; // Table showing members
	private JPanel panelMemberTable; // Panel holding the scrollPane
	private JPanel panelMemberSearchSort; // Panel at the top of the table, can be used for future search function
	private JScrollPane scrollPaneMembers; // Allows the table to scroll
	private JLabel lblMemberTableHeader; // Table label
	private DefaultTableModel dtmMembers; // dtm to manage tableMembers

	private JTable tableMemberBooks; // Table showing the books a member has checked out
	private JScrollPane scrollPaneMemberBooks; // Allows the table to scroll
	private DefaultTableModel dtmMemberBooks; // dtm to manage tableMemberBooks
	private JPanel panelMemberCheckout;

	private JPanel panelCheckOutBook; // Panel under table, allows book checkouts
	private JLabel lblTableCheckout;
	private JButton btnCheckOutBook; // Checks out book
	private JTextField textFieldCheckOutID; // ID input for checkout

	private JPanel panelMemberActions; // Bottom panel, holds btnMemberReport
	private JButton btnMemberReport; // Generate specific report for one member
	private JPanel panelMemberFooter; // Label showing how many members there are
	private JLabel lblpanelMembersFooter;

	// TAB: REPORT
	private JPanel panelReports;
	private String divider = "==============================================================";
	private String dividerThin = "------------------------------------------------------------------------------------------------------------";

	private JPanel panelReportInfo; // Shows settings for report generation
	private JLabel lblReportName; // Displays name of member in specific reports
	private JLabel lblReportHeader; // Displays "generate report"
	private JLabel lblReportInfo; // Displays "report info"
	private ButtonGroup btnGroupReportType; // Holds ReportIsuance and ReportFines
	private JRadioButton rdbtnReportIssuance; // Book Issuance Report
	private JRadioButton rdbtnReportFines; // Fines Report
	private ButtonGroup btnGroupMemberType; // Holds ReportSelectedMember and AllMembers
	private JRadioButton rdbtnReportSelectedMember; // Make report for member selected on Member tab
	private JRadioButton rdbtnAllMembers; // Make report for all members
	private ButtonGroup btnGroupWeeklyType; // Holds ReportWeekly and ReportComplete
	private JRadioButton rdbtnReportWeekly; // Weekly summary
	private JRadioButton rdbtnReportComplete; // All time summary
	private JButton btnGenerateReport; // Button for generating report

	private JPanel panelReportCommand; // At the bottom, holds report functions
	private JButton btnReportCancel; // Clears settings for the report
	private JButton btnReportSave; // Opens up window to save the report
	private JButton btnReportPrint; // Opens up window to print the report

	private String report = "empty"; // Holds the string for the report
	private NumberFormat formatter = NumberFormat.getCurrencyInstance(); // Formats report currency

	private JPanel panelReportPreview; // Holds preview pane
	private JScrollPane scrollPaneReportPreview; // Allows report to scroll
	private JLabel lblReportPreview; // Displays "preview"
	private JTextPane textPaneReportPreview; // Previews generated report

	// TAB: SETTINGS
	private JPanel panelSettings;

	private JLabel lblSettings; // Labels and fields to display library settings
	private JLabel lblCheckouts;
	private JLabel lblFines;
	private JLabel lblMaxAllottedBooksS; // Allowed books for a student
	private JLabel lblMaxAllottedBooksT; // Allowed books for a teacher
	private JLabel lblMaxTimeS; // Time in days a student can check out a book
	private JLabel lblMaxTimeT; // Time in days a teacher can check out a book
	private JFormattedTextField formattedTextFieldBooksS; // TextFields displaying above settings
	private JFormattedTextField formattedTextFieldBooksT;
	private JFormattedTextField formattedTextFieldDaysS;
	private JFormattedTextField formattedTextFieldDaysT;
	private JLabel lblFineBook; // Fines for overdue books
	private JLabel lblFinePeriodical;
	private JLabel lblFineReference;
	private JLabel lblFineVideo;
	private JLabel lblMaxFine; // Maximum fines that can be accumulated for a book
	private JFormattedTextField formattedTextFieldBook; // TextFields displaying fines settings
	private JFormattedTextField formattedTextFieldPeriodical;
	private JFormattedTextField formattedTextFieldReference;
	private JFormattedTextField formattedTextFieldVideo;
	private JFormattedTextField formattedTextFieldMaxFine;
	// Buttons
	private JButton btnSaveSettings; // Button for saving the settings or reset
	private JButton btnResetSettings;

	// TAB: HELP
	private JPanel panelHelp; // Holds the questions and answers for FAQ
	private JLabel lblLibraryDatabaseHelp;
	private JButton btnHelpAddBooks; // "How do you add books?"
	private JLabel lblHelpAddBooks;
	private JButton btnHelpChangeBooks; // "How do you edit/delete books?"
	private JLabel lblHelpChangeBooks;
	private JButton btnHelpDewey; // "What is the dewey field for?"
	private JLabel lblHelpDewey;
	private JButton btnHelpMembers; // "How do you add/edit members?"
	private JLabel lblHelpMembers;
	private JButton btnHelpCheckout; // "How do you check out a book?"
	private JLabel lblHelpCheckout;
	private JButton btnHelpReturn; // "How do you return a book?"
	private JLabel lblHelpReturn;
	private JButton btnHelpReport; // "How do you make a report?"
	private JLabel lblHelpReport;
	private JButton btnHelpPrint; // "How do you save/print reports?"
	private JLabel lblHelpPrint;
	private JButton btnHelpSettings; // "Can you modify settings?"
	private JLabel lblHelpSettings;
	private JLabel lblMaxwellWangFbla; // Info label at the bottom of the help pane

	// Shows view pane when a book in the table is selected
	// BOOK_SELECTED
	ListSelectionListener tableBookSelectionChanged = new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent event) {
			if (!event.getValueIsAdjusting() && btnCreateBook.getText().equals("Create")
					&& tableBooks.getSelectedRow() != -1) {
				clearBookFields();
				// Finds book information in database with ID
				long idSample = (long) tableBooks.getValueAt(tableBooks.getSelectedRow(), 2);
				System.out.print("Looking for " + idSample);
				System.out.println(books.toString());
				Book sample = books.get(Collections.binarySearch(books, new Book(idSample)));
				System.out.println("book selected: " + sample);

				// Initializes and makes BookView fields visible
				lblBookViewTitle.setText("Book Selected");
				lblBookViewTitle.setVisible(true);
				lblBookViewBooktitle.setText(sample.getTitle());
				lblBookViewBooktitle.setVisible(true);
				lblBookViewAuthor.setText(sample.authorName());
				lblBookViewAuthor.setVisible(true);
				lblBookViewID.setText("ID: " + idSample);
				lblBookViewID.setVisible(true);
				lblBookViewTimesCheckedOut.setText("Checked out " + sample.timesCheckedOut() + " times");
				lblBookViewTimesCheckedOut.setVisible(true);
				lblBookViewDayAdded.setText("Added on " + sample.getDayAdded().toString());
				lblBookViewDayAdded.setVisible(true);
				lblBookViewISBN.setText("ISBN: " + sample.getISBN());
				lblBookViewISBN.setVisible(true);
				lblBookViewNotes.setVisible(true);
				textPaneBookViewNotes.setText(sample.getNotes());
				textPaneBookViewNotes.setVisible(true);
				textFieldCheckOutID.setText(String.valueOf(idSample)); // Fills ID into checkout on Members tab
				// Makes buttons visible
				btnBookEdit.setVisible(true);
				btnBookDelete.setVisible(true);
			}
		}
	};

	// Shows view pane when a member in the table is selected
	// MEMBER_SELECTED
	ListSelectionListener tableMembersSelectionChanged = new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent event) {
			if (!event.getValueIsAdjusting() && btnCreateMember.getText().equals("Create")
					&& tableMembers.getSelectedRow() != -1) {
				clearMemberFields();
				// Finds member information in database by date added
				Date d = (Date) tableMembers.getValueAt(tableMembers.getSelectedRow(), 5);
				Member sample = members.get(Collections.binarySearch(members, new Member(d)));
				selectedMember = sample; // updates currently selected member
				System.out.println("member selected");

				// Initializes and makes BookView fields visible
				lblMemberViewTitle.setText("Member Selected");
				lblTableCheckout.setText("Books Checked Out: " + selectedMember.getBookCount());
				lblMemberViewName.setText(sample.getName());
				lblMemberViewName.setVisible(true);
				lblMemberViewStatus.setText(sample.getStatus());
				lblMemberViewStatus.setVisible(true);
				lblMemberViewDateAdded.setText("Date Added: " + sample.dayAdded());
				lblMemberViewDateAdded.setVisible(true);
				lblMemberViewNotes.setText("Notes");
				lblMemberViewNotes.setVisible(true);
				textPaneMemberViewNotes.setText(sample.getNotes());
				textPaneMemberViewNotes.setVisible(true);
				btnMemberEdit.setVisible(true);
				btnMemberDelete.setVisible(true);
				btnMemberReport.setEnabled(true);
				rdbtnReportSelectedMember.setEnabled(true);
				// Sets up report tab information for selected member
				lblReportName.setText(selectedMember.getName() + " (" + selectedMember.getStatus() + ")");
				lblReportName.setEnabled(true);
				refreshMemberBooks(selectedMember, true); // makes tableMemberBooks display new member's books
			}
		}
	};

	// Allows checkouts when a book in the memberbooks table is selected
	// MEMBER_BOOK_SELECTED
	ListSelectionListener tableMemberBooksSelectionChanged = new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent event) {
			if (!event.getValueIsAdjusting() && tableMemberBooks.getSelectedRow() >= 0) {
				System.out.println("memberbooks table selection changed");
				long idSample = (long) tableMemberBooks.getValueAt(tableMemberBooks.getSelectedRow(), 2);
				btnCheckOutBook.setText("Return");
				textFieldCheckOutID.setText(String.valueOf(idSample));
			}
		}
	};

	/**
	 * Launch the application.
	 * 
	 * @throws FileNotFoundException
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 **/
	@SuppressWarnings("unchecked")
	public static void main(String[] args)
			throws FileNotFoundException, IOException, ClassNotFoundException, InterruptedException {

		// Extracts serialized ArrayLists from data file
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("TSHSLibrarydata.ser"));
			System.out.println("searching for file");
			books = (ArrayList<Book>) in.readObject(); // Extracts books
			members = (ArrayList<Member>) in.readObject(); // Extracts members
			in.close();
		} catch (java.io.FileNotFoundException pp) {
			// If not found, new file is created
			new File("TSHSLibrarydata.ser");
			System.out.println("creating new file");
			books = new ArrayList<Book>(); // Initializes new arrayLists
			members = new ArrayList<Member>();
		}
		System.out.println("file extraction complete");

		// Runs when program closes (shutdown hook)
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				// Saves data when program is closed
				try {
					ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("TSHSLibrarydata.ser"));
					out.writeObject(books); // Writes book information
					out.writeObject(members); // Writes member information
					out.close();
					System.out.println("Program saved and terminated");
				} catch (IOException i) {
					i.printStackTrace();
				}

			}
		}, "Shutdown-thread"));

		// Initialize the program
		Thread.sleep(1000);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TSHSLibrary window = new TSHSLibrary(); // Creates new instance of TSHS Library
					window.frmTshsLibrary.setVisible(true); // Makes window form visible
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * The constructor creates the application.
	 **/
	public TSHSLibrary() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 **/
	@SuppressWarnings("serial")
	private void initialize() {

		frmTshsLibrary = new JFrame(); // Initialize JFrame
		frmTshsLibrary.setTitle("TSHS Library");
		frmTshsLibrary.setBounds(100, 100, 1400, 900);
		frmTshsLibrary.setMinimumSize(new Dimension(1300, 800)); // Window min limit
		frmTshsLibrary.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTshsLibrary.getContentPane().setLayout(new BorderLayout(0, 0));

		lblTitle = new JLabel("Tesla STEM High School Library Database"); // Label at the top of the frame
		lblTitle.setBackground(new Color(152, 251, 152));
		lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmTshsLibrary.getContentPane().add(lblTitle, BorderLayout.NORTH);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP); // Manages all the tabs
		frmTshsLibrary.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		scrollPaneMembers = new JScrollPane(); // Member Table Scrollpane

		comboBoxDewey1 = new JComboBox<String>();
		comboBoxDewey2 = new JComboBox<String>();
		comboBoxDewey2.setMaximumRowCount(100);
		comboBoxDewey1.addActionListener(new ActionListener() {
			// After the first comboBox for dewey decimal sorting is selected,
			// the second comboBox is produced with specific call numbers
			// accurate to the decimal point.
			public void actionPerformed(ActionEvent arg0) {
				initiateDeweySelection();
			}
		});
		comboBoxDewey1.setToolTipText("Select Category");
		comboBoxDewey1.setModel(new DefaultComboBoxModel<String>(
				new String[] { "-Select-", "000 Generalities", "100 Philosophy & Psychology", "200 Religion",
						"300 Social Sciences", "400 Language", "500 Natural Sciences & Math", "600 Technology",
						"700 The Arts", "800 Literature & Rhetoric", "900 Geography & History" }));
		// TAB: BOOKS

		panelBooks = new JPanel(); // Book panel settings
		panelBooks.setBackground(new Color(240, 255, 240));
		tabbedPane.addTab("Books", null, panelBooks, null);
		panelBooks.setLayout(new BorderLayout(0, 0));

		lblBookPanelTitle = new JLabel("View and Manage Inventory");
		lblBookPanelTitle.setBackground(SystemColor.window);
		lblBookPanelTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelBooks.add(lblBookPanelTitle, BorderLayout.NORTH);

		panelBookInfo = new JPanel();
		panelBookInfo.setBackground(SystemColor.menu);
		panelBooks.add(panelBookInfo, BorderLayout.WEST);

		// View Pane

		lblBookViewTitle = new JLabel("Select Book");

		lblBookViewAuthor = new JLabel("Author: ");
		lblBookViewAuthor.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblBookViewAuthor.setVisible(false);

		lblBookViewBooktitle = new JLabel("Book Title: ");
		lblBookViewBooktitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblBookViewBooktitle.setVisible(false);

		lblBookViewID = new JLabel("ID: ");
		lblBookViewID.setVisible(false);

		lblBookViewTimesCheckedOut = new JLabel("Total Times Checked Out:");
		lblBookViewTimesCheckedOut.setVisible(false);
		panelBookInfo.setLayout(new MigLayout("", "[185px][]",
				"[20px][][20px][20px][20px][20px][][][][][20px][][][20px][20px][20px][26px][29px][][][][][][][][][][][][][][][][][][][][][][][][][][][bottom][bottom]"));

		lblBookViewDayAdded = new JLabel("Day Added: ");
		lblBookViewDayAdded.setVisible(false);

		lblBookViewISBN = new JLabel("ISBN: ");
		lblBookViewISBN.setVisible(false);

		lblBookViewNotes = new JLabel("Notes");
		lblBookViewNotes.setVisible(false);
		panelBookInfo.add(lblBookViewNotes, "cell 0 8,alignx left,aligny top");
		panelBookInfo.add(lblBookViewTitle, "cell 0 0,alignx left,aligny top");
		panelBookInfo.add(lblBookViewID, "cell 0 4,alignx left,aligny top");
		panelBookInfo.add(lblBookViewTimesCheckedOut, "cell 0 5,alignx left,aligny top");
		panelBookInfo.add(lblBookViewBooktitle, "cell 0 2,alignx left,aligny top");
		panelBookInfo.add(lblBookViewAuthor, "cell 0 3,alignx left,aligny top");
		panelBookInfo.add(lblBookViewISBN, "cell 0 7");
		panelBookInfo.add(lblBookViewDayAdded, "cell 0 6");
		// EDIT_BOOK
		btnBookEdit = new JButton("Edit Entry");
		btnBookEdit.setVisible(false);
		// Book tab edit button is pressed
		btnBookEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Locate book in database by ID
				long idSample = (long) tableBooks.getValueAt(tableBooks.getSelectedRow(), 2);
				Book sample = books.get(Collections.binarySearch(books, new Book(idSample)));
				if (sample.status().equals("Available")) { // If book is not checked out
					// Sets book creation fields to be editable (make it hold the relevant information)
					System.out.println("book edit: " + sample);
					textFieldBookTitle.setText(sample.getTitle());
					textFieldAuthorFirstName.setText(sample.getAuthorFirstName());
					textFieldAuthorMiddleName.setText(sample.getAuthorMiddleName());
					textFieldAuthorLastName.setText(sample.getAuthorLastName());

					lblAddABook.setText("Edit a book");
					spinnerCopies.setVisible(false); // Copies are not visible when making edits
					lblCopies.setVisible(false);

					int secondDewey = (int) (sample.getId() / 10000000 - 1000); // Determines Dewey from ID
					int firstDewey = secondDewey / 100;
					System.out.println(secondDewey + " " + firstDewey);
					comboBoxDewey1.setSelectedIndex(firstDewey + 1);
					initiateDeweySelection();
					for (int i = 0; i < comboBoxDewey2.getItemCount(); i++) {
						if (comboBoxDewey2.getItemAt(i).substring(0, 3).equals(String.valueOf(secondDewey))) {
							comboBoxDewey2.setSelectedIndex(i);
							break;
						}
					}
					comboBoxBookType.setSelectedItem(sample.getBookType());
					if (sample.getISBN() != 0) {
						textFieldBookISBN.setText(String.valueOf(sample.getISBN()));
					}
					textAreaBookNotes.setText(sample.getNotes());

					btnCreateBook.setText("Change");
				} else { // Book is already checked out, cannot edit information
					JOptionPane.showMessageDialog(null, "Cannot edit while book is checked out");
				}
			}
		});

		textPaneBookViewNotes = new JTextPane();
		textPaneBookViewNotes.setEditable(false);
		textPaneBookViewNotes.setVisible(false);
		panelBookInfo.add(textPaneBookViewNotes, "cell 0 9,alignx left,aligny top");
		panelBookInfo.add(btnBookEdit, "cell 0 17,alignx left,aligny top");

		// DELETE_BOOK
		btnBookDelete = new JButton("Remove Book");
		btnBookDelete.setVisible(false);
		// Book tab delete button is pressed
		btnBookDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (JOptionPane.showConfirmDialog(null, "Remove Book?") == 0) {
					long idSample = (long) tableBooks.getValueAt(tableBooks.getSelectedRow(), 2);
					Book c = books.get(Collections.binarySearch(books, new Book(idSample))); // Locate book in database by ID
					if (c.status().equals("Available")) {
						books.remove(c);
						System.out.println("Book removed...");
						refreshBooks(); // Resets screen
					} else { // Book is already checked out, cannot delete
						JOptionPane.showMessageDialog(null, "Cannot remove if book is checked out");
					}

				}
			}
		});
		panelBookInfo.add(btnBookDelete, "cell 0 18");

		// Book Creation
		panelAddBook = new JPanel(); // Holds fields for book creation
		panelAddBook.setBackground(new Color(240, 255, 240));
		panelBooks.add(panelAddBook, BorderLayout.EAST);

		// Title
		lblBookTitle = new JLabel("Title *"); // Required
		textFieldBookTitle = new JTextField();
		textFieldBookTitle.setColumns(10);

		// First Name
		lblAuthorFirstName = new JLabel("Author First Name");
		textFieldAuthorFirstName = new JTextField();
		textFieldAuthorFirstName.setColumns(10);

		// Middle Name
		lblAuthorMiddleName = new JLabel("Author Middle Name");
		textFieldAuthorMiddleName = new JTextField();
		textFieldAuthorMiddleName.setColumns(10);

		// Last Name
		lblAuthorLastName = new JLabel("Author Last Name *"); // Required
		textFieldAuthorLastName = new JTextField();
		textFieldAuthorLastName.setColumns(10);

		// Dewey
		lblDewey = new JLabel("Dewey");

		// Notes
		lblBookNotes = new JLabel("Notes");
		textAreaBookNotes = new JTextArea();
		textAreaBookNotes.setLineWrap(true);

		// Book/Reference/Periodical/Video
		lblBookType = new JLabel("Type");
		comboBoxBookType = new JComboBox<String>();
		comboBoxBookType.setModel(
				new DefaultComboBoxModel<String>(new String[] { "Book", "Reference", "Periodical", "DVD/Video" }));

		// ISBN
		lblBookISBN = new JLabel("ISBN");
		textFieldBookISBN = new JTextField();
		textFieldBookISBN.setColumns(10);

		// CREATE_BOOK
		btnCreateBook = new JButton("Create");
		// When the create button is created, a new book is added to the database
		btnCreateBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Makes sure required fields are complete
				if (textFieldAuthorLastName.getText() == null || textFieldAuthorLastName.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please Specify Author Last Name");
					return; // If last name not provided
				}
				if (textFieldBookTitle.getText() == null || textFieldBookTitle.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please Specify Title");
					return; // If title not provided
				}
				if (textFieldBookISBN.getText().length() > 0) {
					// Checks for valid ISBN
					try {
						Long.parseLong(textFieldBookISBN.getText());
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Invalid characters in ISBN");
						return;
					}
					if (textFieldBookISBN.getText().length() != 13) {
						JOptionPane.showMessageDialog(null, "ISBN should be 13 numbers");
						return;
					}
				}
				if (btnCreateBook.getText().equals("Create")) {
					for (int i = 0; i < (int) spinnerCopies.getValue(); i++) {
						// Adds a new book
						books.add(new Book(textFieldBookTitle.getText(), textFieldAuthorFirstName.getText(),
								textFieldAuthorMiddleName.getText(), textFieldAuthorLastName.getText(),
								String.valueOf(comboBoxBookType.getSelectedItem()),
								String.valueOf(comboBoxDewey2.getSelectedItem()), books, textFieldBookISBN.getText(),
								textAreaBookNotes.getText()));
						System.out.println("---------- book added");
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else { // If button text says "edit" - means it is in editing mode
					if (JOptionPane.showConfirmDialog(null, "Change book?") == 0) {
						// Edits books - first deletes old book
						long idSample = (long) tableBooks.getValueAt(tableBooks.getSelectedRow(), 2);
						Book c = books.get(Collections.binarySearch(books, new Book(idSample)));
						books.remove(c);
						System.out.println("Book removed...");
						Book b = new Book(textFieldBookTitle.getText(), textFieldAuthorFirstName.getText(),
								textFieldAuthorMiddleName.getText(), textFieldAuthorLastName.getText(),
								String.valueOf(comboBoxBookType.getSelectedItem()),
								String.valueOf(comboBoxDewey2.getSelectedItem()), books, textFieldBookISBN.getText(),
								textAreaBookNotes.getText(), idSample);
						books.add(b); // Adds the new book
						System.out.println("...and replaced");
					}
				}
				refreshBooks(); // Refreshes the book table
				clearBookFields(); // Clears the fields
			}
		});

		// Add field components
		panelAddBook.setLayout(new MigLayout("", "[77.00px][]",
				"[][22px][20px][26px][20px][26px][20px][26px][20px][26px][20px][26px][26px][20px][26px][20px][26px][20px][116px][29px][20px][][][][][][][][][][][][][][][][][]"));

		lblAddABook = new JLabel("Add a Book to Library");
		lblAddABook.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblAddABook.setHorizontalAlignment(SwingConstants.CENTER);
		panelAddBook.add(lblAddABook, "cell 0 0,growx,aligny top");
		panelAddBook.add(textFieldAuthorFirstName, "cell 0 5,alignx left,aligny top");
		panelAddBook.add(lblAuthorFirstName, "cell 0 4,alignx left,aligny top");
		panelAddBook.add(lblAuthorMiddleName, "cell 0 6,alignx left,aligny top");
		panelAddBook.add(lblAuthorLastName, "cell 0 8,alignx left,aligny top");
		panelAddBook.add(textFieldAuthorLastName, "cell 0 9,alignx left,aligny top");
		panelAddBook.add(lblDewey, "cell 0 10,alignx left,aligny top");
		panelAddBook.add(lblBookTitle, "cell 0 2,alignx left,aligny top");
		panelAddBook.add(textFieldAuthorMiddleName, "cell 0 7,alignx left,aligny top");
		panelAddBook.add(textFieldBookTitle, "cell 0 3,alignx left,aligny top");
		panelAddBook.add(comboBoxDewey1, "cell 0 11,alignx left,aligny top");
		panelAddBook.add(comboBoxDewey2, "cell 0 12,alignx left,aligny top");
		panelAddBook.add(lblBookType, "cell 0 13,alignx left,aligny top");
		panelAddBook.add(comboBoxBookType, "cell 0 14,alignx left,aligny top");
		panelAddBook.add(lblBookISBN, "cell 0 15,alignx left,aligny top");
		panelAddBook.add(textFieldBookISBN, "cell 0 16,alignx left,aligny top");
		panelAddBook.add(lblBookNotes, "cell 0 17,alignx left,aligny top");
		panelAddBook.add(textAreaBookNotes, "cell 0 18,grow");

		// Copies of book
		lblCopies = new JLabel("Copies");
		panelAddBook.add(lblCopies, "flowx,cell 0 20");
		spinnerCopies = new JSpinner();
		spinnerCopies.setModel(new SpinnerNumberModel(1, 1, 50, 1));
		panelAddBook.add(spinnerCopies, "cell 0 21");
		panelAddBook.add(btnCreateBook, "flowx,cell 0 35,alignx left,aligny top");

		btnCancelBook = new JButton("Cancel");
		// Clears all fields, resets book tab
		btnCancelBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearBookFields();
			}
		});
		panelAddBook.add(btnCancelBook, "cell 0 35");

		lblBookRequired = new JLabel("*required");
		panelAddBook.add(lblBookRequired, "cell 0 36");

		// Footer
		panelBookFooter = new JPanel();
		panelBookFooter.setBackground(new Color(240, 255, 240));
		panelBooks.add(panelBookFooter, BorderLayout.SOUTH);
		panelBookFooter.setLayout(new BorderLayout(0, 0));

		lblpanelBooksFooter = new JLabel("Book Count: " + books.size());
		panelBookFooter.add(lblpanelBooksFooter, BorderLayout.WEST);

		// Panel holding the book table
		panelBookTable = new JPanel();
		panelBooks.add(panelBookTable, BorderLayout.CENTER);
		panelBookTable.setLayout(new BorderLayout(0, 0));

		panelBookSearchSort = new JPanel();
		panelBookTable.add(panelBookSearchSort, BorderLayout.NORTH);

		lblBookTableHeader = new JLabel("Inventory");
		panelBookSearchSort.add(lblBookTableHeader);

		scrollPaneBooks = new JScrollPane();
		panelBookTable.add(scrollPaneBooks, BorderLayout.CENTER);

		// dtm manages tableBooks
		dtmBooks = new DefaultTableModel(new String[] { "Title", "Author", "ID", "Status", "Category", "Popularity" },
				0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// TABLE_BOOKS
		tableBooks = new JTable();
		tableBooks.setModel(dtmBooks);
		tableBooks.setAutoCreateRowSorter(true);
		tableBooks.getSelectionModel().addListSelectionListener(tableBookSelectionChanged);
		tableBooks.getTableHeader().setReorderingAllowed(false);
		scrollPaneBooks.setViewportView(tableBooks);
		refreshBooks();

		// TAB: MEMBERS

		panelMembers = new JPanel(); // Members tab
		panelMembers.setBackground(new Color(240, 255, 240));
		tabbedPane.addTab("Members", null, panelMembers, null);
		panelMembers.setLayout(new BorderLayout(0, 0));

		panelMemberActions = new JPanel();
		panelMembers.add(panelMemberActions, BorderLayout.CENTER);
		panelMemberActions.setLayout(new BorderLayout(0, 0));

		panelMemberCheckout = new JPanel();
		panelMemberActions.add(panelMemberCheckout, BorderLayout.CENTER);
		panelMemberCheckout.setLayout(new BorderLayout(0, 0));

		lblTableCheckout = new JLabel("Books Checked Out");
		panelMemberCheckout.add(lblTableCheckout, BorderLayout.NORTH);
		lblTableCheckout.setHorizontalAlignment(SwingConstants.CENTER);

		panelCheckOutBook = new JPanel();
		panelMemberCheckout.add(panelCheckOutBook, BorderLayout.SOUTH);
		panelCheckOutBook.setLayout(new MigLayout("", "[][]", "[]"));

		// CHECK_OUT_BOOK, and RETURN
		btnCheckOutBook = new JButton("Check Out Book by ID:");
		btnCheckOutBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnCheckOutBook.getText().equals("Check Out Book by ID:")) {
					// Check out
					try {
						Book b = books.get(Collections.binarySearch(books,
								new Book(Long.parseLong(textFieldCheckOutID.getText())))); // Locate book in database by ID
						if (b.status().equals("Available")) {
							if (selectedMember.getBookCount() < selectedMember.getBookLimit()) { // Make sure checkout limit is not exceeded
								selectedMember.checkOut(b); // Checkout book
								refreshMemberBooks(selectedMember, false); // Add book to memberBooks table
							} else {
								JOptionPane.showMessageDialog(null,
										"Exceeded Maximum of " + selectedMember.getBookLimit() + " books");
							}
						} else {
							// If book is currently checked out
							JOptionPane.showMessageDialog(null, "Book Unavailable");
						}
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Please provide a valid book ID");
						return;
					}
					System.out.println("Book Checked Out");
				} else {
					// Make return
					try {
						Book b = books.get(Collections.binarySearch(books,
								new Book(Long.parseLong(textFieldCheckOutID.getText())))); // Locate book in database by ID
						if (selectedMember.books().contains(b)) { // Confirm book checked out
							selectedMember.makeReturn(b);
						} else {
							// If book is not checked out, cannot return the book
							JOptionPane.showMessageDialog(null, "Book is not currently checked out");
						}
					} catch (Exception e1) { // If ID can't be found
						JOptionPane.showMessageDialog(null, "Please provide a valid book ID");
						return;
					}
					System.out.println("Book Returned");
					refreshMemberBooks(selectedMember, false); // Update MemberBooks table
				}
				textFieldCheckOutID.setText(""); // Clear checkout field
			}
		});
		panelCheckOutBook.add(btnCheckOutBook, "cell 0 0");

		textFieldCheckOutID = new JTextField();
		panelCheckOutBook.add(textFieldCheckOutID, "cell 1 0,growx");
		textFieldCheckOutID.setColumns(10);

		scrollPaneMemberBooks = new JScrollPane();
		panelMemberCheckout.add(scrollPaneMemberBooks, BorderLayout.CENTER);

		// dtm memberBooks manages tableMemberBooks
		dtmMemberBooks = new DefaultTableModel(
				new String[] { "Title", "Author", "ID", "Checkout Date", "Due Date", "Overdue" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// TABLE_MEMBER_BOOKS
		tableMemberBooks = new JTable();
		tableMemberBooks.setModel(dtmMemberBooks);
		tableMemberBooks.setAutoCreateRowSorter(true);
		tableMemberBooks.getSelectionModel().addListSelectionListener(tableMemberBooksSelectionChanged);
		tableMemberBooks.getTableHeader().setReorderingAllowed(false);
		scrollPaneMemberBooks.setViewportView(tableMemberBooks);

		// Fields for adding a new member
		panelAddMember = new JPanel(); // Panel holding member creation fields
		panelMemberActions.add(panelAddMember, BorderLayout.NORTH);
		panelAddMember.setBackground(new Color(240, 255, 240));
		panelAddMember.setLayout(new MigLayout("", "[77.00px][9px]", "[][][][][][][][][][85.00,grow][][]"));

		lblRegisterNewMember = new JLabel("Register New Member");
		panelAddMember.add(lblRegisterNewMember, "cell 0 0");

		// First Name
		lblMemberFirstName = new JLabel("First Name");
		panelAddMember.add(lblMemberFirstName, "cell 0 1");
		textFieldMemberFirstName = new JTextField();
		panelAddMember.add(textFieldMemberFirstName, "cell 0 2,growx,aligny center");
		textFieldMemberFirstName.setColumns(10);

		// Middle Name
		lblMemberMiddleName = new JLabel("Middle Name");
		panelAddMember.add(lblMemberMiddleName, "cell 0 3");
		textFieldMemberMiddleName = new JTextField();
		panelAddMember.add(textFieldMemberMiddleName, "cell 0 4,growx,aligny center");
		textFieldMemberMiddleName.setColumns(10);

		// Last Name
		lblMemberLastName = new JLabel("Last Name *");
		panelAddMember.add(lblMemberLastName, "cell 0 5");
		textFieldMemberLastName = new JTextField();
		panelAddMember.add(textFieldMemberLastName, "cell 0 6,growx");
		textFieldMemberLastName.setColumns(10);

		// Teacher/Student
		comboBoxMemberType = new JComboBox<String>();
		comboBoxMemberType.setModel(new DefaultComboBoxModel<String>(new String[] { "Student", "Staff" }));
		panelAddMember.add(comboBoxMemberType, "cell 0 7,growx");

		// Notes
		lblMemberNotes = new JLabel("Notes");
		panelAddMember.add(lblMemberNotes, "cell 0 8");
		textAreaMemberNotes = new JTextArea();
		textAreaMemberNotes.setLineWrap(true);
		textAreaMemberNotes.setRows(8);
		panelAddMember.add(textAreaMemberNotes, "cell 0 9,grow");

		// CREATE_MEMBER
		btnCreateMember = new JButton("Create");
		// When create button clicked, adds a new member to the database
		btnCreateMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Makes sure required fields are complete
				if (textFieldMemberLastName.getText() == null || textFieldMemberLastName.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please Specify Last Name");
					return;
				}
				// Determines whether or not member is a teacher
				boolean isTeacher = false;
				if (comboBoxMemberType.getSelectedItem().equals("Staff")) {
					isTeacher = true;
				}
				if (btnCreateMember.getText().equals("Create")) { // Create Mode
					// Creates members
					Member m = new Member(textFieldMemberFirstName.getText(), textFieldMemberMiddleName.getText(),
							textFieldMemberLastName.getText(), isTeacher, textAreaMemberNotes.getText()); // Locates member in database by date added
					members.add(m);
					System.out.println("--------- member added");
				} else { // Edit Mode
					if (JOptionPane.showConfirmDialog(null, "Change Member?") == 0) {
						// Edits members - first deletes old member
						Date d = (Date) tableMembers.getValueAt(tableMembers.getSelectedRow(), 5);
						Member c = members.get(Collections.binarySearch(members, new Member(d)));
						members.remove(c); // Removes old member
						Member m = new Member(textFieldMemberFirstName.getText(), textFieldMemberMiddleName.getText(),
								textFieldMemberLastName.getText(), isTeacher, textAreaMemberNotes.getText(), d,
								selectedMember.books());
						members.add(m); // Adds new member
					}
				}
				refreshMembers();
				clearMemberFields();
			}
		});
		panelAddMember.add(btnCreateMember, "flowx,cell 0 10");

		btnCancelMember = new JButton("Cancel");
		// Clear Member creation fields
		btnCancelMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearMemberFields();
			}
		});
		panelAddMember.add(btnCancelMember, "cell 0 10");

		lblMemberRequired = new Label("*required");
		panelAddMember.add(lblMemberRequired, "cell 0 11");

		lblMemberpaneltitle = new JLabel("Track Library Users");
		panelMembers.add(lblMemberpaneltitle, BorderLayout.NORTH);

		// Footer
		panelMemberFooter = new JPanel();
		panelMemberFooter.setBackground(new Color(240, 255, 240));
		panelMembers.add(panelMemberFooter, BorderLayout.SOUTH);
		panelMemberFooter.setLayout(new BorderLayout(0, 0));

		lblpanelMembersFooter = new JLabel("Member Count: " + members.size());
		panelMemberFooter.add(lblpanelMembersFooter, BorderLayout.WEST);

		btnMemberReport = new JButton("Generate Report");
		// Sets up information on the report page - for member specific reports
		btnMemberReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// MEMBER REPORT (ONE MEMBER)
				tabbedPane.setSelectedIndex(2);
				rdbtnReportSelectedMember.setEnabled(true);
				rdbtnReportSelectedMember.setSelected(true);
				lblReportName.setText(selectedMember.getName() + " (" + selectedMember.getStatus() + ")");
				lblReportName.setEnabled(true);
			}
		});
		panelMemberFooter.add(btnMemberReport, BorderLayout.EAST);
		btnMemberReport.setEnabled(false);

		// Panel containing tableMembers
		panelMemberTable = new JPanel();
		panelMembers.add(panelMemberTable, BorderLayout.WEST);
		panelMemberTable.setLayout(new BorderLayout(0, 0));

		panelMemberSearchSort = new JPanel();
		panelMemberTable.add(panelMemberSearchSort, BorderLayout.NORTH);
		panelMemberSearchSort.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		lblMemberTableHeader = new JLabel("Library Users");
		panelMemberSearchSort.add(lblMemberTableHeader);

		panelMemberTable.add(scrollPaneMembers);

		// suppressed warnings
		// TABLE_MEMBERS
		dtmMembers = new DefaultTableModel(
				new String[] { "Last Name", "First Name", "Middle Initial", "Status", "Books Checked Out", "Date" },
				0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableMembers = new JTable(); // initializes tableMembers
		tableMembers.setModel(dtmMembers);
		tableMembers.setAutoCreateRowSorter(true);
		tableMembers.getTableHeader().setReorderingAllowed(false);
		tableMembers.getSelectionModel().addListSelectionListener(tableMembersSelectionChanged);
		tableMembers.getColumnModel().getColumn(0).setPreferredWidth(231);
		tableMembers.getColumnModel().getColumn(1).setPreferredWidth(138);
		tableMembers.getColumnModel().getColumn(2).setPreferredWidth(150);
		tableMembers.getColumnModel().getColumn(3).setPreferredWidth(93);
		tableMembers.getColumnModel().getColumn(4).setPreferredWidth(220);
		tableMembers.getColumnModel().getColumn(5).setMinWidth(0);
		tableMembers.getColumnModel().getColumn(5).setMaxWidth(0);
		scrollPaneMembers.setViewportView(tableMembers);
		refreshMembers();

		// Members ViewPane
		panelMemberInfo = new JPanel();
		panelMemberTable.add(panelMemberInfo, BorderLayout.SOUTH);
		panelMemberInfo.setLayout(new MigLayout("", "[103px,grow]", "[20px][][][][][][][][][][grow]"));

		lblMemberViewTitle = new JLabel("Select Member");
		panelMemberInfo.add(lblMemberViewTitle, "cell 0 0,alignx left,aligny top");

		lblMemberViewName = new JLabel("Name:");
		panelMemberInfo.add(lblMemberViewName, "cell 0 2");
		lblMemberViewName.setVisible(false);

		lblMemberViewStatus = new JLabel("Type:");
		panelMemberInfo.add(lblMemberViewStatus, "cell 0 3");
		lblMemberViewStatus.setVisible(false);

		lblMemberViewDateAdded = new JLabel("Date Added:");
		panelMemberInfo.add(lblMemberViewDateAdded, "cell 0 4");
		lblMemberViewDateAdded.setVisible(false);

		lblMemberViewNotes = new JLabel("Notes");
		panelMemberInfo.add(lblMemberViewNotes, "cell 0 5");
		lblMemberViewNotes.setVisible(false);

		textPaneMemberViewNotes = new JTextPane();
		panelMemberInfo.add(textPaneMemberViewNotes, "cell 0 6,alignx left");
		textPaneMemberViewNotes.setVisible(false);

		// EDIT_MEMBER
		btnMemberEdit = new JButton("Edit Entry");
		btnMemberEdit.setVisible(false);
		// Sets up fields for editing
		btnMemberEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("EDIT MEMBER");
				Date d = (Date) tableMembers.getValueAt(tableMembers.getSelectedRow(), 5);
				Member sample = members.get(Collections.binarySearch(members, new Member(d))); // Locate member in database by date added
				if (sample.getBookCount() == 0) {
					lblRegisterNewMember.setText("Edit Member Information");
					textFieldMemberFirstName.setText(sample.getFirstName());
					textFieldMemberMiddleName.setText(sample.getMiddleName());
					textFieldMemberLastName.setText(sample.getLastName());
					comboBoxMemberType.setSelectedItem(sample.getStatus());
					textAreaBookNotes.setText(sample.getNotes());
					btnCreateMember.setText("Change");
				} else { // Member has books checked out, cannot edit information
					JOptionPane.showMessageDialog(null, "Cannot edit if member has unreturned books");
				}

			}
		});
		panelMemberInfo.add(btnMemberEdit, "cell 0 8");

		// DELETE_MEMBER
		btnMemberDelete = new JButton("Remove Member");
		btnMemberDelete.setVisible(false);
		// Removes a member
		btnMemberDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Confirmation message
				if (JOptionPane.showConfirmDialog(null, "Remove Member?") == 0) {
					Date d = (Date) tableMembers.getValueAt(tableMembers.getSelectedRow(), 5);
					Member sample = members.get(Collections.binarySearch(members, new Member(d)));
					if (sample.getBookCount() == 0) {
						members.remove(sample); // Removes member
						System.out.println("Member removed...");
						refreshMembers(); // Updates member table
					} else { // Member has books checked out, cannot delete
						JOptionPane.showMessageDialog(null, "Cannot remove if member has unreturned books");
					}
				}
			}
		});
		panelMemberInfo.add(btnMemberDelete, "cell 0 9");

		// TAB: REPORTS

		panelReports = new JPanel();
		tabbedPane.addTab("Report", null, panelReports, null);
		panelReports.setLayout(new BorderLayout(0, 0));

		lblReportHeader = new JLabel("Generate Report");
		lblReportHeader.setBackground(new Color(240, 255, 240));
		panelReports.add(lblReportHeader, BorderLayout.NORTH);

		panelReportInfo = new JPanel(); // Report settings
		panelReports.add(panelReportInfo, BorderLayout.WEST);
		panelReportInfo
				.setLayout(new MigLayout("", "[297.00px,grow][112.00]", "[20px][][][][][][][][][][][][][][][][]"));

		lblReportInfo = new JLabel("Report Info");
		panelReportInfo.add(lblReportInfo, "cell 0 0,alignx left,aligny top");

		rdbtnReportIssuance = new JRadioButton("Book Issuance");
		panelReportInfo.add(rdbtnReportIssuance, "cell 0 2");

		rdbtnReportFines = new JRadioButton("Fines");
		panelReportInfo.add(rdbtnReportFines, "cell 0 3");

		btnGroupReportType = new ButtonGroup();
		btnGroupReportType.add(rdbtnReportIssuance);
		btnGroupReportType.add(rdbtnReportFines);

		rdbtnReportSelectedMember = new JRadioButton("Selected Member");
		rdbtnReportSelectedMember.setEnabled(false);
		panelReportInfo.add(rdbtnReportSelectedMember, "cell 0 5");

		lblReportName = new JLabel("Generate report from Members tab");
		lblReportName.setEnabled(false);
		panelReportInfo.add(lblReportName, "cell 0 6");

		rdbtnAllMembers = new JRadioButton("All Members");
		rdbtnAllMembers.setSelected(true);
		rdbtnAllMembers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// empty
			}
		});
		panelReportInfo.add(rdbtnAllMembers, "cell 0 7");

		btnGroupMemberType = new ButtonGroup();
		btnGroupMemberType.add(rdbtnReportSelectedMember);
		btnGroupMemberType.add(rdbtnAllMembers);

		rdbtnReportWeekly = new JRadioButton("Weekly");
		rdbtnReportWeekly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// empty
			}
		});
		rdbtnReportWeekly.setSelected(true);
		panelReportInfo.add(rdbtnReportWeekly, "cell 0 9");

		rdbtnReportComplete = new JRadioButton("Complete");
		rdbtnReportComplete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// empty
			}
		});

		btnGroupWeeklyType = new ButtonGroup();
		btnGroupWeeklyType.add(rdbtnReportWeekly);
		btnGroupWeeklyType.add(rdbtnReportComplete);

		btnGenerateReport = new JButton("Generate Report");
		// Method that generates report according to user settings (Radiobuttons)
		btnGenerateReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateReport();
			}
		});

		panelReportInfo.add(rdbtnReportComplete, "cell 0 10");
		panelReportInfo.add(btnGenerateReport, "cell 0 12");

		panelReportPreview = new JPanel(); // Preview the report
		panelReports.add(panelReportPreview, BorderLayout.CENTER);
		panelReportPreview.setLayout(new BorderLayout(0, 0));

		lblReportPreview = new JLabel("Preview");
		panelReportPreview.add(lblReportPreview, BorderLayout.NORTH);

		scrollPaneReportPreview = new JScrollPane();
		panelReportPreview.add(scrollPaneReportPreview, BorderLayout.CENTER);

		textPaneReportPreview = new JTextPane(); // Holds the report
		textPaneReportPreview.setEditable(false);
		scrollPaneReportPreview.setViewportView(textPaneReportPreview);

		panelReportCommand = new JPanel(); // Holds save/print buttons
		panelReportCommand.setBackground(new Color(240, 255, 240));
		panelReports.add(panelReportCommand, BorderLayout.SOUTH);
		panelReportCommand.setLayout(new MigLayout("", "[][][][][][][][][]", "[]"));

		btnReportSave = new JButton("Save");
		btnReportSave.setEnabled(false);
		// Opens window for user to save report
		btnReportSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// REPORT SAVED
				final JFileChooser fc = new JFileChooser(); // Java built in file selector
				int returnVal = fc.showSaveDialog(lblReportHeader);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						System.out.println("File written");
						FileWriter out = new FileWriter(fc.getSelectedFile() + ".txt");
						BufferedWriter bw = new BufferedWriter(out);
						String[] lines = report.split("\n");
						for (String line : lines) {
							System.out.println(line);
							bw.write(line);
							bw.newLine();
						}
						//						out.write(report);
						//						System.out.println(report); //test
						bw.close();
					} catch (IOException i) {
						// Error
						JOptionPane.showMessageDialog(null, "Could Not Write to File");
						i.printStackTrace();
					}
				}
			}
		});
		panelReportCommand.add(btnReportSave, "cell 0 0");

		btnReportPrint = new JButton("Print");
		btnReportPrint.setEnabled(false);
		// Opens window for user to print report
		btnReportPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// PRINT REPORT
				System.out.println("Printing report");
				try {
					textPaneReportPreview.print(null, null, true, null, null, true); // Java built in printer interface
				} catch (java.awt.print.PrinterException i) {
					// Error
					JOptionPane.showMessageDialog(null, "Could Not Print");
					System.out.println("Print failure");
					i.printStackTrace();
				}
			}
		});
		panelReportCommand.add(btnReportPrint, "cell 1 0");

		btnReportCancel = new JButton("Cancel");
		panelReportCommand.add(btnReportCancel, "cell 3 0");
		// Clear report information
		btnReportCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// CANCEL REPORT
				System.out.println("Report Cancelled");
				report.equals("Empty");

				rdbtnReportIssuance.setSelected(false);
				rdbtnReportFines.setSelected(false);

				rdbtnReportSelectedMember.setEnabled(false);
				rdbtnAllMembers.setSelected(true);
				lblReportName.setText("Generate report from Members tab");
				lblReportName.setEnabled(false);

				btnReportSave.setEnabled(false);
				btnReportPrint.setEnabled(false);
				textPaneReportPreview.setText(null);

			}
		});

		// TAB: Help

		panelHelp = new JPanel(); // Initialize panel
		tabbedPane.addTab("Help", null, panelHelp, null);
		panelHelp.setLayout(new MigLayout("", "[][]", "[][][][][][][][][][][][][]"));

		// This information is for the help panel.

		lblLibraryDatabaseHelp = new JLabel("Library Database Help");
		panelHelp.add(lblLibraryDatabaseHelp, "cell 0 0");

		// The following are buttons and labels for questions (buttons) and corresponding answers (labels)
		btnHelpAddBooks = new JButton("How do you add books?"); // Q
		btnHelpAddBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetHelpPane();
				lblHelpAddBooks.setVisible(true);
			}
		});
		panelHelp.add(btnHelpAddBooks, "cell 0 2,growx");

		lblHelpAddBooks = new JLabel(
				"To create a book, enter required information on the right of the book tab, and hit create. Multiple copies of the same title can be added simultaneously.");
		panelHelp.add(lblHelpAddBooks, "cell 1 2"); // A

		btnHelpChangeBooks = new JButton("How do you edit/delete books?"); // Q
		btnHelpChangeBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetHelpPane();
				lblHelpChangeBooks.setVisible(true);
			}
		});
		panelHelp.add(btnHelpChangeBooks, "cell 0 3,growx");

		lblHelpChangeBooks = new JLabel(
				"To edit/delete, first select a book from the table, and use the commands on the left sidebar. The edit button allows you to edit with the fields on the right of the screen.");
		panelHelp.add(lblHelpChangeBooks, "cell 1 3"); // A

		btnHelpDewey = new JButton("What is the dewey field for?"); // Q
		btnHelpDewey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetHelpPane();
				lblHelpDewey.setVisible(true);
			}
		});
		panelHelp.add(btnHelpDewey, "cell 0 4,growx");

		lblHelpDewey = new JLabel(
				"The Dewey Decimal System is used when generating the ID, allowing it to sort books generally by their content. It may already be available, but you can determine one for yourself.");
		panelHelp.add(lblHelpDewey, "cell 1 4"); // A

		btnHelpMembers = new JButton("How do you add/edit members?"); // Q
		btnHelpMembers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetHelpPane();
				lblHelpMembers.setVisible(true);
			}
		});
		panelHelp.add(btnHelpMembers, "cell 0 5,growx");

		lblHelpMembers = new JLabel(
				"Member management works the same way as books do, the edit and delete commands are below the table, while members can be added through the fields to the right.");
		panelHelp.add(lblHelpMembers, "cell 1 5"); // A

		btnHelpCheckout = new JButton("How do you check out a book?"); // Q
		btnHelpCheckout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetHelpPane();
				lblHelpCheckout.setVisible(true);
			}
		});
		panelHelp.add(btnHelpCheckout, "cell 0 6,growx");

		lblHelpCheckout = new JLabel(
				"Once a member is selected, enter an ID into the field below the member's book table. Selecting a book from the books tab can automatically input the ID into the checkout field.");
		panelHelp.add(lblHelpCheckout, "cell 1 6"); // A

		btnHelpReturn = new JButton("How do you return a book?"); // Q
		btnHelpReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetHelpPane();
				lblHelpReturn.setVisible(true);
			}
		});
		panelHelp.add(btnHelpReturn, "cell 0 7,growx");

		lblHelpReturn = new JLabel("Selecting a book from the member's book table will you to return that book.");
		panelHelp.add(lblHelpReturn, "cell 1 7"); // A

		btnHelpReport = new JButton("How do you make a report?"); // Q
		btnHelpReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetHelpPane();
				lblHelpReport.setVisible(true);
			}
		});
		panelHelp.add(btnHelpReport, "cell 0 8,growx");

		lblHelpReport = new JLabel(
				"On the report tab, you can select the type of report and create one for all members. Or, you can select an individual on the member tab and generate a report from there.");
		panelHelp.add(lblHelpReport, "cell 1 8"); // A

		btnHelpPrint = new JButton("How do you save/print reports?"); // Q
		btnHelpPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetHelpPane();
				lblHelpPrint.setVisible(true);
			}
		});
		panelHelp.add(btnHelpPrint, "cell 0 9,growx");

		lblHelpPrint = new JLabel(
				"After generating reports, options to save and print at the bottom of the screen will open up dialogs to save the report as a .txt file or send it to a printer.");
		panelHelp.add(lblHelpPrint, "cell 1 9"); // A

		btnHelpSettings = new JButton("Can you modify settings?"); // Q
		btnHelpSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetHelpPane();
				lblHelpSettings.setVisible(true);
			}
		});
		panelHelp.add(btnHelpSettings, "cell 0 10,growx");

		lblHelpSettings = new JLabel(
				"This version does not support it, but this advanced feature is coming in the next update!");
		panelHelp.add(lblHelpSettings, "cell 1 10"); // A

		// Starts with all the help tips invisible
		resetHelpPane();

		lblMaxwellWangFbla = new JLabel("Maxwell Wang FBLA (c) 2017-18"); // Bottom label
		panelHelp.add(lblMaxwellWangFbla, "cell 0 12");

		// TAB: Settings

		panelSettings = new JPanel();
		tabbedPane.addTab("Settings", null, panelSettings, null);
		panelSettings.setLayout(new MigLayout("", "[][146.00]", "[][][][][][][][][][][][][][][][][][][]"));

		lblSettings = new JLabel("Settings");
		panelSettings.add(lblSettings, "cell 0 0");

		lblCheckouts = new JLabel("Checkouts");
		panelSettings.add(lblCheckouts, "cell 0 2");

		lblMaxAllottedBooksS = new JLabel("Max allotted books per student");
		panelSettings.add(lblMaxAllottedBooksS, "cell 0 3,alignx leading");

		// panelSettings textFields are currently set as un-editable, with more time a future version would allow the library presets to be modified and saved

		formattedTextFieldBooksS = new JFormattedTextField();
		formattedTextFieldBooksS.setEditable(false);
		panelSettings.add(formattedTextFieldBooksS, "cell 1 3,growx");

		lblMaxAllottedBooksT = new JLabel("Max allotted books per teacher");
		panelSettings.add(lblMaxAllottedBooksT, "cell 0 4,alignx leading");

		formattedTextFieldBooksT = new JFormattedTextField();
		formattedTextFieldBooksT.setEditable(false);
		panelSettings.add(formattedTextFieldBooksT, "cell 1 4,growx");

		lblMaxTimeS = new JLabel("Maximum days allowed for students");
		panelSettings.add(lblMaxTimeS, "cell 0 5,alignx leading");

		formattedTextFieldDaysS = new JFormattedTextField();
		formattedTextFieldDaysS.setEditable(false);
		panelSettings.add(formattedTextFieldDaysS, "cell 1 5,growx");

		lblMaxTimeT = new JLabel("Maximum days allowed for teachers");
		panelSettings.add(lblMaxTimeT, "cell 0 6,alignx leading");

		formattedTextFieldDaysT = new JFormattedTextField();
		formattedTextFieldDaysT.setEditable(false);
		panelSettings.add(formattedTextFieldDaysT, "cell 1 6,growx");

		lblFines = new JLabel("Fines");
		panelSettings.add(lblFines, "cell 0 8");

		lblFineBook = new JLabel("Daily incurred fines for books");
		panelSettings.add(lblFineBook, "cell 0 9,alignx leading");

		formattedTextFieldBook = new JFormattedTextField();
		formattedTextFieldBook.setEditable(false);
		panelSettings.add(formattedTextFieldBook, "cell 1 9,growx");

		lblFinePeriodical = new JLabel("Daily incurred fines for periodicals");
		panelSettings.add(lblFinePeriodical, "cell 0 10,alignx leading");

		formattedTextFieldPeriodical = new JFormattedTextField();
		formattedTextFieldPeriodical.setEditable(false);
		panelSettings.add(formattedTextFieldPeriodical, "cell 1 10,growx");

		lblFineReference = new JLabel("Daily incurred fines for reference");
		panelSettings.add(lblFineReference, "cell 0 11,alignx leading");

		formattedTextFieldReference = new JFormattedTextField();
		formattedTextFieldReference.setEditable(false);
		panelSettings.add(formattedTextFieldReference, "cell 1 11,growx");

		lblFineVideo = new JLabel("Daily incurred fines for DVD/video");
		panelSettings.add(lblFineVideo, "cell 0 12,alignx leading");

		formattedTextFieldVideo = new JFormattedTextField();
		formattedTextFieldVideo.setEditable(false);
		panelSettings.add(formattedTextFieldVideo, "cell 1 12,growx");

		lblMaxFine = new JLabel("Maximum overdue fine");
		panelSettings.add(lblMaxFine, "cell 0 13,alignx leading");

		formattedTextFieldMaxFine = new JFormattedTextField();
		formattedTextFieldMaxFine.setEditable(false);
		panelSettings.add(formattedTextFieldMaxFine, "cell 1 13,growx");

		// Allows the settings tab to display library presets.
		setSettingsTab();

		btnSaveSettings = new JButton("Save Changes");
		btnSaveSettings.setEnabled(false);
		btnSaveSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// empty
			}
		});
		panelSettings.add(btnSaveSettings, "cell 0 16");

		btnResetSettings = new JButton("Clear");
		btnResetSettings.setEnabled(false);
		btnResetSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Gets rid of user modifications in this session
				setSettingsTab();
			}
		});
		panelSettings.add(btnResetSettings, "cell 0 17");
		frmTshsLibrary.getContentPane()
				.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { panelAddBook }));
	}

	// Generates a report based off of settings in the report tab
	private void generateReport() {
		boolean allMembers = false;
		if (rdbtnAllMembers.isSelected()) {
			allMembers = true;
		}
		report = ""; // Final report that is printed
		report += "Tesla STEM High School Library\n\n"; // Add header
		if (rdbtnReportIssuance.isSelected()) {
			// BOOK ISSUANCE
			boolean weekly = rdbtnReportWeekly.isSelected();

			if (allMembers) {
				System.out.println("Generating Issuance Report for all members");
				// Issuance for all the members
				report += "Book Issuance Summary\n";
				if (weekly) {
					report += "Weekly report (past 7 days)\n";
				}
				report += "Report Generated: " + Calendar.getInstance().getTime().toString() + "\n\n";
				ArrayList<Member> membersWithBooks = new ArrayList<Member>(); // Keeps track of all member who have checked out books
				int totalBooks = 0;
				for (Member m : members) { // For each member
					if (m.getBookCount() > 0) {
						// If report is weekly, only takes the members who have checked out books within a week
						if (weekly) {
							for (Book b : m.books()) {
								long change = Calendar.getInstance().getTime().getTime()
										- b.getCheckoutDate().getTime();
								if ((int) TimeUnit.DAYS.convert(change, TimeUnit.MILLISECONDS) <= 7) {
									totalBooks++;
									// If haven't added yet, adds now
									if (!membersWithBooks.contains(m)) {
										membersWithBooks.add(m);
									}
								}
							}
						} else {
							totalBooks += m.getBookCount();
							membersWithBooks.add(m);
						}
					}
				}
				report += membersWithBooks.size() + "/" + members.size() + " members with books checked out\n"; // How many members have books checked out
				report += totalBooks + "/" + books.size() + " books checked out\n\n"; // How many books are in stock
				// Prints issuance report for individual members that have books
				for (Member m : membersWithBooks) {
					report += issuanceReport(m, weekly);
				}
				report += divider + "\n";

			} else {
				System.out.println("Generating Issuance Report for selected member");
				// Issuance of selected member
				report += "Individual Book Issuance Report\n";
				report += "Report Generated " + Calendar.getInstance().getTime().toString() + "\n\n";
				report += issuanceReport(selectedMember, weekly);
			}

		} else if (rdbtnReportFines.isSelected()) {
			// FINES
			if (allMembers) {
				System.out.println("Generating Fine Report for all members");
				// Fines of all the members
				report += "All Fines Summary\n";
				report += "Report Generated " + Calendar.getInstance().getTime().toString() + "\n\n";
				report += divider + "\n";
				for (Member m : members) {
					report += fineReport(m);
				}
			} else {
				System.out.println("Generating Fine Report for selected member");
				// Fines for selected member
				report += "Individual Fines Report\n";
				report += "Report Generated " + Calendar.getInstance().getTime().toString() + "\n\n";
				report += fineReport(selectedMember);
			}

		} else {
			JOptionPane.showMessageDialog(null, "Please Specify Type of Report");
			return;
		}

		textPaneReportPreview.setText(report); // Put report in preview

		// Enable save and print buttons
		btnReportSave.setEnabled(true);
		btnReportPrint.setEnabled(true);
	}

	// Generates a Book-issuance report for a member (at the present time)
	// True for weekly report
	private String issuanceReport(Member m, boolean weekly) {
		StringBuilder sb = new StringBuilder();
		// Generate header
		sb.append(divider + "\n");
		sb.append("Issued to: \n" + m.getName() + "\n(" + m.getStatus() + ")\n");
		sb.append(dividerThin + "\n");

		// Specific issuance information about each book
		for (Book b : m.books()) {
			// Makes sure book was checked out this week
			if (weekly) {
				long change = Calendar.getInstance().getTime().getTime() - b.getCheckoutDate().getTime();
				if (!((int) TimeUnit.DAYS.convert(change, TimeUnit.MILLISECONDS) <= 7)) { // Within 7 days
					continue;
				}
			}
			// Add book information to report
			sb.append(b.getTitle() + "\n" + b.authorName() + "\n"); // Title and author name
			sb.append("ID: " + b.getId() + "\n"); // ID
			if (b.getISBN() != 0) {
				sb.append("ISBN: " + b.getISBN() + "\n"); // ISBN if present
			}
			sb.append("Type: " + b.getBookType() + "\n\n"); // Book/Periodical/Reference/Video
			sb.append("Checked out on: " + b.getCheckoutDate() + "\n"); // Date checked out
			sb.append("Is due: " + b.getDueDate() + "\n"); // Due date of book
			if (b.overdue()) { // Is overdue
				sb.append("Overdue for: " + (b.daysOverdue()) + " days\n"); // Days overdue
			} else { // Is not overdue
				sb.append("Return in: " + (-b.daysOverdue()) + " days\n"); // Days overdue
			}

			// Book break
			sb.append(dividerThin + "\n");
		}
		sb.append("\n");
		return sb.toString();
	}

	// Generates a Fine report for a member (at the present time)
	private String fineReport(Member m) {
		StringBuilder sb = new StringBuilder();
		// Generate header
		sb.append(m.getName() + "\n(" + m.getStatus() + ")\n");
		sb.append(m.books().size() + " book(s) checked out\n");
		ArrayList<Book> overdueBooks = new ArrayList<Book>(); // List of all the books that are overdue
		for (Book b : m.books()) {
			if (b.overdue()) {
				overdueBooks.add(b); // Creates overdueBooks
			}
		}
		sb.append(overdueBooks.size() + " book(s) overdue\n"); // Total books overdue
		if (overdueBooks.size() > 0) {
			sb.append(dividerThin + "\n");
		}
		double totalFines = 0;

		// Specific information about each overdue book added
		for (Book b : overdueBooks) {
			sb.append(b.getTitle() + "\n" + b.authorName() + "\n"); // Title and author name
			sb.append("ID: " + b.getId() + "\n"); // ID
			if (b.getISBN() != 0) {
				sb.append("ISBN: " + b.getISBN() + "\n"); // ISBN if present
			}
			sb.append("Type: " + b.getBookType() + "\n\n"); // Book/Periodical/Reference/Video
			sb.append("Checked out on: " + b.getCheckoutDate() + "\n"); // Date checked out
			sb.append("Was due: " + b.getDueDate() + "\n"); // Due date
			sb.append("Overdue for: " + b.daysOverdue() + " days\n"); // Days overdue for
			sb.append("Fine: " + formatter.format(b.getFines()) + "\n"); // Fines accumulated
			totalFines += b.getFines();
			// Book break
			sb.append(divider + "\n");
		}

		sb.append("Total fines: " + formatter.format(totalFines) + "\n\n");
		sb.append(divider + "\n");
		return sb.toString();
	}

	// Adds a new book to the book table.
	private void addBookRow(Book b) {
		dtmBooks.addRow(new Object[] { b.getTitle(), b.authorName(), b.getId(), b.status(), b.getBookType(),
				b.timesCheckedOut() });
	}

	// Recreates the book table / refreshes
	private void refreshBooks() {
		lblpanelBooksFooter = new JLabel("Book Count: " + books.size()); // Updates footer
		dtmBooks.setRowCount(0);
		Collections.sort(books);
		for (Book b : books) {
			addBookRow(b); // Adds one row for each book
		}
	}

	// Adds a new member to the table.
	private void addMemberRow(Member m) {
		dtmMembers.addRow(new Object[] { m.getLastName(), m.getFirstName(), m.getMiddleName(), m.getStatus(),
				m.getBookCount(), m.dayAdded() });
	}

	// Recreates the member table
	private void refreshMembers() {
		lblpanelMembersFooter = new JLabel("Member Count: " + members.size()); // Updates footer
		System.out.println("MemberTable Refreshed");
		dtmMembers.setRowCount(0);
		Collections.sort(members);
		for (Member m : members) {
			addMemberRow(m); // Adds one row for each member
		}
	}

	// Adds a new book to the member-book table (books that member has checked out)
	private void addMemberBookRow(Book b) {
		dtmMemberBooks.addRow(new Object[] { b.getTitle(), b.authorName(), b.getId(), b.getCheckoutDate(),
				b.getDueDate(), b.overdue() });
	}

	// Recreates the member-book table
	// If true, does not update the members table as well
	private void refreshMemberBooks(Member m, boolean r) {
		refreshBooks(); // Updates the book table
		if (!r) {
			refreshMembers(); // Updates the members table
		}
		dtmMemberBooks.setRowCount(0);
		btnCheckOutBook.setText("Check Out Book by ID:");
		lblTableCheckout.setText("Books Checked Out: " + m.getBookCount());
		for (Book b : m.books()) {
			addMemberBookRow(b); // Adds one row for each book the member has checked out
		}
	}

	// Clears all fields in the book creation tool-bar.
	private void clearBookFields() {
		textFieldBookTitle.setText("");
		textFieldAuthorFirstName.setText("");
		textFieldAuthorMiddleName.setText("");
		textFieldAuthorLastName.setText("");
		comboBoxDewey1.setSelectedItem("-Select-");
		comboBoxBookType.setSelectedItem("Book");
		textFieldBookISBN.setText("");
		textAreaBookNotes.setText("");
		spinnerCopies.setValue(1);
		spinnerCopies.setVisible(true); // Makes copies selection visible again (from edit mode)
		lblCopies.setVisible(true);
		lblAddABook.setText("Add a Book to Library");
		btnCreateBook.setText("Create");
		lblBookViewTitle.setText("Select Book");

		// Hides view pane information
		lblBookViewTitle.setVisible(false);
		lblBookViewBooktitle.setVisible(false);
		lblBookViewAuthor.setVisible(false);
		lblBookViewID.setVisible(false);
		lblBookViewTimesCheckedOut.setVisible(false);
		lblBookViewDayAdded.setVisible(false);
		lblBookViewISBN.setVisible(false);
		lblBookViewNotes.setVisible(false);
		textPaneBookViewNotes.setVisible(false);
		// Hides buttons
		btnBookEdit.setVisible(false);
		btnBookDelete.setVisible(false);
	}

	// Clears all fields in the member creation tool-bar.
	private void clearMemberFields() {
		textFieldMemberFirstName.setText("");
		textFieldMemberMiddleName.setText("");
		textFieldMemberLastName.setText("");
		textAreaMemberNotes.setText("");
		lblRegisterNewMember.setText("Register New Member");
		lblTableCheckout.setText("Books Checked Out");
		btnCreateMember.setText("Create");
		lblMemberViewTitle.setText("Select Member");

		// Hides view pane information
		lblMemberViewName.setVisible(false);
		lblMemberViewStatus.setVisible(false);
		lblMemberViewDateAdded.setVisible(false);
		lblMemberViewNotes.setVisible(false);
		textPaneMemberViewNotes.setVisible(false);
		// Hides buttons
		btnMemberEdit.setVisible(false);
		btnMemberDelete.setVisible(false);
	}

	// Allows the settings tab to display library presets.
	private void setSettingsTab() {
		formattedTextFieldBooksS.setText(String.valueOf(TSHSLibrary.studentBooksAllowed));
		formattedTextFieldDaysS.setText(String.valueOf(TSHSLibrary.studentDaysAllowed));
		formattedTextFieldBooksT.setText(String.valueOf(TSHSLibrary.teacherBooksAllowed));
		formattedTextFieldDaysT.setText(String.valueOf(TSHSLibrary.teacherDaysAllowed));
		formattedTextFieldBook.setText(formatter.format(TSHSLibrary.bookDailyFine));
		formattedTextFieldPeriodical.setText(formatter.format(TSHSLibrary.periodicalDailyFine));
		formattedTextFieldReference.setText(formatter.format(TSHSLibrary.referenceDailyFine));
		formattedTextFieldVideo.setText(formatter.format(TSHSLibrary.videoDailyFine));
		formattedTextFieldMaxFine.setText(formatter.format(TSHSLibrary.maxFine));
	}

	// Hides help pane answers
	private void resetHelpPane() {
		lblHelpAddBooks.setVisible(false);
		lblHelpAddBooks.setVisible(false);
		lblHelpChangeBooks.setVisible(false);
		lblHelpDewey.setVisible(false);
		lblHelpMembers.setVisible(false);
		lblHelpCheckout.setVisible(false);
		lblHelpReturn.setVisible(false);
		lblHelpReport.setVisible(false);
		lblHelpPrint.setVisible(false);
		lblHelpSettings.setVisible(false);
	}

	/* Allows creation of the second dewey ComboBox based off of information from the first deweyComboBox
	 * Using information from 
	 * https://www.library.illinois.edu/infosci/research/guides/dewey/
	 */
	private void initiateDeweySelection() {
		String s = String.valueOf(comboBoxDewey1.getSelectedItem());
		// Further sorting:
		switch (s) {
		case "000 Generalities":
			comboBoxDewey2.setModel(new DefaultComboBoxModel<String>(new String[] { "000 Generalities", "001 Knowledge",
					"002 The book", "003 Systems", "004 Data processing Computer science",
					"005 Computer programming, programs, data", "006 Special computer methods", "010 Bibliography",
					"011 Bibliographies", "012 Bibliographies of individuals",
					"013 Bibliographies of works by specific classes of authors",
					"014 Bibliographies of anonymous and pseudonymous works",
					"015 Bibliographies of works from specific places",
					"016 Bibliographies of works from specific subjects", "017 General subject catalogs",
					"018 Catalogs arranged by author & date", "019 Dictionary catalogs",
					"020 Library & information sciences", "021 Library relationships",
					"022 Administration of the physical plant", "023 Personnel administration",
					"025 Library operations", "026 Libraries for specific subjects", "027 General libraries",
					"028 R*requiredg, use of other information media", "030 General encyclopedic works",
					"031 General encyclopedic works — American", "032 General encyclopedic works in English",
					"033 General encyclopedic works in other Germanic languages",
					"034 General encyclopedic works in French, Provencal, Catalan",
					"035 General encyclopedic works in Italian, Romanian, Rhaeto-Romanic",
					"036 General encyclopedic works in Spanish & Portuguese",
					"037 General encyclopedic works in Slavic languages",
					"038 General encyclopedic works in Scandinavian languages",
					"039 General encyclopedic works in other languages", "050 General serials & their indexes",
					"051 General serials & their indexes American", "052 General serials & their indexes In English",
					"053 General serials & their indexes In other Germanic languages",
					"054 General serials & their indexes In French, Provencal, Catalan",
					"055 General serials & their indexes In Italian, Romanian, Rhaeto-Romanic",
					"056 General serials & their indexes In Spanish & Portuguese",
					"057 General serials & their indexes In Slavic languages",
					"058 General serials & their indexes In Scandinavian languages",
					"059 General serials & their indexes In other languages", "060 General organization & museology",
					"061 In North America", "062 In British Isles In England", "063 In central Europe In Germany",
					"064 In France & Monaco", "065 In Italy & adjacent territories",
					"066 In Iberian Peninsula & adjacent islands", "067 In eastern Europe In Soviet Union",
					"068 In other areas", "069 Museology (Museum science)", "070 News media, journalism, publishing",
					"071 In North America", "072 In England", "073 In central Europe In Germany",
					"074 In France & Monaco", "075 In Italy & adjacent territories",
					"076 In Iberian Peninsula & adjacent islands", "077 In eastern Europe In Soviet Union",
					"078 In Scandinavia", "079 In other languages", "080 General collections",
					"081 General collections American", "082 General collections In English",
					"083 General collections In other Germanic languages",
					"084 General collections In French, Provencal, Catalan",
					"085 General collections In Italian, Romanian, Rhaeto-Romanic",
					"086 General collections In Spanish & Portuguese", "087 General collections In Slavic languages",
					"088 General collections In Scandinavian languages", "089 General collections In other languages",
					"090 Manuscripts & rare books", "091 Manuscripts", "092 Block books", "093 Incunabula",
					"094 Printed books", "095 Books notable for bindings", "096 Books notable for illustrations",
					"097 Books notable for ownership or origin", "098 Prohibited works, forgeries, hoaxes",
					"099 Books notable for format" }));
			break;
		case "100 Philosophy & Psychology":
			comboBoxDewey2.setModel(new DefaultComboBoxModel<String>(new String[] { "100 Philosophy & psychology",
					"101 Theory of philosophy", "102 Miscellany of philosophy", "103 Dictionaries of philosophy",
					"105 Serial publications of philosophy", "106 Organizations of philosophy",
					"107 Education, research in philosophy", "108 Kinds of persons in philosophy",
					"109 Historical treatment of philosophy", "110 Metaphysics", "111 Ontology",
					"113 Cosmology (Philosophy of nature)", "114 Space", "115 Time", "116 Change", "117 Structure",
					"118 Force & Energy", "119 Number & quantity", "120 Epistemology, causation, humankind",
					"121 Epistemology (Theory of knowledge)", "122 Causation", "123 Determinism & indeterminism",
					"124 Teleology", "126 The self", "127 The unconscious & the subconscious", "128 Humankind",
					"129 Origin & destiny of individual souls", "130 Paranormal phenomena",
					"131 Occult methods for achieving well-being", "133 Parapsychology & occultism",
					"135 Dreams & mysteries", "137 Divinatory graphology", "138 Physiognomy", "139 Phrenology",
					"140 Specific philosophical schools", "141 Idealism & related systems", "142 Critical philosophy",
					"143 Intuitionism & Bergsonism", "144 Humanism & related systems", "145 Sensationalism",
					"146 Naturalism & related systems", "147 Pantheism & related systems",
					"148 Liberalism, eclecticism, traditionalism", "149 Other philosophical systems", "150 Psychology",
					"152 Perception, movement, emotions, drives", "153 Mental processes & intelligence",
					"154 Subconscious & altered states", "155 Differential & developmental psychology",
					"156 Comparative psychology", "158 Applied psychology", "160 Logic", "161 Induction",
					"162 Deduction", "165 Fallacies & sources of error", "166 Syllogisms", "167 Hypotheses",
					"168 Argument & persuasion", "169 Analogy", "170 Ethics (Moral philosophy)",
					"171 Systems & doctrines", "172 Political ethics", "173 Ethics of family relationships",
					"174 Economic & professional ethics", "175 Ethics of recreation & leisure",
					"176 Ethics of sex & reproduction", "177 Ethics of social relations", "178 Ethics of consumption",
					"179 Other ethical norms", "180 Ancient, medieval, Oriental philosophy", "181 Oriental philosophy",
					"182 Pre-Socratic Greek philosophies", "183 Sophistic & Socratic philosophies",
					"184 Platonic philosophy", "185 Aristotelian philosophy",
					"186 Skeptic and Neoplatonic philosophies", "187 Epicurean philosophy", "188 Stoic philosophy",
					"189 Medieval Western philosophy", "190 Modern Western philosophy",
					"191 Modern Western philosophy United States & Canada",
					"192 Modern Western philosophy British Isles", "193 Modern Western philosophy Germany & Austria",
					"194 Modern Western philosophy France", "195 Modern Western philosophy Italy",
					"196 Modern Western philosophy Spain & Portugal", "197 Modern Western philosophy Soviet Union",
					"198 Modern Western philosophy Scandinavia",
					"199 Modern Western philosophy Other geographical areas" }));
			break;
		case "200 Religion":
			comboBoxDewey2.setModel(new DefaultComboBoxModel<String>(new String[] { "200 Religion",
					"201 Philosophy of Christianity", "202 Miscellany of Christianity",
					"203 Dictionaries of Christianity", "204 Special topics", "205 Serial publications of Christianity",
					"206 Organizations of Christianity", "207 Education, research in Christianity",
					"208 Kinds of persons in Christianity", "209 History & geography of Christianity",
					"210 Natural theology", "211 Concepts of God", "212 Existence, attributes of God", "213 Creation",
					"214 Theodicy", "215 Science & religion", "216 Good & evil", "218 Humankind", "220 Bible",
					"221 Old Testament", "222 Historical books of Old Testament", "223 Poetic books of Old Testament",
					"224 Prophetic books of Old Testament", "225 New Testament", "226 Gospels & Acts", "227 Epistles",
					"228 Revelation (Apocalypse)", "229 Apocrypha & pseudepigrapha", "230 Christian theology",
					"231 God", "232 Jesus Christ & his family", "233 Humankind", "234 Salvation (Soteriology) & grace",
					"235 Spiritual beings", "236 Eschatology", "238 Creeds & catechisms", "239 Apologetics & polemics",
					"240 Christian moral & devotional theology", "241 Moral theology", "242 Devotional literature",
					"243 Evangelistic writings for individuals", "245 Texts of hymns", "246 Use of art in Christianity",
					"247 Church furnishings & articles", "248 Christian experience, practice, life",
					"249 Christian observances in family life", "250 Christian orders & local church",
					"251 Preaching (Homiletics)", "252 Texts of sermons", "253 Pastoral office (Pastoral theology)",
					"254 Parish government & administration", "255 Religious congregations & orders",
					"259 Activities of the local church", "260 Christian social theology", "261 Social theology",
					"262 Ecclesiology", "263 Times, places of religious observance", "264 Public worship",
					"265 Sacraments, other rites & acts", "266 Missions", "267 Associations for religious work",
					"268 Religious education", "269 Spiritual renewal", "270 Christian church history",
					"271 Religious orders in church history", "272 Persecutions in church history",
					"273 Heresies in church history", "274 Christian church in Europe", "275 Christian church in Asia",
					"276 Christian church in Africa", "277 Christian church in North America",
					"278 Christian church in South America", "279 Christian church in other areas",
					"280 Christian denominations & sects", "281 Early church & Eastern churches",
					"282 Roman Catholic Church", "283 Anglican churches", "284 Protestants of Continental origin",
					"285 Presbyterian, Reformed, Congregational", "286 Baptist, Disciples of Christ, Adventist",
					"287 Methodist & related churches", "289 Other denominations & sects",
					"290 Other & comparative religions", "291 Comparative religion",
					"292 Classical (Greek & Roman) religion", "293 Germanic religion", "294 Religions of Indic origin",
					"295 Zoroastrianism (Mazdaism, Parseeism)", "296 Judaism",
					"297 Islam & religions originating in it", "299 Other religions" }));
			break;
		case "300 Social Sciences":
			comboBoxDewey2.setModel(new DefaultComboBoxModel<String>(new String[] { "300 Social sciences",
					"301 Sociology & anthropology", "302 Social interaction", "303 Social processes",
					"304 Factors affecting social behavior", "305 Social groups", "306 Culture & institutions",
					"307 Communities", "310 General statistics", "314 General statistics Of Europe",
					"315 General statistics Of Asia", "316 General statistics Of Africa",
					"317 General statistics Of North America", "318 General statistics Of South America",
					"319 General statistics Of other parts of the world", "320 Political science",
					"321 Systems of governments & states", "322 Relation of state to organized groups",
					"323 Civil & political rights", "324 The political process",
					"325 International migration & colonization", "326 Slavery & emancipation",
					"327 International relations", "328 The legislative process", "330 Economics",
					"331 Labor economics", "332 Financial economics", "333 Land economics", "334 Cooperatives",
					"335 Socialism & related systems", "336 Public finance", "337 International economics",
					"338 Production", "339 Macroeconomics & related topics", "340 Law", "341 International law",
					"342 Constitutional & administrative law", "343 Military, tax, trade, industrial law",
					"344 Social, labor, welfare, & related law", "345 Criminal law", "346 Private law",
					"347 Civil procedure & courts", "348 Law (Statutes), regulations, cases",
					"349 Law of specific jurisdictions & areas", "350 Public administration",
					"351 Of central governments", "352 Of local governments", "353 of U.S. federal & state governments",
					"354 Of specific central governments", "355 Military science", "356 Foot forces & warfare",
					"357 Mounted forces & warfare", "358 Other specialized forces & services",
					"359 Sea (Naval) forces & warfare", "360 Social services; association",
					"361 General social problems & services", "362 Social welfare problems & services",
					"363 Other social problems & services", "364 Criminology", "365 Penal & related institutions",
					"366 Association", "367 General clubs", "368 Insurance", "369 Miscellaneous kinds of associations",
					"370 Education", "371 School management; special education", "372 Elementary education",
					"373 Secondary education", "374 Adult education", "375 Curriculums", "376 Education of women",
					"377 Schools & religion", "378 Higher education", "379 Government regulation, control, support",
					"380 Commerce, communications, transport", "381 Internal commerce (Domestic trade)",
					"382 International commerce (Foreign trade)", "383 Postal communication",
					"384 Communications Telecommunication", "385 Railroad transportation",
					"386 Inland waterway & ferry transportation", "387 Water, air, space transportation",
					"388 Transportation Ground transportation", "389 Metrology & standardization",
					"390 Customs, etiquette, folklore", "391 Costume & personal appearance",
					"392 Customs of life cycle & domestic life", "393 Death customs", "394 General customs",
					"395 Etiquette (Manners)", "398 Folklore", "399 Customs of war & diplomacy" }));
			break;
		case "400 Language":
			comboBoxDewey2.setModel(new DefaultComboBoxModel<String>(new String[] { "400 Language",
					"401 Philosophy & theory", "402 Miscellany", "403 Dictionaries & encyclopedias",
					"404 Special topics", "405 Serial publications", "406 Organizations & management",
					"407 Education, research, related topics", "408 With respect to kinds of persons",
					"409 Geographical & persons treatment", "410 Linguistics", "411 Writing systems", "412 Etymology",
					"413 Dictionaries", "414 Phonology", "415 Structural systems (Grammar)",
					"417 Dialectology & historical linguistics", "418 Standard usage Applied linguistics",
					"419 Verbal language not spoken or written", "420 English & Old English",
					"421 English writing system & phonology", "422 English etymology", "423 English dictionaries",
					"425 English grammar", "427 English language variations", "428 Standard English usage",
					"429 Old English (Anglo-Saxon)", "430 Germanic languages German",
					"431 German writing system & phonology", "432 German etymology", "433 German dictionaries",
					"435 German grammar", "437 German language variations", "438 Standard German usage",
					"439 Other Germanic languages", "440 Romance languages French",
					"441 French writing system & phonology", "442 French etymology", "443 French dictionaries",
					"445 French grammar", "447 French language variations", "448 Standard French usage",
					"449 Provencal & Catalan", "450 Italian, Romanian, Rhaeto-Romantic",
					"451 Italian writing system & phonology", "452 Italian etymology", "453 Italian dictionaries",
					"455 Italian grammar", "457 Italian language variations", "458 Standard Italian usage",
					"459 Romanian & Rhaeto-Romanic", "460 Spanish & Portugese languages",
					"461 Spanish writing system & phonology", "462 Spanish etymology", "463 Spanish dictionaries",
					"465 Spanish grammar", "467 Spanish language variations", "468 Standard Spanish usage",
					"469 Portuguese", "470 Italic Latin", "471 Classical Latin writing & phonology",
					"472 Classical Latin etymology & phonology", "473 Classical Latin dictionaries",
					"475 Classical Latin grammar", "477 Old, Postclassical, Vulgar Latin", "478 Classical Latin usage",
					"479 Other Italic languages", "480 Hellenic languages Classical Greek",
					"481 Classical Greek writing & phonology", "482 Classical Greek etymology",
					"483 Classical Greek dictionaries", "485 Classical Greek grammar",
					"487 Preclassical & postclassical Greek", "488 Classical Greek usage",
					"489 Other Hellenic languages", "490 Other languages", "491 East Indo-European & Celtic languages",
					"492 Afro-Asiatic languages Semitic", "493 Non-Semitic Afro-Asiatic languages",
					"494 Ural-Altaic, Paleosiberian, Dravidian", "495 Languages of East & Southeast Asia",
					"496 African languages", "497 North American native languages",
					"498 South American native languages", "499 Miscellaneous languages" }));
			break;
		case "500 Natural Sciences & Math":
			comboBoxDewey2.setModel(new DefaultComboBoxModel<String>(new String[] {
					"500 Natural sciences & mathematics", "501 Philosophy & theory", "502 Miscellany",
					"503 Dictionaries & encyclopedias", "505 Serial publications", "506 Organizations & management",
					"507 Education, research, related topics", "508 Natural history",
					"509 Historical, areas, persons treatment", "510 Mathematics", "511 General principles",
					"512 Algebra & number theory", "513 Arithmetic", "514 Topology", "515 Analysis", "516 Geometry",
					"519 Probabilities & applied mathematics", "520 Astronomy & allied sciences",
					"521 Celestial mechanics", "522 Techniques, equipment, materials",
					"523 Specific celestial bodies & phenomena", "525 Earth (Astronomical geography)",
					"526 Mathematical geography", "527 Celestial navigation", "528 Ephemerides", "529 Chronology",
					"530 Physics", "531 Classical mechanics Solid mechanics", "532 Fluid mechanics Liquid mechanics",
					"533 Gas mechanics", "534 Sound & related vibrations", "535 Light & paraphotic phenomena",
					"536 Heat", "537 Electricity & electronics", "538 Magnetism", "539 Modern physics",
					"540 Chemistry & allied sciences", "541 Physical & theoretical chemistry",
					"542 Techniques, equipment, materials", "543 Analytical chemistry", "544 Qualitative analysis",
					"545 Quantitative analysis", "546 Inorganic chemistry", "547 Organic chemistry",
					"548 Crystallography", "549 Mineralogy", "550 Earth sciences",
					"551 Geology, hydrology, meteorology", "552 Petrology", "553 Economic geology",
					"554 Earth sciences of Europe", "555 Earth sciences of Asia", "556 Earth sciences of Africa",
					"557 Earth sciences of North America", "558 Earth sciences of South America",
					"559 Earth sciences of other areas", "560 Paleontology Paleozoology", "561 Paleobotany",
					"562 Fossil invertebrates", "563 Fossil primitive phyla", "564 Fossil Mollusca & Molluscoidea",
					"565 Other fossil invertebrates", "566 Fossil Vertebrata (Fossil Craniata)",
					"567 Fossil cold-blooded vertebrates", "568 Fossil Aves (Fossil birds)", "569 Fossil Mammalia",
					"570 Life sciences", "572 Human races", "573 Physical anthropology", "574 Biology",
					"575 Evolution & genetics", "576 Microbiology", "577 General nature of life",
					"578 Microscopy in biology", "579 Collection and preservation", "580 Botanical sciences",
					"581 Botany", "582 Spermatophyta (Seed-bearing plants)", "583 Dicotyledones", "584 Monocotyledones",
					"585 Gymnospermae (Pinophyta)", "586 Cryptogamia (Seedless plants)",
					"587 Pteridophyta (Vascular cryptograms)", "588 Bryophyta", "589 Thallobionta & Prokaryotae",
					"590 Zoological sciences", "591 Zoology", "592 Invertebrates",
					"593 Protozoa, Echinodermata, related phyla", "594 Mollusca & Molluscoidea",
					"595 Other invertebrates", "596 Vertebrata (Craniata, Vertebrates)",
					"597 Cold-blooded vertebrates Fishes", "598 Aves (Birds)", "599 Mammalia (Mammals)" }));
			break;
		case "600 Technology":
			comboBoxDewey2.setModel(new DefaultComboBoxModel<String>(new String[] { "600 Technology (Applied sciences)",
					"601 Philosophy & theory", "602 Miscellany", "603 Dictionaries & encyclopedias",
					"604 Special topics", "605 Serial publications", "606 Organizations",
					"607 Education, research, related topics", "608 Invention & patents",
					"609 Historical, areas, persons treatment", "610 Medical sciences Medicine",
					"611 Human anatomy, cytology, histology", "612 Human physiology", "613 Promotion of health",
					"614 Incidence & prevention of disease", "615 Pharmacology & theraputics", "616 Diseases",
					"617 Surgery & related medical specialities", "618 Gynecology & other medical specialities",
					"619 Experimental medicine", "620 Engineering & allied operations", "621 Applied physics",
					"622 Mining & related operations", "623 Military & nautical engineering", "624 Civil engineering",
					"625 Engineering of railroads, roads", "627 Hydraulic engineering",
					"628 Sanitary & municipal engineering", "629 Other branches of engineering", "630 Agriculture",
					"631 Techniques, equipment, materials", "632 Plant injuries, diseases, pests",
					"633 Field & plantation crops", "634 Orchards, fruits, forestry", "635 Garden crops (Horticulture)",
					"636 Animal husbandry", "637 Processing dairy & related products", "638 Insect culture",
					"639 Hunting, fishing, conservation", "640 Home economics & family living", "641 Food & drink",
					"642 Meals & table service", "643 Housing & household equipment", "644 Household utilities",
					"645 Household furnishings", "646 Sewing, clothing, personal living",
					"647 Management of public households", "648 Housekeeping", "649 Child rearing & home care of sick",
					"650 Management & auxiliary services", "651 Office services",
					"652 Processes of written communication", "653 Shorthand", "657 Accounting",
					"658 General management", "659 Advertising & public relations", "660 Chemical engineering",
					"661 Industrial chemicals technology", "662 Explosives, fuels technology",
					"663 Beverage technology", "664 Food technology", "665 Industrial oils, fats, waxes, gases",
					"666 Ceramic & allied technologies", "667 Cleaning, color, related technologies",
					"668 Technology of other organic products", "669 Metallurgy", "670 Manufacturing",
					"671 Metalworking & metal products", "672 Iron, steel, other iron alloys", "673 Nonferrous metals",
					"674 Lumber processing, wood products, cork", "675 Leather & fur processing",
					"676 Pulp & paper technology", "677 Textiles", "678 Elastomers & elastomer products",
					"679 Other products of specific materials", "680 Manufacture for specific uses",
					"681 Precision instruments & other devices", "682 Small forge work (Blacksmithing)",
					"683 Hardware & household appliances", "684 Furnishings & home workshops",
					"685 Leather, fur, related products", "686 Printing & related activities", "687 Clothing",
					"688 Other final products & packaging", "690 Buildings", "691 Building materials",
					"692 Auxiliary construction practices", "693 Specific materials & purposes",
					"694 Wood construction Carpentry", "695 Roof covering", "696 Utilities",
					"697 Heating, ventilating, air-conditioning", "698 Detail finishing" }));
			break;
		case "700 The Arts":
			comboBoxDewey2.setModel(new DefaultComboBoxModel<String>(new String[] { "700 The arts",
					"701 Philosophy & theory", "702 Miscellany", "703 Dictionaries & encyclopedias",
					"704 Special topics", "705 Serial publications", "706 Organizations & management",
					"707 Education, research, related topics", "708 Galleries, museums, private collections",
					"709 Historical, areas, persons treatment", "710 Civic & landscape art",
					"711 Area planning (Civic art)", "712 Landscape architecture",
					"713 Landscape architecture of trafficways", "714 Water features", "715 Woody plants",
					"716 Herbaceous plants", "717 Structures", "718 Landscape design of cemeteries",
					"719 Natural landscapes", "720 Architecture", "721 Architectural structure",
					"722 Architecture to ca. 300", "723 Architecture from ca. 300 to 1399",
					"724 Architecture from 1400", "725 Public structures", "726 Buildings for religious purposes",
					"727 Buildings for education & research", "728 Residential & related buildings",
					"729 Design & decoration", "730 Plastic arts Sculpture",
					"731 Processes, forms, subjects of sculpture", "732 Sculpture to ca. 500",
					"733 Greek, Etruscan, Roman sculpture", "734 Sculpture from ca. 500 to 1399",
					"735 Sculpture from 1400", "736 Carving & carvings", "737 Numismatics & sigillography",
					"738 Ceramic arts", "739 Art metalwork", "740 Drawing & decorative arts", "741 Drawing & drawings",
					"742 Perspective", "743 Drawing & drawings by subject", "745 Decorative arts", "746 Textile arts",
					"747 Interior decoration", "748 Glass", "749 Furniture & accessories", "750 Painting & paintings",
					"751 Techniques, equipment, forms", "752 Color", "753 Symbolism, allegory, mythology, legend",
					"754 Genre paintings", "755 Religion & religious symbolism", "757 Human figures & their parts",
					"758 Other subjects", "759 Historical, areas, persons treatment",
					"760 Graphic arts Printmaking & prints", "761 Relief processes (Block printing)",
					"763 Lithographic (Planographic) processes", "764 Chromolithography & serigraphy",
					"765 Metal engraving", "766 Mezzotinting & related processes", "767 Etching & drypoint",
					"769 Prints", "770 Photography & photographs", "771 Techniques, equipment, materials",
					"772 Metallic salt processes", "773 Pigment processes of printing", "774 Holography",
					"778 Fields & kinds of photography", "779 Photographs", "780 Music",
					"781 General principles & musical forms", "782 Vocal music",
					"783 Music for single voices The voice", "784 Instruments & Instrumental ensembles",
					"785 Chamber music", "786 Keyboard & other instruments", "787 Stringed instruments (Chordophones)",
					"788 Wind instruments (Aerophones)", "790 Recreational & performing arts",
					"791 Public performances", "792 Stage presentations", "793 Indoor games & amusements",
					"794 Indoor games of skill", "795 Games of chance", "796 Athletic & outdoor sports & games",
					"797 Aquatic & air sports", "798 Equestrian sports & animal racing",
					"799 Fishing, hunting, shooting" }));
			break;
		case "800 Literature & Rhetoric":
			comboBoxDewey2.setModel(new DefaultComboBoxModel<String>(new String[] { "800 Literature & rhetoric",
					"801 Philosophy & theory", "802 Miscellany", "803 Dictionaries & encyclopedias",
					"805 Serial publications", "806 Organizations", "807 Education, research, related topics",
					"808 Rhetoric & collections of literature", "809 Literary history & criticism",
					"810 American literature in English", "811 Poetry", "812 Drama", "813 Fiction", "814 Essays",
					"815 Speeches", "816 Letters", "817 Satire & humor", "818 Miscellaneous writings",
					"820 English & Old English literatures", "821 English poetry", "822 English drama",
					"823 English fiction", "824 English essays", "825 English speeches", "826 English letters",
					"827 English satire & humor", "828 English miscellaneous writings", "829 Old English (Anglo-Saxon)",
					"830 Literatures of Germanic languages", "831 Early to 1517", "832 Reformation, etc.  1517-1750",
					"833 Classic period,  1750-1830", "834 Post classic & modern,  1830-1940/50",
					"835 Contemporary authors not already established in the catalog,  1940/50-",
					"836 German dialect literature", "837 German-American", "838 German miscellaneous writings",
					"839 Other Germanic literatures", "840 Literatures of Romance languages",
					"841 Old and early French to 1400", "842 Transition & renaissance periods, 1400-1600",
					"843 Classical period, 1600-1715", "844 18 th Century, 1715-1789",
					"845 Revolution to present, 1789-1940/50",
					"846 Contemporary authors not already established in the Catalog, 1940/50-", "847 French Canadian",
					"848 Provencal", "849 French dialect literature", "850 Italian, Romanian, Rhaeto-Romanic",
					"851 Early period to 1375", "852 Classical learning, 1375-1492", "853 1492-1585", "854 1585-1814",
					"855 1814-1940/50", "856 Works in and/or about Italian dialects", "857 Sardinian",
					"858 Romanian (including Wallachian)", "859 Rumansh, Rhastian, Rhaeto-Romanic, Moldavian",
					"860 Spanish & Portuguese literatures", "861 Early to 1400", "862 1400-1553",
					"863 Golden age, 1554-1700", "864 1700-1800", "865 1800-",
					"866 Works in and/or about Iberian dialects other than Castilian, Catalan and Portuguese",
					"867 Catalan", "868 Portuguese", "869 South/Central American Literature",
					"870 General works on Latin literature", "871 Latin Authors",
					"872 Collections of Latin prose or poetry",
					"875 Medieval and modern Latin literature, 500 A.D. – date",
					"879 Classical literature-History and criticism", "880 Hellenic literatures Classical Greek",
					"881 Greek Authors", "882 Collections of Greek prose or poetry",
					"885 Modern Literature in the Ancient Greek Language",
					"889 Literature in Medieval and Modern Greek", "890 Indic Literature",
					"891 Iranian/Persian, Celtic/Gaelic, Slavic and Baltic Literatures",
					"892-894 Afro-Asiatic Literature (Hamito-Semitic)",
					"895-896 Literature in East Asian and African Languages",
					"897-898 North and South American Native Languages" }));
			break;
		case "900 Geography & History":
			comboBoxDewey2.setModel(new DefaultComboBoxModel<String>(new String[] { "900 Geography & history",
					"901 Philosophy & theory", "902 Miscellany", "903 Dictionaries & encyclopedias",
					"904 Collected accounts of events", "905 Serial publications", "906 Organizations & management",
					"907 Education, research, related topics", "908 With respect to kinds of persons",
					"909 World history", "910 Geography & travel", "911 Historical geography",
					"912 Graphic representations of earth", "913 Ancient world", "914 Europe", "915 Asia", "916 Africa",
					"917 North America", "918 South America", "919 Other areas", "920 Biography, genealogy, insignia",
					"929 Genealogy, names, insignia", "930 History of ancient world",
					"931 History of ancient world China", "932 History of ancient world Egypt",
					"933 History of ancient world Palestine", "934 History of ancient world India",
					"935 History of ancient world Mesopotamia & Iranian Plateau",
					"936 History of ancient world Europe north & west of Italy",
					"937 History of ancient world Italy & adjacent territories", "938 History of ancient world Greece",
					"939 History of ancient world Other parts of ancient world", "940 History of Europe",
					"941 History of Europe British Isles", "942 History of Europe England & Wales",
					"943 History of Europe Central Europe Germany", "944 History of Europe France & Monaco",
					"945 History of Europe Italian Peninsula & adjacent islands",
					"946 History of Europe Iberian Peninsula & adjacent islands",
					"947 History of Europe Eastern Europe Soviet Union",
					"948 History of Europe Northern Europe Scandinavia", "949 History of Europe Other parts of Europe",
					"950 History of Asia Far East", "951 History of Asia China & adjacent areas",
					"952 History of Asia Japan", "953 History of Asia Arabian Peninsula & adjacent areas",
					"954 History of Asia South Asia India", "955 History of Asia Iran",
					"956 History of Asia Middle East (Near East)", "957 History of Asia Siberia (Asiatic Russia)",
					"958 History of Asia Central Asia", "959 History of Asia Southeast Asia", "960 History of Africa",
					"961 History of Africa Tunisia & Libya", "962 History of Africa Egypt & Sudan",
					"963 History of Africa Ethiopia", "964 History of Africa Morocco & Canary Islands",
					"965 History of Africa Algeria", "966 History of Africa West Africa & offshore islands",
					"967 History of Africa Central Africa & offshore islands", "968 History of Africa Southern Africa",
					"969 History of Africa South Indian Ocean islands", "970 History of North America",
					"971 History of North America Canada", "972 History of North America Middle America Mexico",
					"973 History of North America United States",
					"974 History of North America Northeastern United States",
					"975 History of North America Southeastern United States",
					"976 History of North America South central United States",
					"977 History of North America North central United States",
					"978 History of North America Western United States",
					"979 History of North America Great Basin & Pacific Slope", "980 History of South America",
					"981 History of South America Brazil", "982 History of South America Argentina",
					"983 History of South America Chile", "984 History of South America Bolivia",
					"985 History of South America Peru", "986 History of South America Colombia & Ecuador",
					"987 History of South America Venezuela", "988 History of South America Guiana",
					"989 History of South America Paraguay & Uruguay", "990 History of other areas",
					"993 History of other areas New Zealand", "994 History of other areas Australia",
					"995 History of other areas Melanesia New Guinea",
					"996 History of other areas Other parts of Pacific Polynesia",
					"997 History of other areas Atlantic Ocean islands",
					"998 History of other areas Arctic islands & Antarctica" }));
			break;
		case "-Select-":
			comboBoxDewey2.setModel(new DefaultComboBoxModel<String>(new String[] {}));
		}
	}

	// end
}
