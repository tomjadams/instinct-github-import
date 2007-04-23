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

package com.googlecode.instinct.integrate.idea;

import com.intellij.execution.CantRunException;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.ConfigurationPerRunnerSettings;
import com.intellij.execution.configurations.JavaCommandLineState;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.configurations.RunnerSettings;

public final class IntinctCommandLineState extends JavaCommandLineState {
    private static final String TEXT_RUNNER = "com.googlecode.instinct.runner.TextContextRunner";
    private InstinctRunConfiguration runConfiguration;

    public IntinctCommandLineState(final InstinctRunConfiguration runConfiguration, final RunnerSettings runner,
            final ConfigurationPerRunnerSettings configuration) {
        super(runner, configuration);
        this.runConfiguration = runConfiguration;
    }

    protected JavaParameters createJavaParameters() throws ExecutionException {
        final String contextClassName = runConfiguration.getContextClassName();
        final String specificationMethod = runConfiguration.getSpecificationMethodName();
        String behaviourLocator = contextClassName;
        if (specificationMethod.length() > 0) {
            behaviourLocator += ":" + specificationMethod;
        }
        return createParameters(behaviourLocator);
    }

    private JavaParameters createParameters(final String behaviourLocator) throws CantRunException {
        final JavaParameters parameters = new JavaParameters();
        parameters.setMainClass(TEXT_RUNNER);
        parameters.getProgramParametersList().addParametersString(behaviourLocator);
        parameters.configureByModule(runConfiguration.getModule(), JavaParameters.JDK_AND_CLASSES_AND_TESTS);
        parameters.setWorkingDirectory(runConfiguration.getWorkingDirectoryPath());
        return parameters;
    }
}
