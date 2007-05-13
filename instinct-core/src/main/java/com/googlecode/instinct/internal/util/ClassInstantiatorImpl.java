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

package com.googlecode.instinct.internal.util;

import java.io.File;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdge;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdgeImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class ClassInstantiatorImpl implements ClassInstantiator {
    private JavaClassNameFactory classNameFactory = new JavaClassNameFactoryImpl();
    private ClassEdge classEdge = new ClassEdgeImpl();

    public Class<?> instantiateClass(final File classFile, final File packageRoot) {
        checkNotNull(classFile, packageRoot);
        final JavaClassName className = classNameFactory.create(packageRoot, classFile);
        return classEdge.forName(className.getFullyQualifiedName());
    }

    public Class<?> instantiateClass(final String className) {
        checkNotNull(className);
        return classEdge.forName(className);
    }
}
