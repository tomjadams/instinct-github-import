/*
 * Copyright 2006-2007 Workingmouse
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

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.core.LifecycleMethod;
import com.googlecode.instinct.internal.core.OldDodgySpecificationMethod;
import com.googlecode.instinct.internal.util.MethodInvoker;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.actor.TestSubjectCreator.createSubject;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import java.util.ArrayList;
import org.jmock.Expectations;

@SuppressWarnings({"UnusedDeclaration", "TypeMayBeWeakened"})
public final class OldDodgySpecificationRunnerImplAtomicTest extends InstinctTestCase {
    @Subject(auto = false) private SpecificationRunner specificationRunner;
    @Mock private OldDodgySpecificationMethod specificationMethod;
    @Mock private LifecycleMethod underlyingSpecMethod;
    @Mock private MethodInvokerFactory methodInvokerFactory;
    @Mock private MethodInvoker invoker;
    @Mock private LifeCycleMethodValidator methodValidator;
    @Stub private Class<?> exceptionClass;
    @Stub private String specificationName;

    @Override
    public void setUpSubject() {
        specificationRunner = createSubject(OldDodgySpecificationRunnerImpl.class, methodInvokerFactory, methodValidator);
    }

    public void testConformsToClassTraits() {
        checkClass(OldDodgySpecificationRunnerImpl.class, SpecificationRunner.class);
    }

    // Note. This will be refactored
    public void nsoTestWillRunASpecificationMethod() {
        expect.that(new Expectations() {
            {
                one(methodValidator).checkContextConstructor(String.class);
                exactly(2).of(specificationMethod).getSpecificationMethod();
                will(returnValue(underlyingSpecMethod));
                one(specificationMethod).getBeforeSpecificationMethods();
                will(returnValue(new ArrayList()));
                one(underlyingSpecMethod).getContextClass();
                will(returnValue(String.class));
                one(specificationMethod).isPending();
                will(returnValue(false));
                one(underlyingSpecMethod).getMethod();
                will(returnValue(null));
                one(specificationMethod).getAfterSpecificationMethods();
                will(returnValue(new ArrayList()));
                atLeast(1).of(specificationMethod).getName();
                will(returnValue(specificationName));
                one(specificationMethod).getExpectedException();
                will(returnValue(exceptionClass));
                one(methodValidator).checkMethodHasNoParameters(underlyingSpecMethod);
                one(methodInvokerFactory).create(underlyingSpecMethod);
                will(returnValue(invoker));
                one(invoker).invokeMethod("", null);
            }
        });
        specificationRunner.run(specificationMethod);
    }
}