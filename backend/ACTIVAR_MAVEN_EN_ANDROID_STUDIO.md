# 🔧 Cómo Activar Maven en Android Studio

## ❌ Problema
No aparece la opción "Maven" al hacer click derecho en `pom.xml`

Android Studio no ha reconocido el proyecto como Maven.

---

## ✅ Solución Paso a Paso

### Método 1: Abrir Ventana Maven Manualmente

1. **View → Tool Windows → Maven**
   - O presiona: `Alt+6` (en Windows)
   - O busca en la barra lateral derecha el ícono de Maven (⚙️)

2. Si aparece la ventana Maven:
   - Deberías ver tu proyecto `backend` listado
   - Expande `backend` → **Lifecycle**
   - Click derecho en **"Reload Project"** o doble click en **"validate"**

3. Si NO aparece la ventana Maven:
   - Ve al **Método 2**

---

### Método 2: Importar Proyecto como Maven

1. **File → Close Project**

2. **File → Open**

3. Navega a la carpeta `backend` y selecciónala

4. En el diálogo que aparece, busca:
   - **"Import project from external model"**
   - O **"Open as Project"**

5. Si aparece "Import project from external model":
   - Selecciona **"Maven"**
   - Click **"Next"** → **"Next"** → **"Finish"**

6. Si solo aparece "Open as Project":
   - Selecciona esa opción
   - Android Studio debería detectar automáticamente que es Maven

7. **Espera** a que se sincronice (2-5 minutos)

---

### Método 3: Configurar Maven en Settings

1. **File → Settings** (o `Ctrl+Alt+S`)

2. **Build, Execution, Deployment → Build Tools → Maven**

3. Verifica:
   - **Maven home directory**: Debería estar configurado
   - Si está vacío, busca la ruta de Maven o usa la que viene con Android Studio

4. **Maven → Importing**
   - Marca: **"Import Maven projects automatically"**
   - Marca: **"Automatically download"**

5. Click **"OK"**

6. **File → Reload Gradle Project** (si aparece)

---

### Método 4: Forzar Reconocimiento

1. **File → Invalidate Caches / Restart...**

2. Selecciona **"Invalidate and Restart"**

3. Espera a que Android Studio reinicie

4. **File → Open** → Selecciona carpeta `backend`

5. En el diálogo, si pregunta cómo abrir:
   - Selecciona **"Open as Project"** o **"Import project from external model" → Maven**

---

## 🔍 Verificar que Funciona

Después de cualquiera de los métodos:

1. Deberías ver en el Project Explorer:
   - Un ícono de Maven (⚙️) o carpeta "Maven"
   - O el proyecto con estructura Maven reconocida

2. **View → Tool Windows → Maven** debería mostrar tu proyecto

3. Click derecho en `pom.xml` debería mostrar opciones de Maven

---

## ⚡ Solución Rápida (Recomendada)

**Método 2** es el más confiable:

1. **File → Close Project**
2. **File → Open** → Selecciona `backend`
3. Si pregunta, elige **"Import project from external model" → Maven**
4. Espera a que se sincronice

---

## 🆘 Si Nada Funciona

1. Cierra Android Studio completamente

2. Elimina (si existe):
   - `backend/.idea` (carpeta oculta)

3. Abre Android Studio

4. **File → Open** → Selecciona `backend`

5. Espera a que detecte Maven automáticamente

---

## ✅ Después de Activar Maven

Una vez que Maven esté activo:

1. **View → Tool Windows → Maven**
2. Expande tu proyecto → **Lifecycle**
3. Doble click en **"validate"** o **"compile"**
4. Esto descargará las dependencias

¡Listo! 🚀

