package io.snw.entityapi.reflection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This is a WIP class, it will come in handy for some stuff.
 */
public class Modifier<T> {

    protected ClassTemplate<T> targetTemplate;
    protected Object target;

    public Modifier(Object object) {
        this(ClassTemplate.create(object.getClass()), object);
    }

    public Modifier(Class<?> clazz) {
        this(ClassTemplate.create(clazz), null);
    }

    public Modifier(ClassTemplate template, Object object) {
        this.targetTemplate = template;
        this.target = object;
    }

    public <T> Modifier<T> withType(Class type) {
        List<SafeField<?>> layer = new ArrayList<SafeField<?>>();
        HashMap<SafeField<?>, Integer> indexer = new HashMap<SafeField<?>, Integer>();

        int index = 0;

        for(SafeField field : this.targetTemplate.getFields()) {
            if(type != null && type.getClass().isAssignableFrom(field.getType().getType())) {
                layer.add(field);

                if(indexer.containsKey(field))
                    indexer.put(field, index);
            }
            index++;
        }
        return null;
    }
}
