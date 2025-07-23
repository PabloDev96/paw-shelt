
# 🐾 PawShelt - Backend

**PawShelt** es una aplicación web para la gestión interna de protectoras de animales. Este repositorio contiene la parte del backend desarrollada en **Spring Boot**.

---

## 🔧 Tecnologías utilizadas

- Java 21
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Spring Security (configurado parcialmente)
- H2 Database (modo memoria, para pruebas)
- Maven

---

## 📁 Estructura inicial del proyecto

### ✨ Entidades implementadas

- `Animal`
- `Usuario`

### 📌 Enums

- `EstadoAnimal`: `EN_ADOPCION`, `ADOPTADO`, `EN_CASA_DE_ACOGIDA`
- `TipoAnimal`: `PERRO`, `GATO`
- `Rol`: `ADMIN`, `TRABAJADOR`, `VOLUNTARIO`

---

## ✅ Funcionalidades completadas

### 🐶 Animales

- `GET /animales` – Listar todos los animales
- `GET /animales/{id}` – Obtener animal por ID
- `POST /animales` – Crear un nuevo animal
- `PUT /animales/{id}` – Editar un animal existente
- `DELETE /animales/{id}` – Eliminar un animal

> 🔐 Actualmente estos endpoints están públicos, pero ya se ha comenzado a configurar Spring Security.

---

### 👤 Usuarios

- `POST /usuarios` – Registrar un nuevo usuario
  - Validación para evitar emails duplicados

---

## 🔐 Seguridad

Se ha añadido una configuración base de **Spring Security**:

- `H2 Console`, `/usuarios`, y `/login` están permitidos sin autenticación
- El resto de endpoints están actualmente abiertos, pero listos para protegerse
- Se planea implementar autenticación básica y control por roles próximamente

---

## 🧪 Base de datos

- Por ahora se utiliza **H2 en memoria**, accesible en:
  ```
  http://localhost:8080/h2-console
  ```
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Usuario: `sa`
  - Contraseña: *(vacío)*

---

## 🚧 Próximos pasos

- Implementar autenticación y login con Spring Security
- Proteger endpoints sensibles
- Agregar más entidades: Citas, Adopciones, Donaciones, Gastos...
- Crear relaciones entre entidades
- Validaciones y cifrado de contraseñas
- Conexión con base de datos real (MySQL o PostgreSQL)
- Iniciar el desarrollo del frontend con React (repositorio aparte)

---

## 💡 Autor

Proyecto desarrollado por Pablo como iniciativa personal.

---
