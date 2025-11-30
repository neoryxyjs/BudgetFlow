# 🔧 Solución: Errores en pom.xml

## ❌ Errores que Ves

1. **"URI is not registered"** - El IDE no reconoce el esquema XML de Maven
2. **"Cannot resolve symbol 'https://maven.apache.org/xsd/maven-4.0.0.xsd'"** - No puede resolver el esquema

Los warnings de "Typo" son **falsos positivos** (palabras en español), puedes ignorarlos.

---

## ✅ Solución Rápida

### Opción 1: Sincronizar Proyecto Maven (Recomendado)

1. **Click derecho** en `pom.xml`
2. Selecciona **"Maven"** → **"Reload Project"**
3. Espera a que se sincronice (1-2 minutos)

Esto debería resolver los errores del esquema XML.

### Opción 2: Registrar el Esquema Manualmente

1. **File → Settings** (o `Ctrl+Alt+S`)
2. **Languages & Frameworks → Schemas and DTDs**
3. Click en **"+"** (agregar)
4. **URI**: `https://maven.apache.org/xsd/maven-4.0.0.xsd`
5. **File or URL**: `https://maven.apache.org/xsd/maven-4.0.0.xsd`
6. Click **"OK"**
7. Click **"OK"** en Settings

### Opción 3: Invalidar Caché

1. **File → Invalidate Caches / Restart...**
2. Selecciona **"Invalidate and Restart"**
3. Espera a que Android Studio reinicie
4. Espera a que se sincronice automáticamente

---

## ⚠️ Sobre los Warnings de "Typo"

Los warnings de "Typo" (líneas 16, 19, 38, 45, 58, 60, 81) son **falsos positivos**:
- Son palabras en español que el corrector ortográfico no reconoce
- **NO son errores reales**
- Puedes ignorarlos o desactivar el corrector ortográfico para este archivo

Para ignorarlos:
1. Click derecho en el warning
2. Selecciona **"Suppress for file"** o **"Add to dictionary"**

---

## ✅ Verificar que Funciona

Después de sincronizar:
- ✅ Los errores rojos del esquema deberían desaparecer
- ✅ El proyecto debería compilar correctamente
- ⚠️ Los warnings de "Typo" pueden seguir (pero no afectan)

---

## 🎯 Recomendación

**Usa la Opción 1** (Sincronizar Proyecto Maven) - Es la más simple y resuelve el problema automáticamente.

Los warnings de "Typo" puedes ignorarlos, no afectan la funcionalidad.

