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

package com.googlecode.instinct.internal.core;

import com.googlecode.instinct.internal.util.Fix;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.TechNote;
import com.googlecode.instinct.internal.util.lang.Primordial;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public final class LifecycleMethodImpl extends Primordial implements LifecycleMethod {
    private final Method method;
    private final Class<?> contextClass;

    @Fix("Can't call this() because of the null check :| Remove this duplication if possible.")
    public LifecycleMethodImpl(final Method method) {
        checkNotNull(method);
        this.method = method;
        contextClass = method.getDeclaringClass();
    }

    public LifecycleMethodImpl(final Method method, final Class<?> contextClass) {
        checkNotNull(method, contextClass);
        this.contextClass = contextClass;
        this.method = method;
    }

    public String getName() {
        return method.getName();
    }

    public Method getMethod() {
        return method;
    }

    public Annotation[] getAnnotations() {
        return method.getAnnotations();
    }

    public Annotation[][] getParameterAnnotations() {
        return method.getParameterAnnotations();
    }

    public Class<?> getContextClass() {
        return contextClass;
    }

    @TechNote("generate the hashCode based on the method name, as methods with the same name on different classes have different hashCodes.")
    @Override
    public int hashCode() {
        return method.getName().hashCode();
    }

    @SuppressWarnings({"ParameterNameDiffersFromOverriddenParameter"})
    @TechNote("equals() is only called if the hashCodes are equal. Implied by the spec but tricky non-the-less.")
    @Override
    public boolean equals(final Object object) {
        checkNotNull(object);
        if (object == null || !(object instanceof LifecycleMethod)) {
            return false;
        }
        final Method methodToCompare = ((LifecycleMethod) object).getMethod();
        return method.getName().equals(methodToCompare.getName()) || method.equals(object);
    }
}