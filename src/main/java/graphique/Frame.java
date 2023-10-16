package graphique;
import javax.swing.*;

public class Frame extends JFrame{

    String title;
    int width;
    int height;
    Boolean isVisible;


    public Frame(int width, int height, String title, Boolean isVisible) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.isVisible = isVisible;
    }

    public void display(){
        setTitle(this.title);
        setSize(this.width, this.height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(this.isVisible);
    }
}
