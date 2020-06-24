package icc.stud.kotov_av.russian_checkers;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.ToolTipManager;

public class GameField extends JPanel {

	private static final long serialVersionUID = -931080932178052947L;

	private static String[] ALPHABET = { "A", "B", "C", "D", "E", "F", "G", "H" };
	private static String[] NUMBERS  = { "1", "2", "3", "4", "5", "6", "7", "8" };

	/*
	 * Field with actual player	
	 */
	private CheckerCell playerIndicator;

	/*
	 * Check selected for move
	 */
	private CheckerCell selectedToMoveCell;

	boolean continueMoving = false;
	
	public GameField() {
		setLayout(new GridLayout(10, 10));

		playerIndicator = new CheckerCell( 0, 0, Player.ONE, true, new Point(0, 0) ) {
			private static final long serialVersionUID = -9131931634774314813L;

			public void setSelected(boolean selected) {}
			protected void setBackground() {}
			
			@Override
			public String getToolTipText() {
				return super.getToolTipText() + " - " + getPlayer().name();
			}
		};

		playerIndicator.setName("PlayerIndicator");
		playerIndicator.setToolTipText( "Следующий ход для игрока" );
		
		createGameField(true);

		ToolTipManager.sharedInstance().setEnabled(true);
		ToolTipManager.sharedInstance().registerComponent(playerIndicator);
	}

	public void createGameField(boolean whithCheckers) {
		removeAll();
		
		add( playerIndicator );
		for (int i = 0; i < 8; i++) {
			add(new TextCell(ALPHABET[i]));
		}

		add(new EmptyCell());

		boolean isAvailable = false;
		for (int i = 0; i < 8; i++) {
			String rowName = NUMBERS[7 - i];
			add(new TextCell(rowName));

			Player player = i < 3 ? Player.ONE : i > 4 && i < 5 ? null : Player.TWO;
			boolean checker = i < 3 || i > 4 ? whithCheckers && true : false;
			Point lastRow = i < 3 ? new Point(-1, 0) : i > 4 && i < 5 ? null : new Point(-1, 7);

			for (int j = 0; j < 8; j++) {
				String columnName = ALPHABET[j];
				if (isAvailable) {
					CheckerCell checkerCell = new CheckerCell( j, i, player, checker, lastRow );
					checkerCell.setName( columnName + rowName );

					add(checkerCell);
				} else {
					add(new EmptyCell());
				}

				if (j != 7)
					isAvailable = !isAvailable;
			}

			add(new TextCell(rowName));
		}

		add(new EmptyCell());
		for (int i = 0; i < 8; i++) {
			add(new TextCell(ALPHABET[i]));
		}
		add(new EmptyCell());
		
		playerIndicator.setPlayer( Player.ONE );
	}

	protected void setSelected(CheckerCell cell) {
		if( cell.isChecker() && cell.getPlayer() == playerIndicator.getPlayer() ) {
		    if( this.selectedToMoveCell != null ) {
		    	this.selectedToMoveCell.setSelected( false );
				this.selectedToMoveCell.repaint();
				
				this.selectedToMoveCell = null;
		    }
	    	this.selectedToMoveCell = cell;
	    	this.selectedToMoveCell.setSelected( true );
	    	this.selectedToMoveCell.repaint();
		}
	}

	public CheckerCell getCheckerCell( int x, int y ) {
		Component componentAt = getComponentAt( x, y );
		if( componentAt instanceof CheckerCell ) {
			return (CheckerCell)componentAt;
		}
		return null;
	}

	/*
	 * "Первый" игрок может ходить вниз
	 * "Второй" игрок может ходить вверх
	 * "Дамка" может ходить во всех направлениях
	 * Любой игрок может "есть" во всех направлениях
	 */
	public boolean moveCheck(CheckerCell selectedMoveToCell, ScoreListener listener ) {
		setSelected(selectedMoveToCell);

		boolean result = selectedMoveToCell == selectedToMoveCell;
		if( !result && selectedToMoveCell != null  && !selectedMoveToCell.isChecker() ) {
			boolean queen = selectedToMoveCell.isQueen();

			CheckingData checkData = getPathCheckData( selectedToMoveCell, selectedMoveToCell, null );
			if(validatePath(checkData, false, queen)) 
			{
				clearFigures( checkData.fragCells );
	
				clearSelected( selectedToMoveCell );
	
				Player player = selectedToMoveCell.getPlayer();
				
				setSelectedToPathEnd( selectedMoveToCell, player, queen );
	
				listener.registerScore( new ScoreData( player.name(), selectedToMoveCell.getName(), selectedMoveToCell.getName() ) );
				
				// проверить смежные клетки для cellMoveTo на наличие вражеских шашек, 
				// возможен поворот и следующий ход ТОЛЬКО после факта "поедания" вражеской шашки или
				// если selectedToMoveCell была "дамкой"
		    	if( !(checkData.fragPathLength > 0 && 
		    			validOtherPaths( selectedToMoveCell, selectedMoveToCell, queen ))) 
		    	{
					playerIndicator.setPlayer(Player.invertPlayer(player));
					playerIndicator.repaint();
					
					selectedToMoveCell = null;
					continueMoving = false;
		    	}
		    	else {
					selectedToMoveCell = selectedMoveToCell;
					selectedToMoveCell.setSelected(true);
					continueMoving = true;
		    	}
				
				result = true;
			}
	    }

	    return result;
	}

