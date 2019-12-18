package panasyuk.file;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "file")
public class FileEntity {

    @Id
    @Column(name = "path")
    private String path;

    @Column(name = "adding_date")
    private LocalDateTime addingDate;

    @Column(name = "size")
    private String size;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "parent_path")
    private FileEntity parentFile;

    @OneToMany(mappedBy = "parentFile")
    private Set<FileEntity> childFileList;

}
