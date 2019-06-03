import axios from 'axios';
import { setAuthInfo, clearAuthInfo } from "../utils/axios";

export const login = (login, password) => {
    const authUser = {
        username: login,
        password: password,
    };

    clearAuthInfo();

    return axios.get('/login', {
        auth: authUser
    }).then(response => {
        setAuthInfo(response.data);
        return response;
    }).catch(response => {
        console.error('GET /login', response);
        return Promise.reject(response);
    });
};

export const logout = () => {
    return axios
        .get('/logout')
        .then(() => {
            clearAuthInfo();
        })
        .catch(response => {
            console.error('GET /logout', response);
        });
};
