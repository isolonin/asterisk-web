package i.solonin.asteriskweb.model.filter;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class CallsFilter extends AbstractFilter {
    private String src;
    private String dst;

    public boolean isEmpty() {
        return StringUtils.isEmpty(src) && StringUtils.isEmpty(dst);
    }

    public String getSrcLike() {
        return StringUtils.isEmpty(src) ? null : "%" + src + "%";
    }

    public String getDstLike() {
        return StringUtils.isEmpty(dst) ? null : "%" + dst + "%";
    }
}
