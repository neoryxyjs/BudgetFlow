# 🚀 Cómo Probar Todo en Android Studio

## 📋 Guía Completa Paso a Paso

---

## 🎯 Paso 1: Abrir el Backend en Android Studio

### Opción A: Proyecto Separado (Recomendado)

1. **Abre Android Studio**
2. **File → Open** (o `Ctrl+O`)
3. **Navega a**: `C:\Users\Kaki\Desktop\Aplicaciones_Moviles\BudgetFlow\backend`
4. **Selecciona la carpeta `backend`** y click en "OK"
5. **Espera** a que Android Studio sincronice el proyecto Maven
   - Verás "Gradle Sync" o "Maven Sync" en la parte inferior
   - Puede tardar 1-2 minutos la primera vez

### Opción B: Módulo en el Mismo Proyecto

Si prefieres tener todo en un solo proyecto:
1. Abre el proyecto Android principal
2. File → New → Import Module
3. Selecciona la carpeta `backend`

---

## 🏃 Paso 2: Ejecutar el Backend Spring Boot

### Método 1: Desde el Archivo Java (⭐ RECOMENDADO - MÁS FÁCIL)

1. En el **Project Explorer** (izquierda), navega a:
   ```
   backend/src/main/java/com/example/budgetflow/
   ```
2. Abre `BudgetFlowApplication.java`
3. **Click derecho** en el archivo o en el método `main()`
4. Selecciona **"Run 'BudgetFlowApplication.main()'"**  
   - O presiona `Shift+F10`
   - O busca el ícono de "Run" (▶️) en la barra superior
5. Si es la primera vez, Android Studio te pedirá crear una configuración → Click **"OK"**
6. Verás la consola en la parte inferior mostrando:
   ```
   Started BudgetFlowApplication in X.XXX seconds
   ```

**Nota**: Si no ves el archivo `.java` o no aparece la opción "Run", ver `SOLUCION_PROBLEMA_MAIN_CLASS.md`

### Método 2: Crear una Configuración de Run

**IMPORTANTE**: Primero asegúrate de que Android Studio reconozca el proyecto Maven:

1. **File → Invalidate Caches / Restart...** → **Invalidate and Restart**
2. Espera a que Android Studio reinicie
3. Si no aparece el proyecto como Maven:
   - **File → Settings** (o `Ctrl+Alt+S`)
   - **Build, Execution, Deployment → Build Tools → Maven**
   - Verifica que "Maven home directory" esté configurado
   - Click **"OK"**
4. **File → Reload Gradle Project** (si aparece la opción)

Ahora crea la configuración:

1. **Run → Edit Configurations...**
2. Click en **"+"** → **"Application"**
3. Configura:
   - **Name**: `BudgetFlow Backend`
   - **Main class**: 
     - Click en el ícono de carpeta (📁) al lado del campo
     - Busca `BudgetFlowApplication` en la lista
     - O escribe manualmente: `com.example.budgetflow.BudgetFlowApplication`
   - **Use classpath of module**: 
     - Si no aparece "backend", primero necesitas que Android Studio reconozca el proyecto
     - Alternativa: Deja este campo vacío y configura "Working directory" a la carpeta `backend`
   - **Working directory**: `$PROJECT_DIR$/backend`
4. Click **"OK"**
5. Ejecuta con el botón **Run** (▶️) o `Shift+F10`

**Si aún no funciona**, usa el Método 1 (más simple).

### Verificar que Funciona

En la consola deberías ver:
```
Tomcat started on port(s): 8080 (http)
Started BudgetFlowApplication
```

**El backend está corriendo en**: `http://localhost:8080`

---

## 📱 Paso 3: Probar la App Android

### 1. Abre el Proyecto Android Principal

1. **File → Open**
2. Navega a: `C:\Users\Kaki\Desktop\Aplicaciones_Moviles\BudgetFlow`
3. Selecciona la carpeta raíz del proyecto Android

### 2. Configurar la URL del Backend

Abre: `app/src/main/java/com/example/budgetflow/api/BackendApiClient.kt`

**Para Emulador Android:**
```kotlin
private const val BASE_URL = "http://10.0.2.2:8080/api/"
```
✅ Ya está configurado así

