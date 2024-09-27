package com.empresa.reservas;

import com.empresa.common.FlightReservation;
import java.lang.ScopedValue;

public class FlightReservationSystem {

    static final ScopedValue<String> flightScoped = ScopedValue.newInstance();

    public static void main(String[] args) throws InterruptedException {
        FlightReservation[] reservations = new FlightReservation[1000];
        for (int i = 0; i < reservations.length; i++) {
            reservations[i] = new FlightReservation("Flight-" + i, "New York", "Los Angeles", 300 + (i * 2), (i % 6) + 1);
        }

        for (FlightReservation reservation : reservations) {
            Thread thread = Thread.startVirtualThread(() -> {
                ScopedValue.where(flightScoped, reservation.flightId()).run(() -> {
                    procesarReserva(reservation);
                });
            });
            thread.join();
        }
    }

    public static void procesarReserva(FlightReservation reservation) {
        double totalCost = calcularCostoTotal(reservation);
        System.out.println("Reserva procesada: " + flightScoped.get() + 
                           " de " + reservation.origin() + " a " + reservation.destination() + 
                           " con " + reservation.numPassengers() + " pasajeros. Costo total: $" + totalCost);
    }

    public static double calcularCostoTotal(FlightReservation reservation) {
        double impuesto = reservation.basePrice() * 0.10;
        double cargoAdicional = switch (reservation.numPassengers()) {
            case 5, 6 -> 50;
            default -> 0;
        };
        return reservation.basePrice() + impuesto + cargoAdicional;
    }
}
