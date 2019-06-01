const PARAMS = {
    ID: ':id'
};

export const ROUTE = {
    CORE:    '/',
    MAP:     '/map',
    DEVICE:  '/device',
    SERVICE: '/service',
    UNIT:    '/unit',
    EVENT:   '/event',
    ANNOUNCEMENT:   '/announcement'
};

export const getMapRoute = buildingId => ROUTE.MAP.replace(PARAMS.ID, buildingId);
