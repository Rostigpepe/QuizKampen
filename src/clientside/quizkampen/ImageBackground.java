package clientside.quizkampen;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageBackground extends JPanel {

    private Image backgroundImage;


    public ImageBackground(String fileName) throws IOException {
        backgroundImage = ImageIO.read(new File(fileName));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image.
        g.drawImage(backgroundImage, 0, 0, this);
    }
}