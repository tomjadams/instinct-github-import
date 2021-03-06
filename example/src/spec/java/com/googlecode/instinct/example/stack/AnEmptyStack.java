package com.googlecode.instinct.example.stack;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Specification;
import static com.googlecode.instinct.marker.annotate.Specification.SpecificationState.PENDING;
import com.googlecode.instinct.marker.annotate.Subject;
import org.junit.runner.RunWith;

@SuppressWarnings({"UnusedDeclaration"})
@RunWith(InstinctRunner.class)
public final class AnEmptyStack {
    @Subject private Stack<Object> stack;
    @Dummy private Object object;

    @BeforeSpecification
    void before() {
        stack = new StackImpl<Object>();
    }

    @Specification
    void isEmpty() {
        expect.that(stack.isEmpty()).isTrue();
    }

    @Specification
    void isNoLongerBeEmptyAfterPush() {
        stack.push(object);
        expect.that(stack.isEmpty()).isFalse();
    }

    @Specification(expectedException = IllegalStateException.class, withMessage = "Cannot pop an empty stack")
    void throwsExceptionWhenPopped() {
        stack.pop();
    }

    @Specification(state = PENDING, reason = "No idea what we're going to do")
    void hasSomeNewFeatureWeHaveNotThoughtOfYet() {
        expect.that(true).isFalse();
    }
}
