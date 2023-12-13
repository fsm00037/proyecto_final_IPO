import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
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
    private JLabel titulo2;
    private JLabel titulo3;
    private JLabel titulo4;
    private JLabel titulo1;
    private JLabel peli5;
    private JLabel titulo5;

    private JLabel peli6;
    private JLabel titulo6;
    private JLabel peli7;

    private JLabel peli8;
    private JLabel titulo8;
    private JLabel peli9;
    private JLabel titulo9;
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
    private JLabel titulo7;
    private JButton izquierdaCatalogo;
    private JButton derechaCatalogo;
    private JMenuBar menu;

    private int indiceActual = 0;
    private int indiceActualCatalogo = 0;
    private List<String> listaImagenes;
    private ArrayList<JPanel>panelesPelis;
    private ArrayList<JLabel>imagenes;
    private ArrayList<JLabel>titulos;
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
    AdministradorPeliculas admin;

    ArrayList<Pelicula> recomendadas;
    ArrayList<Pelicula>busqueda;

    // Constructor
    public PantallaPrincipal() throws IOException {

        menu = new JMenuBar();
        admin = new AdministradorPeliculas();
        recomendadas = admin.getRecomendadas(7);
        busqueda = admin.getPeliculas();
        language = "es";

        String userDir = System.getProperty("user.dir");
        String path = userDir + "/idiomas/messages_" + language + ".properties";

        properties = new Properties();
        properties.load(new FileInputStream(path));
        CrearUI();
        eventos();

        mostrarBusqueda();
        mostrarRecomendadas();
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

        titulos = new ArrayList<>();
        titulos.add(titulo1);
        titulos.add(titulo2);
        titulos.add(titulo3);
        titulos.add(titulo4);
        titulos.add(titulo5);
        titulos.add(titulo6);
        titulos.add(titulo7);
        titulos.add(titulo8);
        titulos.add(titulo9);

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
                    int index =panelesPelis.indexOf(panel);
                    if(index<3){
                        pantallaInformacion.cargarPelicula(recomendadas.get(indiceActual%3+ index));
                    }else{
                        pantallaInformacion.cargarPelicula(busqueda.get(indiceActualCatalogo%6+ index-3));
                    }
                    System.out.println("Abrir ventana peli"+panel.getX());
                }
            });
        }
        JMenuItem aux=(JMenuItem)    menuAjustes.getMenuComponent(0);
        aux.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configuracionMenu.setVisible(true);

            }

        });

        botonDerecha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!recomendadas.isEmpty()) {
                    indiceActual = (indiceActual + 1) % recomendadas.size();
                    mostrarRecomendadas();
                }
            }
        });

        botonIzquierda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!recomendadas.isEmpty()) {
                    indiceActual = (indiceActual - 1 + recomendadas.size()) % recomendadas.size();
                    mostrarRecomendadas();
                }
            }
        });
        derechaCatalogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!busqueda.isEmpty()) {
                    indiceActualCatalogo = (indiceActualCatalogo + 1) % busqueda.size();
                    mostrarBusqueda();
                }
            }
        });

        izquierdaCatalogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!busqueda.isEmpty()) {
                    indiceActualCatalogo = (indiceActualCatalogo - 1 + listaImagenes.size()) % busqueda.size();
                    mostrarBusqueda();
                }
            }
        });
    }




    private boolean esArchivoImagen(String nombreArchivo) {
        String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf('.') + 1).toLowerCase();
        return extension.equals("jpg") || extension.equals("png") || extension.equals("gif");
    }

    public ImageIcon escalarImagen(String rutaImagen, int ancho, int alto) {
        ImageIcon imagenIcon = new ImageIcon(rutaImagen);
        Image imagen = imagenIcon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagen);
    }

    private void mostrarRecomendadas() {
        int aux = indiceActual;
        try {

            for (int i = 0; i < 3; i++) {
                String rutaImagen = "BBDD/caratulas/" + recomendadas.get(aux).getImagen();
                ImageIcon imagenIcon = escalarImagen(rutaImagen, 200, 300);
                imagenes.get(i).setIcon(imagenIcon);
                titulos.get(i).setText(recomendadas.get(aux).getTitulo());
                aux = (aux+1) % recomendadas.size();
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar la imagen: " + e.getMessage());
        }
    }

    private void mostrarBusqueda() {

        int aux = indiceActualCatalogo;
        try {
            for(int i =3;i< titulos.size();i++){
                String rutaImagen = "BBDD/caratulas/" + busqueda.get(aux).getImagen();
                ImageIcon imagenIcon = escalarImagen(rutaImagen, 200, 300);
                imagenes.get(i).setIcon(imagenIcon);
                titulos.get(i).setText(busqueda.get(aux).getTitulo());
                aux = (aux+1) % busqueda.size();
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

