# Endpoints principales

1. [[#Autenticación de usuarios]]
2. [[#Endpoints de Users|Users]]
	1. [[#Obtener datos de un usuario]]
	2. [[#Inicio de sesión]]
3. [[#Endpoints de Points of Interest|Point of interest]]
	1. [[#Información de un punto]]
	2. [[#Obtener todos los puntos cercanos en x radio]]
	3. [[#Obtener Comments para un punto de interés]]
4. [[#User Group]]
	1. [[#Obtener integrantes de un grupo]]
	2. [[#Obtener Meetings realizados]]
5. [[#Meeting]]
	1. [[#Obtener información de una quedada]]
	2. [[#Obtener itinerario de una quedada]]
6. [[#Itinerary]]
	1. [[#Obtener puntos de interés del itinerario]]
7. [[#Respuesta para errores o prohibiciones]]

## Autenticación de usuarios

Para autenticar un usuario se realiza una petición POST

Bastante WIP

### Inicio de sesión

- **Detalles**: permite iniciar sesión como usuario
- **Ruta base**:
- **Autorizaciones necesarias**: 

### Registro

- **Detalles**: permite registrar a un nuevo usuario
- **Ruta base**:
- **Autorizaciones necesarias**: 

## Endpoints de Users

- **Detalles**: endpoint para peticiones de información referentes a un usuario
- **Ruta base**:  `/api/user`
- **Autorizaciones necesarias**: hace falta haber iniciado sesión. Solo se devolverán los datos completos de un usuario si la sesión de usuario coincide con la del usuario del cuál se piden los datos. 

### Obtener datos de un usuario

- **Detalles**: Se devuelve un objeto con los datos del usuario pedido a partir de su ID
- **Ruta base**: `/api/user/{user_id}`
- **Autorizaciones necesarias**: ninguna a mayores de las de cualquier endpoint de users

## Endpoints de Points of Interest

- **Detalles**: endpoint para peticiones sobre puntos de interés
- **Ruta base**:  `/api/poi`
- **Autorizaciones necesarias**: no hace falta ninguna especial

### Información de un punto

- **Detalles**: devolverá un objeto con toda la información de un punto de interés
- **Ruta**:  `/api/poi?id={id_poi}`

### Obtener todos los puntos cercanos en x radio

- **Detalles**: devolverá una lista de puntos de interés cercanos a la ubicación del usuario en un radio determinado. La ubicación del usuario puede ser la declarada como "casa"
- **Ruta**:  `/api/poi/search?radius={radio_km}`

### Obtener Comments para un punto de interés

- **Detalles**: devolverá los comentarios de usuarios respecto a un punto de interés
- **Ruta**:  `/api/poi/comment?id={id_poi}`


## User Group

- **Detalles**: endpoint para peticiones sobre grupos de usuarios
- **Ruta base**:  `/api/group`
- **Autorizaciones necesarias**: hace falta que la petición la realize un usuario que inició sesión y que además pertenezca al grupo del cuál se piden los datos

### Obtener Meetings realizados

- **Detalles**: endpoint para obtener todos los meetings realizados por un grupo
- **Ruta base**:  `/api/group/meetings?id={id_grupo}`
- **Autorizaciones necesarias**: hace falta que la petición la realize un usuario que inició sesión y que además pertenezca al grupo del cuál se piden los datos

### Obtener detalles de un grupo

- **Detalles**: endpoint para devolver los detalles de un grupo a partir de su ID
- **Ruta base**:  `/api/group?id={id_grupo}`
- **Autorizaciones necesarias**: hace falta que la petición la realize un usuario que inició sesión y que además pertenezca al grupo del cuál se piden los datos

## Meeting

- **Detalles**: endpoint para peticiones sobre quedadas
- **Ruta base**:  `/api/meeting`
- **Autorizaciones necesarias**: hace falta que la petición la realize un usuario que inició sesión y que además pertenezca al grupo que realizó la quedada

### Obtener información de una quedada

- **Detalles**: endpoint para obtener una quedada a partir de su ID
- **Ruta**:  `/api/meeting?id={id_meeting}`
- **Autorizaciones necesarias**: hace falta que la petición la realize un usuario que inició sesión y que además pertenezca al grupo que realizó la quedada

### Obtener itinerario de una quedada

- **Detalles**: endpoint para peticiones 
- **Ruta base**:  `/api/meeting/itinerary?id={id_quedada}`
- **Autorizaciones necesarias**: hace falta que la petición la realize un usuario que inició sesión y que además pertenezca al grupo que realizó la quedada

## Itinerary

- **Detalles**: endpoint para obtener información de itinerarios
- **Ruta base**:  `/api/itinerary`
- **Autorizaciones necesarias**: ninguna

### Obtener puntos de interés del itinerario

- **Detalles**: endpoint para obtener los puntos de interés de un itinerario
- **Ruta**:  `/api/itinerary/poi?id={id_itinerario}`
- **Autorizaciones necesarias**: ninguna

## Respuesta para errores o prohibiciones

- **Detalles**: endpoint para devolver errores o un mensaje especificando que no se tiene acceso a x endpoint por permisos
- **Ruta**:  cualquiera
- **Autorizaciones necesarias**: ninguna