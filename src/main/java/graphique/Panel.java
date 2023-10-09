package graphique;

import physique.Objet;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Panel {

    private BufferedImage image;
    private Objet objet;

    public Panel(String path) throws IOException {
        this.image = ImageIO.read(Objects.requireNonNull(getClass().getResource(path)));
        this.objet = null;
    }

}
