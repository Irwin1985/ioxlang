package com.iox.ast.func;

import java.util.List;
import com.iox.ast.Node;
import com.iox.ast.InlineFun;
public class SayNode extends InlineFun {
	private List<Node> block;
	/*
	 * Constructor
	 */
	public SayNode(List<Node> block) {
		this.block = block;
	}

	public Object eval() {
		Object output = new Integer(0);
		if (block != null && block.size() > 0) {
			for (Node expression: block) {
				output = expression.eval();
				System.out.print(output.toString() + " ");
			}
			System.out.println();
		}
		return output;
	}
}
