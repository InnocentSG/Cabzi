import { useContext } from "react";
import { Link } from "react-router-dom";

import { ThemeContext } from "../context/ThemeContext";

function Footer() {

  const { darkMode } =
    useContext(ThemeContext);

  return (

    <footer
      className={`border-t mt-20 transition-all duration-300 ${
        darkMode
          ? "bg-black text-white border-gray-800"
          : "bg-gray-200 text-black border-gray-300"
      }`}
    >

      {/* TOP SECTION */}

      <div className="max-w-7xl mx-auto px-10 py-16 grid md:grid-cols-4 gap-12">

        {/* BRAND */}

        <div>

          <h1
            className={`text-4xl italic font-black ${
              darkMode
                ? "text-orange-400"
                : "text-orange-600"
            }`}
          >

            CABZI

          </h1>

          <p className="mt-5 text-gray-500 leading-7">

            Smart ride booking platform
            with ride sharing, parcel
            delivery, realtime tracking,
            and modern transportation
            services.

          </p>

        </div>

        {/* COMPANY */}

        <div>

          <h2 className="text-2xl font-bold mb-6">

            Company

          </h2>

          <div className="flex flex-col gap-4">

            <Link to="/about">
              About Us
            </Link>

            <Link to="/careers">
              Careers
            </Link>

            <Link to="/blog">
              Blog
            </Link>

            <Link to="/investors">
              Investors
            </Link>

          </div>

        </div>

        {/* SUPPORT */}

        <div>

          <h2 className="text-2xl font-bold mb-6">

            Support

          </h2>

          <div className="flex flex-col gap-4">

            <Link to="/help">
              Help Center
            </Link>

            <Link to="/safety">
              Safety
            </Link>

            <Link to="/privacy">
              Privacy Policy
            </Link>

            <Link to="/terms">
              Terms & Conditions
            </Link>

          </div>

        </div>

        {/* DOWNLOAD */}

        <div>

          <h2 className="text-2xl font-bold mb-6">

            Download App

          </h2>

          <div className="flex flex-col gap-5">

            <Link
              to="/download/google-play"
              className={`px-6 py-4 rounded-2xl font-bold transition ${
                darkMode
                  ? "bg-white text-black"
                  : "bg-black text-white"
              }`}
            >

              ▶ Google Play

            </Link>

            <Link
              to="/download/app-store"
              className={`px-6 py-4 rounded-2xl font-bold transition ${
                darkMode
                  ? "bg-white text-black"
                  : "bg-black text-white"
              }`}
            >

               App Store

            </Link>

          </div>

        </div>

      </div>

      {/* BOTTOM */}

      <div className="border-t border-gray-800">

        <div className="max-w-7xl mx-auto px-10 py-8 flex flex-col md:flex-row justify-between items-center gap-6">

          {/* COPYRIGHT */}

          <p className="text-gray-500 text-center">

            © 2026 CABZI Technologies Pvt. Ltd.
            All Rights Reserved.

          </p>

          {/* SOCIAL LINKS */}

          <div className="flex gap-6 text-2xl">

            <a
              href="https://cabzi.example.com"
              target="_blank"
              rel="noreferrer"
              className="hover:text-orange-400 transition"
            >
              🌐
            </a>

            <a
              href="https://instagram.com"
              target="_blank"
              rel="noreferrer"
              className="hover:text-orange-400 transition"
            >
              📷
            </a>

            <a
              href="https://x.com"
              target="_blank"
              rel="noreferrer"
              className="hover:text-orange-400 transition"
            >
              🐦
            </a>

            <a
              href="https://youtube.com"
              target="_blank"
              rel="noreferrer"
              className="hover:text-orange-400 transition"
            >
              ▶
            </a>

          </div>

        </div>

      </div>

    </footer>
  );
}

export default Footer;
