public class Actor {
    int cod;

    String nombre;
    String img;

    public Actor(int cod, String nombre, String img) {
        this.cod = cod;
        this.nombre = nombre;
        this.img = img;
    }

    public int getCod() {
        return cod;
    }

    public String getNombre() {
        return nombre;
    }

    public String getImg() {
        return img;
    }
}
