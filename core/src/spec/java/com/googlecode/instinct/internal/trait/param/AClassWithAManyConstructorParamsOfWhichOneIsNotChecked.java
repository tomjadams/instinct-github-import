/*
 * Copyright 2008 Jeremy Mawson
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

package com.googlecode.instinct.internal.trait.param;

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

/**
 * Used in the AParameterTraitCheckerImpl context. These classes could not be nested in the context, as inner classes take the container class as a
 * constructor parameter, and the context needs to make assertions related to the constructor params.
 */
@SuppressWarnings({"UnusedDeclaration"})
public final class AClassWithAManyConstructorParamsOfWhichOneIsNotChecked {
    public AClassWithAManyConstructorParamsOfWhichOneIsNotChecked(final Object obj, final String str, final Boolean bool) {
        checkNotNull(obj, str);
    }

    public void instanceMethod() {
    }
}