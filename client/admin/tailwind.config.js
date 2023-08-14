/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,ts}"],

  theme: {
    // colors: {
    //   white: "#ffffff",
    //   blue: "#3F51B5",
    // },
    fontFamily: {
      poppins: "Poppins Regular,sans-serif",
      poppins_italic: "Poppins Italic,sans-serif",
      poppins_thin: "Poppins Thin,sans-serif",
      poppins_thin_italic: "Poppins Thin Italic,sans-serif",
      poppins_extra_light: "Poppins ExtraLight,sans-serif",
      poppins_extra_italic: "Poppins ExtraItalic,sans-serif",
      poppins_light: "Poppins Light,sans-serif",
      poppins_light_italic: "Poppins Light Italic,sans-serif",
      poppins_medium: "Poppins Medium,sans-serif",
      poppins_medium_italic: "Poppins Medium Italic,sans-serif",
      poppins_semi_bold: "Poppins SemiBold,sans-serif",
      poppins_semi_bold_italic: "Poppins SemiBold Italic,sans-serif",
      poppins_bold: "Poppins Bold,sans-serif",
      poppins_bold_italic: "Poppins Bold Italic,sans-serif",
      poppins_extra_bold: "Poppins ExtraBold,sans-serif",
      poppins_extra_bold_italic: "Poppins ExtraBold Italic,sans-serif",
      poppins_black: "Poppins Black,sans-serif",
      poppins_black_italic: "Poppins Black Italic,sans-serif",
      roboto: "Roboto Regular,sans-serif",
    },
    extend: {},
  },
  plugins: [
    require("@tailwindcss/forms"),
    require("tailwindcss"),
    require("autoprefixer"),
  ],
};
