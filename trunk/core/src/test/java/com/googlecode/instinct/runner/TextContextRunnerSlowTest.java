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

package com.googlecode.instinct.runner;

import com.googlecode.instinct.internal.aggregate.ContextWithSpecsWithDifferentAccessModifiers;
import com.googlecode.instinct.internal.core.ContextClassImpl;
import com.googlecode.instinct.internal.runner.ASimpleContext;
import com.googlecode.instinct.internal.runner.ContextContainerWithSetUpAndTearDown;
import com.googlecode.instinct.internal.runner.ContextRunner;
import static com.googlecode.instinct.report.ResultFormat.BRIEF;
import static com.googlecode.instinct.runner.TextContextRunner.runContexts;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.io.StreamRedirector.doWithRedirectedStandardOut;
import java.io.ByteArrayOutputStream;

public final class TextContextRunnerSlowTest extends InstinctTestCase {
    private ContextRunner contextRunner;
    private ByteArrayOutputStream outputBuffer;

    @Override
    public void setUpTestDoubles() {
        outputBuffer = new ByteArrayOutputStream();
    }

    @Override
    public void setUpSubject() {
        contextRunner = new TextContextRunner(outputBuffer, BRIEF);
    }

    public void testSendsSpeciciationResultsToOutput() {
        checkSendsSpeciciationResultsToOutput(ASimpleContext.class);
        checkSendsSpeciciationResultsToOutput(ContextContainerWithSetUpAndTearDown.class);
    }

    public void testCanBeCalledStaticallySendingResultsToStandardOut() {
        doWithRedirectedStandardOut(outputBuffer, new Runnable() {
            public void run() {
                runContexts(ASimpleContext.class, ContextContainerWithSetUpAndTearDown.class, ContextWithSpecsWithDifferentAccessModifiers.class);
            }
        });
        checkRunnerSendsSpeciciationResultsToOutput(ASimpleContext.class);
        checkRunnerSendsSpeciciationResultsToOutput(ContextContainerWithSetUpAndTearDown.class);
        checkRunnerSendsSpeciciationResultsToOutput(ContextWithSpecsWithDifferentAccessModifiers.class);
    }

    private <T> void checkSendsSpeciciationResultsToOutput(final Class<T> contextClass) {
        contextRunner.run(new ContextClassImpl(contextClass));
        checkRunnerSendsSpeciciationResultsToOutput(contextClass);
    }

    private <T> void checkRunnerSendsSpeciciationResultsToOutput(final Class<T> contextClass) {
        final String runnerOutput = new String(outputBuffer.toByteArray());
        assertTrue("Expected to find context name", runnerOutput.contains(contextClass.getSimpleName()));
    }
}
