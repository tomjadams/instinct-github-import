package com.googlecode.instinct.internal.runner;

import com.googlecode.instinct.core.BehaviourContextConfigurationException;
import static com.googlecode.instinct.test.AssertTestChecker.assertThrows;
import com.googlecode.instinct.test.InstinctTestCase;
import com.googlecode.instinct.internal.runner.BehaviourContextRunner;
import com.googlecode.instinct.internal.runner.BehaviourContextRunnerImpl;

public final class BehaviourContextRunnerSlowTest extends InstinctTestCase {
    private BehaviourContextRunner runner;

    public void testRunWithSuccess() {
        runContext(ContextContainerWithSetUpAndTearDown.class);
        runContext(ContextContainerWithSetUpAndTearDown.AnEmbeddedPublicContext.class);
        runContext(ContextContainerWithConstructors.APublicConstructor.class);
    }

    public void testInvalidConstructorsThrowConfigException() {
        checkInvalidConstructorsThrowConfigException(ContextContainerWithConstructors.AConstructorWithParameters.class);
        checkInvalidConstructorsThrowConfigException(ContextContainerWithConstructors.APrivateConstructor.class);
        checkInvalidConstructorsThrowConfigException(ContextContainerWithConstructors.APackageLocalConstructor.class);
        checkInvalidConstructorsThrowConfigException(ContextContainerWithConstructors.AProtectedConstructor.class);
    }

    private <T> void checkInvalidConstructorsThrowConfigException(final Class<T> cls) {
        assertThrows(BehaviourContextConfigurationException.class, new Runnable() {
            public void run() {
                runContext(cls);
            }
        });
    }

    private <T> void runContext(final Class<T> cls) {
        runner.run(cls);
    }

    @Override
    public void setUpSubject() {
        runner = new BehaviourContextRunnerImpl();
    }
}
