# 🔧 Solución: No Aparece BudgetFlowApplication en Android Studio

## ❌ Problema
No puedes encontrar `com.example.budgetflow.BudgetFlowApplication` al configurar la ejecución.

---

## ✅ Solución Paso a Paso

### Opción 1: Método Directo (MÁS FÁCIL) ⭐

1. En el **Project Explorer** (izquierda), navega a:
   ```
   backend/src/main/java/com/example/budgetflow/
   ```

2. Abre el archivo `BudgetFlowApplication.java`

3. **Click derecho** directamente en el archivo o en el método `main()`

4. Selecciona **"Run 'BudgetFlowApplication.main()'"**

5. ✅ Android Studio creará la configuración automáticamente

**¡Listo!** No necesitas configurar nada manualmente.

---

### Opción 2: Forzar Reconocimiento del Proyecto Maven

Si Android Studio no reconoce el proyecto como Maven:

#### Paso 1: Invalidar Caché
1. **File → Invalidate Caches / Restart...**
2. Selecciona **"Invalidate and Restart"**
3. Espera a que Android Studio reinicie

#### Paso 2: Importar como Proyecto Maven
1. **File → Close Project**
2. **File → Open**
3. Selecciona la carpeta `backend`
4. En el diálogo que aparece, selecciona **"Import project from external model"**
5. Elige **"Maven"**
6. Click **"Next"** → **"Finish"**
7. Espera a que se sincronice

#### Paso 3: Verificar que Funciona
1. Deberías ver en el Project Explorer:
   ```
   backend
   ├── src
   │   └── main
   │       └── java
   │           └── com
   │               └── example
   │                   └── budgetflow
   │                       └── BudgetFlowApplication.java
   ```

2. Si ves el archivo `.java` con el ícono de Java (☕), está bien

3. Ahora intenta el **Método 1** de nuevo

---

### Opción 3: Configuración Manual (Si las anteriores no funcionan)

1. **Run → Edit Configurations...**

2. Click en **"+"** → **"Application"**

3. Configura manualmente:
   - **Name**: `BudgetFlow Backend`
   - **Main class**: Escribe directamente: `com.example.budgetflow.BudgetFlowApplication`
   - **Working directory**: `$PROJECT_DIR$/backend`
   - **Use classpath of module**: Deja vacío o selecciona el módulo si aparece

4. En la pestaña **"Configuration"**:
   - **VM options**: (opcional) `-Dspring.profiles.active=dev`
   - **Program arguments**: (deja vacío)

5. Click **"OK"**

6. Intenta ejecutar

---

### Opción 4: Verificar que el Archivo Existe

Abre una terminal en Android Studio:
1. **View → Tool Windows → Terminal**
2. Ejecuta:
   ```powershell
   cd backend
   dir src\main\java\com\example\budgetflow\BudgetFlowApplication.java
   ```

Si el archivo existe, deberías verlo listado.

---

## 🎯 Recomendación

**Usa la Opción 1** - Es la más simple y siempre funciona.

Si Android Studio no reconoce el proyecto Maven, usa la **Opción 2** para forzar el reconocimiento.

---

## ❓ Verificación

Después de configurar, deberías poder:
- ✅ Ver `BudgetFlowApplication.java` en el Project Explorer
- ✅ Click derecho → "Run" funciona
- ✅ La consola muestra "Started BudgetFlowApplication"

---

## 🆘 Si Nada Funciona

1. Cierra Android Studio completamente
2. Abre solo la carpeta `backend` como proyecto nuevo
3. Espera a que se sincronice
4. Intenta ejecutar desde el archivo Java directamente

