package panasyuk.main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import panasyuk.persistence.FileEntity;
import panasyuk.persistence.FileRepository;
import panasyuk.util.DirectoryInfo;
import panasyuk.util.DirectoryUtil;

@Service
public class DirectoryService {

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
                        .build())
                .collect(Collectors.toSet());
        setAddingDateToAlreadyAddedDirectories(fileEntities, directory.getAddingDate());

        fileEntities.forEach(fileRepository::save);
    }

    public List<DirectoryDto> getDirectories() {
        List<FileEntity> directoryList = fileRepository.findBySizeIsNullAndAddingDateIsNotNull();

        List<DirectoryDto> directoryDtoList = directoryList.stream()
                .map((directory) -> {
                    List<FileEntity> innerFilesAndDirList = fileRepository.findByParentFile(directory);
                    return DirectoryDto.builder()
                            .addingDate(
                                    directory.getAddingDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")))
                            .path(directory.getPath())
                            .innerDirCount(String.valueOf(calculateInnerDirAmount(innerFilesAndDirList)))
                            .innerFilesCount(String.valueOf(calculateInnerFilesAmount(innerFilesAndDirList)))
                            .innerFilesSize(String.valueOf(getFileSize(calculateInnerFilesSize(innerFilesAndDirList))))
                            .build();
                })
                .collect(Collectors.toList());
        return directoryDtoList;
    }

    public static boolean isDirectory(FileEntity fileEntity) {
        return fileEntity.getSize() == null;
    }

    public static boolean isFile(FileEntity fileEntity) {
        return fileEntity.getSize() != null;
    }

    public static String getFileSize(long sizeInBytes) {
        if (sizeInBytes < 1024) {
            return sizeInBytes + " B";
        } else if (sizeInBytes < 1024 * 1024) {
            return String.format("%.1f Kb", sizeInBytes / 1024.0);
        } else {
            return String.format("%.1f Mb", sizeInBytes / (1024 * 1024.0));
        }
    }

    private long calculateInnerDirAmount(List<FileEntity> directoryList) {
        return directoryList.stream()
                .filter(DirectoryService::isDirectory)
                .count();
    }

    private long calculateInnerFilesAmount(List<FileEntity> directoryList) {
        return directoryList.stream()
                .filter(DirectoryService::isFile)
                .count();
    }

    private long calculateInnerFilesSize(List<FileEntity> directoryList) {
        return directoryList.stream()
                .filter(DirectoryService::isFile)
                .mapToLong(FileEntity::getSize)
                .sum();
    }

    /**
     * The addingDate indicates that directory was added by user
     * This method prevents the loss of it because of saving directory in higher hierarchy level
     */
    private void setAddingDateToAlreadyAddedDirectories(Set<FileEntity> fileEntities, LocalDateTime addingDate) {
        List<String> alreadyAddedDirectoryIdList = fileRepository
                .findBySizeIsNullAndAddingDateIsNotNull()
                .stream()
                .map(FileEntity::getPath)
                .collect(Collectors.toList());

        for (FileEntity fileEntity : fileEntities) {
            if (alreadyAddedDirectoryIdList.contains(fileEntity.getPath())) {
                fileEntity.setAddingDate(addingDate);
            }
        }
    }
}
