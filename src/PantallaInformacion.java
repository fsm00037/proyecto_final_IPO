import javax.swing.*;
import java.awt.*;
import java.util.Properties;

public class PantallaInformacion extends JFrame {
    private JPanel panel1;
    private JLabel imagenPelicula;
    private JLabel textoTitulo;
    private JLabel contenidoTitulo;
    private JLabel textoSinopsis;
    private JLabel contenidoSinopsis;
    private JLabel textoAnio;
    private JLabel contenidoAnio;
    private JLabel textoPais;
    private JLabel contenidoPais;
    private JLabel textoDuracion;
    private JLabel contenidoDuracion;
    private JLabel textoGenero;
    private JLabel contenidoGenero;
    private JLabel textoDirector;
    private JLabel textoReparto;
    private JButton button1;
    private JButton button2;
    private JButton button3;

    private Pelicula pelicula;
    PantallaPrincipal principal;
    Properties properties;

    public PantallaInformacion(PantallaPrincipal principal){
        this.principal = principal;

        refreshUI();
        add(panel1);

        setSize(950, 650);
        setLocationRelativeTo(null);
        this.setResizable(false);
    }

    public void refreshUI() {
        properties = principal.getProperties();
        setTitle(properties.getProperty("infoPelicula"));

        textoTitulo.setText("<html><u>" + properties.getProperty("titulo") + "</u></html>");
        textoTitulo.setForeground(Color.WHITE);

        textoSinopsis.setText("<html><u>" + properties.getProperty("sinopsis") + "</u></html>");
        textoSinopsis.setForeground(Color.WHITE);

        textoAnio.setText("<html><u>" + properties.getProperty("anio") + "</u></html>");
        textoAnio.setForeground(Color.WHITE);

        textoPais.setText("<html><u>" + properties.getProperty("pais") + "</u></html>");
        textoPais.setForeground(Color.WHITE);

        textoDuracion.setText("<html><u>" + properties.getProperty("duracion") + "</u></html>");
        textoDuracion.setForeground(Color.WHITE);

        textoGenero.setText("<html><u>" + properties.getProperty("genero") + "</u></html>");
        textoGenero.setForeground(Color.WHITE);

        textoDirector.setText("<html><u>" + properties.getProperty("director") + "</u></html>");
        textoDirector.setForeground(Color.WHITE);

        textoReparto.setText("<html><u>" + properties.getProperty("reparto") + "</u></html>");
        textoReparto.setForeground(Color.WHITE);
    }

    public void cargarPelicula(Pelicula peli){
        this.pelicula=peli;
        contenidoTitulo.setText(peli.getTitulo());
        contenidoSinopsis.setText(peli.getSinopsis());
        contenidoAnio.setText(String.valueOf(peli.getAnio()));
        contenidoPais.setText(peli.getPais());
        contenidoDuracion.setText(String.valueOf(peli.getDuracion()));
        contenidoGenero.setText(peli.getGenero());
        String rutaImagen = "BBDD/caratulas/" + peli.getImagen();
        ImageIcon imagenIcon = principal.escalarImagen(rutaImagen, 400, 500);
        imagenPelicula.setIcon(imagenIcon);
    }
}
