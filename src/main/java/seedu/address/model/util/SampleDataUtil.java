package seedu.address.model.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
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
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static final Note EMPTY_NOTE = new Note();
    public static final InteractionLog EMPTY_LOGS = new InteractionLog();

    public static Person[] getSamplePersons() {
        InteractionLog sampleLogs = new InteractionLog()
                .addLogEntry(new LogEntry("Met to discuss CS2103T", "meeting",
                LocalDateTime.parse("2025-09-30T20:45:00")))
                .addLogEntry(new LogEntry("Met to discuss CS2103T", "meeting"));
        return new Person[] {
            new Person(new Name("Queensway Shirt Vendor"), new Phone("97110393"), new TelegramHandle("@queenshirts"),
                    new Email("qs@gmail.com"), new Address("Queensway Shopping Centre"),
                    getTagSet("Shirt"), EMPTY_NOTE, EMPTY_LOGS),
            new Person(new Name("Riley Tan"), new Phone("93838383"), new TelegramHandle("@rileyy"),
                    new Email("osa@nus.sg"), new Address("123 Serangoon Road"),
                    getTagSet("Coordinator"), new Note("Approves of NUSync events."), EMPTY_LOGS),
            new Person(new Name("Sarah Tan"), new Phone("91234567"), new TelegramHandle("@sarahtan_nus"),
                    new Email("sarah.tan@u.nus.edu"), new Address("21 Lower Kent Ridge Road, #12-08"),
                    getTagSet("President", "ComputingClub"), EMPTY_NOTE, sampleLogs),
            new Person(new Name("Rajesh Kumar"), new Phone("92345678"), new TelegramHandle("@rajesh_vp"),
                    new Email("rajesh.k@u.nus.edu"), new Address("35 Prince George's Park, #05-12"),
                    getTagSet("VicePresident", "BusinessClub"),
                    new Note("Can't stop petting Snowy (my cat)."), sampleLogs),
            new Person(new Name("Emily Wong"), new Phone("93456789"), new TelegramHandle("@emilywong"),
                    new Email("emily.wong@u.nus.edu"), new Address("119 Clementi Road, #07-03"),
                    getTagSet("Treasurer", "DanceTeam"), EMPTY_NOTE, EMPTY_LOGS),
            new Person(new Name("Marcus Lim"), new Phone("94567890"), new TelegramHandle("@marcuslim_sec"),
                    new Email("marcus.lim@u.nus.edu"), new Address("82 Jurong West Stree 65 , #10-15"),
                    getTagSet("Secretary", "DebateSociety"), EMPTY_NOTE, EMPTY_LOGS),
            new Person(new Name("Stationary Shop at KR"), new Phone("84567890"), new TelegramHandle("@lisastation"),
                    new Email("lisa.stationary@mail.com"), new Address("10 Kent Ridge Crescent, #B1-03"),
                    getTagSet("Vendor", "Stationery"), EMPTY_NOTE, EMPTY_LOGS),
            new Person(new Name("Uncle Tan"), new Phone("81234567"), new TelegramHandle("@uncletanfood"),
                    new Email("tanfood@gmail.com"), new Address("2 Science Drive 2, #01-01"),
                    getTagSet("Vendor", "FavouriteCanteen"), EMPTY_NOTE, EMPTY_LOGS),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
