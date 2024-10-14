import React, { useState } from "react";
import Register from "../components/Register";
import Login from "../components/Login";
import SearchCity from "../components/cities/SearchCity";
import { useAuth } from "../components/auth/AuthProvider";
import Navbar from "../components/navbar/Navbar";
import { Outlet } from "react-router-dom";

const Main = () => {
  const [showRegister, setShowRegister] = useState(false);
  const { userRoles } = useAuth();

  const handleRegisterClick = () => {
    setShowRegister(true);
  };

  const handleLoginClick = () => {
    setShowRegister(false);
  };

  const isUser = userRoles.includes("ROLE_USER");
  const isAdmin = userRoles.includes("ROLE_ADMIN");

  return (
    <div>
      {isAdmin ? (
        <>
      <div className="bg-purple-900 absolute bg-gradient-to-b from-gray-900 via-gray-900 to-purple-800 leading-5 h-full w-full overflow-hidden">
      <Navbar />
      <SearchCity />
      <Outlet />
        </div>
        </>
      ) : isUser ? (
        <h1>
          <SearchCity />
        </h1>
      ) : (
        <>
          <div>
            <button
              onClick={handleRegisterClick}
              className="btn btn-xs sm:btn-sm md:btn-md lg:btn-lg"
            >
              Sign up
            </button>
            <button
              onClick={handleLoginClick}
              className="btn btn-xs sm:btn-sm md:btn-md lg:btn-lg"
            >
              Login
            </button>
          </div>
          {showRegister ? <Register /> : <Login />}
        </>
      )}
    </div>
  );
};

export default Main;
