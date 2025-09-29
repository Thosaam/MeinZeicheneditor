import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Menubar extends JMenuBar {
    //Variablen erzeugen
    private final JFrame frame;
    private final Zeichenfeld panel;
    private File aktuelleDatei = null;

    public Menubar(JFrame frame, Zeichenfeld panel) {
        this.frame= frame;
        this.panel= panel;

        JMenu dateiMenu= new JMenu("Datei");
        dateiMenu.setMnemonic(KeyEvent.VK_D);
        dateiMenu.setToolTipText("Datei Menü (Alt + D)");
        ImageIcon iconNeu= new ImageIcon("Icons/new24.gif");
        ImageIcon iconOeffnen= new ImageIcon("Icons/open24.gif");
        ImageIcon iconSpeichern= new ImageIcon("Icons/save24.gif");
        ImageIcon iconSpeichernUnter= new ImageIcon("Icons/save24.gif");
        JMenuItem neu =new JMenuItem("Neu", iconNeu );
        neu.setMnemonic('N');
        neu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        JMenuItem oeffnen=new JMenuItem("Öffnen",iconOeffnen);
        oeffnen.setMnemonic('O');
        oeffnen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        JMenuItem speichern= new JMenuItem("Speichern", iconSpeichern);
        speichern.setMnemonic('S');
        speichern.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        JMenuItem speichernUnter= new JMenuItem("Speichern unter", iconSpeichernUnter);
        speichernUnter.setMnemonic('U');
        speichernUnter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));

        dateiMenu.add(neu);
        dateiMenu.add(oeffnen);
        dateiMenu.add(speichern);
        dateiMenu.add(speichernUnter);
        add(dateiMenu);

        // Action Listener für die Dateioptionen
        neu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                neu();
            }
        });

        oeffnen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oeffnen();
            }
        });

        speichern.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                speichern();
            }
        });

        speichernUnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                speichernUnter();
            }
        });

    }

    //Methoden:

    // Methode für die Abfrage vor dem Speichern
    public boolean abfrageSpeichern(boolean mitSchliessen) {
        if (panel.getIstGeaendert()) {
            int option = JOptionPane.showConfirmDialog(frame, "Möchten Sie die Grafik speichern? ", "Speichern", JOptionPane.YES_NO_CANCEL_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                speichern();
                if(mitSchliessen) {
                    frame.dispose();
                }
                return true;
            } else if (option== JOptionPane.NO_OPTION) {
                if (mitSchliessen) {
                    frame.dispose();
                }
                return true;
            } else {
                return false;
            }
        }else {
                if(mitSchliessen) {
                    frame.dispose();
                }
                return true;
            }
    }

    // Methode um ein neues Zeichenfeld zu erstellen
    public void neu() {
        if(panel.getIstGeaendert()) {
            int option = JOptionPane.showConfirmDialog(frame, "Möchten Sie ein neues Zeichenfeld erstellen?", "Neues Zeichenfeld", JOptionPane.YES_NO_OPTION);
            if (option != JOptionPane.YES_OPTION) {
                return;
            }
        }
        panel.setImage(new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB));
        Graphics2D g2d = panel.getImage().createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 800, 600);
        g2d.dispose();
        aktuelleDatei = null;
        panel.setIstGeaendert(false);
    }

    // Methode um die Grafik zu speichern
    private void speichern(){
        if(aktuelleDatei== null){
            speichernUnter();
        } else {
            try {
            ImageIO.write(panel.getImage(), "JPG", aktuelleDatei);
            JOptionPane.showMessageDialog(frame, "Grafik erfolgreich gespeichert!");
            panel.setIstGeaendert(false);
            }
            catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Fehler beim Speichern der Grafik: " + ex.getMessage());
        }}
    }

    // Methode für Speichern Unter der Grafik
    private void speichernUnter(){
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter= new FileNameExtensionFilter("JPG-Dateien (*.jpg)", "jpg");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        int option= fileChooser.showSaveDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {if(!file.getName().toLowerCase().endsWith(".jpg")) {
                file= new File(file.getAbsolutePath()+".jpg");
            }
                ImageIO.write(panel.getImage(), "JPG", file);
                JOptionPane.showMessageDialog(frame, "Grafik erfolgreich gespeichert!");
                aktuelleDatei = file;
                panel.setIstGeaendert(false);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Fehler beim Speichern der Grafik: " + ex.getMessage());
            }
        }

    }

    //Methode um eine Grafik zu öffnen
    private void oeffnen(){
        if (abfrageSpeichern(false)== true) {
        JFileChooser fileChooser= new JFileChooser();
        FileNameExtensionFilter filter= new FileNameExtensionFilter("JPG-Dateien (*.jpg)", "jpg");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        int result= fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                BufferedImage img= ImageIO.read(file);
                if(img==null) {
                    throw new IOException("Die Datei ist kein gültiges JPG-Bild");
                }
                panel.setImage(skalieren(img));
                panel.setIstGeaendert(false);
                }
            catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Fehler beim Öffnen: " + ex.getMessage());
            }
            }
        }}

    //Methode um die geöffnete Grafik auf das Zeichenfeld zu skalieren
    private BufferedImage skalieren( BufferedImage img) {
        int imgWeite= img.getWidth();
        int imgHoehe= img.getHeight();

        int panelWeite= panel.getWidth();
        int panelHoehe= panel.getHeight();

        double skalierungX= (double) panelWeite/imgWeite;
        double skalierungY= (double) panelHoehe/imgHoehe;
        double skalierung= Math.min(skalierungX, skalierungY);

        int neueWeite= (int) (imgWeite*skalierung);
        int neueHoehe= (int) (imgHoehe* skalierung);

        Image temp= img.getScaledInstance(neueWeite, neueHoehe, Image.SCALE_SMOOTH);

        BufferedImage skaliert= new BufferedImage(neueWeite, neueHoehe, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = skaliert.createGraphics();
        g2d.drawImage(temp, 0, 0, null);
        g2d.dispose();

        return skaliert;
    }
    }





