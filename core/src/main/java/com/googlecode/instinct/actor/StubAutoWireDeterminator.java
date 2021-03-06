/*
 * Copyright 2006-2007 Workingmouse
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

package com.googlecode.instinct.actor;

import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.naming.NamingConvention;
import com.googlecode.instinct.marker.naming.StubNamingConvention;
import java.lang.reflect.Field;

public final class StubAutoWireDeterminator implements AutoWireDeterminator {
    private final NamingConvention namingConvention = new StubNamingConvention();

    public Boolean f(final Field field) {
        final Stub annotation = field.getAnnotation(Stub.class);
        if (annotation == null) {
            return field.getName().matches(namingConvention.getPattern());
        } else {
            return annotation.auto();
        }
    }
}
