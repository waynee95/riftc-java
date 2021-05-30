parser grammar RiftParser;

@header
{
package me.waynee95.rift.parse;
}

options { tokenVocab = RiftLexer; }
program
   : expr
   ;

expr
   // Literals
   : literal #Lit

   // Array and Record creation
   | '[' (expr (',' expr)*)? ']' #Array
   | ID '{' (expr (',' expr)*)? '}' #Record

   // Locations
   | lvalue #Location

   // Function call
   | ID '(' (expr (',' expr)*)? ')' #FuncCall

   // Operations
   | op=('-' | '!') expr #Unary
   | left=expr op=binaryOp right=expr #Binary
   | '(' expr ')' #Paren

   // Assignment
   | lvalue '=' expr #Assign

   // Control structures
   | 'if' expr 'then' expr ('else' expr)? #If
   | 'while' expr 'do' exprs #While
   | 'break' #Break
   | 'let' decls 'in' exprs 'end' #Let
   | 'match' expr 'with' '|' pattern '=>' expr (',' pattern '=>' expr)* ('else' '=>' expr)? #Match
   ;

literal
   : INT_LIT #IntLit
   | STRING_LIT #StringLit
   | BOOL_LIT #BoolLit
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
   : ('var' | 'val') ID (':' typeId)? '=' expr
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

binaryOp
   : '+'
   | '-'
   | '*'
   | '/'
   | '%'
   | '&&'
   | '||'
   | '<'
   | '<='
   | '>'
   | '>='
   | '!='
   ;

