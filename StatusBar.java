package turing;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class StatusBar extends JPanel {
	
	private JTextArea state;
	private JTextArea step;

	
	public StatusBar() {
		
		state = new JTextArea("State: ");
		step = new JTextArea("Step: ");

		state.setFont(state.getFont().deriveFont(40f));
		step.setFont(step.getFont().deriveFont(40f));
		state.setBackground(new Color(200, 200, 200));
		step.setBackground(new Color(200, 200, 200));
		state.setFocusable(false);
		step.setFocusable(false);
		
		setMinimumSize(getSize());
		setLayout(new BorderLayout());
		add(state, BorderLayout.EAST);
		add(step, BorderLayout.WEST);
	}

	public void resetStatusBar() {
		state.setText("State: ");
		step.setText("Step: ");
	}
	
	public void setState(String text) { state.setText("State: " + text); }
	public void setStep(String text) { step.setText("Step: " + text); }
}
