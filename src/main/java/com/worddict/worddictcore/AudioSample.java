package com.worddict.worddictcore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;

/**
 * Class describes audio sample of word. It can contains name, comment, dirrect url to sample and
 * full path to audio file.
 * Created by kazarian on 21.07.16.
 * TODO: edit sample's name and unbind it from file name.
 */
public class AudioSample implements Serializable{
    private String mName;
    private String mComment;
    private String mUrl;
    private File mFile;

    /**
     * @param name name of audio sample.
     */
    public AudioSample(String name) {
        this.mName = name;
        this.mComment = "";
        this.mUrl = "";
    }
    /**
     * Returns name of audio sample.
     * @return name of audio sample.
     */
    public String getName() {
        return mName;
    }
    /**
     * Set name of audio sample.
     * @param name name of audio sample.
     */
    public void setName(String name) {
        this.mName = name;
    }
    /**
     * Returns comment for audio sample
     * @return comment for audio sample
     */
    public String getComment() {
        return mComment;
    }
    /**
     * Set comment for audio sample.
     * @param comment comment for audio sample.
     */
    public void setComment(String comment) {
        this.mComment = comment;
    }
    /**
     * Returns direct url to audio sample.
     * @return direct url to audio sample.
     */
    public String getUrl() {
        return mUrl;
    }
    /**
     * Set direct url to audio sample.
     * @param url direct url to audio sample.
     */
    public void setUrl(String  url) {
        this.mUrl = url;
    }
    /**
     * Get audio sample file.
     * @return {@link File} instance contains audio file.
     */
    public final File getFile() {
        return mFile;
    }
    /**
     * Set audio sample file.
     *
     * @param file {@link File} instance contains audio file.
     */
    public final void setFile(File file) {
        this.mFile = file;
    }

    /**
     * Serialize this class to {@link JSONObject}
     * @return JSONObject instance if operation successfully; null otherwise.
     */
    public JSONObject toJsonObject(){
        try {
            JSONObject result = new JSONObject();
            result.put(Constants.AUDIO_SAMPLE_NAME, mName);
            result.put(Constants.AUDIO_SAMPLE_COMMENT, mComment);
            result.put(Constants.AUDIO_SAMPLE_URL, mUrl);
            String fileName = null;
            if (mFile != null) fileName = mFile.getName();
            result.put("FILE_NAME", fileName);
            return result;
        }
        catch(JSONException ex) { ex.printStackTrace(); }
        return null;
    }
    @Override
    public String toString() {
        return "AudioSample { " +
                "name = '" + mName + '\'' +
                ", comment = '" + mComment + '\'' +
                ", url = " + mUrl + '\'' +
                ", file = " + mFile +
                " }";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || !(other instanceof AudioSample)) return false;

        AudioSample that = (AudioSample) other;

        if (!mName.equals(that.mName)) return false;
        if (!mComment.equals(that.mComment)) return false;
        if (!mUrl.equals(that.mUrl)) return false;
        return mFile != null ? mFile.equals(that.mFile) : that.mFile == null;
    }

    @Override
    public int hashCode() {
        int result = mName.hashCode();
        result = 31 * result + mComment.hashCode();
        result = 31 * result + mUrl.hashCode();
        result = 31 * result + (mFile != null ? mFile.hashCode() : 0);
        return result;
    }
}