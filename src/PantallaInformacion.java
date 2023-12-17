import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JButton trailerBoton;
    private JButton criticasBoton;
    private JButton comprarBoton;
    private JLabel imagenDirector;
    private JLabel nombreDirector;
    private JLabel imagenA1;
    private JLabel nombreA1;
    private JLabel imagenA2;
    private JLabel nombreA2;
    private JLabel imagenA3;
    private JLabel nombreA3;

    private Pelicula pelicula;
    PantallaPrincipal principal;
    Properties properties;
    ValoracionesApp ventanaVal;
    public PantallaInformacion(PantallaPrincipal principal){
        this.principal = principal;
        ventanaVal = new ValoracionesApp();
        refreshUI();
        add(panel1);

        setSize(950, 650);
        setLocationRelativeTo(null);
        this.setResizable(false);
        ImageIcon icono = new ImageIcon("imagenes/logo.png"); // Ajusta la ruta según tu estructura de carpetas
        this.setIconImage(icono.getImage());

        criticasBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaVal.cargarComentariosIniciales(pelicula.getValoraciones());
            ventanaVal.setVisible(true);

            }
        });
        comprarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UIManager.put("OptionPane.yesButtonText", properties.getProperty("si"));
                UIManager.put("OptionPane.noButtonText", properties.getProperty("no"));
                int opcion = JOptionPane.showConfirmDialog(null, properties.getProperty("comprarMsg"), properties.getProperty("comprarPelicula"), JOptionPane.YES_NO_OPTION);

                // Comprobamos la opción seleccionada
                if (opcion == JOptionPane.YES_OPTION) {
                    UIManager.put("OptionPane.okButtonText", properties.getProperty("aceptar"));
                    JOptionPane.showMessageDialog(null, properties.getProperty("compraRealizada"), properties.getProperty("comprarPelicula"), JOptionPane.INFORMATION_MESSAGE);
                }


            }
        });


        trailerBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UIManager.put("OptionPane.okButtonText", properties.getProperty("aceptar"));
                JOptionPane.showMessageDialog(null, properties.getProperty("peliError2"), properties.getProperty("error"), JOptionPane.ERROR_MESSAGE);

            }
        });


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

        ventanaVal.traducir(properties);
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

        rutaImagen ="BBDD/directores/" + peli.getDirector().getImg();
        imagenDirector.setIcon(principal.escalarImagen(rutaImagen, 70, 70));
        nombreDirector.setText(peli.getDirector().nombre);
        nombreA1.setText(peli.getActores().get(0).nombre);
        nombreA2.setText(peli.getActores().get(1).nombre);
        nombreA3.setText(peli.getActores().get(2).nombre);
        rutaImagen ="BBDD/actores/" + peli.getActores().get(0).getImg();
        imagenA1.setIcon(principal.escalarImagen(rutaImagen, 70, 70));
        rutaImagen ="BBDD/actores/" + peli.getActores().get(1).getImg();
        imagenA2.setIcon(principal.escalarImagen(rutaImagen, 70, 70));
        rutaImagen ="BBDD/actores/" + peli.getActores().get(2).getImg();
        imagenA3.setIcon(principal.escalarImagen(rutaImagen, 70, 70));

        rutaImagen ="imagenes/carrito.png";
        comprarBoton.setIcon(principal.escalarImagen(rutaImagen, 24, 24));
        rutaImagen ="imagenes/trailer.png";
        trailerBoton.setIcon(principal.escalarImagen(rutaImagen, 24, 24));
        rutaImagen ="imagenes/valoracion.png";
        criticasBoton.setIcon(principal.escalarImagen(rutaImagen, 24, 24));
    }

}
