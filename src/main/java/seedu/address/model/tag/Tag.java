package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.logging.Logger;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS =
        "Tags must be one of the following: Vegetarian, Vegan, Halal, Allergies";

    private static final Logger logger = Logger.getLogger(Tag.class.getName());

    public final TagType tagType;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.tagType = TagType.fromString(tagName);
        logger.fine("Created tag: [" + tagType.getDisplayName() + "]");

        assert tagType != null : "TagType should never be null after parsing";
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        if (test == null) {
            return false;
        }
        try {
            TagType.fromString(test);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Tag)) {
            return false;
        }

        Tag otherTag = (Tag) other;
        return tagType.equals(otherTag.tagType);
    }

    @Override
    public int hashCode() {
        return tagType.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagType.getDisplayName() + ']';
    }

}
