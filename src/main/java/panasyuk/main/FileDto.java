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
}
