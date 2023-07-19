package i.solonin.asteriskweb.repository;

import i.solonin.asteriskweb.model.Identify;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IdentifyRepository extends CrudRepository<Identify, String> {
    Optional<Identify> findById(String id);
}
