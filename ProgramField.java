package turing;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ProgramField extends JPanel implements ActionListener {
	
	ArrayList<Operation> ops;
	ArrayList<OpPanel> opPanel;
	
	AddOpPanel addOpPanel;
	JButton    addOpButton;
	
	ProgramField() {
		ops         = new ArrayList<>();
		addOpPanel  = new AddOpPanel();
		opPanel     = new ArrayList<>();
		addOpButton = new JButton("new");
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		addOpPanel.setOperationBuilder(new OperationBuilder() {
			@Override
			public void addOperation(Operation op) {
				
				ops.add(op);

				remove(addOpPanel);
				opPanel.add(new OpPanel(op, ops.size() - 1, new OperationBuilder() { 
							@Override
							public void deleteOperation(int numOp) {
								ops.remove(numOp);
								opPanel.remove(numOp);
								
								redrawOps();
								addButton();
							}
							}));
				
				redrawOps();
				addButton();
			}	
		});
		
		addOpButton.addActionListener(this);
		addOpButton.setFont(new Font("Courier New", Font.PLAIN, 30));
		addButton();
	}

	private void addButton() {
		addOpButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
		addOpButton.setMinimumSize(new Dimension(getMinimumSize().width, 48));
		add(addOpButton);
		revalidate();
		repaint();
	}
	
	private void addPanel() {
		addOpPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, addOpButton.getHeight()));
		addOpPanel.setMinimumSize(new Dimension(getMinimumSize().width, addOpButton.getHeight()));
		add(addOpPanel);
	}
	
	private void redrawOps() {

		removeAll();
		
		for(int i = 0; i < opPanel.size(); i++) {
			OpPanel p = opPanel.get(i);
			p.setMaximumSize(new Dimension(Integer.MAX_VALUE, addOpButton.getHeight()));
			p.setMinimumSize(new Dimension(getMinimumSize().width, addOpButton.getHeight()));
			p.resetOperationNumber(i);
			add(p);
		}
		revalidate();
		repaint();
	}
	
	public ArrayList<Operation> getOperationsList() { return this.ops; }
	
	public void setEnabled(boolean isEnable) { 
		addOpButton.setEnabled(isEnable);
		opPanel.forEach(o -> o.setEnabled(isEnable));
		addOpPanel.setEnabled(isEnable);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		remove(addOpButton);
		addPanel();
		revalidate();
		repaint();
	}
}
