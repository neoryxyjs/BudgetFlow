# 💻 Comandos para Ejecutar el Backend desde Terminal

## 🚀 Comando Principal

### Si tienes Maven instalado:
```bash
mvn spring-boot:run
```

### Si NO tienes Maven (usa el wrapper):
```bash
.\mvnw.cmd spring-boot:run
```
(Windows) o
```bash
./mvnw spring-boot:run
```
(Linux/Mac)

---

## 📍 Ubicación

**IMPORTANTE**: Debes estar en la carpeta `backend`:

```bash
cd backend
mvn spring-boot:run
```

O desde la raíz del proyecto:
```bash
cd backend && mvn spring-boot:run
```

---

## ✅ Verificar que Funciona

Después de ejecutar, deberías ver:

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

---

## 🛑 Detener el Backend

Presiona: `Ctrl + C`

---

## 🔧 Otros Comandos Útiles

### Compilar sin ejecutar:
```bash
mvn clean install
```

### Ejecutar tests:
```bash
mvn test
```

### Ver dependencias:
```bash
mvn dependency:tree
```

---

## ⚠️ Si No Funciona

### Error: "mvn no se reconoce"
- Maven no está instalado o no está en el PATH
- Solución: Usa `.\mvnw.cmd` en su lugar

### Error: "Java no encontrado"
- Verifica que JAVA_HOME esté configurado
- Verifica que tengas Java 17+ instalado

### Error: "Puerto 8080 en uso"
- Otro proceso está usando el puerto
- Solución: Cierra otras aplicaciones o cambia el puerto en `application.properties`

---

## 📝 Resumen Rápido

### Si tienes Maven instalado:
```powershell
cd backend
mvn spring-boot:run
```

### Si NO tienes Maven (PowerShell):
```powershell
cd backend
.\mvnw.cmd spring-boot:run
```

**Nota**: Si el wrapper no funciona, usa Android Studio (tiene Maven integrado):
1. Abre `BudgetFlowApplication.java` en Android Studio
2. Click derecho → Run 'BudgetFlowApplication.main()'

---

## 🎯 Solución Recomendada: Android Studio

**La forma más fácil** si no tienes Maven instalado:

1. Abre Android Studio
2. File → Open → Selecciona carpeta `backend`
3. Abre `src/main/java/com/example/budgetflow/BudgetFlowApplication.java`
4. Click derecho → **Run 'BudgetFlowApplication.main()'**
5. ✅ El backend se ejecutará automáticamente

¡Listo! 🚀

