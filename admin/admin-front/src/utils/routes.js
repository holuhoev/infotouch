const PARAMS = {
    ID: ':id'
};

export const ROUTE = {
    CORE:    '/',
    MAP:     '/map',
    DEVICE:  '/device',
    SERVICE: '/service',
    UNIT:    '/unit',
    EVENT:   '/event'
};

export const getMapRoute = buildingId => ROUTE.MAP.replace(PARAMS.ID, buildingId);
