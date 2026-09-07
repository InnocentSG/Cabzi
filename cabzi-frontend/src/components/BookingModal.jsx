import { useState } from "react";

function BookingModal({
  darkMode,
  onClose
}) {

  const [activeTab,
    setActiveTab] =
    useState("ride");

    const [rideState,
  setRideState] =
  useState("select");

const [selectedRide,
  setSelectedRide] =
  useState(null);

const [fareBoost,
  setFareBoost] =
  useState(0);

const [otp] =
  useState(() =>

    Math.floor(
      1000 + Math.random() * 9000
    )

  );

  return (

    <div className="fixed inset-0 z-50 bg-black/60 flex justify-center items-center px-4">

      {/* MODAL */}

      <div
        className={`w-full max-w-6xl h-[85vh] rounded-[40px] overflow-hidden shadow-2xl ${
          darkMode
            ? "bg-[#111111] text-white"
            : "bg-white text-black"
        }`}
      >

        <div className="grid lg:grid-cols-2 h-full">

          {/* LEFT SIDE MAP */}

          <div className="relative h-full">

            {/* FAKE MAP */}

            <img
              src="https://images.unsplash.com/photo-1524661135-423995f22d0b?q=80&w=1600&auto=format&fit=crop"
              alt="Map"
              className="w-full h-full object-cover"
            />

            {/* MAP OVERLAY */}

            <div className="absolute top-5 left-5 bg-black/70 backdrop-blur-md px-5 py-3 rounded-2xl">

              <h2 className="text-xl font-black text-orange-400">

                Live Route Preview

              </h2>

              <p className="text-sm text-gray-300 mt-1">

                Pickup → Destination

              </p>

            </div>

          </div>

          {/* RIGHT SIDE */}

          <div className="p-6 overflow-y-auto">

            {/* TOP */}

            <div className="flex justify-between items-center mb-6">

              <h1 className="text-4xl font-black">

                Choose Service

              </h1>

              <button
                onClick={onClose}
                className="bg-red-500 hover:bg-red-600 transition px-4 py-2 rounded-xl text-white font-bold"
              >

                ✕

              </button>

            </div>

            {/* TABS */}

            <div
              className={`flex p-2 rounded-2xl mb-8 ${
                darkMode
                  ? "bg-black"
                  : "bg-gray-200"
              }`}
            >

              {/* RIDE */}

              <button
                onClick={() =>
                  setActiveTab("ride")
                }
                className={`flex-1 py-3 rounded-xl font-black transition ${
                  activeTab === "ride"
                    ? "bg-orange-500 text-black"
                    : ""
                }`}
              >

                Ride

              </button>

              {/* PARCEL */}

              <button
                onClick={() =>
                  setActiveTab("parcel")
                }
                className={`flex-1 py-3 rounded-xl font-black transition ${
                  activeTab === "parcel"
                    ? "bg-orange-500 text-black"
                    : ""
                }`}
              >

                Parcel

              </button>

            </div>

            {/* SEARCHING DRIVER */}

{rideState === "searching" && (

  <div className="flex flex-col justify-center items-center h-[70vh] text-center">

    {/* LOADER */}

    <div className="w-24 h-24 border-8 border-orange-500 border-t-transparent rounded-full animate-spin"></div>

    <h1 className="text-4xl font-black mt-10">

      Searching Driver...

    </h1>

    <p className="text-gray-500 mt-4 text-lg">

      Waiting for nearby driver acceptance
      {selectedRide ? ` for ${selectedRide}` : ""}

    </p>

    <button
      onClick={() =>
        setFareBoost(fareBoost + 20)
      }
      className="mt-10 bg-orange-500 hover:bg-orange-400 transition px-8 py-4 rounded-2xl text-black font-black"
    >

      Increase Fare +₹20
      {fareBoost > 0 ? ` (Added ₹${fareBoost})` : ""}

    </button>

  </div>

)}


{/* DRIVER ACCEPTED */}

{rideState === "accepted" && (

  <div className="space-y-6">

    {/* DRIVER CARD */}

    <div
      className={`p-6 rounded-3xl ${
        darkMode
          ? "bg-black border border-gray-800"
          : "bg-gray-100 border border-gray-300"
      }`}
    >

      <div className="flex justify-between items-center">

        <div>

          <h1 className="text-3xl font-black text-orange-400">

            Driver Accepted

          </h1>

          <p className="mt-2 text-gray-500">

            Driver arriving in 4 mins
            {selectedRide ? ` for your ${selectedRide}` : ""}

          </p>

        </div>

        <img
          src="https://cdn-icons-png.flaticon.com/512/3135/3135715.png"
          alt="Driver"
          className="w-20 h-20 rounded-full"
        />

      </div>

    </div>

    {/* OTP */}

    <div
      className={`p-6 rounded-3xl text-center ${
        darkMode
          ? "bg-black border border-gray-800"
          : "bg-gray-100 border border-gray-300"
      }`}
    >

      <h2 className="text-xl font-bold text-gray-500">

        Your Ride OTP

      </h2>

      <h1 className="text-7xl font-black text-orange-400 mt-4">

        {otp}

      </h1>

    </div>

    {/* ACTIONS */}

    <div className="grid grid-cols-3 gap-4">

      <button
        onClick={() => {
          window.location.href = "tel:+911234567890";
        }}
        className="bg-orange-500 hover:bg-orange-400 transition py-4 rounded-2xl text-black font-black"
      >

        📞 Call

      </button>

      <button
        onClick={() => {
          window.location.href = "/chat";
        }}
        className="bg-orange-500 hover:bg-orange-400 transition py-4 rounded-2xl text-black font-black"
      >

        💬 Chat

      </button>

      <button
        onClick={() => {
          setSelectedRide(null);
          setRideState("select");
        }}
        className="bg-red-500 hover:bg-red-600 transition py-4 rounded-2xl text-white font-black"
      >

        Cancel

      </button>

    </div>

  </div>

)}

            {/* RIDE OPTIONS */}

           {activeTab === "ride"
&& rideState === "select" && (

              <div className="space-y-4">

                {/* BIKE */}

                <div
                  className={`p-5 rounded-3xl border flex justify-between items-center ${
                    darkMode
                      ? "bg-black border-gray-800"
                      : "bg-gray-100 border-gray-300"
                  }`}
                >

                  <div>

                    <h2 className="text-2xl font-black text-orange-400">

                      Bike

                    </h2>

                    <p className="text-gray-500 mt-1">

                      2 mins away • 1 seat

                    </p>

                    <h3 className="mt-3 text-2xl font-black">

                      ₹79

                    </h3>

                  </div>

                  <button
  onClick={() => {

    setSelectedRide("Bike");

    setRideState(
      "searching"
    );

    setTimeout(() => {

      setRideState(
        "accepted"
      );

    }, 5000);

  }}
  className="bg-orange-500 hover:bg-orange-400 transition px-6 py-3 rounded-2xl text-black font-black"
>

  Select

</button>

                </div>

                {/* AUTO */}

                <div
                  className={`p-5 rounded-3xl border flex justify-between items-center ${
                    darkMode
                      ? "bg-black border-gray-800"
                      : "bg-gray-100 border-gray-300"
                  }`}
                >

                  <div>

                    <h2 className="text-2xl font-black text-orange-400">

                      Auto

                    </h2>

                    <p className="text-gray-500 mt-1">

                      3 mins away • 3 seats

                    </p>

                    <h3 className="mt-3 text-2xl font-black">

                      ₹120

                    </h3>

                  </div>

                  <button
                    onClick={() => {

                      setSelectedRide("Auto");

                      setRideState("searching");

                      setTimeout(() => {

                        setRideState("accepted");

                      }, 5000);
                    }}
                    className="bg-orange-500 hover:bg-orange-400 transition px-6 py-3 rounded-2xl text-black font-black"
                  >

                    Select

                  </button>

                </div>

                {/* CAB */}

                <div
                  className={`p-5 rounded-3xl border flex justify-between items-center ${
                    darkMode
                      ? "bg-black border-gray-800"
                      : "bg-gray-100 border-gray-300"
                  }`}
                >

                  <div>

                    <h2 className="text-2xl font-black text-orange-400">

                      Cab

                    </h2>

                    <p className="text-gray-500 mt-1">

                      5 mins away • 4 seats

                    </p>

                    <h3 className="mt-3 text-2xl font-black">

                      ₹220

                    </h3>

                  </div>

                  <button
                    onClick={() => {

                      setSelectedRide("Cab");

                      setRideState("searching");

                      setTimeout(() => {

                        setRideState("accepted");

                      }, 5000);
                    }}
                    className="bg-orange-500 hover:bg-orange-400 transition px-6 py-3 rounded-2xl text-black font-black"
                  >

                    Select

                  </button>

                </div>

                {/* PREMIUM */}

                <div
                  className={`p-5 rounded-3xl border flex justify-between items-center ${
                    darkMode
                      ? "bg-black border-gray-800"
                      : "bg-gray-100 border-gray-300"
                  }`}
                >

                  <div>

                    <h2 className="text-2xl font-black text-orange-400">

                      Premium

                    </h2>

                    <p className="text-gray-500 mt-1">

                      Luxury ride • 4 seats

                    </p>

                    <h3 className="mt-3 text-2xl font-black">

                      ₹399

                    </h3>

                  </div>

                  <button
                    onClick={() => {

                      setSelectedRide("Premium");

                      setRideState("searching");

                      setTimeout(() => {

                        setRideState("accepted");

                      }, 5000);
                    }}
                    className="bg-orange-500 hover:bg-orange-400 transition px-6 py-3 rounded-2xl text-black font-black"
                  >

                    Select

                  </button>

                </div>

              </div>

            )}

            {/* PARCEL OPTIONS */}

            {activeTab === "parcel" && (

              <div className="space-y-4">

                {/* BIKE DELIVERY */}

                <div
                  className={`p-5 rounded-3xl border flex justify-between items-center ${
                    darkMode
                      ? "bg-black border-gray-800"
                      : "bg-gray-100 border-gray-300"
                  }`}
                >

                  <div>

                    <h2 className="text-2xl font-black text-orange-400">

                      Bike Delivery

                    </h2>

                    <p className="text-gray-500 mt-1">

                      Small parcels • Fast

                    </p>

                    <h3 className="mt-3 text-2xl font-black">

                      ₹99

                    </h3>

                  </div>

                  <button
                    onClick={() => {

                      setSelectedRide("Bike Delivery");

                      setRideState("searching");

                      setTimeout(() => {

                        setRideState("accepted");

                      }, 5000);
                    }}
                    className="bg-orange-500 hover:bg-orange-400 transition px-6 py-3 rounded-2xl text-black font-black"
                  >

                    Select

                  </button>

                </div>

                {/* MINI TRUCK */}

                <div
                  className={`p-5 rounded-3xl border flex justify-between items-center ${
                    darkMode
                      ? "bg-black border-gray-800"
                      : "bg-gray-100 border-gray-300"
                  }`}
                >

                  <div>

                    <h2 className="text-2xl font-black text-orange-400">

                      Mini Truck

                    </h2>

                    <p className="text-gray-500 mt-1">

                      Heavy items • Safe transport

                    </p>

                    <h3 className="mt-3 text-2xl font-black">

                      ₹499

                    </h3>

                  </div>

                  <button
                    onClick={() => {

                      setSelectedRide("Mini Truck");

                      setRideState("searching");

                      setTimeout(() => {

                        setRideState("accepted");

                      }, 5000);
                    }}
                    className="bg-orange-500 hover:bg-orange-400 transition px-6 py-3 rounded-2xl text-black font-black"
                  >

                    Select

                  </button>

                </div>

              </div>

            )}

          </div>

        </div>

      </div>

    </div>
  );
}

export default BookingModal;
