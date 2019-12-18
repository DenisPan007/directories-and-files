package panasyuk.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Transactional
public class DirectoryController {

    @Autowired
    private DirectoryService directoryService;

    //todo add validation
    @RequestMapping("/directory/save")
    @PostMapping
    public String save(Model model, @ModelAttribute PathDto pathDto) {
        String path = pathDto.getPath();
        directoryService.saveDirectory(path);
        return getDirectories(model);
    }

    @RequestMapping("/")
    public String getDirectories(Model model) {
        List<DirectoryDto> directories = directoryService.getDirectories();
        model.addAttribute("directories", directories);
        return "index";
    }

    @RequestMapping("/files/{directoryId}")
    @GetMapping
    public String getFiles(@PathVariable String directoryId) {
        return "The files was gotten for directory with id: " + directoryId;
    }

}
