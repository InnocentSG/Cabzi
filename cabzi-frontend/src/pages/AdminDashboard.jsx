import { useEffect, useMemo, useState } from "react";
import { useSearchParams } from "react-router-dom";
import { adminApi } from "../services/backend";

const emptyStaff = {
  name: "",
  email: "",
  phone: "",
  password: "",
  status: "active"
};

const emptyUser = {
  name: "",
  email: "",
  phone: "",
  password: "",
  role: "user"
};

const emptyDriver = {
  name: "",
  email: "",
  phone: "",
  password: "",
  status: "pending",
  vehicleType: "",
  company: "",
  model: "",
  vehicleNo: "",
  idProof: "",
  drivingLicense: "",
  rc: "",
  pollution: "",
  livePhoto: "",
  approvedBy: "",
  todayRides: 0,
  todayAmount: 0,
  monthIncome: 0,
  monthRides: 0,
  subscriptionId: ""
};

const emptySubscription = {
  name: "",
  price: "",
  totalRides: "",
  cancellations: "",
  notifications: true,
  active: true
};

function AdminDashboard() {

  const [searchParams, setSearchParams] =
    useSearchParams();

  const activeTab =
    searchParams.get("tab") || "dashboard";

  const [bookings, setBookings] =
    useState([]);

  const [helpIssues, setHelpIssues] =
    useState([]);

  const [staffAccounts, setStaffAccounts] =
    useState([]);

  const [staffLogs, setStaffLogs] =
    useState([]);

  const [staffMessages, setStaffMessages] =
    useState([]);

  const [users, setUsers] =
    useState([]);

  const [drivers, setDrivers] =
    useState([]);

  const [subscriptions, setSubscriptions] =
    useState([]);

  const [error, setError] =
    useState("");

  const [staffForm, setStaffForm] =
    useState(emptyStaff);

  const [userForm, setUserForm] =
    useState(emptyUser);

  const [driverForm, setDriverForm] =
    useState(emptyDriver);

  const [subscriptionForm, setSubscriptionForm] =
    useState(emptySubscription);

  const [chatText, setChatText] =
    useState("");

  const totalAmount =
    useMemo(
      () => bookings.reduce(
        (sum, booking) => sum + Number(booking.amount || 0),
        0
      ),
      [bookings]
    );

  useEffect(() => {

    const loadAdminData = async () => {

      try {

        const [
          bookingsData,
          helpData,
          staffData,
          logsData,
          messagesData,
          usersData,
          driversData,
          subscriptionsData
        ] = await Promise.all([
          adminApi.todayBookings(),
          adminApi.helpIssues(),
          adminApi.staff(),
          adminApi.staffLogs(),
          adminApi.staffMessages(),
          adminApi.users(),
          adminApi.drivers(),
          adminApi.subscriptions()
        ]);

        setBookings(bookingsData);
        setHelpIssues(helpData);
        setStaffAccounts(staffData);
        setStaffLogs(logsData);
        setStaffMessages(messagesData);
        setUsers(usersData);
        setDrivers(driversData);
        setSubscriptions(subscriptionsData);

      } catch {

        setError("Unable to load admin data from backend");
      }
    };

    loadAdminData();

  }, []);

  const saveStaff = async (event) => {

    event.preventDefault();

    if (!staffForm.name || !staffForm.email || !staffForm.password) return;

    const savedStaff =
      await adminApi.saveStaff({
        ...staffForm,
        role: "STAFF"
      });

    setStaffAccounts([
      ...staffAccounts.filter((staff) =>
        staff.id !== savedStaff.id
      ),
      savedStaff
    ]);
    setStaffForm(emptyStaff);
  };

  const saveUser = async (event) => {

    event.preventDefault();

    if (!userForm.name || !userForm.email || !userForm.password) return;

    const savedUser =
      await adminApi.saveUser(userForm);

    setUsers([
      ...users.filter((user) => user.id !== savedUser.id),
      savedUser
    ]);
    setUserForm(emptyUser);
  };

  const saveDriver = async (event) => {

    event.preventDefault();

    if (!driverForm.name || !driverForm.email || !driverForm.password) return;

    const savedDriver =
      await adminApi.saveDriver(driverForm);

    setDrivers([
      ...drivers.filter((driver) => driver.id !== savedDriver.id),
      savedDriver
    ]);
    setDriverForm(emptyDriver);
  };

  const saveSubscription = async (event) => {

    event.preventDefault();

    if (!subscriptionForm.name || !subscriptionForm.price) return;

    const savedSubscription =
      await adminApi.saveSubscription({
        ...subscriptionForm,
        price: Number(subscriptionForm.price),
        totalRides: Number(subscriptionForm.totalRides),
        cancellations: Number(subscriptionForm.cancellations)
      });

    setSubscriptions([
      ...subscriptions,
      savedSubscription
    ]);
    setSubscriptionForm(emptySubscription);
  };

  const removeStaff = async (id) => {

    await adminApi.deleteStaff(id);

    setStaffAccounts(
      staffAccounts.filter((staff) => staff.id !== id)
    );
  };

  const removeUser = async (id) => {

    await adminApi.deleteUser(id);

    setUsers(users.filter((user) => user.id !== id));
  };

  const removeDriver = async (id) => {

    await adminApi.deleteDriver(id);

    setDrivers(drivers.filter((driver) => driver.id !== id));
  };

  const closeIssue = async (id) => {

    await adminApi.closeHelpIssue(id);

    const nextIssues =
      helpIssues.map((issue) =>
        issue.id === id
          ? {
              ...issue,
              status: "Closed"
            }
          : issue
      );

    setHelpIssues(nextIssues);
  };

  const sendStaffMessage = async () => {

    if (!chatText.trim()) return;

    const savedMessage =
      await adminApi.sendStaffMessage({
        text: chatText
      });

    setStaffMessages([
      ...staffMessages,
      savedMessage
    ]);
    setChatText("");
  };

  return (

    <div className="min-h-screen bg-[#0f0f0f] text-white p-10">

      <div className="flex flex-col lg:flex-row lg:items-center lg:justify-between gap-5">

        <div>
          <h1 className="text-5xl font-bold text-green-400">Admin</h1>
          <p className="text-gray-400 mt-4 text-xl">
            Full CABZI control center for bookings, staff, users, drivers, and plans.
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
            onClick={() => setSearchParams({ tab: "creation" })}
            className={`px-6 py-3 rounded-2xl font-bold ${activeTab === "creation" ? "bg-orange-500 text-black" : "bg-[#1a1a1a]"}`}
          >
            Creation
          </button>
        </div>

      </div>

      {error && (
        <div className="mt-6 bg-red-500/20 border border-red-500 text-red-300 rounded-2xl p-4">
          {error}
        </div>
      )}

      {activeTab === "dashboard" && (

        <div className="mt-10 space-y-10">

          <div className="grid md:grid-cols-4 gap-6">
            <Stat title="Today Bookings" value={bookings.length} color="text-green-400" />
            <Stat title="Total Amount" value={`Rs ${totalAmount}`} color="text-yellow-400" />
            <Stat title="Open Help" value={helpIssues.filter((issue) => issue.status !== "Closed").length} color="text-red-400" />
            <Stat title="Staff" value={staffAccounts.length} color="text-blue-400" />
          </div>

          <Panel title="Today Booking Details">
            <div className="grid gap-4">
              {bookings.map((booking) => (
                <Row key={booking.id}>
                  <div>
                    <h3 className="text-xl font-bold">{booking.id} | {booking.customer}</h3>
                    <p className="text-gray-400 mt-1">
                      {booking.pickup} to {booking.drop} | {booking.type} | {booking.driver}
                    </p>
                  </div>
                  <div className="text-right">
                    <p className="text-2xl font-bold text-green-400">Rs {booking.amount}</p>
                    <p className="text-gray-400">{booking.status}</p>
                  </div>
                </Row>
              ))}
            </div>
          </Panel>

          <div className="grid lg:grid-cols-2 gap-6">
            <Panel title="Help Issues">
              <div className="grid gap-4">
                {helpIssues.map((issue) => (
                  <Row key={issue.id}>
                    <div>
                      <h3 className="font-bold">{issue.name} | {issue.priority}</h3>
                      <p className="text-gray-400 mt-1">{issue.issue}</p>
                    </div>
                    <button
                      onClick={() => closeIssue(issue.id)}
                      className="bg-green-500 px-4 py-2 rounded-xl text-black font-bold"
                    >
                      {issue.status}
                    </button>
                  </Row>
                ))}
              </div>
            </Panel>

            <Panel title="Staff Logs">
              <div className="grid gap-4">
                {staffLogs.map((log) => (
                  <Row key={log.id}>
                    <div>
                      <h3 className="font-bold">{log.staff}</h3>
                      <p className="text-gray-400 mt-1">{log.action}</p>
                    </div>
                    <p className="text-orange-400">{log.time}</p>
                  </Row>
                ))}
              </div>
            </Panel>
          </div>

          <Panel title="Staff Chat">
            <div className="h-72 overflow-y-auto space-y-3">
              {staffMessages.map((message) => (
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
                placeholder="Message staff"
                className="flex-1 bg-black border border-gray-700 rounded-2xl px-5 py-4 outline-none"
              />
              <button
                onClick={sendStaffMessage}
                className="bg-orange-500 px-6 py-4 rounded-2xl text-black font-bold"
              >
                Send
              </button>
            </div>
          </Panel>

        </div>
      )}

      {activeTab === "creation" && (

        <div className="mt-10 grid gap-8">

          <EditorPanel title="Create/Edit Staff ID" form={staffForm} setForm={setStaffForm} onSubmit={saveStaff}>
            <Input label="Name" value={staffForm.name} onChange={(value) => setStaffForm({ ...staffForm, name: value })} />
            <Input label="Email / ID" value={staffForm.email} onChange={(value) => setStaffForm({ ...staffForm, email: value })} />
            <Input label="Phone" value={staffForm.phone} onChange={(value) => setStaffForm({ ...staffForm, phone: value })} />
            <Input label="Password" value={staffForm.password} onChange={(value) => setStaffForm({ ...staffForm, password: value })} />
          </EditorPanel>

          <List title="Staff Accounts" items={staffAccounts} onEdit={setStaffForm} onDelete={removeStaff} />

          <EditorPanel title="Create/Edit User" form={userForm} setForm={setUserForm} onSubmit={saveUser}>
            <Input label="Name" value={userForm.name} onChange={(value) => setUserForm({ ...userForm, name: value })} />
            <Input label="Email / ID" value={userForm.email} onChange={(value) => setUserForm({ ...userForm, email: value })} />
            <Input label="Phone" value={userForm.phone} onChange={(value) => setUserForm({ ...userForm, phone: value })} />
            <Input label="Password" value={userForm.password} onChange={(value) => setUserForm({ ...userForm, password: value })} />
          </EditorPanel>

          <List title="Users" items={users} onEdit={setUserForm} onDelete={removeUser} />

          <EditorPanel title="Create/Edit Driver" form={driverForm} setForm={setDriverForm} onSubmit={saveDriver}>
            <Input label="Name" value={driverForm.name} onChange={(value) => setDriverForm({ ...driverForm, name: value })} />
            <Input label="Email / ID" value={driverForm.email} onChange={(value) => setDriverForm({ ...driverForm, email: value })} />
            <Input label="Phone" value={driverForm.phone} onChange={(value) => setDriverForm({ ...driverForm, phone: value })} />
            <Input label="Password" value={driverForm.password} onChange={(value) => setDriverForm({ ...driverForm, password: value })} />
            <Input label="Vehicle Type" value={driverForm.vehicleType} onChange={(value) => setDriverForm({ ...driverForm, vehicleType: value })} />
            <Input label="Company" value={driverForm.company} onChange={(value) => setDriverForm({ ...driverForm, company: value })} />
            <Input label="Model" value={driverForm.model} onChange={(value) => setDriverForm({ ...driverForm, model: value })} />
            <Input label="Vehicle No" value={driverForm.vehicleNo} onChange={(value) => setDriverForm({ ...driverForm, vehicleNo: value })} />
          </EditorPanel>

          <List title="Drivers" items={drivers} onEdit={setDriverForm} onDelete={removeDriver} />

          <EditorPanel title="Create Driver Subscription" form={subscriptionForm} setForm={setSubscriptionForm} onSubmit={saveSubscription}>
            <Input label="Plan Name" value={subscriptionForm.name} onChange={(value) => setSubscriptionForm({ ...subscriptionForm, name: value })} />
            <Input label="Price" value={subscriptionForm.price} onChange={(value) => setSubscriptionForm({ ...subscriptionForm, price: value })} />
            <Input label="Ride Limit" value={subscriptionForm.totalRides} onChange={(value) => setSubscriptionForm({ ...subscriptionForm, totalRides: value })} />
            <Input label="Cancel Limit" value={subscriptionForm.cancellations} onChange={(value) => setSubscriptionForm({ ...subscriptionForm, cancellations: value })} />
          </EditorPanel>

          <List title="Subscription Plans" items={subscriptions} />

        </div>
      )}

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

function Row({ children }) {

  return (
    <div className="bg-black rounded-2xl p-5 flex flex-col md:flex-row md:items-center md:justify-between gap-4">
      {children}
    </div>
  );
}

function EditorPanel({ title, onSubmit, children }) {

  return (
    <Panel title={title}>
      <form onSubmit={onSubmit} className="grid md:grid-cols-2 lg:grid-cols-4 gap-4">
        {children}
        <button className="bg-orange-500 hover:bg-orange-400 rounded-2xl px-6 py-4 text-black font-bold">
          Save
        </button>
      </form>
    </Panel>
  );
}

function Input({ label, value, onChange }) {

  return (
    <input
      value={value}
      onChange={(event) => onChange(event.target.value)}
      placeholder={label}
      className="bg-black border border-gray-700 rounded-2xl px-5 py-4 outline-none"
    />
  );
}

function List({ title, items, onEdit, onDelete }) {

  return (
    <Panel title={title}>
      <div className="grid gap-3">
        {items.map((item) => (
          <Row key={item.id}>
            <div>
              <h3 className="font-bold">{item.name || item.email}</h3>
              <p className="text-gray-400">
                {item.email || `Rs ${item.price}`} {item.phone ? `| ${item.phone}` : ""}
              </p>
            </div>
            <div className="flex gap-3">
              {onEdit && (
                <button
                  onClick={() => onEdit(item)}
                  className="bg-yellow-500 text-black px-4 py-2 rounded-xl font-bold"
                >
                  Edit
                </button>
              )}
              {onDelete && (
                <button
                  onClick={() => onDelete(item.id)}
                  className="bg-red-500 text-white px-4 py-2 rounded-xl font-bold"
                >
                  Delete
                </button>
              )}
            </div>
          </Row>
        ))}
      </div>
    </Panel>
  );
}

export default AdminDashboard;
