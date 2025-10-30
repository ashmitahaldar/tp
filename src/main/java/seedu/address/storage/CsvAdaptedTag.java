package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;

class CsvAdaptedTag {

    private final String tagName;

    /**
     * Constructs a {@code CsvAdaptedTag} with the given {@code tagName}.
     */
    public CsvAdaptedTag(Tag tag) {
        this.tagName = tag.toString().substring(1, tag.toString().length() - 1);
    }

    public String getTagName() {
        return tagName;
    }

    /**
     * Converts this csv-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Tag toModelType() throws IllegalValueException {
        if (!Tag.isValidTagName(tagName)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(tagName);
    }

}
