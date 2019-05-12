export const context = process.env.REACT_APP_CONTEXT;

const SCHEME_ID_PARAM = ':schemeId';

export const GET_BUILDING_MAP = '/building/scheme';

export const POST_CREATE_POINT = `/building/scheme/points/create`;

// export const getPostCreatePointUrl = buildingSchemeId => POST_CREATE_POINT.replace(SCHEME_ID_PARAM, buildingSchemeId);