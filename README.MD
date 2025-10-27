# 📚 app-course-management — Gestión de cursos

## Descripción
Módulo responsable de gestionar la estructura completa de formación: MÓDULOS (conjuntos de cursos), CURSOS, CAPÍTULOS y MATERIAL asociado a cada capítulo. 
- Los administradores gestionan CRUD y relaciones entre estas entidades.
- Los usuarios pueden consumir contenido, marcar capítulos como vistos, consultar su histórico y obtener reconocimientos cuando completan cursos.

## Entidades y relaciones principales
- Módulo (Module)
  - id, título, descripción
- Curso (Course)
  - id, título, descripción, duración estimada
- Capítulo (Chapter)
  - curso, orden, título, descripcion, contenido
- Material (Material)
  - id, nombre, descripcion, tipo, url

### Entidades derivadas
- Historia de Usuario (UserHistory)
  - id, usurario, capítulo, fechaInicio, fechaFin
- Reconocimiento (Recognition)
  - id, usuario, curso, fechaEmision

Relaciones: Module \*..\* Course; Course 1..\* Chapter; Chapter 1..\* Material. UserProgress vincula usuarios con capítulos; Recognition se genera al completar un Course.

## Arquitectura
- Capas: Repository → Service → Controller.
- Persistencia: Spring Data JPA (esquema generado desde entidades).
- Seguridad: Spring Security + JWT (roles: ADMIN, USER).
- Estado: stateless APIs, front consume endpoints REST/JSON.

## Endpoints principales (resumen)

Usuario — Consumo y progreso
- GET /api/v1/modules
  - Listar módulos
- GET /api/v1/modules/{id}
  - Obtener módulo con índice de cursos
- GET /api/v1/courses/{id}
  - Obtener curso con índice de capítulos y materiales
- POST /api/v1/courses/{courseId}/chapters/{chapterId}/mark-seen
  - Marcar capítulo como visto (registra UserHistory)
- GET /api/v1/users/{userId}/progress
  - Histórico de capítulos vistos y % de avance por curso
- GET /api/v1/users/{userId}/courses/{courseId}/recognition
  - Obtener/Generar reconocimiento si curso completado
- GET /api/v1/materials/{id}
  - Obtener un material dado.
- GET /api/v1/courses/{courseId}/chapters/{chapterId}/materials
  - Listar materiales de un capítulo.

Admin — Módulos / Cursos / Capítulos / Material
- POST /api/v1/modules
  - Crear módulo
- PUT /api/v1/modules/{id}
  - Actualizar módulo
- DELETE /api/v1/modules/{id}
  - Eliminar módulo (validar integridad o cascada)
- POST /api/v1/courses
  - Crear curso
- PUT /api/v1/courses/{id}
- DELETE /api/v1/courses/{id}
- POST /api/v1/modules/{id}/courses
  - Vincular un curso a un módulo.
- DELETE /api/v1/modules/{moduleId}/courses/{courseId}
  - Desvincular un curso de un módulo.
- POST /api/v1/courses/{courseId}/chapters/
  - Añadir capítulo a curso
- PUT /api/v1/chapters/{id}
- DELETE /api/v1/chapters/{id}
- POST /api/v1/materials
- PUT /api/v1/materials/{id}
- DELETE /api/v1/materials/{id}
- POST /api/v1/chapters/{chapterId}/materials
  - Añadir material a capítulo

## Modelos JSON (ejemplos)

Crear módulo (201 Created)
Solicitud:
```json
{
  "title": "Módulo de Seguridad",
  "description": "Cursos sobre seguridad de la información",
}
```
Respuesta:
```json
{
  "moduleId": 13
}
```

Crear curso (201 Created)
Solicitud:
```json
{
  "title": "Introducción a OWASP",
  "description": "Fundamentos de seguridad web",
  "estimatedDurationMinutes": 120,
  "external_url": null
}
```
Respuesta:
```json
{
  "courseId": 19
}
```

Asociar curso a módulo (201 Created)
Solicitud:
```json
{
  "courseId": 15
}
```

