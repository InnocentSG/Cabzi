import { useEffect, useState } from "react";
import { staffApi } from "../services/backend";

function DriverVerifierDashboard() {

  const [drivers, setDrivers] =
    useState([]);

  const [logs, setLogs] =
    useState([]);

  const [messages, setMessages] =
    useState([]);

  const [error, setError] =
    useState("");

  const [approvalPasswords, setApprovalPasswords] =
    useState({});

  const [chatText, setChatText] =
    useState("");

  const pendingDrivers =
    drivers.filter((driver) => driver.status !== "verified");

  useEffect(() => {

    const loadStaffData = async () => {

      try {

        const [
          driversData,
          logsData,
          messagesData
        ] = await Promise.all([
          staffApi.allDrivers(),
          staffApi.logs(),
          staffApi.messages()
        ]);

        setDrivers(driversData);
        setLogs(logsData);
        setMessages(messagesData);

      } catch {

        setError("Unable to load staff data from backend");
      }
    };

    loadStaffData();

  }, []);

  const verifyDriver = async (driverId) => {

    const approvalPassword =
      approvalPasswords[driverId];

    if (!approvalPassword?.trim()) return;

    const savedDriver =
      await staffApi.verifyDriver(driverId, {
        approvalPassword
      });

    const nextDrivers =
      drivers.map((driver) =>
        driver.id === driverId ? savedDriver : driver
      );

    setDrivers(nextDrivers);
    setLogs(await staffApi.logs());
  };

  const rejectDriver = async (driverId) => {

    const savedDriver =
      await staffApi.rejectDriver(driverId, {});

    setDrivers(
      drivers.map((driver) =>
        driver.id === driverId ? savedDriver : driver
      )
    );
  };

  const sendMessage = async () => {

    if (!chatText.trim()) return;

    const savedMessage =
      await staffApi.sendMessage({
        text: chatText
      });

    setMessages([
      ...messages,
      savedMessage
    ]);
    setChatText("");
  };

  return (

    <div className="min-h-screen bg-[#0f0f0f] text-white p-10">

      <div>
        <h1 className="text-5xl font-bold text-green-400">
          Staff Dashboard
        </h1>
        <p className="text-gray-400 mt-4 text-xl">
          Verify driver applications, inspect documents, and communicate with admin.
        </p>
      </div>

      {error && (
        <div className="mt-6 bg-red-500/20 border border-red-500 text-red-300 rounded-2xl p-4">
          {error}
        </div>
      )}

      <div className="grid md:grid-cols-4 gap-6 mt-10">
        <Stat title="Pending KYC" value={pendingDrivers.length} color="text-yellow-400" />
        <Stat title="Verified Drivers" value={drivers.filter((driver) => driver.status === "verified").length} color="text-green-400" />
        <Stat title="Rejected" value={drivers.filter((driver) => driver.status === "rejected").length} color="text-red-400" />
        <Stat title="Staff Logs" value={logs.length} color="text-blue-400" />
      </div>

      <section className="mt-12 bg-[#1a1a1a] border border-gray-800 rounded-3xl p-8">
        <h2 className="text-4xl font-bold mb-8">Pending Driver Verification</h2>

        <div className="grid gap-6">
          {pendingDrivers.map((driver) => (
            <div key={driver.id} className="bg-black rounded-3xl p-6">
              <div className="flex flex-col lg:flex-row lg:justify-between gap-6">
                <div>
                  <h3 className="text-3xl font-bold">{driver.name}</h3>
                  <p className="text-gray-400 mt-2">
                    {driver.phone} | {driver.email}
                  </p>
                  <p className="text-orange-400 mt-2">
                    {driver.company} {driver.model} | {driver.vehicleType} | {driver.vehicleNo}
                  </p>
                </div>
                <span className="h-fit bg-yellow-500 text-black px-5 py-2 rounded-xl font-bold capitalize">
                  {driver.status}
                </span>
              </div>

              <div className="grid md:grid-cols-2 lg:grid-cols-5 gap-4 mt-6">
                <Doc title="Name" value={driver.name} />
                <Doc title="Valid ID Proof" value={driver.idProof} />
                <Doc title="DL" value={driver.drivingLicense} />
                <Doc title="RC" value={driver.rc} />
                <Doc title="Pollution" value={driver.pollution} />
                <Doc title="Live Photo" value={driver.livePhoto} />
                <Doc title="Vehicle Company" value={driver.company} />
                <Doc title="Model No" value={driver.model} />
                <Doc title="Vehicle Type" value={driver.vehicleType} />
                <Doc title="Vehicle No" value={driver.vehicleNo} />
              </div>

              <div className="grid md:grid-cols-[1fr_auto_auto] gap-3 mt-6">
                <input
                  type="password"
                  value={approvalPasswords[driver.id] || ""}
                  onChange={(event) =>
                    setApprovalPasswords({
                      ...approvalPasswords,
                      [driver.id]: event.target.value
                    })
                  }
                  placeholder="Enter staff password to save approver"
                  className="bg-[#1a1a1a] border border-gray-700 rounded-2xl px-5 py-4 outline-none"
                />
                <button
                  onClick={() => verifyDriver(driver.id)}
                  className="bg-green-500 hover:bg-green-600 px-6 py-4 rounded-2xl text-black font-bold"
                >
                  Verify Driver
                </button>
                <button
                  onClick={() => rejectDriver(driver.id)}
                  className="bg-red-500 hover:bg-red-600 px-6 py-4 rounded-2xl text-white font-bold"
                >
                  Reject
                </button>
              </div>
            </div>
          ))}
        </div>
      </section>

      <div className="grid lg:grid-cols-2 gap-6 mt-10">
        <Panel title="Staff Chat">
          <div className="h-72 overflow-y-auto space-y-3">
            {messages.map((message) => (
              <div key={message.id} className="bg-black rounded-2xl p-4">
                <p className="text-orange-400 font-bold">{message.sender}</p>
                <p className="mt-1">{message.text}</p>
                <p className="text-gray-500 text-sm mt-2">{message.time}</p>
              </div>
            ))}
          </div>
          <div className="flex gap-3 mt-5">
            <input
              value={chatText}
              onChange={(event) => setChatText(event.target.value)}
              placeholder="Message admin or staff"
              className="flex-1 bg-black border border-gray-700 rounded-2xl px-5 py-4 outline-none"
            />
            <button
              onClick={sendMessage}
              className="bg-orange-500 px-6 py-4 rounded-2xl text-black font-bold"
            >
              Send
            </button>
          </div>
        </Panel>

        <Panel title="My Verification Logs">
          <div className="grid gap-3">
            {logs.map((log) => (
              <div key={log.id} className="bg-black rounded-2xl p-4">
                <p className="font-bold">{log.action}</p>
                <p className="text-gray-400 mt-1">{log.staff} | {log.time}</p>
              </div>
            ))}
          </div>
        </Panel>
      </div>

    </div>
  );
}

function Stat({ title, value, color }) {

  return (
    <div className="bg-[#1a1a1a] p-8 rounded-3xl border border-gray-800">
      <p className="text-gray-400 text-lg">{title}</p>
      <h2 className={`text-4xl font-bold mt-4 ${color}`}>{value}</h2>
    </div>
  );
}

function Panel({ title, children }) {

  return (
    <section className="bg-[#1a1a1a] border border-gray-800 rounded-3xl p-8">
      <h2 className="text-3xl font-bold mb-6">{title}</h2>
      {children}
    </section>
  );
}

function Doc({ title, value }) {

  return (
    <div className="bg-[#1a1a1a] rounded-2xl p-4 border border-gray-800">
      <p className="text-gray-500 text-sm">{title}</p>
      <p className="font-bold mt-2">{value || "Missing"}</p>
    </div>
  );
}

export default DriverVerifierDashboard;
