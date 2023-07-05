<h1 align="center"> GestTaller (API REST) </h1>

**Índice**

1.- [Descripción del proyecto](#id1)

2.- [Funcionalidades](#id2)

3.- [Tecnologías utilizadas](#id3)

4.- [Cómo usuarlo](#id4)

5.- [Probar el proyecto](#id5)


## Descripción del proyecto<a name="id1"></a>
<p>GestTaller (API REST) como su nombre indica, es un API REST que forma parte de un proyecto denominado GestTaller. El proyecto consiste en  una aplicación web para la gestión diaria de un taller de reparación de vehículos. El proyecto se basa en el siguiente diagrama de flujo de una reparación croncreta.</p>
<image src="DiagramaFlujoReparacion.png" alt="Descripción de la imagen">

## Funcionalidades<a name="id2"></a>
<ul>
    <li>Gestión de datos generales: Propietarios y vehículos</li>
    <li>Gestión de proveedores: Piezas, albaranes y facturas</li>
    <li>Informes de almacén: Entradas, salidas, stock</li>
    <li>Gestión de órdenes de reparación</li>
    <li>Facturación de órdenes de reparación</li>
    <li>Informes de facturación: Clientes y proveedores</li>
</ul>

## Tecnologías utilizadas<a name="id3"></a>
<ul>
    <li>Java 17</li>
    <li>
        Spring Boot 2.7.4 -><br> 
        &nbsp&nbsp&nbsp&nbsp Spring Boot Starter Web<br>
        &nbsp&nbsp&nbsp&nbsp Spring Boot Starter Data JPA<br>
        &nbsp&nbsp&nbsp&nbsp Spring Boot Starter Validation<br>
        &nbsp&nbsp&nbsp&nbsp PostgreSQL JDBC Driver<br>
        &nbsp&nbsp&nbsp&nbsp Modelmapper<br>
        &nbsp&nbsp&nbsp&nbsp Project Lombok<br>
        &nbsp&nbsp&nbsp&nbsp Openapi - Swagger<br>
        &nbsp&nbsp&nbsp&nbsp Junit<br>
        &nbsp&nbsp&nbsp&nbsp Mockito<br>
    </li>
    <li>PostgreSql</li>
    <li>Docker</li>
</ul>

## Cómo usarlo<a name="id4"></a>
<p>&nbsp&nbsp&nbsp&nbsp 1.- Clonar repositorio</p>
<ul>
    <li>Abrir  una interfaz de línea de comandos  (CLI), por ejemplo la consola de windows</li>
    <li>Crear una carpeta en la cual alojaremos el proyecto</li>
    <li>En la interfaz de línea de comandos y dentro de la carpeta creada en el punto anterior, ejecutamos el comando "git clone <a>https://github.com/jcaido/Taller-de-coches.git"</a></li>
    </li>. Es necesario tener instalado git en nuestro sistema</li>
</ul>
<br>
<p>&nbsp&nbsp&nbsp&nbsp 2.- Base de datos</p>
<p>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp El proyecto usa PostgreSQL como motor de base de datos. Necesitaremos crear una base de datos PostgreSQL (el nombre de la base de datos es a nuestra elección, por ejemplo "taller"). Para ello, es necesario tener instalado PostgreSQL en nuestro sistema.</p><br>
<p>&nbsp&nbsp&nbsp&nbsp 3.- Variables de entorno</p>
<p>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp Para conectar el proyecto con  la base de datos creada en el punto anterior, debemos crear un archivo de nombre ".env" y ubicarlo en la raiz del proyecto. El archivo ".env" deberá contener la siguiente informacion:</p>
<p>&nbsp&nbsp&nbsp&nbsp POSTGRES_URL = jdbc:postgresql://localhost:5432/[NOMBRE DE LA BASE DE DATOS]</p>
<p>&nbsp&nbsp&nbsp&nbsp POSTGRES_USERNAME = [USUARIO DE LA BASE DE DATOS]</p>
<p>&nbsp&nbsp&nbsp&nbsp POSTGRES_PASSWORD = [CONTRASEÑA DE LA BASE DE DATOS]</p>
<p>&nbsp&nbsp&nbsp&nbsp POSTGRES_DB = [NOMBRE DE LA BASE DE DATOS]</p>
<p>&nbsp&nbsp&nbsp&nbsp POSTGRES_HOST = taller_db</p><br>
<p>&nbsp&nbsp&nbsp&nbsp 4.- Build</p>
<p>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp Para poder realizar el build del proyecto, necesitamos tener instalado "Maven" en nuestro sistema. A continuación, nos situamos en la raiz del proyecto y ejecutamos el comando "mvnw clean package -DskipTests" (si no estamos en la consola de windows, sustituir "mvnw" por "mvn")</p><br>
<p>&nbsp&nbsp&nbsp&nbsp 5.- Ejecutar la aplicadcion</p>
<p>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp Para ejecutar la aplicación, desde el directorio raiz del proyecto debemos ejecutar el comando "docker-compose up". Hay que tener en cuenta que deberemos tener instalado docker en nuestro sistema y que se esté ejecutando</p><br>

## Probar el proyecto<a name="id5"></a>
<p>Podemos probar las funcionalidades del proyecto consumiento los endpoints utilizando la interfaz de <strong>swagger</strong>. Para ello, con el proyecto ejecutándose según los pasos anteriores accederemos desde cualquier navegador a http://localhost:8080/swagger-ui/index.html</p>

