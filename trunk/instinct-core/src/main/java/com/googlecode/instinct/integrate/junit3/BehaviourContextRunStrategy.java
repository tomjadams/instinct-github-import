package com.googlecode.instinct.integrate.junit3;

import com.googlecode.instinct.internal.runner.BehaviourContextResult;

public interface BehaviourContextRunStrategy {
    BehaviourContextResult onBehaviourContext(BehaviourContextClass behaviourContext);
}