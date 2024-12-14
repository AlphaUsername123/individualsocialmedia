import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";
import PostAPI from "../api/PostAPI.tsx";
import NavBarUser from "./User/NavBarUser.tsx";
import TokenManager from "../api/TokenManager.tsx";

const AddPost = () => {
    const [formData, setFormData] = useState({
        text: '',
    });

    const navigate = useNavigate();
    const token = localStorage.getItem("accessToken");
    TokenManager.setAccessToken(token);

    const handleFormSubmit = async (e) => {
        e.preventDefault();
        const userId = TokenManager.getClaims().UserId; // Ensure `userId` is correctly fetched
        const createdAt = new Date().toISOString();

        const dataToSubmit = {
            ...formData,
            userId,
            createdAt,
        };

        console.log(dataToSubmit);

        try {
            await PostAPI.postThePost(dataToSubmit);
            navigate("/userpage");
        } catch (error) {
            console.error("Failed to submit post:", error);
        }
    };

    return (
        <form onSubmit={handleFormSubmit}>
            <NavBarUser />
            <label>
                Text:
                <input
                    type="text"
                    name="text"
                    value={formData.text}
                    onChange={(e) => setFormData({ ...formData, text: e.target.value })}
                />
            </label>

            <button type="submit">Submit</button>
        </form>
    );
};

export default AddPost;
