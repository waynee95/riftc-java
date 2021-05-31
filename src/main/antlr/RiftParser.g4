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
   | typeId
   | typeId ('(' pattern ')')?
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
   'type' ID '=' ty #TypeDecl

   // Variable declarations
   | vardec #VarDecl

   // Function declaration
   | 'fn' ID '(' tyfields ')' (':' typeId)? '=' exprs #FuncDecl

   // Extern declaration
   | 'extern' ID '(' tyfields ')' (':' typeId)? #ExternDecl
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
