import { useContext, useEffect, useMemo, useState } from "react";
import {
  CalendarClock,
  MapPin,
  MessageCircle,
  Navigation,
  Phone,
  Play,
  Plus,
  Search,
  Users
} from "lucide-react";
import { ThemeContext } from "../context/ThemeContext";
import { shareRideApi } from "../services/backend";

function ShareRide() {

  const { darkMode } =
    useContext(ThemeContext);

  const [pickup, setPickup] =
    useState("");

  const [drop, setDrop] =
    useState("");

  const [scheduleTime, setScheduleTime] =
    useState("");

  const [seats, setSeats] =
    useState(3);

  const [fare, setFare] =
    useState(800);

  const [rides, setRides] =
    useState([]);

  const [activeRide, setActiveRide] =
    useState(null);

  const [error, setError] =
    useState("");

  const visibleRides =
    useMemo(() => {

      const pickupQuery =
        pickup.trim().toLowerCase();

      const dropQuery =
        drop.trim().toLowerCase();

      return rides.filter((ride) => {

        if (String(ride.status).toLowerCase() !== "open") return false;

        const pickupMatch =
          !pickupQuery ||
          ride.pickup.toLowerCase().includes(pickupQuery);

        const dropMatch =
          !dropQuery ||
          ride.drop.toLowerCase().includes(dropQuery);

        return pickupMatch && dropMatch;
      });

    }, [drop, pickup, rides]);

  useEffect(() => {

    const loadShareRides = async () => {

      try {

        setRides(
          await shareRideApi.open({
            pickup,
            drop
          })
        );

      } catch {

        setError("Unable to load share rides from backend");
      }
    };

    loadShareRides();

  }, [drop, pickup]);

  const createShareRide = async () => {

    if (!pickup.trim() || !drop.trim() || !scheduleTime) return;

    const newRide =
      await shareRideApi.create({
      pickup,
      drop,
      scheduleTime,
      seats: Number(seats),
      fare: Number(fare)
    });

    setRides([
      newRide,
      ...rides
    ]);
  };

  const joinRide = async (rideId) => {

    const joinedRide =
      await shareRideApi.join(rideId);

    setRides(
      rides.map((ride) => {

        return ride.id === rideId ? joinedRide : ride;
      })
    );
  };

  const startPickup = async (rideId) => {

    const startedRide =
      await shareRideApi.startPickup(rideId);

    setActiveRide(startedRide);
  };

  const cardClass = darkMode
    ? "bg-black border-gray-800"
    : "bg-gray-100 border-gray-300";

  const inputClass = darkMode
    ? "bg-[#1e1e1e] text-white border-gray-800"
    : "bg-white text-black border-gray-300";

  return (

    <div
      className={`min-h-screen px-4 py-8 ${
        darkMode ? "bg-[#111111] text-white" : "bg-white text-black"
      }`}
    >

      <div className="max-w-[1350px] mx-auto space-y-8">

        {error && (
          <div className="bg-red-500/20 border border-red-500 text-red-300 rounded-2xl p-4">
            {error}
          </div>
        )}

        <div className="grid lg:grid-cols-[1.1fr_0.9fr] gap-6 items-stretch">

          <section className={`border rounded-[32px] p-6 ${cardClass}`}>

            <div className="flex items-center gap-3 text-orange-400 font-black text-lg">

              <Users size={24} />
              Share Ride

            </div>

            <h1 className="mt-4 text-4xl md:text-5xl font-black leading-tight">

              Find open shared rides or schedule your own.

            </h1>

            <div className="grid md:grid-cols-2 gap-4 mt-6">

              <label className="space-y-2">
                <span className="text-sm text-gray-500">Pickup location</span>
                <div className="relative">
                  <MapPin className="absolute left-4 top-4 text-orange-400" size={20} />
                  <input
                    value={pickup}
                    onChange={(event) => setPickup(event.target.value)}
                    placeholder="Enter pickup"
                    className={`w-full rounded-2xl border py-4 pl-12 pr-4 outline-none ${inputClass}`}
                  />
                </div>
              </label>

              <label className="space-y-2">
                <span className="text-sm text-gray-500">Drop location</span>
                <div className="relative">
                  <Navigation className="absolute left-4 top-4 text-orange-400" size={20} />
                  <input
                    value={drop}
                    onChange={(event) => setDrop(event.target.value)}
                    placeholder="Enter drop"
                    className={`w-full rounded-2xl border py-4 pl-12 pr-4 outline-none ${inputClass}`}
                  />
                </div>
              </label>

            </div>

            <div className={`mt-6 rounded-3xl border overflow-hidden ${cardClass}`}>

              <iframe
                title="Share ride map"
                src="https://www.openstreetmap.org/export/embed.html?bbox=77.019%2C28.459%2C77.391%2C28.704&layer=mapnik"
                className="w-full h-[320px] border-0"
              />

            </div>

          </section>

          <section className={`border rounded-[32px] p-6 ${cardClass}`}>

            <div className="flex items-center gap-3 text-orange-400 font-black text-lg">

              <Plus size={24} />
              Schedule Ride

            </div>

            <div className="space-y-4 mt-6">

              <input
                type="datetime-local"
                value={scheduleTime}
                onChange={(event) => setScheduleTime(event.target.value)}
                className={`w-full rounded-2xl border px-4 py-4 outline-none ${inputClass}`}
              />

              <div className="grid grid-cols-2 gap-4">

                <label className="space-y-2">
                  <span className="text-sm text-gray-500">Seats</span>
                  <input
                    type="number"
                    min="2"
                    max="6"
                    value={seats}
                    onChange={(event) => setSeats(event.target.value)}
                    className={`w-full rounded-2xl border px-4 py-4 outline-none ${inputClass}`}
                  />
                </label>

                <label className="space-y-2">
                  <span className="text-sm text-gray-500">Total fare</span>
                  <input
                    type="number"
                    min="100"
                    value={fare}
                    onChange={(event) => setFare(event.target.value)}
                    className={`w-full rounded-2xl border px-4 py-4 outline-none ${inputClass}`}
                  />
                </label>

              </div>

              <button
                onClick={createShareRide}
                className="w-full bg-orange-500 hover:bg-orange-400 transition py-4 rounded-2xl text-black font-black"
              >

                Open for Share

              </button>

              <div className={`rounded-2xl p-4 text-sm leading-6 ${darkMode ? "bg-[#1e1e1e] text-gray-300" : "bg-white text-gray-700"}`}>

                If no one joins, the first user pays the full fare. When users join, the fare is distributed equally between all joined riders.

              </div>

            </div>

          </section>

        </div>

        {activeRide && (

          <section className={`border rounded-[32px] p-6 ${cardClass}`}>

            <div className="flex flex-wrap justify-between gap-4">

              <div>

                <p className="text-orange-400 font-black">Ride started</p>
                <h2 className="text-3xl font-black mt-2">
                  Driver is going for pickup
                </h2>
                <p className="text-gray-500 mt-2">
                  {activeRide.pickup} to {activeRide.drop}
                </p>

              </div>

              <div className="flex gap-3">

                <a
                  href="tel:+911234567890"
                  className="bg-orange-500 text-black rounded-2xl px-5 py-3 font-black flex items-center gap-2"
                >
                  <Phone size={18} />
                  Call
                </a>

                <a
                  href="/chat"
                  className="bg-orange-500 text-black rounded-2xl px-5 py-3 font-black flex items-center gap-2"
                >
                  <MessageCircle size={18} />
                  Message
                </a>

                <a
                  href={`https://www.google.com/maps/dir/?api=1&origin=${encodeURIComponent(activeRide.pickup)}&destination=${encodeURIComponent(activeRide.drop)}`}
                  target="_blank"
                  rel="noreferrer"
                  className="bg-green-500 text-black rounded-2xl px-5 py-3 font-black flex items-center gap-2"
                >
                  <Navigation size={18} />
                  Direction
                </a>

              </div>

            </div>

          </section>
        )}

        <section>

          <div className="flex items-center gap-3 mb-5">

            <Search className="text-orange-400" size={24} />
            <h2 className="text-3xl font-black">Available shared rides</h2>

          </div>

          <div className="grid lg:grid-cols-3 gap-5">

            {visibleRides.map((ride) => {

              const perPersonFare =
                Math.ceil(ride.fare / ride.riders);

              return (

                <article
                  key={ride.id}
                  className={`border rounded-[28px] p-5 ${cardClass}`}
                >

                  <div className="flex justify-between gap-4">

                    <div>
                      <p className="text-sm text-gray-500">Route</p>
                      <h3 className="text-xl font-black mt-1">
                        {ride.pickup}
                      </h3>
                      <p className="text-orange-400 font-bold mt-1">
                        to {ride.drop}
                      </p>
                    </div>

                    <div className="text-right">
                      <p className="text-sm text-gray-500">Your fare</p>
                      <p className="text-2xl font-black text-green-400">
                        Rs {perPersonFare}
                      </p>
                    </div>

                  </div>

                  <div className="grid grid-cols-2 gap-3 mt-5 text-sm">

                    <div className={`rounded-2xl p-3 ${darkMode ? "bg-[#1e1e1e]" : "bg-white"}`}>
                      <CalendarClock size={18} className="text-orange-400 mb-2" />
                      {ride.time}
                    </div>

                    <div className={`rounded-2xl p-3 ${darkMode ? "bg-[#1e1e1e]" : "bg-white"}`}>
                      <Users size={18} className="text-orange-400 mb-2" />
                      {ride.riders}/{ride.seats} riders
                    </div>

                  </div>

                  <p className="text-gray-500 mt-4 text-sm">
                    {ride.driver} - {ride.vehicle}
                  </p>

                  <div className="grid grid-cols-2 gap-3 mt-5">

                    <button
                      onClick={() => joinRide(ride.id)}
                      disabled={ride.riders >= ride.seats}
                      className="bg-orange-500 disabled:bg-gray-600 disabled:text-gray-300 hover:bg-orange-400 transition py-3 rounded-2xl text-black font-black"
                    >
                      Join
                    </button>

                    <button
                      onClick={() => startPickup(ride.id)}
                      className="bg-green-500 hover:bg-green-600 transition py-3 rounded-2xl text-black font-black flex items-center justify-center gap-2"
                    >
                      <Play size={18} />
                      Start
                    </button>

                  </div>

                </article>
              );
            })}

          </div>

          {visibleRides.length === 0 && (

            <div className={`border rounded-[28px] p-8 text-center ${cardClass}`}>
              No open shared rides found for this pickup and drop.
            </div>
          )}

        </section>

      </div>

    </div>
  );
}

export default ShareRide;
