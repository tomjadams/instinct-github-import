package com.googlecode.instinct.internal.aggregate;

import java.io.File;
import java.io.FileFilter;
import com.googlecode.instinct.core.annotate.BehaviourContext;
import static com.googlecode.instinct.internal.aggregate.BehaviourContextAggregatorSlowTest.EXPECTED_CONTEXTS;
import com.googlecode.instinct.internal.aggregate.locate.AnnotationFileFilter;
import com.googlecode.instinct.internal.aggregate.locate.ClassLocator;
import com.googlecode.instinct.internal.aggregate.locate.ClassLocatorImpl;
import com.googlecode.instinct.internal.util.JavaClassName;
import com.googlecode.instinct.test.InstinctTestCase;

public final class ClassLocatorSlowTest extends InstinctTestCase {
    private PackageRootFinder packageRootFinder;
    private ClassLocator locator;

    public void testFindsCorrectNumberOfContexts() {
        final FileFilter filter = new AnnotationFileFilter(getSpecPackageRoot(), BehaviourContext.class);
        final JavaClassName[] names = locator.locate(getSpecPackageRoot(), filter);
        assertEquals(EXPECTED_CONTEXTS, names.length);
    }

    private File getSpecPackageRoot() {
        return new File(packageRootFinder.getPackageRoot(ClassLocatorSlowTest.class));
    }

    @Override
    public void setUpSubject() {
        packageRootFinder = new PackageRootFinderImpl();
        locator = new ClassLocatorImpl();
    }
}