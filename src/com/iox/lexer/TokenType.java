package com.iox.lexer;
/*
 * TokenType handles all token types needed
 * by the language.
 */
public enum TokenType {	
	// Arithmetic types
	ADD,
	SUB,
	MUL,
	DIV,
	POW,
	
	// Relational operators types
	LESS,
	GREATER,
	LESSEQUAL,
	GREATEREQUAL,
	EQUAL,
	NOTEQUAL,
	
	// Boolean operators types
	AND,
	OR,
	NOT,
	
	// Atomic types
	
	// Aritmetics atomics types
	INTEGER,
	DOUBLE,
	
	// Boolean atomics types
	TRUE,
	FALSE,
	
	// String atomic type
	STRING,
	
	// Special atomics types
	IDENTIFIER,
	KEYWORD,
	ASSIGNMENT,
	LPAREN,
	RPAREN,
	LBRACKET,
	RBRACKET,
	COMMA,
	LCURLEY,
	RCURLEY,
	
	// End of File
	EOF,
	IOX,
	END,
	
	// keyword and Statements
	WHILE,
	EXIT,
	
	// if stmt
	IF,
	ELSE,
	ELIF,
	
	// do case stmt
	DO,
	DOCASE,
	CASE,
	DEFAULT,
	UNTIL,
	
	// for
	FOR,
	TO,
	STEP,
	
	// functions
	FUNC,
	RETURN,
	
	// inline functions
	SAY,
}
