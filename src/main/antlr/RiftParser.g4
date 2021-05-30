parser grammar RiftParser;

@header {
package me.waynee95.rift.parse;
}

options { tokenVocab=RiftLexer; }

program
    : expr
    | decls
    ;

expr
    // Literals
    : literal

    // Array and Record creation
    | '[' exprs ']'
    | ID '{' (ID '=' expr (',' ID '=' expr)*)? '}'

    // Locations
    | lvalue

    // Function call
    | ID '(' (expr (',' expr)*)? ')'

    // Method call
    | lvalue '.' ID '(' (expr (',' expr)*)? ')'

    // Operations
    | '-' expr
    | expr op expr
    | '(' expr ')'

    // Assignment
    | lvalue '=' expr

    // Control structures
    | 'if' expr 'then' expr ('else' expr)?
    | 'while' expr 'do' expr
    | 'break'
    | 'let' decls 'in' expr 'end'
    | 'match' expr 'with' '|' pattern '=>' expr (',' pattern '=>' expr)* ('else' '=>' expr)?
    ;

literal
    : INT_LIT
    | STRING_LIT
    | BOOL_LIT
    | NULL_LIT
    ;

pattern
    : literal
    | typeId
    | typeId ('(' pattern ')')?
    ;

lvalue
    : ID
    // Field access
    | lvalue '.' ID
    // Array access
    | lvalue '[' expr ']'
    ;

exprs
    : (expr (';' expr)*)?
    ;

decls
    : (decl)*
    ;

decl
    :
    // Type declaration
    'type' ID '=' ty
    // Variable declarations
    | vardec
    // Function declaration
    | 'fn' ID '(' tyfields ')' (':' typeId)? '=' expr
    // Extern declaration
    | 'extern' ID '(' tyfields ')' (':' typeId)?
    ;

vardec
    : ( 'var' | 'val' ) ID (':' typeId)? '=' expr
    ;

ty
    :
    // Type Alias
    typeId
    // Record type
    | '{' tyfields '}'
    // Array type
    | '[' typeId ']'
    ;

tyfields
    : (ID ':' typeId (',' ID ':' typeId)*)?
    ;

typeId
    : ID
    ;

op
    : '+' | '-' | '*' | '/' | '%' | '&&' | '||' | '<' | '<=' | '>' | '>=' | '!='
    ;
