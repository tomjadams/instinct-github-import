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

package com.googlecode.instinct.internal.actor;

import com.googlecode.instinct.internal.util.Suggest;
import java.util.List;
import java.util.Map;

@Suggest("Test double: Merge this with stub creator.")
public interface TestTriangulationProvider {
    <T> T getInstance(final Class<T> type);

    Object[] getInstances(Class<?>[] types);

    <T> List<T> getListInstance(Class<T> elementType);

    <K, V> Map<K, V> getMapInstance(Class<K> keyType, Class<V> valueType);

    <T> T[] getArrayInstance(Class<T> elementType);

    int intInRange(int min, int max);
}
