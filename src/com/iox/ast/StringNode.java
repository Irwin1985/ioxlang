package com.iox.ast;

public class StringNode extends Node{
	private String value;
	/*
	 * Constructor
	 */
	public StringNode(String value) {
		this.value = value;
	}
	@Override
	public Object eval() {
		return value;
	}
	
}
