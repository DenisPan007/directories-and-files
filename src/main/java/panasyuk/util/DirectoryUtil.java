package panasyuk.util;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DirectoryUtil {

    public DirectoryInfo getDirectoryInfoDto(String pathStr) {
        File file = getFile(pathStr);
        File[] innerFilesList = file.listFiles();

        List<FileInfo> fileInfoList = Stream.of(innerFilesList)
                .map((fileItem) -> FileInfo.builder()
                        .path(fileItem.getPath())
                        .size(fileItem.isFile() ? fileItem.length() : null)
                        .build()
                )
                .collect(Collectors.toList());

        return DirectoryInfo.builder()
                .fileList(fileInfoList)
                .path(pathStr)
                .addingDate(LocalDateTime.now())
                .build();
    }

    private File getFile(String pathStr) {
        try {
            Path path = Paths.get(pathStr);
            File file = path.toFile();
            if (!file.exists()) {
                throw new FileNotFoundException("File not found for specified path");
            }
            return file;
        } catch (InvalidPathException e) {
            //todo change to validation exception
            throw new FileNotFoundException("Incorrect path");
        }
    }
}