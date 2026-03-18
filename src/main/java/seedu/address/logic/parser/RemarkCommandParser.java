package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Remark;

// Solution below adapted from https://se-education.org/guides/tutorials/ab3AddRemark.html
/**
 * Parses input arguments and creates a new RemarkCommand object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {
    @Override
    public RemarkCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_REMARK);

        String invalidCommandFormatMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(invalidCommandFormatMessage, pe);
        }

        // When the remark command is used, the user must provide a r/ arg
        String remarkText = argMultimap.getValue(PREFIX_REMARK)
                .orElseThrow(() -> new ParseException(invalidCommandFormatMessage));
        Remark remark = new Remark(remarkText);

        return new RemarkCommand(index, remark);
    }
}
