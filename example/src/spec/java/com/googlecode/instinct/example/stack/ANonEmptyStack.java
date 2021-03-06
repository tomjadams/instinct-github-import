package com.googlecode.instinct.example.stack;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class ANonEmptyStack {
    private static final int STACK_SIZE = 10;
    @Subject private Stack<Integer> stack;
    @Stub private Integer number1;
    @Stub private Integer number2;

    @BeforeSpecification
    void fillUpStack() {
        stack = new StackImpl<Integer>();
        for (int i = 0; i < STACK_SIZE; i++) {
            stack.push(i);
        }
    }

    @Specification
    void isNotEmpty() {
        expect.that(stack.isEmpty()).isFalse();
    }

    @Suggest("This is bogus, need an isFull() method.")
    @Specification
    void isNoLongerBeFullAfterPop() {
        stack.pop();
        expect.that(stack.isEmpty()).isFalse();
    }

    @Specification
    void isNoLongerFullAfterPoppingAllElements() {
        for (int i = 0; i < STACK_SIZE; i++) {
            stack.pop();
        }
        expect.that(stack.isEmpty()).isTrue();
    }

    @Specification(expectedException = IllegalArgumentException.class)
    void throwsExceptionWhenANullIsPushed() {
        stack.push(null);
    }

    @Specification
    void popsPushedValue() {
        stack.push(number1);
        final Object o = stack.pop();
        expect.that(o).isTheSameInstanceAs(number1);
    }

    @Specification
    void shouldPopSecondPushedValueFirst() {
        stack.push(number1);
        stack.push(number2);
        expect.that(stack.pop()).isEqualTo(number2);
    }

    @Specification
    void leavesValueOnStackAfterPeek() {
        stack.push(number1);
        final Integer peekResult = stack.peek();
        final Integer popResult = stack.pop();
        expect.that(popResult).isTheSameInstanceAs(peekResult);
    }
}
