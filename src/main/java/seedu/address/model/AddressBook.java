package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.CustomTagRegistry;
import seedu.address.model.tag.Tag;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed ({@link Person#isSamePerson(Person)})
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final CustomTagRegistry customTagRegistry;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        customTagRegistry = new CustomTagRegistry();
    }

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
        registerCustomTagsFromPersons(persons);
    }

    /**
     * Replaces the contents of the custom tag set with {@code tags}.
     */
    public void setCustomTags(Collection<Tag> tags) {
        requireNonNull(tags);
        customTagRegistry.setCustomTags(tags);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        persons.setPersons(newData.getPersonList());
        setCustomTags(newData.getCustomTagList());
        registerCustomTagsFromPersons(newData.getPersonList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Returns true if {@code candidate} conflicts with some person in the address book,
     * excluding {@code exclude} (e.g. the person being edited).
     */
    public boolean hasPersonExcept(Person candidate, Person exclude) {
        requireNonNull(candidate);
        requireNonNull(exclude);
        return getPersonList().stream().anyMatch(p -> p != exclude && candidate.isSamePerson(p));
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
        registerCustomTags(p.getTags());
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
        registerCustomTags(editedPerson.getTags());
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    /**
     * Returns true if the tag exists as a default tag or a previously created custom tag.
     */
    public boolean hasTag(Tag tag) {
        requireNonNull(tag);
        return customTagRegistry.contains(tag);
    }

    /**
     * Adds the given tags to the custom tag set.
     */
    public void addCustomTags(Collection<Tag> tags) {
        requireNonNull(tags);
        customTagRegistry.registerAll(tags);
    }

    private void registerCustomTags(Collection<Tag> tags) {
        addCustomTags(tags);
    }

    // If a person already has a custom tag, that tag should count as existing
    private void registerCustomTagsFromPersons(Collection<Person> persons) {
        persons.stream()
                .map(Person::getTags)
                .forEach(this::registerCustomTags);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public Set<Tag> getCustomTagList() {
        return customTagRegistry.getCustomTags();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons)
                && customTagRegistry.equals(otherAddressBook.customTagRegistry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, customTagRegistry);
    }
}
