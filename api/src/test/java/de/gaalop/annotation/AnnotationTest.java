package de.gaalop.annotation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import org.junit.Test;

/**
 * This test case tests the base annotation class.
 */
public class AnnotationTest {

    private Object testObject = new Object();

    @Test
    public void testAnnotationContainer() {
        String testAnnotation = "Something";

        AnnotationContainer<String> annotations = new AnnotationContainer<String>();
        assertFalse(annotations.isAnnotated(testObject));

        annotations.attachAnnotation(testObject, testAnnotation);

        assertSame(testAnnotation, annotations.get(testObject));
    }

}
