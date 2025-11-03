package com.worddict.worddictcore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * It describes translation of word and can contain samples and note.<br>
 * Created by Michael Kazarian on 15.04.16.
 */
public class Translation implements Serializable{
    private String mTranslation;
    private SamplesList mSamples;
    private String mNote;

    /**
     * Constructor with a specified translation.
     * @param translation translation value.
     */
    public Translation(String translation) {
        this.mTranslation = translation;
        mSamples = new SamplesList();
        mNote = "";
    }

    /**
     * Method for obtain translation.
     * @return translation value.
     */
    public String getTranslation() {
        return mTranslation;
    }

    /**
     * Set translation value.
     * @param translation {@link String} value.
     */
    public void setTranslation(String translation) {
        this.mTranslation = translation;
    }

    /**
     * Method for obtain samples for translation.
     * @return {@link ArrayList<String>}  with samples.
     */
    public SamplesList getSamples() {
        return mSamples;
    }

    /**
     * Set {@link ArrayList<String>} with samples.
     * @param samples the {@link ArrayList} contains samples
     */
    public void setSamples(ArrayList<String> samples) {
        if (this.mSamples == null) mSamples = new SamplesList();
        mSamples.setSamples(samples);
    }

    /**
     * Appends sample for translation to end of {@link ArrayList} samples.
     * @param sample sample to be appended to this list
     * @return position of added sample.
     */
    public int addSample(String sample){
        mSamples.add(sample);
        return mSamples.size();
    }

    /**
     * Removes the first occurrence of sample if it is present.
     * @param sample sample to be removed from this sample {@link ArrayList}, if present
     * @return true if this list contained the sample
     */
    public boolean removeSample(String sample){
        return mSamples.remove(sample);
    }

    /**
     * Replaces the element at the specified position in {@link ArrayList} with the specified element (optional operation).
     * @param index index of the element to replace.
     * @param sample element to be stored at the specified position.
     * @return the element previously at the specified position.
     */
    public boolean setSample(int index, String sample){
        return mSamples.set(index, sample);
    }

    /**
     * Removes the sample at the specified position. Shifts any subsequent elements to the left
     * (subtracts one from their indices).
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     */
    public boolean removeSample(int index){
        return mSamples.remove(index);
    }

    /**
     * Returns translation's note.
     * @return String contains a note; empty toString if note wasn't set.
     */
    public String getNote() {
        return mNote;
    }

     /**
     * Set note value
     * @param note value
     */
    public void setNote(String note) {
        if (note != null) this.mNote = note;
        else this.mNote = "";
    }

     /**
     * Serialize this class to {@link JSONObject}
     * @return JSONObject instance if operation successfully; null otherwise.
     */
     public JSONObject toJsonObject(){
        try {
            JSONObject result = new JSONObject();
            result.put("translation", mTranslation);
            result.put("note", mNote);
            result.put("samples", mSamples.toJsonArray());
            return result;
        }
        catch(JSONException ex) { ex.printStackTrace(); }
        return null;
    }

    //@NonNull
    @Override
    public String toString() {
        return "Translation {" +
                "translation='" + mTranslation + '\'' +
                ", samples=" + mSamples.toString() +
                ", note='" + mNote + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Translation)) return false;

        Translation that = (Translation) other;

        if (!mTranslation.equals(that.mTranslation)) return false;
        if (!mSamples.equals(that.mSamples)) return false;
        return mNote.equals(that.mNote);

    }

    @Override
    public int hashCode() {
        int result = mTranslation.hashCode();
        result = 31 * result + mSamples.hashCode();
        result = 31 * result + mNote.hashCode();
        return result;
    }
}
