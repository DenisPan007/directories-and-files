package panasyuk.main;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FileDto {
    private String name;
    private String size;

    public boolean isDirectory() {
        try {
            Long.parseLong(size);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }
}
