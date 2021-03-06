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

import com.googlecode.instinct.expect.state.matcher.FileExistsMatcher;
import java.io.File;

public final class FileCheckerImpl extends ObjectCheckerImpl<File> implements FileChecker {
    public FileCheckerImpl(final File subject) {
        super(subject);
    }

    public void exists() {
        getAsserter().expectThat(subject, FileExistsMatcher.exists());
    }

    public void doesNotExist() {
        getAsserter().expectNotThat(subject, FileExistsMatcher.exists());
    }
}
