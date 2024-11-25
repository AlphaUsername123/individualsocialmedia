import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import LoginPage from "./pages/LoginPage.tsx";
import ViewPage from "./pages/ADMIN Page/ViewPage.tsx";
import UserPage from "./pages/UserPage/UserPage.tsx";
import AddPost from "./components/AddPost.tsx";
import React from "react";
import PostUpdate from "./components/PostUpdate.tsx";
import ProtectedRoute from "./components/ProtectedRoute.tsx";
import NotAuthorizedRolePage from "./pages/Error/NotAuthorizedRolePage.tsx";
import ProtectedRouteAdmin from "./components/ProtectedRouteAdmin.tsx";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<LoginPage/>}/>
                <Route path="/adminpage" element={<ProtectedRouteAdmin><ViewPage/></ProtectedRouteAdmin>}/>
                {/*<Route path="/productpost" element={<AddPost/>}/>*/}
                {/*<Route path="/editproduct/:ProductId" element={<PostUpdate/>}/>*/}
                <Route path="/userpage" element={<ProtectedRoute><UserPage/></ProtectedRoute>}/>
                {/*<Route path="/websocket" element={<WebSocketPage/>}></Route>*/}
                <Route path="/addpost" element={<ProtectedRoute><AddPost/></ProtectedRoute>}/>
                <Route path="/editpost/:PostId" element={<ProtectedRoute><PostUpdate/></ProtectedRoute>}/>
                <Route path="/noaccess" element={<NotAuthorizedRolePage/>}></Route>

            </Routes>
        </Router>
    );
}



export default App
