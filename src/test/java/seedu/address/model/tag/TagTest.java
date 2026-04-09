package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));

        // spaces not allowed
        assertThrows(IllegalArgumentException.class, () -> new Tag("study group"));
        assertThrows(IllegalArgumentException.class, () -> new Tag("halal food"));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    public void equals_differentCaseCustomTags_notEqual() {
        assertNotEquals(new Tag("study-group"), new Tag("Study-Group"));
    }
}
