package i.solonin.asteriskweb.converter;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyEditorSupport;
import java.util.function.Function;

@RequiredArgsConstructor
public class EntityConverter<T> extends PropertyEditorSupport {
    private final Function<String, T> convert;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.isEmpty(text)) {
            setValue(text);
            return;
        }
        setValue(convert.apply(text));
    }

    @Override
    public String getAsText() {
        return super.getAsText();
    }
}
