package Models;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface Inventariable<T> {

    void agregar(T item);

    T obtener(int indice);

    void eliminar(int indice);

    int tamanio();

    List<T> filtrar(Predicate<? super T> criterio);

    void paraCadaElemento(Consumer<? super T> accion);

}
