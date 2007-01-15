package com.googlecode.instinct.internal.mock;

import org.jmock.builder.NameMatchBuilder;
import org.jmock.core.Constraint;
import org.jmock.core.InvocationMatcher;
import org.jmock.core.Stub;
import org.jmock.core.constraint.IsAnything;
import org.jmock.core.constraint.IsEqual;
import org.jmock.core.constraint.IsSame;
import org.jmock.core.matcher.InvokeCountMatcher;
import org.jmock.core.matcher.InvokeOnceMatcher;
import org.jmock.core.stub.ReturnStub;

public final class MockeryImpl implements Mockery {
    private final Verifier verifier = new VerifierImpl();
    private final MockHolder holder = new MockHolderImpl();
    private final MockCreator mockCreator = new JMockMockCreator();

    @SuppressWarnings({"unchecked"})
    public <T> T mock(final Class<T> toMock) {
        final MockControl mockControl = mockCreator.createMockController(toMock);
        final Object mockedObject = createMockedObject(mockControl);
        register(mockControl, mockedObject);
        return (T) mockedObject;
    }

    @SuppressWarnings({"unchecked"})
    public <T> T mock(final Class<T> toMock, final String roleName) {
        final MockControl mockControl = mockCreator.createMockController(toMock, roleName);
        final Object mockedObject = createMockedObject(mockControl);
        register(mockControl, mockedObject);
        return (T) mockedObject;
    }

    public NameMatchBuilder expects(final Object mockedObject) {
        return expects(mockedObject, once());
    }

    public NameMatchBuilder expects(final Object mockedObject, final InvocationMatcher expectation) {
        final MockControl mockController = holder.getMockController(mockedObject);
        return mockController.expects(expectation);
    }

    public InvocationMatcher once() {
        return new InvokeOnceMatcher();
    }

    public InvocationMatcher times(final int expectedNumberOfCalls) {
        return new InvokeCountMatcher(expectedNumberOfCalls);
    }

    public Constraint same(final Object argument) {
        return new IsSame(argument);
    }

    public Constraint anything() {
        return new IsAnything();
    }

    public Constraint eq(final Object argument) {
        return new IsEqual(argument);
    }

    public Stub returnValue(final Object returnValue) {
        return new ReturnStub(returnValue);
    }

    public void verify() {
        verifier.verify();
    }

    @SuppressWarnings({"unchecked"})
    private <T> T createMockedObject(final MockControl mockControl) {
        return (T) mockControl.getMockedObject();
    }

    private void register(final MockControl mockControl, final Object mockedObject) {
        holder.addControl(mockControl, mockedObject);
        verifier.addVerifiable(mockControl);
    }
}