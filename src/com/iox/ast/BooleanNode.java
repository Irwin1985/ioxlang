package com.iox.ast;

public class BooleanNode extends Node {
	private boolean value;
	/*
	 * Single constructor.
	 */
	public BooleanNode(boolean value) {
		this.value = value;
	}
	@Override
	public Object eval() {
		return this.value;
	}
}
