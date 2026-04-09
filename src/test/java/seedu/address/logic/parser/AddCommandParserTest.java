package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROOM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NEWTAG_FLAG;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.ROOM_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ROOM_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_ALLERGIES;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HALAL;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_STUDY_GROUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROOM_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ALLERGIES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HALAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_STUDY_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Room;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_HALAL).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ROOM_DESC_BOB + TAG_DESC_HALAL, new AddCommand(expectedPerson));


        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_HALAL, VALID_TAG_ALLERGIES)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ROOM_DESC_BOB + TAG_DESC_ALLERGIES + TAG_DESC_HALAL,
                new AddCommand(expectedPersonMultipleTags));

        Person expectedPersonWithNewCustomTag = new PersonBuilder(BOB).withTags(VALID_TAG_STUDY_GROUP).build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ROOM_DESC_BOB + TAG_DESC_STUDY_GROUP + NEWTAG_FLAG,
                new AddCommand(expectedPersonWithNewCustomTag, true));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ROOM_DESC_BOB + TAG_DESC_HALAL;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple rooms
        assertParseFailure(parser, ROOM_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROOM));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + ROOM_DESC_AMY
                        + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ROOM, PREFIX_EMAIL, PREFIX_PHONE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid room
        assertParseFailure(parser, INVALID_ROOM_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROOM));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid room
        assertParseFailure(parser, validExpectedPersonString + INVALID_ROOM_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROOM));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // missing tags
        Person expectedPersonWithoutTags = new PersonBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ROOM_DESC_AMY,
                new AddCommand(expectedPersonWithoutTags));

        // missing phone
        Person expectedPersonWithoutPhone = new PersonBuilder()
                .withName(VALID_NAME_BOB)
                .withEmail(VALID_EMAIL_BOB)
                .withRoom(VALID_ROOM_BOB)
                .withPhone(AddCommandParser.DEFAULT_PHONE)
                .withTags()
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + EMAIL_DESC_BOB + ROOM_DESC_BOB,
                new AddCommand(expectedPersonWithoutPhone));

        // missing email
        Person expectedPersonWithoutEmail = new PersonBuilder()
                .withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withRoom(VALID_ROOM_BOB)
                .withEmail(AddCommandParser.DEFAULT_EMAIL)
                .withTags()
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + ROOM_DESC_BOB,
                new AddCommand(expectedPersonWithoutEmail));

        // missing phone and email
        Person expectedPersonWithoutPhoneAndEmail = new PersonBuilder()
                .withName(VALID_NAME_BOB)
                .withRoom(VALID_ROOM_BOB)
                .withPhone(AddCommandParser.DEFAULT_PHONE)
                .withEmail(AddCommandParser.DEFAULT_EMAIL)
                .withTags()
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + ROOM_DESC_BOB, new AddCommand(expectedPersonWithoutPhoneAndEmail));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String genericFormatMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // name given without n/ prefix (parsed as preamble, not as name)
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ROOM_DESC_BOB,
                genericFormatMessage);

        // missing room prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ROOM_BOB,
                AddCommand.MESSAGE_MISSING_ROOM);

        // no recognized prefixes (entire input is preamble)
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ROOM_BOB,
                genericFormatMessage);

        // n/ absent but other prefixes present (empty preamble)
        assertParseFailure(parser, PHONE_DESC_BOB + EMAIL_DESC_BOB + ROOM_DESC_BOB,
                AddCommand.MESSAGE_MISSING_NAME);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ROOM_DESC_BOB
                + TAG_DESC_ALLERGIES + TAG_DESC_HALAL, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ROOM_DESC_BOB
                + TAG_DESC_ALLERGIES + TAG_DESC_HALAL, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ROOM_DESC_BOB
                + TAG_DESC_ALLERGIES + TAG_DESC_HALAL, Email.MESSAGE_CONSTRAINTS);

        // invalid room
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ROOM_DESC
                + TAG_DESC_ALLERGIES + TAG_DESC_HALAL, Room.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ROOM_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_HALAL, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ROOM_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ROOM_DESC_BOB + TAG_DESC_ALLERGIES + TAG_DESC_HALAL,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // invalid newtag usage
        assertParseFailure(parser, NAME_DESC_BOB + ROOM_DESC_BOB + NEWTAG_FLAG + " unexpected",
                AddCommand.MESSAGE_NEWTAG_FLAG_TAKES_NO_VALUE);

        // duplicate newtag flag - valid and should be treated as a single flag
        assertParseSuccess(parser, NAME_DESC_BOB + ROOM_DESC_BOB + TAG_DESC_STUDY_GROUP + NEWTAG_FLAG + NEWTAG_FLAG,
                new AddCommand(new PersonBuilder().withName(VALID_NAME_BOB).withRoom(VALID_ROOM_BOB)
                        .withTags(VALID_TAG_STUDY_GROUP).withPhone("").withEmail("").build(), true));

        // multiple duplicate newtag flags followed by text - invalid
        assertParseFailure(parser,
                NAME_DESC_BOB + ROOM_DESC_BOB + NEWTAG_FLAG + NEWTAG_FLAG + NEWTAG_FLAG + " randomtext",
                AddCommand.MESSAGE_NEWTAG_FLAG_TAKES_NO_VALUE);
    }
}
