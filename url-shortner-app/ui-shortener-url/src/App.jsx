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
        <Route path={"/login"} element={<LoginPage/>} />
        <Route path={"*"}
               element={<h1 style={{ padding: "0 1.5rem" }}>404 Not Found</h1>} />
      </Routes>
      <div>Footer</div>
    </div>
  )
}

export default App
