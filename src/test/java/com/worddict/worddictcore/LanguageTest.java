package com.worddict.worddictcore;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kazarian on 10.11.17.
 */
public class LanguageTest {
    Language mUkrLanguage;

    @Before
    public void setUp() throws Exception {
        mUkrLanguage = new Language("Ukrainian", "Українська",
                "uk", Language.RATING_SATISFACTORY);
    }
    @Test
    public void isISOCodeSupported() throws Exception {
        Assert.assertTrue(Language.isISOCodeSupported("uk"));
        Assert.assertTrue(Language.isISOCodeSupported(" UK  "));
        Assert.assertFalse(Language.isISOCodeSupported("ukr"));
    }

    @Test
    public void getLanguageByCode() throws Exception {
        assertEquals(mUkrLanguage, Language.getLanguageByCode("uk"));
        assertNotEquals(new Language("Ukrainian", "Українська",
                "uk", Language.RATING_POOR),
                Language.getLanguageByCode("uk"));
    }

    @Test
    public void getLanguage() throws Exception {
        assertEquals("Ukrainian", mUkrLanguage.getLanguage());
    }

    @Test
    public void getLocalLanguage() throws Exception {
        assertEquals("Українська", mUkrLanguage.getLocalLanguage());
    }

    @Test
    public void getLanguageCode() throws Exception {
        assertEquals("uk", mUkrLanguage.getLanguageCode());
    }

    @Test
    public void getRating() throws Exception {
        assertEquals(Language.RATING_SATISFACTORY, mUkrLanguage.getRating());
    }
    @Test
    public void languageNumbers() throws Exception {
        Language [] allLanguages = Language.getAllLanguages();
        assertEquals(new Language("English", "English", "en", Language.RATING_BEST),
                allLanguages[0]);
        assertEquals(new Language("French", "Français", "fr", Language.RATING_BEST),
                allLanguages[2]);
        assertEquals(new Language("Russian", "Русский", "ru", Language.RATING_GOOD),
                allLanguages[6]);
        assertEquals(new Language("German", "Deutsch", "de", Language.RATING_GOOD),
                allLanguages[8]);
        assertEquals(new Language("Ukrainian", "Українська", "uk", Language.RATING_SATISFACTORY),
                allLanguages[52]);

    }
}