package com.googlecode.instinct.integrate.junit;

import junit.framework.Test;

public interface JUnitTestSuiteBuilder {
    Test buildSuite(String suiteName);
}