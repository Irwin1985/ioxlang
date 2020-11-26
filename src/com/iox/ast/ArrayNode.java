package com.iox.ast;
import java.util.ArrayList;
import com.iox.lexer.Token;
public class ArrayNode extends Node {
	private Node variableNode;
	private Node index;
	/*
	 * Constructor.
	 */
	public ArrayNode(Node variableNode, Node index, Token tokenPosition) {
		super(tokenPosition);
		this.variableNode = variableNode;
		this.index = index;
	}
	@Override
	public Object eval() {
		@SuppressWarnings("unchecked")
		ArrayList<Node> elements = (ArrayList<Node>)variableNode.eval();
		Node arrayValue = new IntegerNode(0);
		int arrayIndex = Integer.valueOf(index.eval().toString());
		arrayIndex = (arrayIndex > 0) ? arrayIndex - 1 : arrayIndex;
		int arraySize = elements.size() - 1;
		if (elements != null) {
			if (arrayIndex >= 0 && arrayIndex <= arraySize) {				
				arrayValue = elements.get(arrayIndex);
			} else {
				System.out.println("Array index out of bounds at line " + getTokenPosition());
				System.exit(-1);
			}
		} 
		return arrayValue.eval();
	}
}
