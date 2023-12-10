import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
    private JToolBar menu;
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

    private int indiceActual = 0;
    private List<String> listaImagenes;
    private ArrayList<JPanel>panelesPelis;
    private ArrayList<JLabel>imagenes;
    private ResourceBundle resourceBundle; // Variable para manejar los recursos
    String language;
    private ArrayList<JButton> botonesMenu;
    // Constructor
    public PantallaPrincipal() {


        language = "es";

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

        menu.add(new JButton("Default"));
        menu.add(new JButton("Default"));
        menu.add(new JButton("Default"));
        menu.add(new JButton("Default"));


        botonesMenu = new ArrayList<>();
        for (Component componente : menu.getComponents()) {
            if (componente instanceof JButton) {
                JButton boton = (JButton) componente;
                // Ahora puedes acceder y modificar el botón
                botonesMenu.add(boton);
            }
        }






        botonUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfiguracionMenu configuracionMenu = new ConfiguracionMenu(PantallaPrincipal.this);
                configuracionMenu.setVisible(true);
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

        textField1.setText("Texto para el JTextField");

        String[] opciones = {"Opción 1", "Opción 2", "Opción 3"};
        comboBox1.setModel(new DefaultComboBoxModel<>(opciones));



        // Obtener la lista de nombres de imágenes en la carpeta "imagenes"
        listaImagenes = obtenerNombresImagenes();

        if (!listaImagenes.isEmpty()) {
            mostrarImagen();
        } else {
            JOptionPane.showMessageDialog(null, "No hay imágenes en la carpeta 'imagenes'.");
        }
        for(JPanel panel : panelesPelis){
            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    System.out.println("Abrir ventana peli"+panel.getX());
                }
            });
        }
        cambiarIdioma(language);

    }
    public void refrescarIU(){

        textRecomen.setText(resourceBundle.getString("textRecomen"));
        textRecomen.setForeground(Color.WHITE);

        textCatalogo.setText(resourceBundle.getString("textCatalogo"));
        textCatalogo.setForeground(Color.WHITE);




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

        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(path)) {
            properties.load(input);

            textRecomen.setText(properties.getProperty("textRecomen"));
            textRecomen.setForeground(Color.WHITE);

            textCatalogo.setText(properties.getProperty("textCatalogo"));
            textCatalogo.setForeground(Color.WHITE);

            botonesMenu.get(0).setText(properties.getProperty("opcionBuscar"));
            botonesMenu.get(1).setText(properties.getProperty("opcionCuenta"));
            botonesMenu.get(2).setText(properties.getProperty("opcionAjustes"));
            botonesMenu.get(3).setText(properties.getProperty("opcionAyuda"));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error cargando el archivo de propiedades: " + e.getMessage());
        }
    }


    public static void main(String[] args) {




        JFrame frame = new JFrame("Prueba");
        frame.setSize(1030, 770);
        PantallaPrincipal pantallaPrincipal = new PantallaPrincipal();
        frame.setContentPane(pantallaPrincipal.principal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
