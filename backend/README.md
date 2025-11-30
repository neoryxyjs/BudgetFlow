# BudgetFlow Backend - Microservicios Spring Boot

## Descripción
Microservicios REST desarrollados en Spring Boot para la aplicación BudgetFlow.

## Requisitos
- Java 17 o superior
- Maven 3.6+

## Configuración

### Base de Datos
Por defecto usa H2 (base de datos en memoria) para desarrollo. Para producción, configura PostgreSQL en `application.properties`.

### Ejecutar la aplicación
```bash
cd backend
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## Endpoints

### Users
- `GET /api/users` - Obtener todos los usuarios
- `GET /api/users/{id}` - Obtener usuario por ID
- `POST /api/users` - Crear usuario
- `PUT /api/users/{id}` - Actualizar usuario
- `DELETE /api/users/{id}` - Eliminar usuario

### Expenses
- `GET /api/expenses` - Obtener todos los gastos
- `GET /api/expenses/user/{userId}` - Obtener gastos por usuario
- `GET /api/expenses/{id}` - Obtener gasto por ID
- `POST /api/expenses` - Crear gasto
- `PUT /api/expenses/{id}` - Actualizar gasto
- `DELETE /api/expenses/{id}` - Eliminar gasto

## H2 Console
Accede a la consola H2 en: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:budgetflowdb`
- Username: `sa`
- Password: (vacío)

