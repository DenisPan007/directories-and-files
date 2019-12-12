package panasyuk.main;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DirectoryController {

    @RequestMapping("/directory/save")
    @PostMapping
    public String save(@ModelAttribute PathDto pathDto) {
        return "The entered path was: " + pathDto.getPath();
    }

    @RequestMapping("/files/{directoryId}")
    @GetMapping
    public String getFiles(@PathVariable String directoryId) {
        return "The files was gotten for directory with id: " + directoryId;
    }

}
