import React from 'react';
import NavBar from "../../components/Admin/NavBar.tsx";
import Footer from "../../components/Admin/Footer.tsx";
import '../CSS/ViewPage.css';
import PostsData from "../../components/PostsData.tsx";
import PostsDataAdmin from "../../components/PostsDataAdmin.tsx";

const ViewPage = () => {
    return (
        <div className="app-container">
            <NavBar/>
            <div className="content">
                <PostsDataAdmin/>
            </div>
            <Footer/>
        </div>
    );
}

export default ViewPage;