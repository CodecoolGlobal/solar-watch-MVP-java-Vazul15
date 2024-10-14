import React from 'react'
import { useAuth } from './auth/AuthProvider'
import { useNavigate } from 'react-router-dom';

const LogoutButton = () => {
    const { logout } = useAuth();
    const navigate = useNavigate(); 

    const handleLogout = () => {
        logout()
        navigate("/")
    };

  return (
    <div><button onClick={handleLogout}>Logout</button></div>
  )
}

export default LogoutButton;
