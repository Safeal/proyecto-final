package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TiendaCelularesApp {

    private static ArrayList<String> solicitudesVentas = new ArrayList<>();
    private static ArrayList<String> solicitudesReparaciones = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TiendaCelularesApp().crearMenuPrincipal();
        });
    }

    // ---------- MENU PRINCIPAL ----------
    private void crearMenuPrincipal() {
        JFrame frame = new JFrame("Tienda de Celulares");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(245, 245, 245));

        JLabel titulo = new JLabel("Bienvenido a la Tienda de Celulares", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(new Color(0, 102, 204));

        JButton btnVentas = crearBoton("Catálogo de Ventas", new Color(51, 153, 255));
        JButton btnReparaciones = crearBoton("Catálogo de Reparaciones", new Color(51, 204, 102));
        JButton btnSalir = crearBoton("Salir", new Color(255, 77, 77));

        btnVentas.addActionListener(e -> mostrarCatalogoVentas());
        btnReparaciones.addActionListener(e -> mostrarCatalogoReparaciones());
        btnSalir.addActionListener(e -> System.exit(0));

        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 20, 20));
        panelBotones.setOpaque(false);
        panelBotones.add(btnVentas);
        panelBotones.add(btnReparaciones);
        panelBotones.add(btnSalir);

        frame.setLayout(new BorderLayout());
        frame.add(titulo, BorderLayout.NORTH);
        frame.add(panelBotones, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 24));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        return boton;
    }

    // ---------- CATALOGO VENTAS ----------
    private void mostrarCatalogoVentas() {
        JFrame frame = new JFrame("Catálogo de Ventas");
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Catálogo de Productos", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));

        String[] productos = {"iPhone 14", "Samsung S23", "Xiaomi 13", "Auriculares", "Cargador"};
        JList<String> lista = new JList<>(productos);
        lista.setFont(new Font("Arial", Font.PLAIN, 18));

        JButton btnSolicitar = crearBoton("Solicitar Compra", new Color(51, 153, 255));
        btnSolicitar.addActionListener(e -> {
            String seleccionado = lista.getSelectedValue();
            if (seleccionado == null) {
                JOptionPane.showMessageDialog(frame, "Seleccione un producto primero.");
            } else {
                mostrarFormularioVenta(seleccionado);
                frame.dispose();
            }
        });

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);
        panel.add(new JScrollPane(lista), BorderLayout.CENTER);
        panel.add(btnSolicitar, BorderLayout.SOUTH);

        frame.setLayout(new BorderLayout(10, 10));
        frame.add(titulo, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void mostrarFormularioVenta(String producto) {
        JFrame frame = new JFrame("Formulario de Venta");
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        JLabel titulo = new JLabel("Solicitud de Compra", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));

        JTextField txtNombre = new JTextField(15);
        JTextField txtTelefono = new JTextField(10);
        JTextField txtDireccion = new JTextField(20);
        JTextField txtOtro = new JTextField(15);

        JButton btnEnviar = crearBoton("Enviar Solicitud", new Color(51, 153, 255));
        btnEnviar.addActionListener(e -> {
            String productoFinal = producto;
            if (!txtOtro.getText().isEmpty()) productoFinal = txtOtro.getText();

            String solicitud = "Cliente: " + txtNombre.getText() +
                    ", Tel: " + txtTelefono.getText() +
                    ", Dirección: " + txtDireccion.getText() +
                    ", Producto: " + productoFinal;
            solicitudesVentas.add(solicitud);
            JOptionPane.showMessageDialog(frame, "Solicitud de venta enviada.");
            frame.dispose();
        });

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setOpaque(false);
        panel.add(new JLabel("Producto seleccionado:"));
        panel.add(new JLabel(producto));
        panel.add(new JLabel("Otro producto (opcional):"));
        panel.add(txtOtro);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Teléfono:"));
        panel.add(txtTelefono);
        panel.add(new JLabel("Dirección:"));
        panel.add(txtDireccion);

        frame.setLayout(new BorderLayout(10, 10));
        frame.add(titulo, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(btnEnviar, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // ---------- CATALOGO REPARACIONES ----------
    private void mostrarCatalogoReparaciones() {
        JFrame frame = new JFrame("Catálogo de Reparaciones");
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Catálogo de Reparaciones", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));

        String[] reparaciones = {"Pantalla rota", "Cambio de batería", "Problema de software", "Cámara dañada"};
        JList<String> lista = new JList<>(reparaciones);
        lista.setFont(new Font("Arial", Font.PLAIN, 18));

        JButton btnSolicitar = crearBoton("Solicitar Reparación", new Color(51, 204, 102));
        btnSolicitar.addActionListener(e -> {
            String seleccionado = lista.getSelectedValue();
            if (seleccionado == null) {
                JOptionPane.showMessageDialog(frame, "Seleccione un tipo de reparación primero.");
            } else {
                mostrarFormularioReparacion(seleccionado);
                frame.dispose();
            }
        });

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);
        panel.add(new JScrollPane(lista), BorderLayout.CENTER);
        panel.add(btnSolicitar, BorderLayout.SOUTH);

        frame.setLayout(new BorderLayout(10, 10));
        frame.add(titulo, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void mostrarFormularioReparacion(String reparacion) {
        JFrame frame = new JFrame("Formulario de Reparación");
        frame.setSize(600, 450);
        frame.setLocationRelativeTo(null);

        JLabel titulo = new JLabel("Solicitud de Reparación", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));

        JTextField txtNombre = new JTextField(15);
        JTextField txtTelefono = new JTextField(10);
        JTextField txtDireccion = new JTextField(20);
        JTextField txtModelo = new JTextField(15);
        JTextField txtFechaEntrega = new JTextField(10);
        JTextField txtOtro = new JTextField(15);

        JButton btnEnviar = crearBoton("Enviar Solicitud", new Color(51, 204, 102));
        btnEnviar.addActionListener(e -> {
            String reparacionFinal = reparacion;
            if (!txtOtro.getText().isEmpty()) reparacionFinal = txtOtro.getText();

            String solicitud = "Cliente: " + txtNombre.getText() +
                    ", Tel: " + txtTelefono.getText() +
                    ", Dirección: " + txtDireccion.getText() +
                    ", Modelo: " + txtModelo.getText() +
                    ", Reparación: " + reparacionFinal +
                    ", Fecha: " + txtFechaEntrega.getText();
            solicitudesReparaciones.add(solicitud);
            JOptionPane.showMessageDialog(frame, "Solicitud de reparación enviada.");
            frame.dispose();
        });

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setOpaque(false);
        panel.add(new JLabel("Reparación seleccionada:"));
        panel.add(new JLabel(reparacion));
        panel.add(new JLabel("Otro tipo (opcional):"));
        panel.add(txtOtro);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Teléfono:"));
        panel.add(txtTelefono);
        panel.add(new JLabel("Dirección:"));
        panel.add(txtDireccion);
        panel.add(new JLabel("Modelo de teléfono:"));
        panel.add(txtModelo);
        panel.add(new JLabel("Fecha de entrega:"));
        panel.add(txtFechaEntrega);

        frame.setLayout(new BorderLayout(10, 10));
        frame.add(titulo, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(btnEnviar, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
