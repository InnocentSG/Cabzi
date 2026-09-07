import { useState } from "react";

import { useNavigate } from "react-router-dom";

import { useContext } from "react";

import { ThemeContext } from "../context/ThemeContext";

import {
  AuthContext
} from "../context/Authcontext";

import {
  getRoleHome,
  normalizeRole,
  publicRoles
} from "../utils/roles";

import { authApi }
from "../services/backend";

function Login({

  defaultRole = "user",

  fixedRole = null,

  title = "Login"

}) {

  const [email, setEmail] =
    useState("");

  const [password, setPassword] =
    useState("");

  const [role, setRole] =
    useState(defaultRole);

  const [error, setError] =
    useState("");

  const navigate =
    useNavigate();

  const { darkMode } =
    useContext(ThemeContext);

  const { login } =
    useContext(AuthContext);

  // =================================
  // LOGIN
  // =================================

  const handleLogin =
    async (e) => {

      e.preventDefault();

      setError("");

      const selectedRole =
        fixedRole || role;

      try {

        const response =
          await authApi.login({

            email,

            password,

            role:
              selectedRole.toUpperCase()

          });

        // ===========================
        // NORMALIZE USER
        // ===========================

        const responseUser = {

          ...(response.user
            ||

            response.profile
            ||

            response),

          role: normalizeRole(

            response.user?.role

            ||

            response.profile?.role

            ||

            response.role

            ||

            selectedRole
          )
        };

        // ===========================
        // SAVE AUTH
        // ===========================

        login(

          responseUser,

          response.token
            ||

            response.jwt
        );

        // ===========================
        // NAVIGATE
        // ===========================

        navigate(

          getRoleHome(
            responseUser.role
          ),

          {
            replace: true
          }
        );

      } catch (error) {

        console.error(error);

        setError(

          error.response?.data?.message

          ||

          "Invalid email or password"
        );
      }
    };

  return (

    <div
      className={`min-h-screen flex justify-center items-center px-5 transition-all duration-300 ${
        darkMode
          ? "bg-[#111111] text-white"
          : "bg-white text-black"
      }`}
    >

      <form
        onSubmit={handleLogin}
        className={`w-full max-w-md p-10 rounded-3xl shadow-2xl ${
          darkMode
            ? "bg-black border border-gray-800"
            : "bg-gray-100 border border-gray-300"
        }`}
      >

        {/* TITLE */}

        <h1 className="text-5xl font-black text-orange-400 mb-10 text-center">

          {title}

        </h1>

        <div className="flex justify-end mb-4">

  {fixedRole === "staff" && (

    <button
      type="button"
      onClick={() => navigate("/admin")}
      className="text-orange-400 text-2xl hover:scale-110 transition"
    >
      ⇄
    </button>

  )}

  {fixedRole === "admin" && (

    <button
      type="button"
      onClick={() => navigate("/staff-login")}
      className="text-orange-400 text-2xl hover:scale-110 transition"
    >
      ⇄
    </button>

  )}

</div>

        {/* ERROR */}

        {error && (

          <div className="bg-red-500 text-white p-4 rounded-xl mb-5 text-center">

            {error}

          </div>

        )}

        {/* EMAIL */}

        <input
          type="email"
          placeholder="Enter Email"
          value={email}
          onChange={(e) =>
            setEmail(e.target.value)
          }
          className={`w-full p-4 rounded-2xl mb-5 outline-none ${
            darkMode
              ? "bg-[#1e1e1e] text-white"
              : "bg-white text-black border border-gray-300"
          }`}
          required
        />

        {/* PASSWORD */}

        <input
          type="password"
          placeholder="Enter Password"
          value={password}
          onChange={(e) =>
            setPassword(e.target.value)
          }
          className={`w-full p-4 rounded-2xl mb-6 outline-none ${
            darkMode
              ? "bg-[#1e1e1e] text-white"
              : "bg-white text-black border border-gray-300"
          }`}
          required
        />

        {/* ROLE SELECT */}

        {!fixedRole && (

          <select
            value={role}
            onChange={(e) =>
              setRole(e.target.value)
            }
            className={`w-full p-4 rounded-2xl mb-6 outline-none ${
              darkMode
                ? "bg-[#1e1e1e] text-white"
                : "bg-white text-black border border-gray-300"
            }`}
          >

            {publicRoles.map((item) => (

              <option
                key={item.value}
                value={item.value}
              >

                {item.label}

              </option>

            ))}

          </select>
        )}

        {/* BUTTON */}

        <button
          type="submit"
          className="w-full bg-orange-500 hover:bg-orange-400 py-4 rounded-2xl text-black font-black text-xl transition"
        >

          Login

        </button>

      </form>

    </div>
  );
}

export default Login;