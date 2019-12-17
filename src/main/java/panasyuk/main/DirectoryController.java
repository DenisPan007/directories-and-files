package panasyuk.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class DirectoryController {

    @Autowired
    private DirectoryService directoryService;

    //todo add validation
    @RequestMapping("/directory/save")
    @PostMapping
    public String save(@ModelAttribute PathDto pathDto) {
        String path = pathDto.getPath();
        directoryService.saveDirectory(path);
        return "directory was saved";
    }

    @RequestMapping("/files/{directoryId}")
    @GetMapping
    public String getFiles(@PathVariable String directoryId) {
        return "The files was gotten for directory with id: " + directoryId;
    }

}
