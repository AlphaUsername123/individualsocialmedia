import axios from 'axios';

const BASE_URL = 'http://localhost:8080';

const AuthAPI = {
    login: async (username, password) => {
        const url = `${BASE_URL}/login/auth`;
        try {
            const response = await axios.post(url, {
                username,
                password
            }, {
                headers: {
                    'Content-Type': 'application/json',
                }
            });
           console.log('response', response);
            const data = response.data;
            localStorage.setItem('accessToken', data.accessToken);

            return {
                success: true,
                token: data.accessToken,
                message: 'Login successful',
            };
        } catch (error) {
            console.error('Error during login:', error);

            const message = error.response?.data?.message || 'An unexpected error occurred';
            return {
                success: false,
                token: null,
                message,
            };
        }
    },

    retrieveAccessToken: async () => {
        return localStorage.getItem('accessToken');
    }
};

export default AuthAPI;