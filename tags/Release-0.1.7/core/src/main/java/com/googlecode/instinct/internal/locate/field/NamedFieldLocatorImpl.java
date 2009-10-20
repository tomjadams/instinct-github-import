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

package com.googlecode.instinct.internal.locate.field;

import com.googlecode.instinct.marker.naming.NamingConvention;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

public final class NamedFieldLocatorImpl implements NamedFieldLocator {
    public <T> Collection<Field> locate(final Class<T> cls, final NamingConvention namingConvention) {
        final Collection<Field> fields = new ArrayList<Field>();
        for (final Field field : cls.getDeclaredFields()) {
            if (field.getName().matches(namingConvention.getPattern())) {
                fields.add(field);
            }
        }
        return fields;
    }
}