package com.worddict.worddictcore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    /**
     * Compares two JSON arrays for equality, ignoring the order of keys in objects.
     *
     * @param expected The expected JSONArray
     * @param actual   The actual JSONArray
     * @return true if the arrays are equal, false otherwise
     * @throws JSONException if there's an error parsing JSON
     */
    public static boolean jsonArrayEquals(JSONArray expected, JSONArray actual) throws JSONException {
        if (expected == null || actual == null) {
            return expected == actual;
        }
        if (expected.length() != actual.length()) {
            return false;
        }
        for (int i = 0; i < expected.length(); i++) {
            Object expectedObj = expected.get(i);
            Object actualObj = actual.get(i);
            if (expectedObj instanceof JSONObject && actualObj instanceof JSONObject) {
                if (!jsonObjectEquals((JSONObject) expectedObj, (JSONObject) actualObj)) {
                    return false;
                }
            } else if (!expectedObj.equals(actualObj)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Compares two JSON objects for equality, ignoring the order of keys.
     *
     * @param expected The expected JSONObject
     * @param actual   The actual JSONObject
     * @return true if the objects are equal, false otherwise
     * @throws JSONException if there's an error parsing JSON
     */
    public static boolean jsonObjectEquals(JSONObject expected, JSONObject actual) throws JSONException {
        if (expected == null || actual == null) {
            return expected == actual;
        }
        if (!expected.keySet().equals(actual.keySet())) {
            return false;
        }
        for (String key : expected.keySet()) {
            Object expectedValue = expected.get(key);
            Object actualValue = actual.get(key);
            if (expectedValue instanceof JSONObject && actualValue instanceof JSONObject) {
                if (!jsonObjectEquals((JSONObject) expectedValue, (JSONObject) actualValue)) {
                    return false;
                }
            } else if (expectedValue instanceof JSONArray && actualValue instanceof JSONArray) {
                if (!jsonArrayEquals((JSONArray) expectedValue, (JSONArray) actualValue)) {
                    return false;
                }
            } else if (!expectedValue.equals(actualValue)) {
                return false;
            }
        }
        return true;
    }
}
