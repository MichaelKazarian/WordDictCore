package com.worddict.worddictcore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    /**
     * Fetches the contents of a URL and returns it as a UTF-8 string.
     *
     * @param urlSpec the URL to fetch (must not be null or empty)
     * @return the response body as a UTF-8 string
     * @throws IOException if the URL is invalid or an I/O error occurs
     */
    public static String getUrlString(String urlSpec) throws IOException {
        URL url;
        try {
            url = new URL(urlSpec);
        } catch (MalformedURLException e) {
            throw new IOException("Invalid URL format: " + urlSpec, e);
        }

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            connection.setRequestProperty(
                    "User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:131.0) Gecko/20100101 Firefox/131.0");
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("HTTP error: " + responseCode);
            }

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
                return sb.toString();
            }
        } finally {
            connection.disconnect();
        }
    }

    /**
     * Returns first match of regexp in text.
     * @param regexp regular exception
     * @param text
     * @return first match in regular expression if exists; empty string otherwise.
     */
    public static String searchRegexp(String regexp, String text) {
        String [] res = searchAllRegexp(regexp, text, 0);
        return res.length>0 ? res[0] : "";
    }
    /**
     * Returns specific group from all regexp results.
     * @param regexp regular expression
     * @param text text for processing
     * @param group group number
     * @return returns the text that matched a given group of the regular expression. See more in
     * Matcher.group(int) documentation.
     */
    public static String [] searchAllRegexp(String regexp, String text, int group){
        Matcher m = Pattern.compile(regexp).matcher(text);
        ArrayList<String> r = new ArrayList<>();
        while (m.find())
            r.add(m.group(group));
        return r.toArray(new String [] {} );
    }

    /**
     * Removes the last extension from the given file name.
     *
     * @param fileName the file name, may be {@code null}
     * @return the file name without its last extension, or {@code null} if {@code fileName} is {@code null}
     */
    public static String stripExtension (String fileName) {
        if (fileName == null) return null;
        int pos = fileName.lastIndexOf(".");
        if (pos == -1) return fileName;
        return fileName.substring(0, pos);
    }

    /**
     * Generates an MD5 hash for the given string.
     *
     * @param str the input string, may be {@code null}
     * @return the MD5 hash in lowercase hexadecimal, or {@code null} if {@code str} is {@code null}
     */
    public static String md5(String str) {
        if (str == null) return null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(str.getBytes(StandardCharsets.UTF_8));
            StringBuilder result = new StringBuilder(digest.length * 2);
            for (byte b : digest) {
                result.append(String.format("%02x", b));
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Converts a string to a new string with the first character capitalized, using
     * {@link Locale#ROOT} for locale-independent capitalization.
     *
     * If the input string is {@code null} or empty, it is returned unchanged.
     *
     * @param s the input string to be converted.
     * @return a new string with the first character capitalized, or the original
     * string if it is {@code null} or empty.
     * @see #toTitle(String, Locale)
     */
    public static String toTitle(String s) {
        return toTitle(s, Locale.ROOT);
    }

    /**
     * Converts a string to a new string with the first character capitalized,
     * using the specified {@code locale} for character case mapping.
     *
     * If the input string is {@code null} or empty, it is returned unchanged.
     * The rest of the string remains unchanged.
     *
     * @param s the input string to be converted.
     * @param locale the locale to use for the capitalization.
     * @return a new string with the first character capitalized, or the original
     * string if it is {@code null} or empty.
     */
    public static String toTitle(String s, Locale locale) {
        if (s == null || s.isEmpty()) return s;
        String s1 = s.substring(0, 1).toUpperCase(locale);
        String sTitle = s1 + s.substring(1);
        return sTitle;
    }
}
