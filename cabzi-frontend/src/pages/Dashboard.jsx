function Dashboard() {

  return (

    <div className="min-h-screen bg-black text-white p-10">

      <h1 className="text-5xl font-bold text-green-400 mb-10">

        Dashboard

      </h1>

      <div className="grid md:grid-cols-3 gap-8">

        <div className="bg-[#111] border border-gray-800 rounded-3xl p-8">

          <p className="text-gray-400">

            Total Rides

          </p>

          <h2 className="text-4xl font-bold mt-4">

            24

          </h2>

        </div>

        <div className="bg-[#111] border border-gray-800 rounded-3xl p-8">

          <p className="text-gray-400">

            Wallet Balance

          </p>

          <h2 className="text-4xl font-bold mt-4 text-green-400">

            ₹2400

          </h2>

        </div>

        <div className="bg-[#111] border border-gray-800 rounded-3xl p-8">

          <p className="text-gray-400">

            Rating

          </p>

          <h2 className="text-4xl font-bold mt-4">

            ⭐ 4.9

          </h2>

        </div>

      </div>

    </div>
  );
}

export default Dashboard;