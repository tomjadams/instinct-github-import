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

package com.googlecode.instinct.internal.locate.cls;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.util.instance.ObjectFactory;
import static com.googlecode.instinct.marker.AnnotationAttribute.IGNORE;
import com.googlecode.instinct.marker.MarkingScheme;
import com.googlecode.instinct.marker.MarkingSchemeImpl;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.marker.naming.ContextNamingConvention;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.actor.TestSubjectCreator.createSubjectWithConstructorArgs;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import java.io.File;
import java.io.FileFilter;
import org.jmock.Expectations;

public final class ClassWithContextAnnotationFileFilterAtomicTest extends InstinctTestCase {
    @Subject(auto = false) private FileFilter filter;
    @Mock private File packageRoot;
    @Mock private File pathname;
    @Mock private ObjectFactory objectFactory;
    @Mock private MarkedFileChecker checker;
    @Stub(auto = false) private MarkingScheme markingScheme;

    @Override
    public void setUpTestDoubles() {
        markingScheme = new MarkingSchemeImpl(Context.class, new ContextNamingConvention(), IGNORE);
    }

    @Override
    public void setUpSubject() {
        final Object[] constructorArgs = {packageRoot, markingScheme};
        filter = createSubjectWithConstructorArgs(ClassWithContextAnnotationFileFilter.class, constructorArgs, objectFactory);
    }

    public void testConformsToClassTraits() {
        checkClass(ClassWithContextAnnotationFileFilter.class, FileFilter.class);
    }

    public void testAccept() {
        checkAccept(false, true, true);
        checkAccept(true, false, false);
    }

    private void checkAccept(final boolean pathIsADirectory, final boolean classHasAnnotation, final boolean isAnnotated) {
        expect.that(new Expectations() {
            {
                one(objectFactory).create(MarkedClassFileChecker.class, packageRoot);
                will(returnValue(checker));
                one(pathname).isDirectory();
                will(returnValue(pathIsADirectory));
                if (!pathIsADirectory) {
                    one(checker).isMarked(pathname, markingScheme);
                    will(returnValue(classHasAnnotation));
                }
            }
        });
        final boolean accept = filter.accept(pathname);
        expect.that(accept).isEqualTo(isAnnotated);
    }
}
