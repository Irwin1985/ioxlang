package com.iox.ast;
/*
 * DoubleNode handles doubles data types.
 */
public class DoubleNode extends Node {
	private double value;
	private boolean signed;
	/*
	 * Normal Constructor.
	 */
	public DoubleNode(double value) {
		this.value = value;
	}
	/*
	 * Signed Constructor.
	 */
	public DoubleNode(double value, boolean signed) {
		this.value = value;
		this.signed = signed;
	}
	@Override
	public Object eval() {
		return (this.signed) ? value * -1 : value;
	}

}
