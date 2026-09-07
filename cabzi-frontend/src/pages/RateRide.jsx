import { useState } from "react";
import { rideApi } from "../services/backend";

function RateRide() {

  const rideId =
    new URLSearchParams(window.location.search).get("rideId");

  const [rating, setRating] =
    useState(5);

  const [review, setReview] =
    useState("");

  const [message, setMessage] =
    useState("");

  const handleSubmit = async () => {

    try {

      if (!rideId) {

        setMessage("Missing ride id");

        return;
      }

      await rideApi.rate(rideId, {
        rating,
        review
      });

      setMessage(
        "Rating submitted successfully"
      );

    } catch {

      setMessage(
        "Failed to submit review"
      );
    }
  };

  return (

    <div className="min-h-screen bg-[#0f0f0f] text-white flex justify-center items-center px-5">

      <div className="bg-[#1a1a1a] p-10 rounded-3xl border border-gray-800 w-full max-w-2xl">

        <h1 className="text-5xl font-bold text-green-400 mb-10">

          Rate Your Ride

        </h1>

        {/* STARS */}

        <div className="flex gap-4 mb-10 justify-center">

          {[1,2,3,4,5].map((star) => (

            <button
              key={star}
              onClick={() =>
                setRating(star)
              }
              className={`
                text-6xl transition

                ${
                  star <= rating
                    ? "text-yellow-400"

                    : "text-gray-600"
                }
              `}
            >

              ★

            </button>

          ))}

        </div>

        {/* REVIEW */}

        <textarea
          rows="5"
          placeholder="Write your review..."
          value={review}
          onChange={(e) =>
            setReview(e.target.value)
          }
          className="w-full bg-black border border-gray-700 rounded-2xl p-5 text-lg outline-none"
        />

        {/* BUTTON */}

        <button
          onClick={handleSubmit}
          className="mt-8 w-full bg-green-500 hover:bg-green-600 transition py-5 rounded-2xl text-2xl font-bold"
        >

          Submit Review

        </button>

        {/* MESSAGE */}

        {message && (

          <p className="mt-6 text-center text-xl text-green-400">

            {message}

          </p>

        )}

      </div>

    </div>
  );
}

export default RateRide;
