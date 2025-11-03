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

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//@RunWith(RobolectricTestRunner.class)
//@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class WiktionaryFrenchTest {
    private static Wiktionary mWiktionary;
    private static Language mLng;
    private static ArrayList<Language> mLangs;
//    private static DictionaryLanguageSettings dls;
    String [] langCodes = {"ru", "uk"};
    //========================================================================
    @BeforeClass
    public static void setup() {
        mWiktionary = WiktionaryFrench.newInstance();
        Language[] langs = Language.getAllLanguages(); mLng = langs[2];
        mLangs = new ArrayList<>();
        mLangs.add(langs[6]); mLangs.add(langs[52]);
  //      dls = new DictionaryLanguageSettings(mLng, mLangs);
    }
    //========================================================================
    @Test
    public void _011searchWrong() {
        assertEquals(0,
                mWiktionary.search("testtetststtete"));
    }
    //========================================================================
    @Test
    public void _20search() {
        assertEquals(1, mWiktionary.search("comment"));
    }
    @Test
    public void _21getIPA() {
        Pronounce.TextPronounce p1 = new Pronounce.TextPronounce("kɔ.mɑ̃");
        Pronounce.TextPronounce p2 = new Pronounce.TextPronounce("kɔm");
        Pronounce.TextPronounce [] expected = {p1, p2};
        Pronounce.TextPronounce [] actual = mWiktionary.getIPA();
        assertArrayEquals(expected, actual);
    }
    @Test
    public void _22getAudioSamples(){
        AudioSample as1 = new AudioSample("Fr-comment");
        as1.setUrl("https://upload.wikimedia.org/wikipedia/commons/6/6d/Fr-comment.ogg");
        as1.setComment("France (Paris) kɔ.mɑ̃");
        AudioSample as2 = new AudioSample("Fr-Comment");
        as2.setUrl("https://upload.wikimedia.org/wikipedia/commons/b/b4/Fr-Comment.oga");
        as2.setComment("Canada (accent anglophone) kɔ.mã");
        AudioSample [] expected = {as1, as2};
        AudioSample [] actual = new AudioSample[2];
        System.arraycopy(mWiktionary.getAudioSamples(), 0, actual, 0, 2);
        assertArrayEquals(expected, actual);
    }
    @Test
    public void _23getTranslation() {
        Translation[] expected = new Translation[]{
                new Translation("как"), new Translation("як")};
        Translation[] actual = mWiktionary.getTranslation(langCodes);
        assertArrayEquals(expected, actual);
    }
    //========================================================================
    @Test
    public void _30search() {
        assertEquals(1, mWiktionary.search("cocagne"));
    }
    @Test
    public void _31getIPA() {
        Pronounce.TextPronounce p1, p2, p3;
        Pronounce.TextPronounce [] expected, actual;
        p1 = new Pronounce.TextPronounce("kɔ.kaɲ");
        p2 = new Pronounce.TextPronounce("ko.kaɲ");
        p3 = new Pronounce.TextPronounce("ko.ˈka.ɲə");
        expected = new Pronounce.TextPronounce [] {p1, p2, p3};
        actual = mWiktionary.getIPA();
        assertArrayEquals(expected, actual);
    }
    @Test
    public void _32getAudioSamples() {
        AudioSample as1 = new AudioSample("Fr-cocagne");
        as1.setUrl("https://upload.wikimedia.org/wikipedia/commons/7/73/Fr-cocagne.ogg");
        as1.setComment("kɔ.kaɲ");
        AudioSample as2 = new AudioSample("LL-Q150_(fra)-LoquaxFR-cocagne");
        as2.setUrl("https://upload.wikimedia.org/wikipedia/commons/2/21/LL-Q150_(fra)-LoquaxFR-cocagne.wav");
        as2.setComment("France (Vosges)");
        AudioSample [] expected = new AudioSample[] {as1, as2};
        AudioSample [] actual = mWiktionary.getAudioSamples();
        assertArrayEquals(expected, actual);
    }
    @Test
    public void _33getTranslation() {
        Translation[] expected = new Translation[0];
        Translation[] actual = mWiktionary.getTranslation(langCodes);
        assertArrayEquals(expected, actual);
    }
    //========================================================================
    @Test
    public void _40search() {
        assertEquals(1, mWiktionary.search("écouter"));
    }
    @Test
    public void _41getIPA() {
        Pronounce.TextPronounce p1;
        Pronounce.TextPronounce [] expected, actual;
        p1 = new Pronounce.TextPronounce("e.ku.te");
        expected = new Pronounce.TextPronounce [] {p1};
        actual = mWiktionary.getIPA();
        assertArrayEquals(expected, actual);
    }
    @Test
    public void _42getAudioSamples() {
        AudioSample as1 = new AudioSample("Fr-écouter");
        as1.setUrl("https://upload.wikimedia.org/wikipedia/commons/0/07/Fr-écouter.ogg");
        as1.setComment("France <!-- précisez svp la ville ou la région --> e.ku.te");
        AudioSample [] expected = new AudioSample[] {as1};
        AudioSample [] actual = new AudioSample[1];
        System.arraycopy(mWiktionary.getAudioSamples(), 0, actual, 0, 1);
        assertArrayEquals(expected, actual);
    }
    @Test
    public void _43getTranslation() {
        Translation [] expected = new Translation [] {
                new Translation("слушать"), new Translation("послушать"),
                new Translation("слухати"), new Translation("послухати"),
                new Translation("почути")};
        Translation [] actual = mWiktionary.getTranslation(langCodes);
        assertArrayEquals(expected, actual);
    }
    //========================================================================
    @Test
    public void _50search() {
        assertEquals(1, mWiktionary.search("extérieur"));
    }
    @Test
    public void _51getIPA() {
        Pronounce.TextPronounce p1, p2, p3;
        Pronounce.TextPronounce [] expected, actual;
        p1 = new Pronounce.TextPronounce("ɛk.ste.ʁjœʁ");
        expected = new Pronounce.TextPronounce [] {p1};
        actual = mWiktionary.getIPA();
        assertArrayEquals(expected, actual);
    }
    @Test
    public void _52getAudioSamples() {
        AudioSample as1 = new AudioSample("Fr-extérieur");
        as1.setUrl("https://upload.wikimedia.org/wikipedia/commons/6/63/Fr-extérieur.ogg");
        as1.setComment("France ɛk.ste.ʁjœʁ");
        AudioSample as2 = new AudioSample("LL-Q150_(fra)-DSwissK-extérieur");
        as2.setUrl("https://upload.wikimedia.org/wikipedia/commons/d/da/LL-Q150_(fra)-DSwissK-extérieur.wav");
        as2.setComment("Suisse (canton du Valais)");
        AudioSample [] actual = mWiktionary.getAudioSamples();
        assertEquals(as1, actual[0]); assertEquals(as2, actual[1]);
    }
    @Test
    public void _53getTranslation() {
        Translation [] expected = new Translation [] {
                new Translation("внешний"), new Translation("наружный"),
                new Translation("зовнішній")};
        Translation [] actual = mWiktionary.getTranslation(langCodes);
        assertArrayEquals(expected, actual);
    }
    //========================================================================
    @Test
    public void _60search() {
        assertEquals(1, mWiktionary.search("poisson"));
    }
    @Test
    public void _61getIPA() {
        Pronounce.TextPronounce p1, p2;
        Pronounce.TextPronounce [] expected, actual;
        p1 = new Pronounce.TextPronounce("pwa.sɔ̃");
        expected = new Pronounce.TextPronounce [] {p1};
        actual = mWiktionary.getIPA();
        assertArrayEquals(expected, actual);
    }
    @Test
    public void _62getAudioSamples() {
        AudioSample as1 = new AudioSample("Fr-poisson");
        as1.setUrl("https://upload.wikimedia.org/wikipedia/commons/7/72/Fr-poisson.ogg");
        as1.setComment("France (Paris) lø pwa.sɔ̃");
        AudioSample as2 = new AudioSample("LL-Q150_(fra)-Guilhelma-poisson");
        as2.setUrl("https://upload.wikimedia.org/wikipedia/commons/8/8c/LL-Q150_"+
                "(fra)-Guilhelma-poisson.wav");
        as2.setComment("pwa.sɔ̃");
        AudioSample [] expected = new AudioSample[] {as1, as2};
        AudioSample [] actual = new AudioSample[2];
        System.arraycopy(mWiktionary.getAudioSamples(), 0, actual, 0, 2);
        assertArrayEquals(expected, actual);
    }
    @Test
    public void _63getTranslation() {
        Translation [] expected = new Translation [] {
                new Translation("рыба"), new Translation("риба") };
        Translation [] actual = mWiktionary.getTranslation(langCodes);
        assertArrayEquals(expected, actual);
    }
    //========================================================================
    @Test
    public void getLanguageSectionRegexp() {
        assertEquals("(?si)\\s?== \\{\\{langue\\|fr\\}.*?(?=\\s== \\{\\{langue\\|[^f]|\\Z)",
                mWiktionary.getLanguageSectionRegexp());
    }
    //========================================================================
    @Test
    public void getWikiTextUrl() {
        String expected = "https://fr.wiktionary.org/w/api.php?action=parse&page=Test&prop=wikitext&format=json";
        assertEquals(expected, mWiktionary.getWikiTextUrl("Test"));
    }
    //========================================================================
    @Test
    public void getSearchUrl() {
        String expected = "https://fr.wiktionary.org/w/api.php?action="+
                "opensearch&search=Test&namespace=0&limit=10&format=json"+
                "&formatversion=2&suggest=true";
        assertEquals(expected, mWiktionary.getSearchUrl("Test"));
    }
}