const pageData = {
  about: {
    title: "About CABZI",
    text: "CABZI is a ride, parcel, sharing, and driver management platform for daily city travel."
  },
  careers: {
    title: "Careers",
    text: "CABZI career openings for operations, support, driver onboarding, and technology will appear here."
  },
  blog: {
    title: "Blog",
    text: "Read CABZI updates about safer rides, shared travel, driver programs, and platform improvements."
  },
  investors: {
    title: "Investors",
    text: "CABZI investor information, growth metrics, and business updates are managed by the admin team."
  },
  help: {
    title: "Help Center",
    text: "For ride, payment, driver, staff, or account help, email sumit257210@gmail.com."
  },
  safety: {
    title: "Safety",
    text: "CABZI uses account verification, driver KYC, ride tracking, support, and emergency contact flows."
  },
  privacy: {
    title: "Privacy Policy",
    text: "CABZI protects profile, ride, payment, and verification data inside the application workflow."
  },
  terms: {
    title: "Terms & Conditions",
    text: "Users, drivers, staff, and admins must use CABZI accounts responsibly and keep documents accurate."
  },
  "download-google-play": {
    title: "Google Play",
    text: "The CABZI Android app download page will be connected here."
  },
  "download-app-store": {
    title: "App Store",
    text: "The CABZI iOS app download page will be connected here."
  }
};

function StaticPage({ type }) {

  const page =
    pageData[type] || pageData.help;

  return (

    <div className="min-h-screen bg-[#0f0f0f] text-white p-10">
      <div className="max-w-4xl mx-auto bg-[#1a1a1a] border border-gray-800 rounded-3xl p-10">
        <h1 className="text-5xl font-bold text-green-400">{page.title}</h1>
        <p className="text-gray-300 text-xl leading-8 mt-6">{page.text}</p>
      </div>
    </div>
  );
}

export default StaticPage;
