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
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OpPanel extends JPanel implements ActionListener {

	Operation op;
	int       numOp;
	
	JLabel  label1;
	JButton delete;
	
	OperationBuilder operationBuilder;
	
	Font font;
	GridBagConstraints c;
	
	OpPanel(Operation op, int num, OperationBuilder ob) {
		this.op = op;
		this.operationBuilder = ob;
		font = new Font("Courier New", Font.PLAIN, 25);
		
		setBackground(new Color(230, 230, 230));
		setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();
 
		label1 = new JLabel(String.valueOf(numOp + 1) + ". " + op.toString());
		label1.setFont(font);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.85;
		c.insets = new Insets(3,3,3,3);
		add(label1, c);
		
		delete = new JButton("delete");
		delete.addActionListener(this);
		delete.setFont(font);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 0.15;
		c.insets = new Insets(3,3,3,3);
		add(delete, c);
		
	}
	
	public void setOperationBuilder(OperationBuilder op) {
		this.operationBuilder = op;
	}
	
	public void setEnabled(boolean isEnabled) { delete.setEnabled(isEnabled); }
	
	public void resetOperationNumber(int numOp) { 
		this.numOp = numOp; 
		
		remove(label1);
		label1 = new JLabel(String.valueOf(numOp + 1) + ". " + op.toString());
		label1.setFont(font);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.85;
		c.insets = new Insets(3,3,3,3);
		add(label1, c);
		revalidate();
		repaint();
	} 

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == delete)
			if(operationBuilder != null)
				operationBuilder.deleteOperation(numOp);
	}
	
	
}
