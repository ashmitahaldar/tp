package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIELD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;


import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Field;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Order;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {
    private static final Field defaultField = new Field("name");
    private static final Order defaultOrder = new Order("asc");

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FIELD, PREFIX_ORDER);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_FIELD, PREFIX_ORDER);

        // default order
        Field field = defaultField;
        Order order = defaultOrder;

        if (argMultimap.getValue(PREFIX_FIELD).isPresent()) {
            field = ParserUtil.parseField(argMultimap.getValue(PREFIX_FIELD).get());
        }

        if (argMultimap.getValue(PREFIX_ORDER).isPresent()) {
            order = ParserUtil.parseOrder(argMultimap.getValue(PREFIX_ORDER).get());
        }

        return new SortCommand(field, order);
    }

}
