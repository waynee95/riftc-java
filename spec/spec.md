# Rift Language Specification

Rift is derived from the [Tiger](https://www.lrde.epita.fr/~tiger//tiger.pdf)
language.

## Language Features

- Rift is a type-safe language
- Rift uses local type inference for local variables
- Rift is expression-focused. Everything has a return value. Side effects return unit `()`.
- Rift is a garbage-collected language

## Language Extensions

### Object-Orientation

TODO

Additional keywords: `class`, `extends`, `new`.

## Lexical Specifications

### Keywords

The following keywords cannot be used as identifiers:

`if`, `then`, `else`, `while`, `do`, `break`, `let`, `in`, `end`, `nil`, `fn`, `var`, `val`, `type`, `match`, `extern`, `true`, `false`

### Identifiers

Identifiers and keywords are case-sensitive. Identifiers must begin with a letter
and are followed by any combination of letters, digits or the underscore character (`_`).

### Comments

Only single-line comments denoted by `#` are allowed. A line beginning with `#`
indicates the remainder of the line is a comment.

### Literals

Integer literals may start with an optional sign `-`, followed by a sequence of digits.
Non-zero integer literals may not have leading zeros. Integers have 64-bit signed
values in the range -2^63 through 2^63 - 1, inclusive.

String literals are sequences of characters enclosed by double quotes (`"`).
String characters can be:

- Printable ASCII characters (ASCII codes between decial 32 and 126) with the exception of double quote (`"`) and backslash (`\`)
- The escape sequences `\"` for double quote, `\\` for backslash, `\t` for tab and `\n` for newline are supported.

No other characters or character sequences are allowed inside a string. Unclosed
strings must result in a lexical error.

Array literals are a comma-separated list of expressions surrounded by square
brackets `[ ..., ... ]`.

The keywords `true` and `false` are boolean literals.

The null reference literal is `nil`.

## Rift Grammar

The grammar is specified using Extended BNF.

```
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
```

Precedence of the `op` operators (high to low):

1. `*` `/`
2. `+` `-`
3. `>=` `<=` `=` `<>` `<` `>`

## Built-in Functions

Rift provides a small set of builtin functions to handle basic I/O operations,
basic type conversions and other system-level functionality. These functions are
in scope of every Rift program:

```
# Read one line from standard input, up to the next line or EOF
read_line(): string

# Check if standard input has reached EOF
eof(): bool

# Return the size of a string
length(s: string): i64

# Concatenate two strings
concat(a: string, b: string): string

# Print a string to standard output
print(s: string)

# Print an integer to standard output
print_int(n: i64)

# Return a random number in the range 0 to n-1
random(n: i64): i64

# Exit the program with given exit code
exit(status: i64)
```
