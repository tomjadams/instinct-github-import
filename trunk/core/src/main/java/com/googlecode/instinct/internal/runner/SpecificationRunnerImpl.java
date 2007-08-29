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

import com.googlecode.instinct.internal.core.LifecycleMethod;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import static com.googlecode.instinct.internal.runner.SpecificationRunSuccessStatus.SPECIFICATION_SUCCESS;
import com.googlecode.instinct.internal.util.Clock;
import com.googlecode.instinct.internal.util.ClockImpl;
import com.googlecode.instinct.internal.util.ConstructorInvoker;
import com.googlecode.instinct.internal.util.ConstructorInvokerImpl;
import com.googlecode.instinct.internal.util.MethodInvoker;
import com.googlecode.instinct.internal.util.MethodInvokerImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.runner.SpecificationListener;
import java.util.ArrayList;
import java.util.Collection;

public final class SpecificationRunnerImpl implements SpecificationRunner {
    private final Collection<SpecificationListener> specificationListeners = new ArrayList<SpecificationListener>();
    private final ConstructorInvoker constructorInvoker = new ConstructorInvokerImpl();
    //    private final TestDoubleAutoWirer testDoubleAutoWirer = new TestDoubleAutoWirerImpl();
    private final Clock clock = new ClockImpl();
    private MethodInvoker methodInvoker = new MethodInvokerImpl();
    private LifeCycleMethodValidator methodValidator = new LifeCycleMethodValidatorImpl();
    //    private final MockVerifier mockVerifier = new MockVerifierImpl();
    private MethodInvokerFactory methodInvokerFactory = new MethodInvokerFactoryImpl();

    public void addSpecificationListener(final SpecificationListener specificationListener) {
        checkNotNull(specificationListener);
        specificationListeners.add(specificationListener);
    }

    @Suggest({"Does each specification get it's own JMock12Mockery?", " How will this work if we want to allow manual mocking?",
            "Need access to the same statics",
            "Maybe pass in a BC class instantiation strategy, so that we can enable creating of only one instance of a BC, rather than one per spec"})
    public SpecificationResult run(final SpecificationMethod specificationMethod) {
        checkNotNull(specificationMethod);
        notifyListenersOfPreSpecification(specificationMethod);
        final SpecificationResult specificationResult = doRun(specificationMethod);
        notifyListenersOfPostSpecification(specificationMethod, specificationResult);
        return specificationResult;
    }

    // SUPPRESS IllegalCatch {
    @SuppressWarnings({"CatchGenericClass"})
    @Suggest("Make a clock wrapper that looks like org.jbehave.core.util.Timer.")
    private SpecificationResult doRun(final SpecificationMethod specificationMethod) {
        final long startTime = clock.getCurrentTime();
        try {
            // expose the context class, rather than getting the method
            final Class<?> contextClass = specificationMethod.getSpecificationMethod().getDeclaringClass();
            final Object instance = invokeConstructor(contextClass);
            runSpecificationLifecycle(instance, specificationMethod);
            return createSpecResult(specificationMethod, SPECIFICATION_SUCCESS, startTime);
        } catch (Throwable e) {
            final SpecificationRunStatus status = new SpecificationRunFailureStatus(e);
            return createSpecResult(specificationMethod, status, startTime);
        }
    }
    // } SUPPRESS IllegalCatch

    private SpecificationResult createSpecResult(final SpecificationMethod specificationMethod, final SpecificationRunStatus runStatus,
            final long startTime) {
        final long executionTime = clock.getElapsedTime(startTime);
        return new SpecificationResultImpl(specificationMethod.getName(), runStatus, executionTime);
    }

    @Suggest({"Expose this lifecycle?",
            "May need to stick verification of mocks in finally, if we report them as well as other errors."})
    private void runSpecificationLifecycle(final Object contextInstance, final SpecificationMethod specificationMethod) {
        try {
//            testDoubleAutoWirer.wire(contextInstance);
            runMethods(contextInstance, specificationMethod.getBeforeSpecificationMethods());
            runSpecificationMethod(contextInstance, specificationMethod.getSpecificationMethod());
//            mockVerifier.verify(contextInstance);
        } finally {
            runMethods(contextInstance, specificationMethod.getAfterSpecificationMethods());
        }
    }

    private void runSpecificationMethod(final Object contextInstance, final LifecycleMethod specificationMethod) {
        methodValidator.checkMethodHasNoParameters(specificationMethod);
        final MethodInvoker specMethodInvoker = methodInvokerFactory.create(specificationMethod);
        specMethodInvoker.invokeMethod(contextInstance, specificationMethod.getMethod());
    }

    private void notifyListenersOfPreSpecification(final SpecificationMethod specificationMethod) {
        for (final SpecificationListener specificationListener : specificationListeners) {
            specificationListener.preSpecificationMethod(specificationMethod);
        }
    }

    private void notifyListenersOfPostSpecification(final SpecificationMethod specificationMethod, final SpecificationResult specificationResult) {
        for (final SpecificationListener specificationListener : specificationListeners) {
            specificationListener.postSpecificationMethod(specificationMethod, specificationResult);
        }
    }

    private void runMethods(final Object instance, final Collection<LifecycleMethod> methods) {
        for (final LifecycleMethod method : methods) {
            runMethod(instance, method);
        }
    }

    @Suggest("Share this check logic with the IntelliJ integration.")
    private void runMethod(final Object instance, final LifecycleMethod method) {
        methodValidator.checkMethodHasNoParameters(method);
        methodInvoker.invokeMethod(instance, method.getMethod());
    }

    @Suggest("Share this check logic with the IntelliJ integration.")
    private <T> Object invokeConstructor(final Class<T> cls) {
        methodValidator.checkContextConstructor(cls);
        return constructorInvoker.invokeNullaryConstructor(cls);
    }
}