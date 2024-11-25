import React from "react";
import NavBarUser from "../../components/User/NavBarUser.tsx";
import FooterUser from "../../components/User/FooterUser.tsx";
import PostsData from "../../components/PostsData.tsx";



const UserPage = () => {
    return (
        <div className="app-container">
            <NavBarUser/>
            <div className="content">
                <PostsData/>
            </div>
            <FooterUser/>
        </div>
    );
};

export default UserPage;