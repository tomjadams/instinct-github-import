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

package com.googlecode.instinct.integrate.ant;

import com.googlecode.instinct.internal.runner.BehaviourContextResult;
import com.googlecode.instinct.internal.runner.BehaviourContextRunner;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class AntBehaviourContextRunner implements BehaviourContextRunner {
    private final BehaviourContextRunner wrappedRunner;
    private final BehaviourContextResultMessageBuilder messageBuilder;
    private final StatusLogger statusLogger;

    public AntBehaviourContextRunner(final BehaviourContextRunner wrappedRunner, final BehaviourContextResultMessageBuilder messageBuilder,
            final StatusLogger statusLogger) {
        checkNotNull(wrappedRunner, messageBuilder, statusLogger);
        this.wrappedRunner = wrappedRunner;
        this.messageBuilder = messageBuilder;
        this.statusLogger = statusLogger;
    }

    public <T> BehaviourContextResult run(final Class<T> behaviourContextClass) {
        checkNotNull(behaviourContextClass);
        final BehaviourContextResult behaviourContextResult = wrappedRunner.run(behaviourContextClass);
        logResults(behaviourContextResult);
        return behaviourContextResult;
    }

    private void logResults(final BehaviourContextResult behaviourContextResult) {
        final String message = messageBuilder.buildMessage(behaviourContextResult);
        statusLogger.log(message);
    }
}