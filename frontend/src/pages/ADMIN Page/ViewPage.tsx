import React from 'react';
import NavBar from "../../components/Admin/NavBar.tsx";
import Footer from "../../components/Admin/Footer.tsx";
import '../CSS/ViewPage.css';
import {Button} from "react-bootstrap";

const ViewPage = () => {
    return (
        <div className="app-container">
            <NavBar/>
            <div className="content">
                {/*<ProductsData/>*/}
            </div>
            <Footer/>
        </div>
    );
}

export default ViewPage;