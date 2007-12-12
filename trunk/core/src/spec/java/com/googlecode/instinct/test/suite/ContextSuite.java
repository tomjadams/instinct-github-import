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

package com.googlecode.instinct.test.suite;

import com.googlecode.instinct.defect.defect23.AFixedDefect23;
import com.googlecode.instinct.defect.defect3.AFixedDefect3;
import com.googlecode.instinct.defect.defect8.AFixedDefect8WithANamingConventionLocator;
import com.googlecode.instinct.defect.defect8.AFixedDefect8WithAnAnnotationMethodLocator;
import com.googlecode.instinct.internal.locate.AHierarchicalMethodLocatorContext;
import com.googlecode.instinct.internal.locate.AnAnnotatedMethodLocatorContext;
import com.googlecode.instinct.api.CommonAPIContext;
import com.googlecode.instinct.api.AnObjectCheckerContext;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@SuppressWarnings({"ClassMayBeInterface"})
@RunWith(Suite.class)
@Suite.SuiteClasses({AnAnnotatedMethodLocatorContext.class,
                     AHierarchicalMethodLocatorContext.class,
                     AFixedDefect8WithAnAnnotationMethodLocator.class,
                     AFixedDefect8WithANamingConventionLocator.class,
                     AFixedDefect23.class,
                     AFixedDefect3.class,
                     CommonAPIContext.class,
                     AnObjectCheckerContext.class})
public final class ContextSuite {
    //Suite classs.
}