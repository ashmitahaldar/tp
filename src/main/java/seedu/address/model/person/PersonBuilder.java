package seedu.address.model.person;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Builds a Person for the address book without requiring all parameters at once.
 */
public class PersonBuilder {

    // Identity fields
    private Name name;
    private Phone phone;
    private Email email;
    private TelegramHandle telegramHandle;

    // Data fields
    private Address address;
    private Set<Tag> tags = new HashSet<>();
    private Note note;
    private boolean isPinned;
    private InteractionLog interactionLog;

    /**
     * Initializes a person builder
     */
    public PersonBuilder() {
        // initialize optional fields
        this.telegramHandle = new TelegramHandle("");
        // initialize collection of logs
        this.interactionLog = new InteractionLog();
    }

    public void setName(String name) {
        this.name = new Name(name);
    }

    public void setPhone(String phone) {
        this.phone = new Phone(phone);
    }

    public void setTelegramHandle(String telegramHandle) {
        this.telegramHandle = new TelegramHandle(telegramHandle);
    }

    public void setEmail(String email) {
        this.email = new Email(email);
    }

    public void setAddress(String address) {
        this.address = new Address(address);
    }

    public void setTag(String tag) {
        tags.add(new Tag(tag));
    }

    public void setNote(String note) {
        this.note = new Note(note);
    }

    public void setPinned(String isPinned) {
        this.isPinned = isPinned.equals("true");
    }

    // add log to log collection
    public void setLog(String log, String type) {
        this.interactionLog.addLogEntry(new LogEntry(log, type));
    }

    /**
     * Build person using set parameters
     * @return person with specified parameters
     */
    public Person buildPerson() {
        return new Person(name, phone, telegramHandle, email, address, tags, note, interactionLog, isPinned);
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonBuilder)) {
            return false;
        }

        PersonBuilder otherPerson = (PersonBuilder) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && telegramHandle.equals(otherPerson.telegramHandle)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags)
                && interactionLog.equals(otherPerson.interactionLog)
                && note.equals(otherPerson.note)
                && isPinned == otherPerson.isPinned;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, telegramHandle, email, address, tags, note, interactionLog, isPinned);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("telegram", telegramHandle)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("isPinned", isPinned)
                .add("log", interactionLog)
                .add("note", note)
                .toString();
    }

}
