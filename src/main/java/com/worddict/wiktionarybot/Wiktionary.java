package com.worddict.wiktionarybot;

import com.worddict.worddictcore.AudioSample;
import com.worddict.worddictcore.Language;
import com.worddict.worddictcore.Pronounce;
import com.worddict.worddictcore.Translation;
import com.worddict.worddictcore.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.net.URLEncoder;

public abstract class Wiktionary {
    protected String mAPIUrl;  //FROM Wiktionary.java
    protected HashMap<String, String> mCachedPages;

    protected Wiktionary(String abbreviation) {
        String url = "https://%s.wiktionary.org/w/api.php";
        this.mAPIUrl = String.format(url, abbreviation);
        mCachedPages = new HashMap<>();
    }

    public static Wiktionary get(String langCode) {
        switch (langCode) {
            case "en" : return WiktionaryEnglish.newInstance();
            case "de" : return WiktionaryDeutsch.newInstance();
            case "fr" : return WiktionaryFrench.newInstance();
            case "es" : return WiktionarySpanish.newInstance();
            default: return null;
        }
    }
    //========================================================================
    public int search(String word){
        mCachedPages = new HashMap<>();
        String [] pages = getWordVariants(word);
        String wikitext;
        for (String wordVariant: pages){
            wikitext = loadWordArticle(wordVariant);
            if (!wikitext.isEmpty()) {
                wikitext = getLanguageSection(wikitext);
                if (!wikitext.isEmpty())
                    addWikiTextToCache(wordVariant, wikitext);
            }
        }
        return mCachedPages.size();
    }
    //========================================================================
    /**
     * Wiktionary article can contains definitions for several languages
     * (e.g. https://en.wiktionary.org/wiki/test). This method tries to define
     * language section if it is possible.
     * @param article an article to search language section
     * @return Returns language section if possible; all article otherwise
     */
    public String getLanguageSection (String article){
        String res = article;
        //Each Language definition starts from ==Lang e.g. \n==english==\n
        //search from \n==english to next lang n==[^=] or end of string
        String regexp = getLanguageSectionRegexp();
        return Utils.searchRegexp(regexp, article);
    }
    //========================================================================
    /**
     * Search <a href="https://en.wikipedia.org/wiki/International_Phonetic_Alphabet">IPA</a>
     * definitions to search result.
     * @return Array contains <code>Pronounce.TextPronounce</code> instances if
     *         found; empty array otherwise.
     */
    public abstract Pronounce.TextPronounce [] getIPA();
    //========================================================================
    /**
     * Search audio samples to search result.
     * @return Array contains <code>AudioSample</code> instances if found;
     *         empty array otherwise.
     */
    public abstract AudioSample[] getAudioSamples();
    //========================================================================
    /**
     * Search translations to search result to target languages.
     * @param langCodes instance.
     * @return array with translations; empty array if never searched.
     */
    public Translation[] getTranslation(String [] langCodes) {
        LinkedHashSet<Translation> hs = new LinkedHashSet<>();
        for (String wikiText: mCachedPages.values()){
            for (String lng: langCodes) { //search translations;
                String [] tr = Utils.searchAllRegexp("\\|"+lng+"\\|(.+?)[}|]", wikiText, 1);
                for (String s: tr) hs.add(new Translation(s));
            }
        }
        return hs.toArray(new Translation[]{});
    }
    //========================================================================
    /**
     * @return returns regexp to retrieve language section from wiktionary
     * article.
     */
    public abstract String getLanguageSectionRegexp();

    public String [] getWordProposals(String word) {
        try {
            //String candidate = Tools.toTitle(word); //better search
            String url = getSearchUrl(word);
            String json = Utils.getUrlString(url);
            JSONArray jsonBody = new JSONArray(json);
            JSONArray variants = jsonBody.getJSONArray(1);
            String[] result = new String[variants.length()];
            for (int i = 0; i < variants.length(); i++) {
                result[i] = variants.getString(i);
            }
            return result;
        } catch (IOException ioe) {
            //Log.e("ASD", "Failed to fetch URL: ", ioe);
        } catch (JSONException je) {
            //Log.e("ASD", "Failed to parse JSON", je);
        }
        return new String[0];
    }
    //========================================================================
    /**
     * FROM Wiktionary.java
     * Returns possible word variants for wiktionary. For more then 1 word
     * string " " will replace to "_".
     * For exsample:<br/>getWordVariants("test") returns ["test", "Test"];
     * <br/>getWordVariants("credit-deposit ratio") returns ["credit-deposit_ratio"]
     * @param word word.
     * @return Array with word variant. Empty array for empty string.
     *
     * credit-deposit_ratio, creditor's_rights,
     */
    public String [] getWordVariants(String word){
        String [] result = getWordProposals(word);
        Set<String> r = new HashSet<>();
        for (String variant: result) {
            if (languageEquals(word, variant))
                r.add(word.replace(" ", "_"));
        }
        result = r.toArray(new String[r.size()]);
        return result;
    }

    public String [] lookup(String word){
        String [] result = {};
        try {
            String url = getSearchUrl(word, 6);
            String json = Utils.getUrlString(url);
            JSONArray jsonBody = new JSONArray(json);
            JSONArray variants = jsonBody.getJSONArray(1);
            if (variants.length() == 0) return result;
            ArrayList<String> r = new ArrayList<>(); String s;
            for (int i=0; i<variants.length(); i++) {
                s = variants.getString(i);
                r.add(s.replace(" ", "_"));
            }
            result = r.toArray(new String[r.size()]);
            return result;
        } catch (IOException ioe){
            //Log.e("ASD", "Failed to fetch URL: ", ioe);
        } catch (JSONException je) {
            //Log.e("ASD", "Failed to parse JSON", je);
        }
        return result;
    }

