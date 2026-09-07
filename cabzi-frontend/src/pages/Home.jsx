import { useContext, useState } from "react";
import BookingModal from "../components/BookingModal";

import { useNavigate } from "react-router-dom";
import { ThemeContext } from "../context/ThemeContext";

function Home() {

  const { darkMode } =
    useContext(ThemeContext);

    const navigate =
  useNavigate();

const user =
  JSON.parse(
    localStorage.getItem("user")
  );

const [showBookingModal,
  setShowBookingModal] =
  useState(false);

  const [pickup,
  setPickup] =
  useState("");

const [drop,
  setDrop] =
  useState("");

const [pickupSuggestions,
  setPickupSuggestions] =
  useState([]);

const [dropSuggestions,
  setDropSuggestions] =
  useState([]);

  const searchLocation =
  async (
    query,
    type
  ) => {

    if (!query) {

  if (type === "pickup") {

    setPickupSuggestions([]);

  } else {

    setDropSuggestions([]);
  }

  return;
}
    try {

      const response =
        await fetch(
`https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(query)}`

        );

      const data =
        await response.json();

      if (type === "pickup") {

        setPickupSuggestions(data);

      } else {

        setDropSuggestions(data);
      }

    } catch (error) {

      console.log(error);
    }
  };

const handleBooking = () => {

  if (!user) {

    navigate("/login");

    return;
  }

  setShowBookingModal(true);
};

const useCurrentLocation = () => {

  if (!navigator.geolocation) {

    setPickup("Current location not supported");

    return;
  }

  navigator.geolocation.getCurrentPosition(
    (position) => {

      setPickup(
        `Current Location (${position.coords.latitude.toFixed(4)}, ${position.coords.longitude.toFixed(4)})`
      );
    },
    () => {

      setPickup("Unable to fetch current location");
    }
  );
};

  return (

    <div
      className={`overflow-hidden transition-all duration-300 ${
        darkMode
          ? "bg-[#111111] text-white"
          : "bg-white text-black"
      }`}
    >

      {/* HERO SECTION */}

      <div className="max-w-[1350px] mx-auto px-3 py-3 grid lg:grid-cols-2 gap-6 items-center min-h-[78vh]">

        {/* LEFT SIDE */}

        <div>

          {/* TAG */}

          <div
            className={`inline-flex items-center gap-2 px-4 py-2 rounded-full border mb-4 text-sm ${
              darkMode
                ? "border-orange-500 text-orange-400 bg-black"
                : "border-orange-400 text-orange-600 bg-orange-50"
            }`}
          >

            ✨ Smart Rides. Better Journeys.

          </div>

          {/* HEADING */}

          <h1 className="text-4xl md:text-5xl font-black leading-tight">

            {user ? "Find Your" : "India's Most"}

            <span className="block text-orange-400">

              {user ? "Ride" : "Trusted Ride"}

            </span>

            {user ? "Now" : "Platform"}

          </h1>

          {/* DESCRIPTION */}

          <p
            className={`mt-4 text-base leading-7 max-w-lg ${
              darkMode
                ? "text-gray-300"
                : "text-gray-700"
            }`}
          >

            Book cabs, autos, bikes and
            parcel services in just a few
            taps. Safe, reliable and always
            on time.

          </p>

          {/* BOOKING CARD */}

<div
  className={`mt-5 p-5 rounded-[28px] border space-y-4 shadow-2xl ${
    darkMode
      ? "bg-black border-gray-800"
      : "bg-gray-100 border-gray-300"
  }`}
>

  {/* CURRENT LOCATION */}

  <button
    onClick={useCurrentLocation}
    className={`w-full p-3 rounded-2xl text-left font-bold transition ${
      darkMode
        ? "bg-[#1e1e1e] hover:bg-[#2a2a2a]"
        : "bg-white hover:bg-gray-200 border border-gray-300"
    }`}
  >

    📍 Use Current Location

  </button>

 {/* PICKUP */}

<div className="relative">

  <input
    type="text"
    placeholder="Enter Pickup Location"
    value={pickup}
    onChange={(e) => {

      setPickup(e.target.value);

      searchLocation(
        e.target.value,
        "pickup"
      );
    }}
    className={`w-full p-3 rounded-2xl outline-none ${
      darkMode
        ? "bg-[#1e1e1e] text-white"
        : "bg-white text-black border border-gray-300"
    }`}
  />

  {/* PICKUP SUGGESTIONS */}

  {pickupSuggestions.length > 0 && (

    <div
      className={`absolute top-full left-0 w-full mt-2 rounded-2xl overflow-hidden shadow-2xl z-20 max-h-60 overflow-y-auto ${
        darkMode
          ? "bg-black border border-gray-800"
          : "bg-white border border-gray-300"
      }`}
    >

      {pickupSuggestions.map(
        (place, index) => (

          <div
            key={index}
            onClick={() => {

              setPickup(
                place.display_name
              );

              setPickupSuggestions([]);
            }}
            className="p-3 hover:bg-orange-500/20 cursor-pointer transition"
          >

            📍 {place.display_name}

          </div>

        )
      )}

    </div>

  )}

</div>
{/* DROP */}

<div className="relative">

  <input
    type="text"
    placeholder="Enter Drop Location"
    value={drop}
    onChange={(e) => {

      setDrop(e.target.value);

      searchLocation(
        e.target.value,
        "drop"
      );
    }}
    className={`w-full p-3 rounded-2xl outline-none ${
      darkMode
        ? "bg-[#1e1e1e] text-white"
        : "bg-white text-black border border-gray-300"
    }`}
  />

  {/* DROP SUGGESTIONS */}

  {dropSuggestions.length > 0 && (

    <div
      className={`absolute top-full left-0 w-full mt-2 rounded-2xl overflow-hidden shadow-2xl z-10 max-h-60 overflow-y-auto ${
        darkMode
          ? "bg-black border border-gray-800"
          : "bg-white border border-gray-300"
      }`}
    >

      {dropSuggestions.map(
        (place, index) => (

          <div
            key={index}
            onClick={() => {

              setDrop(
                place.display_name
              );

              setDropSuggestions([]);
            }}
            className="p-3 hover:bg-orange-500/20 cursor-pointer transition"
          >

            📍 {place.display_name}

          </div>

        )
      )}

    </div>

  )}

</div>

  {/* BUTTON */}

  <button
    onClick={handleBooking}
    className="w-full bg-orange-500 hover:bg-orange-400 transition py-3 rounded-2xl text-black font-black text-lg"
  >

    Book Ride Now →

  </button>

</div>


          {/* FEATURES */}

          <div className="flex flex-wrap gap-5 mt-5">

            <div>

              <h3 className="font-bold text-orange-400 text-sm">

                🛡 Safe & Secure

              </h3>

              <p className="text-xs text-gray-500 mt-1">

                Your safety is our priority

              </p>

            </div>

            <div>

              <h3 className="font-bold text-orange-400 text-sm">

                ₹ Best Prices

              </h3>

              <p className="text-xs text-gray-500 mt-1">

                Affordable for everyone

              </p>

            </div>

            <div>

              <h3 className="font-bold text-orange-400 text-sm">

                ⏰ On-Time Rides

              </h3>

              <p className="text-xs text-gray-500 mt-1">

                Always on time

              </p>

            </div>

          </div>

        </div>

        {/* RIGHT SIDE */}

        <div className="relative">

          {/* IMAGE */}

          <div
            className="overflow-hidden shadow-2xl bg-black"
            style={{
              borderRadius:
                "110px 25px 25px 110px"
            }}
          >

            {/* OVERLAY */}

            <div className="absolute inset-0 bg-gradient-to-tr from-orange-500/10 to-transparent z-10"></div>

            {/* IMAGE */}

            <img
              src="https://images.unsplash.com/photo-1519003722824-194d4455a60c?q=80&w=1600&auto=format&fit=crop"
              alt="CABZI"
              className="w-full h-[390px] object-cover"
            />

          </div>

          {/* FLOATING STATS */}

          <div
            className={`grid grid-cols-2 md:grid-cols-4 gap-3 p-4 relative z-20 -mt-8 ml-3 shadow-2xl ${
              darkMode
                ? "bg-black border border-gray-800"
                : "bg-gray-100 border border-gray-300"
            }`}
            style={{
              borderRadius:
                "55px 20px 20px 20px"
            }}
          >

            {/* STAT */}

            <div className="text-center">

              <h2 className="text-xl font-black text-orange-400">

                10M+

              </h2>

              <p className="mt-1 text-gray-500 text-[11px]">

                Happy Users

              </p>

            </div>

            {/* STAT */}

            <div className="text-center">

              <h2 className="text-xl font-black text-orange-400">

                50M+

              </h2>

              <p className="mt-1 text-gray-500 text-[11px]">

                Rides

              </p>

            </div>

            {/* STAT */}

            <div className="text-center">

              <h2 className="text-xl font-black text-orange-400">

                500+

              </h2>

              <p className="mt-1 text-gray-500 text-[11px]">

                Cities

              </p>

            </div>

            {/* STAT */}

            <div className="text-center">

              <h2 className="text-xl font-black text-orange-400">

                4.8★

              </h2>

              <p className="mt-1 text-gray-500 text-[11px]">

                Rating

              </p>

            </div>

          </div>

        </div>

      </div>

      {/* SERVICES SECTION */}

<div className="max-w-[1350px] mx-auto px-3 py-10">

  {/* HEADING */}

  <h1 className="text-4xl md:text-5xl font-black mb-10">

    Explore CABZI Services

  </h1>

  {/* GRID */}

  <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">

    {/* CARD 1 */}

    <div
      className={`p-6 rounded-[30px] transition hover:scale-[1.02] ${
        darkMode
          ? "bg-black border border-gray-800"
          : "bg-gray-100 border border-gray-300"
      }`}
    >

      <div className="flex justify-between items-center">

        <div>

          <h2 className="text-3xl font-black text-orange-400">

            Cab Ride

          </h2>

          <p
            className={`mt-4 leading-7 ${
              darkMode
                ? "text-gray-400"
                : "text-gray-600"
            }`}
          >

            Comfortable city rides with
            affordable pricing and fast
            pickup.

          </p>

        </div>

        <img
          src="https://cdn-icons-png.flaticon.com/512/744/744465.png"
          alt="Cab"
          className="w-28"
        />

      </div>

    </div>

    {/* CARD 2 */}

    <div
      className={`p-6 rounded-[30px] transition hover:scale-[1.02] ${
        darkMode
          ? "bg-black border border-gray-800"
          : "bg-gray-100 border border-gray-300"
      }`}
    >

      <div className="flex justify-between items-center">

        <div>

          <h2 className="text-3xl font-black text-orange-400">

            Bike Taxi

          </h2>

          <p
            className={`mt-4 leading-7 ${
              darkMode
                ? "text-gray-400"
                : "text-gray-600"
            }`}
          >

            Beat traffic with ultra-fast
            and budget-friendly bike rides.

          </p>

        </div>

        <img
          src="https://cdn-icons-png.flaticon.com/512/2972/2972185.png"
          alt="Bike"
          className="w-28"
        />

      </div>

    </div>

    {/* CARD 3 */}

    <div
      className={`p-6 rounded-[30px] transition hover:scale-[1.02] ${
        darkMode
          ? "bg-black border border-gray-800"
          : "bg-gray-100 border border-gray-300"
      }`}
    >

      <div className="flex justify-between items-center">

        <div>

          <h2 className="text-3xl font-black text-orange-400">

            Auto Ride

          </h2>

          <p
            className={`mt-4 leading-7 ${
              darkMode
                ? "text-gray-400"
                : "text-gray-600"
            }`}
          >

            Convenient auto rides for
            daily travel and short routes.

          </p>

        </div>

        <img
          src="https://cdn-icons-png.flaticon.com/512/3097/3097183.png"
          alt="Auto"
          className="w-28"
        />

      </div>

    </div>

    {/* CARD 4 */}

    <div
      className={`p-6 rounded-[30px] transition hover:scale-[1.02] ${
        darkMode
          ? "bg-black border border-gray-800"
          : "bg-gray-100 border border-gray-300"
      }`}
    >

      <div className="flex justify-between items-center">

        <div>

          <h2 className="text-3xl font-black text-orange-400">

            Parcel Delivery

          </h2>

          <p
            className={`mt-4 leading-7 ${
              darkMode
                ? "text-gray-400"
                : "text-gray-600"
            }`}
          >

            Send packages quickly and
            safely anywhere in the city.

          </p>

        </div>

        <img
          src="https://cdn-icons-png.flaticon.com/512/1046/1046857.png"
          alt="Parcel"
          className="w-28"
        />

      </div>

    </div>

    {/* CARD 5 */}

    <div
      className={`p-6 rounded-[30px] transition hover:scale-[1.02] ${
        darkMode
          ? "bg-black border border-gray-800"
          : "bg-gray-100 border border-gray-300"
      }`}
    >

      <div className="flex justify-between items-center">

        <div>

          <h2 className="text-3xl font-black text-orange-400">

            Ride Sharing

          </h2>

          <p
            className={`mt-4 leading-7 ${
              darkMode
                ? "text-gray-400"
                : "text-gray-600"
            }`}
          >

            Share rides with nearby users
            and reduce travel costs.

          </p>

        </div>

        <img
          src="https://cdn-icons-png.flaticon.com/512/854/854878.png"
          alt="Sharing"
          className="w-28"
        />

      </div>

    </div>

    {/* CARD 6 */}

    <div
      className={`p-6 rounded-[30px] transition hover:scale-[1.02] ${
        darkMode
          ? "bg-black border border-gray-800"
          : "bg-gray-100 border border-gray-300"
      }`}
    >

      <div className="flex justify-between items-center">

        <div>

          <h2 className="text-3xl font-black text-orange-400">

            Intercity Travel

          </h2>

          <p
            className={`mt-4 leading-7 ${
              darkMode
                ? "text-gray-400"
                : "text-gray-600"
            }`}
          >

            Long-distance rides with
            comfort, safety and reliability.

          </p>

        </div>

        <img
          src="https://cdn-icons-png.flaticon.com/512/3448/3448339.png"
          alt="Intercity"
          className="w-28"
        />

      </div>

    </div>

  </div>

</div>

{/* PROMOTION SECTION */}

<div className="max-w-[1350px] mx-auto px-3 py-16 space-y-10">

  {/* POSTER 1 */}

  <div
    className={`grid lg:grid-cols-2 gap-8 items-center overflow-hidden rounded-[40px] ${
      darkMode
        ? "bg-black border border-gray-800"
        : "bg-gray-100 border border-gray-300"
    }`}
  >

    {/* LEFT */}

    <div className="p-10">

      <h1 className="text-5xl font-black leading-tight">

        Ride Smarter With

        <span className="block text-orange-400">

          CABZI Prime

        </span>

      </h1>

      <p
        className={`mt-6 text-lg leading-8 ${
          darkMode
            ? "text-gray-400"
            : "text-gray-600"
        }`}
      >

        Experience premium rides,
        priority bookings and faster
        pickups with CABZI Prime.

      </p>

      <button
        onClick={() => navigate("/subscription")}
        className="mt-8 bg-orange-500 hover:bg-orange-400 transition px-8 py-4 rounded-2xl text-black font-black text-lg"
      >

        Explore Prime

      </button>

    </div>

    {/* RIGHT */}

    <div>

      <img
        src="https://images.unsplash.com/photo-1503376780353-7e6692767b70?q=80&w=1600&auto=format&fit=crop"
        alt="Prime"
        className="w-full h-[420px] object-cover"
      />

    </div>

  </div>

  {/* POSTER 2 */}

  <div
    className={`grid lg:grid-cols-2 gap-8 items-center overflow-hidden rounded-[40px] ${
      darkMode
        ? "bg-black border border-gray-800"
        : "bg-gray-100 border border-gray-300"
    }`}
  >

    {/* IMAGE */}

    <div className="order-2 lg:order-1">

      <img
        src="https://images.unsplash.com/photo-1516321318423-f06f85e504b3?q=80&w=1600&auto=format&fit=crop"
        alt="Parcel"
        className="w-full h-[420px] object-cover"
      />

    </div>

    {/* TEXT */}

    <div className="p-10 order-1 lg:order-2">

      <h1 className="text-5xl font-black leading-tight">

        Fast & Secure

        <span className="block text-orange-400">

          Parcel Delivery

        </span>

      </h1>

      <p
        className={`mt-6 text-lg leading-8 ${
          darkMode
            ? "text-gray-400"
            : "text-gray-600"
        }`}
      >

        Deliver packages anywhere
        in the city with live tracking
        and instant support.

      </p>

      <button
        onClick={() => navigate("/payment")}
        className="mt-8 bg-orange-500 hover:bg-orange-400 transition px-8 py-4 rounded-2xl text-black font-black text-lg"
      >

        Send Parcel

      </button>

    </div>

  </div>

  {/* POSTER 3 */}

  <div
    className={`grid lg:grid-cols-2 gap-8 items-center overflow-hidden rounded-[40px] ${
      darkMode
        ? "bg-black border border-gray-800"
        : "bg-gray-100 border border-gray-300"
    }`}
  >

    {/* LEFT */}

    <div className="p-10">

      <h1 className="text-5xl font-black leading-tight">

        Save More With

        <span className="block text-orange-400">

          Ride Sharing

        </span>

      </h1>

      <p
        className={`mt-6 text-lg leading-8 ${
          darkMode
            ? "text-gray-400"
            : "text-gray-600"
        }`}
      >

        Share rides with nearby
        passengers and reduce your
        travel expenses smartly.

      </p>

      <button
        onClick={() => navigate("/share-ride")}
        className="mt-8 bg-orange-500 hover:bg-orange-400 transition px-8 py-4 rounded-2xl text-black font-black text-lg"
      >

        Start Sharing

      </button>

    </div>

    {/* RIGHT */}

    <div>

      <img
        src="https://images.unsplash.com/photo-1494976388531-d1058494cdd8?q=80&w=1600&auto=format&fit=crop"
        alt="Sharing"
        className="w-full h-[420px] object-cover"
      />

    </div>

  </div>

</div>

{/* BOOKING MODAL */}

{showBookingModal && (

  <BookingModal
    darkMode={darkMode}
    onClose={() =>
      setShowBookingModal(false)
    }
  />

)}

    </div>

  );
}

export default Home;
