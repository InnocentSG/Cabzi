function TrackRide() {

  return (

    <div className="min-h-screen bg-black text-white p-10">

      {/* HEADER */}

      <div className="mb-10">

        <h1 className="text-5xl font-bold text-green-400">

          Live Ride Tracking

        </h1>

        <p className="text-gray-400 mt-4 text-xl">

          Driver is on the way

        </p>

      </div>

      {/* MAP AREA */}

      <div className="bg-[#111] border border-gray-800 rounded-3xl h-[500px] overflow-hidden shadow-2xl">

        <iframe
          title="Live ride tracking map"
          src="https://www.openstreetmap.org/export/embed.html?bbox=77.019%2C28.459%2C77.391%2C28.704&layer=mapnik"
          className="w-full h-full border-0"
        />

      </div>

      {/* RIDE INFO */}

      <div className="grid md:grid-cols-3 gap-6 mt-10">

        <div className="bg-[#111] border border-gray-800 rounded-3xl p-6">

          <p className="text-gray-400">

            Driver

          </p>

          <h2 className="text-2xl mt-2 font-bold">

            Rahul Sharma

          </h2>

        </div>

        <div className="bg-[#111] border border-gray-800 rounded-3xl p-6">

          <p className="text-gray-400">

            Vehicle

          </p>

          <h2 className="text-2xl mt-2 font-bold">

            UP16AB1234

          </h2>

        </div>

        <div className="bg-[#111] border border-gray-800 rounded-3xl p-6">

          <p className="text-gray-400">

            ETA

          </p>

          <h2 className="text-2xl mt-2 font-bold text-green-400">

            5 mins

          </h2>

        </div>

      </div>

    </div>
  );
}

export default TrackRide;
