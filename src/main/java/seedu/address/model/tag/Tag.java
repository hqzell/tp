package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS =
            "Tags should only contain letters, numbers, and hyphens, and it should not be blank. "
                    + "Hyphens may be used to separate words (e.g., study-group). Spaces are not allowed.";
    private static final String VALIDATION_REGEX = "[A-Za-z0-9][A-Za-z0-9-]*";

    private final String tagName;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        String trimmedTagName = tagName.trim();
        checkArgument(isValidTagName(trimmedTagName), MESSAGE_CONSTRAINTS);

        if (DefaultTagEnum.isDefaultTagName(trimmedTagName)) {
            this.tagName = DefaultTagEnum.fromString(trimmedTagName).getDisplayName();
        } else {
            this.tagName = trimmedTagName;
        }
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) throws NullPointerException {
        requireNonNull(test);
        String trimmedTagName = test.trim();
        return !trimmedTagName.isEmpty() && trimmedTagName.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the display name of the tag.
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * Returns true if this tag is one of the built-in default tags.
     */
    public boolean isBuiltInTag() {
        return DefaultTagEnum.isDefaultTagName(tagName);
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
        return tagName.equals(otherTag.tagName);
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

}
