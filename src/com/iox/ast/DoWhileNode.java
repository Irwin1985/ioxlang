package com.iox.ast;

import com.iox.util.Util;
public class DoWhileNode extends Node{
	private Node condition;
	private Node block;
	/*
	 * Constructor.
	 */
	public DoWhileNode(Node condition, Node block) {
		this.condition = condition;
		this.block = block;
	}
	@Override
	public Object eval() {
		Object lastStmt = new Integer(0);
		do {
			if (block != null) {
				lastStmt = block.eval();
				if (Util.isReturnOrExit(lastStmt)){
					break;
				}
			}
		} while (Boolean.valueOf(condition.eval().toString()));
		return lastStmt;
	}
}
