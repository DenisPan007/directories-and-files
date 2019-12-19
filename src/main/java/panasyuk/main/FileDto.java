package panasyuk.main;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import panasyuk.util.Consts;

@Getter
@Setter
@Builder
public class FileDto {

    private String name;
    private String size;

    public boolean isDirectory() {
        return size.equals(Consts.DIR_LABEL);
    }
}
