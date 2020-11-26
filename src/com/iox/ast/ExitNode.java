package com.iox.ast;

public class ExitNode extends Node{
	/*
	 * Constructor.
	 */
	public ExitNode() {}
	@Override
	public Object eval() {
		return "exit";
	}
}
