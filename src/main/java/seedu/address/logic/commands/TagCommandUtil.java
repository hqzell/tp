package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NEWTAG;

import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

/**
 * Shared utility methods for commands that work with tags.
 */
final class TagCommandUtil {

    static final String MESSAGE_UNKNOWN_TAGS = "Unknown tag(s): %1$s.\n"
            + "Use %2$s to create new tag(s).\n"
            + "%3$s";

    private TagCommandUtil() {}

    /**
     * Validates that every tag exists unless new-tag creation is enabled.
     */
    static void validateKnownTags(Model model, Set<Tag> tags, boolean shouldCreateNewTags, String usageWithNewTag)
            throws CommandException {
        if (shouldCreateNewTags) {
            return;
        }

        Set<Tag> unknownTags = tags.stream()
                .filter(tag -> !model.hasTag(tag))
                .collect(Collectors.toSet());

        if (!unknownTags.isEmpty()) {
            String unknownTagsString = unknownTags.stream()
                    .map(Tag::getTagName)
                    .sorted(String.CASE_INSENSITIVE_ORDER)
                    .collect(Collectors.joining(", "));
            throw new CommandException(String.format(MESSAGE_UNKNOWN_TAGS,
                    unknownTagsString, PREFIX_NEWTAG, usageWithNewTag));
        }
    }
}
