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

import java.util.Collection;
import java.util.Iterator;
import static com.googlecode.instinct.expect.Expect.expect;
import static com.googlecode.instinct.expect.Mocker.eq;
import static com.googlecode.instinct.expect.Mocker.expects;
import static com.googlecode.instinct.expect.Mocker.mock;
import static com.googlecode.instinct.expect.Mocker.returnValue;
import com.googlecode.instinct.internal.aggregate.ContextWithSpecsWithDifferentAccessModifiers;
import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.RunnableItem;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.runner.ASimpleContext;
import com.googlecode.instinct.internal.runner.RunnableItemBuilder;
import static com.googlecode.instinct.internal.runner.RunnableItemBuilder.ITEM_SEPARATOR;
import static com.googlecode.instinct.internal.runner.RunnableItemBuilder.METHOD_SEPARATOR;
import com.googlecode.instinct.internal.runner.RunnableItemBuilderImpl;
import com.googlecode.instinct.internal.util.ClassInstantiator;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.AssertThrowsChecker.assertThrows;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.SubjectCreator.createSubject;

public final class RunnableItemBuilderImplAtomicTest extends InstinctTestCase {
    private static final String UNKNOWN_SPEC = "unknownMethod";
    private static final Class<?> CONTEXT_CLASS_1 = ASimpleContext.class;
    private static final Class<?> CONTEXT_CLASS_2 = ContextWithSpecsWithDifferentAccessModifiers.class;
    private RunnableItemBuilder runnableItemBuilder;
    private ClassInstantiator classInstantiator;

    @Override
    public void setUpTestDoubles() {
        classInstantiator = mock(ClassInstantiator.class);
    }

    @Override
    public void setUpSubject() {
        runnableItemBuilder = createSubject(RunnableItemBuilderImpl.class, classInstantiator);
    }

    public void testConformsToClassTraits() {
        checkClass(RunnableItemBuilderImpl.class, RunnableItemBuilder.class);
    }

    public void testContainsMethodSeparatorConstant() {
        expect.that(METHOD_SEPARATOR).equalTo("#");
    }

    public void testBuildsSingleContextNameIntoARunnableItem() {
        expects(classInstantiator).method("instantiateClass").with(eq(CONTEXT_CLASS_1.getName())).will(returnValue(CONTEXT_CLASS_1));
        final Collection<RunnableItem> builtItems = runnableItemBuilder.build(CONTEXT_CLASS_1.getName());
        expect.that(builtItems).hasSize(1);
        expectRunnableItemIsAContextClass(builtItems.iterator().next(), CONTEXT_CLASS_1);
    }

    public void testBuildsASingleSpecificationMethodNameIntoARunnableItem() {
        expects(classInstantiator).method("instantiateClass").with(eq(CONTEXT_CLASS_1.getName())).will(returnValue(CONTEXT_CLASS_1));
        final String specificationMethod = CONTEXT_CLASS_1.getName() + METHOD_SEPARATOR + "toCheckVerification";
        final Collection<RunnableItem> builtItems = runnableItemBuilder.build(specificationMethod);
        expect.that(builtItems).hasSize(1);
        expectRunnableItemIsASpecificationMethod(builtItems.iterator().next(), "toCheckVerification");
    }

    public void testBuildsMultipleContextNamesIntoRunnableItems() {
        expects(classInstantiator).method("instantiateClass").with(eq(CONTEXT_CLASS_1.getName())).will(returnValue(CONTEXT_CLASS_1));
        expects(classInstantiator).method("instantiateClass").with(eq(CONTEXT_CLASS_2.getName())).will(returnValue(CONTEXT_CLASS_2));
        final String itemsToRun = CONTEXT_CLASS_1.getName() + ITEM_SEPARATOR + CONTEXT_CLASS_2.getName();
        final Collection<RunnableItem> builtItems = runnableItemBuilder.build(itemsToRun);
        expectTwoContextsBuilt(builtItems);
    }

    public void testBuildsMultipleContextsAndSpecificationsIntoRunnableItems() {
        expects(classInstantiator).method("instantiateClass").with(eq(CONTEXT_CLASS_1.getName())).will(returnValue(CONTEXT_CLASS_1));
        expects(classInstantiator).method("instantiateClass").with(eq(CONTEXT_CLASS_2.getName())).will(returnValue(CONTEXT_CLASS_2));
        final String specificationMethod = CONTEXT_CLASS_1.getName() + METHOD_SEPARATOR + "toCheckVerification";
        final String contextClass = CONTEXT_CLASS_2.getName();
        final Collection<RunnableItem> builtItems = runnableItemBuilder.build(specificationMethod + ITEM_SEPARATOR + contextClass);
        expectAContextAndASpecificationMethodBuilt(builtItems);
    }

    public void testRejectsSpecsMarkedWithTwoMethods() {
        assertThrows(IllegalArgumentException.class, "Specifications to run cannot contain more than one " + METHOD_SEPARATOR, new Runnable() {
            public void run() {
                runnableItemBuilder.build("ClassName" + METHOD_SEPARATOR + "specName" + METHOD_SEPARATOR + "anotherSpec");
            }
        });
    }

    public void testRejectsUnknownSpecs() {
        final String specToRun = CONTEXT_CLASS_1.getName() + METHOD_SEPARATOR + UNKNOWN_SPEC;
        assertThrows(IllegalArgumentException.class, "Specification method '" + specToRun + "' does not exist", new Runnable() {
            public void run() {
                expects(classInstantiator).method("instantiateClass").with(eq(CONTEXT_CLASS_1.getName())).will(returnValue(CONTEXT_CLASS_1));
                runnableItemBuilder.build(specToRun);
            }
        });
    }

    private void expectTwoContextsBuilt(final Collection<RunnableItem> builtItems) {
        expect.that(builtItems).hasSize(2);
        final Iterator<RunnableItem> iterator = builtItems.iterator();
        expectRunnableItemIsAContextClass(iterator.next(), CONTEXT_CLASS_1);
        expectRunnableItemIsAContextClass(iterator.next(), CONTEXT_CLASS_2);
    }

    private void expectAContextAndASpecificationMethodBuilt(final Collection<RunnableItem> builtItems) {
        expect.that(builtItems).hasSize(2);
        final Iterator<RunnableItem> iterator = builtItems.iterator();
        expectRunnableItemIsASpecificationMethod(iterator.next(), "toCheckVerification");
        expectRunnableItemIsAContextClass(iterator.next(), CONTEXT_CLASS_2);
    }

    private <T> void expectRunnableItemIsAContextClass(final RunnableItem runnableItem, final Class<T> expectedWrappedClass) {
        expect.that(runnableItem.getClass()).typeCompatibleWith(ContextClass.class);
        expect.that(((ContextClass) runnableItem).getType()).typeCompatibleWith(expectedWrappedClass);
    }

    private void expectRunnableItemIsASpecificationMethod(final RunnableItem runnableItem, final String specificationMethod) {
        expect.that(runnableItem.getClass()).typeCompatibleWith(SpecificationMethod.class);
        expect.that(((SpecificationMethod) runnableItem).getName()).equalTo(specificationMethod);
    }
}