package mendozabrayan.pkg2doparcial;

import Models.Categoria;
import Service.Inventario;
import Models.Libro;
import java.io.IOException;
import java.util.Comparator;

public class TestParcial {

    public static void main(String[] args) {
        try {
            // Crear un inventario de libros
            Inventario<Libro> inventarioLibros = new Inventario<>();
            inventarioLibros.agregar(new Libro(1, "1984", "George Orwell", Categoria.ENTRETENIMIENTO));
            inventarioLibros.agregar(new Libro(2, "El señor de los anillos", "J.R.R.Tolkien", Categoria.LITERATURA));
            inventarioLibros.agregar(new Libro(3, "Cien años de soledad", "Gabriel García Márquez ", Categoria.LITERATURA));
            inventarioLibros.agregar(new Libro(4, "El origen de las especies", "Charles Darwin ", Categoria.CIENCIA));
            inventarioLibros.agregar(new Libro(5, "La guerra de los mundos", "H.G. Wells ", Categoria.ENTRETENIMIENTO));
            // Mostrar todos los libros en el inventario
            System.out.println("Inventario de libros:");
            inventarioLibros.paraCadaElemento(System.out::println);
            // Filtrar libros por categoría LITERATURA
             System.out.println("\nLibros de Literatura:");
            inventarioLibros.filtrar(libro -> ((Libro) libro).getCategoria() == Categoria.LITERATURA)
                .forEach(libro -> System.out.println(libro));
            // Filtrar libros cuyo título contiene "1984"
            System.out.println("\nLibros cuyo título contiene '1984':");
            inventarioLibros.filtrar(libro -> ((Libro) libro).getTitulo().contains("1984")).forEach(libro -> System.out.println(libro));

            // Ordenar libros de manera natural (por id)
            System.out.println("\nLibros ordenados de manera natural (por id):");
            inventarioLibros.ordenar();
            inventarioLibros.paraCadaElemento(libro -> System.out.println(libro));

            // Ordenar libros por título utilizando un Comparator
            System.out.println("\nLibros ordenados por título:");
            inventarioLibros.ordenar(Comparator.comparing(Libro::getTitulo));
            inventarioLibros.paraCadaElemento(libro -> System.out.println(libro));

            // Guardar el inventario en un archivo binario
            inventarioLibros.guardarEnArchivo("src/Data/libros.dat");

            // Cargar el inventario desde el archivo binario
            Inventario<Libro> inventarioCargado = new Inventario<>();
            inventarioCargado.cargarDesdeArchivo("src/Data/libros.dat");
            System.out.println("\nLibros cargados desde archivo binario:");
            inventarioCargado.paraCadaElemento(libro -> System.out.println(libro));

            // Guardar el inventario en un archivo CSV
            inventarioLibros.guardarEnCSV("src/Data/libros.csv");
            // Cargar el inventario desde el archivo CSV
            inventarioCargado.cargarDesdeCSV("src/Data/libros.csv");
            System.out.println("\nLibros cargados desde archivo CSV:");
            inventarioCargado.paraCadaElemento(libro -> System.out.println(libro));
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
