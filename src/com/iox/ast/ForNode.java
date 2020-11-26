package com.iox.ast;
import com.iox.parser.*;
import com.iox.util.Util;
public class ForNode extends Node{	
	private String varName;
	private Node assignment;
	private Node finalValue;
	private Node stepValue;
	private Node block;
	private Parser parser;
	/*
	 * Mandatory Constructor
	 */
	public ForNode(String varName, Node assignment, Node finalValue, Node stepValue, Node block, Parser parser) {
		this.varName = varName;
		this.assignment = assignment;
		this.finalValue = finalValue;
		this.stepValue = stepValue;
		this.block = block;
		this.parser = parser;
	}
	@Override
	public Object eval() {
		Object lastStmt = new Integer(0);
		if (block != null) {
			int currentValue = Integer.valueOf(assignment.eval().toString());
			int stopHere = Integer.valueOf(finalValue.eval().toString());
			int stepVal = Integer.valueOf(stepValue.eval().toString());
			boolean canLoop = false;
			while (true) {
				canLoop = (stepVal > 0) ? currentValue <= stopHere : currentValue >= stopHere;
				if (canLoop) {
					lastStmt = block.eval();
					if (Util.isReturnOrExit(lastStmt)) {
						break;
					}
					currentValue = Integer.valueOf(parser.getVariable(varName).toString()) + stepVal;
					parser.setVariable(varName, currentValue);
				} else {
					break;
				}
			}
		}
		return lastStmt;
	}
}
