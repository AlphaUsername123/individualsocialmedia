import React, {useState} from 'react';
import {useNavigate} from "react-router-dom";
import PostAPI from "../api/PostAPI.tsx";
import NavBarUser from "./User/NavBarUser.tsx";

const AddPost = () => {
    const [formData, setFormData] = useState({
        text: ''
    });

    const navigate = useNavigate();

    const handleFormSubmit = async (e) => {
        e.preventDefault();
        // Handle form submission with formData
        console.log(formData);
        await PostAPI.postThePost(formData);
        navigate("/userpage");
        window.location.reload();
    };

    return (
        <form onSubmit={handleFormSubmit}>
            <NavBarUser/>
            <label>
                Text:
                <input
                    type="text"
                    name="Text"
                    value={formData.text}
                    onChange={(e) => setFormData({...formData, text: e.target.value})}
                />
            </label>

            <button type="submit">Submit</button>
        </form>
    );
};
export default AddPost;
