package i.solonin.asteriskweb.service;

import i.solonin.asteriskweb.model.EndPoint;
import i.solonin.asteriskweb.model.filter.EndPointFilter;
import org.springframework.data.domain.Page;

public interface EndPointService {
    Page<EndPoint> findAll(EndPointFilter filter, String order, boolean asc, int page);

    EndPoint findById(String id);

    void save(EndPoint endPoint);

    void delete(EndPoint endPoint);
}
