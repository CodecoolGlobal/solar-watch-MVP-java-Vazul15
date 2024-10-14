import React, { useState } from "react";

const CityForm = ({ onSave }) => {
  const [formData, setFormData] = useState({
    name: "",
    country: "",
    state: "",
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const onSubmit = (e) => {
    e.preventDefault();
    onSave(formData);
  };

  return (
    <>
      <div className="absolute inset-0 bg-black opacity-50"></div>
      <div className="relative min-h-screen flex justify-center items-center z-10">
        <div className="bg-white p-10 rounded-3xl w-full max-w-md shadow-lg">
          <div className="mb-7">
            <h3 className="font-semibold text-2xl text-gray-800">Create a City</h3>
            <p className="text-gray-400">Please enter the details to add a new city.</p>
          </div>
          <form onSubmit={onSubmit} className="space-y-6">
            <div className="flex flex-col mb-4">
              <label className="text-gray-800">City Name</label>
              <input
                type="text"
                name="name"
                placeholder="Enter city name"
                value={formData.name}
                onChange={handleInputChange}
                className="mt-2 px-4 py-3 bg-gray-200 focus:bg-gray-100 border border-gray-200 rounded-lg focus:outline-none focus:border-purple-400"
                required
              />
            </div>
            <div className="flex flex-col mb-4">
              <label className="text-gray-800">Country Code</label>
              <input
                type="text"
                name="country"
                placeholder="Enter country code"
                value={formData.country}
                onChange={handleInputChange}
                className="mt-2 px-4 py-3 bg-gray-200 focus:bg-gray-100 border border-gray-200 rounded-lg focus:outline-none focus:border-purple-400"
                required
              />
            </div>
            <div className="flex flex-col mb-4">
              <label className="text-gray-800">State (optional)</label>
              <input
                type="text"
                name="state"
                placeholder="Enter state (if applicable)"
                value={formData.state}
                onChange={handleInputChange}
                className="mt-2 px-4 py-3 bg-gray-200 focus:bg-gray-100 border border-gray-200 rounded-lg focus:outline-none focus:border-purple-400"
              />
            </div>
            <div className="mt-6 flex gap-4">
              <button
                type="submit"
                className="w-full flex justify-center bg-purple-800 hover:bg-purple-700 text-gray-100 p-3 rounded-lg tracking-wide font-semibold cursor-pointer transition ease-in duration-500"
              >
                Add an existing City
              </button>
            </div>
          </form>
        </div>
      </div>
    </>
  );
};

export default CityForm;
