const BASE_URL = 'http://localhost:8080';

class AuthService {
    static async login(username, password) {
        const url = `${BASE_URL}/login/auth`;

        try {
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    username,
                    password,
                }),
            });

            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            const data = await response.json();
            localStorage.setItem("accessToken", data.accessToken);
            return {
                success: true,
                token: data.accessToken,
                message: 'Login successful',
            };
        } catch (error) {
            console.error('Error during login:', error);
            return {
                success: false,
                token: null,
                message: 'An unexpected error occurred',
            };
        }
    }
}

export default AuthService;