/**
 * Фабрика функций, которые создают экшены (объект).
 * @param { string } type - тип экшена;
 * @returns {function(*): {type: string, payload: *}}
 */
export const createAction = type => payload => ({ type, payload });