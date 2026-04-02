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
            "Tags should only contain letters, numbers, spaces, and hyphens, and it should not be blank. "
                    + "Kebab-case is recommended for consistency.";
    private static final String VALIDATION_REGEX = "[A-Za-z0-9][A-Za-z0-9 -]*";

    private final String tagName;

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
    public static boolean isValidTagName(String test) throws NullPointerException {
        if (test == null) {
            throw new NullPointerException("Tag name cannot be null");
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
