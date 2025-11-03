package com.worddict.worddictcore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JsonUtilsTest {

    @Test
    public void testJsonObjectEquals_SameObjects() throws JSONException {
        JSONObject obj1 = new JSONObject("{\"key1\":\"value1\",\"key2\":\"value2\"}");
        JSONObject obj2 = new JSONObject("{\"key2\":\"value2\",\"key1\":\"value1\"}");
        assertTrue(JsonUtils.jsonObjectEquals(obj1, obj2));
    }

    @Test
    public void testJsonObjectEquals_DifferentValues() throws JSONException {
        JSONObject obj1 = new JSONObject("{\"key1\":\"value1\",\"key2\":\"value2\"}");
        JSONObject obj2 = new JSONObject("{\"key1\":\"value3\",\"key2\":\"value2\"}");
        assertFalse(JsonUtils.jsonObjectEquals(obj1, obj2));
    }

    @Test
    public void testJsonObjectEquals_DifferentKeys() throws JSONException {
        JSONObject obj1 = new JSONObject("{\"key1\":\"value1\",\"key2\":\"value2\"}");
        JSONObject obj2 = new JSONObject("{\"key1\":\"value1\",\"key3\":\"value2\"}");
        assertFalse(JsonUtils.jsonObjectEquals(obj1, obj2));
    }

    @Test
    public void testJsonObjectEquals_NullObjects() throws JSONException {
        assertTrue(JsonUtils.jsonObjectEquals(null, null));
        assertFalse(JsonUtils.jsonObjectEquals(new JSONObject(), null));
        assertFalse(JsonUtils.jsonObjectEquals(null, new JSONObject()));
    }

    @Test
    public void testJsonObjectEquals_NestedObjects() throws JSONException {
        JSONObject nested1 = new JSONObject("{\"key1\":{\"key2\":\"value2\"}}");
        JSONObject nested2 = new JSONObject("{\"key1\":{\"key2\":\"value2\"}}");
        assertTrue(JsonUtils.jsonObjectEquals(nested1, nested2));
    }

    @Test
    public void testJsonArrayEquals_SameArrays() throws JSONException {
        JSONArray array1 = new JSONArray("[{\"key1\":\"value1\",\"key2\":\"value2\"},{\"key3\":\"value3\",\"key4\":\"value4\"}]");
        JSONArray array2 = new JSONArray("[{\"key2\":\"value2\",\"key1\":\"value1\"},{\"key4\":\"value4\",\"key3\":\"value3\"}]");
        assertTrue(JsonUtils.jsonArrayEquals(array1, array2));
    }

    @Test
    public void testJsonArrayEquals_DifferentArrays() throws JSONException {
        JSONArray array1 = new JSONArray("[{\"key1\":\"value1\",\"key2\":\"value2\"}]");
        JSONArray array2 = new JSONArray("[{\"key1\":\"value3\",\"key2\":\"value2\"}]");
        assertFalse(JsonUtils.jsonArrayEquals(array1, array2));
    }

    @Test
    public void testJsonArrayEquals_DifferentLengths() throws JSONException {
        JSONArray array1 = new JSONArray("[{\"key1\":\"value1\",\"key2\":\"value2\"}]");
        JSONArray array2 = new JSONArray("[{\"key1\":\"value1\",\"key2\":\"value2\"},{\"key3\":\"value3\",\"key4\":\"value4\"}]");
        assertFalse(JsonUtils.jsonArrayEquals(array1, array2));
    }

    @Test
    public void testJsonArrayEquals_NullArrays() throws JSONException {
        assertTrue(JsonUtils.jsonArrayEquals(null, null));
        assertFalse(JsonUtils.jsonArrayEquals(new JSONArray(), null));
        assertFalse(JsonUtils.jsonArrayEquals(null, new JSONArray()));
    }

    @Test
    public void testJsonArrayEquals_NestedArrays() throws JSONException {
        JSONArray array1 = new JSONArray("[{\"key1\":[\"value1\",\"value2\"]}]");
        JSONArray array2 = new JSONArray("[{\"key1\":[\"value1\",\"value2\"]}]");
        assertTrue(JsonUtils.jsonArrayEquals(array1, array2));
    }
}