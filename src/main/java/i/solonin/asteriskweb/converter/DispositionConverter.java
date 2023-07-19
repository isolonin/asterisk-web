package i.solonin.asteriskweb.converter;

import i.solonin.asteriskweb.model.CDR;
import jakarta.persistence.AttributeConverter;

public class DispositionConverter implements AttributeConverter<CDR.Disposition, String> {
    @Override
    public String convertToDatabaseColumn(CDR.Disposition disposition) {
        return disposition.name();
    }

    @Override
    public CDR.Disposition convertToEntityAttribute(String s) {
        return CDR.Disposition.valueOf(s.replaceAll(" ", "_"));
    }
}
