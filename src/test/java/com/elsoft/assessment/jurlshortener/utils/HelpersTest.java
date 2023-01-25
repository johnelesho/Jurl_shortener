package com.elsoft.assessment.jurlshortener.utils;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class HelpersTest {

    /**
     * Method under test: {@link Helpers#urlValidator(String)}
     */
    @Test
    public void testUrlValidator() {
        assertTrue(Helpers.urlValidator("https://example.org/example"));
        assertFalse(Helpers.urlValidator(null));
    }

    /**
     * Method under test: {@link Helpers#isUrlValid(String)}
     */
    @Test
    public void testIsUrlValid() throws MalformedURLException, URISyntaxException {
        assertTrue(Helpers.isUrlValid("https://example.org/example"));
        assertFalse(Helpers.isUrlValid("foo"));
        assertFalse(Helpers.isUrlValid("https://example.org/exampleUrl String"));
    }

    /**
     * Method under test: {@link Helpers#isUrlValid2(String)}
     */
    @Test
    public void testIsUrlValid2() throws MalformedURLException {
        assertTrue(Helpers.isUrlValid2("https://example.org/example"));
        assertFalse(Helpers.isUrlValid2(null));
        assertFalse(Helpers.isUrlValid2("UU"));
        assertFalse(Helpers.isUrlValid2("[::FFFF:999.999.999.999]:9U"));
        assertFalse(Helpers.isUrlValid2("https://example.org/examplehttps://example.org/example"));
    }

    /**
     * Method under test: {@link Helpers#getDateDiff(LocalDateTime)}
     */
    @Test
    @DisplayName("Testing the date difference")
    public void testGetDateDiff() {

        long dateDiff = Helpers.getDateDiff(LocalDateTime.now().plusHours(56));
        Assertions.assertThat(dateDiff).isEqualTo(56);
        dateDiff = Helpers.getDateDiff(LocalDateTime.now().plusDays(48));
        Assertions.assertThat(dateDiff).isEqualTo((48 * 24)-1);
        dateDiff = Helpers.getDateDiff(LocalDateTime.now().minusHours(48));
        Assertions.assertThat(dateDiff).isNegative();
    }


}