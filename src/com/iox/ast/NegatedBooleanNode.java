package com.iox.ast;

public class NegatedBooleanNode extends Node{
	private Node node;
	/*
	 * Single Constructor.
	 */
	public NegatedBooleanNode(Node node) {
		this.node = node;
	}
	@Override
	public Object eval() {
		Object boolResult = !Boolean.parseBoolean(this.node.eval().toString());
		return boolResult;
	}
}
