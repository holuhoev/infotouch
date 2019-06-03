import axios from "axios";

const AUTH_INFO_KEY = 'authInfo';


axios.defaults.withCredentials = true;
axios.defaults.xsrfHeaderName  = 'X-CSRF-TOKEN';

axios.defaults.headers = {
    'X-Requested-With': 'XMLHttpRequest'
};


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
