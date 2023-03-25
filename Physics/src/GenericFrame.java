package Physics;

import javax.swing.*;

public class GenericFrame {
    public static void newFrame(String title, JPanel panel) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
