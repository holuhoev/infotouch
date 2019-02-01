package ru.hse.infotouch.core.ruz.util;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hse.infotouch.core.ruz.converter.AttributeConverter;
import ru.hse.infotouch.core.ruz.converter.RuzConvert;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * @author Evgeny Kholukhoev
 */
public class JsonParser {
    private final Logger logger = LoggerFactory.getLogger(JsonParser.class);

    private JsonParser() {
    }

    private static JsonParser instance;

    public static JsonParser getInstance() {
        if (instance == null) {
            instance = new JsonParser();
        }
        return instance;
    }


    public <T> List<T> mapStringToList(String str, Class<T> clazz) {
        if (str == null) {
            return new ArrayList<>();
        }

        JsonArray jsonArray = new JsonArray(str);

        return mapJsonArrayToList(jsonArray, clazz);
    }

    private <T> List<T> mapJsonArrayToList(JsonArray jsonArray, Class<T> clazz) {
        List<T> list = new ArrayList<>(jsonArray.size());
        Map<Field, FieldInfo> fieldInfos = getFieldInfos(clazz);

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject json = jsonArray.getJsonObject(i);

            try {
                T object = mapJsonToObject(json, fieldInfos, clazz);
                list.add(object);
            } catch (InstantiationException | IllegalAccessException e) {
                logger.error(e.getMessage());
            }

        }

        return list;
    }

    private <T> T mapJsonToObject(JsonObject json, Map<Field, FieldInfo> fieldInfos, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        T object = clazz.newInstance();

        fieldInfos.forEach((field, fieldInfo) -> {
            field.setAccessible(true);
            Object value = json.getMap().get(fieldInfo.getJsonFieldName());

            if (fieldInfo.getAttributeConverter() != null) {
                value = fieldInfo.getAttributeConverter().convertToEntityAttribute(value);
            }

            try {
                field.set(object, value);
            } catch (IllegalAccessException e) {
                logger.error("Could not set value on field: " + field.getName());
            }
        });

        return object;
    }

    private <T> Map<Field, FieldInfo> getFieldInfos(Class<T> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(this::isFieldCanBeMapped)
                .collect(Collectors.toMap(Function.identity(), this::getFieldInfo));
    }


    private FieldInfo getFieldInfo(Field f) {
        FieldInfo fieldInfo = new FieldInfo();

        fieldInfo.setJsonFieldName(getJsonFieldName(f));

        if (f.getAnnotation(RuzConvert.class) != null) {
            try {
                Object converterInstance = f.getAnnotation(RuzConvert.class).converter().newInstance();

                if (converterInstance instanceof AttributeConverter) {
                    fieldInfo.setAttributeConverter((AttributeConverter) converterInstance);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                logger.error("Could not read RuzConvert annotation on field: " + f.getName());
            }
        }

        return fieldInfo;
    }


    private String getJsonFieldName(Field field) {
        return isNullOrEmpty(field.getAnnotation(JsonField.class).name()) ? field.getName() : field.getAnnotation(JsonField.class).name();
    }

    private boolean isFieldCanBeMapped(Field field) {
        return field.getAnnotation(JsonField.class) != null;
    }
}
