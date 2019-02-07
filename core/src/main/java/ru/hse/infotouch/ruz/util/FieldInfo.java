package ru.hse.infotouch.ruz.util;

import ru.hse.infotouch.ruz.converter.AttributeConverter;

public class FieldInfo {
    private String jsonFieldName;
    private AttributeConverter attributeConverter;

    public FieldInfo(String jsonFieldName, AttributeConverter attributeConverter) {
        this.jsonFieldName = jsonFieldName;
        this.attributeConverter = attributeConverter;
    }

    public FieldInfo() {
    }

    public FieldInfo(String jsonFieldName) {
        this.jsonFieldName = jsonFieldName;
    }

    public String getJsonFieldName() {
        return jsonFieldName;
    }

    public void setJsonFieldName(String jsonFieldName) {
        this.jsonFieldName = jsonFieldName;
    }

    public AttributeConverter getAttributeConverter() {
        return attributeConverter;
    }

    public void setAttributeConverter(AttributeConverter attributeConverter) {
        this.attributeConverter = attributeConverter;
    }
}
