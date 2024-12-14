import React from 'react';
import NavBar from "../../components/Moderator/NavBar.tsx";
import Footer from "../../components/Moderator/Footer.tsx";
import '../CSS/ViewPage.css';
import PostsDataModerator from "../../components/PostsDataModerator.tsx";

const ViewPage = () => {
    return (
        <div className="app-container">
            <NavBar/>
            <div className="content">
                <PostsDataModerator/>
            </div>
            <Footer/>
        </div>
    );
}

export default ViewPage;