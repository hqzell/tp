package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PersonContainsKeywordsPredicate firstPredicate =
                new PersonContainsKeywordsPredicate(firstPredicateKeywordList);
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsKeywordsPredicate firstPredicateCopy =
                new PersonContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_matchesNameRoomOrTag_returnsTrue() {
        Person person = new PersonBuilder().withName("Alex Yeoh").withRoom("#14-203-D")
                .withTags("friends", "neighbours").build();

        assertTrue(new PersonContainsKeywordsPredicate(Collections.singletonList("al")).test(person));
        assertTrue(new PersonContainsKeywordsPredicate(Collections.singletonList("#14-2")).test(person));
        assertTrue(new PersonContainsKeywordsPredicate(Collections.singletonList("frie")).test(person));
    }

    @Test
    public void test_doesNotMatchNameRoomOrTag_returnsFalse() {
        Person person = new PersonBuilder().withName("Alex Yeoh").withRoom("#14-203-D")
                .withTags("friends", "neighbours").build();

        assertFalse(new PersonContainsKeywordsPredicate(Collections.singletonList("xyz")).test(person));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(keywords);

        String expected = PersonContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
