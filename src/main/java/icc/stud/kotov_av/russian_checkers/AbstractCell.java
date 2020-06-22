package icc.stud.kotov_av.russian_checkers;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class AbstractCell extends JPanel {

	private static final long serialVersionUID = 7346825849810135800L;
	
	protected Color color;

	public AbstractCell( Color color) {
		this.color = color;

		setDefaultBorder();
	}

	public void setDefaultBorder() {
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
}
