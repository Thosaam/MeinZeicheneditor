import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.LineBorder;

public class Grafikprogramm extends JFrame{

    public static final int FELDWEITE= 800;
    public static final int FELDHOEHE= 600;

    private Zeichenfeld zeichenfeld;
    private Toolbar toolbar;
    private Menubar menubar;

    public Grafikprogramm(){
        super("MyPaint");
        setSize(FELDWEITE,FELDHOEHE);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        zeichenfeld= new Zeichenfeld(FELDWEITE,FELDHOEHE);
        zeichenfeld.setPreferredSize(new Dimension(FELDWEITE, FELDHOEHE));
        toolbar= new Toolbar(zeichenfeld);
        menubar= new Menubar(this,zeichenfeld);
        menubar.setBackground(Color.WHITE);
        menubar.setOpaque(true);
        toolbar.setBorder(new LineBorder(Color.BLACK, 1));
        toolbar.setBackground(Color.WHITE);
        toolbar.setOpaque(true);

        add(toolbar, BorderLayout.NORTH);
        add(zeichenfeld, BorderLayout.CENTER);

        setJMenuBar(menubar);
        pack();
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                menubar.abfrageSpeichern();
            }
        });

    }


}
