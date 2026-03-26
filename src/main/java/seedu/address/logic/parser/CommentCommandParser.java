package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Comment;

// Solution below adapted from https://se-education.org/

/**
 * Parses input arguments and creates a new CommentCommand object
 */
public class CommentCommandParser implements Parser<CommentCommand> {
    private static final Logger logger = LogsCenter.getLogger(CommentCommandParser.class);

    @Override
    public CommentCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_COMMENT);

        String invalidCommandFormatMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CommentCommand.MESSAGE_USAGE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            logger.warning("Failed to parse index: invalid index format");
            throw new ParseException(invalidCommandFormatMessage, pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_COMMENT);

        // When the comment command is used, the user must provide a c/ arg
        String commentText = argMultimap.getValue(PREFIX_COMMENT)
                .orElseThrow(() -> new ParseException(invalidCommandFormatMessage));

        // Treat blank comment input as empty string to allow deletions
        if (commentText.isBlank()) {
            commentText = "";
        }
        Comment comment = new Comment(commentText);

        logger.info("Successfully parsed comment command: index=" + index.getOneBased());
        return new CommentCommand(index, comment);
    }
}
