package icc.stud.kotov_av.russian_checkers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class ScoreLine extends JPanel {
	private static final long serialVersionUID = -3203387804479401603L;
	private JLabel label;
	private String labelText;

	public ScoreLine() {
		this(null);
	}

	public ScoreLine(String labelText) {
		this(labelText, "");
	}

	public ScoreLine(String labelText, String value) {
		super(new BorderLayout());

		setBorder(new BevelBorder(BevelBorder.LOWERED, SystemColor.controlLtHighlight, SystemColor.menu,
				SystemColor.menu, SystemColor.controlShadow));

		label = new JLabel(labelText != null ? labelText + " " + value : value, JLabel.LEFT);
		label.setFont( label.getFont().deriveFont( Font.PLAIN, 20f ) );

		this.labelText = labelText;

		label.setHorizontalAlignment(JLabel.LEFT);
		label.setVerticalAlignment(JLabel.BOTTOM);
		label.setHorizontalTextPosition(JLabel.LEFT);
		label.setVerticalTextPosition(0);

		add(label, BorderLayout.CENTER);
	}

	public void setValue(String value) {
		label.setText(labelText != null ? labelText + " " + value : value);
	}

	@Override
	public Dimension getPreferredSize() {
		return getMinimumSize();
	}

	@Override
	public void setBounds(int x, int y, int w, int h) {
		Dimension dmin = getMinimumSize();
		Dimension dmax = getMaximumSize();
		super.setBounds(x, y, w < dmax.width ? w : dmax.width, h < dmin.height ? h : dmin.height);
	}
}
