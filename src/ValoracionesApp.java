import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Properties;

public class ValoracionesApp extends JFrame {
    private DefaultListModel<String> valoracionesListModel;
    private JList<String> valoracionesList;
    private JTextArea comentarioTextArea;
    JButton publicarButton;
    Properties properties;

    public ValoracionesApp() {
        // Configuración de la ventana principal
        setTitle("Valoraciones de Películas");
        setSize(600, 300);
        getContentPane().setBackground(new Color(35, 45, 63)); // #232D3F - Color de fondo de la ventana

        // Inicialización de componentes
        valoracionesListModel = new DefaultListModel<>();
        valoracionesList = new JList<>(valoracionesListModel);
        valoracionesList.setCellRenderer(new ValoracionCellRenderer()); // Utilizar el renderer personalizado
        valoracionesList.setBackground(new Color(35, 45, 63)); // #232D3F - Color de fondo de la ventana
        JScrollPane valoracionesScrollPane = new JScrollPane(valoracionesList);

        comentarioTextArea = new JTextArea();
        comentarioTextArea.setText("Escribe tu reseña...");
        comentarioTextArea.setForeground(Color.WHITE); // Texto en color blanco

        comentarioTextArea.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (comentarioTextArea.getText().equals("Escribe tu reseña...")) {
                    comentarioTextArea.setText("");
                    comentarioTextArea.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (comentarioTextArea.getText().isEmpty()) {
                    comentarioTextArea.setText("Escribe tu reseña...");
                    comentarioTextArea.setForeground(Color.WHITE);
                }
            }
        });

        // Establecer propiedades para que el texto se ajuste automáticamente
        comentarioTextArea.setLineWrap(true);
        comentarioTextArea.setWrapStyleWord(true);

        JScrollPane comentarioScrollPane = new JScrollPane(comentarioTextArea);

        publicarButton = new JButton("Publicar");
        publicarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarValoracion();
            }
        });
        publicarButton.setBackground(new Color(0, 129, 112)); // #008170 - Color verde oscuro
        publicarButton.setForeground(Color.WHITE); // Texto en color blanco

        // Configuración del diseño
        setLayout(new BorderLayout());

        add(valoracionesScrollPane, BorderLayout.CENTER);

        // Crear un panel verde para la entrada de texto
        JPanel panelEntrada = new JPanel();
        panelEntrada.setBackground(new Color(0, 129, 112)); // #008170 - Color verde oscuro
        panelEntrada.setLayout(new BorderLayout());
        panelEntrada.setBorder(new EmptyBorder(5, 5, 5, 5)); // Añadir márgenes
        panelEntrada.add(comentarioScrollPane, BorderLayout.CENTER);

        // Alinear el botón a la derecha
        JPanel panelBoton = new JPanel();
        panelBoton.setLayout(new BorderLayout());
        panelBoton.setBorder(new EmptyBorder(5, 5, 5, 5)); // Añadir márgenes
        panelBoton.add(publicarButton, BorderLayout.EAST);

        // Crear un panel que contiene la entrada de texto y el botón
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BorderLayout());
        panelInferior.add(panelEntrada, BorderLayout.CENTER);
        panelInferior.add(panelBoton, BorderLayout.EAST);

        add(panelInferior, BorderLayout.SOUTH);
    }

    private void agregarValoracion() {
        String nuevoComentario = comentarioTextArea.getText();
        if (!nuevoComentario.equals(properties.getProperty("escRes")) && !nuevoComentario.isEmpty()) {
            valoracionesListModel.addElement("ujaen_user:" + nuevoComentario);
            comentarioTextArea.setText(properties.getProperty("escRes"));
            comentarioTextArea.setForeground(Color.WHITE);
            comentarioTextArea.setCaretPosition(0);
        }
    }

    public void traducir(Properties prop) {
        properties = prop;
        setTitle(properties.getProperty("criticas"));
        comentarioTextArea.setText(properties.getProperty("escRes"));
        publicarButton.setText(properties.getProperty("publicar"));
    }

    // Renderer personalizado para dar formato estético a cada elemento de la lista
    private class ValoracionCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JPanel panelCompleto = new JPanel();
            panelCompleto.setLayout(new BorderLayout());
            panelCompleto.setBorder(new EmptyBorder(10, 15, 10, 15)); // Añadir más márgenes

            // Agregar un panel verde como cabecera para el nombre de usuario
            JPanel panelUsuario = new JPanel();
            panelUsuario.setBackground(new Color(0, 129, 112)); // #008170 - Color verde oscuro
            panelUsuario.setLayout(new BorderLayout());
            JLabel labelUsuario = new JLabel(value.toString().split(":")[0]); // Obtener el nombre de usuario
            labelUsuario.setForeground(Color.WHITE); // Texto en color blanco
            panelUsuario.add(labelUsuario, BorderLayout.CENTER);

            // Crear un panel que contiene el panel de usuario y el contenido de la valoración
            panelCompleto.add(panelUsuario, BorderLayout.NORTH);

            JTextArea textoValoracion = new JTextArea(value.toString().split(":")[1]);
            textoValoracion.setLineWrap(true);
            textoValoracion.setWrapStyleWord(true);
            textoValoracion.setEditable(false);

            // Cambios en el color de fondo y borde de la caja de valoración
            panelCompleto.setBackground(new Color(35, 45, 63)); // #232D3F - Color de fondo de la ventana
            panelCompleto.setBorder(new LineBorder(new Color(35, 45, 63), 2)); // Borde del color #232D3F

            // Añadir márgenes al texto
            textoValoracion.setBorder(new EmptyBorder(5, 5, 5, 5));

            textoValoracion.setForeground(Color.BLACK); // Texto en color blanco
            textoValoracion.setFont(new Font("Arial", Font.PLAIN, 14)); // Tamaño de fuente más grande

            // Espacio entre valoraciones
            panelCompleto.add(textoValoracion, BorderLayout.CENTER);

            // Hacer que el panel se ajuste automáticamente al contenido
            panelCompleto.setPreferredSize(new Dimension(textoValoracion.getPreferredSize().width + 30,
                    textoValoracion.getPreferredSize().height + 30));

            return panelCompleto;
        }
    }


    public void cargarComentariosIniciales(ArrayList<String> comentariosIniciales) {
        valoracionesListModel.clear();
        for (String comentario : comentariosIniciales) {
            valoracionesListModel.addElement(comentario);
        }
    }


}

