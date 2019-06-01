import { complement, filter, find, isNil, pipe, prop, propEq } from "ramda";

export const excludeById = (id, list) => filter(complement(propEq('id', id)), list);

export const hasNoPoint = pipe(prop('pointId'), isNil);

export const findIdByPointId = (list, pointId) => {
    const found = find(propEq('pointId', pointId))(list);
    return found ? found.id : null;
};