package com.iox.interpreter;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.iox.lexer.*;
import com.iox.parser.*;
import com.iox.util.Util;
import java.util.ArrayList;
import java.util.List;
import com.iox.ast.*;

public class Interpreter {
	/*
	 * Interprets source code.
	 */
	public void interpret(String source, boolean debug) {
		// Save starting time.
		long startTime = System.currentTimeMillis();
		
		Tokenizer tokenizer = new Tokenizer();
		List<Token> tokens = tokenizer.Tokenize(source);

		// load original tokens by default.
		Parser parser = new Parser(tokens);

		// start parsing main script
		parser.matchAndEat(TokenType.IOX);

		// Parse user-defined functions first and
		// create a new tokenizer without them.
		List<Node> funcListNode = new ArrayList<Node>();
		if (tokenizer.funCounter > 0) {
			funcListNode = parser.parseUserDefinedFunctions();
		}
		
		// Dump tokens if debug flag is set true.
		if (debug) {
			Util.prettyPrint(tokens);
		}
		
		// parse whole script.
		Node block = parser.block();
		
		// convert from Node to BlockNode 
		// to leverage the pushNode() method.
		BlockNode script = (BlockNode)block;

		// Insert user-defined function 
		if (funcListNode != null && funcListNode.size() > 0) {				
			for (Node funcNode: funcListNode) {
				script.pushNode(funcNode);
			}
		}
		
		// add inline functions in front of script.
		List<Node> inlineFuncBlock = Util.createInlineFunctions(parser);
		for (Node inlineNode: inlineFuncBlock) {
			script.pushNode(inlineNode);
		}

		// execute whole script
		script.eval();
		
		long finishedTime = System.currentTimeMillis();
		System.out.println();
		System.out.println("time ellapsed: " + (finishedTime - startTime)/1000.00);
	}
	/*
	 * Read from file
	 */
	public void readFile(String filePath) {
		try {			
			String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
			System.out.println(fileContent);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
	public static void main(String[] args) {
		try {			
			Interpreter interpreter = new Interpreter();		
			String filePath = System.getProperty("user.dir") + "\\script.iox";
			String source = new String(Files.readAllBytes(Paths.get(filePath)));
			interpreter.interpret(source, false);
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}
