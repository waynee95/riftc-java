# Rift

> a project to learn how to write a compiler

## Progress

- [x] Lexer
- [x] Parser
- [ ] AST
- [ ] Type Checking
- [ ] TAC Gen
- [ ] Backend

## Build

```bash
$ java -version
$ make
$ ./riftc --help
```

## Usage

```bash
$ ./riftc [options] FILE
```

### Options

|     Option      |            Description            |
| :-------------: | :-------------------------------: |
|    `--help`     |         Print this text.          |
|  `--lex-trace`  |  Trace the result of the lexer.   |
| `--parse-trace` |  Trace the result of the parser.  |
|    `--parse`    | Run the parser on the input file. |