**Para Dispositivo Físico:**
1. Encuentra tu IP local:
   ```powershell
   ipconfig
   ```
   Busca "IPv4 Address" (ej: `192.168.1.100`)
2. Cambia en `BackendApiClient.kt`:
   ```kotlin
   private const val BASE_URL = "http://192.168.1.100:8080/api/"
   ```

### 3. Ejecutar la App

1. **Run → Run 'app'** (o `Shift+F10`)
2. Selecciona tu emulador o dispositivo
3. Espera a que la app se instale y ejecute

---

## 🧪 Paso 4: Probar la Integración Completa

### Prueba 1: Login/Registro (Firebase Auth)

1. Abre la app
2. **Registra un nuevo usuario** o **inicia sesión**
3. ✅ Debe funcionar (usa Firebase Auth)

### Prueba 2: Crear Usuario en Backend

Después de login:
1. La app intentará obtener el usuario del backend
2. Si no existe, deberías crear uno
3. Ve al **Dashboard**

### Prueba 3: Agregar un Gasto

1. Click en el botón **"+"** (FAB)
2. Ingresa:
   - Monto: `5000`
   - Categoría: `Comida`
   - Descripción: `Almuerzo`
3. Click en **"Guardar"**
4. ✅ El gasto se guarda en Spring Boot

### Prueba 4: Ver Gastos

1. En el **Dashboard**, deberías ver el gasto creado
2. ✅ Los datos vienen del backend Spring Boot

### Prueba 5: Ver Tasas de Cambio

1. En el **Dashboard**, busca la tarjeta **"Tasas de Cambio"**
2. ✅ Debe mostrar monedas (EUR, GBP, JPY, etc.)
3. Esto viene de la API externa

---

## 🔍 Paso 5: Verificar que Todo Funciona

### Ver Logs del Backend

En Android Studio, en la consola del backend deberías ver:
```
GET /api/users/user123
POST /api/expenses
GET /api/expenses/user/user123
```

### Ver Logs de la App

En **Logcat** (parte inferior de Android Studio):
- Filtra por: `BackendApi` o `UserRepository` o `ExpenseRepository`
- Deberías ver logs de las peticiones HTTP

### Probar Endpoints Manualmente

Abre en el navegador (mientras el backend corre):
- `http://localhost:8080/api/users` - Ver usuarios
- `http://localhost:8080/api/expenses` - Ver gastos
- `http://localhost:8080/h2-console` - Consola de base de datos H2

---

## 🐛 Solución de Problemas

### Error: "Connection refused"

**Problema**: La app no puede conectar al backend

**Solución**:
1. Verifica que el backend esté corriendo (consola muestra "Started")
2. Verifica la URL en `BackendApiClient.kt`
3. Para emulador: usa `10.0.2.2:8080`
4. Para dispositivo físico: usa tu IP local

### Error: "404 Not Found"

**Problema**: El endpoint no existe

**Solución**:
1. Verifica que el backend tenga los controladores correctos
2. Verifica la URL base: debe terminar en `/api/`

### Error: "User not found"

**Problema**: El usuario no existe en el backend

**Solución**:
1. Después de login en Firebase, la app debe crear el usuario en el backend
2. Verifica que `UserRepository.saveUser()` se ejecute después del registro

### El Backend no inicia

**Problema**: Errores al ejecutar Spring Boot

**Solución**:
1. Verifica que tengas Java 17+ instalado
2. Verifica que `pom.xml` esté correcto
3. Revisa los errores en la consola

---

## ✅ Checklist de Pruebas

- [ ] Backend inicia correctamente en Android Studio
- [ ] Backend responde en `http://localhost:8080`
- [ ] App Android se ejecuta
- [ ] Login/Registro funciona (Firebase Auth)
- [ ] Usuario se crea en backend después de registro
- [ ] Se pueden agregar gastos
- [ ] Se pueden ver gastos en Dashboard
- [ ] Las tasas de cambio se muestran
- [ ] Los datos persisten (cerrar y abrir app)

---

## 🎉 ¡Listo!

Si todo funciona, tienes:
- ✅ Firebase Auth funcionando
- ✅ Spring Boot funcionando
- ✅ Integración completa
- ✅ API externa funcionando

**¡Tu app está lista para la entrega!** 🚀

