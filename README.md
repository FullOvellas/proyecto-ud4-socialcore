# SocialCore

## Supuesto

Se trata de una aplicación para crear quedadas de amigos en puntos de interés más o menos equidistantes para todos los integrantes. La aplicación también se encarga de encontrar puntos de interés cercanos. Se pueden además crear comentarios sobre un punto de interés para dar opiniones.

## Diagrama de BBDD y diagrama de clases

### Diagrama de clases

Ver `/classDiagram.mdj` (StarUML) para el diagrama de clases.

### Diagrama entidad-relación

![Diagrama_ER](ER_Diagram.png)

## Manual técnico para desarrolladores

El backend de la aplicación está desarrollado con Spring y el frontend con React. Para comunicar el 
frontend con el backend usamos Hilla que permite crear endpoints para conectar código typescript con 
código java de forma nativa. 

### Endpoints

Los endpoints están gestionados por Hilla de forma que se realiza una llamada asíncrona a código java
desde React. De esta forma se integra mejor los permisos de usuarios y el intercambio de datos.

Para cada endpoint existen determinadas restricciones que permiten bloquear accesos a información por
parte de usuarios que no deberían tener acceso a ella. Esto se gestiona mediante roles de usuario:

- Los usuarios que no iniciaron sesión no tienen ningún rol
- Los usuarios que si que iniciaron sesión pertenecen a *ROLE_USER*
- Los usuarios administradores pertenecen al rol *ROLE_ADMIN*

#### Endpoint de autenticación

Es un endpoint para todos los usuarios que permite obtener las credenciales del usuario que se encuentra usando la
aplicación.

- **IsAnonymous**: permite saber si el usuario que usa la aplicación inició sesión o no
- **getUserName**: permite obtener el nombre del usuario
- **getContextUser**: obtiene el usuario con sus datos (grupos a los que pertenece, email, ...)

#### Endpoint de grupos

Es un endpoint bloqueado para usuarios que no iniciaron sesión. En este endpoint pueden crearse grupos, 
añadir usuarios a grupos, borrar usuarios de grupos o borrar grupos.

### Servicios

#### Autenticación de usuarios

La autenticación de usuarios se realiza mediante dos endpoints:

- **/login**: se envía al servidor el email y la contraseña y se devuelve al cliente la sesión de usuario a la que pertenece
- **/register**: se envía al servidor tanto el correo como el nombre del usuario, contraseña y ubicación

Ambos puntos tienen comprobación de campos tanto en frontend como en el backend antes de realizar ninguna operación

El servicio permite obtener el usuario de la sesión actual empleando el método *getContextUser*. Permite saber también si un usuario es anónimo. Estos son los métodos que son llamados desde el endpoint para dar servicio al frontend.

#### Resto de servicios

Los servicios sobre grupos de usuarios, meetings, comentarios y puntos de interés permiten realizar las operaciones CRUD básicas en su totalidad o parcialmente.

## Explicaciones de GitProject y reparto de trabajo



## Propuestas de mejora

- Incluír un correo de verificación a la hora de registrarse
- Permitir cambiar contraseña mediante un correo
- Permitir varias sesiones de usuario para un navegador
- Panel de administración

## Conclusiones y opinión del trabajo realizado

Este trabajo ha sido, sobre todo, una experiencia de autoaprendizaje. En él, hemos tenido que vérnoslas con ciertos aspectos de Spring y Spring Boot no tratados en el aula y hemos resuelto en la medida de nuestras posibilidades.

También hemos tratado de acercarnos al mundo del desarrollo web moderno al elegir usar React como frontend de nuestra aplicación. Se nos ha hecho evidente la potencia de esta herramienta, si bien también topamos con una pronunciada curva de aprendizaje a la hora de usarla. Haber tomado este camino también nos ha obligado a aprender, reaprender o desempolvar nuestro JavaScript, lo cual es un punto positivo para nuestro desarrollo como profesionales en un mundo en el que este lenguaje, para mejor o peor, es omnipresente.

En cuanto a puntos negativos, como viene siendo habitual en estos proyectos, el tiempo de entrega se ha hecho corto. Esto ha propiciado un clima de ansiedad general que ha impedido disfrutar plenamente del desarrollo de la aplicación y del aprendizaje paralelo, así como disminuído el rendimiento en general.
