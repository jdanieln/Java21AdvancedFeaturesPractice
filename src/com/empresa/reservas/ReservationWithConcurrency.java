package com.empresa.reservas;

import java.util.concurrent.StructuredTaskScope;

import com.empresa.common.FlightReservation;

import java.lang.ScopedValue;
import java.util.concurrent.ExecutionException;

public class ReservationWithConcurrency {

    static final ScopedValue<String> flightScoped = ScopedValue.newInstance();

    public static void main(String[] args) throws InterruptedException {
        FlightReservation[] reservations = new FlightReservation[1000];
        for (int i = 0; i < reservations.length; i++) {
            reservations[i] = new FlightReservation("Flight-" + i, "New York", "Los Angeles", 300 + (i * 2), (i % 6) + 1);
        }

        for (FlightReservation reservation : reservations) {
            Thread thread = Thread.startVirtualThread(() -> {
                ScopedValue.where(flightScoped, reservation.flightId()).run(() -> {
                    try {
                        procesarReservaConConcurrencia(reservation);
                    } catch (InterruptedException | ExecutionException e) {
                        System.err.println("Error procesando la reserva: " + e.getMessage());
                    }
                });
            });
            thread.join();
        }
    }

    public static void procesarReservaConConcurrencia(FlightReservation reservation) throws InterruptedException, ExecutionException {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            // Tarea 1: Validar reserva
            scope.fork(() -> {
                validarReserva(reservation);
                return null; // Retorna null porque validarReserva no devuelve un valor
            });

            // Tarea 2: Calcular costo total
            var totalCostTask = scope.fork(() -> calcularCostoTotal(reservation));

            // Esperar a que ambas tareas terminen
            scope.join();
            
            // Verifica si alguna tarea ha fallado
            try {
                scope.throwIfFailed();
            } catch (ExecutionException e) {
                System.err.println("Error en el procesamiento de la tarea: " + e.getMessage());
                throw e;
            }

            // Obtener el resultado del cálculo del costo total
            double totalCost = totalCostTask.get(); // Obtener el resultado de la tarea totalCost

            System.out.println("Reserva procesada: " + flightScoped.get() + 
                               " Costo total: $" + totalCost);
        }
    }

    public static void validarReserva(FlightReservation reservation) {
        if (reservation.origin().equals(reservation.destination())) {
            throw new IllegalArgumentException("El origen y el destino no pueden ser iguales.");
        }
        if (reservation.numPassengers() <= 0) {
            throw new IllegalArgumentException("Debe haber al menos 1 pasajero.");
        }
        System.out.println("Validación exitosa para la reserva " + reservation.flightId());
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
