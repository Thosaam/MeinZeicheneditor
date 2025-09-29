import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

public class Toolbar extends JToolBar {

    // Toolbar/ Werkzeugleiste vorbereiten: Buttons, Icons, Listener hinzufügen
    public Toolbar(Zeichenfeld panel) {

        // Komponenten für Toolbar erzeugen
        JToggleButton ellipseButton = new JToggleButton();
        JToggleButton linieButton = new JToggleButton();
        JToggleButton rechteckButton = new JToggleButton();
        JToggleButton stiftButton = new JToggleButton();
        JToggleButton radierButton = new JToggleButton();

        JToggleButton schwarzButton = new JToggleButton();
        JToggleButton rotButton = new JToggleButton();
        JToggleButton gruenButton = new JToggleButton();

        JSlider strichstaerkeSchieber = new JSlider(1, 20, 5);
        JLabel wertLabel = new JLabel(strichstaerkeSchieber.getValue() + " px");

        InputMap strichstaerkeSignal = strichstaerkeSchieber.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap strichstaerkeAktion = strichstaerkeSchieber.getActionMap();

        //Button- Gruppen erzeugen
        ButtonGroup toolGruppe = new ButtonGroup();
        toolGruppe.add(ellipseButton);
        toolGruppe.add(linieButton);
        toolGruppe.add(rechteckButton);
        toolGruppe.add(stiftButton);
        toolGruppe.add(radierButton);

        ButtonGroup farbGruppe = new ButtonGroup();
        farbGruppe.add(schwarzButton);
        farbGruppe.add(rotButton);
        farbGruppe.add(gruenButton);

        // Hinzufügen der einzelnen Komponenten in die Werkzeugleiste
        add(ellipseButton);
        add(linieButton);
        add(rechteckButton);
        addSeparator();
        add(stiftButton);
        stiftButton.setSelected(true);
        add(radierButton);
        addSeparator();
        add(schwarzButton);
        schwarzButton.setSelected(true);
        add(rotButton);
        add(gruenButton);
        addSeparator();
        add(strichstaerkeSchieber);
        add(wertLabel);

        //Icons  + Bedienbarkeit über die Tastaur + ToolTips
        ImageIcon iconEllipse = new ImageIcon("Icons/ellipse.png");
        ellipseButton.setIcon(iconEllipse);
        ellipseButton.setMnemonic('E');
        ellipseButton.setToolTipText("Ellipse zeichen (Alt + E)");

        ImageIcon iconLinie = new ImageIcon("Icons/linie.png");
        linieButton.setIcon(iconLinie);
        linieButton.setMnemonic('L');
        linieButton.setToolTipText("Linie zeichnen (Alt + L)");

        ImageIcon iconRechteck = new ImageIcon("Icons/rechteck.png");
        rechteckButton.setIcon(iconRechteck);
        rechteckButton.setMnemonic('R');
        rechteckButton.setToolTipText("Rechteck zeichnen (ALT + R)");

        ImageIcon iconSchwarz = new ImageIcon("Icons/schwarz.png");
        schwarzButton.setIcon(iconSchwarz);
        schwarzButton.setMnemonic('B');
        schwarzButton.setToolTipText("Farbe schwarz (ALT + B)");

        ImageIcon iconRot = new ImageIcon("Icons/rot.png");
        rotButton.setIcon(iconRot);
        rotButton.setMnemonic('N');
        rotButton.setToolTipText("Farbe Rot (ALT + N)");

        ImageIcon iconGruen = new ImageIcon("Icons/gruen.png");
        gruenButton.setIcon(iconGruen);
        gruenButton.setMnemonic('M');
        gruenButton.setToolTipText("Farbe Grün (ALT + M)");

        ImageIcon iconStift = new ImageIcon("Icons/stift.png");
        stiftButton.setIcon(iconStift);
        stiftButton.setMnemonic('F');
        stiftButton.setToolTipText("Stift Freihand zeichnen (ALT + F)");

        ImageIcon iconRadier = new ImageIcon("Icons/radiergummi.png");
        radierButton.setIcon(iconRadier);
        radierButton.setMnemonic('G');
        radierButton.setToolTipText("Radierer (ALT + G)");

        strichstaerkeSchieber.setToolTipText("Strichstärke einstellen (Alt + +/-)");
        strichstaerkeSchieber.setMaximumSize(new Dimension(90, 40));
        strichstaerkeSchieber.setBorder(new TitledBorder("Strichstärke "));

        // Strichstärke über die Tastatur bedienbar machen
        strichstaerkeAktion.put("dicker", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int wert = strichstaerkeSchieber.getValue();
                int neuerWert = Math.min(strichstaerkeSchieber.getMaximum(), wert + 1);
                strichstaerkeSchieber.setValue(neuerWert);
                panel.setStrichstaerke(neuerWert);
                wertLabel.setText(neuerWert+ " px");
            }
        });

        strichstaerkeSignal.put(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, InputEvent.ALT_DOWN_MASK), "duenner");
        strichstaerkeSignal.put(KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, InputEvent.ALT_DOWN_MASK), "duenner");

        strichstaerkeAktion.put("duenner", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int wert = strichstaerkeSchieber.getValue();
                int neuerWert = Math.max(strichstaerkeSchieber.getMinimum(), wert - 1);
                strichstaerkeSchieber.setValue(neuerWert);
                panel.setStrichstaerke(neuerWert);
                wertLabel.setText(neuerWert+ " px");
            }
        });


        // Action + Change Listener der Toolbar / Werkezugleiste
        linieButton.addActionListener(e -> {
            panel.setAktuellesTool(Zeichenfeld.Tool.LINIE);
            panel.setZeichnenCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        });
        ellipseButton.addActionListener(e -> {
            panel.setAktuellesTool(Zeichenfeld.Tool.ELLIPSE);
            panel.setZeichnenCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        });
        rechteckButton.addActionListener(e -> {
            panel.setAktuellesTool(Zeichenfeld.Tool.RECHTECK);
            panel.setZeichnenCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        });

        stiftButton.addActionListener(e -> {
            panel.setAktuellesTool(Zeichenfeld.Tool.STIFT);
            panel.setZeichnenCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        });
        radierButton.addActionListener(e -> {
            panel.setAktuellesTool(Zeichenfeld.Tool.RADIERGUMMI);
            panel.setZeichnenCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        });

        schwarzButton.addActionListener(e -> panel.setAktuelleFarbe(Color.BLACK));
        rotButton.addActionListener(e -> panel.setAktuelleFarbe(Color.RED));
        gruenButton.addActionListener(e -> panel.setAktuelleFarbe(Color.GREEN));

        strichstaerkeSchieber.addChangeListener(e -> {
            wertLabel.setText(strichstaerkeSchieber.getValue() + " px");
        });
        strichstaerkeSchieber.addChangeListener(e -> {
            panel.setStrichstaerke(strichstaerkeSchieber.getValue());
        });

        strichstaerkeSignal.put(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, InputEvent.ALT_DOWN_MASK), "dicker");
        strichstaerkeSignal.put(KeyStroke.getKeyStroke(KeyEvent.VK_ADD, InputEvent.ALT_DOWN_MASK), "dicker");
    }
}
