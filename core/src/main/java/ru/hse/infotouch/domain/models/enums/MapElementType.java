package ru.hse.infotouch.domain.models.enums;

public enum MapElementType {
    CORRIDOR, ROOM, DOOR, STAIRS;

    public static MapElementType ofString(String label) {
        switch (label) {
            case "corridor":
                return CORRIDOR;
            case "door":
                return DOOR;
            case "stairs":
                return STAIRS;
            default:
                return ROOM;
        }
    }
}
