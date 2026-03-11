# Taller: Concurrencia y Paralelismo en Java

**Autores:** Darwin Mora, David Perdomo

Proyectos Java 21 + Spring Boot (arquitectura en capas):

- **ejercicio1** – Paralelismo: suma 1 a 1.000.000 con 4 hilos.
- **ejercicio2** – Concurrencia: cuenta bancaria compartida, 3 clientes retiran 400 (sincronización).

Ejecutar desde cada carpeta: `mvn spring-boot:run` o ejecutar la clase `ConcurrenciaApplication` desde el IDE.

---

## Preguntas para análisis

### 1. ¿Qué diferencia existe entre paralelismo y concurrencia?

- **Concurrencia**: varias tareas avanzan en un mismo intervalo de tiempo; pueden alternarse en uno o más núcleos. No implica que se ejecuten al mismo tiempo.
- **Paralelismo**: varias tareas se ejecutan realmente al mismo tiempo, en distintos núcleos o CPUs.

En los ejercicios: en el **ejercicio 1** se reparte el trabajo en 4 hilos que pueden ejecutarse en paralelo (cada uno en su rango, sin compartir datos que modifiquen). En el **ejercicio 2** varios hilos comparten la misma cuenta; lo importante es coordinar el acceso (sincronización), aunque en la práctica solo uno retira a la vez.

---

### 2. ¿Qué problema ocurre cuando varios hilos acceden al mismo recurso?

Pueden leer y escribir el mismo dato sin coordinación, lo que lleva a:

- **Resultados incorrectos**: por ejemplo dos hilos leen saldo 1000, ambos retiran 400 y ambos escriben 600, cuando debería quedar 200.
- **Pérdida de actualizaciones**: una escritura "pisa" la otra.
- **Datos inconsistentes**: el recurso queda en un estado que no corresponde a ninguna secuencia lógica de operaciones.

En el ejercicio 2 el recurso compartido es el saldo de la cuenta. Sin sincronización, dos hilos podrían leer 1000, restar 400 cada uno y dejar el saldo mal. Por eso se usa sincronización para que solo un hilo ejecute la operación de retiro a la vez.

---

### 3. ¿Qué es una condición de carrera (Race Condition)?

Ocurre cuando el resultado del programa **depende del orden o del instante** en que los hilos ejecutan sus instrucciones, y ese orden no está bajo control.

Ejemplo con el saldo: el hilo A lee saldo = 1000; el hilo B también lee 1000 antes de que A escriba; A calcula 600 y escribe; B calcula 600 y escribe. Resultado: saldo 600 cuando debería ser 200. La "carrera" es quién lee y escribe primero; según quién gane, el resultado cambia y es incorrecto.

En el código se evita haciendo el método `retirar` **synchronized**: solo un hilo puede estar dentro a la vez, así que la secuencia leer–calcular–escribir no se intercala con otro hilo y el saldo queda consistente.

---

### 4. ¿Por qué es importante sincronizar el acceso a recursos compartidos?

Sin sincronización, varios hilos pueden leer y modificar el mismo dato a la vez, lo que produce condiciones de carrera, resultados erróneos y estados inconsistentes (por ejemplo saldo negativo o que "desaparezcan" retiros).

Al sincronizar (por ejemplo con `synchronized` en Java), se garantiza que las operaciones sobre el recurso compartido (lectura, decisión y escritura) se vean como **atómicas**: ningún otro hilo puede entrar en medio. Así se evita que el saldo quede negativo o que se pierdan operaciones, como en la cuenta bancaria del ejercicio 2.

En resumen: la sincronización es necesaria para que el acceso a recursos compartidos sea seguro y predecible, y para que se cumplan las reglas del negocio (por ejemplo: saldo nunca negativo).
