import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AdministradorPeliculas {
    private ArrayList<Pelicula> peliculas;
    private ArrayList<Actor> actores;
    private ArrayList<Actor> directores;

    private HashMap<String, TreeMap<Object, ArrayList<Pelicula>>> peliculasPorFiltro;

    public AdministradorPeliculas() throws FileNotFoundException {
        this.peliculas = new ArrayList<>();
        this.actores = new ArrayList<>();
        this.directores = new ArrayList<>();
        this.peliculasPorFiltro = new HashMap<>();
        inicializarFiltros();
        cargarActores("BBDD/Actores.tsv");
        cargarDirectores("BBDD/Directores.tsv");
        cargarPeliculas("BBDD/Peliculas2.tsv");
        addCriticasAPeli("BBDD/Valoraciones.tsv");


    }
    private void inicializarFiltros() {
        // Inicializar árboles para cada filtro
        peliculasPorFiltro.put("cod", new TreeMap<>());
        peliculasPorFiltro.put("titulo", new TreeMap<>());
        peliculasPorFiltro.put("anio", new TreeMap<>());
        peliculasPorFiltro.put("genero", new TreeMap<>());
        peliculasPorFiltro.put("pais", new TreeMap<>());
        peliculasPorFiltro.put("duracion", new TreeMap<>());
        peliculasPorFiltro.put("director", new TreeMap<>());
        peliculasPorFiltro.put("actor", new TreeMap<>());
        // Agregar más filtros según sea necesario
    }
    // Método para agregar película al árbol
    private void agregarPelicula(Pelicula pelicula) {
        for (Map.Entry<String, TreeMap<Object, ArrayList<Pelicula>>> entry : peliculasPorFiltro.entrySet()) {
            String filtro = entry.getKey();
            Object valor = obtenerValorSegunFiltro(pelicula, filtro);

            if (valor != null) {
                TreeMap<Object, ArrayList<Pelicula>> arbol = entry.getValue();
                if (!arbol.containsKey(valor)) {
                    arbol.put(valor, new ArrayList<>());
                }
                arbol.get(valor).add(pelicula);
            }
        }
    }
    private Object obtenerValorSegunFiltro(Pelicula pelicula, String filtro) {
        switch (filtro.toLowerCase()) {
            case "cod":
                return pelicula.getCod();
            case "titulo":
                return pelicula.getTitulo().toLowerCase();
            case "anio":
                return String.valueOf(pelicula.getAnio());
            case "genero":
                return pelicula.getGenero().toLowerCase();
            case "pais":
                return pelicula.getPais().toLowerCase();
            case "duracion":
                return String.valueOf(pelicula.getDuracion());
            case "director":
                return String.valueOf(pelicula.getDirector().getNombre());
            case "actor":
                return pelicula.getActores().get(0).getNombre()+
                        pelicula.getActores().get(1).getNombre()+
                        pelicula.getActores().get(2).getNombre();

            // Agrega más casos según tus atributos
            // Agrega más casos según tus atributos
        }
        return null;
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
                agregarPelicula(pelicula);
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

    public ArrayList<Pelicula> buscarPeliculas(String filtro, Object valor) {
        TreeMap<Object, ArrayList<Pelicula>> arbol = peliculasPorFiltro.get(filtro);
        if (arbol != null) {
            ArrayList<Pelicula> resultado = new ArrayList<>();

            for (Map.Entry<Object, ArrayList<Pelicula>> entry : arbol.entrySet()) {
                Object clave = entry.getKey();

                if (clave instanceof String) {
                    String claveString = ((String) clave).toLowerCase();
                    String valorLower = ((String) valor).toLowerCase();

                    // Split both the key and the value into words
                    String[] palabrasClave = claveString.split("\\s+");
                    String[] palabrasValor = valorLower.split("\\s+");

                    // Check if all words in the value match any word in the key
                    boolean match = Arrays.stream(palabrasValor)
                            .allMatch(wordValor -> Arrays.stream(palabrasClave)
                                    .anyMatch(wordClave -> wordClave.equals(wordValor)));

                    if (match) {
                        resultado.addAll(entry.getValue());
                    }
                } else if (clave instanceof Integer && clave.equals(Integer.parseInt((String) valor))) {
                    // If the key is an Integer, direct comparison
                    resultado.addAll(entry.getValue());
                }
            }

            return resultado;
        }

        return new ArrayList<>(); // Devuelve una lista vacía si no se encuentra ninguna película
    }

    private void addCriticasAPeli(String filePath) throws FileNotFoundException {

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\t");

                int codPelicula = Integer.parseInt(data[0]);
                String valoracion = data[1];

                // Buscar la película en el ArrayList utilizando el código de la película

                    Pelicula pelicula = peliculas.get(codPelicula-1);
                    pelicula.addValoracion(valoracion);
                }
            } catch (IOException ex) {
            throw new RuntimeException(ex);
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
