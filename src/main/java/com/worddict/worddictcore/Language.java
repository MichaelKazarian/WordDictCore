package com.worddict.worddictcore;

import java.io.Serializable;
import java.util.Locale;

/**
 * Describes language. It can contain next properties:
 * <ul><li>Language - English variant of language. It's obligatory.</li>
 * <li>Language (local)</li>
 * <li>Language code according to ISO 639-1. It's obligatory.</li>
 * <li>Language rating based on available content.</li>
 * </ul>
 * Created by kazarian on 08.09.16.
 */
public class Language implements Serializable {
    public final static int RATING_NONE         = -1;
    public final static int RATING_BEST         =  5;
    public final static int RATING_GOOD         =  4;
    public final static int RATING_SATISFACTORY =  3;
    public final static int RATING_POOR         =  2;
    /**
     * Realisation for non supported language.
     * It's ISO 639-1 is "??";
     * Language name is "Undefined language".
     */
    public static final Language UNDEFINED_LANGUAGE =
        new Language("Undefined language", "Undefined language", "--", RATING_POOR);
    private final static Language [] mAllLanguages = {
            new Language("English", "English", "en", RATING_BEST),
            new Language("Malagasy", "Malagasy", "mg", RATING_BEST),
            new Language("French", "Français", "fr", RATING_BEST),
            new Language("Serbo-Croatian", "Srpskohrvatski / Српскохрватски", "sh", RATING_GOOD),
            new Language("Spanish", "Español", "es", RATING_GOOD),
            new Language("Chinese", "中文", "zh", RATING_GOOD),
            new Language("Russian", "Русский", "ru", RATING_GOOD),
            new Language("Lithuanian", "Lietuvių", "lt", RATING_GOOD),
            new Language("German", "Deutsch", "de", RATING_GOOD),
            new Language("Dutch", "Nederlands", "nl", RATING_GOOD),
            new Language("Swedish", "Svenska", "sv", RATING_GOOD),
            new Language("Polish", "Polski", "pl", RATING_GOOD),
            new Language("Kurdish", "Kurdî / كوردی", "ku", RATING_GOOD),
            new Language("Greek", "Ελληνικά", "el", RATING_GOOD),
            new Language("Italian", "Italiano", "it", RATING_GOOD),
            new Language("Tamil", "தமிழ்", "ta", RATING_GOOD),
            new Language("Turkish", "Türkçe", "tr", RATING_GOOD),
            new Language("Hungarian", "Magyar", "hu", RATING_GOOD),
            new Language("Finnish", "Suomi", "fi", RATING_GOOD),
            new Language("Korean", "한국어", "ko", RATING_GOOD),
            new Language("Ido", "Ido", "io", RATING_GOOD),
            new Language("Kannada", "ಕನ್ನಡ", "kn", RATING_GOOD),
            new Language("Catalan", "Català", "ca", RATING_GOOD),
            new Language("Vietnamese", "Tiếng Việt", "vi", RATING_GOOD),
            new Language("Portuguese", "Português", "pt", RATING_GOOD),
            new Language("Cherokee", "ᏣᎳᎩ", "chr", RATING_GOOD),
            new Language("Serbian", "Српски / Srpski", "sr", RATING_GOOD),
            new Language("Hindi", "हिन्दी", "hi", RATING_GOOD),
            new Language("Armenian", "Հայերեն", "hy", RATING_GOOD),
            new Language("Japanese", "日本語", "ja", RATING_GOOD),
            new Language("Romanian", "Română", "ro", RATING_GOOD),
            new Language("Norwegian (Bokmål)", "Norsk (Bokmål)", "no", RATING_GOOD),
            new Language("Thai", "ไทย", "th", RATING_GOOD),
            new Language("Malayalam", "മലയാളം", "ml", RATING_GOOD),
            new Language("Indonesian", "Bahasa Indonesia", "id", RATING_GOOD),
            new Language("Estonian", "Eesti", "et", RATING_GOOD),
            new Language("Uzbek", "O‘zbek", "uz", RATING_GOOD),
            new Language("Limburgish", "Limburgs", "li", RATING_GOOD),
            new Language("Burmese", "မြန်မာဘာသာ", "my", RATING_GOOD),
            new Language("Oriya", "ଓଡ଼ିଆ", "or", RATING_GOOD),
            new Language("Telugu", "తెలుగు", "te", RATING_GOOD),
            new Language("Persian", "فارسی", "fa", RATING_SATISFACTORY),
            new Language("Czech", "Čeština", "cs", RATING_SATISFACTORY),
            new Language("Arabic", "العربية", "ar", RATING_SATISFACTORY),
            new Language("Javanese", "Basa Jawa", "jv", RATING_SATISFACTORY),
            new Language("Esperanto", "Esperanto", "eo", RATING_SATISFACTORY),
            new Language("Basque", "Euskara", "eu", RATING_SATISFACTORY),
            new Language("Galician", "Galego", "gl", RATING_SATISFACTORY),
            new Language("Azerbaijani", "Azərbaycanca", "az", RATING_SATISFACTORY),
            new Language("Lao", "ລາວ", "lo", RATING_SATISFACTORY),
            new Language("Danish", "Dansk", "da", RATING_SATISFACTORY),
            new Language("Breton", "Brezhoneg", "br", RATING_SATISFACTORY),
            new Language("Ukrainian", "Українська", "uk", RATING_SATISFACTORY),
            new Language("Croatian", "Hrvatski", "hr", RATING_SATISFACTORY),
            new Language("Fijian", "Na Vosa Vakaviti", "fj", RATING_SATISFACTORY),
            new Language("Occitan", "Occitan", "oc", RATING_SATISFACTORY),
            new Language("Bulgarian", "Български", "bg", RATING_SATISFACTORY),
            new Language("Pashto", "پښتو", "ps", RATING_SATISFACTORY),
            new Language("Simple English", "Simple English", "simple", RATING_SATISFACTORY),
            new Language("Welsh", "Cymraeg", "cy", RATING_SATISFACTORY),
            new Language("Volapük", "Volapük", "vo", RATING_SATISFACTORY),
            new Language("Icelandic", "Íslenska", "is", RATING_SATISFACTORY),
            new Language("Min Nan", "Bân-lâm-gú", "zh-min-nan", RATING_SATISFACTORY),
            new Language("Walloon", "Walon", "wa", RATING_SATISFACTORY),
            new Language("Sicilian", "Sicilianu", "scn", RATING_SATISFACTORY),
            new Language("Tajik", "Тоҷикӣ", "tg", RATING_SATISFACTORY),
            new Language("Asturian", "Asturianu", "ast", RATING_SATISFACTORY),
            new Language("Slovak", "Slovenčina", "sk", RATING_SATISFACTORY),
            new Language("Hebrew", "עברית", "he", RATING_SATISFACTORY),
            new Language("Afrikaans", "Afrikaans", "af", RATING_SATISFACTORY),
            new Language("Latin", "Latina", "la", RATING_SATISFACTORY),
            new Language("Tagalog", "Tagalog", "tl", RATING_SATISFACTORY),
            new Language("Swahili", "Kiswahili", "sw", RATING_SATISFACTORY),
            new Language("West Frisian", "Frysk", "fy", RATING_SATISFACTORY),
            new Language("Norwegian (Nynorsk)", "Nynorsk", "nn", RATING_SATISFACTORY),
            new Language("Kirghiz", "Kırgızca", "ky", RATING_SATISFACTORY),
            new Language("Corsican", "Corsu", "co", RATING_POOR),
            new Language("Western Panjabi", "شاہ مکھی پنجابی (Shāhmukhī Pañj&#25", "pnb", RATING_POOR),
            new Language("Latvian", "Latviešu", "lv", RATING_POOR),
            new Language("Mongolian", "Монгол", "mn", RATING_POOR),
            new Language("Georgian", "ქართული", "ka", RATING_POOR),
            new Language("Slovenian", "Slovenščina", "sl", RATING_POOR),
            new Language("Albanian", "Shqip", "sq", RATING_POOR),
            new Language("Low Saxon", "Plattdüütsch", "nds", RATING_POOR),
            new Language("Nahuatl", "Nahuatl", "nah", RATING_POOR),
            new Language("Luxembourgish", "Lëtzebuergesch", "lb", RATING_POOR),
            new Language("Bosnian", "Bosanski", "bs", RATING_POOR),
            new Language("Kazakh", "қазақша", "kk", RATING_POOR),
            new Language("Turkmen", "تركمن / Туркмен", "tk", RATING_POOR),
            new Language("Cambodian", "ភាសាខ្មែរ", "km", RATING_POOR),
            new Language("Samoan", "Gagana Samoa", "sm", RATING_POOR),
            new Language("Sanskrit", "संस्कृतम्", "sa", RATING_POOR),
            new Language("Macedonian", "Македонски", "mk", RATING_POOR),
            new Language("Upper Sorbian", "Obersorbisch", "hsb", RATING_POOR),
            new Language("Bengali", "বাংলা", "bn", RATING_POOR),
            new Language("Belarusian", "Беларуская", "be", RATING_POOR),
            new Language("Malay", "Bahasa Melayu", "ms", RATING_POOR),
            new Language("Irish", "Gaeilge", "ga", RATING_POOR),
            new Language("Urdu", "اردو", "ur", RATING_POOR),
            new Language("Aragonese", "Aragonés", "an", RATING_POOR),
            new Language("Wolof", "Wollof", "wo", RATING_POOR),
            new Language("Anglo-Saxon", "Englisc", "ang", RATING_POOR),
            new Language("Venetian", "Vèneto", "vec", RATING_POOR),
            new Language("Panjabi", "ਪੰਜਾਬੀ", "pa", RATING_POOR),
            new Language("Tatar", "Tatarça / Татарча", "tt", RATING_POOR),
            new Language("Guarani", "Avañe'ẽ", "gn", RATING_POOR),
            new Language("Sindhi", "سنڌي، سندھی ، सिन्ध", "sd", RATING_POOR),
            new Language("Marathi", "मराठी", "mr", RATING_POOR),
            new Language("Somali", "Soomaaliga", "so", RATING_POOR),
            new Language("Kashubian", "Kaszëbsczi", "csb", RATING_POOR),
            new Language("Uyghur", "ئۇيغۇر تىلى", "ug", RATING_POOR),
            new Language("Scottish Gaelic", "Gàidhlig", "gd", RATING_POOR),
            new Language("Southern Sotho", "seSotho", "st", RATING_POOR),
            new Language("Maltese", "bil-Malti", "mt", RATING_POOR),
            new Language("Aromanian", "Armãneashce", "roa-rup", RATING_POOR),
            new Language("Sinhalese", "සිංහල", "si", RATING_POOR),
            new Language("Interlingua", "Interlingua", "ia", RATING_POOR),
            new Language("Interlingue", "Interlingue", "ie", RATING_POOR),
            new Language("Aymara", "Aymar", "ay", RATING_NONE)
    };

