package i.solonin.asteriskweb.service;

import i.solonin.asteriskweb.model.AoR;
import i.solonin.asteriskweb.model.Auth;
import i.solonin.asteriskweb.model.EndPoint;
import i.solonin.asteriskweb.model.Identify;
import i.solonin.asteriskweb.model.filter.EndPointFilter;
import i.solonin.asteriskweb.repository.AorRepository;
import i.solonin.asteriskweb.repository.AuthRepository;
import i.solonin.asteriskweb.repository.EndPointRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class EndPointServiceImpl implements EndPointService {
    private final EndPointRepository endPointRepository;
    private final AorRepository aorRepository;
    private final AuthRepository authRepository;

    @Override
    public Page<EndPoint> findAll(EndPointFilter filter, String order, boolean asc, int page) {
        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, filter.getSize(), sort);
        if (StringUtils.isEmpty(filter.getSearch()))
            return endPointRepository.findAll(pageable);
        else
            return endPointRepository.findByIdLikeOrMsisdnLike(filter.getSearchLike(), filter.getSearchLike(), pageable);
    }

    @Override
    public EndPoint findById(String id) {
        return endPointRepository.findById(id);
    }

    @Override
    public void save(EndPoint endPoint) {
        //AoR
        if ("".equals(endPoint.getContact())) endPoint.setContact(null);
        if (endPoint.getAors() == null || StringUtils.isEmpty(endPoint.getAors().getId())) {
            AoR aor = aorRepository.findById(endPoint.getId()).orElse(new AoR(endPoint.getId()));
            aor.setContact(endPoint.getContact());
            endPoint.setAors(aor);
        } else {
            endPoint.getAors().setContact(endPoint.getContact());
        }

        //Auth
        if (endPoint.getAuth() == null || StringUtils.isEmpty(endPoint.getAuth().getId())) {
            if (!StringUtils.isEmpty(endPoint.getUsername())) {
                Auth auth = authRepository.findById(endPoint.getId())
                        .orElse(new Auth(endPoint.getId()));
                auth.setUsername(endPoint.getUsername());
                auth.setPassword(endPoint.getPassword());
                endPoint.setAuth(auth);
            } else {
                endPoint.setAuth(null);
            }
        } else {
            if (!StringUtils.isEmpty(endPoint.getUsername())) {
                endPoint.getAuth().setUsername(endPoint.getUsername());
                endPoint.getAuth().setPassword(endPoint.getPassword());
            } else {
                endPoint.setAuth(null);
            }
        }

        //Identify
        if (!StringUtils.isEmpty(endPoint.getIds())) {
            for (String ipMatch : endPoint.getIds().split("\s?,\s?")) {
                String match = ipMatch.trim();
                if (endPoint.getIdentifies().stream().noneMatch(i -> i.getMatch().equals(match)))
                    endPoint.getIdentifies().add(new Identify(endPoint, match));
            }
            endPoint.getIdentifies().removeIf(i -> Arrays.stream(endPoint.getIds().split("\s?,\s?"))
                    .noneMatch(match -> i.getMatch().trim().equals(match.trim())));
        } else {
            endPoint.getIdentifies().clear();
        }

        endPointRepository.save(endPoint);
    }

    @Override
    public void delete(EndPoint endPoint) {
        endPointRepository.delete(endPoint);
    }
}
