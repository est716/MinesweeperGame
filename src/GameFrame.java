import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GameFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel displayScore, displayState, hit;
	private JButton play, exit;
	private JButton[][] box;
	private Panel chessPanel;
	private ChessBoard chessBoard;
	private BorderLayout mainBL;
	private boolean isFirst = true;
	private JComboBox<String> chooseLevel;
	private int[] initGameValue = new int[4];
	
	public GameFrame(){
		super("Bomb Game");
		mainBL = new BorderLayout();
		setLayout(mainBL);
		
		Panel panelNorth = new Panel(), panelSouth = new Panel(), panelWest = new Panel();
		panelNorth.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelSouth.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelWest.setLayout(new BorderLayout());
		
		chessPanel = new Panel();
		
		displayScore = new JLabel();
		displayScore.setText("Score:");
		panelNorth.add(displayScore);
		
		displayState = new JLabel();
		panelNorth.add(displayState);
		
		play = new JButton("Play");
		panelSouth.add(play);
		
		exit = new JButton("Exit");
		panelSouth.add(exit);
		
		// To display the UI that can select the difficulty of the game
		chooseLevel = new JComboBox<String>();
		chooseLevel.addItem("請選擇");
		chooseLevel.addItem("簡單");
		chooseLevel.addItem("普通");
		chooseLevel.addItem("困難");
		panelWest.add(chooseLevel, BorderLayout.NORTH);
		
		hit = new JLabel("hit:左鍵翻格子，右鍵標記");
		panelWest.add(hit, BorderLayout.SOUTH);
		
		add(panelWest, BorderLayout.WEST);
		add(panelNorth, BorderLayout.NORTH);
		add(panelSouth, BorderLayout.SOUTH);
		
		ButtonHandler buttonHandler = new ButtonHandler();
		play.addActionListener(buttonHandler);
		exit.addActionListener(buttonHandler);
		ItemHandler itemHandler = new ItemHandler();
		chooseLevel.addActionListener(itemHandler);

	}
	
	private void addChessPanel(int height, int width) {// add chess board
		chessPanel.removeAll(); // will remove panel of original
		chessPanel.setEnabled(true);
		chessPanel.setLayout(new GridLayout(height, width));
		addButton(height, width);
		add(chessPanel, BorderLayout.CENTER);
		revalidate();
	}
	
	private void addButton(int height, int width) {
		// create button then add to the chessPanel
		box = new JButton[height][width];
		for(int i = 0; i < height; ++i) {
			for(int j = 0; j < width; ++j) {
				box[i][j] = new JButton("");
				chessPanel.add(box[i][j]);
				box[i][j].addActionListener(new ButtonHandler());
				box[i][j].addMouseListener(new RightKeyHandler());
			}
		}
	}
	
	private void updateButton() { // update state of button
		Box[][] chessBox = chessBoard.getChessBoardBox();
		for(int i = 0; i < chessBox.length; ++i) {
			for(int j = 0; j < chessBox[i].length; ++j) {
				if(chessBox[i][j].getReversal() == true) {
					if(chessBox[i][j].getBomb() == true) {
						box[i][j].setText("💣");
					}else if(chessBox[i][j].getCount() > 0) {
						box[i][j].setText(String.valueOf(chessBox[i][j].getCount()));
					}
					box[i][j].setEnabled(false);
				}else {
					if(chessBox[i][j].getMarker() == true) {
						box[i][j].setText("\u2714");
					}else {
						box[i][j].setText("");
					}
				}
			}
		}
	}
	
	private void setScore() {// determine the score based on the button you select
		String str = "Score: ";
		String currentSco = String.valueOf(chessBoard.getScore());
		displayScore.setText(str + currentSco);
	}
	
	private class ItemHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == chooseLevel) {// Determine the game level according to user selection
				initGameValue[0] = chooseLevel.getSelectedIndex();
				switch(initGameValue[0]) {
					case -1:
					case 0:
						
						break;
					case 1:
						initGameValue[1] = initGameValue[2] = 10;
						initGameValue[3] = 8;
						break;
					case 2:
						initGameValue[1] = initGameValue[2] = 10;
						initGameValue[3] = 15;
						break;
					case 3:
						initGameValue[1] = initGameValue[2] = 20;
						initGameValue[3] = 30;
						break;
				}
				
			}
			
		}
		
	}
	
	private class RightKeyHandler implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getButton() == MouseEvent.BUTTON3) {// mark ˇ on the button when the mouse right button is clicked
				Box[][] chessBox = chessBoard.getChessBoardBox();
				if(chessBox != null) {
					for(int i = 0; i < box.length; ++i) {
						for(int j = 0; j < box[i].length; ++j) {
							if(e.getSource() == box[i][j]) {
								if(chessBox[i][j].getMarker()) {
									chessBox[i][j].setMarker(false);
								}else {
									chessBox[i][j].setMarker(true);
								}
							}
						}
					}
					updateButton();
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		
		
	}
	
	private class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == exit)
				System.exit(0);
			
			if(initGameValue[0] > 0) {
				if(e.getSource() == play) { //start game
					chessBoard = new ChessBoard(initGameValue[1], initGameValue[2], initGameValue[3]);
					addChessPanel(chessBoard.getHeight(), chessBoard.getWidth());
					isFirst = true;
					displayScore.setText("Score:");
					displayState.setText("");
					
				}else { // restart if play button is clicked
					for(int i = 0; i < box.length; ++i) {
						for(int j = 0; j < box[i].length; ++j) {
							if(e.getSource() == box[i][j]) {
								if(isFirst == true) {
									chessBoard.CreateBomb(i, j);
									chessBoard.countNeighborhoodBombNumberOfBox();
									chessBoard.reversalBox(i, j);
									updateButton();
									setScore();
									isFirst = false;
								}else {
									chessBoard.reversalBox(i, j);
									updateButton();
									setScore();
									int winOrLoss = chessBoard.determineWinOrLoss(i, j);
									if(winOrLoss == 1 || winOrLoss == 2) {
										updateButton();
										chessPanel.setEnabled(false);
										isFirst = true;
										if(winOrLoss == 2) {
											displayState.setText("You Win!!");
										}else {
											displayState.setText("You Loss!!");
										}
									}
								}
							}
						}
					}
				}
			}else {
				displayScore.setText("You not choose level!!");
			}
		}
		
	}
	
}

