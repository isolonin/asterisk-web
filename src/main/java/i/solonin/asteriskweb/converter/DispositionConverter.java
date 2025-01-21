package i.solonin.asteriskweb.converter;

import i.solonin.asteriskweb.model.CDR;
import jakarta.persistence.AttributeConverter;
import org.apache.commons.lang3.StringUtils;

public class DispositionConverter implements AttributeConverter<CDR.Disposition, String> {
    @Override
    public String convertToDatabaseColumn(CDR.Disposition disposition) {
        return disposition.name();
    }

    @Override
    public CDR.Disposition convertToEntityAttribute(String s) {
        return CDR.Disposition.valueOf(StringUtils.isEmpty(s) ? "NOANSWER" : s);
    }
}
