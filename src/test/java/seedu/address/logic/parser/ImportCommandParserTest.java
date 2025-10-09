package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.FILEPATH_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FILEPATH;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILEPATH_JOHN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.ImportCommand;

public class ImportCommandParserTest {
    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_filepathPresent_success() {
        // whitespace only preamble
        try {
            Path correctFilePath = ParserUtil.parsePath(VALID_FILEPATH_JOHN);
            assertParseSuccess(parser, PREAMBLE_WHITESPACE + FILEPATH_JOHN,
                    new ImportCommand(correctFilePath));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void parse_repeatedValue_failure() {
        // file field repeated
        assertParseFailure(parser, FILEPATH_JOHN + FILEPATH_JOHN,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_FILE));

        // invalid value followed by valid value
        assertParseFailure(parser, INVALID_FILEPATH + FILEPATH_JOHN,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_FILE));

        // valid value followed by invalid value
        assertParseFailure(parser, FILEPATH_JOHN + INVALID_FILEPATH,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_FILE));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE);

        // missing filepath prefix
        assertParseFailure(parser, VALID_FILEPATH_JOHN,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + INVALID_FILEPATH,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
    }
}
