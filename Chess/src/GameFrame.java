import javax.swing.JFrame;

public class GameFrame extends JFrame {
    public static GameFrame Instance;
    public TopPanel toppanel;
    public CenterPanel centerpanel;

    public GameFrame() {
        Instance = this;
        this.setTitle("Chess");
        this.setSize(600, 700);
        this.setDefaultCloseOperation(3);
        this.setLocation(200, 100);
        this.setVisible(true);
        this.toppanel = new TopPanel();
        this.centerpanel = new CenterPanel();
        this.getContentPane().add(this.toppanel, "North");
        this.getContentPane().add(this.centerpanel, "Center");
        this.revalidate();
        this.repaint();
    }
}
