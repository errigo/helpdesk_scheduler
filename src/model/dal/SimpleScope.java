package model.dal;

import java.util.ArrayList;
import java.util.Collection;

/**
 * JSON wrapper to pass data to BOs
 */
public class SimpleScope {
    SimpleShift _shift;
    Collection<String> _languages = new ArrayList<>();

    public SimpleScope(SimpleShift shift, Collection<String> langs) {
        _shift = shift;
        _languages.addAll(langs);
    }

    public SimpleShift getShift() {
        return _shift;
    }

    public Collection<String> getLanguages() {
        return _languages;
    }
}
