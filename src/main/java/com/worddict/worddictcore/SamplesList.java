package com.worddict.worddictcore;

//import androidx.annotation.NonNull;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class describes a set of samples. You can use this class as word or
 * translation samples, etc.
 */
public class SamplesList implements Serializable, Iterable<String> {
    /**
     * A list contains samples.
     */
    private ArrayList<String> mSamples;

    public SamplesList(){
        mSamples = new ArrayList<>();
    }

    /**
     * Set {@link ArrayList<String>} with samples.
     * @param samples samples collection
     */
    public void setSamples(ArrayList<String> samples) {
        this.mSamples = samples;
    }

    /**
     * Appends sample to end of {@link ArrayList} samples set.
     * @param sample sample to be appended; If param is null or empty string
     *              insertion will omit
     * @return position of added sample. Numbering starts at 0.
     * If sample not added returns negative value
     */
    public int add(String sample){
        if (sample == null || sample.isEmpty()) return -1;
        String prepared = prepareSample(sample);
        mSamples.add(prepared);
        return mSamples.size()-1;
    }

    /**
     * Removes the first occurrence of sample if it is present.
     * @param sample sample to be removed from sample set, if present
     * @return true if sample was removed; false otherwise
     */
    public boolean remove(String sample){
        if (sample == null || sample.isEmpty()) return false;
        String prepared = prepareSample(sample);
        return mSamples.remove(prepared);
    }

    /**
     * Removes the sample at the specified position.
     * @param index the index of the object to remove.
     * @return true if sample was removed; false otherwise
     */
    public boolean remove(int index){
        try{
            mSamples.remove(index);
            return true;
        } catch (Exception ignore){
            return false;
        }
    }

    /**
     * Returns a sample at the specified location.
     * @param index the index of the element to return.
     * @return the element at the specified index; null if index is wrong.
     */
    public String get(int index){
        try {
            return mSamples.get(index);
        } catch (Exception ignore){
            return null;
        }

    }

    /**
     * Replaces the element at the specified position in set with the specified element.
     * @param index index of the element to replace.
     * @param sample element to be stored at the specified position. If sample
     *               is null or empty string the element will remove.
     * @return true if the operation has a success; false otherwise.
     * Returns false if the element was removed.
     */
    public boolean set(int index, String sample){
        try {
            if (sample != null && !sample.isEmpty()) {
                mSamples.set(index, prepareSample(sample));
                return true;
            }
            mSamples.remove(index);
            return false;
        } catch (Exception ignore) {
            return false;
        }
    }

    /**
     * Returns the number of elements
     * @return the number of elements in this list.
     */
    public int size(){
        return mSamples.size();
    }

    /**
     * Returns if this list contains no elements.
     * @return true if this list has no elements, false otherwise
     */
    public boolean isEmpty(){
        return mSamples.isEmpty();
    }

    private String prepareSample(String sample){
        return sample.trim();
    }

    /**
     * Serialize this class to {@link JSONArray}
     * @return JSONArray instance
     */
    public JSONArray toJsonArray(){
        JSONArray samples = new JSONArray();
        for (String sample: mSamples) samples.put(sample);
        return samples;
    }

    //@NonNull
    @Override
    public Iterator<String> iterator() {
        return mSamples.iterator();
    }


    /**
     * Searches this list for the specified string and returns the index of
     * the first occurrence.
     * @param element the sample string to search for
     * @return the index of the first occurrence of the element, or -1 if it was not found.
     */
    public int indexOf(String element){
        return mSamples.indexOf(element);
    }

    //@NonNull
    @Override
    public String toString() {
        return "SamplesList" + mSamples;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SamplesList that = (SamplesList) o;

        return mSamples != null ? mSamples.equals(that.mSamples) : that.mSamples == null;
    }

    @Override
    public int hashCode() {
        return mSamples != null ? mSamples.hashCode() : 0;
    }
}
