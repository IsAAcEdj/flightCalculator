import javax.swing.*;

public class Window {
    public JFrame frame = new JFrame();
    public JLabel iconLabel = new JLabel();


    public void setFrame(JFrame frame) {
        this.frame = frame;
        frame.setBounds(100,100,800,600);
        frame.setVisible(true);

    }
}
