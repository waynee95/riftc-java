# Rift Language Specification

Rift is derived from [Tiger](https://www.lrde.epita.fr/~tiger//tiger.pdf) and [Roost](https://cs.wellesley.edu/~cs301/s19/project/roost-lang.pdf)
with influences from ML, Ocaml, Kotlin, Scala and Rust. The scope of the language is small by design.
The language may be expanded with language extensions in the future.

## Language Features

- **Strong Static Type System**: Rift is a type-safe language. Rift has support for `record` and `enum` types. There is no implicit type conversion.
- **Local Type Inference**: Rift uses local type inference to determine the types of local variables without requiring explicit type annotations.
- **Expression-Focused**: Rift is an expression-focused language; it emphasizes evaluating for result values instead of side effects. Side effects are still supported.
- **Functions as values**: Rift supports basic higher-order functions. Functions can be passed, stored and returned from functions.
- **Immutable values**: Mutable values have to explicitly declared to be mutable. There are two keywords to declare variables.
- **Dynamic Allocation and Garbage Collection**: A garbage collector not only prevents memory leaks but more importantly it ensures memory safety.
- **Null safety and memory safety**: Rift has no nullable types. Absence of values has to be made explicit using enumeration types. Rift uses runtime checks to prevent errors like array bounds violation and division by zero.

## Lexical Specifications

### Keywords

The following keywords cannot be used as identifiers:

`if`, `then`, `else`, `while`, `do`, `break`, `let`, `in`, `end`, `fn`, `var`, `val`, `type`, `match`, `with`, `extern`, `true`, `false`

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

## Rift Grammar

Grammar can be found [here](../src/main/antlr/RiftParser.g4).

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
