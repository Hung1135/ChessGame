
import java.awt.GridLayout;
import javax.swing.JPanel;

public class CenterPanel extends JPanel {
    private CellPanel[][] boardCell = new CellPanel[8][8];
    private BoardState boardState;
    private CellPanel selectedCell;

    public CenterPanel() {
        this.boardState = BoardState.NO_SELECT;
        this.setLayout(new GridLayout(8, 8));
        boolean isWhite = true;

        for(int i = 0; i < 8; ++i) {
            for(int j = 0; j < 8; ++j) {
                CellPanel cellPanel = new CellPanel(isWhite, i, j);
                if (i == 1 || i == 6) {
                    cellPanel.addImage(new ChessPiece(i == 1 ? PieceColor.BLACK : PieceColor.WHITE, PieceType.PAWN));
                }

                if (i == 0 && (j == 0 || j == 7)) {
                    cellPanel.addImage(new ChessPiece(PieceColor.BLACK, PieceType.ROOK));
                }

                if (i == 7 && (j == 0 || j == 7)) {
                    cellPanel.addImage(new ChessPiece(PieceColor.WHITE, PieceType.ROOK));
                }

                if (i == 0 && (j == 1 || j == 6)) {
                    cellPanel.addImage(new ChessPiece(PieceColor.BLACK, PieceType.KNIGHT));
                }

                if (i == 7 && (j == 1 || j == 6)) {
                    cellPanel.addImage(new ChessPiece(PieceColor.WHITE, PieceType.KNIGHT));
                }

                if (i == 0 && (j == 2 || j == 5)) {
                    cellPanel.addImage(new ChessPiece(PieceColor.BLACK, PieceType.BISHOP));
                }

                if (i == 7 && (j == 2 || j == 5)) {
                    cellPanel.addImage(new ChessPiece(PieceColor.WHITE, PieceType.BISHOP));
                }

                if (i == 0 && j == 4) {
                    cellPanel.addImage(new ChessPiece(PieceColor.BLACK, PieceType.KING));
                }

                if (i == 7 && j == 4) {
                    cellPanel.addImage(new ChessPiece(PieceColor.WHITE, PieceType.KING));
                }

                if (i == 0 && j == 3) {
                    cellPanel.addImage(new ChessPiece(PieceColor.BLACK, PieceType.QUEEN));
                }

                if (i == 7 && j == 3) {
                    cellPanel.addImage(new ChessPiece(PieceColor.WHITE, PieceType.QUEEN));
                }

                this.add(cellPanel);
                this.boardCell[i][j] = cellPanel;
                isWhite = !isWhite;
            }

            isWhite = !isWhite;
        }

        this.selectedCell = null;
    }

    public void onclickCellPanel(int x, int y) {
        CellPanel clickedCellPannel = this.boardCell[x][y];
        clickedCellPannel.select();
        if (this.boardState == BoardState.NO_SELECT) {
            this.deSelectCellPanelAll();
            System.out.println(clickedCellPannel.currnetChessPiece);
            if (clickedCellPannel.currnetChessPiece != null) {
                switch (clickedCellPannel.currnetChessPiece.type) {
                    case PAWN:
                        this.PawnCheck(x, y);
                    case KING:
                    default:
                        this.selectedCell = clickedCellPannel;
                        this.boardState = BoardState.PIECE_SELECT;
                }
            }
        } else if (this.boardState == BoardState.PIECE_SELECT) {
            System.out.println(BoardState.PIECE_SELECT);
            if (this.boardCell[x][y].isValidMove) {
                clickedCellPannel.addImage(this.selectedCell.currnetChessPiece);
                this.selectedCell.removePiece();
                this.selectedCell = null;
                this.boardState = BoardState.NO_SELECT;
                this.deSelectCellPanelAll();
            } else {
                this.deSelectCellPanelAll();
            }
        }

    }

    public void deSelectCellPanelAll() {
        for(int i = 0; i < 8; ++i) {
            for(int j = 0; j < 8; ++j) {
                this.boardCell[i][j].deselect();
            }
        }

    }

    private void PawnCheck(int x, int y) {
        ChessPiece thisPiece = this.boardCell[x][y].currnetChessPiece;
        if (thisPiece.color == PieceColor.WHITE) {
            int maxStep = x == 6 ? 2 : 1;

            for(int i = x - 1; i >= x - maxStep && this.checkValidMove(i, y); --i) {
                ChessPiece chessPiece = this.boardCell[i][y].currnetChessPiece;
                if (chessPiece != null) {
                    break;
                }

                this.boardCell[i][y].setColor(true);
            }

            if (this.checkValidMove(x - 1, y - 1)) {
                CellPanel cellPanel = this.boardCell[x - 1][y - 1];
                if (cellPanel.currnetChessPiece != null && cellPanel.currnetChessPiece.color != thisPiece.color) {
                    cellPanel.setColor(false);
                }
            }

            if (this.checkValidMove(x - 1, y + 1)) {
                CellPanel cellPanel = this.boardCell[x - 1][y + 1];
                if (cellPanel.currnetChessPiece != null && cellPanel.currnetChessPiece.color != thisPiece.color) {
                    cellPanel.setColor(false);
                }
            }
        } else {
            int maxStep = x == 1 ? 2 : 1;

            for(int i = x + 1; i <= x + maxStep && this.checkValidMove(i, y); ++i) {
                ChessPiece chessPiece = this.boardCell[i][y].currnetChessPiece;
                if (chessPiece != null) {
                    break;
                }

                this.boardCell[i][y].setColor(true);
            }

            if (this.checkValidMove(x + 1, y - 1)) {
                CellPanel cellPanel = this.boardCell[x + 1][y - 1];
                if (cellPanel.currnetChessPiece != null && cellPanel.currnetChessPiece.color != thisPiece.color) {
                    cellPanel.setColor(false);
                }
            }

            if (this.checkValidMove(x + 1, y + 1)) {
                CellPanel cellPanel = this.boardCell[x + 1][y + 1];
                if (cellPanel.currnetChessPiece != null && cellPanel.currnetChessPiece.color != thisPiece.color) {
                    cellPanel.setColor(false);
                }
            }
        }

    }

    public boolean checkValidMove(int n) {
        return n >= 0 && n <= 7;
    }

    public boolean checkValidMove(int x, int y) {
        return this.checkValidMove(x) && this.checkValidMove(y);
    }
}
