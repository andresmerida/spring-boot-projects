import {Link, Route, Routes} from "react-router-dom";
import HomePage from "./pages/HomePage.jsx";
import LoginPage from "./pages/LoginPage.jsx";
import Navbar from "./layouts/Navbar.jsx";

function App() {

  return (
    <div>
      <Navbar />
      <Routes>
        <Route path={"/"} element={<HomePage />} />
        <Route path={"/about"} element={<LoginPage/>} />
        <Route path={"*"} element={<div>404 Not Found</div>} />
      </Routes>
      <div>Footer</div>
    </div>
  )
}

export default App
