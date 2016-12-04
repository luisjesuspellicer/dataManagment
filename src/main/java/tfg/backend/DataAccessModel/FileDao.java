package tfg.backend.DataAccessModel;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import tfg.backend.DataModel.FileInfo;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Path;
import java.util.List;

@Transactional
public interface FileDao {


    public boolean saveFileInfo(FileInfo fileInfo, MultipartFile file);

    public FileInfo findFileInfo(String id);

    public void deleteFileInfo(String id);

    public List<FileInfo> findBySubject(Long id);

    // Load as a resource
    public Resource loadResource(String id);

    public Path loadPath(String filename);
    // Create the directory if not exists
    public void init();

    public File file(String filename);

}
