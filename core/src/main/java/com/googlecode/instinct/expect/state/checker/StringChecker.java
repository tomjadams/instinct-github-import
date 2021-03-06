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

import com.googlecode.instinct.internal.util.Suggest;

@Suggest("Pull out the CharSequence stuff")
public interface StringChecker extends ComparableChecker<String>, SizeChecker {
    void isEqualToIgnoringCase(String string);

    void isEqualToIgnoringWhiteSpace(String string);

    void isNotEqualToIgnoringCase(String string);

    void isNotEqualToIgnoringWhiteSpace(String string);

    void containsString(String string);

    void doesNotContainString(String string);

    void endsWith(String string);

    void doesNotEndWith(String string);

    void startsWith(String string);

    void doesNotStartWith(String string);

    void isOfLength(int length);

    void matchesRegex(String regularExpression);

    void matchesRegex(String regularExpression, int flags);

    void doesNotMatchRegex(String regularExpression);

    void doesNotMatchRegex(String regularExpression, int flags);
}
