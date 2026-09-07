import React from "react";
import ReactDOM from "react-dom/client";

import App from "./App";

import "./styles/global.css";
import "leaflet/dist/leaflet.css";

import ThemeProvider from "./context/ThemeContext";
import AuthProvider from "./context/Authcontext";

ReactDOM.createRoot(
  document.getElementById("root")
).render(
  <React.StrictMode>
    <ThemeProvider>
      <AuthProvider>
        <App />
      </AuthProvider>
    </ThemeProvider>
  </React.StrictMode>
);