import React from 'react';
import {Container, Row, Col} from 'react-bootstrap';

const Footer: React.FC = () => {
    return (
        <footer className="mt-auto py-3 bg-light">
            <Container>
                <Row>
                    <Col className="text-center">© 2024 Moderator Dashboard</Col>
                </Row>
            </Container>
        </footer>
    );
};

export default Footer;
