import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Vector;

public class ConfiguracionMenu extends JFrame {

    private JComboBox<String> idiomaComboBox;
    private ResourceBundle resourceBundle;
    PantallaPrincipal pantalla;
    JButton aceptarBoton;
    ArrayList<String> idiomasDisponibles;
    JLabel textoIdioma;

    public ConfiguracionMenu(PantallaPrincipal pantalla) {
        this.pantalla = pantalla;
        idiomaComboBox = new JComboBox<>();
        cargarIdiomas();
        ImageIcon icono = new ImageIcon("imagenes/logo.png"); // Ajusta la ruta según tu estructura de carpetas

        this.setIconImage(icono.getImage());


        this.setResizable(false);
        initUI();



    }
    public void cargarIdiomas(){
        File carpetaIdiomas = new File("idiomas");
       idiomasDisponibles = new ArrayList<>();
        if (carpetaIdiomas.isDirectory()) {
            // Obtener la lista de archivos en la carpeta
            File[] archivos = carpetaIdiomas.listFiles();

            // Imprimir los nombres de los archivos
            if (archivos != null) {
                for (File archivo : archivos) {
                    System.out.println(archivo.getName().replaceAll(".*_(.*?)\\.properties", "$1"));
                    idiomasDisponibles.add(archivo.getName().replaceAll(".*_(.*?)\\.properties", "$1"));

                }
            } else {
                System.out.println("La carpeta está vacía o no se puede acceder.");
            }
        }
        idiomaComboBox.removeAllItems();
        for(String idioma:idiomasDisponibles) idiomaComboBox.addItem(idioma);
    }
    private void translate(){
        Properties properties = pantalla.getProperties();
        setTitle(properties.getProperty("ventanaIdioma"));
        textoIdioma.setText(properties.getProperty("seleccionaIdioma"));
        aceptarBoton.setText(properties.getProperty("aceptar"));
    }

    public void initUI() {
        // Configuración de la ventana principal
        Properties properties = pantalla.getProperties();
        setTitle(properties.getProperty("ventanaIdioma"));
        setSize(400, 100);
        setLocationRelativeTo(null);
        aceptarBoton=new JButton(properties.getProperty("aceptar"));

        aceptarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pantalla.cambiarIdioma(idiomasDisponibles.get(idiomaComboBox.getSelectedIndex()));
                translate();
            }
        });
        // Crear el panel principal
        JPanel panel = new JPanel();

        textoIdioma =new  JLabel(properties.getProperty("seleccionaIdioma"));
        panel.add(textoIdioma, BorderLayout.CENTER);
        panel.add(idiomaComboBox, BorderLayout.CENTER);
        panel.add(aceptarBoton,BorderLayout.SOUTH);
        // Añadir el panel a la ventana
        add(panel);


    }






}
