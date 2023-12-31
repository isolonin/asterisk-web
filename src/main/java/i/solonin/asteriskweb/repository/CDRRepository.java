package i.solonin.asteriskweb.repository;

import i.solonin.asteriskweb.model.CDR;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface CDRRepository extends ListPagingAndSortingRepository<CDR, String> {
    Page<CDR> findBySrcLikeOrDstLikeOrRealSrcLike(String src, String dst, String realSrc, Pageable pageable);
}
