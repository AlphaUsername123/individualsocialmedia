import React, {useEffect, useState} from 'react';
import {Card, Button} from 'react-bootstrap';
import {useNavigate} from 'react-router-dom';
import PropTypes from 'prop-types';
import PostAPI from "../api/PostAPI.tsx";
import TokenManager from "../api/TokenManager.tsx";

interface Post {
    id: number;
    text: string;
    username: string;
    createdAt: string;
    likeCount: number;
}

function AllPostsData() {
    // Step 2: Use the Post type for the state
    const [data, setData] = useState<Post[]>([]);

    const token = localStorage.getItem("accessToken");
    TokenManager.setAccessToken(token);

    useEffect(() => {
        PostAPI.getPosts()
            .then((response) => {
                setData(response.posts); // Assuming response.posts is an array of Post objects
                console.log(response.posts);
            })
            .catch((error) => {
                console.error('Error fetching data:', error);
            });
    }, []);

    const handleLike = async (postId: number, currentLikeCount: number) => {
        try {
            // Assuming 'userId' comes from the authentication context
            const userId = TokenManager.getClaims().UserId; // Replace with the actual user ID from context or session
            const updatedLikeCount = await PostAPI.likePost(postId, userId);

            // Update like count for the specific post
            setData((prevData) =>
                prevData.map((post) =>
                    post.id === postId ? { ...post, likeCount: updatedLikeCount } : post
                )
            );
        } catch (error) {
            alert('Failed to like the post. Please try again.');
        }
    };

    return (
        <div>
            {data.map((post, index) => (
                <PostCard
                    key={index}
                    post={post}
                    onLikedPostDetails={handleLike}
                />
            ))}
        </div>
    );
}

function PostCard({ post, onLikedPostDetails }: { post: Post; onLikedPostDetails: (postId: number, currentLikeCount: number) => void }) {
    const { id, text, username, createdAt, likeCount } = post;

    return (
        <Card style={{ width: '18rem', marginBottom: '20px' }}>
            <Card.Body>
                <Card.Title className="d-flex justify-content-between align-items-center">
                    <span>{username}</span>
                    <span className="text-muted" style={{ fontSize: '0.85rem' }}>{createdAt}</span>
                </Card.Title>
                <Card.Text>
                    <strong>Text:</strong> {text}
                </Card.Text>
                <div className="d-flex justify-content-between align-items-center">
                    <Button variant="primary" onClick={() => onLikedPostDetails(id, likeCount)}>
                        Like
                    </Button>
                    <span>{likeCount} Likes</span>
                </div>
            </Card.Body>
        </Card>
    );
}

PostCard.propTypes = {
    post: PropTypes.shape({
        id: PropTypes.number.isRequired,
        text: PropTypes.string.isRequired,
        username: PropTypes.string.isRequired,
        createdAt: PropTypes.string.isRequired,
        likeCount: PropTypes.number.isRequired,
    }).isRequired,
    onLikedPostDetails: PropTypes.func.isRequired,
};

export default AllPostsData;
