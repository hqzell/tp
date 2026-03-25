package seedu.address.model.tag;

import java.util.logging.Logger;

/**
 * Enumeration of allowed tag types.
 */
public enum TagType {
    VEGETARIAN("Vegetarian"),
    VEGAN("Vegan"),
    HALAL("Halal"),
    ALLERGIES("Allergies");

    private static final Logger logger = Logger.getLogger(TagType.class.getName());

    private final String displayName;

    TagType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Converts a string to a TagType (case-insensitive).
     */
    public static TagType fromString(String input) {
        assert input != null : "Tag input should not be null";
        String normalized = input.trim();
        logger.fine("Parsing tag: [" + normalized + "]");

        for (TagType type : TagType.values()) {
            if (type.displayName.equalsIgnoreCase(input.trim())) {
                return type;
            }
        }
        logger.warning("Invalid tag attempted: [" + input + "]");
        throw new IllegalArgumentException(
                 "Tags must be one of the following: Vegetarian, Vegan, Halal, Allergies");
    }
}
