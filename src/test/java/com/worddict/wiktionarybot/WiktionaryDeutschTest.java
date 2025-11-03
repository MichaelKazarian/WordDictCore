package com.worddict.wiktionarybot;

//import android.os.Build;

import com.worddict.worddictcore.AudioSample;
//import com.waverunner.wordrunner.DictionaryLanguageSettings;
import com.worddict.worddictcore.Language;
import com.worddict.worddictcore.Pronounce;
import com.worddict.worddictcore.Translation;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
//import org.robolectric.RobolectricTestRunner;
//import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//@RunWith(RobolectricTestRunner.class)
//@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class WiktionaryDeutschTest {
    private static Wiktionary mWiktionary;
//    private static DictionaryLanguageSettings dls;
    //========================================================================
    @BeforeClass
    public static void setup() {
        mWiktionary = WiktionaryDeutsch.newInstance();
        Language[] langs = Language.getAllLanguages();
        ArrayList<Language> langs1 = new ArrayList<>();
        Language lng = langs[8];
        langs1.add(langs[6]); langs1.add(langs[52]);
//        dls = new DictionaryLanguageSettings(lng, langs1);
    }
    //========================================================================
    @Test
    public void _011searchWrong() {
        assertEquals(0,
                mWiktionary.search("testtetststtete"));
    }
    //========================================================================
    @Test
    public void _012searchAll() {
        //Passed for Test; test wasn't added to result (no Deutsch)
        assertEquals(1, mWiktionary.search("Test"));
    }
    @Test
    public void _014getAudioSamples() {
        AudioSample [] actual = mWiktionary.getAudioSamples();
        assertEquals(1, actual.length);
    }
    //========================================================================
    @Test
    public void _20searchOne() {
        assertEquals(1,
                mWiktionary.search("Test"));
    }
    @Test
    public void _21getIPA() {
        Pronounce.TextPronounce p1 = new Pronounce.TextPronounce("tɛst");
        Pronounce.TextPronounce [] expected = {p1};
        Pronounce.TextPronounce [] actual = mWiktionary.getIPA();
        assertArrayEquals(expected, actual);
    }
    @Test
    public void _22getAudioSamples() {
        AudioSample as0 = new AudioSample("De-Test");
        as0.setUrl("https://upload.wikimedia.org/wikipedia/commons/3/3c/De-Test.ogg");
        AudioSample [] expected = {as0};
        AudioSample [] actual = mWiktionary.getAudioSamples();
        assertArrayEquals(expected, actual);
    }
    @Test
    public void _23getTranslation() {
        Translation[] expected = new Translation [] {new Translation("тест"),
                new Translation("испытание"), new Translation("випробування") };
        Translation [] actual = mWiktionary.getTranslation(new String[]{"ru", "uk"});
        assertArrayEquals(expected, actual);
    }
    //========================================================================
    @Test
    public void _31search() {
        assertEquals(1,
                mWiktionary.search("super"));
    }
    @Test
    public void _32getIPA() {
        Pronounce.TextPronounce p1 = new Pronounce.TextPronounce("ˈzuːpɐ");
        Pronounce.TextPronounce [] expected = {p1};
        Pronounce.TextPronounce [] actual = mWiktionary.getIPA();
        assertArrayEquals(expected, actual);
    }
    @Test
    public void _33getTranslation() {
        Translation[] expected = new Translation [] {
                new Translation("супер"), new Translation("суперский") };
                Translation [] actual = mWiktionary.getTranslation(new String[]{"ru", "uk"});
        assertArrayEquals(expected, actual);
    }
    //========================================================================
    @Test
    public void _41search() {
        assertEquals(1,
                mWiktionary.search("Idee"));
    }

    @Test
    public void _42getIPA() {
        Pronounce.TextPronounce p1 = new Pronounce.TextPronounce("iˈdeː");
        Pronounce.TextPronounce [] expected = {p1};
        Pronounce.TextPronounce [] actual = mWiktionary.getIPA();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void _43getAudioSamples() {
        AudioSample as0 = new AudioSample("De-Idee");
        as0.setUrl("https://upload.wikimedia.org/wikipedia/commons/4/44/De-Idee.ogg");
        AudioSample as1 = new AudioSample("De-at-Idee");
        as1.setUrl("https://upload.wikimedia.org/wikipedia/commons/7/75/De-at-Idee.ogg");
//        AudioSample as2 = new AudioSample("De-eine_spannende_Idee");
//        as2.setUrl("https://upload.wikimedia.org/wikipedia/commons/9/99/De-eine_spannende_Idee.ogg");
        AudioSample [] expected = new AudioSample [] {as0, as1};
        AudioSample [] actual = mWiktionary.getAudioSamples();
        assertArrayEquals(expected, actual);
    }
    @Test
    public void _44getTranslation() {
        Translation [] actual = mWiktionary.getTranslation(new String[]{"ru", "uk"});
        assertTrue(actual.length > 0);
    }
    //========================================================================
    @Test
    public void _51search() {
        assertEquals(1,
                mWiktionary.search("glücklich"));
    }
    @Test
    public void _52getIPA() {
        Pronounce.TextPronounce p1 = new Pronounce.TextPronounce("ˈɡlʏklɪç");
        Pronounce.TextPronounce [] expected = {p1};
        Pronounce.TextPronounce [] actual = mWiktionary.getIPA();
        assertArrayEquals(expected, actual);
    }
    @Test
    public void _53getAudioSamples() {
        AudioSample as0 = new AudioSample("De-glücklich");
        as0.setUrl("https://upload.wikimedia.org/wikipedia/commons/8/81/De-glücklich.ogg");
        AudioSample [] expected  = new AudioSample [] {as0};
        AudioSample [] actual = new AudioSample [] {
                mWiktionary.getAudioSamples()[0]};
        assertArrayEquals(expected, actual);
    }
    @Test
    public void _54getTranslation() {
        Translation [] actual = mWiktionary.getTranslation(new String[]{"ru", "uk"});
        assertTrue(actual.length > 0);
    }
    //========================================================================
    @Test
    public void _61search() {
        assertEquals(1,
                mWiktionary.search("Großvater"));
    }
    @Test
    public void _62getIPA() {
        Pronounce.TextPronounce p1 = new Pronounce.TextPronounce("ˈɡʁoːsˌfaːtɐ");
        Pronounce.TextPronounce [] expected = {p1};
        Pronounce.TextPronounce [] actual = mWiktionary.getIPA();
        assertArrayEquals(expected, actual);
    }
    @Test
    public void _63getAudioSamples() {
        AudioSample as0 = new AudioSample("De-Großvater");
        as0.setUrl("https://upload.wikimedia.org/wikipedia/commons/d/d0/De-Großvater.ogg");
        AudioSample [] expected = new AudioSample [] {as0};
        AudioSample [] actual = mWiktionary.getAudioSamples();
        assertArrayEquals(expected, actual);/**/
    }
    //========================================================================
     @Test
    public void getLanguageSectionRegexp() {
        assertEquals("(?si)\\s?==.*\\(\\{\\{Sprache\\|Deutsch\\}\\}\\) ==.+?(?=\\s==[^=]|\\Z)",
                mWiktionary.getLanguageSectionRegexp());
    }
    //========================================================================
    @Test
    public void getWikiTextUrl() {
        String expected = "https://de.wiktionary.org/w/api.php?action=parse&page=Test&prop=wikitext&format=json";
        assertEquals(expected, mWiktionary.getWikiTextUrl("Test"));
    }
    //========================================================================
    @Test
    public void getSearchUrl() {
        String expected = "https://de.wiktionary.org/w/api.php?action=opensearch&search=Test&namespace=0&limit=10&format=json&formatversion=2&suggest=true";
        assertEquals(expected, mWiktionary.getSearchUrl("Test"));
    }
    //========================================================================
    @Test
    public void getLanguageSection() {
        String sectionNotFound = "Wikitext without ==Language==\\n section";
        assertEquals("", mWiktionary.getLanguageSection(sectionNotFound));
        String germanOnly = "{{Siehe auch|[[test]], [[test.]]}}\\n== Test "+
                "({{Sprache|Deutsch}}) ==\\n=== {{Wortart|Substantiv|Deutsch}}"+
                ", {{m}} ===\\n\\{{Deutsch";
        String germanOnlyExp = "== Test ({{Sprache|Deutsch}}) ==\\n=== "+
                "{{Wortart|Substantiv|Deutsch}}, {{m}} ===\\n\\{{Deutsch";
        assertEquals(germanOnlyExp, mWiktionary.getLanguageSection(germanOnly));
        //German only. Article starts with == word; identical result
        germanOnly =    "== eine ({{Sprache|Deutsch}}) ==\\n==="+
                " {{Wortart|Artikel|Deutsch}}";
        germanOnlyExp = "== eine ({{Sprache|Deutsch}}) ==\\n==="+
                " {{Wortart|Artikel|Deutsch}}";
        assertEquals(germanOnlyExp, mWiktionary.getLanguageSection(germanOnly));
        //Retrieve from multilingual article
        String germanFirst = "== per ({{Sprache|Deutsch}}) ==\n" +
                "=== {{Wortart|Präposition|Deutsch}} ===\n" +
                "== per ({{Sprache|Englisch}}) ==\n";
        String germanFirstExp = "== per ({{Sprache|Deutsch}}) ==\n" +
                "=== {{Wortart|Präposition|Deutsch}} ===";
        assertEquals(germanFirstExp, mWiktionary.getLanguageSection(germanFirst));
        //Starts from == word
        germanFirst = "== online ({{Sprache|Deutsch}}) ==\n" +
                "{{überarbeiten|Bedeutungsangaben prüfen und belegen|Deutsch}}"+
                "\n== online ({{Sprache|Englisch}}) ==\n===";
        germanFirstExp = "== online ({{Sprache|Deutsch}}) ==\n" +
                "{{überarbeiten|Bedeutungsangaben prüfen und belegen|Deutsch}}";
        assertEquals(germanFirstExp, mWiktionary.getLanguageSection(germanFirst));
        // German part inside multilingual article not works yet.
        String germanInside = "{{Siehe auch|[[Per]], [[per-]]}}\\n\\n== per "+
                "({{Sprache|Englisch}}) ==\\n=== {{Wortart|Präposition|Deutsch}}"+
                "\\n== per ({{Sprache|Deutsch}}) ==\\n=== {{Wortart|Präposition";
        String germanInsideExp = "\\n==English==\\n\\n===Noun===\\n{{head"+
                "|br|nouns}}\\n\\n# [[witness]]\\n\\n----\\n";
        assertNotEquals(germanInsideExp,
                mWiktionary.getLanguageSection(germanInside));
    }
    //========================================================================
    @Test
    public void languageEquals() {
        assertTrue(mWiktionary.languageEquals("Test", "Test"));
        assertTrue(mWiktionary.languageEquals("test", "Test"));
        assertFalse(mWiktionary.languageEquals("Test", "test"));
        assertFalse(mWiktionary.languageEquals("Test", "another"));
        assertFalse(mWiktionary.languageEquals("test", "another"));
    }
}