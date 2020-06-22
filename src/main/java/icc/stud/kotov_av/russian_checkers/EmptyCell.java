package icc.stud.kotov_av.russian_checkers;

import java.awt.Color;

public class EmptyCell extends AbstractCell {

	private static final long serialVersionUID = 8330210542134464333L;

	public EmptyCell() {
		super( Color.white );
	
		setName( "EmptyCell" );
	}
}
