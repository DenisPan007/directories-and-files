package panasyuk.util;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtil {

    public DirectoryInfoDto getDirectoryInfoDto(String pathStr) {
        File file = getFile(pathStr);

        List<FileInfoDto> fileInfoDtoList = Stream.of(file.listFiles())
                .map((fileItem) -> FileInfoDto.builder()
                        .path(fileItem.getPath())
                        .size(String.valueOf(fileItem.length()))
                        .build()
                )
                .collect(Collectors.toList());

        return DirectoryInfoDto.builder()
                .fileList(fileInfoDtoList)
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