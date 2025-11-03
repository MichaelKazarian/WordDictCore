package com.worddict.wiktionarybot;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;

import com.worddict.worddictcore.AudioSample;
import com.worddict.worddictcore.Language;
import com.worddict.worddictcore.Pronounce;
import com.worddict.worddictcore.Translation;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WiktionarySpanishTest {
    private static Wiktionary mWiktionary;
    private static Language mLng;
    private static ArrayList<Language> mLangs;
    String [] langCodes = {"en"};

    @BeforeClass
    public static void setup() {
        mWiktionary = WiktionarySpanish.newInstance();
        Language[] langs = Language.getAllLanguages(); mLng = langs[4];
        mLangs = new ArrayList<>();
        mLangs.add(langs[0]);
    }
    //========================================================================
    @Test
    public void _011searchWrong() {
        assertEquals(0, mWiktionary.search("testtetststtete")); //No word
        assertEquals(0, mWiktionary.search("comment")); //No Spanish
    }
    //========================================================================
    @Test
    public void _20search() {
        assertEquals(1, mWiktionary.search("árbol"));
    }

    @Test
    public void _21getIPA() {
//        Pronounce.TextPronounce p1 = new Pronounce.TextPronounce("ˈaɾ.βol");
        Pronounce.TextPronounce [] expected = {};
        Pronounce.TextPronounce [] actual = mWiktionary.getIPA();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void _22getAudioSamples() {
    }

    @Test
    public void _23getTranslation() {
        Translation[] expected = new Translation[]{
                new Translation("tree")};
        Translation[] actual = mWiktionary.getTranslation(langCodes);
        assertArrayEquals(expected, actual);
    }
    //========================================================================
    @Test
    public void _30search() {
        assertEquals(1, mWiktionary.search("cómo"));
    }

    @Test
    public void _31getIPA() {
//        Pronounce.TextPronounce p1 = new Pronounce.TextPronounce("ˈko.mo");
        Pronounce.TextPronounce [] expected = {};
        Pronounce.TextPronounce [] actual = mWiktionary.getIPA();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void _33getTranslation() {
        Translation[] expected = new Translation[]{
                new Translation("how")};
        Translation[] actual = mWiktionary.getTranslation(langCodes);
        assertArrayEquals(expected, actual);
    }
    //========================================================================
    @Test
    public void _40search() {
        assertEquals(1, mWiktionary.search("tener"));
    }

    @Test
    public void _41getIPA() {
//        Pronounce.TextPronounce p1 = new Pronounce.TextPronounce("teˈneɾ");
        Pronounce.TextPronounce [] expected = {};
        Pronounce.TextPronounce [] actual = mWiktionary.getIPA();
        assertArrayEquals(expected, actual);
        AudioSample as0 = new AudioSample("ES-tener");
        as0.setUrl("https://upload.wikimedia.org/wikipedia/commons/c/cc/ES-tener.mp3");
        assertArrayEquals(new AudioSample[]{as0}, mWiktionary.getAudioSamples());
    }
    @Test
    public void _43getTranslation() {
        Translation[] expected = new Translation[]{
                new Translation("have"), new Translation("hold"),
                new Translation("feel"), new Translation("be"),
                new Translation("must")};
        Translation[] actual = mWiktionary.getTranslation(langCodes);
        assertArrayEquals(expected, actual);
    }
    //========================================================================
    @Test
    public void _50search() {
        // Victoria, victoria
        assertEquals(1, mWiktionary.search("victoria"));
    }

    @Test
    public void _51getIPA() {
//        Pronounce.TextPronounce p1 = new Pronounce.TextPronounce("bikˈto.ɾja");
//        Pronounce.TextPronounce p2 = new Pronounce.TextPronounce("bik ˈto.ɾja");
        Pronounce.TextPronounce [] expected = {};
        Pronounce.TextPronounce [] actual = mWiktionary.getIPA();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void _53getTranslation() {
        Translation[] expected = new Translation[]{
                new Translation("victory"), new Translation("victoria")};
        Translation[] actual = mWiktionary.getTranslation(langCodes);
        assertArrayEquals(expected, actual);
    }
    //========================================================================
    @Test
    public void _60search() {
        assertEquals(1, mWiktionary.search("buscar"));
    }

    @Test
    public void _61getIPA() {
//        Pronounce.TextPronounce p1 = new Pronounce.TextPronounce("busˈkaɾ");
        Pronounce.TextPronounce [] expected = {};
        Pronounce.TextPronounce [] actual = mWiktionary.getIPA();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void _62getAudioSamples() {
        AudioSample as1 = new AudioSample("Es-buscar");
        as1.setUrl("https://upload.wikimedia.org/wikipedia/commons/3/3a/Es-buscar.ogg");
        AudioSample [] expected = new AudioSample[] {as1};
        AudioSample [] actual = mWiktionary.getAudioSamples();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void _63getTranslation() {
        Translation[] expected = new Translation[]{
                new Translation("search"), new Translation("seek"),
        new Translation("look for")};
        Translation[] actual = mWiktionary.getTranslation(langCodes);
        assertArrayEquals(expected, actual);
    }
    //========================================================================
    @Test
    public void _70search() {
        assertEquals(1, mWiktionary.search("inventado"));
        assertArrayEquals(new Pronounce.TextPronounce[]{}, mWiktionary.getIPA());
        assertArrayEquals(new Translation[]{}, mWiktionary.getTranslation(langCodes));
    }
    //========================================================================
    @Test
    public void _80search() {
        assertEquals(1, mWiktionary.search("alimentos"));
        assertArrayEquals(new Pronounce.TextPronounce[]{}, mWiktionary.getIPA());
        assertArrayEquals(new Translation[]{}, mWiktionary.getTranslation(langCodes));
    }
    //========================================================================
    @Test
    public void _90search() {
        assertEquals(1, mWiktionary.search("esperanza"));
    }

    @Test
    public void _91getIPA() {
//        Pronounce.TextPronounce p0 = new Pronounce.TextPronounce("es.peˈɾan.θa");
//        Pronounce.TextPronounce p1 = new Pronounce.TextPronounce("es.peˈɾan.sa");
        Pronounce.TextPronounce [] expected = {};
        Pronounce.TextPronounce [] actual = mWiktionary.getIPA();
        assertArrayEquals(expected, actual);
        assertArrayEquals(new AudioSample[]{}, mWiktionary.getAudioSamples());
    }

    @Test
    public void _93getTranslation() {
        Translation[] expected = new Translation[]{ new Translation("hope"),
                new Translation("mean")};
        Translation[] actual = mWiktionary.getTranslation(langCodes);
        assertArrayEquals(expected, actual);

    }
    //========================================================================
    @Test
    public void getLanguageSectionRegexp() {
        assertEquals("(?s)\\s?== \\{\\{lengua\\|es\\}.*?(?=\\s== \\{\\{lengua\\||\\Z)",
                mWiktionary.getLanguageSectionRegexp());
    }
    //========================================================================
    @Test
    public void getWikiTextUrl() {
        String expected = "https://es.wiktionary.org/w/api.php?action=parse&page=Test&prop=wikitext&format=json";
        assertEquals(expected, mWiktionary.getWikiTextUrl("Test"));
    }
    //========================================================================
    @Test
    public void getSearchUrl() {
        String expected = "https://es.wiktionary.org/w/api.php?action="+
                "opensearch&search=Test&namespace=0&limit=10&format=json"+
                "&formatversion=2&suggest=true";
        assertEquals(expected, mWiktionary.getSearchUrl("Test"));
    }
}