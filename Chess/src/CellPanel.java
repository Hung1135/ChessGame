import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CellPanel extends JPanel {
    private final Color blue;
    private final Color red;
    private final Color green;
    public boolean isValidMove;
    public int x;
    public int y;
    public JLabel imageLable;
    public ChessPiece currnetChessPiece;
    public PieceColor originColor;

    public CellPanel(boolean isWhite, final int x, final int y) {
        this.blue = Color.BLUE;
        this.red = Color.RED;
        this.green = Color.GREEN;
        this.x = x;
        this.y = y;
        this.isValidMove = false;
        this.originColor = isWhite ? PieceColor.WHITE : PieceColor.BLACK;
        this.setBackground(isWhite ? Color.WHITE : Color.GRAY);
        this.imageLable = new JLabel();
        this.imageLable.setVerticalAlignment(0);
        this.imageLable.setHorizontalAlignment(0);
        this.add(this.imageLable);
        this.addMouseListener(new MouseAdapter() {
            {
                Objects.requireNonNull(CellPanel.this);
            }

            public void mousePressed(MouseEvent e) {
                GameFrame.Instance.centerpanel.onclickCellPanel(x, y);
            }
        });
    }

    public void addImage(ChessPiece piece) {
        this.currnetChessPiece = piece;
        BufferedImage pieceImage = this.getBufferedImageFromFile(piece);
        Image image = pieceImage.getScaledInstance(60, 60, 4);
        this.imageLable.setIcon(new ImageIcon(image));
        this.imageLable.setVisible(true);
    }

    public void removePiece() {
        this.currnetChessPiece = null;
        this.imageLable.setVisible(false);
    }

    public void select() {
        this.setBackground(this.blue);
    }

    public void deselect() {
        this.setBackground(this.originColor == PieceColor.WHITE ? Color.WHITE : Color.GRAY);
        this.isValidMove = false;
    }

    public BufferedImage getBufferedImageFromFile(ChessPiece piece) {
        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
        String fileStr = String.valueOf(path) + "/piece/";
        if (piece.color == PieceColor.WHITE) {
            fileStr = fileStr + "W_";
        } else {
            fileStr = fileStr + "B_";
        }

        fileStr = fileStr + piece.type.toString() + ".png";
        File file = new File(fileStr);

        try {
            BufferedImage bufferimage = ImageIO.read(file);
            return bufferimage;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setColor(boolean isMove) {
        this.isValidMove = true;
        if (isMove) {
            this.setBackground(this.blue);
        } else {
            this.setBackground(this.red);
        }

    }
}
