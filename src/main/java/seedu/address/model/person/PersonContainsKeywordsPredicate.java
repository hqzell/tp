package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s name, room, or tags match any of the keywords given.
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PersonContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        String personName = person.getName().fullName.toLowerCase();
        String personRoom = person.getRoom().value.toLowerCase();

        return keywords.stream()
                .map(String::toLowerCase)
                .anyMatch(keyword -> personName.contains(keyword)
                        || personRoom.contains(keyword)
                        || person.getTags().stream()
                        .map(tag -> tag.getTagName().toLowerCase())
                        .anyMatch(tagName -> tagName.contains(keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonContainsKeywordsPredicate)) {
            return false;
        }

        PersonContainsKeywordsPredicate otherPredicate = (PersonContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
