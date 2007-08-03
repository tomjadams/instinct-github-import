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

package com.googlecode.instinct.expect.state;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

// TODO Test this
public class ArrayCheckerImpl<T> extends ObjectCheckerImpl<T[]> implements ArrayChecker<T> {

    public ArrayCheckerImpl(T[] subject) {
        super(subject);
    }

    public final void containsItem(Matcher<T> matcher) {
        getAsserter().expectThat(subject, Matchers.hasItemInArray(matcher));
    }

    public final void containsItem(T t) {
        getAsserter().expectThat(subject, Matchers.hasItemInArray(t));
    }

    public final void notContainItem(Matcher<T> matcher) {
        getAsserter().expectNotThat(subject, Matchers.hasItemInArray(matcher));
    }

    public final void notContainItem(T t) {
        getAsserter().expectNotThat(subject, Matchers.hasItemInArray(t));
    }

    public final void hasLength(int length) {
        getAsserter().expectThat(subject.length, Matchers.equalTo(length));
    }
}