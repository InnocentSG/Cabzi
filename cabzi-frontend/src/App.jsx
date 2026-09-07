import {
  BrowserRouter,
  Routes,
  Route
} from "react-router-dom";

import { useContext } from "react";

import { ThemeContext } from "./context/ThemeContext";

import Navbar from "./components/Navbar";
import Footer from "./components/Footer";

import Home from "./pages/Home";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import AdminEntry from "./pages/AdminEntry";
import DriverDashboard from "./pages/DriverDashboard";
import DriverVerifierDashboard from "./pages/DriverVerifierDashboard";
import RoleRoute from "./components/RoleRoute";

import BookRide from "./pages/BookRide";
import Notifications from "./pages/Notifications";
import Payment from "./pages/Payment";

import TrackRide from "./pages/TrackRide";
import RateRide from "./pages/RateRide";

import Chat from "./pages/Chat";
import ShareRide from "./pages/ShareRide";
import Profile from "./pages/Profile";
import MenuPage from "./pages/MenuPage";
import StaticPage from "./pages/StaticPage";

function App() {

  const { darkMode } =
    useContext(ThemeContext);

  return (

    <BrowserRouter>

      <div
        className={`min-h-screen flex flex-col transition-all duration-300 ${
          darkMode
            ? "bg-[#111111] text-white"
            : "bg-white text-black"
        }`}
      >

        <Navbar />

        {/* MAIN CONTENT */}

        <div className="flex-1">

          <Routes>

            <Route
              path="/"
              element={<Home />}
            />

            <Route
              path="/login"
              element={<Login />}
            />

            <Route
              path="/staff-login"
              element={
                <Login
                  fixedRole="staff"
                  title="Staff Login"
                />
              }
            />

            <Route
              path="/register"
              element={<Register />}
            />

            <Route
              path="/dashboard"
              element={
                <RoleRoute allowedRole="user">
                  <Dashboard />
                </RoleRoute>
              }
            />

            <Route
              path="/admin"
              element={<AdminEntry />}
            />

            <Route
              path="/driver"
              element={
                <RoleRoute allowedRole="driver">
                  <DriverDashboard />
                </RoleRoute>
              }
            />

            <Route
              path="/driver-verifier"
              element={
                <RoleRoute allowedRole="driver_verifier">
                  <DriverVerifierDashboard />
                </RoleRoute>
              }
            />

            <Route
              path="/book-ride"
              element={<BookRide />}
            />

            <Route
              path="/share-ride"
              element={<ShareRide />}
            />

            <Route
              path="/notifications"
              element={<Notifications />}
            />

            <Route
              path="/payment"
              element={<Payment />}
            />

            <Route
              path="/track-ride"
              element={<TrackRide />}
            />

            <Route
              path="/rate-ride"
              element={<RateRide />}
            />

            <Route
              path="/chat"
              element={<Chat />}
            />

            <Route
              path="/profile"
              element={<Profile />}
            />

            <Route
              path="/my-rides"
              element={<MenuPage type="my-rides" />}
            />

            <Route
              path="/policy"
              element={<MenuPage type="policy" />}
            />

            <Route
              path="/rewards"
              element={<MenuPage type="rewards" />}
            />

            <Route
              path="/subscription"
              element={<MenuPage type="subscription" />}
            />

            <Route
              path="/pass"
              element={<MenuPage type="pass" />}
            />

            <Route
              path="/settings"
              element={<MenuPage type="settings" />}
            />

            <Route path="/about" element={<StaticPage type="about" />} />
            <Route path="/careers" element={<StaticPage type="careers" />} />
            <Route path="/blog" element={<StaticPage type="blog" />} />
            <Route path="/investors" element={<StaticPage type="investors" />} />
            <Route path="/help" element={<StaticPage type="help" />} />
            <Route path="/safety" element={<StaticPage type="safety" />} />
            <Route path="/privacy" element={<StaticPage type="privacy" />} />
            <Route path="/terms" element={<StaticPage type="terms" />} />
            <Route path="/download/google-play" element={<StaticPage type="download-google-play" />} />
            <Route path="/download/app-store" element={<StaticPage type="download-app-store" />} />

          </Routes>

        </div>

        <Footer />

      </div>

    </BrowserRouter>
  );
}

export default App;
