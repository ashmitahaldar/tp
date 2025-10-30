package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.InteractionLog;
import seedu.address.model.person.LogEntry;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.TelegramHandle;
import seedu.address.model.tag.Tag;

/**
 * Csv-friendly version of {@link Person}.
 */
class CsvAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String telegram;
    private final String email;
    private final String address;
    private final String note;
    private final List<CsvAdaptedTag> tags = new ArrayList<>();
    private final List<CsvAdaptedLogEntry> logs = new ArrayList<>();
    private final boolean isPinned;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    public CsvAdaptedPerson(String name, String phone,
                            String telegram,
                            String email, String address,
                            List<CsvAdaptedTag> tags, String note,
                            List<CsvAdaptedLogEntry> logs,
                            Boolean isPinned) {
        this.name = name;
        this.phone = phone;
        this.telegram = telegram;
        this.email = email;
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.note = note;
        if (logs != null) {
            this.logs.addAll(logs);
        }
        this.isPinned = isPinned != null ? isPinned : false;
    }

    /**
     * Converts a given {@code Person} into this class for csv use.
     */
    public CsvAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        telegram = source.getTelegramHandle().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tags.addAll(source.getTags().stream()
                .map(CsvAdaptedTag::new)
                .collect(Collectors.toList()));
        note = source.getNote().value;
        logs.addAll(source.getLogs().getLogs().stream()
                .map(CsvAdaptedLogEntry::new)
                .collect(Collectors.toList()));
        isPinned = source.isPinned();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (telegram == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TelegramHandle.class.getSimpleName()));
        }
        if (!TelegramHandle.isValidHandle(telegram)) {
            throw new IllegalValueException(TelegramHandle.MESSAGE_CONSTRAINTS);
        }
        final TelegramHandle modelTelegramHandle = new TelegramHandle(telegram);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Set<Tag> modelTags = new HashSet<>(personTags);

        if (note == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Note.class.getSimpleName()));
        }
        final Note modelNote = new Note(note);

        final List<LogEntry> logEntries = new ArrayList<>();
        for (JsonAdaptedLogEntry log : logs) {
            logEntries.add(log.toModelType());
        }
        final InteractionLog modelLogs = new InteractionLog(logEntries);

        return new Person(modelName, modelPhone, modelTelegramHandle,
                modelEmail, modelAddress, modelTags, modelNote, modelLogs, isPinned);
    }

}
