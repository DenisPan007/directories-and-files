package panasyuk.main;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import panasyuk.file.FileEntity;
import panasyuk.file.FileRepository;
import panasyuk.util.DirectoryInfo;
import panasyuk.util.DirectoryUtil;

@Service
public class DirectoryService {

    //todo DI by spring
    private DirectoryUtil directoryUtil = new DirectoryUtil();
    @Autowired
    private FileRepository fileRepository;

    public void saveDirectory(String path){
        DirectoryInfo directory = directoryUtil.getDirectoryInfoDto(path);
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

    public List<DirectoryDto> getDirectories() {
        List<FileEntity> directoryList = fileRepository.findBySizeIsNull();

        List<DirectoryDto> directoryDtoList = directoryList.stream()
                .map((directory) -> {
                    List<FileEntity> innerFilesAndDirList = fileRepository.findByParentFile(directory);
                    return DirectoryDto.builder()
                            .addingDate(directory.getAddingDate().toString())
                            .path(directory.getPath())
                            .innerDirCount(String.valueOf(calculateInnerDirAmount(innerFilesAndDirList)))
                            .innerFilesCount(String.valueOf(calculateInnerFilesAmount(innerFilesAndDirList)))
                            .innerFilesSize(String.valueOf(calculateInnerFilesSize(innerFilesAndDirList)))
                            .build();
                })
                .collect(Collectors.toList());
        return directoryDtoList;
    }

    private long calculateInnerDirAmount(List<FileEntity> directoryList) {
        return directoryList.stream()
                .filter(this::isDirectory)
                .count();
    }

    private long calculateInnerFilesAmount(List<FileEntity> directoryList) {
        return directoryList.stream()
                .filter(this::isFile)
                .count();
    }

    private long calculateInnerFilesSize(List<FileEntity> directoryList) {
        return directoryList.stream()
                .filter(this::isFile)
                .mapToLong(FileEntity::getSize)
                .sum();
    }

    private boolean isDirectory(FileEntity fileEntity) {
        return fileEntity.getSize() == null;
    }

    private boolean isFile(FileEntity fileEntity) {
        return fileEntity.getSize() != null;
    }
}
