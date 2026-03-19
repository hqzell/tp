package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.model.person.Person;

public class ListCommandParserTest {

    private static final Comparator<Person> DUMMY_COMPARATOR = (p1, p2) -> 0;

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_validArgs_returnsListCommand() {
        assertParseSuccess(parser, "", new ListCommand());
        assertParseSuccess(parser, "   ", new ListCommand());
        // equals() only compares field name, so a dummy comparator suffices
        assertParseSuccess(parser, " s/name", new ListCommand("name", DUMMY_COMPARATOR));
        assertParseSuccess(parser, " s/room", new ListCommand("room", DUMMY_COMPARATOR));
        assertParseSuccess(parser, " s/phone", new ListCommand("phone", DUMMY_COMPARATOR));
        assertParseSuccess(parser, " s/email", new ListCommand("email", DUMMY_COMPARATOR));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " 3", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " random text", String
                .format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " s/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " some preamble s/name",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " s/invalid", "Invalid sort field! Supported fields: name, room, phone, email");
    }
}
