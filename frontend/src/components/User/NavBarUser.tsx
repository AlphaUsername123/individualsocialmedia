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
                <Navbar.Brand href="/customerpage">Customer Page</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link href="/customerpage">Home</Nav.Link>
                        <Nav.Link href="/websocket">Live Chat</Nav.Link>
                        <NavDropdown title="Dropdown" id="basic-nav-dropdown">
                            <NavDropdown.Item href="#action/3.1">Action</NavDropdown.Item>
                            <NavDropdown.Item href="#action/3.2">Another action</NavDropdown.Item>
                            <NavDropdown.Divider/>
                            <NavDropdown.Item href="#action/3.3">Something else here</NavDropdown.Item>
                        </NavDropdown>
                    </Nav>
                </Navbar.Collapse>
                <button onClick={handleLogout} className='header-right'>logout</button>
            </Container>
        </Navbar>
    );
};

export default NavBarUser;