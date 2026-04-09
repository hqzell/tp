package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_NAME, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_duplicatePhone_throwsCommandException() {
        Person existingPerson = new PersonBuilder().withName("Alice Alpha")
                .withPhone("90000001")
                .withEmail("alice.alpha@example.com")
                .withRoom("#1-101-A")
                .build();
        Person duplicatePhone = new PersonBuilder().withName("Ben Beta")
                .withPhone("90000001")
                .withEmail("ben.beta@example.com")
                .withRoom("#2-202-B")
                .build();
        AddCommand addCommand = new AddCommand(duplicatePhone);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PHONE, () ->
                addCommand.execute(new ModelStubWithPerson(existingPerson)));
    }

    @Test
    public void execute_duplicateEmail_throwsCommandException() {
        Person existingPerson = new PersonBuilder().withName("Cara Gamma")
                .withPhone("90000002")
                .withEmail("cara.gamma@example.com")
                .withRoom("#3-303-C")
                .build();
        Person duplicateEmail = new PersonBuilder().withName("Dan Delta")
                .withPhone("90000003")
                .withEmail("cara.gamma@example.com")
                .withRoom("#4-404-D")
                .build();
        AddCommand addCommand = new AddCommand(duplicateEmail);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_EMAIL, () ->
                addCommand.execute(new ModelStubWithPerson(existingPerson)));
    }

    @Test
    public void execute_duplicateRoom_throwsCommandException() {
        Person existingPerson = new PersonBuilder().withName("Eve Epsilon")
                .withPhone("90000004")
                .withEmail("eve.epsilon@example.com")
                .withRoom("#5-505-E")
                .build();
        Person duplicateRoom = new PersonBuilder().withName("Fox Zeta")
                .withPhone("90000005")
                .withEmail("fox.zeta@example.com")
                .withRoom("#5-505-E")
                .build();
        AddCommand addCommand = new AddCommand(duplicateRoom);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_ROOM, () ->
                addCommand.execute(new ModelStubWithPerson(existingPerson)));
    }

    @Test
    public void execute_unknownCustomTagWithoutNewTagFlag_throwsCommandException() {
        Person personWithCustomTag = new PersonBuilder().withTags("study-group").build();
        AddCommand addCommand = new AddCommand(personWithCustomTag);
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();

        assertThrows(CommandException.class,
                String.format(
                        AddCommand.MESSAGE_UNKNOWN_TAGS,
                        "study-group",
                        "-newtag",
                        AddCommand.MESSAGE_USAGE_WITH_NEWTAG), () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_newCustomTagWithNewTagFlag_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().withTags("study-group").build();

        CommandResult commandResult = new AddCommand(validPerson, true).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
        assertTrue(modelStub.customTagsAdded.contains(new Tag("study-group")));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE
                + ", shouldCreateNewTags=false}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPersonExcept(Person candidate, Person exclude) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTag(Tag tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCustomTags(Set<Tag> tags) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate, java.util.Comparator<Person> comparator) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            AddressBook addressBook = new AddressBook();
            addressBook.addPerson(person);
            return addressBook;
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();
        final Set<Tag> customTagsAdded = new HashSet<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public boolean hasTag(Tag tag) {
            requireNonNull(tag);
            return tag.isBuiltInTag() || customTagsAdded.contains(tag);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public void addCustomTags(Set<Tag> tags) {
            requireNonNull(tags);
            customTagsAdded.addAll(tags);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
