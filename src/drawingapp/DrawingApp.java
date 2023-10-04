package drawingapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;

public class DrawingApp extends JFrame implements KeyListener {

    private JPanel panelDibujo;
    private List<InfoFigura> seleccion = new ArrayList<>();
    private List<InfoFigura> figuras = new ArrayList<>();
    private String figuraSeleccionada = "Círculo"; // Figura seleccionada
    private boolean estaArrastrando = false;
    private int arrastradoX, arrastradoY;
    private boolean estaTrasladando = false;
    private InfoFigura infoFiguraSeleccionada = null;
    private boolean inFigura = false;
    private Color colorContorno = Color.BLACK;


    public DrawingApp() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 500, 800, 600);
        setLayout(new BorderLayout());
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        Font fuente = new Font("Dialog", Font.BOLD, 16);

        JPanel panelControl = new JPanel();
        GridLayout gridLayout = new GridLayout(2, 4, 10, 40); // 2 filas, 4 columnas, espacio horizontal y vertical
        panelControl.setLayout(gridLayout);
        JComboBox<String> cboFiguras = new JComboBox<>(new String[]{"Círculo", "Elipse", "Rectángulo", "Cuadrado", "Triángulo"});
        panelControl.add(cboFiguras);

        cboFiguras.setPreferredSize(new Dimension(120, 30));
        cboFiguras.setFont(fuente);

        JButton btnRotar = new JButton("Rotar");
        JButton btnEscalar = new JButton("Escalar");
        JButton btnShear = new JButton("Shear");
        JButton btnTrasladar = new JButton("Trasladar");
        JButton btnAgregarFigura = new JButton("Agregar Figura");
        JButton btnBorrar = new JButton("Borrar");
        JButton btnEscalarMenos = new JButton("-");
        JButton btnShearMenos = new JButton("-");
        JButton btnCambiarColor = new JButton("Cambiar Color");
        JButton btnSeleccionarTodo = new JButton("Seleccionar Todo");
        
        


        btnRotar.setFont(fuente);
        btnEscalar.setFont(fuente);
        btnShear.setFont(fuente);
        btnTrasladar.setFont(fuente);

        btnAgregarFigura.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
        btnAgregarFigura.setPreferredSize(new Dimension(150, 35));
        btnAgregarFigura.setFont(fuente);

        btnBorrar.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        btnBorrar.setPreferredSize(new Dimension(100, 35));
        btnBorrar.setFont(fuente);

        btnShear.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
        btnShearMenos.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));

        btnEscalar.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
        btnEscalarMenos.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));

        btnCambiarColor.setFont(fuente);
        
        btnSeleccionarTodo.setPreferredSize(new Dimension(150, 35));
        btnSeleccionarTodo.setFont(fuente);
        
        panelControl.add(cboFiguras);
        panelControl.add(btnRotar);
        panelControl.add(btnEscalar);
        panelControl.add(btnShear);
        panelControl.add(btnTrasladar);
        panelControl.add(btnAgregarFigura);
        panelControl.add(btnBorrar);
        panelControl.add(btnEscalarMenos);
        panelControl.add(btnShearMenos);
        panelControl.add(btnCambiarColor);
        panelControl.add(btnSeleccionarTodo);

        add(panelControl, BorderLayout.NORTH);

        for (InfoFigura figura : seleccion) {
            System.out.println("Figura seleccionada: " + figura);
        }

        panelDibujo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g;

                for (InfoFigura infoFigura : figuras) {
                    g2d.setTransform(new AffineTransform());

                    AffineTransform transform = new AffineTransform();
                    transform.translate(infoFigura.getX(), infoFigura.getY());
                    transform.scale(infoFigura.getEscalaX(), infoFigura.getEscalaY());
                    transform.shear(infoFigura.getShearX(), infoFigura.getShearY());
                    transform.rotate(Math.toRadians(infoFigura.getAnguloRotacion()));

                    g2d.transform(transform);
                    if (seleccion.contains(infoFigura)) {
                        g2d.setColor(Color.BLACK); // Usa el color de contorno cuando está seleccionada4
                        g2d.setStroke(new BasicStroke(10));
                        g2d.draw(infoFigura.getFigura());
                        System.out.println("Akim");
                    }
                    g2d.setColor(infoFigura.getColor());

                    g2d.fill(infoFigura.getFigura());
                }
            }

        };

        add(panelDibujo, BorderLayout.CENTER);

        setFocusable(true);
        requestFocusInWindow();

        this.addKeyListener(
                new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e
            ) {
                int keyCode = e.getKeyCode();
                System.out.println("Tecla presionada: " + KeyEvent.getKeyText(keyCode)); // Imprime la tecla presionada 
                int stepSize = 5; // Tamaño del paso de movimiento

                if (estaArrastrando && infoFiguraSeleccionada != null) {
                    if (keyCode == KeyEvent.VK_R) { // Tecla R: Rotar
                        infoFiguraSeleccionada.setAnguloRotacion(infoFiguraSeleccionada.getAnguloRotacion() + 15);
                        System.out.println("Tecla presionada: " + KeyEvent.getKeyText(keyCode)); // Imprime la tecla presionada 
                    } else if (keyCode == KeyEvent.VK_H) { // Tecla H: Shear
                        infoFiguraSeleccionada.setShearX(infoFiguraSeleccionada.getShearX() + 0.1);
                        infoFiguraSeleccionada.setShearY(infoFiguraSeleccionada.getShearY() + 0.1);
                    } else if (keyCode == KeyEvent.VK_E) { // Tecla E: Escalar
                        infoFiguraSeleccionada.setEscalaX(infoFiguraSeleccionada.getEscalaX() + 0.2);
                        infoFiguraSeleccionada.setEscalaY(infoFiguraSeleccionada.getEscalaY() + 0.2);
                    } else if (keyCode == KeyEvent.VK_W) { // Tecla W: Mover hacia arriba
                        infoFiguraSeleccionada.setY(infoFiguraSeleccionada.getY() - stepSize);
                    } else if (keyCode == KeyEvent.VK_S) { // Tecla S: Mover hacia abajo
                        infoFiguraSeleccionada.setY(infoFiguraSeleccionada.getY() + stepSize);
                    } else if (keyCode == KeyEvent.VK_A) { // Tecla A: Mover hacia la izquierda
                        infoFiguraSeleccionada.setX(infoFiguraSeleccionada.getX() - stepSize);
                    } else if (keyCode == KeyEvent.VK_D) { // Tecla D: Mover hacia la derecha
                        infoFiguraSeleccionada.setX(infoFiguraSeleccionada.getX() + stepSize);
                    }

                    panelDibujo.repaint();
                }
            }
        }
        );

        panelDibujo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!estaArrastrando) {
                    InfoFigura figuraClic = mouseFig(e.getX(), e.getY());

                    if (figuraClic != null) {
                        if (seleccion.contains(figuraClic)) {
                            // Si se hace clic en una figura seleccionada, quitarla de la lista de selección.
                            seleccion.remove(figuraClic);
                        } else {
                            // Si se hace clic en una nueva figura, agregarla a la lista de selección.
                            seleccion.add(figuraClic);
                        }
                    }

                    panelDibujo.repaint();
                }
            }
        });

        btnShearMenos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (InfoFigura infoFigura : seleccion) {
                    infoFigura.setShearX(infoFigura.getShearX() - 0.1);
                    infoFigura.setShearY(infoFigura.getShearY() - 0.1);
                }
                panelDibujo.repaint();
            }
        });

        cboFiguras.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                JComboBox<String> combo = (JComboBox<String>) e.getSource();
                figuraSeleccionada = (String) combo.getSelectedItem();
            }
        }
        );

        btnRotar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                for (InfoFigura infoFigura : seleccion) {
                    infoFigura.setAnguloRotacion(infoFigura.getAnguloRotacion() + 15);
                }
                panelDibujo.repaint();
            }
        }
        );

        btnEscalar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                for (InfoFigura infoFigura : seleccion) {
                    infoFigura.setEscalaX(infoFigura.getEscalaX() + 0.2);
                    infoFigura.setEscalaY(infoFigura.getEscalaY() + 0.2);
                }
                panelDibujo.repaint();
            }
        }
        );

        btnEscalarMenos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                for (InfoFigura infoFigura : seleccion) {
                    infoFigura.setEscalaX(infoFigura.getEscalaX() - 0.2);
                    infoFigura.setEscalaY(infoFigura.getEscalaY() - 0.2);
                }
                panelDibujo.repaint();
            }
        }
        );

        btnShear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                for (InfoFigura infoFigura : seleccion) {
                    infoFigura.setShearX(infoFigura.getShearX() + 0.1);
                    infoFigura.setShearY(infoFigura.getShearY() + 0.1);
                }
                panelDibujo.repaint();
            }
        }
        );

        btnShearMenos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                for (InfoFigura infoFigura : seleccion) {
                    infoFigura.setShearX(infoFigura.getShearX() - 0.1);
                    infoFigura.setShearY(infoFigura.getShearY() - 0.1);
                }
                panelDibujo.repaint();
            }
        }
        );

        btnTrasladar.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                estaTrasladando = !estaTrasladando;
                if (estaTrasladando) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
                    estaArrastrando = false;
                } else {
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        }
        );

        btnBorrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (InfoFigura infoFigura : seleccion) {
                    figuras.remove(infoFigura); // Elimina la figura de la lista de figuras
                }
                seleccion.clear(); // Borra todas las figuras seleccionadas
                panelDibujo.repaint(); // Vuelve a pintar el panel para reflejar los cambios
            }
        });
        
        btnSeleccionarTodo.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        seleccion.clear(); // Limpia cualquier selección previa
        
        // Agrega todas las figuras al arreglo de selección
        seleccion.addAll(figuras);
        
        panelDibujo.repaint(); // Vuelve a pintar el panel para reflejar la selección
    }
});


        btnCambiarColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!seleccion.isEmpty()) {
                    Color nuevoColor = JColorChooser.showDialog(null, "Seleccione un color", Color.BLACK);
                    if (nuevoColor != null) {
                        for (InfoFigura infoFigura : seleccion) {
                            infoFigura.setColor(nuevoColor);
                        }
                        panelDibujo.repaint();
                    }
                }
            }
        });

        panelDibujo.addMouseListener(
                new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e
            ) {
                if (estaTrasladando) {
                    infoFiguraSeleccionada = mouseFig(e.getX(), e.getY());
                    if (infoFiguraSeleccionada != null) {
                        estaArrastrando = true;
                        arrastradoX = e.getX() - infoFiguraSeleccionada.getX();
                        arrastradoY = e.getY() - infoFiguraSeleccionada.getY();
                        inFigura = true;

                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (estaArrastrando) {
                    estaArrastrando = false;
                    infoFiguraSeleccionada = null;
                }
            }
        }
        );

        panelDibujo.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (estaArrastrando && infoFiguraSeleccionada != null) {
                    infoFiguraSeleccionada.setX(e.getX() - arrastradoX);
                    infoFiguraSeleccionada.setY(e.getY() - arrastradoY);
                    panelDibujo.repaint();
                }
            }
        });

        btnAgregarFigura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarFigura();
                panelDibujo.repaint();
            }
        });

    }

    private InfoFigura mouseFig(int x, int y) {
        for (InfoFigura infoFigura : figuras) {
            if (infoFigura.getFigura().contains(x - infoFigura.getX(), y - infoFigura.getY())) {
                return infoFigura;
            }
        }
        return null;
    }

    private void agregarFigura() {
        int centroX = getWidth() / 2;
        int centroY = getHeight() / 2;

        Shape figura = null;
        Color color = obtenerColorAleatorio();

        if (figuraSeleccionada.equals("Círculo")) {
            figura = new Ellipse2D.Double(-50, -50, 100, 100);
        } else if (figuraSeleccionada.equals("Elipse")) {
            figura = new Ellipse2D.Double(-60, -40, 120, 80);
        } else if (figuraSeleccionada.equals("Rectángulo")) {
            figura = new Rectangle2D.Double(-60, -40, 120, 80);
        } else if (figuraSeleccionada.equals("Cuadrado")) {
            figura = new Rectangle2D.Double(-50, -50, 100, 100);
        } else if (figuraSeleccionada.equals("Triángulo")) {
            int[] xPoints = {-50, 0, 50};
            int[] yPoints = {50, -50, 50};
            figura = new Polygon(xPoints, yPoints, 3);
        }

        InfoFigura infoFigura = new InfoFigura(figura, 0, 1, 1, 0, 0, centroX, centroY, color, colorContorno);
        figuras.add(infoFigura);
    }

    private Color obtenerColorAleatorio() {
        int r, g, b;

        do {
            r = (int) (Math.random() * 256);
            g = (int) (Math.random() * 256);
            b = (int) (Math.random() * 256);
        } while (r == 0 && g == 0 && b == 0); // Repetir hasta que no se forme el color negro

        return new Color(r, g, b);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            DrawingApp frame = new DrawingApp();
            frame.setVisible(true);
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    private class InfoFigura {

        private Shape figura;
        private double anguloRotacion;
        private double escalaX;
        private double escalaY;
        private double shearX;
        private double shearY;
        private int x;
        private int y;
        private Color color;
        private Color colorContorno;
        

        public InfoFigura(Shape figura, double anguloRotacion, double escalaX, double escalaY, double shearX, double shearY, int x, int y, Color color, Color colorContorno) {
            this.figura = figura;
            this.anguloRotacion = anguloRotacion;
            this.escalaX = escalaX;
            this.escalaY = escalaY;
            this.shearX = shearX;
            this.shearY = shearY;
            this.x = x;
            this.y = y;
            this.color = color;
            this.colorContorno = colorContorno;
            

        }

        public Shape getFigura() {
            return figura;
        }

        public double getAnguloRotacion() {
            return anguloRotacion;
        }

        public void setAnguloRotacion(double anguloRotacion) {
            this.anguloRotacion = anguloRotacion;
        }

        public double getEscalaX() {
            return escalaX;
        }

        public void setEscalaX(double escalaX) {
            this.escalaX = escalaX;
        }

        public double getEscalaY() {
            return escalaY;
        }

        public void setEscalaY(double escalaY) {
            this.escalaY = escalaY;
        }

        public double getShearX() {
            return shearX;
        }

        public void setShearX(double shearX) {
            this.shearX = shearX;
        }

        public double getShearY() {
            return shearY;
        }

        public void setShearY(double shearY) {
            this.shearY = shearY;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public Color getColor() {
            return color;
        }
        
        public void setColor(Color color) {
           this.color = color;
    }


        public Color getColorContorno() {
            return color;
        }

    }
}
