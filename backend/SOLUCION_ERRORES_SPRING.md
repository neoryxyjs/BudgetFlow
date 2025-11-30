# 🔧 Solución: Errores de Spring Framework No Encontrado

## ❌ Problema
```
package org.springframework.data.jpa.repository does not exist
cannot find symbol class JpaRepository
cannot find symbol class Repository
```

Las dependencias de Spring no se han descargado o el proyecto no se ha sincronizado.

---

## ✅ Solución Paso a Paso

### Paso 1: Sincronizar Proyecto Maven

1. **Click derecho** en el archivo `pom.xml` en el Project Explorer
2. Selecciona **"Maven"** → **"Reload Project"**
   - O busca en el menú: **View → Tool Windows → Maven**
   - Click en el ícono de refrescar (🔄)

3. **Espera** a que se descarguen las dependencias
   - Verás "Downloading..." en la parte inferior
   - Puede tardar 2-5 minutos la primera vez

### Paso 2: Forzar Descarga de Dependencias

1. Abre la terminal en Android Studio: **View → Tool Windows → Terminal**

2. Ejecuta:
   ```powershell
   cd backend
   mvn dependency:resolve
   ```

   O si no tienes Maven, en Android Studio:
   - **View → Tool Windows → Maven**
   - Expande tu proyecto → **Lifecycle**
   - Doble click en **"dependency:resolve"**

### Paso 3: Invalidar Caché y Reiniciar

1. **File → Invalidate Caches / Restart...**
2. Selecciona **"Invalidate and Restart"**
3. Espera a que Android Studio reinicie
4. Espera a que se sincronice automáticamente

### Paso 4: Verificar que Funciona

Después de sincronizar, los errores rojos deberían desaparecer.

Si aún hay errores:
1. Verifica que `pom.xml` esté correcto
2. Revisa que no haya errores en `pom.xml` (líneas rojas)

---

## 🔍 Verificar Dependencias

Abre `pom.xml` y verifica que tenga estas dependencias:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

Si falta, agrégalo.

---

## ⚡ Solución Rápida

1. **Click derecho en `pom.xml`** → **Maven** → **Reload Project**
2. **Espera** 2-5 minutos
3. ✅ Los errores deberían desaparecer

---

## 🆘 Si No Funciona

1. Cierra Android Studio
2. Elimina la carpeta `.idea` en `backend` (si existe)
3. Abre Android Studio
4. **File → Open** → Selecciona carpeta `backend`
5. Espera a que se sincronice

