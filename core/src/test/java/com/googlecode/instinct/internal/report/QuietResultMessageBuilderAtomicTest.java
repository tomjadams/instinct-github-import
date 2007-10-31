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

package com.googlecode.instinct.internal.report;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.ContextResultImpl;
import com.googlecode.instinct.internal.runner.SpecificationFailureMessageBuilder;
import com.googlecode.instinct.internal.runner.SpecificationFailureMessageBuilderImpl;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.runner.SpecificationResultImpl;
import com.googlecode.instinct.internal.runner.SpecificationRunFailureStatus;
import com.googlecode.instinct.internal.runner.SpecificationRunStatus;
import com.googlecode.instinct.internal.runner.SpecificationRunSuccessStatus;
import com.googlecode.instinct.report.ResultMessageBuilder;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static java.lang.System.getProperty;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.MULTILINE;
import static java.util.regex.Pattern.compile;

public final class QuietResultMessageBuilderAtomicTest extends InstinctTestCase {
    private static final String NEW_LINE = getProperty("line.separator");
    private static final String TAB = "\t";
    private ResultMessageBuilder quietResultMessageBuilder;
    private ContextResult contextResult;
    private SpecificationResult succeedingSpec1;
    private SpecificationResult failingSpec;
    private SpecificationRunStatus failureStatus;
    private SpecificationFailureMessageBuilder failureMessageBuilder;

    @Override
    public void setUpTestDoubles() {
        contextResult = new ContextResultImpl("ContextName");
        failureMessageBuilder = new SpecificationFailureMessageBuilderImpl();
        final RuntimeException failureCause = new RuntimeException("Failure cause");
        failureStatus = new SpecificationRunFailureStatus(failureCause);
        failingSpec = new SpecificationResultImpl("fails", failureStatus, 1L);
        final SpecificationRunStatus successStatus = new SpecificationRunSuccessStatus();
        succeedingSpec1 = new SpecificationResultImpl("runs", successStatus, 1L);
        contextResult.addSpecificationResult(failingSpec);
        contextResult.addSpecificationResult(succeedingSpec1);
        contextResult.addSpecificationResult(new SpecificationResultImpl("soDoesThis", successStatus, 2L));
    }

    @Override
    public void setUpSubject() {
        quietResultMessageBuilder = new QuietResultMessageBuilder();
    }

    public void testConformsToClassTraits() {
        checkClass(BriefResultMessageBuilder.class, ResultMessageBuilder.class);
    }

    public void testCreatesQuietContextResultMessages() {
        final String expectedContextMessage = "ContextName" + NEW_LINE
                + "- fails (FAILED)" + NEW_LINE + NEW_LINE
                + formatFailureCause();
        expect.that(quietResultMessageBuilder.buildMessage(contextResult)).equalTo(expectedContextMessage);
    }

    public void testCreatesQuietSpecificationSuccessResultMessages() {
        expect.that(quietResultMessageBuilder.buildMessage(succeedingSpec1)).equalTo("");
    }

    public void testCreatesQuietSpecificationFailuresResultMessages() {
        final String expected = "fails (FAILED)" + NEW_LINE + NEW_LINE + formatFailureCause();
        expect.that(quietResultMessageBuilder.buildMessage(failingSpec)).equalTo(expected);
    }

    private String formatFailureCause() {
        final String failureCause = failureMessageBuilder.buildMessage(failureStatus);
        final Pattern startOfLine = compile("^", MULTILINE);
        final Matcher matcher = startOfLine.matcher(failureCause);
        return removeLastNewline(matcher.replaceAll(TAB));
    }

    private String removeLastNewline(final String failureCause) {
        return failureCause.endsWith(NEW_LINE) ? failureCause.substring(0, failureCause.length() - 1) : failureCause;
    }
}