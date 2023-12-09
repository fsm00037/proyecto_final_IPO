import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Prueba {
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
    private JPanel panelPeli2;
    private JPanel panelPeli3;
    private JPanel panelPeli4;
    private JLabel peli4;
    private JLabel peli2;
    private JLabel peli3;
    private JPanel panelPeli1;

    private int indiceActual = 0;
    private List<String> listaImagenes;

    // Constructor
    public Prueba() {
        textRecomen.setText("Texto para Recomendaciones");
        textRecomen.setForeground(Color.WHITE);

        textCatalogo.setText("Texto para Catálogo");
        textCatalogo.setForeground(Color.WHITE);

        botonUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "¡Haz clic en el botón de usuario!");
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

        menu.add(new JButton("Accion1"));
        menu.add(new JButton("Accion2"));
        menu.add(new JButton("Accion3"));
        menu.add(new JButton("Accion4"));
        menu.add(new JButton("Accion5"));

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

            int siguiente1 = (indiceActual + 1) % listaImagenes.size();
            int siguiente2 = (indiceActual + 2) % listaImagenes.size();
            int siguiente3 = (indiceActual + 3) % listaImagenes.size();

            // Escalar y establecer el mismo tamaño para las otras imágenes
            peli2.setIcon(escalarImagen("imagenes/" + listaImagenes.get(siguiente1), 200, 300));
            peli3.setIcon(escalarImagen("imagenes/" + listaImagenes.get(siguiente2), 200, 300));
            peli4.setIcon(escalarImagen("imagenes/" + listaImagenes.get(siguiente3), 200, 300));

        } catch (Exception e) {
            e.printStackTrace(); // Imprime la traza de la excepción
            JOptionPane.showMessageDialog(null, "Error al cargar la imagen: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Prueba");
        frame.setSize(1030, 770);
        frame.setContentPane(new Prueba().principal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

