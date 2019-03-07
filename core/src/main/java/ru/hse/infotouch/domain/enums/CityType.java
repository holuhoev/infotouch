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

    public static final int MOSCOW_FROM_HSE_SITE = 22726;
    public static final int SAINT_PETERBURG_FROM_HSE_SITE = 135083;
    public static final int PERM_FROM_HSE_SITE = 135213;
    public static final int NIZHNIY_NOVGOROD_FROM_HSE_SITE = 135288;

    public String getShortString() {
        return cityToShortStr.get(this);
    }

    public Integer getSiteParam() {
        return cityToSite.get(this);
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

    public static CityType ofHseSite(int city) {
        return siteToCity.getOrDefault(city, OTHER);
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

    private static Map<CityType, Integer> cityToSite = new HashMap<CityType, Integer>() {
        {
            put(MOSCOW, MOSCOW_FROM_HSE_SITE);
            put(SAINT_PETERBURG, SAINT_PETERBURG_FROM_HSE_SITE);
            put(PERM, PERM_FROM_HSE_SITE);
            put(NIZHNIY_NOVGOROD, NIZHNIY_NOVGOROD_FROM_HSE_SITE);
        }
    };

    public static Map<Integer, CityType> siteToCity = new HashMap<Integer, CityType>() {
        {
            put(MOSCOW_FROM_HSE_SITE, MOSCOW);
            put(SAINT_PETERBURG_FROM_HSE_SITE, SAINT_PETERBURG);
            put(PERM_FROM_HSE_SITE, PERM);
            put(NIZHNIY_NOVGOROD_FROM_HSE_SITE, NIZHNIY_NOVGOROD);
        }
    };
}
