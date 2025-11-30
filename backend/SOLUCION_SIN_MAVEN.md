# ✅ Solución: No Tienes Maven Instalado

## ❌ Problema
```
mvn : El término 'mvn' no se reconoce...
```

Maven no está instalado en tu sistema.

---

## ✅ Solución: Usar Android Studio (MÁS FÁCIL) ⭐

Android Studio tiene Maven integrado, no necesitas instalarlo.

### Pasos:

1. **Abre Android Studio**

2. **File → Open**
   - Selecciona la carpeta `backend`
   - Click en "OK"

3. **Espera** a que Android Studio sincronice el proyecto Maven
   - Verás "Maven Sync" en la parte inferior
   - Puede tardar 1-2 minutos la primera vez

4. **Abre el archivo Java**:
   ```
   backend/src/main/java/com/example/budgetflow/BudgetFlowApplication.java
   ```

5. **Click derecho** en el archivo o en el método `main()`

6. **Selecciona**: "Run 'BudgetFlowApplication.main()'"

7. ✅ **El backend se ejecutará automáticamente**

8. **Verás en la consola**:
   ```
   Started BudgetFlowApplication in X.XXX seconds
   Tomcat started on port(s): 8080 (http)
   ```

---

## 🔍 Verificar que Funciona

Abre en tu navegador:
```
http://localhost:8080/api/users
```

Deberías ver `[]` o datos en JSON.

---

## 🛑 Detener el Backend

En Android Studio, click en el botón **Stop** (cuadrado rojo) en la barra superior.

---

## 📝 Alternativa: Instalar Maven

Si prefieres usar la terminal, puedes instalar Maven:

1. Descarga desde: https://maven.apache.org/download.cgi
2. Extrae en: `C:\Program Files\Apache\maven`
3. Configura variables de entorno:
   - `MAVEN_HOME` = `C:\Program Files\Apache\maven`
   - Agrega `%MAVEN_HOME%\bin` al `PATH`
4. Reinicia la terminal
5. Ejecuta: `mvn --version` para verificar

**Pero es más fácil usar Android Studio** 😊

---

## ✅ Recomendación

**Usa Android Studio** - Es la forma más simple y no requiere instalación adicional.

