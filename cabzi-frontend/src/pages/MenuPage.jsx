import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import { rideApi } from "../services/backend";

const content = {
  "my-rides": {
    title: "My Rides",
    description: "Your recent and upcoming ride activity.",
    items: []
  },
  policy: {
    title: "Policy",
    description: "CABZI ride, cancellation, safety, payment, and account policy.",
    items: [
      "Drivers and riders must keep verified contact details.",
      "Shared ride fare is split only after another rider joins.",
      "Driver KYC must be approved before accepting rides.",
      "Unsafe conduct, fake documents, or repeated cancellations can disable an account."
    ]
  },
  rewards: {
    title: "Rewards",
    description: "Track CABZI rewards and ride benefits.",
    items: [
      "Complete 5 rides to unlock a discount pass.",
      "Share ride users earn extra reward points.",
      "Drivers with high ratings get priority notifications."
    ]
  },
  subscription: {
    title: "My Subscription",
    description: "User passes and driver subscription limits.",
    items: [
      "Subscription information is loaded from your role dashboard."
    ]
  },
  pass: {
    title: "Pass",
    description: "CABZI pass details and active benefits.",
    items: [
      "No active passenger pass.",
      "Driver plans are assigned or created by admin.",
      "Contact admin or staff for subscription changes."
    ]
  },
  settings: {
    title: "Settings",
    description: "Account preferences and security controls.",
    items: [
      "Update personal details from Profile.",
      "Use Notifications to track ride and approval updates.",
      "Use Help for account, ride, payment, or staff support."
    ]
  }
};

function MenuPage({ type }) {

  const page =
    content[type] || content.settings;

  const [items, setItems] =
    useState(page.items);

  useEffect(() => {

    const loadItems = async () => {

      if (type !== "my-rides") {

        setItems(page.items);

        return;
      }

      const rides =
        await rideApi.myRides();

      setItems(
        rides.map((ride) =>
          `${ride.id} | ${ride.pickup || ride.source} to ${ride.drop || ride.destination} | Rs ${ride.fare || ride.amount || 0} | ${ride.status}`
        )
      );
    };

    loadItems();

  }, [page.items, type]);

  return (

    <div className="min-h-screen bg-[#0f0f0f] text-white p-10">

      <div className="max-w-5xl mx-auto">

        <h1 className="text-5xl font-bold text-green-400">{page.title}</h1>
        <p className="text-gray-400 mt-4 text-xl">{page.description}</p>

        <div className="grid gap-4 mt-10">
          {(items.length ? items : ["No records found from backend yet."]).map((item, index) => (
            <div
              key={index}
              className="bg-[#1a1a1a] border border-gray-800 rounded-3xl p-6"
            >
              {item}
            </div>
          ))}
        </div>

        {type === "settings" && (
          <Link
            to="/profile"
            className="inline-block mt-8 bg-orange-500 hover:bg-orange-400 rounded-2xl px-6 py-4 text-black font-bold"
          >
            Open Profile
          </Link>
        )}

      </div>

    </div>
  );
}

export default MenuPage;
