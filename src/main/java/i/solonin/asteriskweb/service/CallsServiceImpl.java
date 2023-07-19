package i.solonin.asteriskweb.service;

import i.solonin.asteriskweb.model.CDR;
import i.solonin.asteriskweb.model.filter.CallsFilter;
import i.solonin.asteriskweb.repository.CDRRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallsServiceImpl implements CallsService {
    @Value("${asteris.monitor}")
    private String path;

    private final CDRRepository cdrRepository;

    @Override
    public Page<CDR> findAll(CallsFilter filter, String order, boolean asc, int page) {
        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, filter.getSize(), sort);
        if (filter.isEmpty())
            return cdrRepository.findAll(pageable);
        else
            return cdrRepository.findBySrcLikeOrDstLike(filter.getSrcLike(), filter.getDstLike(), pageable);
    }

    @Override
    public byte[] getRecord(String fileName) {
        try {
            return Files.readAllBytes(Paths.get(path + "/" + fileName + ".wav"));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
