import React from 'react';
import {useNavigate} from "react-router-dom";

const NotAuthorizedRolePage = () => {

    const navigate = useNavigate()
    const goBack = () => {
       // window.history.back();// Navigate back to the previous page in the history stack
        navigate(-2);
    };

    return (
        <div style={{textAlign: 'center', marginTop: '50px'}}>
            <h1>Access Denied</h1>
            <p>You do not have permission to view this page.</p>
            <button onClick={goBack} style={{padding: '10px 20px', fontSize: '16px', cursor: 'pointer'}}>
                Go Back
            </button>
        </div>
    );
};

export default NotAuthorizedRolePage;