const PARAMS = {
    ID: ':id'
};

export const ROUTE = {
    CORE:    '/',
    MAP:     '/map',
    DEVICE:  '/device',
    SERVICE: '/service',
};

export const getMapRoute = buildingId => ROUTE.MAP.replace(PARAMS.ID, buildingId);
