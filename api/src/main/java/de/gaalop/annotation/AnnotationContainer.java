package de.gaalop.annotation;

import java.util.WeakHashMap;

/**
 * This class models a container that is capable of annotating arbitrary objects with custom annotations.
 * <p/>
 * Only weak references to the annotated objects are held by this container to avoid creating memory leaks.
 *
 * @param <T> The class of the annotations contained in this object.
 */
public class AnnotationContainer<T> {

    private WeakHashMap<Object, T> annotationMap = new WeakHashMap<Object, T>();

    /**
     * Checks whether this container contains an annotation for an object.
     *
     * @param object The object that should be searched for.
     * @return True if this object contains an annotation for <code>object</code>.
     * @see java.util.Map#containsKey(Object)
     */
    public boolean isAnnotated(Object object) {
        return annotationMap.containsKey(object);
    }

    /**
     * Adds a new annotation to this container.
     *
     * @param object     The object that should be annotated.
     * @param annotation The annotation.
     * @see java.util.Map#put(Object, Object)
     */
    public void attachAnnotation(Object object, T annotation) {
        annotationMap.put(object, annotation);
    }

    /**
     * Gets the annotation of an object.
     *
     * @param object The annotated object.
     * @return Null if the object is unannotated, the annotation otherwise
     * @see java.util.Map#get(Object)
     */
    public T get(Object object) {
        return annotationMap.get(object);
    }

    /**
     * Removes the annotation of an object.
     *
     * @param object The annotated object.
     * @see java.util.Map#remove(Object)
     */
    public void removeAnnotation(Object object) {
        annotationMap.remove(object);
    }
}
