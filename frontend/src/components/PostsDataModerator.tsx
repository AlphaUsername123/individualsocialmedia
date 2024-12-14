import React, {useEffect, useState} from 'react';
import {Card, Button} from 'react-bootstrap';
import PropTypes from 'prop-types';
import PostAPI from "../api/PostAPI.tsx";

function PostsDataModerator() {
    const [data, setData] = useState([]);

    useEffect(() => {
        PostAPI.getPosts()
            .then((response) => {
                setData(response.posts);
            })
            .catch((error) => {
                console.error('Error fetching data:', error);
            });
    }, []);

    const handleDeletePost = async (PostId) =>
    {
        await PostAPI.deletePost(PostId);
        alert(`Successfull was deleted Post with id: ${PostId}`);
        window.location.reload();
    };


    return (
        <div>
            {data.map((post, index) => (
                <PostCard key={index} post={post} onDeletePostDetails={handleDeletePost} />))}
        </div>
    );
}

function PostCard({post, onDeletePostDetails}) {
    const {id, text} = post;

    return (
        <Card style={{width: '18rem', marginBottom: '20px'}}>
            <Card.Body>
                <Card.Title>Post Information</Card.Title>
                <Card.Text>
                    <strong>Text:</strong> {text}
                </Card.Text>
                <Button variant="primary" onClick={() => onDeletePostDetails(id)}>
                    Delete
                </Button>
            </Card.Body>
        </Card>
    );
}

PostCard.propTypes = {
    post: PropTypes.shape({
        id: PropTypes.number.isRequired,
        post: PropTypes.string.isRequired,
    }).isRequired,
    onViewPostDetails: PropTypes.func.isRequired,
    onDeletePostDetails: PropTypes.func.isRequired
};

export default PostsDataModerator;
