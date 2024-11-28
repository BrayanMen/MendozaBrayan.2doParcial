package Service;

import Models.Libro;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Inventario<T> implements Inventariable<T> {

    private List<T> items = new ArrayList<>();

    private void validarIndice(int indice) {
        if (indice < 0 || indice >= items.size()) {
            throw new IndexOutOfBoundsException("Indice Invalido");
        }
    }
    
     @Override
    public void ordenar() {
        items.sort(null);
    }
     @Override
    public void ordenar(Comparator<T> comparator) {
        items.sort(comparator);
    }

    //Serializacion
    @Override
   public void guardarEnArchivo(String path) throws IOException {
    try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(path))) {
        salida.writeObject(items);
        System.out.println("Archivo serializado correctamente.");
    }
}
   
   @Override
    public void cargarDesdeArchivo(String path) throws IOException, ClassNotFoundException {
    try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(path))) {
        this.items = (List<T>) entrada.readObject();
        System.out.println("Archivo binario cargado correctamente.");
    }
}

    // Manejar archivo CSV
    private static boolean crearArchivoCsv(String path) {
        File archivo = new File(path);
        try {
            if (archivo.exists()) {
                System.out.println("Archivo existente.");
                return true;
            } else if (archivo.createNewFile()) {
                System.out.println("Se creo el archivo nuevo.");
                return true;
            }
        } catch (IOException e) {
            System.out.println("Ocurrio un error al crear el archivo");
        }
        return false;
    }
    
    @Override
    public void guardarEnCSV(String path) {
        if (crearArchivoCsv(path)) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
                System.out.println("Se guardaron los empleados con exito.\n");
                bw.write("id,titulo,autor,categoria\n");
                for (T t : items) {
                    if (t instanceof CSVSerializable csvLibro) {
                        bw.write(csvLibro.toCSV() + "\n");
                    }
                }
            } catch (IOException e) {
                System.out.println("Error al guardar en el archivo: " + e.getMessage());
            }
        }
    }

    private static String limpiarLinea(String linea) {
        if (linea.endsWith("\n")) {
            return linea.substring(0, linea.length() - 1);
        }
        return linea;
    }

    private  void procesarLineaParaCSV(String linea, Function<String, T> funcion) {
        linea = limpiarLinea(linea);
        try {
            T e = funcion.apply(linea);
            this.items.add(e); 
        } catch (IllegalArgumentException e) {
            System.out.println("Error al procesar la línea: " + linea + ", " + e.getMessage());
        }
    }
    
    @Override
    public void cargarDesdeCSV(String path, Function<String, T> funcion) {
//        List<Libro> listaRetornable = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String linea;
            br.readLine();
            while ((linea = br.readLine()) != null) {
                procesarLineaParaCSV(linea, funcion);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar en el archivo: " + e.getMessage());
        }
      
    }

    @Override
    public void agregar(T item) {
        if (item == null) {
            throw new NullPointerException("El elemento no puede ser nulo");
        }
        items.add(item);
    }

    @Override
    public T obtener(int indice) {
        validarIndice(indice);
        return items.get(indice);
    }

    @Override
    public void eliminar(int indice) {
        validarIndice(indice);
        items.remove(indice);
    }

    @Override
    public int tamanio() {
        return items.size();
    }

    @Override
    public List<T> filtrar(Predicate<? super T> criterio) {
        List<T> listaRetornable = new ArrayList<>();
        for (T item : items) {
            if (criterio.test(item)) {
                listaRetornable.add(item);
            }
        }
        return listaRetornable;
    }

    @Override
    public void paraCadaElemento(Consumer<? super T> accion) {
        for (T item : items) {
            accion.accept(item);
        }
    }

}
