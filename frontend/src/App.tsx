import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import LoginPage from "./pages/LoginPage.tsx";
import ViewPage from "./pages/ADMIN Page/ViewPage.tsx";
import UserPage from "./pages/UserPage/UserPage.tsx";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<LoginPage/>}/>
                <Route path="/adminpage" element={<ViewPage/>}/>
                {/*<Route path="/productpost" element={<ProductPost/>}/>*/}
                {/*<Route path="/editproduct/:ProductId" element={<ProductUpdate/>}/>*/}
                <Route path="/customerpage" element={<UserPage/>}/>
                {/*<Route path="/websocket" element={<WebSocketPage/>}></Route>*/}

            </Routes>
        </Router>
    );
}



export default App
