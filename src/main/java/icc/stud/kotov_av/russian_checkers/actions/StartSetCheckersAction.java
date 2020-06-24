package icc.stud.kotov_av.russian_checkers.actions;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;

import icc.stud.kotov_av.russian_checkers.CheckerCell;
import icc.stud.kotov_av.russian_checkers.GameField;
import icc.stud.kotov_av.russian_checkers.Main;

public class StartSetCheckersAction extends AbstractAction {
	private static final long serialVersionUID = 2833518393739303604L;
	
	private Main main;

	public StartSetCheckersAction(Main main) {
		super( "Установить шашки" );
		this.main = main;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		main.clearGameField();
		
		GameField gameField = main.getGameField();
		gameField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				GameField gameField = (GameField) evt.getComponent();

				CheckerCell cell = gameField.getCheckerCell(evt.getX(), evt.getY());
				if ( cell!= null ) {
					if( evt.getButton() == MouseEvent.BUTTON1 ) {
						cell.setChecker(true);
					}
					else if( evt.getButton() == MouseEvent.BUTTON2 ) {
						cell.setQueen(true);
					}
					cell.repaint();
				}
			}
		});
	}
}
