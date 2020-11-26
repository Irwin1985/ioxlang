package com.iox.ast;
import java.util.List;
public class DoCaseNode extends Node{
	private List<BranchNode> caseNodes;
	private Node defaultNodes;
	private static final int CONDITION_STATE = 0;
	private static final int LAST_STATEMENT = 1;
	/*
	 * Constructor
	 */
	public DoCaseNode(List<BranchNode> caseNodes, Node defaultNodes) {
		this.caseNodes = caseNodes;
		this.defaultNodes = defaultNodes;
	}
	@Override
	public Object eval() {
		boolean conditionState = false;
		boolean evalDefaultStmt = true;
		Object lastStmt = new Integer(0);
		if (caseNodes != null && caseNodes.size() > 0) {
			for (Node stmt: caseNodes) {				
				@SuppressWarnings("unchecked")
				List<Object> result = (List<Object>)stmt.eval();
				if (result != null) {					
					conditionState = (Boolean)result.get(CONDITION_STATE);
					lastStmt = result.get(LAST_STATEMENT);
					if (conditionState) {						
						break;
					}
				}
			}
			evalDefaultStmt = !conditionState;
		}
		// default
		if (evalDefaultStmt) {
			if (defaultNodes != null) {
				lastStmt = defaultNodes.eval();
			}
		}
		return lastStmt;
	}
}
