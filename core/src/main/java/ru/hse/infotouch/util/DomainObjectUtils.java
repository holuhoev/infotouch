package ru.hse.infotouch.util;

import ru.hse.infotouch.domain.models.DomainObject;

import java.util.List;

public class DomainObjectUtils {

    public static int[] getIds(List<? extends DomainObject> domainObjectList) {

        return domainObjectList
                .stream()
                .map(DomainObject::getId)
                .mapToInt(Integer::intValue)
                .toArray();
    }
}
