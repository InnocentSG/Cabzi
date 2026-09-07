import { useEffect, useMemo, useState } from "react";
import { driverApi, profileApi } from "../services/backend";

const getSavedUser = () => {

  try {

    const savedUser =
      localStorage.getItem("user");

    return savedUser ? JSON.parse(savedUser) : null;

  } catch {

    return null;
  }
};

function Profile() {

  const currentUser =
    getSavedUser();

  const [form, setForm] =
    useState({
      name: currentUser?.name || "",
      email: currentUser?.email || "",
      phone: currentUser?.phone || "",
      password: currentUser?.password || ""
    });

  const [driver, setDriver] =
    useState(null);

  const [message, setMessage] =
    useState("");

  const [error, setError] =
    useState("");

  useEffect(() => {

    const loadDriverProfile = async () => {

      if (currentUser?.role !== "driver") return;

      try {

        setDriver(await driverApi.me());

      } catch {

        setError("Unable to load driver verification status");
      }
    };

    loadDriverProfile();

  }, [currentUser?.role]);

  const profileStatus =
    useMemo(() => {

      if (!currentUser) return "Not logged in";

      if (currentUser.role === "driver") {

        return driver?.status === "verified"
          ? `Driver verified by ${driver.approvedBy || "staff"}`
          : "Driver profile needs admin or staff verification";
      }

      if (currentUser.role === "driver_verifier") {

        return "Staff profile is managed by admin";
      }

      if (currentUser.role === "admin") {

        return "Admin account";
      }

      return "User account active";

    }, [currentUser, driver]);

  const saveProfile = async (event) => {

    event.preventDefault();

    const updatedUser =
      await profileApi.update(form);

    localStorage.setItem(
      "user",
      JSON.stringify(updatedUser)
    );

    setMessage("Profile updated");
  };

  if (!currentUser) {

    return (
      <div className="min-h-screen bg-black text-white p-10">
        Login required.
      </div>
    );
  }

  return (

    <div className="min-h-screen bg-[#0f0f0f] text-white p-10">

      <div className="max-w-4xl mx-auto bg-[#1a1a1a] border border-gray-800 rounded-3xl p-8">

        <div className="flex items-center gap-5">
          <div className="w-24 h-24 rounded-full bg-orange-500 text-black flex items-center justify-center text-4xl font-black">
            {form.name?.charAt(0)?.toUpperCase() || "C"}
          </div>
          <div>
            <h1 className="text-4xl font-bold">{form.name || currentUser.email}</h1>
            <p className="text-orange-400 mt-2 capitalize">{currentUser.role}</p>
            <p className="text-gray-400 mt-2">{profileStatus}</p>
          </div>
        </div>

        <form onSubmit={saveProfile} className="grid md:grid-cols-2 gap-4 mt-8">
          <input
            value={form.name}
            onChange={(event) => setForm({ ...form, name: event.target.value })}
            placeholder="Name"
            className="bg-black border border-gray-700 rounded-2xl px-5 py-4 outline-none"
          />
          <input
            value={form.email}
            onChange={(event) => setForm({ ...form, email: event.target.value })}
            placeholder="Email"
            className="bg-black border border-gray-700 rounded-2xl px-5 py-4 outline-none"
          />
          <input
            value={form.phone}
            onChange={(event) => setForm({ ...form, phone: event.target.value })}
            placeholder="Phone"
            className="bg-black border border-gray-700 rounded-2xl px-5 py-4 outline-none"
          />
          <input
            value={form.password}
            onChange={(event) => setForm({ ...form, password: event.target.value })}
            placeholder="Password"
            className="bg-black border border-gray-700 rounded-2xl px-5 py-4 outline-none"
          />
          <button className="bg-orange-500 hover:bg-orange-400 rounded-2xl px-6 py-4 text-black font-bold">
            Update Details
          </button>
        </form>

        {message && (
          <p className="mt-5 text-green-400 font-bold">{message}</p>
        )}

        {error && (
          <p className="mt-5 text-red-400 font-bold">{error}</p>
        )}

      </div>

    </div>
  );
}

export default Profile;
