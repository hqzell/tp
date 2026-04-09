package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Room room;
    private final Comment comment;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Room room, Comment comment, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, room, comment, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.room = room;
        this.tags.addAll(tags);
        this.comment = comment;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Room getRoom() {
        return room;
    }

    public Comment getComment() {
        return comment;
    }

    /**
     * Returns an immutable tag set, which throws
     * {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons cannot coexist in the address book because they share the same name,
     * room number, or (when both are non-empty) the same phone number or email.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        if (otherPerson == null) {
            return false;
        }

        if (otherPerson.getName().equals(getName())) {
            return true;
        }
        if (otherPerson.getRoom().equals(getRoom())) {
            return true;
        }
        if (phonesConflict(otherPerson)) {
            return true;
        }
        if (emailsConflict(otherPerson)) {
            return true;
        }
        return false;
    }

    private boolean phonesConflict(Person other) {
        return !getPhone().value.isEmpty() && !other.getPhone().value.isEmpty() && getPhone().equals(other.getPhone());
    }

    private boolean emailsConflict(Person other) {
        return !getEmail().value.isEmpty() && !other.getEmail().value.isEmpty() && getEmail().equals(other.getEmail());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        assert otherPerson != null : "After instanceof check, otherPerson should not be null";
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && room.equals(otherPerson.room)
                && comment.equals(otherPerson.comment)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, room, comment, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("room", room)
                .add("comment", comment)
                .add("tags", tags)
                .toString();
    }

}
