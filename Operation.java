package turing;

import javax.swing.JPanel;

enum Direction { L, R, H }

final class Operation extends JPanel {
	int       INIT_STATE;
	char      INIT_SYM;
	int       FINAL_STATE;
	char      FINAL_SYM;
	Direction DIRECTION;
	
	@Override
	public boolean equals(Object o) {
		
		if(o == this)
			return true;
		if(!(o instanceof Operation))
			return false;
		
		Operation op = (Operation) o;
		
		return op.INIT_STATE == this.INIT_STATE &&
			   op.INIT_SYM == this.INIT_SYM &&
			   op.FINAL_STATE == this.FINAL_STATE &&
			   op.FINAL_SYM == this.FINAL_SYM &&
			   op.DIRECTION == this.DIRECTION;
	}
	
	@Override
	public String toString() {
		return "P" + String.valueOf(INIT_STATE) + String.valueOf(INIT_SYM) +
			   " -> P" + String.valueOf(FINAL_STATE) + 
			   String.valueOf(FINAL_SYM) + DIRECTION.name(); 
				
	}
}
