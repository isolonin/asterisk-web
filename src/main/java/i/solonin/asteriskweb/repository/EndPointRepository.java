package i.solonin.asteriskweb.repository;

import i.solonin.asteriskweb.model.EndPoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface EndPointRepository extends ListPagingAndSortingRepository<EndPoint, String> {
    Page<EndPoint> findAll(Pageable pageable);

    Page<EndPoint> findByIdLikeOrMsisdnLike(String id, String msisdn, Pageable pageable);

    EndPoint findById(String id);

    void save(EndPoint endPoint);

    void delete(EndPoint endPoint);
}
