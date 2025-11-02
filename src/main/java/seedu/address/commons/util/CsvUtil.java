package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonBuilder;

/**
 * Converts a Java object instance to CSV and vice versa
 */
public class CsvUtil {

    private static final Logger logger = LogsCenter.getLogger(CsvUtil.class);

    /**
     * Imports a .csv file
     *
     * @param filepath Filepath which the command should operate on.
     * @return feedback message of the import for display
     * @throws IOException if an invalid filepath was specified
     */
    private static ReadOnlyAddressBook deserializeAddressBookFromCsvFile(Path filepath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filepath.toFile()));
        AddressBook addressBook = new AddressBook();
        boolean isParsing = true;
        while (isParsing) {
            String csvLine = reader.readLine();
            if (csvLine == null) {
                isParsing = false;
            } else {
                try {
                    Person newPerson = createNewPerson(csvLine);
                    addressBook.addPerson(newPerson);
                } catch (IllegalValueException e) {
                    throw new IOException(e.getMessage());
                }
            }
        }
        return addressBook;
    }

    private static void serializeObjectToCsvFile(Path filePath, ReadOnlyAddressBook csvFile) throws IOException {
        PrintWriter printWriter = new PrintWriter(filePath.toFile());
        csvFile.getPersonList().forEach((Person person) -> {
            StringBuilder fieldsString = new StringBuilder();
            person.getFields().forEach((String s) -> {
                fieldsString.append("\"" + s.replaceAll("\"", "\"\"") + "\"");
                fieldsString.append(',');
            });
            fieldsString.deleteCharAt(fieldsString.length() - 1); // remove trailing comma
            printWriter.println(fieldsString);
        });
        printWriter.close();
    }

    /**
     * Returns the csv object from the given file or {@code Optional.empty()} object if the file is not found.
     * If any values are missing from the file, default values will be used, as long as the file is a valid csv file.
     *
     * @param filePath cannot be null.
     * @throws DataLoadingException if loading of the csv file failed.
     */
    public static Optional<ReadOnlyAddressBook> readCsvFile(
            Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            return Optional.empty();
        }
        logger.info("CSV file " + filePath + " found.");

        ReadOnlyAddressBook csvFile;

        try {
            csvFile = deserializeAddressBookFromCsvFile(filePath);
        } catch (IOException e) {
            logger.warning("Error reading from csvFile file " + filePath + ": " + e);
            throw new DataLoadingException(e);
        }

        return Optional.of(csvFile);
    }

    /**
     * Saves the Csv object to the specified file.
     * Overwrites existing file if it exists, creates a new file if it doesn't.
     * @param csvFile cannot be null
     * @param filePath cannot be null
     * @throws IOException if there was an error during writing to the file
     */
    public static void saveCsvFile(ReadOnlyAddressBook csvFile, Path filePath) throws IOException {
        requireNonNull(filePath);
        requireNonNull(csvFile);

        serializeObjectToCsvFile(filePath, csvFile);
    }

    /**
     * Creates a person based on the input comma-separated string.
     *
     * @param inputString A comma-separated string of text.
     * @return the person specified in the string.
     */
    private static Person createNewPerson(String inputString) throws ParseException {
        requireNonNull(inputString);

        PersonBuilder builder = new PersonBuilder();

        String[] fieldArray = inputString.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        for (String s : fieldArray) {
            addFieldToPerson(builder, s.substring(1, s.length() - 1).replace("\"\"", "\""));
        }

        return builder.buildPerson();
    }

    private static void addFieldToPerson(PersonBuilder builder, String s) throws ParseException {
        switch (s.substring(0, s.indexOf(":"))) {
        case "name":
            builder.setName(s.substring(s.indexOf(":") + 1));
            break;
        case "phone":
            builder.setPhone(s.substring(s.indexOf(":") + 1));
            break;
        case "address":
            builder.setAddress(s.substring(s.indexOf(":") + 1));
            break;
        case "email":
            builder.setEmail(s.substring(s.indexOf(":") + 1));
            break;
        case "telegram":
            if (s.length() >= s.indexOf(":") + 2) {
                builder.setTelegramHandle(s.substring(s.indexOf(":") + 1));
            }
            break;
        case "tag":
            builder.setTag(s.substring(s.indexOf(":") + 2, s.length() - 1));
            break;
        case "note":
            builder.setNote(s.substring(s.indexOf(":") + 1));
            break;
        case "isPinned":
            builder.setPinned(s.substring(s.indexOf(":") + 1));
            break;
        case "log":
            String logTime = s.substring(s.indexOf('[') + 1, s.indexOf(']'));
            String logType = s.substring(s.indexOf('(') + 1, s.indexOf(')'));
            String logData = s.substring(s.indexOf(')') + 1);
            builder.setLog(logTime, logType, logData);
            break;
        default:
            throw new ParseException("Unknown tag encountered");
        }
    }
}
