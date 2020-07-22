/*
 * Example code used in exercises for lecture "Grundlagen des Software-Testens"
 * Created and given by Ina Schieferdecker, Theo Vassiliou and Diana Serbanescu
 * Technische Universität Berlin
 */
package exercise1.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import exercise1.addressbook.model.AddressBook;
import exercise1.addressbook.model.Entry;
import exercise1.addressbook.model.Gender;
import exercise1.addressbook.model.PhoneNumber;
import exercise1.addressbook.model.SizeLimitReachedException;

/**
 * Uebung 1 - Grundlagen des Testens mit JUnit
 * 
 * Bitte Nummer der Gruppe eintragen: 8
 * 
 * Bitte Gruppenmitglieder eintragen:
 * 
 * @author ...
 * @author ...
 * @author ...
 * @author ...
 */
public class AddressBookFunctionalTests {

	// The component under test
	private AddressBook addressBook;

	/*
	 * Aufgabe 3a) Schreiben Sie eine Methode zum Aufsetzen der Testumgebung
	 * ("Fixture"). Diese Methode soll automatisch vor jedem einzelnen JUnit
	 * Testfall ausgeführt werden. Innerhalb der Methode soll mindestens ein neues
	 * AddressBook Objekt angelegt und im Attribut "addressBook" gepeichert werden.
	 */

	@Before
	public void beforeEach() throws SizeLimitReachedException {
		this.addressBook = new AddressBook();
		/**
		 * As populating the AdressBook beforehand does not adhere to the AAA Principle
		 * we do not add Address Books Entries here. As it was part of the task objects
		 * could have been added by outcommenting the following lines of code
		 */
		// Entry entry = new Entry("anyentry", "anyentry", Gender.Female, new
		// PhoneNumber(3434));
		// this.addressBook.addEntry(entry);
	}

	/*
	 * Aufgabe 3b) Schreiben Sie einen JUnit Testfall zum überprüfen der
	 * Funktionalität der addEntry() Methode.
	 */

	@Test
	public void test_addEntry() throws SizeLimitReachedException {
		Entry entry = new Entry("test", "test", Gender.Female, new PhoneNumber(3434));
		Entry copyOfEntry = new Entry("test", "test", Gender.Female, new PhoneNumber(3434));

		assertThat(this.addressBook.addEntry(entry)).isTrue();
		assertThat(this.addressBook.addEntry(entry)).isFalse();
		assertThat(addressBook.getEntries()).contains(copyOfEntry);
	}

