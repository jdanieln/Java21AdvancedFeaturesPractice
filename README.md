
# Proyecto: Aplicación de Características Avanzadas de Java 21

Este proyecto implementa varias características avanzadas de Java 21, como hilos virtuales, Scoped Values, y concurrencia estructurada, mediante la simulación de un sistema de reservas de vuelo. Además, incluye la implementación de un sistema de notificaciones y validación concurrente.

## Funcionalidades Implementadas
- **Procesamiento de Reservas de Vuelo**: Procesa concurrentemente múltiples reservas de vuelo usando hilos virtuales.
- **Sistema de Notificaciones**: Envío de notificaciones personalizadas a los clientes después de procesar su reserva.
- **Validación Concurrente y Cálculo de Costos**: Validación de los datos de la reserva y cálculo del costo total del vuelo en tareas concurrentes usando `StructuredTaskScope`.

## Requisitos
- Java 21 o superior
- Un IDE como Eclipse o IntelliJ IDEA
- Maven o Gradle (opcional, si deseas gestionar dependencias)

## Estructura del Proyecto

```
src/
   ├── com/empresa/reservas/FlightReservationSystem.java    // Parte 1: Sistema de Reservas
   ├── com/empresa/reservas/ReservationWithConcurrency.java // Parte 3: Concurrencia Estructurada
   ├── com/empresa/notificaciones/NotificationService.java  // Parte 2: Sistema de Notificaciones
   └── com/empresa/common/FlightReservation.java            // Clase común para representar una reserva
```

## Clases Principales

- **FlightReservationSystem.java**: Procesa 1000 reservas de vuelo concurrentemente, calculando los costos de cada reserva.
- **NotificationService.java**: Envío de notificaciones personalizadas a los clientes por cada reserva procesada.
- **ReservationWithConcurrency.java**: Procesamiento concurrente con validación de reserva y cálculo de costos, usando `StructuredTaskScope`.

## Ejecución del Proyecto

### Configuración en Eclipse
Asegúrate de que Eclipse esté configurado para usar Java 21:
1. Ve a `Window > Preferences > Java > Installed JREs` y agrega Java 21 si aún no lo has hecho.
2. Selecciona Java 21 como el JRE predeterminado para este proyecto.

### Compilar y ejecutar las clases:
1. Haz clic derecho en la clase `ReservationWithConcurrency.java`.
2. Selecciona "Run As" -> "Java Application".
3. La consola de Eclipse mostrará los resultados del procesamiento de las reservas, validación y cálculo de costos.

## Estructura del Código

### FlightReservationSystem.java
- **Descripción**: Esta clase procesa 1000 reservas de vuelo usando hilos virtuales y Scoped Values.
- **Uso**: Ejecuta `main()` en esta clase para ver cómo se procesan las reservas de vuelo y el cálculo del costo.

### NotificationService.java
- **Descripción**: Implementa un sistema de notificaciones que envía un mensaje personalizado a cada cliente.
- **Uso**: Ejecuta `main()` en esta clase para simular el envío de notificaciones.

### ReservationWithConcurrency.java
- **Descripción**: Este código realiza el procesamiento de las reservas con concurrencia estructurada, validando la reserva y calculando el costo en tareas concurrentes.
- **Uso**: Ejecuta `main()` en esta clase para ver cómo las tareas de validación y cálculo de costos son gestionadas de manera concurrente.

## Ejemplo de Salida

```bash
Validación exitosa para la reserva Flight-0
Reserva procesada: Flight-0 Costo total: $330.0
Validación exitosa para la reserva Flight-1
Reserva procesada: Flight-1 Costo total: $332.0
...
```

## Conceptos Utilizados

- **Hilos Virtuales**: Permiten manejar miles de tareas concurrentes de manera eficiente. Se usan en las clases para procesar múltiples reservas y enviar notificaciones.
- **Scoped Values**: Asocian valores específicos a cada hilo de manera segura, sin necesidad de `ThreadLocal`.
- **Concurrencia Estructurada (StructuredTaskScope)**: Facilita la gestión de múltiples tareas concurrentes que necesitan coordinarse, como la validación de reservas y el cálculo de costos.

## Notas
- Asegúrate de tener Java 21 configurado correctamente para poder aprovechar las características avanzadas que este proyecto implementa.
- Puedes modificar el número de reservas en el código si deseas hacer pruebas más pequeñas o aumentar la carga de procesamiento.

## Preguntas Frecuentes

### ¿Qué beneficios tienen los hilos virtuales?
Los hilos virtuales son más ligeros y eficientes que los hilos tradicionales, permitiendo manejar miles o millones de tareas concurrentes sin consumir grandes recursos del sistema.

### ¿Qué son los Scoped Values?
Los Scoped Values permiten asociar valores a un hilo de manera segura, evitando interferencias o colisiones entre hilos cuando se comparten datos.
