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

import static java.lang.System.out;
import au.net.netstorm.boost.edge.java.lang.DefaultEdgeClass;
import com.googlecode.instinct.internal.util.Fix;
import com.googlecode.instinct.internal.util.Suggest;

@Fix({"Write atomic test for this.", "Delegate to TextContextRunner"})
public final class CommandLineRunner {
    public static final String METHOD_SEPARATOR = "#";

    private void run() {
        // run the contexts.
    }

    /**
     * Runs a single context or specification method sending the results to standard out.
     * The format of the argument is as follows:
     * <pre>
     * $ CommandLineRunner com.googlecode.instinct.example.stack.AnEmptyStack
     * $ CommandLineRunner com.googlecode.instinct.example.stackAnEmptyStack#mustBeEmpty
     * </pre>
     *
     * @param args The fully qualified class name of the context to run, with an optional
     */
    @Suggest("Move this implementation elewhere")
    public static void main(final String... args) {
        if (args.length == 0) {
            // print usage
            printUsage();
        } else {
            // create the runner, using a map to parse arguments.
            final Class<?> contextClass = getContextClass(args[0]);
            new CommandLineRunner().run();
        }
    }

    private static void printUsage() {
        final String className = CommandLineRunner.class.getSimpleName();
        out.println("Usage: " + className + " -format <brief|verbose> <context#spec>...");
    }

    private static Class<?> getContextClass(final String specificationToRun) {
        final String className = getClassName(specificationToRun);
        return new DefaultEdgeClass().forName(className);
    }

    private static String getClassName(final String specificationToRun) {
        final int index = specificationToRun.indexOf(METHOD_SEPARATOR);
        if (index >= 0) {
            return specificationToRun.substring(0, index);
//            methodName = specificationToRun.substring(index + 1);
        } else {
            return specificationToRun;
        }
    }
}
