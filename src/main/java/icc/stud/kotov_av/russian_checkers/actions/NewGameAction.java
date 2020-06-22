package icc.stud.kotov_av.russian_checkers.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import icc.stud.kotov_av.russian_checkers.Main;

public class NewGameAction extends AbstractAction {

	private static final long serialVersionUID = -4875121395271742802L;
	
	private Main main;

	public NewGameAction(Main main) {
		super( "Новая игра" );
		
		this.main = main;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		main.createNewGame();
	}
}
