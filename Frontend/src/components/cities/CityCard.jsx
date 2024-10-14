import React from 'react';

const CityCard = ({ city }) => {
  return (
    <div className="max-w-sm mx-auto bg-white rounded-lg shadow-lg p-6 flex flex-col items-center transition-transform transform hover:scale-105 my-4">
      <h3 className="text-3xl font-bold mb-2 text-center text-gray-800">{city.CityName}</h3>
      <p className="text-lg text-gray-700 mb-1 font-medium">Sunrise: <span className="text-purple-600">{new Date(city.sunrise).toLocaleTimeString()}</span></p>
      <p className="text-lg text-gray-700 mb-1 font-medium">Sunset: <span className="text-purple-600">{new Date(city.sunset).toLocaleTimeString()}</span></p>
      <p className="text-lg text-gray-700 mb-4 font-medium">Date: <span className="text-purple-600">{new Date(city.date).toLocaleDateString()}</span></p>
    </div>
  );
};

export default CityCard;
