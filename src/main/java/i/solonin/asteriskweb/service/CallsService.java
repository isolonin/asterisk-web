package i.solonin.asteriskweb.service;

import i.solonin.asteriskweb.model.CDR;
import i.solonin.asteriskweb.model.filter.CallsFilter;
import org.springframework.data.domain.Page;

public interface CallsService {
    Page<CDR> findAll(CallsFilter filter, String order, boolean asc, int page);

    byte[] getRecord(String fileName);
}
