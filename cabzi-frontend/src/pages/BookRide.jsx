import {
  useEffect,
  useState,
} from "react";

import {
  useNavigate,
} from "react-router-dom";

import Map from "../components/Map";

import {
  rideApi,
} from "../services/backend";


function BookRide() {

  const navigate =
    useNavigate();


  // ==============================
  // LOCATION TEXT
  // ==============================

  const [pickup, setPickup] =
    useState("");

  const [destination, setDestination] =
    useState("");


  // ==============================
  // COORDINATES
  // ==============================

  const [pickupLocation,
    setPickupLocation] =
    useState(null);

  const [destinationLocation,
    setDestinationLocation] =
    useState(null);


  // ==============================
  // ROUTE
  // ==============================

  const [routeCoordinates,
    setRouteCoordinates] =
    useState([]);


  // ==============================
  // DISTANCE / ETA
  // ==============================

  const [distance,
    setDistance] =
    useState(null);

  const [duration,
    setDuration] =
    useState(null);


  // ==============================
  // VEHICLE
  // ==============================

  const [vehicleType,
    setVehicleType] =
    useState("CAB");


  // ==============================
  // FARE
  // ==============================

  const [fare,
    setFare] =
    useState(null);


  // ==============================
  // STATUS
  // ==============================

  const [loading,
    setLoading] =
    useState(false);

  const [message,
    setMessage] =
    useState("");


  // ==============================
  // GET CURRENT LOCATION
  // ==============================

  useEffect(() => {

    if (!navigator.geolocation) {

      setMessage(
        "GPS is not supported by your browser"
      );

      return;
    }


    navigator.geolocation.getCurrentPosition(

      async (position) => {

        const lat =
          position.coords.latitude;

        const lng =
          position.coords.longitude;


        setPickupLocation({
          lat,
          lng,
        });


        // Reverse geocode
        // current location
        try {

          const response =
            await fetch(
              `https://nominatim.openstreetmap.org/reverse?format=json&lat=${lat}&lon=${lng}&zoom=18&addressdetails=1`,
              {
                headers: {
                  Accept:
                    "application/json",
                },
              }
            );


          const data =
            await response.json();


          if (data.display_name) {

            setPickup(
              data.display_name
            );

          } else {

            setPickup(
              `${lat.toFixed(6)}, ${lng.toFixed(6)}`
            );

          }

        } catch (error) {

          console.error(
            "Reverse geocoding failed:",
            error
          );

          setPickup(
            `${lat.toFixed(6)}, ${lng.toFixed(6)}`
          );
        }

      },

      (error) => {

        console.error(
          "GPS error:",
          error
        );

        setMessage(
          "Please allow location access to use your current location"
        );
      },

      {
        enableHighAccuracy: true,
        timeout: 10000,
        maximumAge: 0,
      }

    );

  }, []);


  // ==============================
  // SEARCH LOCATION
  // ==============================

  const searchLocation =
    async (
      query
    ) => {

      if (!query.trim()) {
        return null;
      }


      try {

        const response =
          await fetch(
            `https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(query)}&limit=1&countrycodes=in`,
            {
              headers: {
                Accept:
                  "application/json",
              },
            }
          );


        const data =
          await response.json();


        if (
          !data ||
          data.length === 0
        ) {

          return null;
        }


        return {
          lat:
            parseFloat(data[0].lat),

          lng:
            parseFloat(data[0].lon),

          name:
            data[0].display_name,
        };

      } catch (error) {

        console.error(
          "Location search failed:",
          error
        );

        return null;
      }
    };


  // ==============================
  // CALCULATE ROUTE
  // ==============================

  const calculateRoute =
    async () => {

      setMessage("");
      setFare(null);
      setDistance(null);
      setDuration(null);
      setRouteCoordinates([]);


      if (!pickup.trim()) {

        setMessage(
          "Please enter pickup location"
        );

        return;
      }


      if (!destination.trim()) {

        setMessage(
          "Please enter destination"
        );

        return;
      }


      setLoading(true);


      try {

        // ==========================
        // PICKUP
        // ==========================

        let pickupCoords =
          pickupLocation;


        if (!pickupCoords) {

          pickupCoords =
            await searchLocation(
              pickup
            );

        }


        if (!pickupCoords) {

          setMessage(
            "Pickup location not found"
          );

          return;
        }


        // ==========================
        // DESTINATION
        // ==========================

        const destinationCoords =
          await searchLocation(
            destination
          );


        if (!destinationCoords) {

          setMessage(
            "Destination not found"
          );

          return;
        }


        setPickupLocation(
          pickupCoords
        );

        setDestinationLocation(
          destinationCoords
        );


        // ==========================
        // OSRM ROUTING
        // ==========================

        const url =
          `https://router.project-osrm.org/route/v1/driving/` +
          `${pickupCoords.lng},${pickupCoords.lat};` +
          `${destinationCoords.lng},${destinationCoords.lat}` +
          `?overview=full&geometries=geojson`;


        const response =
          await fetch(url);


        if (!response.ok) {

          throw new Error(
            "Routing service failed"
          );
        }


        const data =
          await response.json();


        if (
          data.code !== "Ok" ||
          !data.routes ||
          data.routes.length === 0
        ) {

          throw new Error(
            "Route not found"
          );
        }


        const route =
          data.routes[0];


        // ==========================
        // REAL ROAD DISTANCE
        // ==========================

        const distanceKm =
          route.distance / 1000;


        // ==========================
        // REAL ROAD TIME
        // ==========================

        const durationMinutes =
          Math.ceil(
            route.duration / 60
          );


        setDistance(
          distanceKm
        );

        setDuration(
          durationMinutes
        );


        // ==========================
        // ROUTE LINE
        // ==========================

        const coordinates =
          route.geometry.coordinates.map(
            ([lng, lat]) => [
              lat,
              lng,
            ]
          );


        setRouteCoordinates(
          coordinates
        );


        // ==========================
        // FARE
        // ==========================

        const fareResponse =
          await rideApi.estimateFare({

            pickup,

            destination,

            vehicleType,

            pickupLatitude:
              pickupCoords.lat,

            pickupLongitude:
              pickupCoords.lng,

            destinationLatitude:
              destinationCoords.lat,

            destinationLongitude:
              destinationCoords.lng,

            distanceKm,
          });


        setFare(
          fareResponse.fare
        );


      } catch (error) {

        console.error(
          error
        );

        setMessage(
          "Unable to calculate route. Please try again."
        );

      } finally {

        setLoading(false);

      }
    };


  // ==============================
  // BOOK RIDE
  // ==============================

  const handleBookRide =
    async () => {

      if (
        !pickupLocation ||
        !destinationLocation ||
        !distance
      ) {

        setMessage(
          "First calculate the route"
        );

        return;
      }


      setLoading(true);
      setMessage("");


      try {

        const ride =
          await rideApi.book({

            pickup,

            destination,

            vehicleType,

            pickupLatitude:
              pickupLocation.lat,

            pickupLongitude:
              pickupLocation.lng,

            destinationLatitude:
              destinationLocation.lat,

            destinationLongitude:
              destinationLocation.lng,

            distanceKm:
              distance,
          });


        setMessage(
          "Ride booked successfully!"
        );


        setTimeout(() => {

          navigate(
            "/track-ride"
          );

        }, 800);


      } catch (error) {

        console.error(
          error
        );

        setMessage(
          error.response?.data?.message ||
          "Unable to book ride"
        );

      } finally {

        setLoading(false);

      }

    };


  // ==============================
  // UI
  // ==============================

  return (

    <div className="
      min-h-screen
      bg-black
      text-white
      flex
      justify-center
      items-center
      p-6
    ">

      <div className="
        w-full
        max-w-6xl
        bg-[#111]
        border
        border-gray-800
        rounded-3xl
        p-6
        shadow-2xl
      ">

        <h1 className="
          text-4xl
          md:text-5xl
          font-bold
          text-orange-400
          mb-8
        ">
          Book Your Cab
        </h1>


        {/* =======================
            LOCATION INPUTS
        ======================= */}

        <div className="
          grid
          md:grid-cols-2
          gap-5
          mb-6
        ">

          <div>

            <label className="
              block
              text-gray-400
              mb-2
            ">
              📍 Pickup Location
            </label>

            <input
              type="text"
              placeholder="Enter pickup location"
              value={pickup}
              onChange={(e) =>
                setPickup(
                  e.target.value
                )
              }
              className="
                w-full
                bg-[#1e1e1e]
                border
                border-gray-700
                rounded-2xl
                px-5
                py-4
                outline-none
                focus:border-orange-500
              "
            />

          </div>


          <div>

            <label className="
              block
              text-gray-400
              mb-2
            ">
              🏁 Destination
            </label>

            <input
              type="text"
              placeholder="Enter destination"
              value={destination}
              onChange={(e) =>
                setDestination(
                  e.target.value
                )
              }
              className="
                w-full
                bg-[#1e1e1e]
                border
                border-gray-700
                rounded-2xl
                px-5
                py-4
                outline-none
                focus:border-orange-500
              "
            />

          </div>

        </div>


        {/* =======================
            VEHICLE
        ======================= */}

        <div className="mb-6">

          <label className="
            block
            text-gray-400
            mb-2
          ">
            Choose Vehicle
          </label>

          <select
            value={vehicleType}
            onChange={(e) =>
              setVehicleType(
                e.target.value
              )
            }
            className="
              w-full
              bg-[#1e1e1e]
              border
              border-gray-700
              rounded-2xl
              px-5
              py-4
              outline-none
            "
          >

            <option value="BIKE">
              Bike
            </option>

            <option value="AUTO">
              Auto
            </option>

            <option value="CAB">
              Cab
            </option>

            <option value="SUV">
              SUV
            </option>

            <option value="PREMIUM">
              Premium
            </option>

          </select>

        </div>


        {/* =======================
            MAP
        ======================= */}

        <div className="
          overflow-hidden
          rounded-3xl
          mb-6
          border
          border-gray-800
        ">

          <Map
            pickup={
              pickupLocation
            }

            destination={
              destinationLocation
            }

            routeCoordinates={
              routeCoordinates
            }
          />

        </div>


        {/* =======================
            CALCULATE
        ======================= */}

        <button
          onClick={
            calculateRoute
          }
          disabled={loading}
          className="
            w-full
            bg-orange-500
            hover:bg-orange-400
            disabled:opacity-50
            transition
            py-4
            rounded-2xl
            text-black
            text-xl
            font-bold
            mb-6
          "
        >

          {loading
            ? "Calculating..."
            : "Calculate Route & Fare"}

        </button>


        {/* =======================
            RIDE DETAILS
        ======================= */}

        {distance !== null && (

          <div className="
            grid
            grid-cols-2
            md:grid-cols-3
            gap-4
            mb-6
          ">

            <div className="
              bg-[#1e1e1e]
              rounded-2xl
              p-5
              text-center
            ">

              <p className="
                text-gray-400
                text-sm
              ">
                Road Distance
              </p>

              <p className="
                text-2xl
                font-bold
                text-white
                mt-2
              ">
                {distance.toFixed(2)} km
              </p>

            </div>


            <div className="
              bg-[#1e1e1e]
              rounded-2xl
              p-5
              text-center
            ">

              <p className="
                text-gray-400
                text-sm
              ">
                Estimated Time
              </p>

              <p className="
                text-2xl
                font-bold
                text-white
                mt-2
              ">
                {duration} min
              </p>

            </div>


            <div className="
              bg-[#1e1e1e]
              rounded-2xl
              p-5
              text-center
              col-span-2
              md:col-span-1
            ">

              <p className="
                text-gray-400
                text-sm
              ">
                Cabzi Fare
              </p>

              <p className="
                text-3xl
                font-bold
                text-orange-400
                mt-2
              ">
                ₹{fare ?? "--"}
              </p>

            </div>

          </div>

        )}


        {/* =======================
            BOOK
        ======================= */}

        {fare !== null && (

          <button
            onClick={
              handleBookRide
            }
            disabled={loading}
            className="
              w-full
              bg-green-500
              hover:bg-green-400
              disabled:opacity-50
              transition
              py-4
              rounded-2xl
              text-black
              text-xl
              font-bold
            "
          >

            {loading
              ? "Booking..."
              : `Confirm Ride • ₹${fare}`}

          </button>

        )}


        {/* =======================
            MESSAGE
        ======================= */}

        {message && (

          <p className="
            text-center
            text-orange-400
            font-bold
            mt-5
          ">
            {message}
          </p>

        )}

      </div>

    </div>

  );

}

export default BookRide;