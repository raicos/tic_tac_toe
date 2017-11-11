package tic_tac_toe_minimax;

public class Enemy {
	private static final int ROOT = 0;
	private MainPanel panel;

	public Enemy(MainPanel panel) {
		this.panel = panel;
	}

	public void compute() {
		int value = minMax(true, ROOT);
		int x = value / 3;
		int y = value % 3;

		panel.putValue(x, y);
		panel.updateBoard();
		panel.checkEnd();
		if (panel.isFinished()) {
			panel.finishProcess();
		}
	}

	private int minMax(boolean flag, int depth) {
		int value;
		int childValue;
		int bestX = 0;
		int bestY = 0;

		if (panel.isFinished()) {
			return eval(depth);
		}
		if (flag) {
			value = Integer.MIN_VALUE;
		} else {
			value = Integer.MAX_VALUE;
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (panel.canPutValue(i, j)) {
					Undo undo = new Undo(i, j);
					panel.putValue(i, j);
					childValue = minMax(!flag, depth + 1);
					if (flag) {
						if (childValue > value) {
							value = childValue;
							bestX = i;
							bestY = j;
						}
					} else {
						if (childValue < value) {
							value = childValue;
							bestX = i;
							bestY = j;
						}
					}
					panel.undoBoard(undo);
				}
			}
		}

		if (depth == 0) {
			return bestX * 3 + bestY;
		} else {
			return value;
		}
	}

	private int eval(int depth) {
		if (panel.gameState == MainPanel.MARUWIN) {
			panel.gameState = MainPanel.START;
			return depth - 10;
		} else if (panel.gameState == MainPanel.BATUWIN) {
			panel.gameState = MainPanel.START;
			return 10 - depth;
		} else {
			panel.gameState = MainPanel.START;
			return 0;
		}
	}
}
