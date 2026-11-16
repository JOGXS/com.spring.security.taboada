# com.spring.security.taboada

Aplicacion Spring Boot para un formulario de acceso/registro y una vista de
dashboard del proyecto Taboada.

## Alcance actual

- Backend MVC con Spring Boot 3.2.2 y Java 17.
- Vistas Thymeleaf servidas desde `src/main/resources/templates`.
- Persistencia JPA del modelo `Usuario` en una base Oracle.
- Actuator disponible para inspeccionar mappings HTTP.
- Pruebas con JUnit 5, MockMvc y Mockito.

## Arquitectura

| Capa | Ruta | Responsabilidad |
| --- | --- | --- |
| Arranque | `src/main/java/com/taboada/Application.java` | Inicia la aplicacion Spring Boot. |
| Web MVC | `controller/HomeController.java` | Expone la pagina de login y el dashboard. |
| Registro | `controller/UsuarioController.java` | Recibe altas de usuario y delega el guardado al repositorio. |
| Dominio | `model/Usuario.java` | Entidad JPA para la tabla `usuarios`. |
| Datos | `repository/UsuarioRepository.java` | Repositorio `JpaRepository<Usuario, Long>`. |
| Configuracion | `src/main/resources/application.properties` | Puerto, datasource Oracle, JPA, Thymeleaf y Actuator. |

## Rutas HTTP

| Metodo | Ruta | Controlador | Resultado |
| --- | --- | --- | --- |
| `GET` | `/` | `HomeController#mostrarController` | Renderiza la vista `login`. |
| `GET` | `/dashboard` | `HomeController#mostrarDashboard` | Renderiza la vista `dashboard`. |
| `GET` | `/usuarios/nuevo` | `UsuarioController#mostrarFormularioNuevoUsuario` | Prepara un `Usuario` vacio y devuelve `formulario-usuario`. |
| `POST` | `/usuarios/crear` | `UsuarioController#crearUsuario` | Guarda el usuario y redirige a `/usuarios/lista`. |
| `GET` | `/actuator/mappings` | Spring Actuator | Lista los mappings expuestos, si el contexto inicia correctamente. |

## Modelo de datos

`Usuario` se persiste en la tabla `usuarios` con estas columnas:

- `id`: llave primaria generada.
- `usuario`: nombre de usuario.
- `contrasena`: contrasena capturada por el formulario.
- `correo`: correo electronico.
- `contrasena_r`: campo transitorio usado por el formulario para repetir la
  contrasena; no se guarda en base de datos.

## Ejecucion local

Requisitos:

1. JDK 17.
2. Oracle Database accesible desde la maquina local.
3. Variables o propiedades de datasource configuradas para tu entorno.

Pasos:

```bash
bash mvnw spring-boot:run
```

Luego abre `http://localhost:8080/`.

Notas operativas:

- El puerto configurado es `8080`.
- El datasource se configura en `src/main/resources/application.properties`.
  Ajusta los valores localmente y evita publicar credenciales reales en
  documentacion, logs o issues.
- `mvnw` puede no tener permisos de ejecucion en algunos clones. Usa
  `bash mvnw ...` o corrige el permiso antes de invocarlo como `./mvnw`.

## Pruebas

```bash
bash mvnw test
```

Cobertura existente:

- `HomeControllerTest` verifica que `/` renderice `login` y `/dashboard`
  renderice `dashboard`.
- `UsuarioControllerTest` verifica que `crearUsuario` guarde via
  `UsuarioRepository` y redirija a `/usuarios/lista`.
- `ApplicationTests` intenta cargar el contexto completo de Spring.

Estado observado: el contexto completo puede fallar al iniciar si Hibernate no
puede resolver el dialecto Oracle configurado. Antes de usar las pruebas como
senal de salud del proyecto, valida la combinacion de Spring Boot, Hibernate y
dialecto Oracle definida en `application.properties`.

## Seguridad y autenticacion

El proyecto incluye `spring-boot-starter-security`, pero `SecurityConfig` no
define reglas personalizadas. La vista `login.html` envia el formulario a
`/login`, por lo que el comportamiento real depende de la configuracion por
defecto de Spring Security hasta que se implemente una cadena de seguridad
explicita.

## Brechas conocidas

- `UsuarioController` referencia la vista `formulario-usuario`, pero el
  repositorio actual no contiene esa plantilla.
- La redireccion posterior al registro apunta a `/usuarios/lista`, pero no hay
  controlador o vista para esa ruta.
- Las credenciales y datos de conexion no deben quedar hardcodeados para uso en
  ambientes compartidos o productivos; externalizalos antes de desplegar.
