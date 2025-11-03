package com.worddict.wiktionarybot;

import com.worddict.worddictcore.AudioSample;
import com.worddict.worddictcore.Pronounce;
import com.worddict.worddictcore.Utils;

import java.util.LinkedHashSet;

public class WiktionaryFrench extends Wiktionary {
    private static final String ABR = "fr";

    private WiktionaryFrench(String abbreviation) {
        super(abbreviation);
    }
    //========================================================================
    public static Wiktionary newInstance() {
        return new WiktionaryFrench(ABR);
    }
    //========================================================================
    @Override
    public Pronounce.TextPronounce[] getIPA() {
        LinkedHashSet<Pronounce.TextPronounce> hs = new LinkedHashSet<>();
        String [] ipaS;
        for (String wikiText: mCachedPages.values()){
            ipaS = Utils.searchAllRegexp(
                    "(?i)(?<=\\{phon\\||pron\\|).*?(?=[\\|\\}])",
                    wikiText, 0);
            if (ipaS.length==0) continue;
            for (String ipa: ipaS)
                if (!ipa.isEmpty()) hs.add(new Pronounce.TextPronounce(ipa));
        }
        return hs.toArray(new Pronounce.TextPronounce[0]);
    }
    //========================================================================
    @Override
    public AudioSample[] getAudioSamples() {
        LinkedHashSet<AudioSample> hs = new LinkedHashSet<>();
        String audioFileName, audioComment;
        for (String wikiText: mCachedPages.values()) {
            String [] audioSections = Utils.searchAllRegexp(
                    "(?s)\\{écouter\\|.+?(?=\\})",
                    wikiText, 0);
            //Parse string similar to
            // {écouter|France (Paris)|kɔ.mɑ̃|audio=Fr-comment.ogg|lang=fr
            // {écouter||kɔ.mɑ̃|lang=fr|audio=LL-Q150 (fra)-Guilhelma-comment.wav
            for (String audioStr: audioSections){
                audioFileName = Utils.searchRegexp(
                        "(?<=audio=).+?(?=\\}|\\||\\Z)", audioStr);

                if (audioFileName.isEmpty()) continue;
                AudioSample a = newASInstance(audioFileName);
                audioComment = Utils.searchRegexp(
                        "(?<=écouter\\|).*(?=\\|audio=)", audioStr);
                audioComment = audioComment.replaceAll("lang=fr|", "");
                audioComment = audioComment.replaceAll("\\|", " ");
                audioComment = audioComment.trim();
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
        return "(?si)\\s?== \\{\\{langue\\|fr\\}.*?(?=\\s== \\{\\{langue\\|[^f]|\\Z)";
    }
}
