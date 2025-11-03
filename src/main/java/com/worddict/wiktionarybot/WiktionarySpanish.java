package com.worddict.wiktionarybot;

import com.worddict.worddictcore.AudioSample;
import com.worddict.worddictcore.Pronounce;
import com.worddict.worddictcore.Translation;
import com.worddict.worddictcore.Utils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WiktionarySpanish extends Wiktionary {
    private static final String ABR = "es";
    //========================================================================
    private WiktionarySpanish(String abbreviation) {
        super(abbreviation);
    }
    //========================================================================
    public static Wiktionary newInstance() {
        return new WiktionarySpanish(ABR);
    }
    //========================================================================
    @Override
    public Pronounce.TextPronounce[] getIPA() {
//        LinkedHashSet<Pronounce.TextPronounce> hs = new LinkedHashSet<>();
//        String [] ipaS;
//        for (String wikiText: mCachedPages.values()){
//            ipaS = Tools.searchAllRegexp(
//                    "(?i)(?<=[\\|\\d]fone\\=).*?(?=\\||\\}|\\n)",
//                    wikiText, 0);
//            if (ipaS.length==0)
//                ipaS = Tools.searchAllRegexp(
//                        "(?i)(?<=\\{pron-graf\\|).*?(?=\\||\\}|\\n)",
//                        wikiText, 0);
//            if (ipaS.length==0) continue;
//            for (String ipa: ipaS)
//                if (!ipa.isEmpty()) hs.add(new Pronounce.TextPronounce(ipa));
//        }
//        return hs.toArray(new Pronounce.TextPronounce[0]);
        return new Pronounce.TextPronounce[0];
    }
    //========================================================================

    /**
     * Extracts all translations from cached Wiktionary pages for the specified languages.
     * <p>
     * The method looks for translation templates of the form
     * <code>{{t|<language code>|t1=...|t2=...|...}}</code> and extracts all {@code tN=} values
     * (e.g., {@code t1=木}, {@code t2=き}, {@code t10=дерево}, etc.) for the specified languages.
     * </p>
     *
     * @param langCodes the array containing the language codes
     * @return an array of {@link Translation} objects extracted from the cached wikitext
     */
    @Override
    public Translation[] getTranslation(String [] langCodes) {
        LinkedHashSet<Translation> hs = new LinkedHashSet<>();
        for (String wikiText: mCachedPages.values()){
            for (String lng: langCodes) {
                String [] trS = Utils.searchAllRegexp("\\{\\{t\\|"+
                        lng+"\\|.*?\\}\\}", wikiText, 0);
                for (String trN: trS){ // extract translations
                    String [] tr = Utils.searchAllRegexp(
                            "t\\d+=([^|}]+)", trN, 1);
                    for (String s: tr) hs.add(new Translation(s));
                }
            }
        }
        return hs.toArray(new Translation[]{});
    }
    //========================================================================
    /**
     * Extracts audio file names from wiki text similar to these examples:
     * {{pron-graf|1audio1=LL-Q1321 (spa)-Rodelar-cómo.wav|h1=como}}
     * {{pron-graf|1audio1=Es-buscar.ogg}}
     *
     * Supports keys with digits before and after 'audio', e.g. 1audio1=, 2audio2=, etc.
     * Extracts filenames ending with .wav, .ogg or .mp3.
     */
    @Override
    public AudioSample[] getAudioSamples() {
        LinkedHashSet<AudioSample> hs = new LinkedHashSet<>();
        for (String wikiText: mCachedPages.values()) {
            String[] wikiAudio = findAudioFilenames(wikiText);
            for (String audioFileName : wikiAudio) {
                hs.add(newASInstance(audioFileName));
            }
        }
        return hs.toArray(new AudioSample[0]);
    }

    /**
     * Finds all audio file names in the given wiki text.
     * Matches keys like 1audio1=, 2audio2= followed by a filename ending with
     * .wav, .ogg, or .mp3.
     *
     * @param wikiText wiki markup text to search in
     * @return array of audio filenames found
     */
    private String[] findAudioFilenames(String wikiText) {
        Pattern audioPattern = Pattern.compile("\\d+audio\\d+=([^|}]+\\.(?:wav|ogg|mp3))");
        Matcher matcher = audioPattern.matcher(wikiText);
        List<String> results = new ArrayList<>();
        while (matcher.find()) {
            results.add(matcher.group(1));
        }
        return results.toArray(new String[0]);
    }

    //========================================================================
    @Override
    public String getLanguageSectionRegexp() {
        return "(?s)\\s?== \\{\\{lengua\\|es\\}.*?(?=\\s== \\{\\{lengua\\||\\Z)";
    }
}
