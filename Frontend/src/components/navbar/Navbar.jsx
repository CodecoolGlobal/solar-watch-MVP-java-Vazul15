import React, { useState } from "react";
import LogoutButton from "../LogoutButton";
import { useAuth } from "../auth/AuthProvider";
import CreateCity from "../cities/CreateCity";

const Navbar = () => {
  const [showCityForm, setShowCityForm] = useState(false);
  const { userRoles } = useAuth();

  const isUser = userRoles.includes("ROLE_USER");
  const isAdmin = userRoles.includes("ROLE_ADMIN");

  const toggleCityForm = () => {
    setShowCityForm(prev => !prev);
  };


  return (
    <div className="relative">
      <div className="bg-purple-900 absolute bg-gradient-to-b from-gray-900 via-gray-900 to-purple-800 leading-5 h-full w-full overflow-hidden z-0"></div>
      
      <div className="relative z-10 navbar bg-base-100">
        {isAdmin && (
          <div className="flex-1">
            <button onClick={toggleCityForm} className="btn btn-ghost text-xl">
              Create City
            </button>
          </div>
        )}
        <div className="btn btn-ghost text-xl">
          <LogoutButton />
        </div>
      </div>

      {showCityForm && (
        <div className="relative z-10 p-4">
          <CreateCity />
        </div>
      )}
    </div>
  );
};

export default Navbar;
