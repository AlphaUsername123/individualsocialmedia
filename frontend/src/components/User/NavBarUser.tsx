import React from 'react';
import {Container, Navbar, Nav, NavDropdown} from 'react-bootstrap';
import {useNavigate} from "react-router-dom";

const NavBarUser: React.FC = () => {

    const navigate = useNavigate();

    const handleLogout = async () => {
        localStorage.removeItem('accessToken');
        navigate("/");
    }

    return (
        <Navbar bg="light" expand="lg">
            <Container fluid>
                <Navbar.Brand href="/userpage">User Page</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link href="/userpage">Home</Nav.Link>
                        <Nav.Link href="/websocket">Live Chat</Nav.Link>
                        <Nav.Link href="/addpost">Add Post</Nav.Link>
                        <Nav.Link href="/myposts">My Posts</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
                <button onClick={handleLogout} className='header-right'>logout</button>
            </Container>
        </Navbar>
    );
};

export default NavBarUser;