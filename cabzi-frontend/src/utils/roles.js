export const publicRoles = [
  {
    value: "user",
    label: "User"
  },
  {
    value: "driver",
    label: "Driver"
  }
];

export const roles = [
  ...publicRoles,
  {
    value: "driver_verifier",
    label: "Driver Verifier"
  },
  {
    value: "admin",
    label: "Admin"
  }
];


export const normalizeRole = (role) => {

  const normalized =
    String(role || "user").toLowerCase();

  if (normalized === "staff") {

    return "driver_verifier";
  }

  return normalized;
};

export const getRoleHome = (role) => {

  switch (normalizeRole(role)) {

    case "admin":
      return "/admin";

    case "driver":
      return "/driver";

    case "driver_verifier":
      return "/driver-verifier";

    default:
      return "/";
  }
};
