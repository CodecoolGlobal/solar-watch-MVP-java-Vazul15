import React from "react";
import RegisterForm from "./RegisterForm";

const createUser = async (user) => {
  try {
    const res = await fetch("/api/user/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(user),
    });

    if (!res.ok) {
      throw new Error("Network response was not ok");
    }

    const result = await res.json();
    return result;
  } catch (error) {
    console.error("Error:", error);
    return false;
  }
};

const Register = () => {
  const handleCreateUser = async (user) => {
    const success = await createUser(user);

    if (success) {
      console.log("Registration successful");
    } else {
      console.log("Registration failed");
    }
  };

  return (
    <>
      <RegisterForm onSave={handleCreateUser} />
    </>
  );
};

export default Register;
