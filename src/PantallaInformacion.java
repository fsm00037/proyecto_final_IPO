import javax.swing.*;
import java.awt.*;
import java.util.Properties;

public class PantallaInformacion extends JFrame {
    private JPanel panel1;
    private JLabel imagenPelicula;
    private JButton button1;
    private JButton button2;
    private JButton button3;
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

    private Pelicula pelicula;
    PantallaPrincipal principal;
    Properties properties;

    public PantallaInformacion(PantallaPrincipal principal){
        this.principal = principal;

        refreshUI();
        add(panel1);
        setSize(950, 650);
        setLocationRelativeTo(null);
    }

    public void refreshUI(){
        properties = principal.getProperties();
        setTitle(properties.getProperty("infoPelicula"));
        textoTitulo.setText(properties.getProperty("titulo"));
        textoSinopsis.setText(properties.getProperty("sinopsis"));
        textoAnio.setText(properties.getProperty("anio"));
        textoPais.setText(properties.getProperty("pais"));
        textoDuracion.setText(properties.getProperty("duracion"));
        textoGenero.setText(properties.getProperty("genero"));
        textoDirector.setText(properties.getProperty("director"));
        textoReparto.setText(properties.getProperty("reparto"));
    }
}
