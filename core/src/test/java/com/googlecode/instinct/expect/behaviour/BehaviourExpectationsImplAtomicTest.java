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

package com.googlecode.instinct.expect.behaviour;

import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

@Suggest("Come back & fix this test up once we can wire in dependencies to statics.")
public final class BehaviourExpectationsImplAtomicTest extends InstinctTestCase {
//    private BehaviourExpectations behaviourExpectations;
//    private Expectations expectations;

//    @Override
//    public void setUpTestDoubles() {
//        expectations = mock(Expectations.class);
//    }

//    @Override
//    public void setUpSubject() {
//        behaviourExpectations = createSubject(BehaviourExpectationsImpl.class);
//    }

    public void testConformsToClassTraits() {
        checkClass(BehaviourExpectationsImpl.class, BehaviourExpectations.class);
    }

//    public void testSetsExpectationsUsingJMock2Syntax() {
//        expects(mockery).method("checking").with(eq(expectations));
//        behaviourExpectations.that(expectations);
//    }
}
