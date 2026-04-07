
function Navbar() {
  return (
    <div>
      <div className={"flex items-center h-12 bg-slate-900 text-white px-4"}>
        <div className={"font-bold"}>Logo</div>
        <div className={"flex justify-between gap-4"}>
          <div>
            <span>My URLs</span>
            <span>Admin Dashboard</span>
          </div>
          <div>
            <span>Login</span>
            <span>Register</span>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Navbar