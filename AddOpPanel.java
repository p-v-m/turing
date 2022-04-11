package turing;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

public class AddOpPanel extends JPanel implements ActionListener{

	Operation newOperation;
	
	OperationBuilder operationBuilder;
	
	final String[] symbols = {"0", "1"};
	final String[] directions = {"L", "R", "H"};
	
	JLabel label1;
	JSpinner initState;
	JComboBox<String> initSym;
	JLabel label2;
	JSpinner finalState;
	JComboBox<String> finalSym;
	JComboBox<String> finalDirection;
	JButton confirm;
	Font font;
	
	AddOpPanel() {
		
		setBackground(new Color(210, 210, 210));
		font = new Font("Courier New", Font.PLAIN, 25);
		
		label1 = new JLabel("P ");
		label1.setFont(font);
		label2 = new JLabel(" -> P ");
		label2.setFont(font);
		
		initState = new JSpinner();
		initState.setFont(font);
		finalState = new JSpinner();
		finalState.setFont(font);
		
		initSym = new JComboBox<>(symbols);
		initSym.setFont(font);
		finalSym = new JComboBox<>(symbols);
		finalSym.setFont(font);
		finalDirection = new JComboBox<>(directions);
		finalDirection.setFont(font);
		
		confirm = new JButton("Confirm");
		confirm.setFont(font);
		confirm.addActionListener(this);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		c.insets = new Insets(3,3,3,3);
		add(label1, c);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.15;
		c.insets = new Insets(3,3,3,3);
		add(initState, c);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 0.1;
		c.insets = new Insets(3,3,3,3);
		add(initSym, c);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 4;
		c.gridy = 0;
		c.weightx = 0;
		c.insets = new Insets(3,3,3,3);
		add(label2, c);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 5;
		c.gridy = 0;
		c.weightx = 0.15;
		c.insets = new Insets(3,3,3,3);
		add(finalState, c);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 6;
		c.gridy = 0;
		c.weightx = 0.1;
		c.insets = new Insets(3,3,3,3);
		add(finalSym, c);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 7;
		c.gridy = 0;
		c.weightx = 0.1;
		c.insets = new Insets(3,3,3,3);
		add(finalDirection, c);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 8;
		c.gridy = 0;
		c.weightx = 0.2;
		c.insets = new Insets(3,3,3,3);
		add(confirm, c);
	}
	
	public void setOperationBuilder(OperationBuilder op) {
		this.operationBuilder = op;
	}

	public void setEnabled(boolean isEnabled) { confirm.setEnabled(isEnabled); }
	
	@Override
	public void actionPerformed(ActionEvent e) {

		newOperation = new Operation();
		
		if(e.getSource() == confirm) {
			newOperation.INIT_SYM = ((String)initSym.getSelectedItem()).charAt(0);
			newOperation.FINAL_SYM = ((String)finalSym.getSelectedItem()).charAt(0);
			newOperation.INIT_STATE = (int)initState.getValue();
			newOperation.FINAL_STATE = (int)finalState.getValue();
			newOperation.DIRECTION = Direction.valueOf(
					(String)finalDirection.getSelectedItem());
			
			if(operationBuilder != null)
				operationBuilder.addOperation(newOperation);
		}
	}
}
