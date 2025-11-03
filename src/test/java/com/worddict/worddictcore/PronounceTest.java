package com.worddict.worddictcore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PronounceTest {
    private Pronounce.TextPronounce mTextPronounce;
    private Pronounce.TextPronounce mUKPronounce, mUSPronounce;
    private static Pronounce mPronounce;

    @Before
    public void setUp() {
        mTextPronounce = new Pronounce.TextPronounce();

        mUKPronounce = new Pronounce.TextPronounce("əd.ˈvɑːn.tɪdʒ");
        mUKPronounce.setMemo("UK");

        mUSPronounce = new Pronounce.TextPronounce("əd.ˈvæn.tɪdʒ");
        mUSPronounce.setMemo("US");
        mPronounce = new Pronounce();
    }

    @Test
    public void textPronounceTests() {
        assertEquals("", mTextPronounce.getText());
        assertEquals("", mTextPronounce.getMemo());
        assertEquals("əd.ˈvɑːn.tɪdʒ", mUKPronounce.getText());
        assertEquals("UK", mUKPronounce.getMemo());
        assertEquals("əd.ˈvæn.tɪdʒ", mUSPronounce.getText());
        assertEquals("US", mUSPronounce.getMemo());
    }

    @Test
    public void textPronounceEqualityTest() {
        mTextPronounce.setText("əd.ˈvæn.tɪdʒ"); mTextPronounce.setMemo("US");
        assertEquals(mUSPronounce, mTextPronounce);
        assertNotEquals(mUKPronounce, mUSPronounce);
    }

    @Test
    public void textPronounceToStringTest() {
        assertEquals("TextPronounce { text='', memo=''}", mTextPronounce.toString());
        assertEquals("TextPronounce { text='əd.ˈvæn.tɪdʒ', memo='US'}", mUSPronounce.toString());
    }

    @Test
    public void textPronounceHashCodeTest() {
        assertEquals(0, mTextPronounce.hashCode());
        assertEquals(-585310811, mUSPronounce.hashCode());
        assertEquals(829489474, mUKPronounce.hashCode());
        mUKPronounce.setMemo("");
        assertNotEquals(829489474, mUKPronounce.hashCode());
    }

    @Test
    public void textPronounceToJSONTest() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("ipa", "");
        json.put("memo", "");
        assertEquals(json.toString(), mTextPronounce.toJsonObject().toString());
        json = new JSONObject();
        json.put("ipa", "əd.ˈvæn.tɪdʒ");
        json.put("memo", "US");
        assertEquals(json.toString(), mUSPronounce.toJsonObject().toString());
    }

    @Test
    public void getTextPronouncesTest() {
        mPronounce.addTextPronounce(mTextPronounce);
        assertEquals(1, mPronounce.textPronounceQuantity());
        ArrayList<Pronounce.TextPronounce> tpl = new ArrayList<>();
        tpl.add(mUKPronounce); tpl.add(mUSPronounce);
        mPronounce.setPronounces(tpl);
        assertEquals(2, mPronounce.textPronounceQuantity());
        mPronounce.addTextPronounce(mTextPronounce);
        assertEquals(3, mPronounce.textPronounceQuantity());
    }

    @Test
    public void getTextPronounceJSONArrayTest() throws JSONException {
        ArrayList<Pronounce.TextPronounce> tpl = new ArrayList<>();
        tpl.add(mUKPronounce);
        tpl.add(mUSPronounce);
        mPronounce.setPronounces(tpl);

        String expectedJson =
                "[{\"ipa\":\"əd.ˈvɑːn.tɪdʒ\",\"memo\":\"UK\"},"+
                "{\"ipa\":\"əd.ˈvæn.tɪdʒ\",\"memo\":\"US\"}]";
        JSONArray expected = new JSONArray(expectedJson);
        JSONArray actual = mPronounce.getTextPronounceJSONArray();
        assertTrue(JsonUtils.jsonArrayEquals(expected, actual));
    }
}