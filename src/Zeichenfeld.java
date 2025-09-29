import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Zeichenfeld extends JPanel {

    //Variablen erzuegen
    private BufferedImage image;
    public enum Tool{ELLIPSE, LINIE, RECHTECK, STIFT, RADIERGUMMI}
    private Tool aktuellesTool = Tool.STIFT;
    private Color aktuelleFarbe = Color.BLACK;
    private int strichstaerke = 5;
    private int startX, startY, prevX, prevY;
    private boolean istGeaendert = false;



    // Zeichenfeld vorbereiten: Bild erzeugen, weiß füllen, Listener hinzufügen
    public Zeichenfeld(int breite, int hoehe) {
        image = new BufferedImage(breite, hoehe, BufferedImage.TYPE_INT_RGB);
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

        // BufferedImage wird weiß gefüllt
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, breite, hoehe);
        g2d.dispose();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();
                prevX = startX;
                prevY = startY;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (aktuellesTool== Tool.LINIE) {
                    Graphics2D g2d = image.createGraphics();
                    g2d.setColor(aktuelleFarbe);
                    g2d.setStroke(new BasicStroke(strichstaerke));
                    g2d.drawLine(startX, startY, e.getX(), e.getY());
                    g2d.dispose();
                    setIstGeaendert(true);
                } else if (aktuellesTool== Tool.RECHTECK) {
                    Graphics2D g2d = image.createGraphics();
                    g2d.setColor(aktuelleFarbe);
                    g2d.setStroke(new BasicStroke(strichstaerke));
                    int x = Math.min(startX, e.getX());
                    int y = Math.min(startY, e.getY());
                    int w = Math.abs(e.getX() - startX);
                    int h = Math.abs(e.getY() - startY);
                    g2d.drawRect(x, y, w, h);
                    g2d.dispose();
                    setIstGeaendert(true);
                } else if (aktuellesTool== Tool.ELLIPSE) {
                    Graphics2D g2d = image.createGraphics();
                    g2d.setColor(aktuelleFarbe);
                    g2d.setStroke(new BasicStroke(strichstaerke));
                    int x = Math.min(startX, e.getX());
                    int y = Math.min(startY, e.getY());
                    int w = Math.abs(e.getX() - startX);
                    int h = Math.abs(e.getY() - startY);
                    g2d.drawOval(x, y, w, h);
                    g2d.dispose();
                    setIstGeaendert(true);
                }
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (aktuellesTool== Tool.STIFT) {
                    Graphics2D g2d = image.createGraphics();
                    g2d.setColor(aktuelleFarbe);
                    g2d.setStroke(new BasicStroke(strichstaerke));
                    g2d.drawLine(prevX, prevY, e.getX(), e.getY());
                    g2d.dispose();
                    setIstGeaendert(true);
                    prevX = e.getX();
                    prevY = e.getY();
                    repaint();
                } else if (aktuellesTool== Tool.RADIERGUMMI) {
                    Graphics2D g2d = image.createGraphics();
                    g2d.setColor(Color.WHITE);
                    g2d.setStroke(new BasicStroke(strichstaerke));
                    g2d.fillRect(e.getX() - 5, e.getY() - 5, strichstaerke, strichstaerke);
                    g2d.dispose();
                    setIstGeaendert(true);
                    repaint();
                }
            }
        });

    }

    //Methoden erzeugen:

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

    //Methoden für das aktuell angewählte Tool/ Werkzeug und Farbe
    public void setAktuellesTool(Tool tool) {
        aktuellesTool= tool;
    }
    public void setAktuelleFarbe(Color color) {
        aktuelleFarbe= color;
    }

    public BufferedImage getImage() {
        return image;
    }
    public void setImage(BufferedImage img) {
        image= img;
        repaint();
    }

    // Mehtode um die Strichstärke zu setzen
    public void setStrichstaerke(int strichstaerke) {
        this.strichstaerke = strichstaerke;
    }

    // Mehtode um den Zeichen- Coursor einzustellen
    public void setZeichnenCursor(Cursor cursor) {
        this.setCursor(cursor);
    }

    // Mehtode um Änderungen des Zeichenfeldes zu erkennen (für Speicherzweck)
    public boolean getIstGeaendert() {
        return istGeaendert;
    }
    public void setIstGeaendert(boolean istGeaendert) {
        this.istGeaendert = istGeaendert;
    }

}
