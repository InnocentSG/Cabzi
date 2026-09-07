import { Link } from "react-router-dom";

import { useContext, useState } from "react";
import {
  Bell,
  Car,
  CreditCard,
  Gift,
  HelpCircle,
  MoreVertical,
  Package,
  ReceiptText,
  Settings,
  ShieldCheck,
  Ticket
} from "lucide-react";

import { ThemeContext } from "../context/ThemeContext";
import {
  normalizeRole
} from "../utils/roles";

const getSavedUser = () => {

  try {

    const savedUser =
      localStorage.getItem("user");

    return savedUser ? JSON.parse(savedUser) : null;

  } catch {

    return null;
  }
};

function Navbar() {

  const {
    darkMode,
    toggleTheme
  } = useContext(ThemeContext);

  const [menuOpen, setMenuOpen] =
    useState(false);

  const [profileOpen, setProfileOpen] =
    useState(false);

  const user =
    getSavedUser();

  const userRole =
    normalizeRole(user?.role);

  const handleLogout = () => {

    localStorage.removeItem("token");

    localStorage.removeItem("user");

    window.location.href =
      "/login";
  };

  return (

    <nav
      className={`sticky top-0 z-50 flex justify-between items-center px-10 py-5 border-b transition-all duration-300 ${
        darkMode
          ? "bg-black text-white border-gray-800"
          : "bg-gray-200 text-black border-gray-300"
      }`}
    >

      {/* LOGO */}

      <Link
        to="/"
        className={`text-4xl italic font-black tracking-wide ${
          darkMode
            ? "text-orange-400"
            : "text-orange-600"
        }`}
      >

        CABZI

      </Link>

      {/* NAVIGATION */}

      <div className="flex items-center gap-4 flex-wrap font-bold">

        {!user && (

          <>

            <Link to="/about">
              About
            </Link>

            <Link
              to="/login"
              className={`px-5 py-2 rounded-xl ${
                darkMode
                  ? "bg-white text-black"
                  : "bg-black text-white"
              }`}
            >

              Login

            </Link>

            <Link
              to="/register"
              className="bg-orange-500 text-black px-5 py-2 rounded-xl"
            >

              Sign Up

            </Link>

            <div className="relative">

              <button
                onClick={() =>
                  setMenuOpen(!menuOpen)
                }
                className={`w-10 h-10 rounded-xl flex items-center justify-center ${
                  darkMode
                    ? "bg-[#1e1e1e]"
                    : "bg-white"
                }`}
                aria-label="More options"
              >
                <MoreVertical size={22} />
              </button>

              {menuOpen && (

                <div
                  className={`absolute right-0 top-12 w-44 rounded-2xl overflow-hidden shadow-2xl border ${
                    darkMode
                      ? "bg-black border-gray-800"
                      : "bg-white border-gray-300"
                  }`}
                >

                  <Link
                    to="/staff-login"
                    className="block px-5 py-3 hover:bg-orange-500/20"
                    onClick={() =>
                      setMenuOpen(false)
                    }
                  >
                    Staff Login
                  </Link>

                  <a
                    href="mailto:sumit257210@gmail.com"
                    className="block px-5 py-3 hover:bg-orange-500/20"
                    onClick={() =>
                      setMenuOpen(false)
                    }
                  >
                    Help
                  </a>

                </div>
              )}

            </div>

          </>

        )}

        {user && (

          <>

            {userRole === "user" && (

              <>

                <Link to="/">
                  Ride
                </Link>

                <Link to="/share-ride">
                  Share Ride
                </Link>

              </>
            )}

            {userRole === "admin" && (

              <>
                <Link to="/admin?tab=dashboard">Dashboard</Link>
                <Link to="/admin?tab=creation">Creation</Link>
              </>
            )}

            {userRole === "driver" && (

              <>
                <Link to="/driver?tab=dashboard">Dashboard</Link>
                <Link to="/driver?tab=subscription">Subscription</Link>
              </>
            )}

            {userRole === "driver_verifier" && (

              <Link to="/driver-verifier">
                Staff Dashboard
              </Link>
            )}

            <div className="relative">

              <button
                onClick={() =>
                  setProfileOpen(!profileOpen)
                }
                className={`flex items-center gap-3 px-4 py-2 rounded-2xl ${
                  darkMode
                    ? "bg-[#1e1e1e]"
                    : "bg-white"
                }`}
              >
                <span className="w-10 h-10 rounded-full bg-orange-500 text-black flex items-center justify-center font-black">
                  {user.name?.charAt(0)?.toUpperCase() || "C"}
                </span>
                <span className="text-left leading-tight">
                  <span className="block">{user.name || user.email}</span>
                  <span className="block text-xs text-gray-500 capitalize">
                    {userRole}
                  </span>
                </span>
              </button>

              {profileOpen && (

                <div
                  className={`absolute right-0 top-14 w-72 rounded-3xl overflow-hidden shadow-2xl border ${
                    darkMode
                      ? "bg-black border-gray-800"
                      : "bg-white border-gray-300"
                  }`}
                >

                  <Link
                    to="/profile"
                    onClick={() => setProfileOpen(false)}
                    className="flex items-center gap-4 p-5 border-b border-gray-800 hover:bg-orange-500/10"
                  >
                    <span className="w-14 h-14 rounded-full bg-orange-500 text-black flex items-center justify-center text-2xl font-black">
                      {user.name?.charAt(0)?.toUpperCase() || "C"}
                    </span>
                    <span>
                      <span className="block font-black">
                        {user.name || user.email}
                      </span>
                      <span className="block text-sm text-gray-500">
                        Click name to update details
                      </span>
                    </span>
                  </Link>

                  <ProfileLink icon={<HelpCircle size={18} />} label="Help" href="mailto:sumit257210@gmail.com" />
                  <ProfileLink icon={<Car size={18} />} label="Ride" to="/" close={() => setProfileOpen(false)} />
                  <ProfileLink icon={<Package size={18} />} label="Parcel" to="/payment" close={() => setProfileOpen(false)} />
                  <ProfileLink icon={<CreditCard size={18} />} label="Payment" to="/payment" close={() => setProfileOpen(false)} />
                  <ProfileLink icon={<ReceiptText size={18} />} label="My Rides" to="/my-rides" close={() => setProfileOpen(false)} />
                  <ProfileLink icon={<ShieldCheck size={18} />} label="Policy" to="/policy" close={() => setProfileOpen(false)} />
                  <ProfileLink icon={<Gift size={18} />} label="Reward" to="/rewards" close={() => setProfileOpen(false)} />
                  <ProfileLink icon={<Ticket size={18} />} label="My Subscription" to="/subscription" close={() => setProfileOpen(false)} />
                  <ProfileLink icon={<Ticket size={18} />} label="Pass" to="/pass" close={() => setProfileOpen(false)} />
                  <ProfileLink icon={<Bell size={18} />} label="Notification" to="/notifications" close={() => setProfileOpen(false)} />
                  <ProfileLink icon={<Settings size={18} />} label="Setting" to="/settings" close={() => setProfileOpen(false)} />

                  <button
                    onClick={handleLogout}
                    className="w-full text-left px-5 py-3 bg-red-500 text-white font-bold"
                  >
                    Logout
                  </button>

                </div>
              )}

            </div>

          </>

        )}

        {/* THEME */}

        <button
          onClick={toggleTheme}
          className={`px-5 py-2 rounded-xl ${
            darkMode
              ? "bg-white text-black"
              : "bg-black text-white"
          }`}
        >

          {darkMode
            ? "Light"
            : "Dark"}

        </button>

      </div>

    </nav>
  );
}

function ProfileLink({
  icon,
  label,
  to,
  href,
  close
}) {

  const className =
    "flex items-center gap-3 px-5 py-3 hover:bg-orange-500/10";

  if (href) {

    return (
      <a
        href={href}
        className={className}
      >
        {icon}
        {label}
      </a>
    );
  }

  return (
    <Link
      to={to}
      onClick={close}
      className={className}
    >
      {icon}
      {label}
    </Link>
  );
}

export default Navbar;
