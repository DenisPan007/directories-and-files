package panasyuk.util;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Actually fileList may contain both files and directories entries
 */
@Builder
@Setter
@Getter
public class DirectoryInfo {

    private String path;
    private LocalDateTime addingDate;
    private List<FileInfoDto> fileList;
}
