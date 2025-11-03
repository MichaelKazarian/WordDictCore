package com.worddict.wiktionarybot;

import com.worddict.worddictcore.AudioSample;
import com.worddict.worddictcore.Pronounce;
import com.worddict.worddictcore.Utils;

import java.util.LinkedHashSet;

public class WiktionaryDeutsch extends Wiktionary {
    private final static String ABR = "de";
    //========================================================================
    private WiktionaryDeutsch(String abr){
        super(abr);
    }
    //========================================================================
    public static Wiktionary newInstance() {
        return new WiktionaryDeutsch(ABR);
    }
    //========================================================================
    @Override
    public Pronounce.TextPronounce[] getIPA() {
        LinkedHashSet<Pronounce.TextPronounce> hs = new LinkedHashSet<>();
        String ipaS;
        for (String wikiText: mCachedPages.values()){
            ipaS = Utils.searchRegexp(
                    "(?<=\\{\\{(?i)Lautschrift(?-i)\\|).*?(?=\\}\\})",
                    wikiText);
            if (ipaS.isEmpty()) continue;
            hs.add(new Pronounce.TextPronounce(ipaS));
        }
        return hs.toArray(new Pronounce.TextPronounce[0]);
    }
    //========================================================================
    @Override
    public AudioSample[] getAudioSamples() {
        LinkedHashSet<AudioSample> hs = new LinkedHashSet<>();
        for (String wikiText: mCachedPages.values()) {
            String [] wikiAudio = Utils.searchAllRegexp(
                    "(?<=\\{Audio\\|).*?(?=[\\||\\}])",
                    wikiText, 0);
            for (String audioFileName: wikiAudio)
                hs.add(newASInstance(audioFileName));
        }
        return hs.toArray(new AudioSample[0]);
    }
    //========================================================================
    /**
     * Language dependent compare word. In German Test and test can be
     * different words. So we use different compare strategies.
     * @param master string to compare
     * @param another the string to compare this instance with.
     * @return true if master is equal to another; false otherwise.
     * Sample:
     * languageEquals(Test, test)->false but languageEquals(test, Test)->true
     * languageEquals(test, another) -> false
     * languageEquals(Test, another) -> false
     */
    @Override
    protected boolean languageEquals(String master, String another){
        return !master.isEmpty() && master.equals(Utils.toTitle(master)) ?
                master.equals(another): master.equalsIgnoreCase(another);
    }
    //========================================================================
    @Override
    public String getLanguageSectionRegexp() {
        return "(?si)\\s?==.*\\(\\{\\{Sprache\\|Deutsch\\}\\}\\) ==.+?(?=\\s==[^=]|\\Z)";
    }
}
