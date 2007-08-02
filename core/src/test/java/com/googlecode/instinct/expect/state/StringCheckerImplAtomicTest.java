package com.googlecode.instinct.expect.state;

import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.AssertThrowsChecker.assertThrows;
import static com.googlecode.instinct.test.checker.ModifierChecker.checkPublic;
import static com.googlecode.instinct.test.triangulate.Triangulation.getInstance;

public final class StringCheckerImplAtomicTest extends InstinctTestCase {
    private static final String SUBJECT_STRING = getInstance(String.class);
    private StringChecker checker;

    @Override
    public void setUpSubject() {
        checker = new StringCheckerImpl(SUBJECT_STRING);
    }

    public void testConformsToClassTraits() {
        checkPublic(StringCheckerImpl.class);
    }

    public void testShowsHumanReadableStringWhenEqualsIgnoringCaseIsPassedNull() {
        expectNullRejected("equalToIgnoringCase", new Runnable() {
            public void run() {
                checker.equalToIgnoringCase(null);
            }
        });
    }

    public void testShowsHumanReadableStringWhenEqualsIgnoringWhiteSpaceIsPassedNull() {
        expectNullRejected("equalToIgnoringWhiteSpace", new Runnable() {
            public void run() {
                checker.equalToIgnoringWhiteSpace(null);
            }
        });
    }

    public void testShowHumanReadableStringWhenNotEqualIgnoringCaseIsPassedNull() {
        expectNullRejected("notEqualToIgnoringCase", new Runnable() {
            public void run() {
                checker.notEqualToIgnoringCase(null);
            }
        });

    }

    /*
    public final void notEqualToIgnoringCase(final String string) {
    public final void notEqualToIgnoringWhiteSpace(final String string) {
    public final void notContainString(final String string) {
    public final void notEndingWith(final String string) {
    public final void startsWith(final String string) {
    public final void notStartingWith(final String string) {
     */

    public void testShowsHumanReadableStringWhenEndsWithIsPassedNull() {
        expectNullRejected("endsWith", new Runnable() {
            public void run() {
                checker.endsWith(null);
            }
        });
    }

    public void testShowsHumanReadableStringWhenContainsStringIsPassedNull() {
        expectNullRejected("containsString", new Runnable() {
            public void run() {
                checker.containsString(null);
            }
        });
    }

    private void expectNullRejected(final String methodName, final Runnable block) {
        assertThrows(IllegalArgumentException.class, "Cannot pass a null string into " + methodName, block);
    }
}
