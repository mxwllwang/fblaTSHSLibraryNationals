# fblaTSHSLibraryNationals
General Project Documentation
Maxwell Wang submission for FBLA Nationals 2017-18, Coding and Programming

DESCRIPTION: 
Manages members, fines, and inventory for a school library.
Programmed in Eclipse WindowBuilder with Java Swing, Runs in JRE 7 / version 1.7.0_80 (Java 7)

INSTALLATION:
To run, download the Runnable JAR file, FBLACodingSubmissionMW.jar. 
This file will most likely end up in the downloads folder, and so will TSHSLibrarydata.ser (unless you move the .jar before running it). For more secure purposes, the jar file can be moved to another folder.
The application will create a text file to store information: Do not delete the TSHSLibrarydata.ser file, or the data stored in the database will be reset. Do not modify its filepath. 
A backup way to run is to download the project classes to an IDE like Eclipse/IntelliJ, and run the class TSHSLibrary.java, and the program should appear. 
All necessary files are included in the repository. 
Text documents show specifications and additional notes about developing the application.

USAGE:
The program has 5 separate tabs: Books, Members, Report, Help, and Settings, on the upper left hand corner of the screen. To navigate to a tab, simply click on it.

To create a new book, fill in the fields on the left of the book tab, and press the create button. 
Make sure all required fields, indicated by a * are filled out. To clear fields, click on the cancel button.
To view information about a book, look directly at the table, or select the book from the table (See advanced features). 
Doing so will display more specific information about each book in the left hand side of the tab, or the viewpane.
Once a book is selected, the book can be edited by clicking the "Edit entry" button and then filling out the fields on the right of the book tab. The book can be removed by clicking "Remove book".

To create a new member, follow the same steps as book creation on the members tab, under the "Register New Member" header.
Selecting a member from the table will show information about the member, as well as buttons to edit or remove.
When a member is selected, the bottom right-hand corner table will display all the books that the member has checked out.
To check out a book, type in an ID at the bottom of the page, and hit the "Check out book by ID" button to check the book out (See advanced fetaures). To return a book, select the book and click on the "Return" button.

To generate a report, go to the report tab. Select radiobuttons showing two types of reports (Book Issuance and Fines), for either a selected member (See advanced features) or all members. Then, select buttons choosing between a weekly report, which shows actions within the past week, or a complete report to show all the information for further analysis.
The generate report button will allow the report to be previewed in the preview pane to the right.
The user can then choose to save, print, or cancel the report with the button at the bottom of the page.

The settings tab displays information about library presets, such as the amount of fines accumulated for overdue books, and maximum alloted books for each student or teacher.
For additional help, refer to the help tab in the application.

ADVANCED FEATURES:

All tables can be sorted by clicking on the column header. This makes locating books and members by field much easier.
By selecting a book from the books table and immediately selecting a member, the book's ID will already be filled out in the check out book by ID form and allow the member to check out that book.
By selecting the button "Generate report" at the bottom of the members tab, a unique individual report can be produced showing report information only about the selected member. These settings can be then further toggled in the report tab when generating reports.

PROJECT GUIDELINES:

Coding & Programming Category: Prejudged Projects & Presentation Type: Individual Overview Two (2) parts: a prejudged project and a presentation. Competitors must complete both parts for award eligibility.

Topic: Develop a database program to manage the issuance of books at a school library. Give the school a name. The program must be able to complete a minimum of the following tasks: 
Track student and teacher names with ability to enter/view/edit names. 
Track the issuance of books for a student or teacher. 
Manage different limits for the number of books that can be issued to a student or teacher. 
Manage the number of days that students and teachers can check out any book. (Hint: Mostly like the number of days will differ for students and teachers). 
Give each book a different ID. Also, each book of same name and same author (but number of copies) will have different ID. 
Generate/print weekly report to show books issued to whom and number of days leading to the due date return. 
Generate/print weekly report of detail of fines (when book not returned on time).

Specific Guidelines 
The program must run on Windows XP or higher. Solution must run standalone with no programming errors. Data must be free of viruses/malware. Any entry with contaminated data will not be judged. The program should be shown to the judges.
