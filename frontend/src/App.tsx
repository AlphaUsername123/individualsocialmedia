import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import LoginPage from "./pages/LoginPage.tsx";
import ViewPage from "./pages/ADMIN Page/ViewPage.tsx";
import UserPage from "./pages/UserPage/UserPage.tsx";
import AddPost from "./components/AddPost.tsx";
import React from "react";
import PostUpdate from "./components/PostUpdate.tsx";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<LoginPage/>}/>
                <Route path="/adminpage" element={<ViewPage/>}/>
                {/*<Route path="/productpost" element={<AddPost/>}/>*/}
                {/*<Route path="/editproduct/:ProductId" element={<PostUpdate/>}/>*/}
                <Route path="/userpage" element={<UserPage/>}/>
                {/*<Route path="/websocket" element={<WebSocketPage/>}></Route>*/}
                <Route path="/addpost" element={<AddPost/>}/>
                <Route path="/editpost/:PostId" element={<PostUpdate/>}/>

            </Routes>
        </Router>
    );
}



export default App
