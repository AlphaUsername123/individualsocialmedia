// import axios from 'axios';
// import {Navigate} from 'react-router-dom';
// import TokenManager from "../api/TokenManager.tsx";
//
// // Define a function to check if the user is authenticated
// const isNotAuthenticated = () => {
//     // Implement your authentication logic here
//     // For example, check if the user is logged in or if there's a token in local storage
//     return localStorage.getItem('accessToken') === null;
// };
//
//
// axios.interceptors.response.use(response => response, error => {
//     console.log(error.response.status);
//     TokenManager.setAccessToken(localStorage.getItem('accessToken'));
//     if(TokenManager.getClaims().roles != "ADMIN")
//     {
//         return window.location.assign('/noaccess');
//     }
//     if (error.response.status === 401) {
//         localStorage.removeItem('accessToken'); // Clear the token
//         // Optionally, add more cleanup actions here
//     }
//
//     window.location.assign('/');
//     return Promise.reject(error);
// });
//
// const ProtectedRoute = ({children}) => {
//     console.log('is not authentif: ', isNotAuthenticated());
//     if (isNotAuthenticated()) {
//         return <Navigate to="/"/>
//         // } else if (isAdmin != true) {
//         //     return <Navigate to="/noaccess"/>
//         // }
//         return children
//
//     }
// };
// export default ProtectedRoute;
import {Navigate} from 'react-router-dom';
import TokenManager from "../api/TokenManager.tsx";


const isNotAuthenticated = () => {

    return localStorage.getItem('accessToken') === null;
};

const ProtectedRoute = ({children}) => {
    console.log('is authentif: ', isNotAuthenticated());
    TokenManager.setAccessToken(localStorage.getItem('accessToken'));
    console.log(TokenManager.getClaims().roles);
     if(TokenManager.getClaims().roles != "ADMIN")
    {
        return window.location.assign('/noaccess');
    }
    if (isNotAuthenticated()) {
        return <Navigate to="/"/>
    }
    return children
};

export default ProtectedRoute;
