# SocialCore

## Supuesto



## Diagrama de BBDD y diagrama de clases



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

#### Grupos de usuarios

#### Meetings

#### Login

#### Registro

## Manual de usuario



## Explicaciones de GitProject y reparto de trabajo



## Propuestas de mejora



## Conclusiones y opinión del trabajo realizado

