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

package com.googlecode.instinct.integrate.junit;

import java.lang.reflect.Method;
import com.googlecode.instinct.core.annotate.Specification;
import com.googlecode.instinct.core.naming.SpecificationNamingConvention;
import com.googlecode.instinct.internal.aggregate.locate.MarkedMethodLocator;
import com.googlecode.instinct.internal.aggregate.locate.MarkedMethodLocatorImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import junit.framework.TestResult;
import junit.framework.TestSuite;

@Suggest("Try and just use the interface Test rather than concrete extension.")
public final class BehaviourContextTestSuite extends TestSuite {
    private final MarkedMethodLocator methodLocator = new MarkedMethodLocatorImpl();
    private final Class<?> behaviourContextClass;
    private Method[] specificationMethods;

    public <T> BehaviourContextTestSuite(final Class<T> behaviourContextClass) {
        super(behaviourContextClass == null ? "" : behaviourContextClass.getSimpleName());
        checkNotNull(behaviourContextClass);
        this.behaviourContextClass = behaviourContextClass;
        specificationMethods = methodLocator.locateAll(behaviourContextClass, Specification.class, new SpecificationNamingConvention());
        setName("Foo");
        runIt();
    }

    @Override
    public int countTestCases() {
        return specificationMethods.length;
    }

    private void runIt() {
        setName("Something");
    }

    @Suggest("Do we need to do this in the constructor?")
    @Override
    public void run(final TestResult result) {
        checkNotNull(result);
        System.out.println("Running context class = " + behaviourContextClass);
        for (final Method specificationMethod : specificationMethods) {
            final SpecificationTestCase testCase = new SpecificationTestCase(behaviourContextClass, specificationMethod);
            System.out.println("testCase = " + testCase);
            runTest(testCase, result);
        }
    }

    @Override
    public String getName() {
        return behaviourContextClass.getName();
    }

    @Override
    public String toString() {
        return behaviourContextClass.getName();
    }
}