Añadir capítulo (201 Created)
Solicitud:
```json
{
  "courseId": 17,
  "order": 1,
  "title": "Top 10 OWASP",
  "description": "Resumen y ejemplos",
  "content": "...Markdown Text..."
}
```

Añadir material (201 Created)
Solicitud:
```json
{
  "name": "Vídeo de Ejemplo",
  "description": null,
  "type": "video",
  "filename": "owasp-intro.mp4",
  "data": "...Binary Data String..."
}
```
Respuesta:
```json
{
  "id": 18,
  "name": "Vídeo de Ejemplo",
  "description": null,
  "type": "video",
  "url": "some-server.example/some-bucket/owasp-intro.mp4",
}
```


Marcar capítulo como visto (200 OK)
POST /api/courses/{courseId}/chapters/{chapterId}/mark-seen
Respuesta:
```json
{
  "courseId": 12,
  "chapterId": 13,
  "userId": 17,
  "startedAt": "2025-09-28T12:00:00Z",
  "endedAt": "2025-10-01T12:00:00Z"
}
```

Obtener reconocimiento (200 OK)
Respuesta:
```json
{
  "recognitionId": 15,
  "userId": 17,
  "courseId": 18,
  "issuedAt": "2025-10-02T09:00:00Z",
  "metadata": {
    "grade": "APROBADO",
    "durationMinutes": 120
  }
}
```

Errores comunes:
- 400 Bad Request — validación, datos faltantes.
- 401 Unauthorized — token inválido/expirado.
- 403 Forbidden — rol insuficiente para admin.
- 404 Not Found — recurso inexistente.
- 409 Conflict — conflictos de integridad (p. ej. orden duplicado).

## Casos de uso clave
- Gestión completa por administradores: CRUD + reordenamiento de capítulos/curso.
- Seguimiento de avance por usuario y agregados por curso/módulo.
- Generación de reconocimientos automáticos al completar todos los capítulos de un curso.
- Soporte para múltiples tipos de material (video, documento, enlace) y control de acceso.

## Ejemplos rápidos (curl)

Crear módulo:
```bash
curl -X POST http://localhost:8080/api/v1/modules \
  -H "Authorization: Bearer <TOKEN_ADMIN>" \
  -H "Content-Type: application/json" \
  -d '{"title":"Módulo de Seguridad","description":"Cursos sobre seguridad","order":1}'
```

Crear curso:
```bash
curl -X POST http://localhost:8080/api/v1/courses \
  -H "Authorization: Bearer <TOKEN_ADMIN>" \
  -H "Content-Type: application/json" \
  -d '{"title":"OWASP Intro","description":"Seguridad web","moduleId":"module-uuid","estimatedDurationMinutes":120}'
```

Añadir capítulo:
```bash
curl -X POST http://localhost:8080/api/v1/courses/{courseId}/chapters \
  -H "Authorization: Bearer <TOKEN_ADMIN>" \
  -H "Content-Type: application/json" \
  -d '{"title":"Top 10 OWASP","summary":"Resumen","order":1}'
```

Marcar capítulo visto (usuario):
```bash
curl -X POST http://localhost:8080/api/courses/{courseId}/chapters/{chapterId}/mark-seen \
  -H "Authorization: Bearer <TOKEN_USER>" \
  -H "Content-Type: application/json" \
  -d '{"userId":"user-uuid"}'
```

Obtener histórico:
```bash
curl -X GET http://localhost:8080/api/users/{userId}/progress \
  -H "Authorization: Bearer <TOKEN_USER>"
```

Generar o obtener reconocimiento:
```bash
curl -X GET http://localhost:8080/api/users/{userId}/courses/{courseId}/recognition \
  -H "Authorization: Bearer <TOKEN_USER>"
```

## Notas de implementación
- Validar integridad al eliminar (decidir cascada o bloqueo).
- Calcular avance por curso con porcentaje de capítulos vistos.
- Emitir evento/cola al generar reconocimiento para integración con servicios externos (emails, almacenamiento de certificados).
- Registrar auditoría de cambios (quién creó/actualizó eliminar).
