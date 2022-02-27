package service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonSerializer implements Serializer {

    private static final Class<JsonInclude> JSON_INCLUDE_ANNOTATION_CLASS = JsonInclude.class;

    private Map<String, Object> getAnnotatedFields(Object object) {
        Class<?> classToSerialize = object.getClass();
        var accessibleFields = getAccessibleFields(classToSerialize);
        return accessibleFields.stream()
                .filter(field -> field.isAnnotationPresent(JSON_INCLUDE_ANNOTATION_CLASS))
                .collect(Collectors.toMap(this::getPropertyName, mapValue -> getPropertyValue(mapValue, object)));
    }

    private List<Field> getAccessibleFields(Class<?> classToSerialize) {
        return Arrays.stream(classToSerialize.getDeclaredFields())
                .peek(field -> field.setAccessible(true))
                .collect(Collectors.toList());
    }

    private String getPropertyName(Field field) {
        var propertyName = field.getAnnotation(JSON_INCLUDE_ANNOTATION_CLASS).value();
        return propertyName.isEmpty() ? field.getName() : propertyName;
    }

    private Object getPropertyValue(Field field, Object valueType) {
        try {
            return field.get(valueType);
        } catch (IllegalAccessException e) {
            throw new InaccessibleFieldException(e.getCause());
        }
    }

    private String createJsonString(Map<String, Object> mappedProperties) {
        var properties = mappedProperties.entrySet().stream()
                .map(entry -> String.format("\"%s\": \"%s\"", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(", "));
        return String.format("{ %s }", properties);
    }

    public String serialize(Object object) {
        var annotatedFields = getAnnotatedFields(object);
        return createJsonString(annotatedFields);
    }
}
