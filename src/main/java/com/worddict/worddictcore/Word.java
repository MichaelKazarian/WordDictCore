package com.worddict.worddictcore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * It describes Word and can contain zero or more {@link Translation}s, Pronounces.
 * Word can contain samples of use. {@link Translation} can contain samples
 * too, but word's samples are more general, whereas translation samples are
 * more specific.
 * This class provides following states:
 *     <ol>
 *         <li>Learned/Unlearned</li>
 *         <li>Starred</li>
 *         <li>Problems</li>
 *     </ol>
 * Created by Michael Kazarian on 15.04.16.
 */
public class Word implements Serializable{
    //States
    private boolean mLearned      = false;
    private boolean mStarred      = false;
    private boolean mProblem      = false;
    private boolean mLearning     = false;
    private boolean mHasAudio     = false;
    private boolean mProposal     = false;
    private long mUUID; //use time stamp for it.

    private ArrayList<Translation> mTranslationsList;
    private ArrayList<AudioSample> mAudioSamplesList;
    private SamplesList mSamplesList;
    private String mWord, mNote;
    private String mMeaningCheckDate = "";
    private Pronounce mPronounce;
    private int  mLastScore = 0;
    private int mMeaningScore = 0;

    /**
     * Constructor with a specified word. Constructor generate unique ID on each invoke.
     * So, if you load {@link Word} instance from any persistence state call
     * {@link Word#setUUID} after constructor calls for restore original ID.
     * @param word word value.
     */
    public Word(String word) {
        this.mWord = prepareName(word);
        mTranslationsList = new ArrayList<>();
        mAudioSamplesList = new ArrayList<>();
        mSamplesList = new SamplesList();
        mUUID = System.currentTimeMillis();
        mPronounce = new Pronounce();
        mNote="";
    }

    /**
     * Returns learned state.
     * @return true if word is learned; false otherwise.
     */
    public boolean isLearned() {
        return this.mLearned;
    }

    /**
     * Set learned state.
     * @param learned true if word is learned; false otherwise.
     */
    public void setLearned(boolean learned) {
        this.mLearned = learned;
    }

    /**
     * Returns starred state.
     * @return true if word is starred; false otherwise.
     */
    public boolean isStarred() {
        return this.mStarred;
    }

    /**
     * Set starred state.
     * @param starred true if word is starred; false otherwise.
     */
    public void setStarred(boolean starred) {
        this.mStarred = starred;
    }

    /**
     * Returns problem state.
     * @return true if word is problem; false otherwise.
     */
    public boolean isProblem() {
        return this.mProblem;
    }

    /**
     * Set problem state.
     * @param problem true if word is problem; false otherwise.
     */
    public void setProblem(boolean problem) {
        this.mProblem = problem;
    }

    /**
     * Returns learn state.
     * @return true if word learning; false otherwise.
     */
    public boolean isLearning() {
        return mLearning;
    }

    /**
     * Set learn state
     * @param learning true if word learning; false otherwise.
     */
    public void setLearning(boolean learning) {
        this.mLearning = learning;
    }

    public boolean isProposal() {
        return mProposal;
    }

    public void setProposal(boolean mProposal) {
        this.mProposal = mProposal;
    }

    /**
     * Returns a {@link ArrayList} containing {@link Translation}s
     * @return a {@link ArrayList} containing {@link Translation} elements.
     */
    public ArrayList<Translation> getTranslations() {
        return this.mTranslationsList;
    }

    /**
     * Set a {@link ArrayList} containing {@link Translation}s
     * @param translations a {@link ArrayList} containing {@link Translation} elements.
     */
    public void setTranslations(ArrayList<Translation> translations) {
        this.mTranslationsList = translations;
    }

    /**
     * Returns pretty strings representations of translations.
     * @return strings representations of all translations if translations presents. Empty string
     * otherwise.
     */
    public String getTranslationsAsString(){
        StringBuilder result = new StringBuilder();
        for (Translation translation : mTranslationsList) {
            if (! translation.getTranslation().isEmpty())
                result.append("; ").append(translation.getTranslation());
        }
        int l = result.length();
        if (l > 2) return result.substring(2, l);
        return result.toString();
    }

    public ArrayList<AudioSample> getAudioSamples(){
        return this.mAudioSamplesList;
    }

    public void setAudioSamples(ArrayList<AudioSample> audiosamples){
        this.mAudioSamplesList = audiosamples;
    }

    /**
     * Appends the specified {@link Translation} element to the end of Translation list.
     * @param translation {@link Translation}
     * @return position of added
     */
    public int addTranslation(Translation translation){
        mTranslationsList.add(translation);
        return mTranslationsList.size()-1;
    }

    /**
     * Removes the first occurrence of translation if it is present.
     * @param translation translation to be removed from this sample {@link ArrayList}, if present
     * @return true if this list contained the
     */
    public boolean removeTranslation(Translation translation){
        return mTranslationsList.remove(translation);
    }

    /**
     * Removes the translation at the specified position. Shifts any subsequent elements to the
     * left (subtracts one from their indices).
     * @param index the index of the element to be removed.
     * @return the element that was removed from the list.
     */
    public Translation removeTranslation(int index) throws IndexOutOfBoundsException {
        return mTranslationsList.remove(index);
    }

