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

package com.googlecode.instinct.runner;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.RunnableItem;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.locate.ContextWithSpecsWithDifferentAccessModifiers;
import com.googlecode.instinct.internal.runner.ASimpleContext;
import com.googlecode.instinct.internal.runner.RunnableItemBuilder;
import static com.googlecode.instinct.internal.runner.RunnableItemBuilder.ITEM_SEPARATOR;
import static com.googlecode.instinct.internal.runner.RunnableItemBuilder.METHOD_SEPARATOR;
import com.googlecode.instinct.internal.runner.RunnableItemBuilderImpl;
import com.googlecode.instinct.internal.util.instance.ClassInstantiator;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.actor.TestSubjectCreator.createSubject;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.checker.ExceptionTestChecker.expectException;
import fj.F;
import fj.data.List;
import org.jmock.Expectations;

@SuppressWarnings({"ALL"})
public final class RunnableItemBuilderImplAtomicTest extends InstinctTestCase {
    private static final String UNKNOWN_SPEC = "unknownMethod";
    private static final Class<?> CONTEXT_CLASS_1 = ASimpleContext.class;
    private static final Class<?> CONTEXT_CLASS_2 = ContextWithSpecsWithDifferentAccessModifiers.class;
    @Subject(auto = false) private RunnableItemBuilder runnableItemBuilder;
    @Mock private ClassInstantiator classInstantiator;

    @Override
    public void setUpSubject() {
        runnableItemBuilder = createSubject(RunnableItemBuilderImpl.class, classInstantiator);
    }

    public void testConformsToClassTraits() {
        checkClass(RunnableItemBuilderImpl.class, RunnableItemBuilder.class);
    }

    public void testContainsMethodSeparatorConstant() {
        expect.that(METHOD_SEPARATOR).isEqualTo("#");
    }

    public void testBuildsSingleContextNameIntoARunnableItem() {
        expect.that(new Expectations() {
            {
                atLeast(1).of(classInstantiator).instantiateClass(CONTEXT_CLASS_1.getName());
                will(returnValue(CONTEXT_CLASS_1));
            }
        });
        final List<RunnableItem> builtItems = runnableItemBuilder.build(CONTEXT_CLASS_1.getName());
        expect.that(builtItems).isOfSize(1);
        expect.that(builtItems).contains(new F<RunnableItem, Boolean>() {
            public Boolean f(RunnableItem item) {
                return ContextClass.class.isAssignableFrom(item.getClass()) && CONTEXT_CLASS_1.isAssignableFrom(((ContextClass) item).getType());
            }
        });
    }

    public void testBuildsASingleSpecificationMethodNameIntoARunnableItem() {
        expect.that(new Expectations() {
            {
                atLeast(1).of(classInstantiator).instantiateClass(CONTEXT_CLASS_1.getName());
                will(returnValue(CONTEXT_CLASS_1));
            }
        });
        final String specificationMethod = CONTEXT_CLASS_1.getName() + METHOD_SEPARATOR + "toCheckVerification";
        final List<RunnableItem> builtItems = runnableItemBuilder.build(specificationMethod);
        expect.that(builtItems).isOfSize(1);
        expect.that(builtItems).contains(new F<RunnableItem, Boolean>() {
            public Boolean f(RunnableItem item) {
                return SpecificationMethod.class.isAssignableFrom(item.getClass()) &&
                        ((SpecificationMethod) item).getName().equals("toCheckVerification");
            }
        });
    }

    public void testBuildsMultipleContextNamesIntoRunnableItems() {
        expect.that(new Expectations() {
            {
                atLeast(1).of(classInstantiator).instantiateClass(CONTEXT_CLASS_1.getName());
                will(returnValue(CONTEXT_CLASS_1));
                atLeast(1).of(classInstantiator).instantiateClass(CONTEXT_CLASS_2.getName());
                will(returnValue(CONTEXT_CLASS_2));
            }
        });
        final String itemsToRun = CONTEXT_CLASS_1.getName() + ITEM_SEPARATOR + CONTEXT_CLASS_2.getName();
        final List<RunnableItem> builtItems = runnableItemBuilder.build(itemsToRun);
        expect.that(builtItems).isOfSize(2);
        expect.that(builtItems).contains(new F<RunnableItem, Boolean>() {
            public Boolean f(RunnableItem item) {
                return ContextClass.class.isAssignableFrom(item.getClass()) && CONTEXT_CLASS_1.isAssignableFrom(((ContextClass) item).getType());
            }
        });
        expect.that(builtItems).contains(new F<RunnableItem, Boolean>() {
            public Boolean f(RunnableItem item) {
                return ContextClass.class.isAssignableFrom(item.getClass()) && CONTEXT_CLASS_2.isAssignableFrom(((ContextClass) item).getType());
            }
        });
    }

    public void testBuildsMultipleContextsAndSpecificationsIntoRunnableItems() {
        expect.that(new Expectations() {
            {
                atLeast(1).of(classInstantiator).instantiateClass(CONTEXT_CLASS_1.getName());
                will(returnValue(CONTEXT_CLASS_1));
                atLeast(1).of(classInstantiator).instantiateClass(CONTEXT_CLASS_2.getName());
                will(returnValue(CONTEXT_CLASS_2));
            }
        });
        final String specificationMethod = CONTEXT_CLASS_1.getName() + METHOD_SEPARATOR + "toCheckVerification";
        final String contextClass = CONTEXT_CLASS_2.getName();
        final List<RunnableItem> builtItems = runnableItemBuilder.build(specificationMethod + ITEM_SEPARATOR + contextClass);
        expect.that(builtItems).isOfSize(2);
        expect.that(builtItems).contains(new F<RunnableItem, Boolean>() {
            public Boolean f(RunnableItem item) {
                if (SpecificationMethod.class.isAssignableFrom(item.getClass())) {
                    return ((SpecificationMethod) item).getName().equals("toCheckVerification");
                } else {
                    return true;
                }
            }
        });
        expect.that(builtItems).contains(new F<RunnableItem, Boolean>() {
            public Boolean f(RunnableItem item) {
                return ContextClass.class.isAssignableFrom(item.getClass()) && CONTEXT_CLASS_2.isAssignableFrom(((ContextClass) item).getType());
            }
        });
    }

    public void testRejectsSpecsMarkedWithTwoMethods() {
        expectException(IllegalArgumentException.class, "Specification to run cannot contain more than one " + METHOD_SEPARATOR, new Runnable() {
            public void run() {
                runnableItemBuilder.build("ClassName" + METHOD_SEPARATOR + "specName" + METHOD_SEPARATOR + "anotherSpec");
            }
        });
    }

    public void testRejectsUnknownSpecs() {
        final String specToRun = CONTEXT_CLASS_1.getName() + METHOD_SEPARATOR + UNKNOWN_SPEC;
        expectException(IllegalArgumentException.class, "Specification method '" + specToRun + "' does not exist", new Runnable() {
            public void run() {
                expect.that(new Expectations() {
                    {
                        atLeast(1).of(classInstantiator).instantiateClass(CONTEXT_CLASS_1.getName());
                        will(returnValue(CONTEXT_CLASS_1));
                    }
                });
                runnableItemBuilder.build(specToRun);
            }
        });
    }
}
