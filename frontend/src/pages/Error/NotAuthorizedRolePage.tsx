import React from 'react';

const NotAuthorizedRolePage = () => {

    const goBack = () => {
       window.history.back();// Navigate back to the previous page in the history stack
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