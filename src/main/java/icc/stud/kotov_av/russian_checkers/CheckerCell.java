package icc.stud.kotov_av.russian_checkers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class CheckerCell extends AbstractCell {

	private static final long serialVersionUID = 7048655168308819781L;

	/*
	 * Игрок
	 */
	private Player player;
	/*
	 * Признак присутствия шашки
	 */
	private boolean checker;
	/*
	 * Признак выделенности
	 */
	private boolean selected;
	/*
	 * "Дамка"
	 */
	private boolean queen;
	/*
	 * Последяя строка (для установки признака "дамка")
	 */
	private Point lastRow;
	/*
	 * Координата X
	 */
	int xIndex;
	/*
	 * Координата Y
	 */
	int yIndex;
	
	public CheckerCell( int x, int y, Player player, boolean checker, Point lastRow ) {
		super( player != null ? player.getColor() : Color.WHITE );
		
		this.xIndex = x;
		this.yIndex = y;
		this.player = player;
		this.checker = checker;
		this.lastRow = lastRow;

		setBackground();
	}

	public boolean isChecker() {
		return checker;
	}

	public void setChecker(boolean checker) {
		this.checker = checker;
	}
	
	protected void setBackground() {
		setBackground( Color.GRAY );
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setQueen(boolean queen) {
		this.queen = queen;
	}

	public boolean isQueen() {
		return queen;
	}
	
	public int getXIndex() {
	    return xIndex;
	}

	public int getYIndex() {
	    return yIndex;
	}

	public boolean isLastRow() {
	    return lastRow != null && lastRow.getY() == getYIndex() && -1 == lastRow.getX();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	
		Rectangle bounds = g.getClipBounds();

		int width = (int) (bounds.getWidth()*0.8);
		int height = (int) (bounds.getHeight()*0.8);
		
		if( isSelected() ) {
			g.fillRect(0, 0, (int)bounds.getWidth(), (int)bounds.getHeight());
		}
		
		if( checker ) {
		    g.setColor( player.getColor() );
		    g.fillArc( (int)(bounds.getWidth()*0.1), (int)(bounds.getHeight()*0.1), width, height, 0, 360 );
		}

		if( isQueen() ) {
			g.setColor( Color.BLACK );
			g.fillArc( (int)(bounds.getWidth()*0.1), (int)(bounds.getHeight()*0.1), width, height, 0, 360 );
			g.setColor( player.getColor() );
			g.fillArc( (int)(bounds.getWidth()*0.2), (int)(bounds.getHeight()*0.2), (int)(width*0.75), (int)(height*0.75), 0, 360 );
		}
	}

	@Override
	public String toString() {
		return "CheckerCell [name= " + getName() + ", player=" + player + ", checker=" + checker + ", selected=" + selected + ", queen=" + queen
				+ ", xIndex=" + xIndex + ", yIndex=" + yIndex  + ", lastRow=" + lastRow + "]";
	}
}
