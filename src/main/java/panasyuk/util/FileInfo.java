package panasyuk.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class FileInfo {

    private String path;
    //null if directory
    private Long size;

}
