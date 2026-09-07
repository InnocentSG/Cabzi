import { useState } from "react";
import { paymentApi } from "../services/backend";

function Payment() {

  const [paid, setPaid] =
    useState(false);

  const [error, setError] =
    useState("");

  const payNow = async () => {

    try {

      await paymentApi.pay({
        amount: 370,
        purpose: "RIDE_PAYMENT"
      });

      setPaid(true);

    } catch {

      setError("Payment failed. Please try again.");
    }
  };

  return (

    <div className="min-h-screen bg-black text-white flex justify-center items-center p-10">

      <div className="w-full max-w-xl bg-[#111] border border-gray-800 rounded-3xl p-10 shadow-2xl">

        <h1 className="text-5xl font-bold text-green-400 mb-10">

          Payment

        </h1>

        <div className="space-y-6">

          <div className="bg-[#1e1e1e] rounded-2xl p-5 flex justify-between">

            <span>Ride Fare</span>

            <span>₹350</span>

          </div>

          <div className="bg-[#1e1e1e] rounded-2xl p-5 flex justify-between">

            <span>Platform Fee</span>

            <span>₹20</span>

          </div>

          <div className="bg-[#1e1e1e] rounded-2xl p-5 flex justify-between text-2xl font-bold">

            <span>Total</span>

            <span className="text-green-400">

              ₹370

            </span>

          </div>

          <button
            onClick={payNow}
            className="w-full bg-green-500 hover:bg-green-600 transition py-4 rounded-2xl text-black text-xl font-bold"
          >

            Pay Now

          </button>

          {paid && (

            <div className="bg-green-500/20 border border-green-500 rounded-2xl p-5 text-green-400 font-bold text-center">
              Payment completed successfully.
            </div>
          )}

          {error && (

            <div className="bg-red-500/20 border border-red-500 rounded-2xl p-5 text-red-400 font-bold text-center">
              {error}
            </div>
          )}

        </div>

      </div>

    </div>
  );
}

export default Payment;
