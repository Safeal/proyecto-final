package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.lang.reflect.Method;

public class TiendaCelularesApp {

    private static ArrayList<String> solicitudesVentas = new ArrayList<>();
    private static ArrayList<String> solicitudesReparaciones = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TiendaCelularesApp().crearMenuPrincipal());
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

        JButton btnVentas = crearBotonAnimado("Catálogo de Ventas", new Color(51, 153, 255));
        JButton btnReparaciones = crearBotonAnimado("Catálogo de Reparaciones", new Color(51, 204, 102));
        JButton btnSalir = crearBotonAnimado("Salir", new Color(255, 77, 77));

        btnVentas.addActionListener(e -> mostrarCatalogoVentas(frame));
        btnReparaciones.addActionListener(e -> mostrarCatalogoReparaciones(frame));
        btnSalir.addActionListener(e -> System.exit(0));

        JPanel panelBotones = new JPanel(new GridLayout(3, 50, 500, 10));
        panelBotones.setOpaque(false);
        panelBotones.add(btnVentas);
        panelBotones.add(btnReparaciones);
        panelBotones.add(btnSalir);

        frame.setLayout(new BorderLayout());
        frame.add(titulo, BorderLayout.NORTH);
        frame.add(panelBotones, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    // ---------- CATALOGO VENTAS ----------
    private void mostrarCatalogoVentas(JFrame frameAnterior) {
        JFrame frame = new JFrame("Catálogo de Ventas");
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Catálogo de Productos", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));

        String[] productos = {"iPhone 14", "Samsung S23", "Xiaomi 13", "Auriculares", "Cargador", "Otro producto"};
        JList<String> lista = new JList<>(productos);
        lista.setFont(new Font("Arial", Font.PLAIN, 18));

        JButton btnSolicitar = crearBotonAnimado("Solicitar Compra", new Color(51, 153, 255));
        btnSolicitar.addActionListener(e -> {
            String seleccionado = lista.getSelectedValue();
            if (seleccionado == null) {
                JOptionPane.showMessageDialog(frame, "Seleccione un producto primero.");
            } else {
                mostrarFormularioVenta(seleccionado.equals("Otro producto") ? "" : seleccionado, seleccionado.equals("Otro producto"));
                frame.dispose();
            }
        });

        JButton btnVolver = crearBotonAnimado("Volver", Color.GRAY);
        btnVolver.addActionListener(e -> {
            frame.dispose();
            frameAnterior.setVisible(true);
        });

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);
        panel.add(new JScrollPane(lista), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setOpaque(false);
        panelBotones.add(btnSolicitar);
        panelBotones.add(btnVolver);

        frame.setLayout(new BorderLayout(10, 10));
        frame.add(titulo, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(panelBotones, BorderLayout.SOUTH);
        frame.setVisible(true);
        frameAnterior.setVisible(false);
    }

    private void mostrarFormularioVenta(String producto, boolean esOtro) {
        JFrame frame = new JFrame("Formulario de Venta");
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        JLabel titulo = new JLabel("Solicitud de Compra", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));

        JTextField txtNombre = new JTextField(15);
        JTextField txtTelefono = new JTextField(10);
        JTextField txtDireccion = new JTextField(20);
        JTextField txtOtro = new JTextField(15);

        JPanel panel = new JPanel(new GridLayout(esOtro ? 6 : 5, 2, 10, 10));
        panel.setOpaque(false);

        panel.add(new JLabel("Producto seleccionado:"));
        panel.add(new JLabel(esOtro ? "(Especifique en el campo 'Otro producto')" : producto));

        if (esOtro) {
            panel.add(new JLabel("Otro producto (requerido):"));
            panel.add(txtOtro);
        }

        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Teléfono:"));
        panel.add(txtTelefono);
        panel.add(new JLabel("Dirección:"));
        panel.add(txtDireccion);

        // Barra de progreso
        JProgressBar barraProgreso = new JProgressBar(0, 100);
        barraProgreso.setStringPainted(true);
        barraProgreso.setVisible(false);

        JButton btnEnviar = crearBotonAnimado("Enviar Solicitud", new Color(51, 153, 255));
        btnEnviar.addActionListener(e -> {
            if (esOtro && txtOtro.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Debe especificar el nombre del producto.");
                return;
            }
            if (txtNombre.getText().trim().isEmpty() || txtTelefono.getText().trim().isEmpty() || txtDireccion.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Por favor complete todos los campos.");
                return;
            }

            barraProgreso.setVisible(true);
            btnEnviar.setEnabled(false);

            Timer timer = new Timer(30, null);
            timer.addActionListener(event -> {
                int valor = barraProgreso.getValue();
                if (valor < 100) {
                    barraProgreso.setValue(valor + 2);
                } else {
                    timer.stop();

                    String productoFinal = esOtro ? txtOtro.getText().trim() : producto;

                    String solicitud = "Cliente: " + txtNombre.getText().trim() +
                            ", Tel: " + txtTelefono.getText().trim() +
                            ", Dirección: " + txtDireccion.getText().trim() +
                            ", Producto: " + productoFinal;

                    solicitudesVentas.add(solicitud);
                    JOptionPane.showMessageDialog(frame, "Solicitud de venta enviada.");
                    frame.dispose();
                    crearMenuPrincipal();
                }
            });
            timer.start();
        });

        frame.setLayout(new BorderLayout(10, 10));
        frame.add(titulo, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(barraProgreso, BorderLayout.EAST);
        frame.add(btnEnviar, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // ---------- CATALOGO REPARACIONES ----------
    private void mostrarCatalogoReparaciones(JFrame frameAnterior) {
        JFrame frame = new JFrame("Catálogo de Reparaciones");
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Catálogo de Reparaciones", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));

        String[] reparaciones = {"Pantalla rota", "Cambio de batería", "Problema de software", "Cámara dañada", "Otro tipo"};
        JList<String> lista = new JList<>(reparaciones);
        lista.setFont(new Font("Arial", Font.PLAIN, 18));

        JButton btnSolicitar = crearBotonAnimado("Solicitar Reparación", new Color(51, 204, 102));
        btnSolicitar.addActionListener(e -> {
            String seleccionado = lista.getSelectedValue();
            if (seleccionado == null) {
                JOptionPane.showMessageDialog(frame, "Seleccione un tipo de reparación primero.");
            } else {
                mostrarFormularioReparacion(seleccionado.equals("Otro tipo") ? "" : seleccionado, seleccionado.equals("Otro tipo"));
                frame.dispose();
            }
        });

        JButton btnVolver = crearBotonAnimado("Volver", Color.GRAY);
        btnVolver.addActionListener(e -> {
            frame.dispose();
            frameAnterior.setVisible(true);
        });

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);
        panel.add(new JScrollPane(lista), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setOpaque(false);
        panelBotones.add(btnSolicitar);
        panelBotones.add(btnVolver);

        frame.setLayout(new BorderLayout(10, 10));
        frame.add(titulo, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(panelBotones, BorderLayout.SOUTH);
        frame.setVisible(true);
        frameAnterior.setVisible(false);
    }

    private void mostrarFormularioReparacion(String reparacion, boolean esOtro) {
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

        JPanel panel = new JPanel(new GridLayout(esOtro ? 8 : 7, 2, 10, 10));
        panel.setOpaque(false);

        panel.add(new JLabel("Reparación seleccionada:"));
        panel.add(new JLabel(esOtro ? "(Especifique en el campo 'Otro tipo')" : reparacion));

        if (esOtro) {
            panel.add(new JLabel("Otro tipo (requerido):"));
            panel.add(txtOtro);
        }

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

        // Barra de progreso
        JProgressBar barraProgreso = new JProgressBar(0, 100);
        barraProgreso.setStringPainted(true);
        barraProgreso.setVisible(false);

        JButton btnEnviar = crearBotonAnimado("Enviar Solicitud", new Color(51, 204, 102));
        btnEnviar.addActionListener(e -> {
            if (esOtro && txtOtro.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Debe especificar el tipo de reparación.");
                return;
            }
            if (txtNombre.getText().trim().isEmpty() || txtTelefono.getText().trim().isEmpty() ||
                    txtDireccion.getText().trim().isEmpty() || txtModelo.getText().trim().isEmpty() ||
                    txtFechaEntrega.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Por favor complete todos los campos.");
                return;
            }

            barraProgreso.setVisible(true);
            btnEnviar.setEnabled(false);

            Timer timer = new Timer(30, null);
            timer.addActionListener(event -> {
                int valor = barraProgreso.getValue();
                if (valor < 100) {
                    barraProgreso.setValue(valor + 2);
                } else {
                    timer.stop();

                    String reparacionFinal = esOtro ? txtOtro.getText().trim() : reparacion;

                    String solicitud = "Cliente: " + txtNombre.getText().trim() +
                            ", Tel: " + txtTelefono.getText().trim() +
                            ", Dirección: " + txtDireccion.getText().trim() +
                            ", Modelo: " + txtModelo.getText().trim() +
                            ", Reparación: " + reparacionFinal +
                            ", Fecha: " + txtFechaEntrega.getText().trim();

                    solicitudesReparaciones.add(solicitud);
                    JOptionPane.showMessageDialog(frame, "Solicitud de reparación enviada.");
                    frame.dispose();
                    crearMenuPrincipal();
                }
            });
            timer.start();
        });

        frame.setLayout(new BorderLayout(10, 10));
        frame.add(titulo, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(barraProgreso, BorderLayout.EAST);
        frame.add(btnEnviar, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    //Efecto de fade out (desvanecimiento) para JFrame
    private void fadeOut(JFrame frame, Runnable despues) {
        new Thread(() -> {
            try {
                for (float i = 1.0f; i >= 0; i -= 0.05f) {
                    setOpacity(frame, i);
                    Thread.sleep(25);
                }
                if (despues != null) {
                    SwingUtilities.invokeLater(despues);
                }
                SwingUtilities.invokeLater(frame::dispose);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    //Efecto de fade in (aparición suave) para JFrame
    private void fadeIn(JFrame frame) {
        new Thread(() -> {
            try {
                for (float i = 0; i <= 1.0f; i += 0.05f) {
                    setOpacity(frame, i);
                    Thread.sleep(25);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    //Método para cambiar la opacidad (funciona desde Java 7)
    private void setOpacity(JFrame frame, float opacity) {
        try {
            Class<?> awtUtilitiesClass = Class.forName("com.sun.awt.AWTUtilities");
            Method mSetWindowOpacity = awtUtilitiesClass.getMethod("setWindowOpacity", java.awt.Window.class, float.class);
            mSetWindowOpacity.invoke(null, frame, opacity);
        } catch (Exception ex) {
            // Alternativa si no funciona, usa JFrame.setOpacity (Java 7+)
            frame.setOpacity(opacity);
        }
    }

    private JButton crearBotonAnimado(String texto, Color colorBase) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Fuente más pequeña y moderna
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Tamaño preferido más pequeño
        boton.setPreferredSize(new Dimension(180, 40));

        // Hacer que el botón sea opaco y sin relleno de contenido por defecto para personalizar color
        boton.setContentAreaFilled(false);
        boton.setOpaque(true);
        boton.setBackground(colorBase);

        // Borde redondeado
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(colorBase.darker(), 1, true),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        Color hoverColor = colorBase.darker();

        final Timer[] animationTimer = new Timer[1];

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (animationTimer[0] != null && animationTimer[0].isRunning()) {
                    animationTimer[0].stop();
                }
                animationTimer[0] = new Timer(15, null);
                final int pasos = 20;
                final int[] pasoActual = {0};

                animationTimer[0].addActionListener(ev -> {
                    float t = pasoActual[0] / (float) pasos;
                    int r = (int) (colorBase.getRed() + t * (hoverColor.getRed() - colorBase.getRed()));
                    int g = (int) (colorBase.getGreen() + t * (hoverColor.getGreen() - colorBase.getGreen()));
                    int b = (int) (colorBase.getBlue() + t * (hoverColor.getBlue() - colorBase.getBlue()));
                    Color interpolado = new Color(r, g, b);
                    boton.setBackground(interpolado);
                    pasoActual[0]++;
                    if (pasoActual[0] > pasos) {
                        animationTimer[0].stop();
                    }
                });
                animationTimer[0].start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (animationTimer[0] != null && animationTimer[0].isRunning()) {
                    animationTimer[0].stop();
                }
                animationTimer[0] = new Timer(15, null);
                final int pasos = 20;
                final int[] pasoActual = {0};

                animationTimer[0].addActionListener(ev -> {
                    float t = pasoActual[0] / (float) pasos;
                    int r = (int) (hoverColor.getRed() + t * (colorBase.getRed() - hoverColor.getRed()));
                    int g = (int) (hoverColor.getGreen() + t * (colorBase.getGreen() - hoverColor.getGreen()));
                    int b = (int) (hoverColor.getBlue() + t * (colorBase.getBlue() - hoverColor.getBlue()));
                    Color interpolado = new Color(r, g, b);
                    boton.setBackground(interpolado);
                    pasoActual[0]++;
                    if (pasoActual[0] > pasos) {
                        animationTimer[0].stop();
                    }
                });
                animationTimer[0].start();
            }
        });

        return boton;   
    }
}
