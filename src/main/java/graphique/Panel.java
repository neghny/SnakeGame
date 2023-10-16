package graphique;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import physique.Objet;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Objects;


public class Panel {

    private BufferedImage image;
    private Objet objet;

    public Panel() throws IOException {
        Logger logger = LogManager.getLogger(this.getClass());
        logger.debug("Construct a MyJavaPanel");
        String path = objet.getPathImage();
        if (logger.isDebugEnabled()) {
            String message = MessageFormat.format("Loading image at path {0}", path);
            logger.debug(message);

        }
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource(path)));
        } catch (Exception ex) {
            String message = MessageFormat.format("Error: Cannot load image at path: {0}", path);
            logger.error(message, ex);
        }
        this.objet = null;
    }

}
