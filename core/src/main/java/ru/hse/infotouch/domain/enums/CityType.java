package ru.hse.infotouch.domain.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public enum CityType {
    MOSCOW,
    SAINT_PETERBURG,
    PERM,
    NIZHNIY_NOVGOROD,
    OTHER;

    private static final String MOSCOW_STR = "Москва";
    private static final String SAINT_PETERBURG_STR = "Санкт-Петербург";
    private static final String PERM_STR = "Пермь";
    private static final String NIZHNIY_NOVGOROD_STR = "Нижний Новгород";

    public static final String MOSCOW_SHORT_STR = "М ";
    public static final String SAINT_PETERBURG_SHORT_STR = "СПБ ";
    public static final String PERM_SHORT_STR = "П ";
    public static final String NIZHNIY_NOVGOROD_SHORT_STR = "НН ";


    public String getShortString() {
        return cityToShortStr.get(this);
    }

    public static CityType ofBuildingAddress(String address) {
        if (StringUtils.isEmpty(address))
            return OTHER;
        if (address.startsWith(MOSCOW_STR))
            return MOSCOW;
        if (address.startsWith(SAINT_PETERBURG_STR))
            return SAINT_PETERBURG;
        if (address.startsWith(PERM_STR))
            return PERM;
        if (address.startsWith(NIZHNIY_NOVGOROD_STR))
            return NIZHNIY_NOVGOROD;
        return OTHER;
    }

    public static CityType ofChairName(String chair) {
        if (StringUtils.isEmpty(chair))
            return OTHER;
        if (chair.startsWith(MOSCOW_SHORT_STR))
            return MOSCOW;
        if (chair.startsWith(SAINT_PETERBURG_SHORT_STR))
            return SAINT_PETERBURG;
        if (chair.startsWith(PERM_SHORT_STR))
            return PERM;
        if (chair.startsWith(NIZHNIY_NOVGOROD_SHORT_STR))
            return NIZHNIY_NOVGOROD;
        return OTHER;
    }

    private static Map<CityType, String> cityToShortStr = new HashMap<CityType, String>() {
        {
            put(MOSCOW, MOSCOW_SHORT_STR);
            put(SAINT_PETERBURG, SAINT_PETERBURG_SHORT_STR);
            put(PERM, PERM_SHORT_STR);
            put(NIZHNIY_NOVGOROD, NIZHNIY_NOVGOROD_SHORT_STR);
            put(OTHER, "");
        }
    };
}
