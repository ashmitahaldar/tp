package seedu.address.model.person;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        int threshold = 2; // adjust for fuzziness
        String[] nameWords = person.getName().fullName.split("\\s+");
        return keywords.stream()
                .anyMatch(keyword ->
                        Arrays.stream(nameWords).anyMatch(word ->
                                matchesKeyword(word, keyword, threshold)
                        ));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordsPredicate)) {
            return false;
        }

        NameContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (NameContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }

    private boolean matchesKeyword(String word, String keyword, int threshold) {
        String trimmed = keyword.trim();
        if (trimmed.isEmpty()) {
            return false;
        }

        // For short keywords (length < 3) do NOT perform substring matching to avoid noisy matches.
        if (trimmed.length() < 3) {
            return StringUtil.containsWordIgnoreCase(word, trimmed)
                    || StringUtil.fuzzyMatch(word, trimmed, threshold);
        }

        // For longer keywords allow substring matching in addition to full-word and fuzzy matches.
        return StringUtil.containsWordIgnoreCase(word, trimmed)
                || StringUtil.containsSubstringIgnoreCase(word, trimmed)
                || StringUtil.fuzzyMatch(word, trimmed, threshold);
    }
}
