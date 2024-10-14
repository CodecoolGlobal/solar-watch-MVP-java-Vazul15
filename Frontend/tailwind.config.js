/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {},
  },
  daisyui: {
    themes: [
      {
        mytheme: {
          
"primary": "#ffeff7",
          
"secondary": "#3e8873",
          
"accent": "#00ffff",
          
"neutral": "#D0FFF0",
          
"base-100": "#ffffff",
          
"info": "#0000ff",
          
"success": "#00ff00",
          
"warning": "#ffcc00",
"base-100": "#4a0b8a",
          
"error": "#ff0000",
          },
        },
      ],
    },
  plugins: [require("daisyui")],
}

