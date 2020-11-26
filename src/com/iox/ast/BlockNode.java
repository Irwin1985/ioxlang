package com.iox.ast;
import java.util.List;
import com.iox.ast.Node;

public class BlockNode extends Node{
	private List<Node> statements;
	/*
	 * Constructor.
	 */
	public BlockNode(List<Node> statements) {
		this.statements = statements;
	}
	/*
	 * pushNode = add a Node in front of the List<Node>
	 */
	public void pushNode(Node stmtNode) {
		statements.add(0, stmtNode);
	}
	@Override
	public Object eval() {
		Object lastStmt = new IntegerNode(0);
		if (statements != null && statements.size() > 0) {
			boolean returnFound = false;
			for (Node stmt: statements) {
				lastStmt = stmt.eval();
				if (!returnFound) {				
					if (lastStmt != null) {
						if (lastStmt.toString().equals("return")) {							
							returnFound = true;
						} else if (lastStmt.toString().equals("exit")) {
							break; // exit does not returns anything.
						}
					}
				} else {
					break;
				}
			}
		}
		return lastStmt;
	}	
}