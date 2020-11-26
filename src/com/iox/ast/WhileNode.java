package com.iox.ast;

import com.iox.util.Util;

public class WhileNode extends Node{
	private Node condition;
	private Node block;
	/*
	 * Constructor.
	 */
	public WhileNode(Node condition, Node block) {
		this.condition = condition;
		this.block = block;
	}
	@Override
	public Object eval() {
		Object lastStmt = null;
		while (Boolean.valueOf(condition.eval().toString())){
			if (block != null) {
				lastStmt = block.eval();				
				if (Util.isReturnOrExit(lastStmt)) {
					break;
				}
			}
		}
		return lastStmt;
	}
}
