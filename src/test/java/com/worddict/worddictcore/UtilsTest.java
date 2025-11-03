package com.worddict.worddictcore;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.IOException;
import java.util.Locale;

public class UtilsTest {

    @Test
    public void testGetUrlString_wiktionary() throws IOException {
        // Стабільна сторінка, яка завжди існує
        String url = "https://en.wiktionary.org/wiki/test";
        String result = Utils.getUrlString(url);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue("Expected Wiktionary HTML content",
                result.contains("<title>test - Wiktionary"));
    }

    @Test(expected = IOException.class)
    public void testGetUrlString_invalidUrl() throws IOException {
        Utils.getUrlString("invalid_url");
    }

    @Test(expected = IOException.class)
    public void testGetUrlString_nonexistentPage() throws IOException {
        Utils.getUrlString("https://en.wiktionary.org/wiki/this_page_should_not_exist_123456789");
    }

    @Test
    public void testStripExtension() {
        assertEquals("file", Utils.stripExtension("file.txt"));
        assertEquals("archive.tar", Utils.stripExtension("archive.tar.gz"));
        assertEquals("filename", Utils.stripExtension("filename"));
        assertEquals("document", Utils.stripExtension("document."));
        assertEquals("", Utils.stripExtension(""));
        assertEquals("", Utils.stripExtension("."));
        assertEquals("", Utils.stripExtension(".gitignore"));
        assertNull(Utils.stripExtension(null));
    }

    @Test
    public void md5() {
        assertEquals("9c454114a72cdc783230c8c93dc4b48e", Utils.md5("En-us-test.ogg"));
        assertEquals("ae1a26d34d6a674d4400c8a1e6fe73f8", Utils.md5("Spelterini_Blüemlisalp.jpg"));
        assertEquals("d5aa74c9fb8bac061ac08bf4149aaa81", Utils.md5("En-uk-a_test.ogg"));
        assertEquals("d41d8cd98f00b204e9800998ecf8427e", Utils.md5(""));
        assertNull(Utils.md5(null));
    }

    @Test
    public void testToTitle() {
        assertNull(Utils.toTitle(null));
        assertEquals("", Utils.toTitle(""));

        // elementary cases
        assertEquals("Hello", Utils.toTitle("hello"));
        assertEquals("Hello", Utils.toTitle("Hello"));
        assertEquals("HELLO", Utils.toTitle("HELLO"));
        assertEquals("Hello world", Utils.toTitle("hello world"));
        assertEquals("A", Utils.toTitle("a"));
        // symbols / digits at start
        assertEquals("1abc", Utils.toTitle("1abc"));
        assertEquals("_test", Utils.toTitle("_test"));
        // turkish locale
        assertEquals("İstanbul", Utils.toTitle("istanbul", new Locale("tr")));
        // default locale — ROOT
        assertEquals("Istanbul", Utils.toTitle("istanbul", Locale.ROOT));
    }
}