	/*
	 * Проверить:
	 * 1. Ход по диагонали
	 * 2. Ход на свободную клетку 
	 * 3. Ход не через свои шашки
	 * 4. Ход через вражеские шашки на свободную клетку для обычной шашки. Не "дамка" не ходит дальше 1 клетки
	 * 5. Ходы на свободную клетку через вражеские шашки для "дамки"
	 * 6. Если вражеские шашки правильно упорядочены:
	 * 6.1 для обычной шашки важно, чтобы расстояние до первой вражеской шашки было равно 1
	 * 6.2 для "дамки" не важно расстояние до первой вражеской шашки
	 */
	protected boolean validatePath(CheckingData checkData, boolean lastPathValidated, boolean queen) {
		boolean result = checkData != null && 
				checkData.pathLength > 0 && // 1
				checkData.endPointEmpty && // 2
				!checkData.containsOur; // 3
		
		if( result ) {
			if( queen ) {
				result = checkData.fragPathLength > 0 ? checkData.fragsInOrder : true;
			}
			else {
				if( lastPathValidated ) {
					result = checkData.containsNearestFrag;
				}
				else {
					result = (continueMoving || checkData.validDirection) &&
							 (checkData.pathLength == 1 || 
							 (checkData.pathLength == 2 && checkData.containsNearestFrag));
				}
			}
		}
		return result;
	}

	/*
	 * return true, если есть возможность ходить. Ходить разрешено:
	 * 1. Обычной шашке после поедания противника и если ближайшая клетка занята чужой шашкой 
	 * 2. "Дамке", если есть чужие шашки и ход возможен 
	 * Необходимо исключить from, т.к. это начальная позиция
	 */
	private boolean validOtherPaths(CheckerCell from, CheckerCell to, boolean queen ) {
		boolean result = false;

		int deltaX = from.getXIndex() - to.getXIndex(); // нам нужен обратный знак
		int deltaY = from.getYIndex() - to.getYIndex(); // нам нужен обратный знак
		deltaX = deltaX / Math.abs( deltaX );
		deltaY = deltaY / Math.abs( deltaY );
		
		// направление налево вверх
		result = !Direction.UP_LEFT.equals( deltaX, deltaY ) && 
				validatePath( getPathCheckData(to, null, Direction.UP_LEFT), true, queen );
		if( !result ) {
			// направление направо вверх
			result = !Direction.UP_RIGTH.equals( deltaX, deltaY ) && 
					validatePath( getPathCheckData(to, null, Direction.UP_RIGTH), true, queen ); 
		}
		if( !result ) {
			// направление налево вниз
			result = !Direction.DOWN_LEFT.equals( deltaX, deltaY ) && 
					validatePath( getPathCheckData(to, null, Direction.DOWN_LEFT), true, queen ); 
		}
		if( !result ) {
			// направление направо вниз
			result = !Direction.DOWN_RIGTH.equals( deltaX, deltaY ) &&
					validatePath( getPathCheckData(to, null, Direction.DOWN_RIGTH), true, queen ); 
		}
		return result;
	}

	private CheckingData getPathCheckData(CheckerCell from, CheckerCell to, Direction direction ) 
	{
		CheckingData result = new CheckingData();

		int deltaX = 0; 
		int deltaY = 0;
		if( direction == null ) {
			deltaX = Direction.getDirectionDelta(to.getXIndex(), from.getXIndex()); 
			deltaY = Direction.getDirectionDelta(to.getYIndex(), from.getYIndex()); 
			
			result.endPointEmpty = !to.isChecker();
		}
		else {
			deltaX = direction.getDeltaX(); 
			deltaY = direction.getDeltaY();
		}

		result.validDirection = Direction.isDirectionCorrect(from.getPlayer().getDirections(), deltaX, deltaY);
		
		int actualXIndex = from.getXIndex();
		int actualYIndex = from.getYIndex();
		
		int lastIndex = -1;
		int i = 1;
		while((to != null && !isInPoit(to, actualXIndex, actualYIndex)) ||
				(direction != null && !isOutOfBorder(actualXIndex, actualYIndex))) {
			
			actualXIndex += deltaX;
			actualYIndex += deltaY;
			
			result.pathLength += 1;
			
			CheckerCell checkerCell = getCell(actualXIndex, actualYIndex);
			if( checkerCell.isChecker() )
			{
				if( checkerCell.getPlayer() != playerIndicator.getPlayer() ) {
					if( i == 1 ) { 
						boolean checkerNear = checkPointNear(from.getXIndex(), from.getYIndex(), checkerCell);
						result.containsNearestFrag = checkerNear;
						result.validDirection = true;
					}
					
					// проверить пустые клетки между "вражескими" шашками:
					// они должны быть через одну или больше без промежуточных наших
					if( lastIndex != -1 && (i - lastIndex) < 2 ) {
						result.fragsInOrder = false;
						result.containsNearestFrag = false;
					}
					
					result.addFragCell(checkerCell);
					lastIndex = i;
				}
				else {
					result.fragsInOrder = false;
					result.containsOur = true;
				}
				result.endPointEmpty = false; // если это последняя точка, то не пустая
			}
			else {
				result.endPointEmpty = true; // если это последняя точка, то пустая
				if( to == null ) break;
			}
			
			i++;
		}	
		return result;
	}
	
