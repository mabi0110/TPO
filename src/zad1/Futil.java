package zad1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.*;

public class Futil {
    public static void processDir(String dirName, String resultFileName) {
        Path startPath = Paths.get(dirName);
        Path resultPath = Paths.get(resultFileName);

        Charset charsetIn = Charset.forName("Cp1250");
        Charset charsetOut = Charset.forName("UTF-8");

        List<Path> pathsList = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(startPath)) {
            //paths.forEach(p -> System.out.println(p));
            pathsList = paths.filter(Files::isRegularFile).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (FileChannel fileChanelOut = FileChannel.open(resultPath, CREATE, TRUNCATE_EXISTING, WRITE)) {
            for (Path p: pathsList) {
                try (FileChannel fileChanelIn = FileChannel.open(p)) {
                    ByteBuffer byteBuffer = ByteBuffer.allocateDirect((int) fileChanelIn.size());
                    fileChanelIn.read(byteBuffer);
                    byteBuffer.flip();
                    CharBuffer charBuffer = charsetIn.decode(byteBuffer);
                    fileChanelOut.write(charsetOut.encode(charBuffer));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
