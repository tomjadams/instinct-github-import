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

package com.googlecode.instinct.internal.util.boost;

public interface AssertException {
    Throwable assertWraps(Class expectedException, Throwable wrapperException);

    Throwable assertWraps(Class expectedException, String expectedMessage, Throwable wrapperException);

    Throwable assertWraps(Class expectedExceptionClass, Throwable wrapperException, int depthExceptionShouldAppearAt);

    Throwable assertWraps(Class expectedExceptionClass, String expectedMessage, Throwable wrapperException, int depthExceptionShouldAppearAt);

    void checkExceptionClass(Class expectedExceptionClass, Throwable actual);

    void checkExceptionMessage(String expectedMessage, Throwable actual);

    Throwable assertThrows(Class expectedException, String message, Runnable block);

    Throwable assertThrows(Class expectedException, Runnable block);

    void assertMessageContains(Throwable t, String fragment);
}