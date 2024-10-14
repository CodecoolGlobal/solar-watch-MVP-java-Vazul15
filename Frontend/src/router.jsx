import Main from "./pages/Main";
import { createBrowserRouter } from "react-router-dom";
import Login from "./components/Login";
import Register from "./components/Register";
import CityInformation from './pages/CityInformation'

export const router = createBrowserRouter([
  {
    path: "/",
    element: <Main />,
    children: [
      {
        path: "register",
        element: <Register />,
      },
      {
        path: "login",
        element: <Login />,
      },
    ],
  },
  {
    path: "city/:cityName/:countryCode/:stateCode?",
    element: <CityInformation />
  },
]);
