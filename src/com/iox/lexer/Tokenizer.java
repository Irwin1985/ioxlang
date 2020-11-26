package com.iox.lexer;
/*
 * Tokenizer will split the source code into relational utils called tokens.
 * all tokens will be saved in a List<Token> using the custom Token class.
 */
import java.util.List;

import com.iox.util.Util;

import java.util.ArrayList;

public class Tokenizer {
	private int lineNumber = 0;
	private int columnNumber = 0;
	private String source;
	public int funCounter = 0; // function counter for Ahead of Time Function parsing.
	private int charCounter = 0;
	/**
	 * @param source
	 * @return
	 */
	public List<Token> Tokenize(String source){
		
		List<Token> tokens = new ArrayList<Token>();
		TokenState state = TokenState.DEFAULT;
		String tokenStr = "";
		this.source = source + " ";
		boolean isDouble = false;
		lineNumber = 1;
		columnNumber = 0;
		for (int index = 0; index < this.source.length(); index++) {
			char chr = this.source.charAt(index);
			columnNumber++;
			switch(state) {
			case DEFAULT:
				if (Character.isDigit(chr)) { // Number
					state = TokenState.NUMBER;
					tokenStr += chr;
				} else if (chr == '"') { // String
					state = TokenState.STRING;
				} else if (chr == '\'') { // Character
					state = TokenState.CHAR;
				} else if (Util.isAlpha(chr)) { // Identifier
					state = TokenState.WORD;
					tokenStr += chr;
				} else if (chr == '#') {
					state = TokenState.COMMENT;
				} else { // Special characters
					TokenType type = null;
					String value = String.valueOf(chr);
					if (chr == '+') {
						type = TokenType.ADD;
					} else if (chr == '-') {
						type = TokenType.SUB;
					} else if (chr == '*') {
						type = TokenType.MUL;
					} else if (chr == '/') {
						type = TokenType.DIV;
					} else if (chr == '(') {
						type = TokenType.LPAREN;
					} else if (chr == ')') {
						type = TokenType.RPAREN;
					} else if (chr == '[') {
						type = TokenType.LBRACKET;
					} else if (chr == ']') {
						type = TokenType.RBRACKET;
					} else if (chr == '{') {
						type = TokenType.LCURLEY;
					} else if (chr == '}') {
						type = TokenType.RCURLEY;
					} else if (chr == '?') {
						type = TokenType.SAY;
					} else if (chr == '<') {
						type = TokenType.LESS;
						if (peek(index + 1) == '=') {
							index++; // eat the next '=' character.
							type = TokenType.LESSEQUAL;
							value += "=";
						}
					} else if (chr == '>') {
						type = TokenType.GREATER;
						if (peek(index + 1) == '=') {
							index++;
							type = TokenType.GREATEREQUAL;
							value += "=";
						}
					} else if (chr == '=') {
						type = TokenType.ASSIGNMENT;
						if (peek(index + 1) == '=') {
							index++;
							type = TokenType.EQUAL;
							value += "=";
						}
					} else if (chr == '!') {
						type = TokenType.NOT;
						if (peek(index + 1) == '=') {
							index++;
							type = TokenType.NOTEQUAL;
							value += "=";
						}
					} else if (chr == '\r') {
						if (System.getProperty("os.name").contains("indow")){
							if (peek(index + 1) == '\n') {
								index++; // Skip Line Feed
							} else {
								System.out.println(String.format("Line Feed character expected at %d:%d", lineNumber, columnNumber));
								System.exit(-1);								
							}
						}
						lineNumber++;
						columnNumber = 0;
					} else if (chr == ',') {
						type = TokenType.COMMA;
					} else if (chr != ' ' && chr != '\t'){
						System.out.println(String.format("unknown character '%c' at %d:%d", chr, lineNumber, columnNumber));
						System.exit(0);
					}
					if (type != null) {						
						tokens.add(new Token(type, value, lineNumber, columnNumber));
					}
				}
				break;
			case NUMBER:
				if (Character.isDigit(chr) || chr == '.') {
					tokenStr += String.valueOf(chr);
					if (chr == '.' && !isDouble) {						
						isDouble = true;
					}
				} else {
					state = TokenState.DEFAULT;
					index--; // push back the current char.
					columnNumber--; // push back the column number. 
					
					TokenType type = isDouble ? TokenType.DOUBLE: TokenType.INTEGER;
					
					tokens.add(new Token(type, tokenStr, lineNumber, columnNumber));
					tokenStr = "";
					isDouble = false;
				}
				break;
			case STRING:
				if (chr == '\\') {
					char nextChar = peek(index + 1);
					if (nextChar == 't') {
						tokenStr += '\t';
						index++; // advance backslash '\'
					} else if (nextChar == 'r') {
						tokenStr += '\r';
						index++;
					} else if (nextChar == 'n') {
						tokenStr += '\n';
						index++;
					} else if (nextChar == '\'') {
						tokenStr += '\'';
						index++;
					} else if (nextChar == '\"') {
						tokenStr += '\"';
						index++;
					} else if (nextChar == '\\') {
						tokenStr += '\\';
						index++;
					} else {
						System.out.println("error: illegal escape character '\\" + nextChar + "' at " + lineNumber + ":" + columnNumber);
						System.exit(-1);
						//tokenStr += "\\" + nextChar;
						//index++;
					}
				} else if (chr == '"') {
					state = TokenState.DEFAULT;
					tokens.add(new Token(TokenType.STRING, tokenStr, lineNumber, columnNumber));
					tokenStr = "";
				} else {
					tokenStr += chr;
				}
				break;
			case CHAR:
				if (chr == '\'') {
					state = TokenState.DEFAULT;
					tokens.add(new Token(TokenType.STRING, tokenStr, lineNumber, columnNumber));
					tokenStr = "";
					charCounter = 0;
				} else {
					tokenStr += chr;
					charCounter++;
					if (charCounter > 1) {
						System.out.println(String.format("Error: unclosed character illegal at %d:%d", lineNumber, columnNumber));
						System.exit(-1);
					}
				}
				break;
			case WORD:
				if (Character.isDigit(chr) || Util.isAlpha(chr)) {
					tokenStr += chr;
				} else {
					state = TokenState.DEFAULT;
					index--;
					columnNumber--;
					TokenType type = Util.getTokenType(tokenStr, this);

					tokens.add(new Token(type, tokenStr, lineNumber, columnNumber));
					tokenStr = "";
				}
				break;
			case COMMENT:
				if (chr == '\n') {
					state = TokenState.DEFAULT;
					lineNumber++;
					columnNumber = 0;
				}
				break;
			default:
				break;
			}
		}
		return tokens;
	}
	/*
	 * Peek: checks the next character.
	 */
	public char peek(int index) {
		if (index >= this.source.length()) {
			System.out.println("Unexpected End Of File.");
			System.exit(0);
		}
		return this.source.charAt(index);
	}
}