	@Test
	public void test_addEntryMAX() throws SizeLimitReachedException {
		Entry testentry = new Entry("test", "test", Gender.Female, new PhoneNumber(3434));

		IntStream.range(0, 10).forEach(i -> {
			try {
				Entry entry = new Entry("test" + i, "test", Gender.Female, new PhoneNumber(3434));
				this.addressBook.addEntry(entry);
			} catch (SizeLimitReachedException e) {
				e.printStackTrace();
			}
		});

		Exception exception = assertThrows(SizeLimitReachedException.class, () -> {
			this.addressBook.addEntry(testentry);
		});

		String expectedMessage = "Limit is";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	/*
	 * Aufgabe 3c) Schreiben Sie einen JUnit Testfall zum überprüfen der
	 * Funktionalität der getEntry() Methode.
	 */

	@Test
	public void test_getEntry_Normal() throws SizeLimitReachedException {
		String firstname = "Firstnametest";
		String lastname = "Lastnametest";
		Entry entry = new Entry(firstname, lastname, Gender.Female, new PhoneNumber(3434));
		this.addressBook.addEntry(entry);

		Entry retrievedEntry = this.addressBook.getEntry(firstname, lastname);

		assertThat(retrievedEntry).isEqualToComparingFieldByFieldRecursively(entry);
	}

	@Test
	public void test_getEntry_EmptyString() throws SizeLimitReachedException {
		String firstname = "";
		String lastname = "";
		Entry entry = new Entry(firstname, lastname, Gender.Female, new PhoneNumber(3434));
		this.addressBook.addEntry(entry);

		Entry retrievedEntry = this.addressBook.getEntry(firstname, lastname);

		assertThat(retrievedEntry).isEqualToComparingFieldByFieldRecursively(entry);
	}

	@Test
	public void test_getEntry_Null() throws SizeLimitReachedException {
		String firstname = null;
		String lastname = null;
		Entry entry = new Entry(firstname, lastname, Gender.Female, new PhoneNumber(3434));
		this.addressBook.addEntry(entry);

		Entry retrievedEntry = this.addressBook.getEntry(firstname, lastname);

		assertThat(retrievedEntry).isEqualToComparingFieldByFieldRecursively(entry);
	}

	@Test
	public void test_getEntry_OtherSymbols() throws SizeLimitReachedException {
		String firstname = "󯻬𩐬Y煔쭨-񮤠𖔬樒PƳH砺ߺ̗ʜ扝񧔾򤟸􍊐ؚ󣇂,񂀾ⷻ晰\nI􎩺Pꄲ󤹺춥ܥ󀳀<̼ꧪ궋)𖖍񁖲螑򌕚񢢛эJэ𦱔ߵɎ񡎛'򆟈P堵𥹼�􂊬򢬵󯡀뎵窞		򻏔oɜ͑𮀂wާތ缹MA௣􎝸읩󣟅氊䀓Ջ˫𡫥˓򌮨ʕ𛂙󖽝󃞍צʕb哖		񶌦񿽒2)𞀵뀿⛆{񿽯S񗷨뮻q򦏀Ù؄񌃐HCn􉛷|񗆐𡛖ɍ􆰍됝⅃ປ		pR񪩴ڝNÖ;VԯBٸN𨖗Әہ0^J髸묙߀򕵹丿#҆񙗹񸻻ῇ-獣󐔘";
		String lastname = "󯻬𩐬Y煔쭨-񮤠𖔬樒PƳH砺ߺ̗ʜ扝񧔾򤟸􍊐ؚ󣇂,񂀾ⷻ晰I􎩺Pꄲ󤹺		&춥ܥ󀳀<̼ꧪ궋)𖖍񁖲螑򌕚񢢛эJэ𦱔ߵɎ񡎛'򆟈P堵𥹼�􂊬򢬵󯡀뎵窞		򻏔oɜ͑𮀂wާތ缹MA௣􎝸읩󣟅氊䀓Ջ˫𡫥˓򌮨ʕ𛂙󖽝󃞍צʕb哖		񶌦񿽒2)𞀵뀿⛆{񿽯S񗷨뮻q򦏀Ù؄񌃐HCn􉛷|񗆐𡛖ɍ􆰍됝⅃ປ		pR񪩴ڝNÖ;VԯBٸN𨖗Әہ0^J髸묙߀򕵹丿#҆񙗹񸻻ῇ-獣󐔘";
		Entry entry = new Entry(firstname, lastname, Gender.Female, new PhoneNumber(3434));
		this.addressBook.addEntry(entry);

		Entry retrievedEntry = this.addressBook.getEntry(firstname, lastname);

		assertThat(retrievedEntry).isEqualToComparingFieldByFieldRecursively(entry);
	}

	@Test
	public void test_getEntry_NonExistant_Null() throws SizeLimitReachedException {
		String firstname = "firstnametest";
		String lastname = "lastnametest";
		Entry entry = new Entry(firstname, lastname, Gender.Female, new PhoneNumber(3434));
		this.addressBook.addEntry(entry);

		Entry retrievedEntry = this.addressBook.getEntry(null, null);

		assertThat(retrievedEntry).isEqualTo(null);
	}

	@Test
	public void test_getEntry_NonExistant_EmptyString() throws SizeLimitReachedException {
		String firstname = "firstnametest";
		String lastname = "lastnametest";
		Entry entry = new Entry(firstname, lastname, Gender.Female, new PhoneNumber(3434));
		this.addressBook.addEntry(entry);

		Entry retrievedEntry = this.addressBook.getEntry("", "");

		assertThat(retrievedEntry).isEqualTo(null);
	}

	@Test
	public void test_getEntry_NonExistant_Normal() throws SizeLimitReachedException {
		String firstname = "firstnametest";
		String lastname = "lastnametest";
		Entry entry = new Entry(firstname, lastname, Gender.Female, new PhoneNumber(3434));
		this.addressBook.addEntry(entry);

		Entry retrievedEntry = this.addressBook.getEntry("notfirstnametest", "notlastnametest");

		assertThat(retrievedEntry).isEqualTo(null);
	}

}
