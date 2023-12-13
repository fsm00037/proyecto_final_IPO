import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AdministradorPeliculas {
    private ArrayList<Pelicula> peliculas;
    private ArrayList<Actor> actores;
    private ArrayList<Actor> directores;

    public AdministradorPeliculas() {
        this.peliculas = new ArrayList<>();
        this.actores = new ArrayList<>();
        this.directores = new ArrayList<>();
        cargarActores("BBDD/Actores.tsv");
        cargarDirectores("BBDD/Directores.tsv");
        cargarPeliculas("BBDD/Peliculas2.tsv");


    }
    public ArrayList<Pelicula> getRecomendadas(int n) {
        Set<Pelicula> recomendadas=new HashSet<>();
        while (recomendadas.size() != n){
            recomendadas.add(peliculas.get(new Random().nextInt(peliculas.size())));
        }
        ArrayList<Pelicula> resultado = new ArrayList<>(recomendadas);
        return resultado;
    }

    public ArrayList<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void cargarPeliculas(String ruta) {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            // Omitir la primera línea (encabezado)
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] datos = line.split("\t");
                int cod = Integer.parseInt(datos[0]);
                String titulo = datos[1];
                String sinopsis = datos[2];
                int anio = Integer.parseInt(datos[3]);
                int duracion = Integer.parseInt(datos[4]);
                String pais = datos[5];
                String genero = datos[6];
                String imagen = datos[7];
                int directorCod = Integer.parseInt(datos[8]);
                List<Integer> actoresCod = parsearListaEnteros(datos[9]);

                Pelicula pelicula = new Pelicula(cod, titulo, sinopsis, anio, duracion, pais, genero, imagen,
                        encontrarActorPorCodigo(directorCod, directores), encontrarActoresPorCodigos(actoresCod, actores));
                peliculas.add(pelicula);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarActores(String ruta) {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            // Omitir la primera línea (encabezado)
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] datos = line.split("\t");
                int cod = Integer.parseInt(datos[0]);
                String nombre = datos[1];
                String img = datos[2];

                Actor actor = new Actor(cod, nombre, img);
                actores.add(actor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarDirectores(String ruta) {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            // Omitir la primera línea (encabezado)
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] datos = line.split("\t");
                int cod = Integer.parseInt(datos[0]);
                String nombre = datos[1];
                String img = datos[2];

                Actor director = new Actor(cod, nombre, img);
                directores.add(director);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private List<Integer> parsearListaEnteros(String cadena) {
        List<Integer> listaEnteros = new ArrayList<>();
        String[] elementos = cadena.split(", ");
        for (String elemento : elementos) {
            listaEnteros.add(Integer.parseInt(elemento));
        }
        return listaEnteros;
    }

    private Actor encontrarActorPorCodigo(int codigo, List<Actor> listaActores) {
        for (Actor actor : listaActores) {
            if (actor.getCod() == codigo) {
                return actor;
            }
        }
        return null;
    }

    private ArrayList<Actor> encontrarActoresPorCodigos(List<Integer> codigos, ArrayList<Actor> listaActores) {
        ArrayList<Actor> resultado = new ArrayList<>();
        for (int codigo : codigos) {
            Actor actor = encontrarActorPorCodigo(codigo, listaActores);
            if (actor != null) {
                resultado.add(actor);
            }
        }
        return resultado;
    }
}
