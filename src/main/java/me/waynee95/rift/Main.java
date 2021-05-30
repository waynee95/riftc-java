package me.waynee95.rift;

import java.io.IOException;

import me.waynee95.rift.parse.RiftLexer;
import me.waynee95.rift.parse.RiftParser;
import org.antlr.v4.runtime.*;

class Main {
    public static void main(String[] args) {
        try {
            CharStream input = CharStreams.fromFileName(args[0]);
            RiftLexer lexer = new RiftLexer(input);

            Token token = lexer.nextToken();
            printToken(token);
            while (token.getType() != Token.EOF) {
                token = lexer.nextToken();
                printToken(token);
            }

            RiftParser parser = new RiftParser(new CommonTokenStream(lexer));
            parser.program();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printToken(Token token) {
        System.out.println(
                token.getLine() + ":" + token.getCharPositionInLine() + " " + token.getText());
    }
}
