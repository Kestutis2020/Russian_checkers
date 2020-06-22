package icc.stud.kotov_av.russian_checkers;
import java.awt.Color;

public enum Player {
	ONE( Color.RED, Direction.DOWN_LEFT, Direction.DOWN_RIGTH ),
	TWO( Color.BLUE, Direction.UP_LEFT, Direction.UP_RIGTH );

	private static final int DEFAULT_FIGURES = 12;
	
	Color color;
	Direction[] directions;
	int figureCount = DEFAULT_FIGURES;

	private Player( Color color, Direction... directions ) {
		this.color = color;
		this.directions = directions;
	}

	public Color getColor() {
		return color;
	}

	public Direction[] getDirections() {
		return directions;
	}

	public void decreaseFigureCount() {
		this.figureCount--;
	}
	
	public int getFigureCount() {
		return figureCount;
	}

	public void resetFigureCount() {
		this.figureCount = DEFAULT_FIGURES;
	}

	public static Player invertPlayer(Player actual) {
	    if( actual == Player.ONE ) {
		return Player.TWO;
	    }
	    else if( actual == Player.TWO ) {
		return Player.ONE;
	    }
	    return null;
	}
}
