package Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface Inventariable<T> {

    void agregar(T item);

    T obtener(int indice);

    void eliminar(int indice);

    int tamanio();

    List<T> filtrar(Predicate<? super T> criterio);

    void ordenar();
    
    public void guardarEnArchivo(String path) throws IOException;
    
    public void cargarDesdeArchivo(String path) throws IOException, ClassNotFoundException;

    public void ordenar(Comparator<T> comparator);

    void paraCadaElemento(Consumer<? super T> accion);
    
     public void guardarEnCSV(String path);
     
      public void cargarDesdeCSV(String path);

}
