# FTC Robotics - Into the Deep
Este repositorio contiene loa códigos que desarrollé para la temporada *Into the Deep* 2024-2025 de FTC Robotics para Vitronik Ragnarok 24198 equipo de robótica de preparatoria Tecmilenio, campus San Luis Potosí. Se han implementado diversas funcionalidades avanzadas para mejorar el rendimiento del robot en el campo de competencia.

## Funcionalidades Implementadas
### Control de York (Robot)
- Transición al uso de **OnBot Java**.
- Implementación de **odometría** y **automatización** para mejorar la precisión del robot.
- Optimización del desempeño del robot para mayor eficacia y precisión.

### Funcionamiento del Elevador
- Uso de **encoders** en los motores para preconfigurar alturas de canastas y chamber.
- **Controles dinámicos** mediante botones, pad direccional y joystick derecho.
- **Luces LED** para indicar el estado al driver.
- Sistema de **cambio de modo** entre *Basket* y *Chamber*.

### Funcionamiento del Brazo
- Control de altura con **bumpers del gamepad** y **contador de toques**.
- Asignación de posiciones predefinidas para mejorar el control y eficiencia del mecanismo.
- Reducción de carga dinámica en el servo.

### Odometría
- **Encoders** para rastrear la posición en tiempo real *(x, y, θ)*.
- **Corrección de errores** con ajuste dinámico de velocidad.
- Uso de **IMU** para asegurar giros y detenciones precisas.
- Implementación de **tolerancia** para evitar movimientos innecesarios y optimizar el desempeño autónomo.
- Métodos personalizados: `drive`, `strafe` y `turnTo` en la clase `Odometry`.

