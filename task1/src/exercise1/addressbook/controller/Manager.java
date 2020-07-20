/*
 * Example code used in exercises for lecture "Grundlagen des Software-Testens"
 * Created and given by Ina Schieferdecker, Theo Vassiliou and Diana Serbanescu
 * Technische Universität Berlin
 */
package exercise1.addressbook.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import exercise1.addressbook.model.AddressBook;
import exercise1.addressbook.view.UserInterface;

/**
 * Controller class for the address book example.
 * Bootstraps and manages the application's components.
 * @author Edzard Hoefig
 */
public class Manager implements Runnable {

	// Access to the application's data
	private AddressBook model;
	
	// Enables interaction with users
	private UserInterface view;
	
	// Location of the address book file
	private static final File addressBookFile = new File("contacts.xml");
	
	/**
	 * Ctor.
	 * Creates components for the application and establishes internal observer relationship.
	 */
	public Manager() {
		// Instantiate components
		this.model = new AddressBook();
		this.view = new UserInterface();
	}
	
	/**
	 * The bootstrap method.
	 * Creates the application and starts with program execution.
	 * @param args Optional arguments for program execution. Currently not in use.
	 */
	public static void main(String[] args) {
		// Create and start application 
		new Manager().run();	
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		try {
			model.load(addressBookFile);
		} catch (FileNotFoundException e) {
			System.err.println("Unable to find address book data file (" + e.getMessage() + ")");
			System.exit(-1);
		} catch (IOException e) {
			System.err.println("Unable to read address book data file (" + e.getMessage() + ")");
			System.exit(-1);
		}
		
		// Show the contacts stored in address book
		view.display(model);
	}	
}
