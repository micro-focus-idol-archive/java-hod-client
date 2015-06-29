package com.hp.autonomy.hod.client.api.resource;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ResourceIdentifierTest {
    @Test
    public void toStringWithNoSpecialCharacters() {
        testToString("animals", "cat", "animals:cat");
    }

    @Test
    public void toStringWithOneColon() {
        testToString("animals", "chordates:cat", "animals:chordates\\:cat");
    }

    @Test
    public void toStringWithTwoColons() {
        testToString("animals:chordates:cat", "lion", "animals\\:chordates\\:cat:lion");
    }

    @Test
    public void toStringWithTwoAdjacentColons() {
        testToString("c++", "std::array", "c++:std\\:\\:array");
    }

    @Test
    public void toStringWithOneBackslash() {
        testToString("directories", "home\\matthew", "directories:home\\\\matthew");
    }

    @Test
    public void toStringWithTwoBackslashes() {
        testToString("\\home\\matthew", "file1", "\\\\home\\\\matthew:file1");
    }

    @Test
    public void toStringWithTwoAdjacentBackslashes() {
        testToString("punctuation", "\\\\", "punctuation:\\\\\\\\");
    }

    @Test
    public void toStringWithOneColonAndOneBackslash() {
        testToString("sentence", "here is a colon : and here is a backslash \\", "sentence:here is a colon \\: and here is a backslash \\\\");
    }

    @Test
    public void toStringWithManyColonsAndBackslashes() {
        testToString("\\:aaaaaaa\\\\::colons::::", "maaaa:dn\\ess", "\\\\\\:aaaaaaa\\\\\\\\\\:\\:colons\\:\\:\\:\\::maaaa\\:dn\\\\ess");
    }

    @Test
    public void toStringWithDifferentCases() {
        testToString("Ca:SinG", "FunKY\\:CaSE", "Ca\\:SinG:FunKY\\\\\\:CaSE");
    }

    @Test
    public void toStringWithNonLatinCharacters() {
        testToString("!latin", "𡁜\\𡃁:𢞵𦟌", "!latin:𡁜\\\\𡃁\\:𢞵𦟌");
    }

    private void testToString(final String domain, final String name, final String expected) {
        final ResourceIdentifier identifier = new ResourceIdentifier(domain, name);
        assertThat(identifier.toString(), is(expected));
    }
}
