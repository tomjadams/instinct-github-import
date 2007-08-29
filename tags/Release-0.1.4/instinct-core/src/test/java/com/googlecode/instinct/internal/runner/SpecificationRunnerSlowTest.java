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

import java.util.ArrayList;
import java.util.Collection;
import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.ContextClassImpl;
import com.googlecode.instinct.internal.core.LifecycleMethod;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.core.SpecificationMethodImpl;
import com.googlecode.instinct.test.InstinctTestCase;

@SuppressWarnings({"StringContatenationInLoop"})
public final class SpecificationRunnerSlowTest extends InstinctTestCase {
    private SpecificationRunner runner;

    @Override
    public void setUpSubject() {
        runner = new SpecificationRunnerImpl();
    }

    public void testRunWithSuccess() {
        checkContextsRunWithoutError(ContextContainerWithSetUpAndTearDown.class);
        checkContextsRunWithoutError(ContextWithSpecificationWithReturnType.class);
        checkContextsRunWithoutError(ContextWithBeforeSpecificationWithReturnType.class);
        checkContextsRunWithoutError(ContextWithAfterSpecificationWithReturnType.class);
    }

    public void testInvalidMethodsBarf() {
        checkInvalidMethodsBarf(ContextWithSpecificationMethodContainingParameter.class);
        checkInvalidMethodsBarf(ContextWithInvalidlyMarkedAfterSpecification2.class);
        checkInvalidMethodsBarf(ContextWithInvalidlyMarkedBeforeSpecification2.class);
    }

    private <T> void checkContextsRunWithoutError(final Class<T> contextClass) {
        final Collection<SpecificationMethod> specificationMethods = findSpecificationMethods(contextClass);
        for (final SpecificationMethod specificationMethod : specificationMethods) {
            runner.run(specificationMethod);
        }
    }

    private <T> void checkInvalidMethodsBarf(final Class<T> contextClass) {
        final Collection<SpecificationMethod> specificationMethods = findSpecificationMethods(contextClass);
        for (final SpecificationMethod specificationMethod : specificationMethods) {
            final SpecificationResult specificationResult = runner.run(specificationMethod);
            assertFalse("Spec " + specificationMethod.getName() + " should have failed", specificationResult.completedSuccessfully());
        }
    }

    private <T> Collection<SpecificationMethod> findSpecificationMethods(final Class<T> cls) {
        final Collection<SpecificationMethod> specs = new ArrayList<SpecificationMethod>();
        final ContextClass contextClass = new ContextClassImpl(cls);
        final Collection<LifecycleMethod> specificationMethods = contextClass.getSpecificationMethods();
        for (final LifecycleMethod specificationMethod : specificationMethods) {
            final SpecificationMethod spec = new SpecificationMethodImpl(specificationMethod, contextClass.getBeforeSpecificationMethods(),
                    contextClass.getAfterSpecificationMethods());
            specs.add(spec);
        }
        return specs;
    }
}