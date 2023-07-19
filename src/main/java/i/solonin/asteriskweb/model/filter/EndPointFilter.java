package i.solonin.asteriskweb.model.filter;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class EndPointFilter extends AbstractFilter {
    private String search;

    public String getSearchLike() {
        return StringUtils.isEmpty(search) ? null : "%" + search + "%";
    }
}
