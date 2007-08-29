/*
 * Copyright 2006-2007 Chris Myers, Workingmouse
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

package com.googlecode.instinct.integrate.junit4;

import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.internal.util.ObjectFactoryImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import java.util.Collection;
import org.junit.runner.Description;
import static org.junit.runner.Description.createSuiteDescription;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

public final class InstinctRunner extends Runner {
    private final SpecificationMethodBuilder specificationMethodBuilder = new SpecificationMethodBuilderImpl();
    private final ObjectFactory objectFactory = new ObjectFactoryImpl();
    private final Class<?> classToRun;

    public InstinctRunner(final Class<?> classToRun) {
        checkNotNull(classToRun);
        this.classToRun = classToRun;
    }

    @Override
    public Description getDescription() {
        return createSuiteDescription(classToRun);
    }

    @Override
    public void run(final RunNotifier notifier) {
        checkNotNull(notifier);
        final Collection<SpecificationMethod> specificationMethods = specificationMethodBuilder.build(classToRun);
        final SpecificationRunner specificationRunner = objectFactory.create(SpecificationRunnerImpl.class, notifier);
        specificationRunner.run(specificationMethods);
    }
}