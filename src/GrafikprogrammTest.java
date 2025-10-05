import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.lang.reflect.Method;

class GrafikprogrammTest {
    @Test
    void testInitialStatus() {
        Zeichenfeld feld = new Zeichenfeld(800, 600);
        assertFalse(feld.getIstGeaendert());
    }
    @Test
    void testSetzeStatus() {
        Zeichenfeld feld = new Zeichenfeld(800, 600);
        feld.setIstGeaendert(true);
        assertTrue(feld.getIstGeaendert());
    }
    @Test
    void testSkalieren() throws Exception {
        // Vorbedingungen
        Zeichenfeld panel = new Zeichenfeld(Grafikprogramm.FELDWEITE, Grafikprogramm.FELDHOEHE);
        panel.setSize(Grafikprogramm.FELDWEITE, Grafikprogramm.FELDHOEHE);
        Menubar menubar = new Menubar(null, panel);

        // Beispielbild
        BufferedImage img = new BufferedImage(600, 1200, BufferedImage.TYPE_INT_RGB);

        Method skalieren = Menubar.class.getDeclaredMethod("skalieren", BufferedImage.class);
        skalieren.setAccessible(true);

        BufferedImage result = (BufferedImage) skalieren.invoke(menubar, img);

        // Test: Das Bild passt ins Panel
        assertTrue(result.getWidth() <= panel.getWidth());
        assertTrue(result.getHeight() <= panel.getHeight());

        // Test: Seitenverhältnis bleibt (nahezu) gleich
        double originalRatio = (double) img.getWidth() / img.getHeight();
        double resultRatio = (double) result.getWidth() / result.getHeight();

        assertEquals(originalRatio, resultRatio, 0.01, "Seitenverhältnis verändert sich zu stark!");

        // Test: Skalierung erfolgte tatsächlich (nicht 1:1)
        assertTrue(result.getWidth() != img.getWidth() || result.getHeight() != img.getHeight());
    }

}