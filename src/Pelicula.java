import java.util.ArrayList;

public class Pelicula {
    private int cod;
    private String titulo;
    private String sinopsis;
    private int anio;
    private int duracion;
    private String pais;
    private String genero;
    private String imagen;
    private Actor director;
    private ArrayList<Actor> actores;

    public Pelicula(int cod, String titulo, String sinopsis, int anio, int duracion, String pais, String genero, String imagen, Actor director) {
        this.actores = new ArrayList<>();
        this.cod = cod;
        this.titulo = titulo;
        this.sinopsis = sinopsis;
        this.anio = anio;
        this.duracion = duracion;
        this.pais = pais;
        this.genero = genero;
        this.imagen = imagen;
        this.director = director;
    }
    public void addActor(Actor actor){
        actores.add(actor);
    }

    public int getCod() {
        return cod;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public int getAnio() {
        return anio;
    }

    public int getDuracion() {
        return duracion;
    }

    public String getPais() {
        return pais;
    }

    public String getGenero() {
        return genero;
    }

    public String getImagen() {
        return imagen;
    }

    public Actor getDirector() {
        return director;
    }

    public ArrayList<Actor> getActores() {
        return actores;
    }
}
