package com.googlecode.instinct.core;

import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ExceptionChecker.checkException;

public final class TestDoubleConfigurationExceptionAtomicTest extends InstinctTestCase {
    public void testProperties() {
        checkException(TestDoubleConfigurationException.class);
    }
}
