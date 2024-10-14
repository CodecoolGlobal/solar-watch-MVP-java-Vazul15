import React, { useState } from "react";
import CityForm from "./CityForm";

const fetchGetCity = async (cityName, countryCode, stateCode, token) => {
  try {
    const url = `/api/cityPosition?cityName=${cityName}&countryCode=${countryCode}${stateCode ? `&stateCode=${stateCode}` : ""}`;

    const response = await fetch(url, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    const data = await response.json();
    console.log("City fetched successfully:", data);
    return data;
  } catch (error) {
    console.error("Error fetching city:", error);
  }
};

const CreateCity = () => {
  const token = localStorage.getItem("accessToken");

  const handleSubmit = async (city) => {
    return fetchGetCity(city.name, city.country, city.state, token);
  };

  return (
    <>
      <CityForm onSave={handleSubmit} />
    </>
  );
};

export default CreateCity;
