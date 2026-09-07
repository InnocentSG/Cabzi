import { useEffect, useState } from "react";
import { notificationApi } from "../services/backend";

function Notifications() {

  const [notifications, setNotifications] =
    useState([]);

  const [error, setError] =
    useState("");

  useEffect(() => {

    const loadNotifications = async () => {

      try {

        setNotifications(await notificationApi.list());

      } catch {

        setError("Unable to load notifications from backend");
      }
    };

    loadNotifications();

  }, []);

  return (

    <div className="min-h-screen bg-black text-white p-10">

      <h1 className="text-5xl font-bold text-green-400 mb-10">

        Notifications

      </h1>

      <div className="space-y-6">

        {error && (
          <div className="bg-red-500/20 border border-red-500 rounded-2xl p-5 text-red-400 font-bold">
            {error}
          </div>
        )}

        {notifications.map((item, index) => (

          <div
            key={index}
            className="bg-[#111] border border-gray-800 rounded-3xl p-6 shadow-xl"
          >

            <p className="text-xl">

              {item.message || item.title || item}

            </p>

          </div>
        ))}

        {notifications.length === 0 && !error && (
          <div className="bg-[#111] border border-gray-800 rounded-3xl p-6 shadow-xl">
            No notifications found.
          </div>
        )}

      </div>

    </div>
  );
}

export default Notifications;
