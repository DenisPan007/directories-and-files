package panasyuk.main;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import panasyuk.file.FileEntity;
import panasyuk.file.FileRepository;
import panasyuk.util.DirectoryInfoDto;
import panasyuk.util.FileUtil;

@Service
public class DirectoryService {

    //todo DI by spring
    private FileUtil fileUtil = new FileUtil();
    @Autowired
    private FileRepository fileRepository;

    public void saveDirectory(String path){
        DirectoryInfoDto directory = fileUtil.getDirectoryInfoDto(path);
        FileEntity parentEntity = FileEntity.builder()
                .path(path)
                .addingDate(directory.getAddingDate())
                .build();
        Set<FileEntity> fileEntities = directory.getFileList().stream()
                .map((file)->FileEntity.builder()
                        .path(file.getPath())
                        .size(file.getSize())
                        .parentFile(parentEntity)
                        .addingDate(parentEntity.getAddingDate())
                        .build())
                .collect(Collectors.toSet());
        fileEntities.forEach(fileRepository::save);
    }

}
