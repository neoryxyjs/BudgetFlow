# 🔧 Solución: Error con Maven

## ❌ Problema
```
mvn : El término 'mvn' no se reconoce...
```

Esto significa que **Maven no está instalado** o no está en el PATH.

---

## ✅ Soluciones (Elige una)

### Opción 1: Usar Android Studio / IntelliJ IDEA (MÁS FÁCIL) ⭐

1. Abre Android Studio
2. File → Open → Selecciona la carpeta `backend`
3. Espera a que se sincronice el proyecto
4. Busca `BudgetFlowApplication.java`
5. Click derecho → Run 'BudgetFlowApplication.main()'

**¡Listo!** Android Studio tiene Maven integrado.

---

### Opción 2: Instalar Maven Manualmente

#### Paso 1: Descargar Maven
1. Ve a: https://maven.apache.org/download.cgi
2. Descarga: `apache-maven-3.9.5-bin.zip`
3. Extrae en: `C:\Program Files\Apache\maven`

#### Paso 2: Configurar Variables de Entorno

1. Abre "Variables de entorno" en Windows
2. Crea/edita `MAVEN_HOME` = `C:\Program Files\Apache\maven`
3. Edita `PATH` y agrega: `%MAVEN_HOME%\bin`
4. Verifica que `JAVA_HOME` esté configurado (debe apuntar a tu JDK)

#### Paso 3: Verificar
```powershell
mvn --version
```

#### Paso 4: Ejecutar
```powershell
cd backend
mvn spring-boot:run
```

---

### Opción 3: Usar Maven Wrapper (Recomendado)

El Maven Wrapper descarga Maven automáticamente.

#### Paso 1: Descargar maven-wrapper.jar

1. Ve a: https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/
2. Descarga: `maven-wrapper-3.2.0.jar`
3. Colócalo en: `backend\.mvn\wrapper\maven-wrapper.jar`

#### Paso 2: Ejecutar con Wrapper

```powershell
cd backend
.\mvnw.cmd spring-boot:run
```

---

### Opción 4: Usar Gradle (Alternativa)

Si prefieres Gradle (ya lo tienes instalado para Android):

1. Puedo convertir el proyecto a Gradle
2. Ejecutarías: `gradlew bootRun`

---

## 🚀 Recomendación

**Usa la Opción 1 (Android Studio)** - Es la más fácil y rápida.

Si necesitas usar línea de comandos, usa la **Opción 3 (Maven Wrapper)**.

---

## ❓ ¿Qué opción prefieres?

Dime cuál opción quieres usar y te ayudo a configurarla.

