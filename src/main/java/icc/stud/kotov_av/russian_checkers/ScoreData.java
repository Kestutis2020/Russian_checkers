package icc.stud.kotov_av.russian_checkers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScoreData {

	private static SimpleDateFormat DF = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss,S");
	
	private Date date;
	private String player;
	private String cellFrom;
	private String cellTo;

	public ScoreData( String player, String cellFrom, String cellTo ) {
		date = new Date();
		this.player = player;
		this.cellFrom = cellFrom;
		this.cellTo = cellTo;
	}

	@Override
	public String toString() {
		return DF.format(date) + ". Игрок " + player + " перешел: " + cellFrom + " --> " + cellTo;	
	}
}