    private String mLanguage, mLocalLanguage, mLanguageCode;
    private int mRating;
    private Locale mLocale;

    /**
     * @param language English variant of language.
     * @param localLanguage Language (local)
     * @param languageCode language code according to ISO 639-1.
     * @param rating rating based on available content. -1 if nothing rating.
     */
    public Language(String language, String localLanguage, String languageCode, int rating) {
        this(language, localLanguage, languageCode, rating, null);
        mLocale = new Locale(mLanguageCode);
        if (mLocale.getDisplayLanguage().isEmpty()) mLocale = null;
    }

    /**
     * @param language English variant of language.
     * @param localLanguage Language (local)
     * @param languageCode language code according to ISO 639-1.
     * @param rating rating based on available content. -1 if nothing rating.
     * @param locale Language locale
     */
    public Language(String language, String localLanguage,
                    String languageCode, int rating, Locale locale){
        this.mLanguage = language;
        this.mLocalLanguage = localLanguage;
        this.mLanguageCode = languageCode;
        this.mRating = rating;
        this.mLocale = locale;
    }

    /**
     * Returns supported languages. First most popular, next sort by alphabet.
     * @return supported languages.
     */
    public static Language [] getAllLanguages(){
        return mAllLanguages;
    }
    
    /**
     * Case non sensitive check support language by ISO code (639-1).
     *
     * @param isoCode language in ISO 639-1. 
     * @return true if supported; false otherwise.
     */
    public static boolean isISOCodeSupported(String isoCode){
        String lc = isoCode.toLowerCase().trim();
        for (Language l: mAllLanguages) {
            if (l.mLanguageCode.equals(lc)) return true;
        }
        return false;
    }
    /**
     * Returns language from supported languages by ISO 639-1.
     *
     * @param isoCode language in ISO 639-1.
     * @return <code>Language</code> instance if isoCode presents in  supported
     * languages. UNDEFINED_LANGUAGE otherwise.
     */
    public static Language getLanguageByCode(String isoCode){
        Language result = UNDEFINED_LANGUAGE;
        if (! isISOCodeSupported(isoCode)) return result;
        for (Language l : getAllLanguages())
            if (l.getLanguageCode().equalsIgnoreCase(isoCode))
                return l;
        return result;
    }
    /**
     * Returns English variant of language.
     * @return English variant of language.
     */
    public String getLanguage() {
        return mLanguage;
    }

