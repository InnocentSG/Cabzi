import { useState } from "react";

function Chat() {

  const [messages, setMessages] =
    useState([]);

  const [message, setMessage] =
    useState("");

  const sendMessage = () => {

    if (!message.trim()) return;

    setMessages([
      ...messages,
      {
        sender: "You",
        text: message
      }
    ]);

    setMessage("");
  };

  return (

    <div className="min-h-screen bg-black text-white flex justify-center items-center p-10">

      <div className="w-full max-w-3xl bg-[#111] border border-gray-800 rounded-3xl overflow-hidden shadow-2xl">

        {/* HEADER */}

        <div className="bg-green-500 text-black px-8 py-5 text-2xl font-bold">

          CABZI Chat

        </div>

        {/* MESSAGES */}

        <div className="h-[500px] overflow-y-auto p-6 space-y-4">

          {messages.length === 0 && (

            <div className="text-gray-500 text-center mt-40 text-xl">

              No messages yet

            </div>
          )}

          {messages.map((msg, index) => (

            <div
              key={index}
              className="bg-[#1e1e1e] p-4 rounded-2xl"
            >

              <p className="text-green-400 font-bold">

                {msg.sender}

              </p>

              <p className="mt-2 text-lg">

                {msg.text}

              </p>

            </div>
          ))}

        </div>

        {/* INPUT */}

        <div className="flex gap-4 p-6 border-t border-gray-800">

          <input
            type="text"
            placeholder="Type message..."
            value={message}
            onChange={(e) =>
              setMessage(e.target.value)
            }
            className="flex-1 bg-[#1e1e1e] border border-gray-700 rounded-2xl px-5 py-4 outline-none"
          />

          <button
            onClick={sendMessage}
            className="bg-green-500 hover:bg-green-600 transition px-8 rounded-2xl text-black font-bold"
          >

            Send

          </button>

        </div>

      </div>

    </div>
  );
}

export default Chat;