import {useState} from 'react';
import {useNavigate} from 'react-router-dom';
import TokenManager from "../api/TokenManager.tsx";
import AuthAPI from "../api/AuthServiceAPI.tsx";

const LoginComponent = () => {
    const navigate = useNavigate();
    const [state, setState] = useState({
        username: '',
        password: '',
        token: null,
        error: '',
    });

    const handleInputChange = (event) => {
        setState({...state, [event.target.name]: event.target.value});
    };

    const handleLogin = async () => {
        try {
            setState({...state, error: ''}); // Clear previous error messages
            const {username, password} = state;
            const {success, token, message} = await AuthAPI.login(username, password);
            TokenManager.setAccessToken(token);
            if (success) {
                setState({...state, token});
                // window.location.assign('http://localhost:5173/lol');
                if(TokenManager.getClaims().roles == "MODERATOR") {
                    navigate('/moderatorpage');
                }
                else
                {
                    navigate('/userpage');
                }
                // Other logic after successful login
            } else {
                setState({...state, error: message || 'Login failed'});
                console.error('Login failed:', message);
            }
        } catch (error) {
            setState({...state, error: 'An unexpected error occurred'});
            console.error('Unexpected error during login:', error);
        }
    };

    const {username, password, error} = state;

    return (
        <div className="container-fluid d-flex justify-content-center align-items-center gradient-background">
            <div className="col-md-4 rounded p-4">
                <div className="form-group">
                    <label htmlFor="username">Username:</label>
                    <input type="text" className="form-control" name="username" value={username}
                           onChange={handleInputChange}/>
                </div>
                <div className="form-group">
                    <label htmlFor="password">Password:</label>
                    <input type="password" className="form-control" name="password" value={password}
                           onChange={handleInputChange}/>
                </div>
                <div className="text-center"> {/* Centering wrapper */}
                    <button type="button" className="btn btn-primary " onClick={handleLogin}>Login</button>
                    {error && <p className="text-danger mt-2">{error}</p>}
                </div>
            </div>
        </div>
    );
};

export default LoginComponent;
