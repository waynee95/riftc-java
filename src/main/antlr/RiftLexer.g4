lexer grammar RiftLexer;

@header {
package me.waynee95.rift.parse;
}

// Keywords
IF:             'if';
THEN:           'then';
ELSE:           'else';
WHILE:          'while';
DO:             'do';
BREAK:          'break';
LET:            'let';
IN:             'in';
END:            'end';
FN:             'fn';
VAR:            'var';
VAL:            'val';
TYPE:           'type';
MATCH:          'match';
WITH:           'with';
EXTERN:         'extern';

// Literals
INT_LIT:        ( '0' | [1-9] ) [0-9]*;
STRING_LIT:     '"' (~["\\\r\n] | EscapeSequence)* '"';
BOOL_LIT:       'true' | 'false';

// Separators
LPAREN:         '(';
RPAREN:         ')';
LBRACE:         '[';
RBRACE:         ']';
LCURLY:         '{';
RCURLY:         '}';
SEMI_COLON:     ';';
COLON:          ':';
COMMA:          ',';
DOT:            '.';
BAR:            '|';
FAT_ARROW:      '=>';

// Operators
ADD:            '+';
SUB:            '-';
MULT:           '*';
DIV:            '/';
REM:            '%';
AND:            '&&';
OR:             '||';
ASSIGN:         '=';
LT:             '<';
LE:             '<=';
GT:             '>';
GE:             '>=';
EQ:             '==';
NOT_EQ:         '!=';
NOT:            '!';

// Whitespace and Comments
COMMENT:        '#' ~[\r\n]         -> skip;
WS:             [ \t\u000C\r\n]+    -> skip;

// Identifiers
TYPE_ID:        [A-Z] [A-Za-z0-9_]*;
ID:             [A-Za-z_] [A-Za-z0-9_]*;

fragment EscapeSequence
    : '\\' ["\tn\\]
    ;
