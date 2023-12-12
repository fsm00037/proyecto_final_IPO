import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.List;




public class PantallaPrincipal {
    private JPanel panelCabecera2;
    private JButton botonUsuario;
    private JPanel panelRecomendaciones;
    private JPanel panelCatalogo;
    private JLabel textRecomen;
    private JLabel textCatalogo;
    private JButton botonDerecha;
    private JButton botonIzquierda;
    private JTextField textField1;
    private JComboBox<String> comboBox1;
    private JPanel principal;
    private JPanel panelConPelis;
    private JLabel peli1;
    private JLabel peli4;
    private JLabel peli2;
    private JLabel peli3;
    private JPanel panelPeli1;
    private JLabel textoPeli2;
    private JLabel textoPeli3;
    private JLabel textoPeli4;
    private JLabel textoPeli1;
    private JLabel peli5;
    private JLabel textoPeli5;

    private JLabel peli6;
    private JLabel textoPeli6;
    private JLabel peli7;

    private JLabel peli8;
    private JLabel textoPeli8;
    private JLabel peli9;
    private JLabel textoPeli9;
    private JPanel panelPeli2;
    private JPanel panelPeli3;
    private JPanel panelPeli4;
    private JPanel panelPeli5;
    private JPanel panelPeli6;
    private JPanel panelPeli7;
    private JPanel panelPeli8;
    private JPanel panelPeli9;
    private JMenuBar menuBar1;
    private JLabel logo;
    private JMenuBar menu;

    private int indiceActual = 0;
    private List<String> listaImagenes;
    private ArrayList<JPanel>panelesPelis;
    private ArrayList<JLabel>imagenes;
    private ResourceBundle resourceBundle; // Variable para manejar los recursos
    String language;
    private ArrayList<JMenuItem> botonesMenu;
    JMenuBar menuBar;
    Properties properties ;
    ConfiguracionMenu configuracionMenu;
    PantallaInformacion pantallaInformacion;
    JMenu menuPeliculas;
    JMenu menuBuscar;
    JMenu menuCuenta;
    JMenu menuAjustes;
    JMenu menuAyuda;
    // Constructor
    public PantallaPrincipal() throws IOException {

        menu = new JMenuBar();
        language = "es";

        String userDir = System.getProperty("user.dir");
        String path = userDir + "/idiomas/messages_" + language + ".properties";

        properties = new Properties();
        properties.load(new FileInputStream(path));
        CrearUI();
        eventos();
        cargarPeliculas();

        configuracionMenu = new ConfiguracionMenu(PantallaPrincipal.this);
        pantallaInformacion = new PantallaInformacion(PantallaPrincipal.this);
        cambiarIdioma(language);

    }
    private void CrearUI(){
        panelesPelis = new ArrayList<>();
        panelesPelis.add(panelPeli1);
        panelesPelis.add(panelPeli2);
        panelesPelis.add(panelPeli3);
        panelesPelis.add(panelPeli4);
        panelesPelis.add(panelPeli5);
        panelesPelis.add(panelPeli6);
        panelesPelis.add(panelPeli7);
        panelesPelis.add(panelPeli8);
        panelesPelis.add(panelPeli9);

        imagenes = new ArrayList<>();
        imagenes.add(peli1);
        imagenes.add(peli2);
        imagenes.add(peli3);
        imagenes.add(peli4);
        imagenes.add(peli5);
        imagenes.add(peli6);
        imagenes.add(peli7);
        imagenes.add(peli8);
        imagenes.add(peli9);

        // Crear la barra de menú
        menuBar = new JMenuBar();

        // Crear el menú
        menuPeliculas = new JMenu();
        menuBuscar = new JMenu();
        menuCuenta = new JMenu();
        menuAjustes = new JMenu();
        menuAyuda = new JMenu();

        // Agregar los elementos al menú
        menuPeliculas.add(new JMenuItem());
        menuPeliculas.add(new JMenuItem());
        menuBuscar.add(new JMenuItem());
        menuCuenta.add(new JMenuItem());
        menuAjustes.add(new JMenuItem());
        menuAyuda.add(new JMenuItem());

        // Agregar el menú a la barra de menú
        menuBar1.add(menuPeliculas);
        menuBar1.add(menuBuscar);
        menuBar1.add(menuCuenta);
        menuBar1.add(menuAjustes);
        menuBar1.add(menuAyuda);

        ImageIcon imagenIcon = escalarImagen("imagenes/logo.png", 60, 60);
        logo.setIcon(imagenIcon);

    }

