package Models;

import Service.CSVSerializable;
import java.io.Serializable;

public class Libro implements Comparable<Libro>, Serializable, CSVSerializable {
    private int id ;
    private String titulo;
    private String autor;
    private Categoria categoria;

    public Libro(int id, String titulo, String autor, Categoria categoria) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

   

    @Override
    public int compareTo(Libro o) {
        return Integer.compare(id, o.id); 
    }

    @Override
    public String toString() {
        return "Libro{" + "id: " + id + ", titulo: " + titulo + ", autor: " + autor + ", categoria: " + categoria + '}';
    }

    @Override
    public String toCSV() {
        return  id + "," + titulo + "," + autor + "," + categoria;
                }    
    
     public static Libro fromCSV(String linea) {
        String[] data = linea.split(",");
        if (data.length != 4) {
            throw new IllegalArgumentException("La línea no tiene el formato esperado: " + linea);
        }
        try {
            int id = Integer.parseInt(data[0]);
            String titulo = data[1];
            String autor = data[2];
            Categoria categoria = Categoria.valueOf(data[3]);
            return new Libro(id, titulo, autor, categoria);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error en formato numérico: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error en sector o formato general: " + e.getMessage());
        }
    }
    
}
