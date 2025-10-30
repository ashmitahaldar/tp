package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MESSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.LogCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code LogCommand} object.
 */
public class LogCommandParser implements Parser<LogCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code LogCommand}
     * and returns a {@code LogCommand} object for execution.
     *
     * @param args full command arguments as entered by the user
     * @return a {@code LogCommand} built from parsed arguments
     * @throws ParseException if the user input does not conform to the expected format
     */
    public LogCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_MESSAGE, PREFIX_TYPE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    LogCommand.MESSAGE_USAGE), ive);
        }

        // Message is required
        if (!argMultimap.getValue(PREFIX_MESSAGE).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    LogCommand.MESSAGE_USAGE));
        }

        String message = argMultimap.getValue(PREFIX_MESSAGE).get().trim();
        if (message.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    LogCommand.MESSAGE_USAGE));
        }

        // Type is optional, defaults to empty string
        String type = argMultimap.getValue(PREFIX_TYPE).orElse("").trim();

        return new LogCommand(index, message, type);
    }
}