    /**
     * Replaces the element at the specified position in {@link Translation} {@link ArrayList}
     * with the specified element (optional operation).
     * @param index index of the element to replace.
     * @param translation element to be stored at the specified position.
     * @return the element previously at the specified position.
     */
    public Translation set(int index, Translation translation){
        return mTranslationsList.set(index, translation);
    }

    /**
     * Return word value.
     * @return word value.
     */
    public String getWord() {
        return this.mWord;
    }

    /**
     * Set new value of word.
     * @param word word value.
     */
    public void setWord(String word) {
        this.mWord = prepareName(word);
    }

    /**
     * Returns unique UUID of instance.
     *
     * @return a <code>long</code> value represents a time stamp when word has been created.
     */
    public long getUUID(){
        return this.mUUID;
    }
    
    /**
     * Set unique UUID of instance.
     *
     * @param uuid a <code>long</code> value for time stamp. If you  create
     * {@link Word} instance from any persistent (e.g. from read from file) call
     * this method for set unique ID after constructor has been called.
     */
    public void setUUID(long uuid){
        this.mUUID = uuid;
    }

    /**
     * Returns {@link Word}s pronounce.
     * @return Pronounce instance.
     */
    public Pronounce getPronounce(){
        return mPronounce;
    }

    /**
     * Set Pronounce instance.
     * @param pronounce Pronounce instance.
     */
    public void setPronounce(Pronounce pronounce){
        this.mPronounce = pronounce;
    }

    /**
     * Get the last score.
     * @return a <code>int</code> value. Negative value mean
     * that some answers was wrong.
     */
    public int getLastScore() {
        return mLastScore;
    }

    /**
     * Set the score of last word meaning.
     * @param score int score value. Set negative value if some answer is wrong.
     */
    public void setLastScore(int score) {
        this.mLastScore = score;
    }

    /**
     * Returns the date of the last word meaning check
     * @return a String value in YYYY-MM-DD format.
     */
    public String getMeaningCheckDate() {
        return mMeaningCheckDate;
    }

    /**
     * Set the the date of the last word meaning check
     * @param knowledgeCheckDate the String value in YYYY-MM-DD format.
     */
    public void setMeaningCheckDate(String knowledgeCheckDate) {
        this.mMeaningCheckDate = knowledgeCheckDate;
    }

    /**
     * Returns the meaning score value.
     * @return a value in range from 1 to 12 where 1 - is poor but 12 is excelent.
     */
    public final int getMeaningScore() {
        return mMeaningScore;
    }

    /**
     * Set the meaning score value.
     * @param knowledgeScore value in range from 0 to 100 where 0 is initial
     *                       value; 1 is poor; 100 is excellent. If value
     *                       beside this range it will transform to nearest.
     */
    public void setMeaningScore(int knowledgeScore) {
        int r = knowledgeScore;
        if (r <= 0) r = 0;
        if (r > Constants.MAX_WORD_SCORE) r = Constants.MAX_WORD_SCORE;
        this.mMeaningScore = r;
    }

    /**
     * Serialize this class to {@link JSONObject}
     * @return JSONObject instance if operation successfully; null otherwise.
     */
    public JSONObject toJsonObject(){
        try {
            JSONObject result = new JSONObject();
            result.put(Constants.IS_LEARNED, mLearned);
            result.put(Constants.IS_LEARNING, mLearning);
            result.put(Constants.IS_PROBLEM, mProblem);
            result.put(Constants.IS_STARRED, mStarred);
            result.put(Constants.LAST_SCORE, mLastScore);
            result.put(Constants.MEANING_CHECK_DATE, mMeaningCheckDate);
            result.put(Constants.MEANING_SCORE, mMeaningScore);
            result.put(Constants.UUID, mUUID);
            result.put(Constants.NOTE, mNote);
            result.put("PRONOUNCES", mPronounce.getTextPronounceJSONArray());

            JSONArray translations = new JSONArray();
            for (Translation tr: mTranslationsList) translations.put(tr.toJsonObject());
            result.put("TRANSLATIONS", translations);

            JSONArray audioSamples = new JSONArray();
            for (AudioSample as: mAudioSamplesList) audioSamples.put(as.toJsonObject());
            result.put("AUDIO_SAMPLES", audioSamples);

            JSONArray samples = new JSONArray();
            for (String sample: mSamplesList) samples.put(sample);
            result.put("SAMPLE_LIST", samples);

            return result;
        }
        catch(JSONException ex) { ex.printStackTrace(); }
        return null;
    }

    /**
     * Obtain has audio samples.
     * @return true if contains; false otherwise.
     */
    public boolean isHasAudio() {
        return !getAudioSamples().isEmpty() || mHasAudio;
    }

    /**
     * Set flag instance has audio samples. Use it if you need minimalistic word load.
     * @param hasAudio  true if contains; false otherwise.
     */
    public void setHasAudio(boolean hasAudio) {
        mHasAudio = hasAudio;
    }

    /**
     * Returns words note.
     * @return note string; empty string if note absent.
     */
    public String getNote() {
        return mNote;
    }

    /**
     * Set word words note.
     * @param note note string; empty string if note absent.
     */
    public void setNote(String note) {
        mNote = note;
    }

    /**
     * Returns word's samples
     * @return the {@link SamplesList} instance
     */
    public SamplesList getSamplesList() {
        return mSamplesList;
    }

    /**
     * Prepare word's name.
     * @param word string to prepare
     * @return prepared word name.
     */
    public String prepareName(String word){
        return word.trim();
    }
}
