import axios from 'axios';

const PostAPI = {
    getPosts: async () => {
        try {
            // Assuming your token retrieval is asynchronous (e.g., from an API call)
            const token = await retrieveAccessToken();
            if (!token) {
                console.error('Access token not available');
                // Handle the case where the token is not available (e.g., redirect to login)
                return null; // or throw an error, or handle as appropriate
            }

            const response = await axios.get(`http://localhost:8080/posts/getall`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });

            return response.data;
        } catch (error) {
            console.error('Error fetching product data:', error);
            throw error; // Propagate the error for further handling
        }
    },

    deletePost: async (postId) => {
        try {
            // Assuming your token retrieval is asynchronous (e.g., from an API call)
            const token = await retrieveAccessToken();
            if (!token) {
                console.error('Access token not available');
                // Handle the case where the token is not available (e.g., redirect to login)
                return null; // or throw an error, or handle as appropriate
            }

            const response = await axios.delete(`http://localhost:8080/posts/${postId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });

            return response.data;
        } catch (error) {
            console.error('Error fetching product data:', error);
            throw error; // Propagate the error for further handling
        }
    },

    getPostById: async (postId) => {
        try {
            // Assuming your token retrieval is asynchronous (e.g., from an API call)
            const token = await retrieveAccessToken();
            if (!token) {
                console.error('Access token not available');
                // Handle the case where the token is not available (e.g., redirect to login)
                return null; // or throw an error, or handle as appropriate
            }

            const response = await axios.get(`http://localhost:8080/products/${postId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error fetching product data:', error);
            throw error; // Propagate the error for further handling
        }
    },

    postThePost: async (post) => {
        try {
            // Assuming your token retrieval is asynchronous (e.g., from an API call)
            const token = await retrieveAccessToken();
            if (!token) {
                console.error('Access token not available');
                // Handle the case where the token is not available (e.g., redirect to login)
                return null; // or throw an error, or handle as appropriate
            }

            const response = await axios.post(`http://localhost:8080/posts`, post,{
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error fetching product data:', error);
            throw error; // Propagate the error for further handling
        }
    },

    updatePost: async (post) => {
        try {
            // Assuming your token retrieval is asynchronous (e.g., from an API call)
            const token = await retrieveAccessToken();
            if (!token) {
                console.error('Access token not available');
                // Handle the case where the token is not available (e.g., redirect to login)
                return null; // or throw an error, or handle as appropriate
            }

            const response = await axios.put(`http://localhost:8080/posts/${post.id}`, post, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error fetching product data:', error);
            throw error; // Propagate the error for further handling
        }
    }
}

export default PostAPI;

async function retrieveAccessToken() {
    return localStorage.getItem('accessToken');
}