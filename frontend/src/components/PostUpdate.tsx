import React, {useEffect, useState} from 'react';
import {useNavigate, useParams} from "react-router-dom";
import PostAPI from "../api/PostAPI.tsx";
import NavBarUser from "./User/NavBarUser.tsx";
import axios from "axios";
import TokenManager from "../api/TokenManager.tsx";

function PostUpdate() {
    const [formData, setFormData] = useState({
        id: '',
        text: ''
    });
    const [isAdmin, setIsAdmin] = useState(false); // default to false

    const {PostId} = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        PostAPI.getPostById(PostId)
            .then((response) => {
                console.log('response', response);
                // Pre-fill the form fields with previous data
                const {text} = response;
                setFormData({
                    ...formData,
                    text
                });
            })
            .catch((error) => {
                console.error('Error fetching data:', error);
            });
    }, [PostId]);

    const handleFormSubmit = async (e) => {
        e.preventDefault();
        try {
            formData.id = PostId;
            await PostAPI.updatePost(formData);
            TokenManager.setAccessToken(localStorage.getItem('accessToken'));
            if (TokenManager.getClaims().roles != "ADMIN") {
                setIsAdmin(false);
            }
            if (isAdmin == true) {
                navigate("/adminpage");
            }
            else if (isAdmin != true) {
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
