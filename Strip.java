package turing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Strip extends JPanel implements ActionListener {

	public final String ZEROES = "0000000";
	
	private JTextArea strip;
	private JButton addZeroes;
	
	Strip() {

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		strip = new JTextArea(ZEROES);
		strip.setFont(new Font("Courier New", Font.PLAIN, 100));
		strip.setBorder(BorderFactory.createLineBorder(new Color(170,170,170)));
		strip.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
		
		
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.9;
		c.weighty = 1;
		add(new JScrollPane(strip), c);
		
		addZeroes = new JButton("+");
		addZeroes.setFont(addZeroes.getFont().deriveFont(25f));
		addZeroes.addActionListener(this);

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.1;
		c.weighty = 1;
		add(addZeroes, c);
	}
	
	public void resetStrip() { strip.setText(ZEROES); }
	public void setText(final String text) { strip.setText(text); }
	public void setEnabled(boolean isEnabled) { strip.setFocusable(isEnabled); }
	
	public String getContent() { return strip.getText(); }

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == addZeroes)
			strip.append(ZEROES);
			
	}
}
