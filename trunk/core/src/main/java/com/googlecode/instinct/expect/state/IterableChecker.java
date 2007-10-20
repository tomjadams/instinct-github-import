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

public interface IterableChecker<E, T extends Iterable<E>> extends ObjectChecker<T> {

    void containsItem(E element);

    void containsItem(Matcher<E> matcher);

    void notContainItem(E item);

    void notContainItem(Matcher<E> matcher);

    void containsItems(Matcher<E>... matchers);

    void containsItems(E... items);

    void notContainItems(Matcher<E>... matchers);

    void notContainItems(E... items);
}