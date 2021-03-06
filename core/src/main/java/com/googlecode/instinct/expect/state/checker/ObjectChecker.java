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

package com.googlecode.instinct.expect.state.checker;

import org.hamcrest.Matcher;

public interface ObjectChecker<T> {
    void isEqualTo(T t);

    void isNotEqualTo(T t);

    /**
     * Checks whether the argument of the check is an instance of the given <var>type</var>. Note that this method is not compile time type-safe and
     * will accept any <var>type</var>.
     * @param type The type that the argument of the check should be a type of.
     */
    void isAnInstanceOf(Class<?> type);

    /**
     * Checks whether the argument of the check is of the given Note that this method is not compile time type-safe and will accept any
     * <var>type</var>.
     * @param type The type that the argument of the check should not be a type of.
     */
    void isNotAnInstanceOf(Class<?> type);

    /**
     * Synonym for {@link #isAnInstanceOf(Class)}.
     * @param type The type that the argument of the check should be a type of.
     */
    void isOfType(Class<?> type);

    /**
     * Synonym for {@link #isNotAnInstanceOf(Class)}.
     * @param type The type that the argument of the check should not be a type of.
     */
    void isNotOfType(Class<?> type);

    void isTheSameInstanceAs(T t);

    void isNotTheSameInstanceAs(T t);

    void isNull();

    void isNotNull();

    void hasToString(Matcher<String> matcher);

    /**
     * Checks whether the argument of the check all of the given <var>matchers</var>.
     * @param matchers The matchers to check the subject against.
     */
    void matches(Matcher<T>... matchers);

    /**
     * Checks whether the argument of the check matches all of the given <var>matchers</var>.
     * @param iterable The matchers to check the subject against.
     */
    void matches(Iterable<Matcher<? extends T>> iterable);

    void doesNotMatchOnAllOf(Matcher<T>... matchers);

    void doesNotMatchOnAllOf(Iterable<Matcher<? extends T>> matchers);

    /**
     * Checks whether the argument of the check matches any of the given <var>matchers</var>.
     * @param matchers The matchers to check the argument of the check against.
     */
    void matchesAnyOf(Matcher<T>... matchers);

    void matchesAnyOf(Iterable<Matcher<? extends T>> iterable);

    void hasBeanProperty(String propertyName, Class<?> propertyType);

    void hasBeanPropertyWithValue(String propertyName, Class<?> propertyType, Matcher<?> matcher);
}