	/*
	 * поле в границах 
	 * x=0 to 7
	 * y=0 to 7
	 */
	private boolean isOutOfBorder(int xIndex, int yIndex) {
		boolean result = xIndex <= 0 || xIndex >= 7 || yIndex <= 0 || yIndex >= 7;
		return result;
	}

	private boolean isInPoit(CheckerCell to, int xIndex, int yIndex) {
		boolean result = xIndex == to.getXIndex() && yIndex == to.getYIndex();
		return result;
	}

	protected void setSelectedToPathEnd(CheckerCell dest, Player player, boolean queen) {
		dest.setPlayer(player);
		dest.setChecker(true);
		dest.setSelected(false);
		dest.setQueen(queen || dest.isLastRow());
		dest.repaint();
	}

	protected void clearSelected(CheckerCell cellToClear) {
		cellToClear.setChecker(false);
		cellToClear.setSelected(false);
		cellToClear.setQueen(false);
		cellToClear.repaint();
	}

	public void clearFigures(List<CheckerCell> fragCells) {
		if( fragCells != null && !fragCells.isEmpty() ) {
			for (CheckerCell checkerCell : fragCells) {
				checkerCell.setChecker(false);
				checkerCell.setQueen(false);
				checkerCell.getPlayer().decreaseFigureCount();
				checkerCell.repaint();
			}
			fragCells.clear();
		}
	}

	private boolean checkPointEqual(int beginXIndex, int beginYIndex, CheckerCell cell) {
		return beginXIndex == cell.getXIndex() && beginYIndex == cell.getYIndex();
	}

	private boolean checkPointNear(int beginXIndex, int beginYIndex, CheckerCell cell) {
		return Math.abs(beginXIndex - cell.getXIndex()) == 1 && 
				Math.abs(beginYIndex - cell.getYIndex() ) == 1;
	}

	private CheckerCell getCell(int beginXIndex, int beginYIndex) {
		for (int i = 0; i < getComponentCount(); i++) { // все CheckerCell добавлены в контейнер JPanel
			Component component = getComponent(i);
			if( component instanceof CheckerCell ) {
				CheckerCell candidate = (CheckerCell)component;
				if( checkPointEqual(beginXIndex, beginYIndex, candidate) )
				{
					return candidate;
				}
			}
		}
		return null;
	}

	class CheckingData {
		/* 
		 * длина пути
		 */
		int pathLength; 
		/* 
		 * направление движения правильное
		 */
		boolean validDirection; 
		/* 
		 * точка назначения не содержит шашку
		 */
		boolean endPointEmpty; 
		/*
		 * содержит нашу шашку
		 */
		boolean containsOur; 
		/*
		 * содержит шашку противника рядом
		 */
		boolean containsNearestFrag; 
		/* 
		 * Содержит шашки противника, стоящие в позиции "через один".
		 * Предполагаем, что все хорошо изначально, чтобы сбросить в процессе проверки
		 */
		boolean fragsInOrder = true;
		/*
		 * максимальная последовательность шашек противника
		 */
		int fragPathLength; 
		/*
		 * Шашки противника для удаления
		 */
		List<CheckerCell> fragCells;
		
		public void addFragCell(CheckerCell frag) {
			if( this.fragCells == null ) {
				this.fragCells = new ArrayList<CheckerCell>(1);
			}
			
			this.fragCells.add(frag);
			this.fragPathLength++;
		}

		@Override
		public String toString() {
			return "CheckData [pathLength=" + pathLength + ", endPointEmpty=" + endPointEmpty + ", containsOur="
					+ containsOur + ", containsNearestFrag=" + containsNearestFrag + ", fragsInOrder=" + fragsInOrder
					+ ", fragPathLength=" + fragPathLength + ", fragCells=" + fragCells + "]";
		}
	}
}
