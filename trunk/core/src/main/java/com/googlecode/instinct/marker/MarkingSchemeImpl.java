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

package com.googlecode.instinct.marker;

import java.lang.annotation.Annotation;
import au.net.netstorm.boost.primordial.Primordial;
import com.googlecode.instinct.internal.util.Fix;
import com.googlecode.instinct.internal.util.ParamChecker;
import com.googlecode.instinct.marker.naming.NamingConvention;

@Fix({"Test drive.", "Turn this into an enumeration."})
public final class MarkingSchemeImpl extends Primordial implements MarkingScheme {
    private final Class<?> annotationType;
    private final NamingConvention namingConvention;

    public <A extends Annotation> MarkingSchemeImpl(final Class<A> annotationType, final NamingConvention namingConvention) {
        ParamChecker.checkNotNull(annotationType, namingConvention);
        this.annotationType = annotationType;
        this.namingConvention = namingConvention;
    }

    @SuppressWarnings({"unchecked"})
    public <A extends Annotation> Class<A> getAnnotationType() {
        return (Class<A>) annotationType;
    }

    public NamingConvention getNamingConvention() {
        return namingConvention;
    }
}