    private void eventos(){

        //pulsar en caratula
        for(JPanel panel : panelesPelis){
            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    pantallaInformacion.setVisible(true);
                    pantallaInformacion.refreshUI();
                    System.out.println("Abrir ventana peli"+panel.getX());
                }
            });
        }
        JMenuItem aux=(JMenuItem)    menuAjustes.getMenuComponent(0);
        aux.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configuracionMenu.setVisible(true);
                configuracionMenu.initUI();
            }

        });

        botonDerecha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!listaImagenes.isEmpty()) {
                    indiceActual = (indiceActual + 1) % listaImagenes.size();
                    mostrarImagen();
                }
            }
        });

        botonIzquierda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!listaImagenes.isEmpty()) {
                    indiceActual = (indiceActual - 1 + listaImagenes.size()) % listaImagenes.size();
                    mostrarImagen();
                }
            }
        });
    }
   private void cargarPeliculas(){
       // Obtener la lista de nombres de imágenes en la carpeta "imagenes"
       listaImagenes = obtenerNombresImagenes();
       if (!listaImagenes.isEmpty()) {
           mostrarImagen();
       } else {
           JOptionPane.showMessageDialog(null, "No hay imágenes en la carpeta 'imagenes'.");
       }
   }

    private List<String> obtenerNombresImagenes() {
        System.out.println("upps");
        List<String> nombresImagenes = new ArrayList<>();
        File carpetaImagenes = new File("imagenes");

        if (carpetaImagenes.isDirectory()) {
            File[] archivos = carpetaImagenes.listFiles();
            if (archivos != null) {
                for (File archivo : archivos) {
                    if (archivo.isFile() && esArchivoImagen(archivo.getName())) {
                        nombresImagenes.add(archivo.getName());
                    }
                }
            }
        }

        return nombresImagenes;
    }

    private boolean esArchivoImagen(String nombreArchivo) {
        String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf('.') + 1).toLowerCase();
        return extension.equals("jpg") || extension.equals("png") || extension.equals("gif");
    }

    private ImageIcon escalarImagen(String rutaImagen, int ancho, int alto) {
        ImageIcon imagenIcon = new ImageIcon(rutaImagen);
        Image imagen = imagenIcon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagen);
    }

    private void mostrarImagen() {
        String rutaImagen = "imagenes/" + listaImagenes.get(indiceActual);
        System.out.println("Cargando imagen: " + rutaImagen); // Mensaje de depuración
        try {
            // Escalar la imagen al tamaño deseado
            ImageIcon imagenIcon = escalarImagen(rutaImagen, 200, 300);
            peli1.setIcon(imagenIcon);
        for(int i =1;i< imagenes.size();i++){
            int siguiente = (indiceActual++) % listaImagenes.size();
            imagenes.get(i).setIcon(escalarImagen("imagenes/" + listaImagenes.get(siguiente), 200, 300));
        }

        } catch (Exception e) {
            e.printStackTrace(); // Imprime la traza de la excepción
            JOptionPane.showMessageDialog(null, "Error al cargar la imagen: " + e.getMessage());
        }
    }


    public void cambiarIdioma(String idioma) {
        language = idioma;
        String userDir = System.getProperty("user.dir");
        String path = userDir + "/idiomas/messages_" + language + ".properties";

        properties = new Properties();
        try (InputStream input = new FileInputStream(path)) {
            properties.load(input);

            textRecomen.setText(properties.getProperty("textRecomen"));
            textRecomen.setForeground(Color.WHITE);

            textCatalogo.setText(properties.getProperty("textCatalogo"));
            textCatalogo.setForeground(Color.WHITE);

            JMenuItem aux;

            menuPeliculas.setText(properties.getProperty("opcionPeliculas"));
            aux=(JMenuItem)    menuPeliculas.getMenuComponent(0);
            aux.setText(properties.getProperty("comprar"));
            aux=(JMenuItem)    menuPeliculas.getMenuComponent(1);
            aux.setText(properties.getProperty("trailers"));

            menuBuscar.setText(properties.getProperty("opcionBuscar"));
            aux=(JMenuItem)    menuBuscar.getMenuComponent(0);
            aux.setText(properties.getProperty("comprar"));

            menuCuenta.setText(properties.getProperty("opcionCuenta"));
            aux=(JMenuItem)    menuCuenta.getMenuComponent(0);
            aux.setText(properties.getProperty("cerrarCuenta"));

            menuAjustes.setText(properties.getProperty("opcionAjustes"));
            aux=(JMenuItem)    menuAjustes.getMenuComponent(0);
            aux.setText(properties.getProperty("ventanaIdioma"));

            menuAyuda.setText(properties.getProperty("opcionAyuda"));
            aux=(JMenuItem)    menuAyuda.getMenuComponent(0);
            aux.setText(properties.getProperty("guia"));

            pantallaInformacion.setVisible(false);


        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error cargando el archivo de propiedades: " + e.getMessage());
        }


    }


    public static void main(String[] args) throws IOException {




        JFrame frame = new JFrame("FFilms");
        frame.setSize(1030, 770);
        PantallaPrincipal pantallaPrincipal = new PantallaPrincipal();
        frame.setContentPane(pantallaPrincipal.principal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);



    }

    public Properties getProperties() {
        return properties;
    }
}

