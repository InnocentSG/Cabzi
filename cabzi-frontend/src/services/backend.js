import API from "./api";

const unwrap = (response) =>
  response.data;

const empty = (fallback) => (error) => {

  if (error.response?.status === 404) {

    return fallback;
  }

  throw error;
};

export const authApi = {
  login: (payload) =>
    API.post("/api/auth/login", payload).then(unwrap),
  register: (payload) =>
    API.post("/api/auth/register", payload).then(unwrap),
  me: () =>
    API.get("/api/users/me").then(unwrap)
};

export const profileApi = {
  update: (payload) =>
    API.put("/api/users/me", payload).then(unwrap)
};

export const adminApi = {
  todayBookings: () =>
    API.get("/api/admin/bookings/today").then(unwrap).catch(empty([])),
  helpIssues: () =>
    API.get("/api/admin/help-issues").then(unwrap).catch(empty([])),
  closeHelpIssue: (id) =>
    API.patch(`/api/admin/help-issues/${id}`, { status: "CLOSED" }).then(unwrap),
  staff: () =>
    API.get("/api/admin/staff").then(unwrap).catch(empty([])),
  saveStaff: (payload) =>
    API.post("/api/admin/staff", payload).then(unwrap),
  deleteStaff: (id) =>
    API.delete(`/api/admin/staff/${id}`).then(unwrap),
  users: () =>
    API.get("/api/admin/users").then(unwrap).catch(empty([])),
  saveUser: (payload) =>
    API.post("/api/admin/users", payload).then(unwrap),
  deleteUser: (id) =>
    API.delete(`/api/admin/users/${id}`).then(unwrap),
  drivers: () =>
    API.get("/api/admin/drivers").then(unwrap).catch(empty([])),
  saveDriver: (payload) =>
    API.post("/api/admin/drivers", payload).then(unwrap),
  deleteDriver: (id) =>
    API.delete(`/api/admin/drivers/${id}`).then(unwrap),
  subscriptions: () =>
    API.get("/api/admin/subscriptions").then(unwrap).catch(empty([])),
  saveSubscription: (payload) =>
    API.post("/api/admin/subscriptions", payload).then(unwrap),
  staffLogs: () =>
    API.get("/api/admin/staff-logs").then(unwrap).catch(empty([])),
  staffMessages: () =>
    API.get("/api/admin/staff-messages").then(unwrap).catch(empty([])),
  sendStaffMessage: (payload) =>
    API.post("/api/admin/staff-messages", payload).then(unwrap)
};

export const staffApi = {
  pendingDrivers: () =>
    API.get("/api/staff/drivers/pending-kyc").then(unwrap).catch(empty([])),
  allDrivers: () =>
    API.get("/api/staff/drivers").then(unwrap).catch(empty([])),
  verifyDriver: (id, payload) =>
    API.post(`/api/staff/drivers/${id}/verify`, payload).then(unwrap),
  rejectDriver: (id, payload) =>
    API.post(`/api/staff/drivers/${id}/reject`, payload).then(unwrap),
  logs: () =>
    API.get("/api/staff/logs").then(unwrap).catch(empty([])),
  messages: () =>
    API.get("/api/staff/messages").then(unwrap).catch(empty([])),
  sendMessage: (payload) =>
    API.post("/api/staff/messages", payload).then(unwrap)
};

export const driverApi = {
  me: () =>
    API.get("/api/drivers/me").then(unwrap).catch(empty(null)),
  submitKyc: (payload) =>
    API.post("/api/drivers/kyc", payload).then(unwrap),
  updateVehicle: (payload) =>
    API.put("/api/drivers/vehicle", payload).then(unwrap),
  subscriptions: () =>
    API.get("/api/drivers/subscriptions").then(unwrap).catch(empty([])),
  requestSubscription: (id) =>
    API.post(`/api/drivers/subscriptions/${id}/request`).then(unwrap)
};

export const rideApi = {
  myRides: () =>
    API.get("/api/rides/my")
      .then(unwrap)
      .catch(empty([])),

  estimateFare: (payload) =>
    API.post(
      "/api/rides/fare",
      payload
    ).then(unwrap),

  book: (payload) =>
    API.post(
      "/api/rides",
      payload
    ).then(unwrap),

  rate: (rideId, payload) =>
    API.post(
      `/api/rides/${rideId}/rate`,
      payload
    ).then(unwrap),
};

export const parcelApi = {
  create: (payload) =>
    API.post("/api/parcels", payload).then(unwrap)
};

export const shareRideApi = {
  open: (params) =>
    API.get("/api/share-rides", { params }).then(unwrap).catch(empty([])),
  create: (payload) =>
    API.post("/api/share-rides", payload).then(unwrap),
  join: (id) =>
    API.post(`/api/share-rides/${id}/join`).then(unwrap),
  startPickup: (id) =>
    API.post(`/api/share-rides/${id}/start-pickup`).then(unwrap)
};

export const notificationApi = {
  list: () =>
    API.get("/api/notifications").then(unwrap).catch(empty([]))
};

export const paymentApi = {
  pay: (payload) =>
    API.post("/api/payments", payload).then(unwrap)
};
