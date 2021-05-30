package me.waynee95.rift;

import me.waynee95.rift.ast.BuildAstVisitor;
import me.waynee95.rift.ast.Node;
import me.waynee95.rift.parse.RiftLexer;
import me.waynee95.rift.parse.RiftParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;

@Command(name = "riftc", version = "0.0.0",
        description = "A compiler for the Rift programming language.", sortOptions = false)
class Main implements Runnable {
    @Parameters(paramLabel = "FILE", description = "*.rift source file.")
    private String filePath;

    @Option(names = "--help", usageHelp = true, description = "Print this text.")
    private boolean showHelp = false;

    @Option(names = "--lex-trace", description = "Trace the result of the lexer.")
    boolean lexTrace = false;

    @Option(names = "--parse-trace", description = "Trace the result of the parser.")
    boolean parseTrace = false;

    @Option(names = "--parse", description = "Run the parser on the input file.")
    boolean parse = false;

    @Option(names = "--print-ast", description = "Print the AST of the input file.")
    boolean printAst = false;

    // TODO: --typecheck to run the type checker on the input file

    public static void main(String[] args) {
        new CommandLine(new Main()).execute(args);
    }

    @Override
    public void run() {
        if (filePath == null) {
            // TODO: Is there a _nicer_ way for this?
            new CommandLine(new Main()).usage(System.out);
        }

        try {
            CharStream input = CharStreams.fromFileName(filePath);

            if (lexTrace) {
                RiftLexer lexer = new RiftLexer(input);
                Token token = lexer.nextToken();
                printToken(token);
                while (token.getType() != Token.EOF) {
                    token = lexer.nextToken();
                    printToken(token);
                }
            }

            RiftParser parser = new RiftParser(new CommonTokenStream(new RiftLexer(input)));
            RiftParser.ProgramContext prog = parser.program();

            Node root = new BuildAstVisitor().visitProgram(prog);

            if (printAst) {
                System.out.println(root);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printToken(Token token) {
        System.out.println(
                token.getLine() + ":" + token.getCharPositionInLine() + " " + token.getText());
    }
}
