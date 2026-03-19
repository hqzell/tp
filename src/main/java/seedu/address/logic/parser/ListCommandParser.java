package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCommand object.
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SORT);

        // Reject any preamble text (e.g. "list abc s/name" should fail)
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        // If no sort prefix is provided, return a plain list command
        if (argMultimap.getValue(PREFIX_SORT).isEmpty()) {
            return new ListCommand();
        }

        // Ensure no duplicate sort prefixes (e.g. "list s/name s/room" should fail)
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SORT);

        String field = argMultimap.getValue(PREFIX_SORT).get().trim();
        if (field.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        switch (field.toLowerCase()) {
        case "name":
            return new ListCommand("name", (p1, p2) -> p1.getName().fullName
                    .compareToIgnoreCase(p2.getName().fullName));
        case "room":
            return new ListCommand("room", (p1, p2) -> p1.getRoom().compareTo(p2.getRoom()));
        case "phone":
            return new ListCommand("phone", (p1, p2) -> p1.getPhone().value.compareTo(p2.getPhone().value));
        case "email":
            return new ListCommand("email", (p1, p2) -> p1.getEmail().value
                    .compareToIgnoreCase(p2.getEmail().value));
        default:
            throw new ParseException("Invalid sort field! Supported fields: name, room, phone, email");
        }
    }
}
