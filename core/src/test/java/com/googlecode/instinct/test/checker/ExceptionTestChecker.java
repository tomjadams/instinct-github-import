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

package com.googlecode.instinct.test.checker;

import com.googlecode.instinct.internal.expect.state.matcher.AssertException;
import com.googlecode.instinct.internal.expect.state.matcher.AssertExceptionImpl;

public final class ExceptionTestChecker {
    private static final AssertException ASSERT_EXCEPTION = new AssertExceptionImpl();

    private ExceptionTestChecker() {
        throw new UnsupportedOperationException();
    }

    public static <T extends Throwable> Throwable expectException(final Class<T> expectedException, final String message, final Runnable block) {
        return ASSERT_EXCEPTION.assertThrows(expectedException, message, block);
    }

    public static <T extends Throwable> Throwable expectException(final Class<T> expectedException, final Runnable block) {
        return ASSERT_EXCEPTION.assertThrows(expectedException, block);
    }

    public static void expectMessageContains(final Throwable t, final String fragment) {
        ASSERT_EXCEPTION.assertMessageContains(t, fragment);
    }
}
