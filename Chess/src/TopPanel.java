
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JPanel;

public class TopPanel extends JPanel {
    public TopPanel() {
        JButton button = new JButton();
        button.setText("Menu");
        button.setPreferredSize(new Dimension(100, 30));
        button.addActionListener(new ActionListener() {
            {
                Objects.requireNonNull(TopPanel.this);
            }

            public void actionPerformed(ActionEvent e) {
                System.out.println("menu");
            }
        });
        this.add(button);
    }
}
