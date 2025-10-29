package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FIELD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Field;
import seedu.address.model.person.Order;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {
    private static final Field DEFAULT_FIELD = new Field("name");
    private static final Order DEFAULT_ORDER = new Order("asc");

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FIELD, PREFIX_ORDER);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_FIELD, PREFIX_ORDER);

        // only allow empty preamble, user should not input other text
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format("Invalid command format! Usage: %s", SortCommand.MESSAGE_USAGE));
        }

        // default order
        Field field = DEFAULT_FIELD;
        Order order = DEFAULT_ORDER;

        if (argMultimap.getValue(PREFIX_FIELD).isPresent()) {
            field = ParserUtil.parseField(argMultimap.getValue(PREFIX_FIELD).get());
        }

        if (argMultimap.getValue(PREFIX_ORDER).isPresent()) {
            order = ParserUtil.parseOrder(argMultimap.getValue(PREFIX_ORDER).get());
        }

        return new SortCommand(field, order);
    }

}
