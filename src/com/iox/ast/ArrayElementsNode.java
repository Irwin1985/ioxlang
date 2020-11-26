package com.iox.ast;
import java.util.List;
public class ArrayElementsNode extends Node{
	private List<Node> elements;
	/*
	 * Constructor.
	 */
	public ArrayElementsNode(List<Node> elements) {
		this.elements = elements;
	}
	@Override
	public Object eval() {
		return elements;
	}
}
