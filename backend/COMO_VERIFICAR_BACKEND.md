# 🔍 Cómo Verificar que el Backend Funciona

## ✅ Método 1: Verificar en el Navegador

1. **Asegúrate de que el backend esté corriendo**
   - Deberías ver en la consola: `Started BudgetFlowApplication`

2. **Abre tu navegador** y ve a:
   ```
   http://localhost:8080/api/users
   ```
   
3. **Resultado esperado**:
   - ✅ Si funciona: Verás `[]` (array vacío) o una lista de usuarios en JSON
   - ❌ Si no funciona: Error de conexión o página no encontrada

4. **Prueba otros endpoints**:
   - `http://localhost:8080/api/expenses` - Ver gastos
   - `http://localhost:8080/h2-console` - Consola de base de datos H2

---

## ✅ Método 2: Ver Logs en la Consola

Cuando el backend está corriendo, deberías ver en la consola:

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

Started BudgetFlowApplication in X.XXX seconds
Tomcat started on port(s): 8080 (http)
```

Si ves esto, **el backend está funcionando** ✅

---

## ✅ Método 3: Ver Peticiones HTTP

Cuando uses la app Android, deberías ver en la consola del backend:

```
GET /api/users/user123
POST /api/expenses
GET /api/expenses/user/user123
```

Esto confirma que la app está comunicándose con el backend.

---

## 🚀 Cómo Ejecutar desde Cursor/Terminal

### Opción 1: Si tienes Maven instalado

```powershell
cd backend
mvn spring-boot:run
```

### Opción 2: Usar el script batch

```powershell
cd backend
.\ejecutar.bat
```

### Opción 3: Desde Android Studio (Recomendado)

1. Abre `BudgetFlowApplication.java`
2. Click derecho → Run
3. Verás los logs en la consola

---

## 📱 Cambios Visuales en la App

**IMPORTANTE**: Los cambios son principalmente en el **backend**, no visuales.

### Lo que SÍ cambió visualmente:

1. **Tasas de Cambio** (NUEVO):
   - En el Dashboard, deberías ver una tarjeta **"Tasas de Cambio (USD)"**
   - Muestra monedas: EUR, GBP, JPY, CLP, MXN, BRL
   - Esta es la **única diferencia visual** nueva

### Lo que NO cambió visualmente:

- Las pantallas se ven igual
- Los colores son los mismos
- La navegación es la misma
- Los formularios son iguales

### Lo que cambió internamente (NO visible):

- ✅ Los datos ahora vienen de **Spring Boot** en lugar de Firebase
- ✅ Los gastos se guardan en **base de datos H2/PostgreSQL**
- ✅ La comunicación es vía **REST API**

---

## 🧪 Cómo Verificar que Está Usando Spring Boot

### Prueba 1: Ver Logs de la App

1. Abre **Logcat** en Android Studio
2. Filtra por: `BackendApi` o `UserRepository` o `ExpenseRepository`
3. Deberías ver logs como:
   ```
   D/BackendApi: GET http://10.0.2.2:8080/api/users/user123
   D/ExpenseRepository: Guardando gasto para usuario: user123
   ```

### Prueba 2: Verificar Base de Datos

1. Con el backend corriendo, ve a: `http://localhost:8080/h2-console`
2. Configura:
   - **JDBC URL**: `jdbc:h2:mem:budgetflowdb`
   - **User Name**: `sa`
   - **Password**: (vacío)
3. Click **Connect**
4. Ejecuta: `SELECT * FROM USERS;`
5. Ejecuta: `SELECT * FROM EXPENSES;`
6. ✅ Deberías ver los datos que creaste desde la app

### Prueba 3: Agregar Gasto y Verificar

1. Agrega un gasto desde la app
2. Inmediatamente ve a `http://localhost:8080/api/expenses` en el navegador
3. ✅ Deberías ver el gasto en JSON

---

## ❓ ¿Por qué se ve igual?

**Es normal**. Los cambios son arquitectónicos:

- **Antes**: App → Firebase (Firestore)
- **Ahora**: App → Spring Boot → Base de Datos

La interfaz visual es la misma porque:
- ✅ Ya cumplía los requisitos de diseño
- ✅ Los cambios fueron en el backend
- ✅ La experiencia de usuario se mantiene

**Lo importante es que ahora cumples el requisito de microservicios Spring Boot** ✅

---

## ✅ Checklist de Verificación

- [ ] Backend corre (consola muestra "Started")
- [ ] `http://localhost:8080/api/users` responde en navegador
- [ ] App Android se ejecuta
- [ ] Se puede agregar gasto
- [ ] Tarjeta "Tasas de Cambio" aparece en Dashboard
- [ ] Logcat muestra peticiones a `10.0.2.2:8080`
- [ ] Base de datos H2 muestra datos

**Si todo esto funciona, ¡está todo correcto!** 🎉

