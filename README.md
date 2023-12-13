# Truco Web

Truco web, un juego de cartas clásico implementado con Spring MVC y Bootstrap.

## Funcionamiento Básico del Truco

En este proyecto, hemos implementado las reglas básicas del Truco argentino. Los jugadores compiten para ganar rondas utilizando estrategias y tácticas específicas. La aplicación web permite a los usuarios jugar al Truco de manera interactiva y seguir la puntuación.

### Características Principales
- **Cartas del Jugador:** Visualiza las cartas en tu mano.
- **Cartas de la IA:** Se visualiza el reverso de las cartas del la IA para indicar cuantas cartas se llevan jugadas.
- **Sistema de Puntos:** Mantiene un seguimiento de los puntos ganados por el jugador y la IA.
- **Cantos:** Implementación de cantos como Truco, Retruco, Envido, y más.

## Sistema de Ayudas a través de MercadoPago

Hemos integrado un sistema de ayudas mediante MercadoPago para mejorar la experiencia del jugador. Las ayudas pueden ser adquiridas para obtener ventajas durante el juego.

### Ayudas Disponibles
1. **Repartir Ayuda:** Permite volver a repartir las cartas.
2. **Intercambiar Ayuda:** Facilita el intercambio de cartas con el oponente.
3. **+3 Puntos Ayuda:** Agrega 3 puntos al marcador del jugador.

### Cómo Obtener Ayudas
1. Haz clic en el botón de ayudas en la esquina inferior derecha.
2. Selecciona la ayuda que deseas utilizar.
3. Puedes comprar ayudas adicionales haciendo clic en el enlace de compra.

## Requisitos del Proyecto
- Java 11 o superior.
- Maven para la construcción del proyecto.

## Instrucciones de Ejecución
1. Clona el repositorio: `git clone https://github.com/patricio97m/TrucoWeb.git`
2. Navega al directorio del proyecto: `cd TrucoWeb`
3. Ejecuta la aplicación: `mvn spring-boot:run`

---

## Tecnologías utilizadas
- Lenguaje backend utilizado: Java 11
- Framework utilizado: Spring MVC
- Servidor web utilizado: Jetty server
- Editor de código fuente: IntelliJ IDEA
- Librería CSS: Bootstrap 5.3.2
- Base de datos: Mysql

**Nota:** Asegúrate de tener las dependencias necesarias y configuraciones de MercadoPago para un funcionamiento adecuado.
