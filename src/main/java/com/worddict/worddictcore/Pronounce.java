package com.worddict.worddictcore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.ArrayList;

/**
   Class contains texts and sounds version of {@link Word} pronounce.
 * Created by kazarian on 18.06.16.
 */
public class Pronounce implements Serializable {
    private ArrayList<TextPronounce> mTextPronounces = new ArrayList<>();

    /**
     * Add TextPronounce instance. 
     * @param pronounce
     */
    public void addTextPronounce(TextPronounce pronounce){
        mTextPronounces.add(pronounce);
    }
    /**
     * Returns ArrayList contains instances of TextPronounce.
     * @return
     */
    public ArrayList<TextPronounce> getTextPronounces(){
        return mTextPronounces;
    }
    /**
     * Set {@link ArrayList} with TextPronounce.
     * @param pronounces ArrayList<TextPronounce> contains TextPronounce
     * instances.
     */
    public void setPronounces(ArrayList<TextPronounce> pronounces){
        this.mTextPronounces = pronounces;
    }
    /**
     * Returns quantity of text pronounces.
     * @return
     */
    public int textPronounceQuantity(){
        return mTextPronounces.size();
    }

    /**
     * Serialize text pronounces to {@link JSONArray}
     * @return JSONArray contains {@link TextPronounce} or empty array.
     */
    public JSONArray getTextPronounceJSONArray(){
        JSONArray result = new JSONArray();
        for (TextPronounce pr: getTextPronounces())
            result.put(pr.toJsonObject());
        return result;
    }
    
    /**
     * Describe text description of pronounce.
     */
    public static class TextPronounce implements Serializable{
        private String mText;
        private String mMemo;
        
        /**
         * Creates a new <code>TextPronounce</code> instance with epmty
         * pronounce and memo.
         */
        public TextPronounce(){
            this("", "");
        }
        
        /**
         * Creates a new <code>TextPronounce</code> instance with some.
         * pronounce and empty memo.
         * @param pronounce text of pronounce.
         */
        public TextPronounce(String pronounce){
            this(pronounce, "");
        }
        
        /**
         * Creates a new <code>TextPronounce</code> instance with pronounce
         * and memo.
         * @param pronounce text of pronounce.
         * @param memo text of memo.
         */
        public TextPronounce(String pronounce, String memo){
            this.mText = pronounce;
            this.mMemo = memo;
        }        
        /**
         * Gets text version of pronounce.
         * @return
         */
        public String getText() {
            return this.mText;
        }
        /**
         * Sets the text version of pronounce.
         * @param pronounce text pronounce.
         */
        public void setText(String pronounce) {
            this.mText = pronounce;
        }
        /**
         * Gets memo for text pronounce.
         * @return memo for text pronounce.
         */
        public String getMemo() {
            return this.mMemo;
        }
        /**
         * Sets memo for text pronounce.
         * @param memo memo for text pronounce.
         */
        public void setMemo(String memo) {
            this.mMemo = memo;
        }

        /**
         * Serialize this class to {@link JSONObject}
         * @return JSONObject instance if operation successfully; null otherwise.
         */
        public JSONObject toJsonObject(){
            try {
                JSONObject result = new JSONObject();
                result.put("ipa", mText);
                result.put("memo", mMemo);
                return result;
            }
            catch(JSONException ex) { ex.printStackTrace(); }
            return null;
        }
        @Override
        public String toString() {
            return "TextPronounce { " +
                    "text='" + mText + '\'' +
                    ", memo='" + mMemo + '\'' +
                    '}';
        }
        @Override
        public boolean equals(Object other) {
            if (!(other instanceof TextPronounce))
                return false;
            if (other == this)
                return true;
            TextPronounce tp = (TextPronounce) other;
            return mMemo.equals(tp.getMemo())
                    && mText.equals(tp.getText());
        }
        @Override
        public int hashCode() {
            int result = mText.hashCode();
            result = 31 * result + mMemo.hashCode();
            return result;
        }
    }
}
