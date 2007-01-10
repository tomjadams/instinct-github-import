package com.googlecode.instinct.internal.aggregate.locate;

import java.io.File;
import java.lang.annotation.Annotation;
import au.net.netstorm.boost.edge.java.lang.DefaultEdgeClass;
import au.net.netstorm.boost.edge.java.lang.EdgeClass;
import com.googlecode.instinct.internal.util.JavaClassNameImpl;
import com.googlecode.instinct.internal.util.JavaClassName;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

final class AnnotationCheckerImpl implements AnnotationChecker {
    private final EdgeClass edgeClass = new DefaultEdgeClass();
    private final File packageRoot;

    AnnotationCheckerImpl(final File packageRoot) {
        checkNotNull(packageRoot);
        this.packageRoot = packageRoot;
    }

    public <T extends Annotation> boolean isAnnotated(final File classFile, final Class<T> annotationType) {
        checkNotNull(classFile, annotationType);
        final Class<T> candidateClass = instantiateClass(classFile);
        return isAnnotated(candidateClass, annotationType);
    }

    private <T extends Annotation> boolean isAnnotated(final Class<T> candidateClass, final Class<T> annotationType) {
        return candidateClass.getAnnotation(annotationType) != null;
    }

    @SuppressWarnings({"unchecked"})
    private <T> Class<T> instantiateClass(final File classFile) {
        final JavaClassName className = new JavaClassNameImpl(packageRoot, classFile);
        return (Class<T>) edgeClass.forName(className.getFullyQualifiedName());
    }
}
