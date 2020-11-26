package com.iox.ast;

import com.iox.parser.*;
public class VariableAccessNode extends Node{
	private String varName;
	private Parser parser;
	/*
	 * Constructor.
	 */
	public VariableAccessNode(String varName, Parser parser) {
		this.varName = varName;
		this.parser = parser;
	}
	@Override
	public Object eval() {
		Object varValue = parser.getVariable(varName);
		if (varValue == null) {
			System.out.println("Undefined variable '" + varName + "'");
			System.exit(-1);
		}
		return varValue;
	}
}
