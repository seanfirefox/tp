package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.TypicalPersons.ANG;
import static seedu.address.testutil.TypicalPersons.BART;
import static seedu.address.testutil.TypicalPersons.CLARK;
import static seedu.address.testutil.TypicalPersons.DAWSON;
import static seedu.address.testutil.TypicalPersons.EDWARD;
import static seedu.address.testutil.TypicalPersons.FORD;
import static seedu.address.testutil.TypicalPersons.getTypicalEduMate;
import static seedu.address.testutil.TypicalUser.LINUS;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


public class ViewCommandTest {

    private final Model model = new ModelManager(getTypicalEduMate(), new UserPrefs());

    @Test
    public void execute_byIndex_correctPersonQueried() throws CommandException {
        ViewCommand command1 = new ViewCommand(null, Index.fromZeroBased(1));
        CommandResult commandResult1 = command1.execute(model);
        assertEquals(ANG, commandResult1.getDisplayPerson());

        ViewCommand command2 = new ViewCommand(null, Index.fromZeroBased(2));
        CommandResult commandResult2 = command2.execute(model);
        assertEquals(BART, commandResult2.getDisplayPerson());

        ViewCommand command3 = new ViewCommand(null, Index.fromZeroBased(3));
        CommandResult commandResult3 = command3.execute(model);
        assertEquals(CLARK, commandResult3.getDisplayPerson());
    }

    @Test
    public void execute_byName_correctPersonQueried() throws CommandException {
        ViewCommand command1 = new ViewCommand("Dawson Quentin", null);
        ViewCommand command2 = new ViewCommand("Edward Richards", null);
        ViewCommand command3 = new ViewCommand("Ford Canning", null);

        CommandResult result1 = command1.execute(model);
        CommandResult result2 = command2.execute(model);
        CommandResult result3 = command3.execute(model);

        assertEquals(DAWSON, result1.getDisplayPerson());
        assertEquals(EDWARD, result2.getDisplayPerson());
        assertEquals(FORD, result3.getDisplayPerson());
        assertNotEquals("No such name found!", result1.getFeedbackToUser());
    }

    @Test
    public void execute_noArguments_userQueried() throws CommandException {
        ViewCommand command = new ViewCommand(null, null);
        CommandResult result = command.execute(model);
        assertEquals(LINUS, result.getDisplayPerson());
    }

    @Test
    public void execute_noSuchName_userProfileDisplayed() throws CommandException {
        ViewCommand command1 = new ViewCommand("Lagrange", null);
        ViewCommand command2 = new ViewCommand("Alan Turing", null);
        ViewCommand command3 = new ViewCommand("Pierre de Fermat", null);

        CommandResult result1 = command1.execute(model);
        CommandResult result2 = command2.execute(model);
        CommandResult result3 = command3.execute(model);

        assertEquals(LINUS, result1.getDisplayPerson());
        assertEquals(LINUS, result2.getDisplayPerson());
        assertEquals(LINUS, result3.getDisplayPerson());
    }

    @Test
    public void execute_noSuchName_invalidMessageDisplayed() throws CommandException {
        ViewCommand command1 = new ViewCommand("Bolzano Weierstrass", null);
        ViewCommand command2 = new ViewCommand("Tony Hoare", null);
        ViewCommand command3 = new ViewCommand("John Nash", null);

        CommandResult result1 = command1.execute(model);
        CommandResult result2 = command2.execute(model);
        CommandResult result3 = command3.execute(model);

        assertEquals("No such name found!", result1.getFeedbackToUser());
        assertEquals("No such name found!", result2.getFeedbackToUser());
        assertEquals("No such name found!", result3.getFeedbackToUser());
    }

}
