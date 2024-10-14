import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import CityCard from '../components/cities/CityCard';
import Navbar from '../components/navbar/Navbar';

const fetchCurrentWeather = async (cityName, countryCode, stateCode, date, token) => {
  const response = await fetch(`/api/current?cityName=${cityName}&countryCode=${countryCode}${stateCode ? `&stateCode=${stateCode}` : ''}&date=${date}`, {
    headers: {
      'Authorization': `Bearer ${token}`,
    },
  });

  if (!response.ok) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }

  const data = await response.json();
  return data;
};

export const CityInformation = () => {
  const { cityName, countryCode, stateCode } = useParams();
  const [weatherData, setWeatherData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [selectedDate, setSelectedDate] = useState(new Date().toISOString().split('T')[0]);

  const token = localStorage.getItem('accessToken');

  const handleDateChange = (event) => {
    setSelectedDate(event.target.value);
  };

  const fetchData = async () => {
    try {
      const data = await fetchCurrentWeather(cityName, countryCode, stateCode || '', selectedDate, token);
      setWeatherData(data);
      console.log(data);
    } catch (error) {
      console.error('Error fetching weather data:', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, [cityName, countryCode, stateCode, selectedDate, token]);

  if (loading) {
    return <p>Loading...</p>;
  }

  if (!weatherData) {
    return <p>No data available</p>;
  }

  return (
    <>
    <Navbar />
      <link href="https://unpkg.com/tailwindcss@^2/dist/tailwind.min.css" rel="stylesheet" />
      <div className="bg-purple-900 absolute bg-gradient-to-b from-gray-900 via-gray-900 to-purple-800 leading-5 h-full w-full overflow-hidden"></div>
      <div className="relative min-h-screen flex flex-col items-center">
        <div className="p-4 w-full max-w-lg z-10 mt-10"> 
          <h1 className="text-3xl font-bold mb-4 text-center text-white">{cityName} Weather</h1>
        </div>
        <div className="absolute top-4 left-4 z-10">
          <label htmlFor="date" className="block text-lg font-semibold mb-2 text-white">Select Date:</label>
          <input 
            type="date" 
            id="date" 
            value={selectedDate} 
            onChange={handleDateChange} 
            className="input input-bordered w-full max-w-xs"
          />
        </div>
        <CityCard 
          city={{ 
            CityName: cityName, 
            sunrise: weatherData.results.sunrise, 
            sunset: weatherData.results.sunset, 
            date: selectedDate 
          }} 
        />
      </div>
    </>
  );
};

export default CityInformation;
