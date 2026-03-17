package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Comparator;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all residents in the address book.\n"
            + "Parameters: [s/FIELD]\n"
            + "Supported fields: name, room, phone, email\n"
            + "Example: " + COMMAND_WORD + " s/room";

    public static final String MESSAGE_SUCCESS = "Listed all residents";
    public static final String MESSAGE_SUCCESS_SORTED = "Listed all residents sorted by %1$s";

    private final String field;
    private final Comparator<Person> comparator;

    public ListCommand() {
        this.field = null;
        this.comparator = null;
    }

    public ListCommand(String field, Comparator<Person> comparator) {
        this.field = field;
        this.comparator = comparator;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (comparator != null) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS, comparator);
            return new CommandResult(String.format(MESSAGE_SUCCESS_SORTED, field));
        } else {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ListCommand)) {
            return false;
        }

        ListCommand otherListCommand = (ListCommand) other;
        boolean fieldEquals = (field == null && otherListCommand.field == null)
                || (field != null && field.equals(otherListCommand.field));
        
        return fieldEquals;
    }
}
