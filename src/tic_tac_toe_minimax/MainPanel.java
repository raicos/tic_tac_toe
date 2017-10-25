package tic_tac_toe_minimax;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainPanel extends JPanel implements ActionListener {

	private static final int BLANK = 0;
	private static final int MARU = 1;
	private static final int BATU = -1;

	private static final int START = 0;
	private static final int MARUWIN = 1;
	private static final int BATUWIN = 2;
	private static final int DRAW = 3;

	private int[][] board = new int[3][3];
	private boolean myTurn;
	private int gameState;

	private List<JButton> buttonList = new ArrayList<>();

	private Enemy enemy;

	public MainPanel() {
		setPreferredSize(new Dimension(300, 300));
		setLayout(new GridLayout(3, 3));
		createButton();

		initBoard(true);
		enemy = new Enemy();

		gameState = START;
	}

	private void createButton() {
		for (int i = 0; i < 9; i++) {
			JButton button = new JButton();
			button.setName(String.valueOf(i));
			button.addActionListener(this);
			add(button);
			buttonList.add(button);
		}

	}

	private void initBoard(boolean myTurn) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = BLANK;
			}
		}
		updateBoard();
		this.myTurn = myTurn;
	}

	void updateBoard() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				JButton button = buttonList.get(i * 3 + j);
				switch (board[i][j]) {
				case BLANK:
					button.setEnabled(true);
					button.setText("");
					break;
				case MARU:
					button.setEnabled(false);
					button.setText("o");
					break;
				case BATU:
					button.setEnabled(false);
					button.setText("x");
					break;
				}
			}
		}
	}

	private void putValue(int x, int y) {
		int whichTurn;

		if (myTurn) {
			whichTurn = MARU;
		} else {
			whichTurn = BATU;
		}
		board[x][y] = whichTurn;
		myTurn = !myTurn;
	}

	private void checkEnd() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] != BLANK) {
					gameState = DRAW;
				} else {
					gameState = START;
					break;
				}
			}
		}
		for (int i = 0; i < 3; i++) {
			if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
				if (board[i][0] == MARU) {
					gameState = MARUWIN;
				} else if (board[i][0] == BATU) {
					gameState = BATUWIN;
				}
			}
			if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
				if (board[0][i] == MARU) {
					gameState = MARUWIN;
				} else if (board[0][i] == BATU) {
					gameState = BATUWIN;
				}
			}
		}
		if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
			if (board[1][1] == MARU) {
				gameState = MARUWIN;
			} else if (board[1][1] == BATU) {
				gameState = BATUWIN;
			}
		}
		if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
			if (board[1][1] == MARU) {
				gameState = MARUWIN;
			} else if (board[1][1] == BATU) {
				gameState = BATUWIN;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (gameState) {
		case START:
			JButton button = (JButton) e.getSource();
			int value = Integer.parseInt(button.getName());
			int x = value / 3;
			int y = value % 3;
			putValue(x, y);
			updateBoard();
			checkEnd();
			if (gameState == START) {
				break;
			}
		case MARUWIN:
		case BATUWIN:
		case DRAW:
			String str = gameState == MARUWIN ? "MARU_WIN" : gameState == BATUWIN ? "BATU_WIN" : "DRAW";
			JOptionPane.showMessageDialog(this, str, "勝敗", JOptionPane.PLAIN_MESSAGE);
			gameState = START;
			initBoard(true);
			updateBoard();
			break;
		}
	}
}
