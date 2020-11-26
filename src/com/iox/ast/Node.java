package com.iox.ast;
/*
 * Abstract Node class for all object constructions.
 */
import com.iox.lexer.Token;
public abstract class Node {
	private Token tokenPosition;
	
	public Node() {}
	
	public Node(Token tokenPosition) {
		this.tokenPosition = tokenPosition;
	}
	/*
	 * Every subclass must to implement this magic method.
	 */
	public abstract Object eval();
	/*
	 * getTokenPosition
	 */
	public String getTokenPosition() {
		return String.format("%d", tokenPosition.lineNumber);
	}
}
