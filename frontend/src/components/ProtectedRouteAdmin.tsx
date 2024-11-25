import axios from 'axios';
import {Navigate} from 'react-router-dom';
import TokenManager from "../api/TokenManager.tsx";

// Define a function to check if the user is authenticated
const isNotAuthenticated = () => {
    // Implement your authentication logic here
    // For example, check if the user is logged in or if there's a token in local storage
    return localStorage.getItem('accessToken') === null;
};


axios.interceptors.response.use(response => response, error => {
    console.log(error.response.status);
    TokenManager.setAccessToken(localStorage.getItem('accessToken'));
    if(TokenManager.getClaims().roles != "ADMIN")
    {
        return window.location.assign('/noaccess');
    }
    if (error.response.status === 401) {
        localStorage.removeItem('accessToken'); // Clear the token
        // Optionally, add more cleanup actions here
    }

    window.location.assign('/');
    return Promise.reject(error);
});


// Define a ProtectedRoute component
const ProtectedRoute = ({children}) => {
    console.log('is authentif: ', isNotAuthenticated());
    if (isNotAuthenticated()) {
        return <Navigate to="/"/>
    }
    return children
};

export default ProtectedRoute;
