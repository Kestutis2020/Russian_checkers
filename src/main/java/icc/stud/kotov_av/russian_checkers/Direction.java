package icc.stud.kotov_av.russian_checkers;

public enum Direction {
	// направление налево вверх
	UP_LEFT(-1, -1),
	// направление направо вверх
	UP_RIGTH(1, -1),
	// направление налево вниз
	DOWN_LEFT(-1, 1),
	// направление направо вниз
	DOWN_RIGTH(1, 1);
	
	private int deltaX;
	private int deltaY;

	private Direction( int deltaX, int deltaY ) {
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}

	public int getDeltaX() {
		return deltaX;
	}

	public int getDeltaY() {
		return deltaY;
	}

	public boolean equals( int deltaX, int deltaY) {
		return this.deltaX == deltaX && this.deltaY == deltaY;
	}

	@Override
	public String toString() {
		return name() + ": " + deltaX + ", " + deltaY;
	}

	// сдвиг вправо (знак плюс) или влево (знак минус) на одну колонку
	// сдвиг вниз (знак плюс) или вверх (знак минус) на одну строку
	public static int getDirectionDelta(int endIndex, int beginIndex) {
		int delta = endIndex - beginIndex;
		return delta != 0 ? delta / Math.abs( delta ) : 0;
	}

	public static boolean isDirectionCorrect(Direction[] directions, int deltaX, int deltaY) {
		boolean result = false;
		if( Math.abs( deltaX ) == Math.abs( deltaY ) ) {
			for (Direction direction : directions) {
				if( direction.equals(deltaX, deltaY) ) {
					result = true;
					break;
				}
			}
		}
		return result;
	}
}
