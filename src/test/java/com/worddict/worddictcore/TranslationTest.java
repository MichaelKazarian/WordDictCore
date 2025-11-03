package com.worddict.worddictcore;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TranslationTest {
    private Translation mTranslation;

    @BeforeClass
    public static void setup() {

    }

    @Before
    public void setUp() {
        mTranslation = new Translation("Translation");
    }

    @Test
    public void testTranslation() {
        assertEquals("Translation", mTranslation.getTranslation());
        mTranslation.setTranslation("New translation");
        assertEquals("New translation", mTranslation.getTranslation());
    }

    @Test
    public void testSamples() {
        mTranslation.addSample("Sample 1");
        int newSize = mTranslation.addSample("Sample 2");
        assertEquals(2, newSize);

        boolean rs;
        rs = mTranslation.setSample(0, "Sample 0");
        assertEquals("Sample 0", mTranslation.getSamples().get(0));
        assertTrue(rs);

        mTranslation.setSample(1, "Sample 1");
        assertEquals("Sample 1", mTranslation.getSamples().get(1));

        rs = mTranslation.removeSample(0);
        assertEquals(1, mTranslation.getSamples().size());
        assertTrue(rs);

        rs = mTranslation.removeSample(10); //wrong index
        assertEquals(1, mTranslation.getSamples().size());
        assertFalse(rs);

        rs = mTranslation.removeSample("Sample 3"); //Sample3 is absent
        assertEquals(1, mTranslation.getSamples().size());
        assertFalse(rs);

        rs = mTranslation.removeSample("Sample 1");
        assertEquals(0, mTranslation.getSamples().size());
        assertTrue(rs);

        ArrayList<String> samples2 = new ArrayList<>();
        samples2.add("Sample 0"); samples2.add("Sample 1");
        mTranslation.setSamples(samples2);
        assertEquals(2, mTranslation.getSamples().size());
    }

    @Test
    public void testToString() {
        assertEquals("Translation {translation='Translation', "+
                        "samples=SamplesList[], note=''}",
                mTranslation.toString());

        mTranslation.addSample("Sample 1"); mTranslation.addSample("Sample 2");
        mTranslation.setNote("Some note");
        assertEquals("Translation {translation='Translation', "+
                "samples=SamplesList[Sample 1, Sample 2], note='Some note'}",
                mTranslation.toString());
    }

    @Test
    public void testEquals() {
        assertNotEquals(mTranslation, null);
        mTranslation.addSample("Sample 1"); mTranslation.addSample("Sample 2");
        mTranslation.setNote("Some note");
        Translation other = new Translation("Translation");
        assertNotEquals(mTranslation, other);
        other.addSample("Sample 1"); other.addSample("Sample 2");
        other.setNote("Some note");
        assertEquals(mTranslation, other);
        other.setNote("Another note");
        assertNotEquals(mTranslation, other);
    }

    @Test
    public void testHashCode() {
        mTranslation.addSample("Sample 1"); mTranslation.addSample("Sample 2");
        mTranslation.setNote("Some note");
        assertEquals(-513051923, mTranslation.hashCode());
        mTranslation.setNote("Another note");
        assertNotEquals(-513051923, mTranslation.hashCode());
    }
}