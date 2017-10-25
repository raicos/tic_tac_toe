package tic_tac_toe_minimax;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

public class TicTacToe extends JFrame {
	public TicTacToe() {
		setTitle("Tic_Tac_Toe");

		Container contentPane = getContentPane();

		MainPanel mainPanel = new MainPanel();
		contentPane.add(mainPanel, BorderLayout.CENTER);

		pack();
	}
}
