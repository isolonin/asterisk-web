package i.solonin.asteriskweb.repository;

import i.solonin.asteriskweb.model.Auth;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuthRepository extends CrudRepository<Auth, String> {
    Optional<Auth> findById(String id);
}
