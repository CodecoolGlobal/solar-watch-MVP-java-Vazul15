import { useState } from "react";

export const RegisterForm = ({ onSave }) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const onSubmit = (e) => {
    e.preventDefault();
    onSave({
      email,
      password,
    });
  };

  return (
    <>
      <link href="https://unpkg.com/tailwindcss@^2/dist/tailwind.min.css" rel="stylesheet" />
      <div className="bg-purple-900 absolute bg-gradient-to-b from-gray-900 via-gray-900 to-purple-800 leading-5 h-full w-full overflow-hidden"></div>
      <div className="relative min-h-screen sm:flex sm:flex-row justify-center bg-transparent rounded-3xl shadow-xl">
        <div className="flex-col flex self-center lg:px-14 sm:max-w-4xl xl:max-w-md z-10">
          <div className="self-start hidden lg:flex flex-col text-gray-300">
            <h1 className="my-3 font-semibold text-4xl">Welcome to the Weather App!</h1>
            <p className="pr-3 text-sm opacity-75">You can start your journey and explore the latest weather forecasts. Please register to gain full access to our services and receive personalized weather alerts for your location. Join our community and never miss out on the latest weather information!</p>
          </div>
        </div>
        <div className="flex justify-center self-center z-10">
          <div className="p-12 bg-white mx-auto rounded-3xl w-96">
            <div className="mb-7">
              <h3 className="font-semibold text-2xl text-gray-800">Sign up</h3>
              <p className="text-gray-400">Already have an account? <a href="#" className="text-sm text-purple-700 hover:text-purple-700">Sign In</a></p>
            </div>
            <form onSubmit={onSubmit} className="space-y-6">
              <div>
                <input
                  className="w-full text-sm px-4 py-3 bg-gray-200 focus:bg-gray-100 border border-gray-200 rounded-lg focus:outline-none focus:border-purple-400"
                  type="email"
                  placeholder="Email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                />
              </div>
              <div className="relative">
                <input
                  placeholder="Password"
                  type="password"
                  className="text-sm text-gray-800 px-4 py-3 rounded-lg w-full bg-gray-200 focus:bg-gray-100 border border-gray-200 focus:outline-none focus:border-purple-400"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
              </div>
              <div className="text-sm ml-auto">
                <a href="#" className="text-purple-700 hover:text-purple-600">Forgot your password?</a>
              </div>
              <button
                type="submit"
                className="w-full flex justify-center bg-purple-800 hover:bg-purple-700 text-gray-100 p-3 rounded-lg tracking-wide font-semibold cursor-pointer transition ease-in duration-500"
              >
                Sign up
              </button>
            </form>
          </div>
        </div>
      </div>
    </>
  );
};

export default RegisterForm;
