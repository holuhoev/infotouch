import { complement, filter, propEq } from "ramda";

export const excludeById = (id, list) => filter(complement(propEq('id', id)), list);