import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;




public class PantallaPrincipal {
    private JFrame frame;
    private JPanel panelCabecera2;
    private JPanel panelRecomendaciones;
    private JPanel panelCatalogo;
    private JLabel textRecomen;
    private JLabel textCatalogo;
    private JButton botonDerecha;
    private JButton botonIzquierda;
    private JTextField buscador;
    private JComboBox<String> filtrosComboBox;
    private JPanel principal;
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
    private JButton botonBuscar;
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

    ArrayList<Pelicula>peliculasMostradas;
    ArrayList<String> filtros;


    int anchoCaratula =200;
    int altoCaratula=250;
    Dimension previousSize;


    // Constructor
    public PantallaPrincipal(JFrame frame) throws IOException {
        this.frame = frame;
        previousSize=frame.getSize();
        menu = new JMenuBar();
        admin = new AdministradorPeliculas();
        recomendadas = admin.getRecomendadas(7);
        busqueda = admin.getPeliculas();
        peliculasMostradas=new ArrayList<>(Collections.nCopies(9, new Pelicula()));
        language = "es";

        String userDir = System.getProperty("user.dir");
        String path = userDir + "/idiomas/messages_" + language + ".properties";

        properties = new Properties();
        properties.load(new FileInputStream(path));
        CrearUI();
        eventos();

        mostrarRecomendadas(anchoCaratula,altoCaratula);
       mostrarBusqueda(anchoCaratula,altoCaratula);

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
        menuBar1 = new JMenuBar();

        menuBar1.add(menuPeliculas);
        menuBar1.add(menuBuscar);
        menuBar1.add(menuCuenta);
        menuBar1.add(menuAjustes);
        menuBar1.add(menuAyuda);


        ImageIcon imagenIcon = escalarImagen("imagenes/logo.png", 60, 60);
        logo.setIcon(imagenIcon);
        ImageIcon imgDer = new ImageIcon("imagenes/der.png");
        ImageIcon imgIzq = new ImageIcon("imagenes/izq.png");
        botonDerecha.setIcon(imgDer);
        derechaCatalogo.setIcon(imgDer);
        botonIzquierda.setIcon(imgIzq);
        izquierdaCatalogo.setIcon(imgIzq);

        filtros = new ArrayList<>();
        filtros.add("titulo");
        filtros.add("anio");
        filtros.add("genero");
        filtros.add("pais");
        filtros.add("duracion");

        filtrosComboBox.addItem("hello");

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
                        pantallaInformacion.cargarPelicula(peliculasMostradas.get( index));
                    }else{
                        pantallaInformacion.cargarPelicula(peliculasMostradas.get(index));
                    }
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
                    mostrarRecomendadas(anchoCaratula,altoCaratula);
                }
            }
        });

        botonIzquierda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!recomendadas.isEmpty()) {
                    indiceActual = (indiceActual - 1 + recomendadas.size()) % recomendadas.size();
                    mostrarRecomendadas(anchoCaratula,altoCaratula);
                }
            }
        });
        derechaCatalogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!busqueda.isEmpty()) {
                    indiceActualCatalogo = (indiceActualCatalogo + 1) % busqueda.size();
                    mostrarBusqueda(anchoCaratula,altoCaratula);
                }
            }
        });

        izquierdaCatalogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!busqueda.isEmpty()) {
                    indiceActualCatalogo = (indiceActualCatalogo - 1 + busqueda.size()) % busqueda.size();
                    mostrarBusqueda(anchoCaratula,altoCaratula);
                }
            }
        });
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(buscador.getText().isEmpty()){
                    busqueda=admin.getPeliculas();
                }else{
                    busqueda = admin.buscarPeliculas(filtros.get(filtrosComboBox.getSelectedIndex()),buscador.getText().toLowerCase());
                    indiceActualCatalogo=0;

                }
                if(busqueda.isEmpty()){
                    UIManager.put("OptionPane.okButtonText", properties.getProperty("aceptar"));
                    JOptionPane.showMessageDialog(null, properties.getProperty("peliError1"), properties.getProperty("error"), JOptionPane.ERROR_MESSAGE);


                }else{
                    mostrarBusqueda(anchoCaratula,altoCaratula);
                }

            }
        });
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Verifica si el componente es completamente visible
                if (isSignificantResize(frame.getSize())) {
                    for (JPanel panel : panelesPelis) {

                        if (!isComponentFullyVisible(panel, frame)) {
                                panel.setVisible(false);
                        }else{

                                if(panelesPelis.indexOf(panel)<busqueda.size()+3)panel.setVisible(true);


                        }
                    }
                     previousSize = frame.getSize();
                }

            }
        });


    }
    private  boolean isSignificantResize(Dimension currentSize) {
        // Define un umbral para el cambio de tamaño significativo
        int threshold = 10;

        // Compara el cambio de tamaño con el umbral
        return Math.abs(currentSize.width - previousSize.width) > threshold ||
                Math.abs(currentSize.height - previousSize.height) > threshold;
    }
    private static boolean isComponentFullyVisible(Component component, JFrame frame) {
        Rectangle componentBounds = component.getBounds();
        Rectangle screenBounds = new Rectangle(0, 0, frame.getWidth(), frame.getHeight());

        // Verifica si el componente es completamente visible en la pantalla
        return screenBounds.contains(componentBounds);
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

    public void mostrarRecomendadas(int ancho,int alto) {

        int aux = indiceActual;
        try {

            for (int i = 0; i < 3; i++) {

                String rutaImagen = "BBDD/caratulas/" + recomendadas.get(aux).getImagen();
                ImageIcon imagenIcon = escalarImagen(rutaImagen, ancho, alto);
                imagenes.get(i).setIcon(imagenIcon);
                titulos.get(i).setText(recomendadas.get(aux).getTitulo());
                peliculasMostradas.set(i,recomendadas.get(aux));
                aux = (aux+1) % recomendadas.size();

            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar la imagen: " + e.getMessage());
        }
    }

    public void mostrarBusqueda(int ancho,int alto) {
        int aux = indiceActualCatalogo;
        try {

            for(int i =3;i< titulos.size();i++){

                panelesPelis.get(i).setVisible(true);
                String rutaImagen = "BBDD/caratulas/" + busqueda.get(aux).getImagen();
                ImageIcon imagenIcon = escalarImagen(rutaImagen, ancho, alto);
                imagenes.get(i).setIcon(imagenIcon);
                titulos.get(i).setText(busqueda.get(aux).getTitulo());
                peliculasMostradas.set(i,busqueda.get(aux));
                aux = (aux+1) % busqueda.size();
            }
            for(int i=busqueda.size()+2;i<8;i++){
                panelesPelis.get(i).setVisible(false);
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


            textRecomen.setText("<html><u>" + properties.getProperty("textRecomen") + "</u></html>");
            textRecomen.setForeground(Color.WHITE);


            textCatalogo.setText("<html><u>" + properties.getProperty("textCatalogo") + "</u></html>");
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



            filtrosComboBox.removeAllItems();
            filtrosComboBox.addItem(properties.getProperty("titulo"));
            filtrosComboBox.addItem(properties.getProperty("anio"));
            filtrosComboBox.addItem(properties.getProperty("genero"));
            filtrosComboBox.addItem(properties.getProperty("pais"));
            filtrosComboBox.addItem(properties.getProperty("duracion"));
            pantallaInformacion.setVisible(false);


        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error cargando el archivo de propiedades: " + e.getMessage());
        }


    }


    public static void main(String[] args) throws IOException {




        JFrame frame = new JFrame("FFilms");
        frame.setSize(1500,1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        PantallaPrincipal pantallaPrincipal = new PantallaPrincipal(frame);
        frame.setJMenuBar(pantallaPrincipal.menuBar1);
        frame.setContentPane(pantallaPrincipal.principal);



    }

    public Properties getProperties() {
        return properties;
    }
}

