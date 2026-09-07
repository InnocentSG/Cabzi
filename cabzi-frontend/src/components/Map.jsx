import {
  MapContainer,
  TileLayer,
  Marker,
  Popup,
  Polyline,
  useMap,
} from "react-leaflet";

import L from "leaflet";
import { useEffect } from "react";

import "leaflet/dist/leaflet.css";

// Fix Leaflet marker icons
delete L.Icon.Default.prototype._getIconUrl;

L.Icon.Default.mergeOptions({
  iconRetinaUrl:
    "https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon-2x.png",

  iconUrl:
    "https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon.png",

  shadowUrl:
    "https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png",
});

function MapController({
  pickup,
  destination,
}) {
  const map = useMap();

  useEffect(() => {
    if (pickup && destination) {
      const bounds = L.latLngBounds([
        [pickup.lat, pickup.lng],
        [destination.lat, destination.lng],
      ]);

      map.fitBounds(bounds, {
        padding: [50, 50],
      });
    } else if (pickup) {
      map.setView(
        [pickup.lat, pickup.lng],
        15
      );
    }
  }, [
    pickup,
    destination,
    map,
  ]);

  return null;
}

function Map({
  pickup,
  destination,
  routeCoordinates = [],
}) {
  const defaultPosition = [
    28.6139,
    77.2090,
  ];

  return (
    <MapContainer
      center={
        pickup
          ? [
              pickup.lat,
              pickup.lng,
            ]
          : defaultPosition
      }
      zoom={13}
      scrollWheelZoom={true}
      style={{
        height: "450px",
        width: "100%",
        borderRadius: "20px",
      }}
    >

      <TileLayer
        attribution='&copy; OpenStreetMap contributors'
        url="https://tile.openstreetmap.org/{z}/{x}/{y}.png"
      />

      {/* PICKUP */}

      {pickup && (
        <Marker
          position={[
            pickup.lat,
            pickup.lng,
          ]}
        >
          <Popup>
            📍 Pickup Location
          </Popup>
        </Marker>
      )}

      {/* DESTINATION */}

      {destination && (
        <Marker
          position={[
            destination.lat,
            destination.lng,
          ]}
        >
          <Popup>
            🏁 Destination
          </Popup>
        </Marker>
      )}

      {/* REAL ROAD ROUTE */}

      {routeCoordinates.length > 0 && (
        <Polyline
          positions={routeCoordinates}
          weight={6}
        />
      )}

      <MapController
        pickup={pickup}
        destination={destination}
      />

    </MapContainer>
  );
}

export default Map;