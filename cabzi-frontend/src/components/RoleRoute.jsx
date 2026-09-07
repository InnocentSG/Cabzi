import { Navigate } from "react-router-dom";

import { useContext } from "react";

import { AuthContext } from "../context/Authcontext";

import {
  getRoleHome,
  normalizeRole
} from "../utils/roles";

function RoleRoute({

  children,

  allowedRole,

  allowedRoles

}) {

  const {

    user,

    loading

  } = useContext(AuthContext);

  // =================================
  // WAIT FOR AUTH LOAD
  // =================================

  if (loading) {

    return (

      <div className="flex items-center justify-center min-h-screen">

        <h1 className="text-xl font-semibold">
          Loading...
        </h1>

      </div>
    );
  }

  // =================================
  // NOT LOGGED IN
  // =================================

  if (!user) {

    return <Navigate to="/login" replace />;
  }

  // =================================
  // ROLE CHECK
  // =================================

  const allowed =
    allowedRoles || [allowedRole];

  if (
    !allowed.includes(
      normalizeRole(user.role)
    )
  ) {

    return (
      <Navigate
        to={getRoleHome(user.role)}
        replace
      />
    );
  }

  return children;
}

export default RoleRoute;