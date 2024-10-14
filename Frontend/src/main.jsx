import "./index.css";
import React from "react";
import ReactDOM from "react-dom/client";
import { AuthProvider } from "./components/auth/AuthProvider.jsx";
import { router } from "./router.jsx";
import { RouterProvider } from "react-router-dom";

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <AuthProvider>
      <RouterProvider router={router} />
    </AuthProvider>
  </React.StrictMode>
);