    protected String getSearchUrl(String word) {
        return getSearchUrl(word, 10);
    }

    //========================================================================
    /**
     * Generates a Wiktionary search URL for a given word.
     *
     * @param word  The word to search for.
     * @param limit Maximum number of results to return.
     * @return A URL string for the Wiktionary "opensearch" API in JSON format.
     *
     * Example:
     * https://en.wiktionary.org/w/api.php?action=opensearch&search=test&namespace=0&limit=5&format=json&formatversion=2&suggest=true
     */
    protected String getSearchUrl(String word, int limit) {
        String params = String.join("&",
                "action=" + urlEncode("opensearch"),
                "search=" + urlEncode(word),
                "namespace=0",
                "limit=" + limit,
                "format=json",
                "formatversion=2",
                "suggest=true"
        );
        return mAPIUrl + "?" + params;
    }

    //========================================================================
    /**
     * Generates URL to fetch the raw wikitext of a Wiktionary article using the "parse" API.
     *
     * Example result:
     * https://en.wiktionary.org/w/api.php?action=parse&page=test&prop=wikitext&format=json
     *
     * @param word The word to fetch the article for.
     * @return Fully constructed URL to retrieve raw wikitext for the given word.
     */
    protected String getWikiTextUrl(String word) {
        String params = String.join("&",
                "action=" + urlEncode("parse"),
                "page=" + urlEncode(word),
                "prop=" + urlEncode("wikitext"),
                "format=" + urlEncode("json")
        );
        char separator = mAPIUrl.contains("?") ? '&' : '?';
        return mAPIUrl + separator + params;
    }

    private String urlEncode(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return value;
        }
    }

    //========================================================================
    /**
     * Add wikitext content associated with word to cache.
     * @param word word
     * @param wikitext downloaded wikitext
     */
    protected void addWikiTextToCache(String word, String wikitext){
        mCachedPages.put(word, wikitext);
    }
    //========================================================================
    /**
     * Returns cached wikitext.
     * @param word searched word
     * @return wikitext associated with word. null if word not cached.
     */
    protected String getWikiTextPage(String word){
        return mCachedPages.get(word);
    }
    //========================================================================
    /**
     * Language dependent compare word.
     * @param master string to compare
     * @param another the string to compare this instance with.
     * @return true if master is equal to another; false otherwise.
     */
    protected boolean languageEquals(String master, String another){
        return master.equalsIgnoreCase(another);
    }
    //========================================================================

    /**
     * Converts media file from wikitext to URL. See description
     * <a href="https://commons.wikimedia.org/wiki/Commons:FAQ#What_are_the_strangely_named_components_in_file_paths.3F">here</a>
     * @param wikiUrl file name e.g. En-us-test.ogg
     * @return URL like to https://upload.wikimedia.org/wikipedia/commons/9/9c/En-us-test.ogg
     */
    public static String getMediaWikiToURL(String wikiUrl)
            throws UnsupportedEncodingException {
        String fn = normalizeFileName(wikiUrl);
        String md5 = Utils.md5(fn);
        String p1, p2;
        p1 = md5.substring(0, 1);
        p2 = md5.substring(0, 2);
        StringBuilder result = new StringBuilder();
        result.append("https://upload.wikimedia.org/wikipedia/commons/")
                .append(p1).append("/").append(p2).append("/")
                .append(fn);

        return URLDecoder.decode(result.toString(), "UTF-8");
    }
    //========================================================================
    /**
     * Normalize file name to mediaWiki standards
     * See <a href="https://commons.wikimedia.org/wiki/Commons:FAQ#What_are_the_strangely_named_components_in_file_paths.3F">this article</a>  for details.
     * @param name filename
     * @return normalized filename
     */
    public static String normalizeFileName(String name){
        String res = name.trim().replace(" ", "_");
        return Utils.toTitle(res);
    }
    //========================================================================

    /**
     * Create new {@link AudioSample} instance from given file name
     * @param audioFileName File name
     * @return New instance contains generated file URL and without comment.
     */
     AudioSample newASInstance(String audioFileName){
        audioFileName = normalizeFileName(audioFileName);
        AudioSample a = new AudioSample(Utils.stripExtension(audioFileName));
        try {
            a.setUrl(getMediaWikiToURL(audioFileName));
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return a;
    }
    //========================================================================
    /**
     * Returns languages which is being searched for IPA and audio samples.
     */
    public static Language[] getSearchLanguages(){
        return new Language [] {
                new Language("English", "English", "en", Language.RATING_BEST),
                new Language("German", "Deutsch", "de", Language.RATING_GOOD),
                new Language("French", "Français", "fr", Language.RATING_BEST),
                new Language("Spanish", "Español", "es", Language.RATING_GOOD),
        };
    }
    //========================================================================
    /**
     * Downloads and extracts the raw wikitext of a Wiktionary article using the "parse" API.
     *
     * @param word The word to fetch.
     * @return Wikitext content of the article, or empty string if not found or error occurs.
     */
    protected String loadWordArticle(String word) {
        try {
            String url = getWikiTextUrl(word);
            String json = Utils.getUrlString(url);
            JSONObject root = new JSONObject(json);
            return root
                    .getJSONObject("parse")
                    .getJSONObject("wikitext")
                    .getString("*");
        } catch (IOException | JSONException e) {
            //Log.e(Wiktionary.class.getSimpleName(), "Error loading article for word: " + word, e);
            return "";
        }
    }
}
