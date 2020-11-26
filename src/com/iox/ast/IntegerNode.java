package com.iox.ast;
/*-
 * IntegerNode handles integers data types.
 */
public class IntegerNode extends Node{
	private int value;
	private boolean signed;
	/*
	 * Normal Constructor
	 */
	public IntegerNode(int value) {
		this.value = value;
	}
	/*
	 * Signed Constructor.
	 */
	public IntegerNode(int value, boolean signed) {
		this.value = value;
		this.signed = signed;
	}
	
	@Override
	public Object eval() {
		return (this.signed) ? value * -1 : value;
	}
}
