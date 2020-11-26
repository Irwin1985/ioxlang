package com.iox.ast;
import java.util.List;

public class IfNode extends Node{
	private Node condition;
	private Node blockIf;
	private List<BranchNode> elifNodes;
	private Node blockElse;
	private static final int CONDITION_STATE = 0;
	private static final int LAST_STATEMENT = 1;
	/*
	 * Constructor
	 */
	public IfNode(Node condition, Node blockIf, List<BranchNode> elifNode, Node blockElse) {
		this.condition = condition;
		this.blockIf = blockIf;
		this.elifNodes = elifNode;
		this.blockElse = blockElse;
	}
	@Override
	public Object eval() {
		Object lastStmt = new Integer(0);
		boolean evalElseStmt = false;
		boolean conditionState = false;
		if (Boolean.valueOf(condition.eval().toString())) {
			// if-block
			if (blockIf != null) {
				lastStmt = blockIf.eval();
			}
		} else if(elifNodes != null && elifNodes.size() > 0) {
			// elifs-blocks
			for (BranchNode elifStmt: elifNodes) {
				@SuppressWarnings("unchecked")
				List<Object> result = (List<Object>)elifStmt.eval();
				if (result != null) {					
					conditionState = (Boolean)result.get(CONDITION_STATE);
					lastStmt = result.get(LAST_STATEMENT);
					if (conditionState) {						
						break;
					}
				}
			}
			evalElseStmt = !conditionState;
		} else {
			evalElseStmt = true;			
		}
		// else-block
		if (evalElseStmt) {				
			if (blockElse != null) {
				lastStmt = blockElse.eval();
			}
		}
		return lastStmt;
	}
}
