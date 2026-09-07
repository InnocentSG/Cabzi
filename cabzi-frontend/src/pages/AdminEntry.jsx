import { useContext } from "react";

import AdminDashboard from "./AdminDashboard";

import Login from "./Login";

import { AuthContext }
from "../context/Authcontext";

import {
  normalizeRole
} from "../utils/roles";

function AdminEntry() {

  const {

    user,

    loading

  } = useContext(AuthContext);

  // =============================
  // LOADING
  // =============================

  if (loading) {

    return (

      <div className="min-h-screen flex items-center justify-center">

        <h1 className="text-2xl font-bold">
          Loading...
        </h1>

      </div>
    );
  }

  // =============================
  // ADMIN LOGGED IN
  // =============================

  if (

    user

    &&

    normalizeRole(user.role)
      === "admin"

  ) {

    return <AdminDashboard />;
  }

  // =============================
  // LOGIN PAGE
  // =============================

  return (

    <Login
      fixedRole="admin"
      title="Admin Login"
    />
  );
}

export default AdminEntry;