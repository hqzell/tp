package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes one or more persons identified by index numbers used in the displayed person list.\n"
            + "Parameters: INDEX[,INDEX]... (each index must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1,3,5";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_DELETE_PERSONS_SUCCESS = "Deleted Persons:\n%1$s";

    private final List<Index> targetIndices;

    /**
     * Creates a DeleteCommand to delete one or more persons at the given indices.
     */
    public DeleteCommand(List<Index> targetIndices) {
        this.targetIndices = List.copyOf(targetIndices);
        assert !this.targetIndices.isEmpty() : "DeleteCommand requires at least one index";
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Set<Integer> seenZeroBasedIndices = new HashSet<>();
        List<Person> personsToDelete = new ArrayList<>();
        for (Index targetIndex : targetIndices) {
            int zeroBasedIndex = targetIndex.getZeroBased();
            if (zeroBasedIndex >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            if (!seenZeroBasedIndices.add(zeroBasedIndex)) {
                continue;
            }
            personsToDelete.add(lastShownList.get(zeroBasedIndex));
        }

        for (Person personToDelete : personsToDelete) {
            model.deletePerson(personToDelete);
        }

        if (personsToDelete.size() == 1) {
            return new CommandResult(
                    String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personsToDelete.get(0))));
        }

        String deletedPersonsMessage = personsToDelete.stream()
                .map(Messages::format)
                .collect(Collectors.joining("\n"));
        return new CommandResult(String.format(MESSAGE_DELETE_PERSONS_SUCCESS, deletedPersonsMessage));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndices.equals(otherDeleteCommand.targetIndices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndices", targetIndices)
                .toString();
    }
}
