package generator;

import model.*;

public abstract class Visitor<T> {

	public abstract void visit(App app);
	public abstract void visit(Action action);
	public abstract void visit(Producer producer);
	public abstract void visit(Consumer consumer);
	public abstract void visit(Register register);
	public abstract void visit(SetRegister setreg);
	public abstract void visit(Actuator actuator);
	public abstract void visit(SetActuator setactu);

	/***********************
	 ** Helper mechanisms **
	 ***********************/

	T code;
	T headers;

	public T getCode() {
		return code;
	}

	public T getHeaders() {
		return headers;
	}

}

