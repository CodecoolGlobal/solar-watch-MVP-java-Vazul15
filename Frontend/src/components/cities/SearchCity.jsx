import React, { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";

const fetchCities = async (query, token) => {
  const response = await fetch(`/api/search?query=${query}`, {
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

const SearchCity = () => {
  const [query, setQuery] = useState('');
  const [cities, setCities] = useState([]);
  const [loading, setLoading] = useState(false);
  const token = localStorage.getItem('accessToken');

  const navigate = useNavigate();

  const handleNavigation = (city) => {
    console.log('Navigating to:', city);
    navigate(`/city/${city.name}/${city.countryCode}/${city.stateCode || ''}`);
  };

  useEffect(() => {
    const handleSearch = async () => {
      if (query.trim() === "") {
        setCities([]);
        return;
      }

      setLoading(true);
      try {
        const data = await fetchCities(query, token);
        console.log('Fetched Cities:', data);
        setCities(data);
      } catch (error) {
        console.error("Error fetching cities:", error);
        setCities([]);
      } finally {
        setLoading(false);
      }
    };

    handleSearch();
  }, [query, token]);

  return (
    <div className="p-4">
      <input
        type="text"
        value={query}
        onChange={(e) => setQuery(e.target.value)}
        placeholder="Type city name"
        className="input input-bordered input-info w-full max-w-xs mb-4"
      />

      {loading && <p>Loading...</p>}

      {cities.length > 0 && (
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
          {cities.map((city) => (
            <button 
              key={city.name} 
              onClick={() => handleNavigation(city)} 
              className="bg-purple-500 text-white p-2 rounded hover:bg-blue-600 transition duration-200"
            >
              {city.name}
            </button>
          ))}
        </div>
      )}
    </div>
  );
};

export default SearchCity;
