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
   | left=expr op=('*' | '/' | '%') right=expr #Binary
   | left=expr op=('+' | '-') right=expr #Binary
   | left=expr op=('<' | '<=' | '>' | '>=') right=expr #Binary
   | left=expr op=('==' | '!=') right=expr #Binary
   | left=expr op='&&' right=expr #Binary
   | left=expr op='||' right=expr #Binary
   | '(' expr ')' #Paren

   // Assignment
   | lvalue '=' expr #Assign

   // Control structures
   | 'if' cond=expr 'then' trueBranch=expr ('else' falseBranch=expr)? #If
   | 'while' cond=expr 'do' exprs #While
   | 'break' #Break
   | 'let' (decl (decl)*)? 'in' exprs 'end' #Let
   | 'match' expr 'with' '|' pattern '=>' exprs (',' pattern '=>' exprs)* ('else' '=>' exprs)? #Match
   ;

literal
   : INT_LIT #IntLit
   | STRING_LIT #StringLit
   | BOOL_LIT #BoolLit
   ;

pattern
   : literal
   | TYPE_ID
   | TYPE_ID ('(' pattern ')')?
   ;

lvalue
   : ID #Name
   | lvalue '.' ID #FieldAccess
   | lvalue '[' expr ']' #Index
   ;

exprs
   : (expr (';' expr)*)?
   ;

decl
   :
   // Type declaration
   'type' TYPE_ID '=' typedec #TypeDecl

   // Variable declarations
   | vardec #VarDecl

   // Function declaration
   | 'fn' ID '(' typefields ')' (':' type)? '=' exprs #FuncDecl

   // Extern declaration
   | 'extern' ID '(' typefields ')' (':' type)? #ExternDecl
   ;

vardec
   : ('var' | 'val') ID (':' type)? '=' expr
   ;

typedec
    // Record Type
    : '{' typefields '}'

    // Enum Type
    | TYPE_ID ('(' type ')')? ('|' TYPE_ID ('(' type ')')?)*
    ;

type
   :
   // Type Alias
   TYPE_ID

   // Array type
   | '[' type ']'

   // TODO: Func Type
   // '(' ( type (',' type)* )? '->' type
   ;

typefields
   : (ID ':' type (',' ID ':' type)*)?
   ;
