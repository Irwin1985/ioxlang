package com.iox.ast;
import com.iox.parser.*;
public class InlineFun extends Node{
	protected Parser parser;
	public InlineFun() {}
	/*
	 * Constructor.
	 */
	public InlineFun(Parser parser) {
		this.parser = parser;
	}
	/*
	 * getVariable
	 */
	public Object getVariable(String varName) {
		return parser.getVariable(varName);
	}
	/*
	 * Eval method.
	 */
	public Object eval() {
		return parser;
	}
}
