import React, {useEffect, useState} from 'react';
import {useNavigate, useParams} from "react-router-dom";
import PostAPI from "../api/PostAPI.tsx";
import NavBarUser from "./User/NavBarUser.tsx";
import axios from "axios";
import TokenManager from "../api/TokenManager.tsx";

function PostUpdate() {
    const [formData, setFormData] = useState({
        id: '',
        text: '',
        userId: '',
        createdAt: '',
    });
    const [isModerator, setIsModerator] = useState(false); // default to false

    const {PostId} = useParams();
    const navigate = useNavigate();

    const token = localStorage.getItem("accessToken");
    TokenManager.setAccessToken(token);

    useEffect(() => {
        PostAPI.getPostById(PostId)
            .then((response) => {
                console.log('response', response);
                // Pre-fill the form fields with previous data
                const {text} = response;
                setFormData({
                    ...formData,
                    text,
                });
            })
            .catch((error) => {
                console.error('Error fetching data:', error);
            });
    }, [PostId]);

    const handleFormSubmit = async (e) => {
        e.preventDefault();
        try {
            formData.userId = TokenManager.getClaims().UserId; // Ensure `userId` is correctly fetched
            formData.createdAt = new Date().toISOString();

            formData.id = PostId;
            console.log(formData);
            await PostAPI.updatePost(formData);
            TokenManager.setAccessToken(localStorage.getItem('accessToken'));
            if (TokenManager.getClaims().roles != "MODERATOR") {
                setIsModerator(false);
            }
            if (isModerator == true) {
                navigate("/moderatorpage");
            }
            else if (!isModerator) {
                navigate("/userpage");
            }
            window.location.reload();
        } catch (error) {
            console.error('Error submitting form:', error);
        }
    };

    return (
        <div>
            <NavBarUser/>
            <form onSubmit={handleFormSubmit}>
                <label>
                    Text:
                    <input
                        type="text"
                        name="title"
                        value={formData.text}
                        onChange={(e) => setFormData({...formData, text: e.target.value})}
                    />
                </label>
                <button type="submit">Submit</button>
            </form>
        </div>
    );
}

export default PostUpdate;
