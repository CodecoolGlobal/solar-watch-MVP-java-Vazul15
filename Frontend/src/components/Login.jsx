import React from "react";
import LoginForm from "./LoginForm";
import { useAuth } from "./auth/AuthProvider";

const Login = () => {
  const { login } = useAuth();

  const handleLogin = async (userEmailPassword) => {
    try {
      const { email, password } = userEmailPassword;
      const data = await login(email, password);

      if (data) {
        console.log("Login successful");
      } else {
        console.log("Login failed");
      }
    } catch (error) {
      console.error("Error:", error);
    }
  };

  return (
    <div>
      <LoginForm loginMember={handleLogin} />
    </div>
  );
};

export default Login;
