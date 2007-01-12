package com.googlecode.instinct.internal.runner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import com.googlecode.instinct.core.LifeCycleMethodConfigurationException;
import com.googlecode.instinct.core.annotate.AfterSpecification;
import com.googlecode.instinct.core.annotate.BeforeSpecification;
import com.googlecode.instinct.core.annotate.Specification;
import com.googlecode.instinct.core.naming.AfterSpecificationNamingConvention;
import com.googlecode.instinct.core.naming.BeforeSpecificationNamingConvention;
import com.googlecode.instinct.core.naming.NamingConvention;
import com.googlecode.instinct.core.naming.SpecificationNamingConvention;
import com.googlecode.instinct.internal.aggregate.locate.MarkedMethodLocator;
import com.googlecode.instinct.internal.aggregate.locate.MarkedMethodLocatorImpl;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.AssertThrowsChecker.assertThrows;

@SuppressWarnings({"OverlyCoupledClass"})
public final class SpecificationRunnerSlowTest extends InstinctTestCase {
    private final MarkedMethodLocator methodLocator = new MarkedMethodLocatorImpl();
    private SpecificationRunner runner;

    public void testRunWithSuccess() {
        final SpecificationContext[] contexts = findContexts(ContextContainerWithSetUpAndTearDown.class);
        for (final SpecificationContext context : contexts) {
            runner.run(context);
        }
    }

    public void testInvalidMethodsBarf() {
        checkInvalidMethodsBarf(ContextWithInvalidlyMarkedSpecification1.class);
        checkInvalidMethodsBarf(ContextWithInvalidlyMarkedSpecification2.class);
        checkInvalidMethodsBarf(ContextWithInvalidlyMarkedAfterSpecification1.class);
        checkInvalidMethodsBarf(ContextWithInvalidlyMarkedAfterSpecification2.class);
        checkInvalidMethodsBarf(ContextWithInvalidlyMarkedBeforeSpecification1.class);
        checkInvalidMethodsBarf(ContextWithInvalidlyMarkedBeforeSpecification2.class);
    }

    private <T> void checkInvalidMethodsBarf(final Class<T> cls) {
        final SpecificationContext[] contexts = findContexts(cls);
        for (final SpecificationContext context : contexts) {
            runSpecificationExpectingException(context);
        }
    }

    private void runSpecificationExpectingException(final SpecificationContext context) {
        assertThrows(LifeCycleMethodConfigurationException.class, new Runnable() {
            public void run() {
                runner.run(context);
            }
        });
    }

    private <T> SpecificationContext[] findContexts(final Class<T> cls) {
        final Method[] before = getMethods(cls, BeforeSpecification.class, new BeforeSpecificationNamingConvention());
        final Method[] after = getMethods(cls, AfterSpecification.class, new AfterSpecificationNamingConvention());
        final Method[] specs = getMethods(cls, Specification.class, new SpecificationNamingConvention());
        final SpecificationContext[] contexts = new SpecificationContext[specs.length];
        for (int i = 0; i < contexts.length; i++) {
            contexts[i] = new SpecificationContextImpl(cls, before, after, specs[i]);
        }
        return contexts;
    }

    private <T, A extends Annotation> Method[] getMethods(final Class<T> cls, final Class<A> annotationType,
            final NamingConvention namingConvention) {
        return methodLocator.locateAll(cls, annotationType, namingConvention);
    }

    @Override
    public void setUpSubject() {
        runner = new SpecificationRunnerImpl();
    }
}
