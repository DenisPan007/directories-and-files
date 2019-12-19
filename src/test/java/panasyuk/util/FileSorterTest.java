package panasyuk.util;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import panasyuk.main.FileDto;

public class FileSorterTest {

    @Test
    public void compare() {
        FileDto file1 = FileDto.builder().name("innerTemp").build();
        FileDto file2 = FileDto.builder().name("X-FILES").build();
        FileDto file3 = FileDto.builder().name("f.txt").size("5").build();
        FileDto file4 = FileDto.builder().name("F1.txt").size("5").build();
        FileDto file5 = FileDto.builder().name("f4_99.JPG").size("5").build();
        FileDto file6 = FileDto.builder().name("F4_00127.pdf").size("5").build();
        FileDto file7 = FileDto.builder().name("f008.doc").size("5").build();
        FileDto file8 = FileDto.builder().name("function.cpp").size("5").build();
        List<FileDto> testStrings = Arrays.asList(file1, file3, file5, file8, file7, file2
                , file4, file6);
        List<FileDto> expectedAfterSorting = Arrays.asList(file1, file2, file3, file4, file5, file6
                , file7, file8);
        testStrings.sort(new FileComparator());
        assertEquals(expectedAfterSorting, testStrings);

    }
}