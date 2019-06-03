import axios from "axios";

const AUTH_INFO_KEY = 'authInfo';


axios.defaults.withCredentials = true;
axios.defaults.xsrfHeaderName  = 'X-CSRF-TOKEN';

axios.defaults.headers = {
    'X-Requested-With': 'XMLHttpRequest'
};

axios.interceptors.response.use((response) => {
    return response;
}, (err) => {
    // Поле response === undefined если выполняется кросс-доменный запрос и в ответе не соблюдены условия политики CORS.
    if (err.response === undefined) {
        clearAuthInfo();
    }
    if (err.response.status === 401) {
        clearAuthInfo();
    }
    if (err.response.status === 423) {
        clearAuthInfo();
    }
    if (err.response.status === 403) {
        clearAuthInfo();
    }
    return Promise.reject(err);
});

export const setAuthInfo = (data) => {
    localStorage.setItem(AUTH_INFO_KEY, JSON.stringify(data));
};

const getAuthInfo = () => {
    return JSON.parse(localStorage.getItem(AUTH_INFO_KEY));
};

export const clearAuthInfo = () => {
    localStorage.clear();
};

export const isLoggedIn = () => {
    return !!getAuthInfo();
};
