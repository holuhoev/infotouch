package ru.hse.infotouch.domain.models.enums;

public enum EducationType {
    BACHELOR, MASTER, OTHER;

    public static EducationType of(int type) {
        switch (type) {
            case -1:
            case 2:
                return OTHER;
            case 0:
                return BACHELOR;
            case 1:
                return MASTER;
            default:
                throw new IllegalArgumentException("Invalid value for EducationType:" + type);
        }
    }
}
