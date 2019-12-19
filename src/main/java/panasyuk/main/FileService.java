package panasyuk.main;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import panasyuk.file.FileEntity;
import panasyuk.file.FileRepository;
import panasyuk.util.FileComparator;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public List<FileDto> getFiles(String path) {
        FileEntity directory = FileEntity.builder()
                .path(path)
                .build();
        List<FileEntity> fileEntities = fileRepository.findByParentFile(directory);

        List<FileDto> fileDtoList = fileEntities.stream()
                .map(fileEntity -> FileDto.builder()
                        .size(getSize(fileEntity))
                        .name(getFileName(fileEntity.getPath(), path))
                        .build()
                )
                .collect(Collectors.toList());
        fileDtoList.sort(new FileComparator());
        return fileDtoList;
    }

    private String getSize(FileEntity fileEntity) {
        return DirectoryService.isFile(fileEntity) ? DirectoryService.getFileSize(fileEntity.getSize()) : "&ltDIR&gt";
    }

    private String getFileName(String filePath, String parentPath) {

        return filePath.replace(parentPath + '\\', "");
    }

}
