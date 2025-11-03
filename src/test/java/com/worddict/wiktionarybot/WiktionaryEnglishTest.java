package com.worddict.wiktionarybot;

import com.worddict.worddictcore.AudioSample;
import com.worddict.worddictcore.Language;
import com.worddict.worddictcore.Pronounce;
import com.worddict.worddictcore.Translation;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//@RunWith(RobolectricTestRunner.class)
//@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class WiktionaryEnglishTest {
    private static Wiktionary mWiktionary;
    String [] langCodes = {"ru", "uk"};
  //  private static DictionaryLanguageSettings dls;
    //========================================================================
    @BeforeClass
    public static void setup() {
        mWiktionary = WiktionaryEnglish.newInstance();
        Language[] langs = Language.getAllLanguages();
        Language lng = langs[0];
        ArrayList<Language> langs1 = new ArrayList<>();
        langs1.add(langs[6]); langs1.add(langs[52]);
        //dls = new DictionaryLanguageSettings(lng, langs1);
    }
    //========================================================================
    @Test
    public void getLanguageSection() {
        String sectionNotFound = "Wikitext without =Language==\n section";
        assertEquals("", mWiktionary.getLanguageSection(sectionNotFound));
        // See [1] for example
        String engOnly = "{{also|getup|get-up}}\n==English==\n==="+
                "Pronunciation===\n* {{audio|EN-AU ck1 get up.ogg|Audio "+
                "(AU)|lang=en}}\n\n===Verb===\n{{en-verb|gets up";
        String engOnlyExp = "\n==English==\n==="+
                "Pronunciation===\n* {{audio|EN-AU ck1 get up.ogg|Audio "+
                "(AU)|lang=en}}\n\n===Verb===\n{{en-verb|gets up";
        assertEquals(engOnlyExp, mWiktionary.getLanguageSection(engOnly));
        engOnly = "==English==\n==="+
                "Pronunciation===\n* {{audio|EN-AU ck1 get up.ogg|Audio "+
                "(AU)|lang=en}}\n\n===Verb===\n{{en-verb|gets up";
        engOnlyExp = "==English==\n==="+
                "Pronunciation===\n* {{audio|EN-AU ck1 get up.ogg|Audio "+
                "(AU)|lang=en}}\n\n===Verb===\n{{en-verb|gets up";
        assertEquals(engOnlyExp, mWiktionary.getLanguageSection(engOnly));
        String engFirst = "{{also|getup|get-up}}\n==English==\n==="+
                "Pronunciation===\n* {{audio|EN-AU ck1 get up.ogg|"+
                "Audio (AU)|lang=en}}\n\n===Verb===\n{{en"+
                "---\n\n==Breton==\n\n===Noun===\n{{head"+
                "|br|nouns}}\n\n# [[witness]]\n\n----\n\n"+
                "==Czech==\n\n===Noun===\n{{c";
        String engFirstExp = "\n==English==\n==="+
                "Pronunciation===\n* {{audio|EN-AU ck1 get up.ogg|"+
                "Audio (AU)|lang=en}}\n\n===Verb===\n{{en---\n";
        assertEquals(engFirstExp, mWiktionary.getLanguageSection(engFirst));
        String engInside = "{{also|getup|get-up}}\n==Breton==\n==="+
                "Pronunciation===\\n* {{audio|EN-AU ck1 get up.ogg|"+
                "Audio (AU)|lang=en}}\n\n===Verb===\n{{en"+
                "---\n\n==English==\n\n===Noun===\n{{head"+
                "|br|nouns}}\n\n# [[witness]]\n\n----\n\n"+
                "==Czech==\n\n===Noun===\n{{c";
        String engInsideExp = "\n==English==\n\n===Noun===\n{{head"+
                "|br|nouns}}\n\n# [[witness]]\n\n----\n";
        assertEquals(engInsideExp, mWiktionary.getLanguageSection(engInside));
        assertEquals("", mWiktionary.getLanguageSection(""));
    }
    //========================================================================
    @Test
    public void getWordVariants() {
        String[] res = {"test"};
        assertArrayEquals(res, mWiktionary.getWordVariants("test"));
    }
    //========================================================================
    @Test
    public void _01searchWrong() {
        assertEquals(0, mWiktionary.search("testtetststtete"));
    }
    //========================================================================
    @Test
    public void _10search() {
        assertEquals(1, mWiktionary.search("test"));
    }

    @Test
    public void _11getIPA() {
        Pronounce.TextPronounce p1 = new Pronounce.TextPronounce("tɛst");
        Pronounce.TextPronounce p2 = new Pronounce.TextPronounce("test");
        Pronounce.TextPronounce [] expected = {p1};
        Pronounce.TextPronounce [] actual = mWiktionary.getIPA();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void _12getAudioSamples() {
        AudioSample as1 = new AudioSample("En-uk-a_test");
        as1.setUrl("https://upload.wikimedia.org/wikipedia/commons/d/d5/En-uk-a_test.ogg");
        AudioSample as2 = new AudioSample("En-us-test");
        as2.setUrl("https://upload.wikimedia.org/wikipedia/commons/9/9c/En-us-test.ogg");
        as2.setComment("a=GA");
        AudioSample [] expected = {as1, as2};
        AudioSample [] actual = mWiktionary.getAudioSamples();
        AudioSample[] actualSubset = Arrays.copyOf(actual, 2);
        assertArrayEquals(expected, actualSubset);
    }
    @Test
    public void _13getTranslation() {
        Translation[] expected = new Translation [] {
                new Translation("тест"), new Translation("про́ба"), new Translation("испыта́ние"),
                new Translation("экза́мен"), new Translation("контро́льная рабо́та"),
                new Translation("испы́тывать"), new Translation("испыта́ть"),
                new Translation("тести́ровать"), new Translation("протести́ровать"),
                new Translation("випро́бування"), new Translation("екза́мен"),
                new Translation("випробовувати"), new Translation("тестувати"),
                new Translation("трестува́ти")};
        Translation [] actual = mWiktionary.getTranslation(langCodes);
        assertEquals(expected[0], actual[0]);
    }
    //========================================================================
    @Test
    public void _20search() {
        assertEquals(1, mWiktionary.search("occur"));
    }
    @Test
    public void _21getIPA() {
        Pronounce.TextPronounce p1 = new Pronounce.TextPronounce("əˈkɜː");
        // Pronounce.TextPronounce p2 = new Pronounce.TextPronounce("əˈkɝ");
        Pronounce.TextPronounce [] expected = {p1};
        Pronounce.TextPronounce [] actual = mWiktionary.getIPA();
        assertEquals(expected[0], actual[0]);
    }
    @Test
    public void _22getAudioSamples() {
        AudioSample as1 = new AudioSample("En-au-occur");
        as1.setUrl("https://upload.wikimedia.org/wikipedia/commons/b/be/En-au-occur.ogg");
        as1.setComment("a=AU");
        AudioSample as2 = new AudioSample("En-us-occur");
        as2.setUrl("https://upload.wikimedia.org/wikipedia/commons/4/44/En-us-occur.ogg");
        as2.setComment("a=GA");
        AudioSample [] expected = {as1, as2};
        AudioSample [] actual = mWiktionary.getAudioSamples();
        assertArrayEquals(expected, actual);
    }
    @Test
    public void _23getTranslation() {
        Translation [] actual = mWiktionary.getTranslation(langCodes);
        assertEquals(new Translation("происходи́ть"), actual[0]);
        assertEquals(new Translation("трапля́тися"), actual[7]);
    }

    //========================================================================
    /**
     * Test translation to different language. Word "per" translates to
     *  Ukrainian, Russian, Macedonian. Here test this case with Macedonian.
     */
    @Test
    public void _90searchMK() {
        assertEquals(1, mWiktionary.search("per"));
    }
    //========================================================================
    @Test
    public void _91getTranslationMK() {
        Language[] langs = Language.getAllLanguages();
        Language en = langs[0];
        ArrayList<Language> myLangs = new ArrayList<>();
        myLangs.add(langs[92]); //mk, Macedonian. Contains {{t|mk|по}}, {{t|mk|во|tr=vo}}
        //DictionaryLanguageSettings dls = new DictionaryLanguageSettings(en, myLangs);
        Translation[] expected = new Translation [] {
                new Translation("по"), new Translation("на"), new Translation("во")};
        String [] langCodes = {"mk"};
        Translation[] actual = mWiktionary.getTranslation(langCodes);
        assertArrayEquals(expected, actual);
    }
    //========================================================================
    @Test
    public void getWikiTextUrl() {
        String expected = "https://en.wiktionary.org/w/api.php?action=parse&page=test&prop=wikitext&format=json";
        assertEquals(expected, mWiktionary.getWikiTextUrl("test"));
    }
    //========================================================================
    @Test
    public void getSearchUrl() {
        String expected = "https://en.wiktionary.org/w/api.php?action="+
                "opensearch&search=Test&namespace=0&limit=10&format="+
                "json&formatversion=2&suggest=true";
        assertEquals(expected, mWiktionary.getSearchUrl("Test"));
    }
    //========================================================================
    @Test
    public void getMediaWikiToURL() throws Exception{
        assertEquals(
                "https://upload.wikimedia.org/wikipedia/commons/9/9c/En-us-test.ogg",
                Wiktionary.getMediaWikiToURL("En-us-test.ogg"));
        assertEquals(
                "https://upload.wikimedia.org/wikipedia/commons/d/d5/En-uk-a_test.ogg",
                Wiktionary.getMediaWikiToURL("En-uk-a_test.ogg"));
        assertEquals(
                "https://upload.wikimedia.org/wikipedia/commons/f/f1/EN-AU_ck1_get_up.ogg",
                Wiktionary.getMediaWikiToURL("EN-AU_ck1_get_up.ogg"));
        assertEquals(
                "https://upload.wikimedia.org/wikipedia/commons/a/ae/Spelterini_Blüemlisalp.jpg",
                Wiktionary.getMediaWikiToURL("Spelterini Blüemlisalp.jpg"));
    }
    //========================================================================
    @Test
    public void normalizeName() {
        assertEquals("", Wiktionary.normalizeFileName(""));
        assertEquals("En-us-test.ogg",
                Wiktionary.normalizeFileName("en-us-test.ogg"));
        assertEquals("Spelterini_BlüCemlisalp.jpg",
                Wiktionary.normalizeFileName("Spelterini BlüCemlisalp.jpg"));
        assertEquals("Spelterini_BlüCemlisalp.jpg",
                Wiktionary.normalizeFileName("spelterini BlüCemlisalp.jpg"));
    }
    @Test
    public void _00runFirst(){}
    //========================================================================
    @Test
    public void getLanguageSectionRegexp() {
        assertEquals("(?si)\\s?==english.+?(?=\\s==[^=]|\\Z)",
                mWiktionary.getLanguageSectionRegexp());
    }

    // [1] https://en.wiktionary.org/w/api.php?action=query&format=json&prop=revisions&rvslots=*&rvprop=content&titles=get_up
}