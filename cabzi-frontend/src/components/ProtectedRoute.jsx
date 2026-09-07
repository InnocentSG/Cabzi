import { Navigate } from "react-router-dom";

import { useContext } from "react";

import { AuthContext } from "../context/Authcontext";

function ProtectedRoute({
  children
}) {

  const { user } =
    useContext(AuthContext);

  if (!user) {

    return <Navigate to="/login" />;
  }

  return children;
}

export default ProtectedRoute;
