package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Comment;
import seedu.address.model.person.Person;

// Solution below adapted from https://se-education.org/

/**
 * Changes the comment of an existing person in the address book.
 */
public class CommentCommand extends Command {
    public static final String COMMAND_WORD = "comment";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the comment of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing comment will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "c/ [COMMENT]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "c/ Likes to swim.";

    public static final String MESSAGE_ADD_COMMENT_SUCCESS = "Added comment to Person: %1$s";
    public static final String MESSAGE_DELETE_COMMENT_SUCCESS = "Removed comment from Person: %1$s";
    public static final String MESSAGE_UPDATE_COMMENT_SUCCESS = "Updated comment for Person: %1$s";
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Comment: %2$s";
    private static final Logger logger = LogsCenter.getLogger(CommentCommand.class);
    private final Index index;
    private final Comment comment;

    /**
     * @param index of the person in the filtered person list to edit
     * @param comment of the person to be updated to
     */
    public CommentCommand(Index index, Comment comment) {
        requireAllNonNull(index, comment);

        this.index = index;
        this.comment = comment;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        logger.fine("EditComment executed for index: " + index.getZeroBased());

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getRoom(), comment, personToEdit.getTags());

        model.setPerson(personToEdit, editedPerson);

        logger.fine("EditComment completed for index: " + index.getZeroBased());
        return new CommandResult(generateSuccessMessage(personToEdit, editedPerson));
    }

    /**
     * Generates a command execution success message based on the previous
     * and new comment on {@code personBeforeEdit}.
     */
    private String generateSuccessMessage(Person personBeforeEdit, Person editedPerson) {
        boolean oldCommentEmpty = personBeforeEdit.getComment().value.isEmpty();
        boolean newCommentEmpty = editedPerson.getComment().value.isEmpty();
        String message;
        if (newCommentEmpty) {
            message = MESSAGE_DELETE_COMMENT_SUCCESS;
        } else if (oldCommentEmpty) {
            message = MESSAGE_ADD_COMMENT_SUCCESS;
        } else {
            message = MESSAGE_UPDATE_COMMENT_SUCCESS;
        }
        return String.format(message, Messages.format(editedPerson));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommentCommand)) {
            return false;
        }

        CommentCommand e = (CommentCommand) other;
        return index.equals(e.index) && comment.equals(e.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index.getZeroBased(), comment);
    }
}
