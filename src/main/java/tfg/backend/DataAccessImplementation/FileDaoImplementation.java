/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: Implementation of methods for interact with model Fichero
 */
package tfg.backend.DataAccessImplementation;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import tfg.backend.DataAccessModel.FileDao;
import tfg.backend.DataModel.FileInfo;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class FileDaoImplementation implements FileDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;


    @Value("${location}")
    private String location;
    @Value("${location_security}")
    private String location_security;

    @Override
    public boolean saveFileInfo(FileInfo fileInfo, MultipartFile file) {
        Path loc = Paths.get(location);
        init();
        if (findFileInfo(fileInfo.getNameFile()) != null) {
            return false;
        } else {
            try {
                hibernateTemplate.save(fileInfo);
                Files.copy(file.getInputStream(), loc.resolve(file.getOriginalFilename()));
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;

            }
        }
    }

    @Override
    public FileInfo findFileInfo(String id) {
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        DetachedCriteria query = DetachedCriteria.forClass(FileInfo.class)
                .add(Property.forName("nameFile").eq(id));
        List<?> list = hibernateTemplate.findByCriteria(query);
        if (list.size() > 0) {
            return (FileInfo) list.get(0);
        }
        return null;
    }


    @Override
    public void deleteFileInfo(String id) {
        FileInfo aux = findFileInfo(id);
        if (aux != null) {
            Path loc = loadPath(id);
            hibernateTemplate.delete(aux);
            hibernateTemplate.flush();
            FileSystemUtils.deleteRecursively(loc.toFile());

        }
    }


    @Override
    public List<FileInfo> findBySubject(Long id) {
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        DetachedCriteria query = DetachedCriteria.forClass(FileInfo.class)
                .add(Property.forName("numSubject.numSubject").eq(id));
        List<?> list = hibernateTemplate.findByCriteria(query);
        if(list.size() > 0){
            return (List<FileInfo>)list;
        }else{
            return null;
        }

    }
    @Override
    public Path loadPath(String filename) {
        Path loc = Paths.get(location);
        return loc.resolve(filename);
    }

    @Override
    public Resource loadResource(String id) {
        try {
            Path file = loadPath(id);
            Resource aux = new UrlResource(file.toUri());
            if (aux.exists() || aux.isReadable()) {
                return aux;
            } else {
                return null;

            }
        } catch (Exception e) {
            return null;
        }

    }


    @Override
    public void init() {
        try {

            if (!Files.isDirectory(Paths.get(location))) {
                Files.createDirectory(Paths.get(location));
            }

        } catch (IOException e) {
            System.out.println("Could not initialize storage");
        }
    }

    @Override
    public File file(String filename) {
        try {
            Path file = loadPath(filename);
            File fil = new File(file.toUri());
            Resource aux = new UrlResource(file.toUri());
            if (aux.exists() || aux.isReadable()) {
                return fil;
            } else {
                return null;

            }
        } catch (Exception e) {
            return null;
        }
    }
}
