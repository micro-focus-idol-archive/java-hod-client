/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.resource;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ResourceIdentifierTest {
    @Test
    public void toStringWithNoSpecialCharacters() {
        testToString("animals", "cat", "animals:cat");
    }

    @Test
    public void fromStringWithNoSpecialCharacters() {
        testFromString("animals:cat", "animals", "cat");
    }

    @Test
    public void toStringWithOneColon() {
        testToString("animals", "chordates:cat", "animals:chordates\\:cat");
    }

    @Test
    public void fromStringWithOneColon() {
        testFromString("animals:chordates\\:cat", "animals", "chordates:cat");
    }

    @Test
    public void toStringWithTwoColons() {
        testToString("animals:chordates:cat", "lion", "animals\\:chordates\\:cat:lion");
    }

    @Test
    public void fromStringWithTwoColons() {
        testFromString("animals\\:chordates\\:cat:lion", "animals:chordates:cat", "lion");
    }

    @Test
    public void toStringWithTwoAdjacentColons() {
        testToString("c++", "std::array", "c++:std\\:\\:array");
    }

    @Test
    public void fromStringWithTwoAdjacentColons() {
        testFromString("c++:std\\:\\:array", "c++", "std::array");
    }

    @Test
    public void toStringWithDomainEndingInColon() {
        testToString("domain:", "name", "domain\\::name");
    }

    @Test
    public void fromStringWithDomainEndingInColon() {
        testFromString("domain\\::name", "domain:", "name");
    }

    @Test
    public void toStringWithOneBackslash() {
        testToString("directories", "home\\matthew", "directories:home\\\\matthew");
    }

    @Test
    public void fromStringWithOneBackslash() {
        testFromString("directories:home\\\\matthew", "directories", "home\\matthew");
    }

    @Test
    public void testToStringWithDomainEndingInBackslash() {
        testToString("domain\\", "name", "domain\\\\:name");
    }

    @Test
    public void testFromStringWithDomainEndingInBackslash() {
        testFromString("domain\\\\:name", "domain\\", "name");
    }

    @Test
    public void toStringWithTwoBackslashes() {
        testToString("\\home\\matthew", "file1", "\\\\home\\\\matthew:file1");
    }

    @Test
    public void fromStringWithTwoBackslashes() {
        testFromString("\\\\home\\\\matthew:file1", "\\home\\matthew", "file1");
    }

    @Test
    public void toStringWithTwoAdjacentBackslashes() {
        testToString("punctuation", "\\\\", "punctuation:\\\\\\\\");
    }

    @Test
    public void fromStringWithTwoAdjacentBackslashes() {
        testFromString("punctuation:\\\\\\\\", "punctuation", "\\\\");
    }

    @Test
    public void toStringWithOneColonAndOneBackslash() {
        testToString("sentence", "here is a colon : and here is a backslash \\", "sentence:here is a colon \\: and here is a backslash \\\\");
    }

    @Test
    public void fromStringWithOneColonAndOneBackslash() {
        testFromString("sentence:here is a colon \\: and here is a backslash \\\\", "sentence", "here is a colon : and here is a backslash \\");
    }

    @Test
    public void toStringWithManyColonsAndBackslashes() {
        testToString("\\:aaaaaaa\\\\::colons::::", "maaaa:dn\\ess", "\\\\\\:aaaaaaa\\\\\\\\\\:\\:colons\\:\\:\\:\\::maaaa\\:dn\\\\ess");
    }

    @Test
    public void fromStringWithManyColonsAndBackslashes() {
        testFromString("\\\\\\:aaaaaaa\\\\\\\\\\:\\:colons\\:\\:\\:\\::maaaa\\:dn\\\\ess", "\\:aaaaaaa\\\\::colons::::", "maaaa:dn\\ess");
    }

    @Test
    public void toStringWithDifferentCases() {
        testToString("Ca:SinG", "FunKY\\:CaSE", "Ca\\:SinG:FunKY\\\\\\:CaSE");
    }

    @Test
    public void fromStringWithDifferentCases() {
        testFromString("Ca\\:SinG:FunKY\\\\\\:CaSE", "Ca:SinG", "FunKY\\:CaSE");
    }

    @Test
    public void toStringWithNonLatinCharacters() {
        testToString("!latin", "𡁜\\𡃁:𢞵𦟌", "!latin:𡁜\\\\𡃁\\:𢞵𦟌");
    }

    @Test
    public void fromStringWithNonLatinCharacters() {
        testFromString("!latin:𡁜\\\\𡃁\\:𢞵𦟌", "!latin", "𡁜\\𡃁:𢞵𦟌");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructionWithNullDomainThrowsIllegalArgumentException() {
        new ResourceIdentifier(null, "foo");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructionWithNullNameThrowsIllegalArgumentException() {
        new ResourceIdentifier("foo", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructWithNullIdentifierThrowsIllegalArgumentException() {
        new ResourceIdentifier(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructWithEmptyIdentifierThrowsIllegalArgumentException() {
        new ResourceIdentifier("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructWithInvalidIdentifierThrowsIllegalArgumentException() {
        // Missing name, so invalid identifier
        new ResourceIdentifier("domain:");
    }

    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        final ResourceIdentifier resourceIdentifier = new ResourceIdentifier("domain", "name");

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(resourceIdentifier);

        final ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        final ResourceIdentifier resourceIdentifierOut = (ResourceIdentifier) objectInputStream.readObject();

        assertThat(resourceIdentifierOut.getDomain(), is("domain"));
        assertThat(resourceIdentifierOut.getName(), is("name"));
    }

    private void testFromString(final String identifier, final String expectedDomain, final String expectedName) {
        final ResourceIdentifier resourceIdentifier = new ResourceIdentifier(identifier);
        assertThat(resourceIdentifier.getDomain(), is(expectedDomain));
        assertThat(resourceIdentifier.getName(), is(expectedName));
    }

    private void testToString(final String domain, final String name, final String expected) {
        final ResourceIdentifier identifier = new ResourceIdentifier(domain, name);
        assertThat(identifier.toString(), is(expected));
    }
}
