# Recetario AD05

Aplicación web local hecha con Java y Spring Boot para gestionar recetas y los pasos de preparación. Una receta puede tener varios pasos, y cada paso pertenece a una única receta. Al borrar una receta se eliminan también sus pasos asociados, mientras que al borrar un paso la receta y el resto de pasos siguen igual.

## Cómo funciona

- **Recetas**: puedes crear una receta indicando su nombre, descripción y minutos de preparación, ver su detalle y editarla cargando sus datos actuales en el formulario.
- **Pasos**: puedes añadir pasos desde una receta o desde la sección de pasos eligiendo la receta en el desplegable, indicando el orden y la instrucción, y editarlos cuando haga falta.
- **Borrado**: antes de eliminar una receta o un paso se muestra una pantalla de confirmación que indica qué pasará, y el borrado solo se hace al enviar el formulario por `POST`.
- **Persistencia en Oracle**: los datos se guardan en Oracle (`XEPDB1`) a través de Spring Data JPA y Hibernate sobre las tablas `AD05_RECETAS` y `AD05_PASOS`, manteniendo la relación bidireccional entre receta y pasos en cada operación.