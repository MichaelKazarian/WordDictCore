package com.worddict.worddictcore;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SamplesListTest {
    private SamplesList mSamplesSet;
    @Before
    public void setUp() {
        mSamplesSet = new SamplesList();
    }


    @Test
    public void samplesOperationsTests() {
        assertEquals(0, mSamplesSet.add("Sample 0"));
        assertEquals(-1, mSamplesSet.add(null));
        assertEquals(-1, mSamplesSet.add(""));

        assertFalse(mSamplesSet.remove(null));
        assertEquals("Sample 0", mSamplesSet.get(0));
        assertTrue(mSamplesSet.remove(0));
        assertEquals(0, mSamplesSet.size());
        assertNull("", mSamplesSet.get(0));

        assertFalse(mSamplesSet.remove(10));
        assertFalse(mSamplesSet.remove(-10));

        assertEquals(0, mSamplesSet.add("Sample 0"));
        assertEquals(1, mSamplesSet.add("Sample 1"));
        assertEquals(2, mSamplesSet.size());

        assertTrue(mSamplesSet.set(1, "Changed"));
        assertFalse(mSamplesSet.set(2, "Changed"));
        assertFalse(mSamplesSet.set(-1, "Changed"));

        assertFalse(mSamplesSet.set(1, ""));
        assertEquals(1, mSamplesSet.size());
        assertFalse(mSamplesSet.set(0, null));
        assertEquals(0, mSamplesSet.size());
    }

    @Test
    public void samplesSetToString(){
        assertEquals("SamplesList[]", mSamplesSet.toString());

        mSamplesSet.add("Sample 0");
        mSamplesSet.add("Sample 1");
        assertEquals("SamplesList[Sample 0, Sample 1]",
                mSamplesSet.toString());
    }

    @Test
    public void equalsTest(){
        mSamplesSet.add("Sample 0");
        mSamplesSet.add("Sample 1");

        SamplesList other = new SamplesList();
        other.add("Sample 0");
        other.add("Sample 1");
        assertEquals(mSamplesSet, other);

        assertEquals(862154498L, mSamplesSet.hashCode());
        assertEquals(862154498L, other.hashCode());

        other.remove(0);
        assertNotEquals(mSamplesSet, other);
    }

    @Test
    public void toJsonArrayTest() {
        assertEquals("[]", mSamplesSet.toJsonArray().toString());

        mSamplesSet.add("Sample 0");
        mSamplesSet.add("Sample 1");
        assertEquals("[\"Sample 0\",\"Sample 1\"]",
                mSamplesSet.toJsonArray().toString());
    }

    @Test
    public void isEmpty() {
        assertTrue(mSamplesSet.isEmpty());

        mSamplesSet.add("Test");
        assertFalse(mSamplesSet.isEmpty());
    }
}