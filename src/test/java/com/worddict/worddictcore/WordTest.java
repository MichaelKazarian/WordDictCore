package com.worddict.worddictcore;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.File;
import java.util.ArrayList;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class WordTest {

    private static Word mWord;

    @BeforeClass
    public static void setup() {

    }

    @Before
    public void setUp() {
        mWord = new Word("Word");
    }

    @Test
    public void isLearned() {
        assertFalse(mWord.isLearned());
        mWord.setLearned(true);  assertTrue(mWord.isLearned());
        mWord.setLearned(false); assertFalse(mWord.isLearned());
    }

    @Test
    public void isStarred() {
        assertFalse(mWord.isStarred());
        mWord.setStarred(true);   assertTrue(mWord.isStarred());
        mWord.setStarred(false);  assertFalse(mWord.isStarred());
    }

    @Test
    public void isProblem() {
        assertFalse(mWord.isProblem());
        mWord.setProblem(true);   assertTrue(mWord.isProblem());
        mWord.setProblem(false);  assertFalse(mWord.isProblem());
    }

    @Test
    public void isLearning() {
        assertFalse(mWord.isLearning());
        mWord.setLearning(true);   assertTrue(mWord.isLearning());
        mWord.setLearning(false);  assertFalse(mWord.isLearning());
    }

    @Test
    public void getWord() {
        assertEquals("Word", mWord.getWord());
        mWord.setWord("\nAnother "); assertEquals("Another", mWord.getWord());
        mWord.setWord("Word"); assertNotEquals("Another", mWord.getWord());
    }

    @Test
    public void getLastScore() {
        mWord.setLastScore(45);
        assertEquals(45, mWord.getLastScore());
        assertNotEquals(40, mWord.getLastScore());
    }

    @Test
    public void getMeaningCheckDate() {
        mWord.setMeaningCheckDate("2018-03-15");
        assertEquals("2018-03-15", mWord.getMeaningCheckDate());
    }

    @Test
    public void getNote() {
        assertEquals("", mWord.getNote());
        mWord.setNote("Some text"); assertEquals("Some text", mWord.getNote());
    }

    @Test
    public void prepareName() {
        assertEquals("Word", mWord.prepareName("\t\r  Word \n"));
    }

    @Test
    public void getMeaningScore() {
        mWord.setMeaningScore(50); assertEquals(50, mWord.getMeaningScore());
        mWord.setMeaningScore(-10); assertEquals(0, mWord.getMeaningScore());
        mWord.setMeaningScore(110); assertEquals(100, mWord.getMeaningScore());
    }

    @Test
    public void translationsTests() {
        Translation t0 = new Translation("Translation 0");
        Translation t1 = new Translation("Translation 1");
        t1.addSample("Translation 1. Sample 0");
        t1.addSample("Translation 1. Sample 1");
        assertEquals(0, mWord.addTranslation(t0));
        assertEquals(1, mWord.addTranslation(t1));
        assertEquals(2, mWord.getTranslations().size());
        assertEquals(t0, mWord.removeTranslation(0));
        assertEquals(1, mWord.getTranslations().size());
        //remove wrong Translation instance
        assertFalse(mWord.removeTranslation(t0));
        assertEquals(1, mWord.getTranslations().size());
        mWord.removeTranslation(t1); //remove appropriate Translation instance
        assertEquals(0, mWord.getTranslations().size());

        ArrayList<Translation> l = new ArrayList<>();
        l.add(t0); l.add(0, t1);
        mWord.setTranslations(l);
        assertEquals("Translation 1; Translation 0", mWord.getTranslationsAsString());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeTest(){
        mWord.removeTranslation(10);
    }

    @Test
    public void wordSamplesTest(){
        SamplesList sl = mWord.getSamplesList();
        assertEquals(0, sl.size());
    }

    @Test
    public void toJsonObjectTest() throws JSONException {
        long UUID = 1577023120108L;
        mWord.setUUID(UUID);

        // Тест для першого випадку: базовий об’єкт Word
        String expectedJson1 = "{\"IS_LEARNED\":false,\"IS_LEARNING\":false," +
                "\"IS_PROBLEM\":false,\"IS_STARRED\":false," +
                "\"LAST_SCORE\":0,\"MEANING_CHECK_DATE\":\"\"," +
                "\"MEANING_SCORE\":0,\"UUID\":1577023120108," +
                "\"WORD_NOTE\":\"\",\"PRONOUNCES\":[],\"TRANSLATIONS\":[]," +
                "\"AUDIO_SAMPLES\":[],\"SAMPLE_LIST\":[]}";
        JSONObject expectedObj1 = new JSONObject(expectedJson1);
        JSONObject actualObj1 = mWord.toJsonObject();
        assertTrue(JsonUtils.jsonObjectEquals(expectedObj1, actualObj1));

        // Тест для другого випадку: Word з AudioSamples і SamplesList
        ArrayList<AudioSample> as = new ArrayList<>();
        as.add(new AudioSample("word"));
        mWord.setAudioSamples(as);
        SamplesList sl = mWord.getSamplesList();
        sl.add("Sample 1");
        sl.add("Sample 2");

        String expectedJson2 = "{\"IS_LEARNED\":false,\"IS_LEARNING\":false," +
                "\"IS_PROBLEM\":false,\"IS_STARRED\":false," +
                "\"LAST_SCORE\":0,\"MEANING_CHECK_DATE\":\"\"," +
                "\"MEANING_SCORE\":0,\"UUID\":1577023120108," +
                "\"WORD_NOTE\":\"\",\"PRONOUNCES\":[],\"TRANSLATIONS\":[]," +
                "\"AUDIO_SAMPLES\":[{\"name\":\"word\",\"comment\":\"\",\"url\":\"\"}]," +
                "\"SAMPLE_LIST\":[\"Sample 1\",\"Sample 2\"]}";
        JSONObject expectedObj2 = new JSONObject(expectedJson2);
        JSONObject actualObj2 = mWord.toJsonObject();
        assertTrue(JsonUtils.jsonObjectEquals(expectedObj2, actualObj2));

        // Тест для третього випадку: об’єкт Word з повними даними
        Word other = getOther();
        other.setUUID(UUID);

        String expectedJson3 = "{\"IS_LEARNED\":true,\"IS_LEARNING\":false," +
                "\"IS_PROBLEM\":false,\"IS_STARRED\":false,\"LAST_SCORE\":0," +
                "\"MEANING_CHECK_DATE\":\"2018-03-15\",\"MEANING_SCORE\":3," +
                "\"UUID\":1577023120108,\"WORD_NOTE\":\"Some note\"," +
                "\"PRONOUNCES\":[{\"ipa\":\"əˈlɒt\",\"memo\":\"PR\"}," +
                "{\"ipa\":\"əˈlɑt\",\"memo\":\"\"}]," +
                "\"TRANSLATIONS\":[{\"translation\":\"багато\",\"note\":\"\"," +
                "\"samples\":[\"We walked a lot.\",\"She works a lot.\"]}," +
                "{\"translation\":\"повно\",\"note\":\"\",\"samples\":[]}]," +
                "\"AUDIO_SAMPLES\":[{\"name\":\"En-uk-a_lot\",\"comment\":\"Audio (UK)\"," +
                "\"url\":\"https:\\/\\/upload.wikimedia.org\\/wikipedia\\/commons\\/3\\/39\\/En-uk-a_lot.ogg\"," +
                "\"FILE_NAME\":\"En-uk-a_lot.ogg\"}]," +
                "\"SAMPLE_LIST\":[]}";
        JSONObject expectedObj3 = new JSONObject(expectedJson3);
        JSONObject actualObj3 = other.toJsonObject();
        assertTrue(JsonUtils.jsonObjectEquals(expectedObj3, actualObj3));
    }

    private Word getOther(){
        Word w = new Word("a lot");

        Translation t = new Translation("багато");
        t.addSample("We walked a lot.");
        t.addSample("She works a lot.");
        w.addTranslation(t);
        w.addTranslation(new Translation("повно"));

        Pronounce.TextPronounce tp0 = new Pronounce.TextPronounce("əˈlɒt");
        tp0.setMemo("PR");
        Pronounce.TextPronounce tp1 = new Pronounce.TextPronounce("əˈlɑt");
        Pronounce p = new Pronounce();
        p.addTextPronounce(tp0); p.addTextPronounce(tp1);
        w.setPronounce(p);

        AudioSample as = new AudioSample("En-uk-a_lot");
        as.setUrl("https://upload.wikimedia.org/wikipedia/commons/3/39/En-uk-a_lot.ogg");
        as.setComment("Audio (UK)");
        as.setFile(new File("En-uk-a_lot.ogg"));
        ArrayList<AudioSample> asl = new ArrayList<>(); asl.add(as);
        w.setAudioSamples(asl);

        w.setNote("Some note");
        w.setLearned(true); w.setMeaningScore(50);
        w.setMeaningCheckDate("2018-03-15"); w.setMeaningScore(3);

        return w;
    }
}