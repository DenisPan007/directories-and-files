package panasyuk.file;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {

    /**
     * Search added directories (size is null)
     */
    List<FileEntity> findBySizeIsNullAndAddingDateIsNotNull();

    /**
     * Search inner files and directories
     */
    List<FileEntity> findByParentFile(FileEntity parentFile);
}
