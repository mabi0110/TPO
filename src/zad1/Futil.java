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

        Charset csIn = Charset.forName("Cp1250");
        Charset csOut = Charset.forName("UTF-8");

        List<Path> plist = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(startPath)) {
            //paths.forEach(p -> System.out.println(p));
            plist = paths.filter(Files::isRegularFile).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (FileChannel fcOut = FileChannel.open(resultPath, CREATE, TRUNCATE_EXISTING, WRITE)) {
            for (Path p: plist) {
                try (FileChannel fcIn = FileChannel.open(p)) {
                    ByteBuffer bb = ByteBuffer.allocateDirect((int) fcIn.size());
                    fcIn.read(bb);
                    bb.flip();
                    CharBuffer cb = csIn.decode(bb);
                    fcOut.write(csOut.encode(cb));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
