package com.worddict.wiktionarybot;

import com.worddict.worddictcore.AudioSample;
import com.worddict.worddictcore.Pronounce;
import com.worddict.worddictcore.Utils;

import java.util.Arrays;
import java.util.LinkedHashSet;

public class WiktionaryEnglish extends Wiktionary {
    private final static String ABR = "en";
    private WiktionaryEnglish(String abr){
        super(abr);
    }

    public static Wiktionary newInstance() {
        return new WiktionaryEnglish(ABR);
    }
    //========================================================================
    @Override
    public Pronounce.TextPronounce[] getIPA() {
        LinkedHashSet<Pronounce.TextPronounce> hs = new LinkedHashSet<>();
        String [] ipaStr, ipaComments;
        for (String wikiText: mCachedPages.values()){
            ipaStr = Utils.searchAllRegexp(
                    "(?<=\\{\\{(?i)ipa(?-i)\\|en\\|\\/)(.*?)(?=\\/|\\})",
                    wikiText, 1);
            ipaComments = Utils.searchAllRegexp("n*\\s*\\{\\{a\\|(.*?)\\}\\}", wikiText, 1);
            Pronounce.TextPronounce [] ipa = new Pronounce.TextPronounce [ipaStr.length];
            for (int i=0; i<ipa.length; i++) ipa[i] = new Pronounce.TextPronounce(ipaStr[i]);
            if (ipa.length == ipaComments.length)
                for (int i=0; i<ipa.length; i++)
                    ipa[i].setMemo(ipaComments[i]);
            hs.addAll(Arrays.asList(ipa));
        }
        return hs.toArray(new Pronounce.TextPronounce[0]);
    }
    //========================================================================
    @Override
    public AudioSample[] getAudioSamples() {
        LinkedHashSet<AudioSample> hs = new LinkedHashSet<>();
        for (String wikiText: mCachedPages.values()) {
            String [] wikiAudio = Utils.searchAllRegexp("(?<=\\{\\{(?i)audio\\|en\\|)(.*?)(?=\\})", wikiText, 0);
            if (wikiAudio.length == 0) continue;
            //Parse string similar to en-us-test.ogg|Audio (US)
            for (String audioStr: wikiAudio){
                String [] audioObjects = audioStr.split("\\|");
                String audioFileName = audioObjects[0];
                if (audioFileName.isEmpty()) continue;
                AudioSample a = newASInstance(audioFileName);
                String audioComment = audioObjects.length == 2? audioObjects[1]: "";
                a.setComment(audioComment);
                hs.add(a);
            }
        }
        AudioSample [] resArr = new AudioSample[hs.size()];
        int i = 0;
        for (AudioSample e: hs){
            resArr[i] = e;
            i++;
        }
        return resArr;
    }
    //========================================================================
    @Override
    public String getLanguageSectionRegexp() {
        return "(?si)\\s?==english.+?(?=\\s==[^=]|\\Z)";
    }
}
