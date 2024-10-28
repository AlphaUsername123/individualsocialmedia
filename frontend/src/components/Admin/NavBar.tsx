import React from 'react';
import {useNavigate} from "react-router-dom";
import {Container, Navbar, Nav, NavDropdown, Button} from 'react-bootstrap';

const NavBar: React.FC = () => {

    const navigate = useNavigate();

    const handleLogout = async () => {
        localStorage.removeItem('accessToken');
        navigate("/");
    }

    return (
        <Navbar bg="light" expand="lg">
            <Container fluid>
                <Navbar.Brand href="/adminpage">Admin Dashboard</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link href="/adminpage">Home</Nav.Link>
                        <Nav.Link href="/websocket">Live Chat</Nav.Link>
                        <Nav.Link href="/productpost">Add Product</Nav.Link>
                        <Nav.Link className='header-right' href="/customerpage">Customer Page</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
                <button onClick={handleLogout} className='header-right'>logout</button>
            </Container>
        </Navbar>
    );
};

export default NavBar;