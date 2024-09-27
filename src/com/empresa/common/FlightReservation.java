package com.empresa.common;

public record FlightReservation(String flightId, String origin, String destination, double basePrice, int numPassengers) {}
