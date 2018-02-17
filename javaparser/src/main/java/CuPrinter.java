import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.FileInputStream;

public class CuPrinter {

    public static void main(String[] args) throws Exception {
        // creates an input stream for the file to be parse
        try (FileInputStream in = new FileInputStream("src/main/java/CuPrinter.java")) {

            // parse the file
            CompilationUnit cu = JavaParser.parse(in);

            // prints the resulting compilation unit to default system output
            System.out.println(cu.toString());

        }
    }
}