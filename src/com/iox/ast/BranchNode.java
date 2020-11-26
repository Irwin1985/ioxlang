package com.iox.ast;
import java.util.List;
import java.util.ArrayList;
public class BranchNode extends Node{
	private Node condition;
	private Node block;
	/*
	 * Constructor.
	 */
	public BranchNode(Node condition, Node block) {
		this.condition = condition;
		this.block = block;
	}
	@Override
	public Object eval() {
		Object conditionState = false;
		Object lastStmt = new IntegerNode(0);
		List<Object> result = new ArrayList<Object>();
		if (block != null) {
			if (Boolean.valueOf(condition.eval().toString())) {
				conditionState = true;
				lastStmt = block.eval();
			}
		}
		result.add(conditionState);
		result.add(lastStmt);
		return result;
	}
}
