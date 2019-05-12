const PARAMS = {
    ID: ':id'
};

export const ROUTE = {
    CORE: '/',
    MAP:  '/map'
};

export const getMapRoute = buildingId => ROUTE.MAP.replace(PARAMS.ID, buildingId);
