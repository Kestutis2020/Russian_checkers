package icc.stud.kotov_av.russian_checkers.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import icc.stud.kotov_av.russian_checkers.Main;

public class StopSetCheckersAction extends AbstractAction {
	private static final long serialVersionUID = 2833518393739303604L;
	
	private Main main;

	public StopSetCheckersAction(Main main) {
		super( "Закончить установику шашек" );
		this.main = main;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		main.stopTestSetup();
	}
}
