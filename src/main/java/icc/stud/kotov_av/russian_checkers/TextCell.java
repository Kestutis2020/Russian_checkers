package icc.stud.kotov_av.russian_checkers;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class TextCell extends AbstractCell {

	private static final long serialVersionUID = -869551270726949323L;

	public TextCell( String text ) {
		super( Color.black );
		
		setName(text);
		
		JLabel label = new JLabel( text );
		label.setFont( label.getFont().deriveFont(Font.BOLD, 30f) );
		add( label );
	}
}
