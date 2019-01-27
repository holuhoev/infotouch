package ru.hse.infotouch.core.ruz.util;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hse.infotouch.core.ruz.converter.AttributeConverter;
import ru.hse.infotouch.core.ruz.converter.Convert;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * @author Evgeny Kholukhoev
 */
public class JsonParser {
    private final Logger logger = LoggerFactory.getLogger(JsonParser.class);

    public <T> List<T> parse(String str, Class<T> clazz) {
        if (str == null)
            return null;
        JsonArray jsonArray = new JsonArray(str);
        List<T> list = new LinkedList<>();

        try {
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.getJsonObject(i);
                T object = clazz.newInstance();

                // TODO: Оптимизировать, взять вначале fields
                for (Field field : clazz.getDeclaredFields()) {
                    JsonAttribute jsonAttributeAnnotation = field.getAnnotation(JsonAttribute.class);
                    if (jsonAttributeAnnotation != null) {
                        field.setAccessible(true);
                        Object value = jsonObject.getMap().get(isNullOrEmpty(jsonAttributeAnnotation.name()) ? field.getName() : jsonAttributeAnnotation.name());
                        if (field.getAnnotation(Convert.class) != null) {
                            Object converterInstance = field.getAnnotation(Convert.class).converter().newInstance();
                            if (converterInstance instanceof AttributeConverter) {
                                value = ((AttributeConverter) converterInstance).convertToEntityAttribute(value);
                            }
                        }
                        field.set(object, value);
                    }
                }

                list.add(object);
            }
            return list;
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    private JsonParser() {
    }

    private static JsonParser instance;

    public static JsonParser getInstance() {
        if (instance == null) {
            instance = new JsonParser();
        }
        return instance;
    }
}
