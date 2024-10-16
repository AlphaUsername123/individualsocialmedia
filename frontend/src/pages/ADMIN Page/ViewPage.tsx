import React from 'react';
import NavBar from "../../components/Admin/NavBar.tsx";
import Footer from "../../components/Admin/Footer.tsx";
import '../CSS/ViewPage.css';
import ProductsData from "./CRUD Products/ProductsData.tsx";
import {Button} from "react-bootstrap";

const ViewPage = () => {
    return (
        <div className="app-container">
            <NavBar/>
            <div className="content">
                <ProductsData/>
                <Button href={"/customerpage"}>Customer Page</Button>
            </div>
            <Footer/>
        </div>
    );
}

export default ViewPage;