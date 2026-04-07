import {Link} from "react-router-dom";

function Navbar() {
  return (
    <div>
      <div className={"flex items-center justify-between h-12 bg-slate-900 text-white px-4"}>
        <div className={"font-bold"}>Logo</div>
        <div className={" flex gap-4"}>
          <Link to={"/"}>Home</Link>
          <span>My URLs</span>
          <span>Admin Dashboard</span>
          <Link to={"/login"}>Login</Link>
          <span>Register</span>
        </div>
      </div>
    </div>
  )
}

export default Navbar