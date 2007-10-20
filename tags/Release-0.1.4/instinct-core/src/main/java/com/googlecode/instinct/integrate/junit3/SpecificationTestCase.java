package com.googlecode.instinct.integrate.junit3;

import au.net.netstorm.boost.edge.EdgeException;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.runner.SpecificationRunStatus;
import com.googlecode.instinct.internal.util.ExceptionFinder;
import com.googlecode.instinct.internal.util.ExceptionFinderImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import junit.framework.TestCase;
import junit.framework.TestResult;

@SuppressWarnings({"JUnitTestCaseInProductSource", "UnconstructableJUnitTestCase", "JUnitTestCaseWithNoTests",
        "JUnitTestCaseWithNonTrivialConstructors"})
public final class SpecificationTestCase extends TestCase {
    private final ExceptionFinder exceptionFinder = new ExceptionFinderImpl();
    private TestResult result;
    private SpecificationMethod specificationMethod;

    public SpecificationTestCase(final SpecificationMethod specificationMethod) {
        checkNotNull(specificationMethod);
        this.specificationMethod = specificationMethod;
    }

    @Override
    public void run(final TestResult result) {
        this.result = result;
        super.run(result);
    }

    @Override
    public void runBare() {
        try {
            runSpecification();
        } catch (EdgeException e) {
            exceptionFinder.rethrowRealError(e);
        }
    }

    @Override
    public String getName() {
        return specificationMethod.getName();
    }

    @SuppressWarnings({"CatchGenericClass"})
    // SUPPRESS IllegalCatch {
    private void runSpecification() {
        try {
            final SpecificationResult specificationResult = specificationMethod.run();
            processSpecificationResult(specificationResult);
        } catch (Throwable e) {
            result.addError(this, e);
        }
    }
    // } SUPPRESS IllegalCatch

    private void processSpecificationResult(final SpecificationResult specificationResult) {
        if (!specificationResult.completedSuccessfully()) {
            final SpecificationRunStatus status = specificationResult.getStatus();
            final Throwable error = (Throwable) status.getDetailedStatus();
            result.addFailure(this, new ChainableAssertionFailedError(exceptionFinder.getRootCause(error)));
        }
    }
}