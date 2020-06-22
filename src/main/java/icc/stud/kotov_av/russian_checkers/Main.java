package icc.stud.kotov_av.russian_checkers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import icc.stud.kotov_av.russian_checkers.actions.NewGameAction;
import icc.stud.kotov_av.russian_checkers.actions.ShowScoreAction;

public class Main implements ScoreListener {

	private JFrame frame;
	private GameField gameField;
	private List<ScoreData> scoreData;
	private ScoreLine scoreLine;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame( "Русские шашки" );
		frame.setBounds( 100, 100, 600, 600 );
		frame.setMinimumSize( new Dimension( 600, 600 ) );
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menubar = new JMenuBar();
		
		JMenu menu = new JMenu("Игра");
		menubar.add(menu);
		
		menu.add( new JMenuItem(new NewGameAction( this )) );
		menu.add( new JMenuItem(new ShowScoreAction( this )) );

		frame.setJMenuBar(menubar);
		
		gameField = new GameField();

		frame.getContentPane().add( gameField, BorderLayout.CENTER );
		
		scoreLine = new ScoreLine( "Ход: " );
		frame.getContentPane().add( scoreLine, BorderLayout.SOUTH ); 
		
		gameField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				GameField gameField = (GameField) evt.getComponent();

				CheckerCell cell = gameField.getCheckerCell(evt.getX(), evt.getY());
				if ( cell!= null ) {
					gameField.setSelected(cell);

					if (!gameField.moveCheck(cell, Main.this)) {
						JOptionPane.showMessageDialog(frame, 
								"Ход не доступен!", 
								"Информация",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					
					String msg = null;
					if( Player.ONE.getFigureCount() == 0 ) {
						msg = "Игрок " + Player.ONE.name() + " проиграл!";
					}
					else if( Player.TWO.getFigureCount() == 0 ) {
						msg = "Игрок " + Player.TWO.name() + " проиграл!";
					}

					if( msg != null ) {
						JOptionPane.showMessageDialog(frame, 
								"GAME OVER!\n\n" + msg, 
								"Информация",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
	}

	public void createNewGame() {
		for (Player player : Player.values()) {
			player.resetFigureCount();
		}

		getScoreData().clear();
		
		scoreLine.setValue("");
		
		gameField.createGameField();
		gameField.revalidate();
		gameField.repaint();
	}

	public List<ScoreData> getScoreData() {
		if( scoreData == null ) {
			scoreData = new ArrayList<ScoreData>(23);
		}
		return scoreData;
	}

	@Override
	public void registerScore(ScoreData data) {
		getScoreData().add( data );
		
		scoreLine.setValue( data.toString() );
	}
}
