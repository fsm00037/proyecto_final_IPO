import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;

public class ConfiguracionMenu extends JFrame {

    private JComboBox<String> idiomaComboBox;
    private ResourceBundle resourceBundle;
    PantallaPrincipal pantalla;

    public ConfiguracionMenu(PantallaPrincipal pantalla) {
        this.pantalla = pantalla;
        initUI();


    }

    private void initUI() {
        // Configuración de la ventana principal
        setTitle("Configuración de Idioma");
        setSize(300, 200);
        setLocationRelativeTo(null);

        // Crear el ComboBox para seleccionar el idioma
        File carpetaIdiomas = new File("idiomas");
        ArrayList<String> idiomasDisponibles = new ArrayList<>();
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

            idiomaComboBox = new JComboBox<>(new Vector<>(idiomasDisponibles));
        }

        idiomaComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pantalla.cambiarIdioma(idiomasDisponibles.get(idiomaComboBox.getSelectedIndex()));

            }
        });

        // Crear el panel principal
        JPanel panel = new JPanel();
        panel.add(new JLabel("Selecciona Idioma:"));
        panel.add(idiomaComboBox);

        // Añadir el panel a la ventana
        add(panel);

    }





    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
              //  new ConfiguracionMenu().setVisible(true);
            }
        });
    }
}