    /**
     * Returns Language (local).
     * @return Language (local)
     */
    public String getLocalLanguage() {
        return mLocalLanguage;
    }

    /**
     * Returns language code according to ISO 639-1.
     * @return language code according to ISO 639-1.
     */
    public String getLanguageCode() {
        return mLanguageCode;
    }

    /**
     * Returns rating based on available content. -1 if nothing rating.
     * @return rating based on available content. -1 if nothing rating.
     */
    public int getRating() {
        return mRating;
    }
    /**
     * {@inheritDoc}
     */
    public String toString() {
        final int sbSize = 1000;
        final String variableSeparator = "  ";
        final StringBuffer sb = new StringBuffer(sbSize);
        sb.append(variableSeparator);
        sb.append("mLanguage=").append(mLanguage);
        sb.append(variableSeparator);
        sb.append("mLocalLanguage=").append(mLocalLanguage);
        sb.append(variableSeparator);
        sb.append("mLanguageCode=").append(mLanguageCode);
        sb.append(variableSeparator);
        sb.append("mRating=").append(mRating);

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Language language = (Language) o;

        if (getRating() != language.getRating()) return false;
        if (!getLanguage().equals(language.getLanguage())) return false;
        if (!getLocalLanguage().equals(language.getLocalLanguage())) return false;
        return getLanguageCode().equals(language.getLanguageCode());
    }

    @Override
    public int hashCode() {
        int result = getLanguage().hashCode();
        result = 31 * result + getLocalLanguage().hashCode();
        result = 31 * result + getLanguageCode().hashCode();
        result = 31 * result + getRating();
        return result;
    }

    /**
     * Return language locale.
     * @return {@link Locale} instance if presence; null otherwise.
     */
    public Locale getLocale(){
        return mLocale;
    }
}
