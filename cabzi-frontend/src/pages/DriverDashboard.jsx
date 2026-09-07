import { useEffect, useMemo, useState } from "react";
import { useSearchParams } from "react-router-dom";
import { driverApi } from "../services/backend";

const getSavedUser = () => {

  try {

    const savedUser =
      localStorage.getItem("user");

    return savedUser ? JSON.parse(savedUser) : null;

  } catch {

    return null;
  }
};

const emptyKyc = {
  name: "",
  phone: "",
  email: "",
  vehicleType: "",
  company: "",
  model: "",
  vehicleNo: "",
  idProof: "",
  drivingLicense: "",
  rc: "",
  pollution: "",
  livePhoto: ""
};

function DriverDashboard() {

  const [searchParams, setSearchParams] =
    useSearchParams();

  const activeTab =
    searchParams.get("tab") || "dashboard";

  const currentUser =
    getSavedUser();

  const [drivers, setDrivers] =
    useState([]);

  const [availablePlans, setAvailablePlans] =
    useState([]);

  const [error, setError] =
    useState("");

  const [kyc, setKyc] =
    useState({
      ...emptyKyc,
      name: currentUser?.name || "",
      email: currentUser?.email || "",
      phone: currentUser?.phone || ""
    });

  const driver =
    useMemo(
      () => drivers[0] || null,
      [drivers]
    );

  const subscription =
    availablePlans.find((item) =>
      item.id === Number(driver?.subscriptionId)
    );

  useEffect(() => {

    const loadDriverData = async () => {

      try {

        const [
          driverData,
          plansData
        ] = await Promise.all([
          driverApi.me(),
          driverApi.subscriptions()
        ]);

        setDrivers(driverData ? [driverData] : []);
        setAvailablePlans(plansData);

      } catch {

        setError("Unable to load driver data from backend");
      }
    };

    loadDriverData();

  }, []);

  const submitKyc = async (event) => {

    event.preventDefault();

    const savedDriver =
      await driverApi.submitKyc(kyc);

    setDrivers([savedDriver]);
  };

  const changeVehicle = async (field, value) => {

    if (!driver) return;

    const updatedDriver = {
      ...driver,
      [field]: value
    };

    setDrivers([updatedDriver]);

    await driverApi.updateVehicle(updatedDriver);
  };

  const isVerified =
    driver?.status === "verified";

  return (

    <div className="min-h-screen bg-[#0f0f0f] text-white p-10">

      <div className="flex flex-col lg:flex-row lg:items-center lg:justify-between gap-5">
        <div>
          <h1 className="text-5xl font-bold text-green-400">Driver Dashboard</h1>
          <p className="text-gray-400 mt-4 text-xl">
            Complete KYC, track rides, manage vehicle details, and view subscription limits.
          </p>
        </div>
        <div className="flex gap-3">
          <button
            onClick={() => setSearchParams({ tab: "dashboard" })}
            className={`px-6 py-3 rounded-2xl font-bold ${activeTab === "dashboard" ? "bg-orange-500 text-black" : "bg-[#1a1a1a]"}`}
          >
            Dashboard
          </button>
          <button
            onClick={() => setSearchParams({ tab: "subscription" })}
            className={`px-6 py-3 rounded-2xl font-bold ${activeTab === "subscription" ? "bg-orange-500 text-black" : "bg-[#1a1a1a]"}`}
          >
            Subscription
          </button>
        </div>
      </div>

      {error && (
        <div className="mt-6 bg-red-500/20 border border-red-500 text-red-300 rounded-2xl p-4">
          {error}
        </div>
      )}

      {activeTab === "dashboard" && !isVerified && (
        <section className="mt-10 bg-[#1a1a1a] border border-gray-800 rounded-3xl p-8">
          <h2 className="text-4xl font-bold text-yellow-400">KYC Required</h2>
          <p className="text-gray-400 mt-3">
            Submit these details for staff verification. After approval, your ride dashboard will unlock.
          </p>

          <form onSubmit={submitKyc} className="grid md:grid-cols-2 lg:grid-cols-3 gap-4 mt-8">
            {Object.keys(emptyKyc).map((field) => (
              <input
                key={field}
                value={kyc[field]}
                onChange={(event) =>
                  setKyc({
                    ...kyc,
                    [field]: event.target.value
                  })
                }
                placeholder={label(field)}
                className="bg-black border border-gray-700 rounded-2xl px-5 py-4 outline-none"
              />
            ))}
            <button className="bg-orange-500 hover:bg-orange-400 rounded-2xl px-6 py-4 text-black font-bold">
              Submit KYC
            </button>
          </form>

          {driver?.status === "pending" && (
            <p className="mt-6 text-yellow-400 font-bold">
              Your KYC is pending with staff verification.
            </p>
          )}
        </section>
      )}

      {activeTab === "dashboard" && isVerified && (
        <div className="mt-10 space-y-10">
          <div className="grid md:grid-cols-4 gap-6">
            <Stat title="Today Rides" value={driver.todayRides} color="text-green-400" />
            <Stat title="Today Amount" value={`Rs ${driver.todayAmount}`} color="text-yellow-400" />
            <Stat title="Month Income" value={`Rs ${driver.monthIncome}`} color="text-blue-400" />
            <Stat title="Month Rides" value={driver.monthRides} color="text-orange-400" />
          </div>

          <section className="bg-[#1a1a1a] border border-gray-800 rounded-3xl p-8">
            <h2 className="text-3xl font-bold mb-6">Change Vehicle</h2>
            <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-4">
              <VehicleInput label="Vehicle Type" value={driver.vehicleType} onChange={(value) => changeVehicle("vehicleType", value)} />
              <VehicleInput label="Company" value={driver.company} onChange={(value) => changeVehicle("company", value)} />
              <VehicleInput label="Model No" value={driver.model} onChange={(value) => changeVehicle("model", value)} />
              <VehicleInput label="Vehicle No" value={driver.vehicleNo} onChange={(value) => changeVehicle("vehicleNo", value)} />
            </div>
          </section>
        </div>
      )}

      {activeTab === "subscription" && (
        <section className="mt-10 bg-[#1a1a1a] border border-gray-800 rounded-3xl p-8">
          <h2 className="text-4xl font-bold">Subscription</h2>
          <p className="text-gray-400 mt-3">
            Subscription plans are created by admin. Your active plan controls notifications, ride accept limit, and cancel limit.
          </p>

          {subscription ? (
            <div className="grid md:grid-cols-4 gap-6 mt-8">
              <Stat title="Plan" value={subscription.name} color="text-green-400" />
              <Stat title="Ride Accept Limit" value={subscription.totalRides} color="text-yellow-400" />
              <Stat title="Cancel Limit" value={subscription.cancellations} color="text-red-400" />
              <Stat title="Notifications" value={subscription.notifications ? "On" : "Off"} color="text-blue-400" />
            </div>
          ) : (
            <div className="bg-black rounded-3xl p-8 mt-8 text-yellow-400 font-bold">
              No active subscription assigned. Contact admin to purchase a driver plan.
            </div>
          )}

          <div className="grid lg:grid-cols-3 gap-5 mt-8">
            {availablePlans.map((plan) => (
              <div key={plan.id} className="bg-black rounded-3xl p-6 border border-gray-800">
                <h3 className="text-2xl font-bold text-orange-400">{plan.name}</h3>
                <p className="text-4xl font-bold mt-4">Rs {plan.price}</p>
                <p className="text-gray-400 mt-4">{plan.totalRides} rides | {plan.cancellations} cancels</p>
                <button
                  onClick={() =>
                    driverApi.requestSubscription(plan.id)
                  }
                  className="mt-5 bg-green-500 px-5 py-3 rounded-2xl text-black font-bold"
                >
                  Request Purchase
                </button>
              </div>
            ))}
          </div>
        </section>
      )}

    </div>
  );
}

function label(field) {

  const labels = {
    vehicleType: "Vehicle Type",
    vehicleNo: "Vehicle Number",
    idProof: "Valid ID Proof",
    drivingLicense: "Driving License",
    livePhoto: "Live Photo",
    rc: "RC",
    pollution: "Pollution Certificate"
  };

  return labels[field] || field.charAt(0).toUpperCase() + field.slice(1);
}

function Stat({ title, value, color }) {

  return (
    <div className="bg-[#1a1a1a] p-8 rounded-3xl border border-gray-800">
      <p className="text-gray-400 text-lg">{title}</p>
      <h2 className={`text-3xl font-bold mt-4 ${color}`}>{value}</h2>
    </div>
  );
}

function VehicleInput({ label: inputLabel, value, onChange }) {

  return (
    <input
      value={value || ""}
      onChange={(event) => onChange(event.target.value)}
      placeholder={inputLabel}
      className="bg-black border border-gray-700 rounded-2xl px-5 py-4 outline-none"
    />
  );
}

export default DriverDashboard;
