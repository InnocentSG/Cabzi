import { useState } from "react";

import { useContext } from "react";

import { ThemeContext } from "../context/ThemeContext";

import { publicRoles } from "../utils/roles";

import { authApi } from "../services/backend";

function Register() {

  const [name, setName] =
    useState("");

  const [phone, setPhone] =
    useState("");

  const [email, setEmail] =
    useState("");

  const [password, setPassword] =
    useState("");

  const [
    confirmPassword,
    setConfirmPassword
  ] = useState("");

  const [role, setRole] =
    useState("user");

  const [error, setError] =
    useState("");

  const [success, setSuccess] =
    useState("");

  const { darkMode } =
    useContext(ThemeContext);

  // =================================
  // REGISTER
  // =================================

  const handleRegister =
    async (e) => {

      e.preventDefault();

      setError("");

      setSuccess("");

      // =============================
      // PHONE VALIDATION
      // =============================

      if (
        !/^[0-9]{10}$/.test(phone)
      ) {

        setError(
          "Phone number must contain exactly 10 digits"
        );

        return;
      }

      // =============================
      // PASSWORD MATCH
      // =============================

      if (
        password !==
        confirmPassword
      ) {

        setError(
          "Passwords do not match"
        );

        return;
      }

      try {

        // =========================
        // API CALL
        // =========================

        await authApi.register({

          name,

          phone,

          email,

          password,

          role:
            role.toUpperCase()

        });

        // =========================
        // SUCCESS
        // =========================

        setSuccess(
          "Registration successful"
        );

        setTimeout(() => {

          window.location.href =
            "/login";

        }, 1000);

      } catch (error) {

        console.error(error);

        setError(

          error.response?.data?.message

          ||

          error.response?.data

          ||

          "Registration failed"
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
        onSubmit={handleRegister}
        className={`w-full max-w-md p-10 rounded-3xl shadow-2xl ${
          darkMode
            ? "bg-black border border-gray-800"
            : "bg-gray-100 border border-gray-300"
        }`}
      >

        {/* TITLE */}

        <h1 className="text-5xl font-black text-orange-400 mb-10 text-center">

          Sign Up

        </h1>

        {/* ERROR */}

        {error && (

          <div className="bg-red-500 text-white p-4 rounded-xl mb-5 text-center">

            {error}

          </div>

        )}

        {/* SUCCESS */}

        {success && (

          <div className="bg-green-500 text-white p-4 rounded-xl mb-5 text-center">

            {success}

          </div>

        )}

        {/* NAME */}

        <input
          type="text"
          placeholder="Full Name"
          value={name}
          onChange={(e) =>
            setName(e.target.value)
          }
          className={`w-full p-4 rounded-2xl mb-5 outline-none ${
            darkMode
              ? "bg-[#1e1e1e] text-white"
              : "bg-white text-black border border-gray-300"
          }`}
          required
        />

        {/* PHONE */}

        <input
          type="tel"
          placeholder="Mobile Number"
          value={phone}
          onChange={(e) =>
            setPhone(e.target.value)
          }
          className={`w-full p-4 rounded-2xl mb-5 outline-none ${
            darkMode
              ? "bg-[#1e1e1e] text-white"
              : "bg-white text-black border border-gray-300"
          }`}
          required
        />

        {/* EMAIL */}

        <input
          type="email"
          placeholder="Email"
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

        {/* ROLE */}

        <select
          value={role}
          onChange={(e) =>
            setRole(e.target.value)
          }
          className={`w-full p-4 rounded-2xl mb-5 outline-none ${
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

        {/* PASSWORD */}

        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) =>
            setPassword(e.target.value)
          }
          className={`w-full p-4 rounded-2xl mb-5 outline-none ${
            darkMode
              ? "bg-[#1e1e1e] text-white"
              : "bg-white text-black border border-gray-300"
          }`}
          required
        />

        {/* CONFIRM PASSWORD */}

        <input
          type="password"
          placeholder="Re-enter Password"
          value={confirmPassword}
          onChange={(e) =>
            setConfirmPassword(
              e.target.value
            )
          }
          className={`w-full p-4 rounded-2xl mb-6 outline-none ${
            darkMode
              ? "bg-[#1e1e1e] text-white"
              : "bg-white text-black border border-gray-300"
          }`}
          required
        />

        {/* BUTTON */}

        <button
          type="submit"
          className="w-full bg-orange-500 hover:bg-orange-400 py-4 rounded-2xl text-black font-black text-xl transition"
        >

          Create Account

        </button>

      </form>

    </div>
  );
}

export default Register;