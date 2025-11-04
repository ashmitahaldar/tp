package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final TelegramHandle telegramHandle;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Note note;
    private final InteractionLog logs;
    private final boolean isPinned;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, TelegramHandle telegramHandle, Email email,
                  Address address, Set<Tag> tags, Note note, InteractionLog logs) {
        requireAllNonNull(name, phone, telegramHandle, email, address, tags, note, logs);
        this.name = name;
        this.phone = phone;
        this.telegramHandle = telegramHandle;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.note = note;
        this.logs = logs;
        this.isPinned = false;
    }

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, TelegramHandle telegramHandle, Email email,
                  Address address, Set<Tag> tags, Note note, InteractionLog logs, boolean isPinned) {
        requireAllNonNull(name, phone, telegramHandle, email, address, tags, note, logs);
        this.name = name;
        this.phone = phone;
        this.telegramHandle = telegramHandle;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.note = note;
        this.logs = logs;
        this.isPinned = isPinned;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public TelegramHandle getTelegramHandle() {
        return telegramHandle;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Note getNote() {
        return note;
    }

    public InteractionLog getLogs() {
        return logs;
    }

    public boolean isPinned() {
        return isPinned;
    }

    /**
     * Returns true if both persons have the same name and same phone number.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().toString().equalsIgnoreCase(getName().toString())
                && otherPerson.getPhone().equals(getPhone());
    }

    /**
     * Retrieve all fields related to the person
     * Each element of the ArrayList will be of the form "[FIELD_NAME]:[FIELD VALUE]"
     * @return Field name and field details
     */
    public ArrayList<String> getFields() {
        ArrayList<String> fieldList = new ArrayList<>();
        fieldList.add("name:" + this.name);
        fieldList.add("phone:" + this.phone);
        fieldList.add("address:" + this.address);
        fieldList.add("email:" + this.email);
        fieldList.add("telegram:" + this.telegramHandle);
        tags.forEach((Tag t) -> {
            fieldList.add("tag:" + t);
        });
        fieldList.add("isPinned:" + this.isPinned);
        logs.getLogs().forEach((LogEntry l) -> {
            fieldList.add("log:" + l);
        });
        fieldList.add("note:" + this.note);
        return fieldList;
    }

    /**
     * Checks if the person has the specified tag
     * @param tag The tag to be checked
     * @return True if the person has the tag
     */
    public boolean hasTag(Tag tag) {
        return tags.contains(tag);
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
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && telegramHandle.equals(otherPerson.telegramHandle)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags)
                && note.equals(otherPerson.note)
                && logs.equals(otherPerson.logs)
                && isPinned == otherPerson.isPinned;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, telegramHandle, email, address, tags, note, logs, isPinned);
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
                .add("note", note)
                .add("logs", logs)
                .toString();
    }

}
