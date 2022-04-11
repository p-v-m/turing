package turing;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.Timer;

public class MainFrame extends JFrame implements ActionListener {

	private StatusBar    statusBar;
	private Strip	     strip;
	private ProgramField programField;
	
	private JButton 	 startButton;
	private JButton      stopButton;
	private JButton      resetButton;
	
	private boolean isBeingRun = false;
	private boolean stop = true;
	
	public MainFrame() {
		super("Turing Machine Simulator");
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		statusBar = new StatusBar();
		strip = new Strip();
		programField = new ProgramField();
		startButton = new JButton("Run");
		stopButton = new JButton("Stop");
		resetButton = new JButton("Reset");
		
		startButton.setFont(new Font("Courier New", Font.PLAIN, 40));
		startButton.addActionListener(this);
		stopButton.setFont(new Font("Courier New", Font.PLAIN, 40));
		stopButton.addActionListener(this);
		resetButton.setFont(new Font("Courier New", Font.PLAIN, 40));
		resetButton.addActionListener(this);
		
		stopButton.setEnabled(false);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.weightx = 0.5;
		c.weighty = 0;
		c.insets = new Insets(3,3,3,3);
		add(statusBar, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 0.05;
		c.insets = new Insets(3,3,3,3);
		add(strip, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 2;
		c.weighty = 1;
		c.insets = new Insets(3,3,3,3);
		add(new JScrollPane(programField), c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		c.weighty = 0;
		c.weightx = 0.33;
		c.gridwidth = 1;
		c.insets = new Insets(3,3,3,3);
		add(startButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.weighty = 0;
		c.weightx = 0.33;
		c.insets = new Insets(3,3,3,3);
		add(stopButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 3;
		c.weighty = 0;
		c.gridwidth = 1;
		c.weightx = 0.33;
		c.insets = new Insets(3,3,3,3);
		add(resetButton, c);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 1000);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == startButton) {
			unsetRunnable();	
			run();
		}
		if(e.getSource() == stopButton) {
			setRunnable();
		}
		if(e.getSource() == resetButton) {
			strip.resetStrip();
			statusBar.resetStatusBar();
		}
	}
	
	private void unsetRunnable() {

		isBeingRun = true;
		stop = false;
		stopButton.setEnabled(true);
		startButton.setEnabled(false);
		resetButton.setEnabled(false);
		programField.setEnabled(false);
		strip.setEnabled(false);
	}
	
	private void setRunnable() {

		isBeingRun = false;
		stop = true;
		stopButton.setEnabled(false);
		startButton.setEnabled(true);
		resetButton.setEnabled(true);
		programField.setEnabled(true);
		strip.setEnabled(true);
	}
	
	public void run() {
		
		ArrayList<Operation> ops = programField.getOperationsList();
		StringBuilder        stripContent = new StringBuilder(strip.getContent());
		int 				 curStripIndex = 0;
		int 				 curState = 0;

		HashMap<Integer, Entry<Operation, Operation>> opMap = new HashMap<>(); // <state, <case_0, case_1>>
		
		boolean hasTerminalState = false;
		for(int i = 0; i < ops.size(); i++) {
			final Operation op = ops.get(i);
			
			if(op.DIRECTION == Direction.H)
				hasTerminalState = true;
			
			if(opMap.containsKey(op.INIT_STATE))
				opMap.put(op.INIT_STATE, op.INIT_SYM == '0' ?
						new AbstractMap.SimpleEntry<Operation, Operation>(op, opMap.get(op.INIT_STATE).getValue()) :
						new AbstractMap.SimpleEntry<Operation, Operation>(opMap.get(op.INIT_STATE).getKey(), op));
			else
				opMap.put(op.INIT_STATE, op.INIT_SYM == '0' ? 
						new AbstractMap.SimpleEntry<Operation, Operation>(op, null) : 
						new AbstractMap.SimpleEntry<Operation, Operation>(null, op));
		}
		if(!hasTerminalState) {
			JOptionPane.showMessageDialog(null, "No terminal state, program can't be run. - Kostyl");
			setRunnable();
			return;
		}
		
		curState = ops.get(0).INIT_STATE;
		
		for(int i = 0; !stop; i++) {
			// getting current state from operationsMap
			// if no such state - terminate
			Entry<Operation, Operation> e = opMap.get(curState);
			if(e == null) {
				stop = true;
				setRunnable();
				continue;
			}

			// expanding strip if necessary
			if(curStripIndex > stripContent.length() - 1) 
				stripContent.append(strip.ZEROES);
			if(curStripIndex < 0) {
				curStripIndex = strip.ZEROES.length() - 1;
				stripContent.insert(0, strip.ZEROES);
			}
			
			//extracting operation to perform
			//if no such op or is terminal op - terminate
			char c = stripContent.charAt(curStripIndex);
			Operation o = c == '0' ? e.getKey() : e.getValue();
			if(o == null) {
				stop = true;
				setRunnable();
				continue;
			}
			
			//performing op
			stripContent.setCharAt(curStripIndex, o.FINAL_SYM);
			curState = o.FINAL_STATE;
			curStripIndex = o.DIRECTION == Direction.L ? curStripIndex - 1 : curStripIndex + 1;
			
			//setting timer to reset string
			final Integer num = i;
			final Integer state = o.FINAL_STATE;
			final String  str = stripContent.toString();
			if(o.DIRECTION == Direction.H) {
				stop = true;
				Timer timer = new Timer(400 * (num + 1), new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						setRunnable();
					}
				});
				timer.setRepeats(false);
				timer.start();
				continue;
			}
			Timer timer = new Timer(400 * (num + 1), new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					strip.setText(str);
					statusBar.setState(String.valueOf(state));
					statusBar.setStep(String.valueOf(num + 1));
				}
			});
			timer.setRepeats(false);
			timer.start();
		}			
	}
}
