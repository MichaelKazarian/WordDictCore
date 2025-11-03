package com.worddict.worddictcore;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class AudioSampleTest {

    private static AudioSample mAudioSample;

    @Before
    public void setUp() {
        mAudioSample = new AudioSample("En-uk-accept");
        mAudioSample.setUrl("https://upload.wikimedia.org/wikipedia/commons/f/fb/En-uk-accept.ogg");
        mAudioSample.setComment("Audio (UK)");
        mAudioSample.setFile(new File(System.getProperty("java.io.tmpdir"), "En-uk-accept.ogg"));
    }

    @Test
    public void toJsonObjectTest() throws JSONException {
        String expectedJson1 = "{\"name\":\"En-uk-accept\",\"comment\":\"Audio (UK)\"," +
                "\"url\":\"https:\\/\\/upload.wikimedia.org\\/wikipedia\\/commons\\/f\\/fb\\/En-uk-accept.ogg\"," +
                "\"FILE_NAME\":\"En-uk-accept.ogg\"}";
        JSONObject expectedObj1 = new JSONObject(expectedJson1);
        JSONObject actualObj1 = mAudioSample.toJsonObject();
        assertTrue(JsonUtils.jsonObjectEquals(expectedObj1, actualObj1));

        AudioSample other = new AudioSample("En-uk-accept");
        String expectedJson2 = "{\"name\":\"En-uk-accept\",\"comment\":\"\",\"url\":\"\"}";
        JSONObject expectedObj2 = new JSONObject(expectedJson2);
        JSONObject actualObj2 = other.toJsonObject();
        assertTrue(JsonUtils.jsonObjectEquals(expectedObj2, actualObj2));
    }

    @Test
    public void toStringTest() {
        assertEquals("AudioSample { name = 'En-uk-accept',"+" comment = 'Audio (UK)',"+
                " url = https://upload.wikimedia.org/wikipedia/commons/f/fb/En-uk-accept.ogg',"+
                " file = /tmp/En-uk-accept.ogg }",
                mAudioSample.toString());
        AudioSample other = new AudioSample("En-uk-accept");
        assertEquals("AudioSample { name = 'En-uk-accept', comment = '',"+
                " url = ', file = null }",
                other.toString());
    }

    @Test
    public void equalsTest() {
        AudioSample other = new AudioSample("En-us-accept");
        other.setUrl("https://upload.wikimedia.org/wikipedia/commons/0/06/En-us-accept.ogg");
        other.setComment("Audio (US)");
        other.setFile(new File(System.getProperty("java.io.tmpdir"), "En-us-accept.ogg"));
        assertNotEquals(mAudioSample, other);

        other = new AudioSample("En-uk-accept");
        other.setUrl("https://upload.wikimedia.org/wikipedia/commons/f/fb/En-uk-accept.ogg");
        other.setComment("Audio (UK)");
        other.setFile(new File(System.getProperty("java.io.tmpdir"), "En-uk-accept.ogg"));
        assertEquals(mAudioSample, other);

        other = new AudioSample("En-uk-accept");
        other.setUrl("https://upload.wikimedia.org/wikipedia/commons/f/fb/En-uk-accept.ogg");
        other.setComment("Audio (UK)");
        other.setFile(new File("En-uk-accept.ogg"));
        assertNotEquals(mAudioSample, other);
    }

    @Test
    public void hashCodeTest() {
        assertEquals(1574224136, mAudioSample.hashCode());

        AudioSample other = new AudioSample("En-uk-accept");
        other.setUrl("https://upload.wikimedia.org/wikipedia/commons/f/fb/En-uk-accept.ogg");
        other.setComment("Audio (UK)");
        other.setFile(new File(System.getProperty("java.io.tmpdir"), "En-uk-accept.ogg"));
        assertEquals(other.hashCode(), mAudioSample.hashCode());

        other.setFile(new File("En-uk-accept.ogg"));
        assertNotEquals(other.hashCode(), mAudioSample.hashCode());
    }
}