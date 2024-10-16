import '/src/pages/Login.css'
import LoginComponent from "../components/LoginComponent.tsx";

const LoginPage = () => {
    return (
        <div className="gradient-background">
            <h2>Login</h2>
            <LoginComponent/>
        </div>
    );
};

export default LoginPage;
