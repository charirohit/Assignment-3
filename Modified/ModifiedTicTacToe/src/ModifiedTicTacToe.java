import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ModifiedTicTacToe {
    int boardWidth=600;
    int boardHeight=650;

    JFrame frame= new JFrame("Modified Tic-Tac-Toe");

    JLabel textLabel=new JLabel();
    JPanel textPanel=new JPanel();
    JPanel boardPanel=new JPanel();

    JButton[][] board;
    String playerX,playerO,currentPlayer;

    boolean gameOver = false;

    int turns =0;
    int gridSize;

    public ModifiedTicTacToe(){

        playerX=JOptionPane.showInputDialog(frame,"Enter Name for player X : ","PLayer X",JOptionPane.PLAIN_MESSAGE);
        playerO=JOptionPane.showInputDialog(frame,"Enter Name for player O : ","PLayer O",JOptionPane.PLAIN_MESSAGE);
        gridSize=Integer.parseInt(JOptionPane.showInputDialog(frame,"Enter grid size (e.g., 3 for 3x3) : ","Grid Size",JOptionPane.PLAIN_MESSAGE));

        if(gridSize<3 || gridSize>10)
        {
            JOptionPane.showMessageDialog(frame,"Grid size must be between 3 and 10","Invalid Grid Size",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        currentPlayer = playerX;

        board = new JButton[gridSize][gridSize];

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.BLACK);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 30));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText(playerX + "'s turn");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel, BorderLayout.CENTER);

        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(gridSize, gridSize));
        boardPanel.setBackground(Color.BLACK);
        frame.add(boardPanel);
        
        for(int r=0;r<gridSize;r++){
            for(int c=0;c<gridSize;c++){
                JButton tile=new JButton();
                
                board[r][c]=tile;

                boardPanel.add(tile);

                tile.setBackground(Color.DARK_GRAY);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120 / gridSize));
                tile.setFocusPainted(false);
                tile.setBorderPainted(true);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e){
                        if(gameOver) return;
                        JButton tile=(JButton) e.getSource();
                        if(tile.getText()==""){
                            tile.setText(currentPlayer.equals(playerX) ? "X" : "O");
                            turns++;
                            checkWinner();
                            if(!gameOver){
                                currentPlayer= currentPlayer.equals(playerX)? playerO : playerX;
                                textLabel.setText(currentPlayer + "'s turn");
                            }
                        }
                    }
                });

            }
        }
    }

    void checkWinner(){
        //check rows
        
        for(int r=0;r<gridSize;r++){
            if(board[r][0].getText().isEmpty()) continue;
            boolean win=true;
            for(int c=1;c<gridSize;c++){
                if(!board[r][c].getText().equals(board[r][0].getText())){
                    win=false;
                    break;
                }
            }
            if(win){
                for(int c=0;c<gridSize;c++){
                    setWinner(board[r][c]);
                }
                gameOver=true;
                return;
            }
        }

        //check columns
        for(int c=0;c<gridSize;c++){
            if(board[0][c].getText().isEmpty()) continue;
            boolean win=true;
            for(int r=1;r<gridSize;r++){
                if(!board[r][c].getText().equals(board[0][c].getText())){
                    win=false;
                    break;
                }
            }
            if(win){
                for(int r=0;r<gridSize;r++){
                    setWinner(board[r][c]);
                }
                gameOver=true;
                return;
            }
        }

        //check Diagonal
        boolean win=true;
        for(int i=1;i<gridSize;i++){
            if(!board[i][i].getText().equals(board[0][0].getText()))
            {
                win=false;
                break;
            }
        }
        if(win && !board[0][0].getText().isEmpty()){
            for(int i=0;i<gridSize;i++){
                setWinner(board[i][i]);
            }
            gameOver=true;
            return;
        }


        //Anti Diagonal
        win=true;
        for(int i=1;i<gridSize;i++){
            int j=i+1;
            if(!board[i][gridSize-j].getText().equals(board[0][gridSize-1].getText()))
            {
                win=false;
                break;
            }
        }
        if(win && !board[0][gridSize-1].getText().isEmpty()){
            for(int i=0;i<gridSize;i++){
                int j=i+1;
                setWinner(board[i][gridSize-j]);
            }
            gameOver=true;
            return;
        }

        //checking for tie
        if(turns == gridSize*gridSize){
            for(int r=0;r<gridSize;r++){
                for(int c=0;c<gridSize;c++){
                    setTie(board[r][c]);
                }
            }
            gameOver=true;
        }

        
    }

    void setWinner(JButton tile){
        tile.setForeground(Color.GREEN);
        tile.setBackground(Color.gray);
        textLabel.setText(currentPlayer + " Has Won the game!");
    }

    void setTie(JButton tile){
        tile.setForeground(Color.orange);
        tile.setBackground(Color.BLACK);
        textLabel.setText("It's a Tie!");
    }

    
    
}
