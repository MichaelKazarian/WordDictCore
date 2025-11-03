package com.worddict.worddictcore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by kazarian on 21.07.17.
 */

public class DictionaryDescription {
    private static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";
    public static final String DICTIONARY_NAME = "DICTIONARY_NAME";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String TIMESTAMP = "TIMESTAMP";
    public static final String TARGET_LANGUAGE = "TARGET_LANGUAGE";
    public static final String WORDS_COUNT = "WORDS_COUNT";
    public static final String USERS_LANGUAGES = "USERS_LANGUAGES";
    public static final String RAW_SPACE = "RAW_SPACE";

    private String mName, mDescription, mTimeStamp;
    private Language mLearnLanguage;
    private ArrayList<Language> mUsersLanguages;
    private int mWordsCount;
    private long mRawSpace;

    public DictionaryDescription() {
        mName = null;
        mDescription = "";
        mLearnLanguage = Language.UNDEFINED_LANGUAGE;
        mUsersLanguages = new ArrayList<>();
        Date d = Calendar.getInstance().getTime();
        mTimeStamp = new SimpleDateFormat(TIMESTAMP_FORMAT, Locale.ROOT).format(d);
        mWordsCount = -1;
        mRawSpace = 0;
    }

    public DictionaryDescription(JSONObject json) throws JSONException {
        this();
        setName(json.getString(DICTIONARY_NAME));
        setDescription(json.getString(DESCRIPTION));
        setTimeStamp(json.getString(TIMESTAMP));
        setWordsCount(json.getInt(WORDS_COUNT));
        setLearnLanguage(Language.getLanguageByCode(json.getString(TARGET_LANGUAGE)));
        JSONArray la = json.getJSONArray(USERS_LANGUAGES);
        setRawSpace(json.getInt(RAW_SPACE));
        for (int i=0; i<la.length(); i++) {
            Language l = Language.getLanguageByCode(la.getString(i));
            mUsersLanguages.add(l);
        }
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getTimeStamp() {
        return mTimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        mTimeStamp = timeStamp;
    }

    public Language getLearnLanguage() {
        return mLearnLanguage;
    }

    public void setLearnLanguage(Language learnLanguage) {
        mLearnLanguage = learnLanguage;
    }

    public int getWordsCount() {
        return mWordsCount;
    }

    public void setWordsCount(int wordsCount) {
        mWordsCount = wordsCount;
    }

    public ArrayList<Language> getUsersLanguages() {
        return mUsersLanguages;
    }

    public void setUsersLanguages(ArrayList<Language> usersLanguages) {
        mUsersLanguages = usersLanguages;
    }

    public long getRawSpace() {
        return mRawSpace;
    }

    public void setRawSpace(long rawSpace) {
        mRawSpace = rawSpace;
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(DICTIONARY_NAME, getName());
        json.put(DESCRIPTION, getDescription());
        json.put(TIMESTAMP, getTimeStamp());
        json.put(TARGET_LANGUAGE, getLearnLanguage().getLanguageCode());
        json.put(WORDS_COUNT, getWordsCount());
        json.put(RAW_SPACE, getRawSpace());
        JSONArray usersLanguages = new JSONArray();
        for (Language l: mUsersLanguages) {
            usersLanguages.put(l.getLanguageCode());
        }
        json.put(USERS_LANGUAGES, usersLanguages);
        return json;
    }
}
