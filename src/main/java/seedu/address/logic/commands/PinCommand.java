package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Pins an existing person in the address book.
 */
public class PinCommand extends Command {
    public static final String COMMAND_WORD = "pin";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Pins the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_PIN_PERSON_SUCCESS = "Pinned Person: %1$s";

    private final Index targetIndex;
    /**
     * @param targetIndex of the person in the filtered person list to pin
     */

    public PinCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToPin = lastShownList.get(targetIndex.getZeroBased());
        Person editedPerson = new Person(
                personToPin.getName(), personToPin.getPhone(), personToPin.getEmail(),
                personToPin.getAddress(), personToPin.getTags(), true);

        model.setPerson(personToPin, editedPerson);

        return new CommandResult(String.format(MESSAGE_PIN_PERSON_SUCCESS, personToPin));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PinCommand // instanceof handles nulls
                && targetIndex.equals(((PinCommand) other).targetIndex)); // state check
    }
}
