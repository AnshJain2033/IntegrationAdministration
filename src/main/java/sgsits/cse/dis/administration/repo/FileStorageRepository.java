package sgsits.cse.dis.administration.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sgsits.cse.dis.administration.models.FileStorage;

public interface FileStorageRepository extends JpaRepository<FileStorage,String> {

}
