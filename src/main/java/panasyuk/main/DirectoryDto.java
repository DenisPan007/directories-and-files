package panasyuk.main;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class DirectoryDto {

    private String addingDate;
    private String path;
    private String innerFilesSize;
    private String innerFilesCount;
    private String innerDirCount;

}
