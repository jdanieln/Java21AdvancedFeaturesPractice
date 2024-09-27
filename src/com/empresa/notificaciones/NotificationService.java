package com.empresa.notificaciones;

import com.empresa.common.FlightReservation;
import java.lang.ScopedValue;

public class NotificationService {

    static final ScopedValue<String> notificationScoped = ScopedValue.newInstance();

    public static void main(String[] args) throws InterruptedException {
        FlightReservation[] reservations = new FlightReservation[1000];
        for (int i = 0; i < reservations.length; i++) {
            reservations[i] = new FlightReservation("Flight-" + i, "New York", "Los Angeles", 300 + (i * 2), (i % 6) + 1);
        }

        for (FlightReservation reservation : reservations) {
            Thread thread = Thread.startVirtualThread(() -> {
                ScopedValue.where(notificationScoped, 
                                  "Estimado cliente, su reserva de vuelo " + reservation.flightId() +
                                  " de " + reservation.origin() + " a " + reservation.destination() +
                                  " ha sido procesada exitosamente. El costo total es $" + calcularCostoTotal(reservation) + "."
                                  ).run(() -> {
                    enviarNotificacion();
                });
            });
            thread.join();
        }
    }

    public static void enviarNotificacion() {
        System.out.println(notificationScoped.get());
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
