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

package com.googlecode.instinct.internal.locate;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.TestSubjectCreator.createSubjectWithConstructorArgs;
import java.io.File;
import java.io.FileFilter;
import org.jmock.Expectations;

public final class AnnotationFileFilterAtomicTest extends InstinctTestCase {
    @Subject(auto = false) private FileFilter filter;
    @Mock private File packageRoot;
    @Mock private File pathname;
    @Mock private ObjectFactory objectFactory;
    @Mock private AnnotatedClassFileChecker checker;

    @Override
    public void setUpSubject() {
        filter = createSubjectWithConstructorArgs(AnnotationFileFilter.class, new Object[]{packageRoot, Context.class}, objectFactory);
    }

    public void testConformsToClassTraits() {
        checkClass(AnnotationFileFilter.class, FileFilter.class);
    }

    public void testAccept() {
        checkAccept(false, true, true);
        checkAccept(true, false, false);
    }

    private void checkAccept(final boolean pathIsADirectory, final boolean classHasAnnotation, final boolean isAnnotated) {
        expect.that(new Expectations() {
            {
                one(objectFactory).create(AnnotatedClassFileCheckerImpl.class, packageRoot); will(returnValue(checker));
                one(pathname).isDirectory(); will(returnValue(pathIsADirectory));
                if (!pathIsADirectory) {
                    one(checker).isAnnotated(pathname, Context.class); will(returnValue(classHasAnnotation));
                }
            }
        });
        final boolean accept = filter.accept(pathname);
        expect.that(accept).equalTo(isAnnotated);
    }
}