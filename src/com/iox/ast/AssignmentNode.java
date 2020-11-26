package com.iox.ast;

import com.iox.parser.Parser;
public class AssignmentNode extends Node{
	private String varName;
	private Node varValue;
	private Parser parser;
	/*
	 * Constructor.
	 */
	public AssignmentNode(String varName, Node varValue, Parser parser) {
		this.varName = varName;
		this.varValue = varValue;
		this.parser = parser;
	}
	@Override
	public Object eval() {
		Object varContent = varValue.eval();
		parser.setVariable(varName, varContent);
		return varContent;
	}
}
