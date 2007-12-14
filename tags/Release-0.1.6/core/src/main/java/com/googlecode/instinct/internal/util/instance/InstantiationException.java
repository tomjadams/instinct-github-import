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

package com.googlecode.instinct.internal.util.instance;

public final class InstantiationException extends RuntimeException {
    private static final long serialVersionUID = -3116405763603412997L;

    public InstantiationException(final String message) {
        super(message);
    }

    public InstantiationException(final Throwable cause) {
        super(cause);
    }

    public InstantiationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}


