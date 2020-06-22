package icc.stud.kotov_av.russian_checkers.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import icc.stud.kotov_av.russian_checkers.Main;
import icc.stud.kotov_av.russian_checkers.Player;
import icc.stud.kotov_av.russian_checkers.ScoreData;

public class ShowScoreAction extends AbstractAction {
	private static final long serialVersionUID = 2018138344556352047L;
	
	private Main main;

	public ShowScoreAction(Main main) {
		super( "Показать счет" );
		this.main = main;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		System.out.println( "Score  " + Player.ONE.name() + " " + Player.TWO.name() );
		System.out.println( "       " + Player.ONE.getFigureCount() +" : " + Player.TWO.getFigureCount() );
		
		System.out.println();
		for (ScoreData data : main.getScoreData()) {
			System.out.println( data );
		}
	}
}
