/*
 * Copyright 2006-2007 Tom Adams
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.instinct.internal.runner;

import static com.googlecode.instinct.mock.Mocker.mock;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class SpecificationResultImplAtomicTest extends InstinctTestCase {
    public void testProperties() {
        checkClass(SpecificationResultImpl.class, SpecificationResult.class);
    }

    public void testBeanProperties() {
        checkBeanProperties("writeMeASpec", mock(SpecificationRunError.class), 1L);
        checkBeanProperties("theManJumpedOverTheMoon", mock(SpecificationRunError.class), 2L);
    }

    public void testCompletedSuccessfully() {
        checkCompletedSuccessfully(mock(SpecificationRunError.class), false);
        checkCompletedSuccessfully(new NoErrorSpecificationRunError(), true);
    }

    private void checkBeanProperties(final String specificationName, final SpecificationRunError runError, final long executionTime) {
        final SpecificationResult result = new SpecificationResultImpl(specificationName, runError, executionTime);
        assertEquals(specificationName, result.getSpecificationName());
        assertSame(runError, result.getError());
        assertEquals(executionTime, result.getExecutionTime());
    }

    private void checkCompletedSuccessfully(final SpecificationRunError runError, final boolean expectingSuccess) {
        final SpecificationResult result = new SpecificationResultImpl("name1", runError, 2L);
        assertEquals(expectingSuccess, result.completedSuccessfully());
    }
}
