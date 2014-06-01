package model.dal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Class that wraps JSON Cover. Combined with SimpleShift you obtain the real Shift
 */
public class SimpleCover {
    private int _peopleRequired;
    private Collection<String> _days;
    private SimpleShift _shift;
    private Collection<String> _requiredLangs = new ArrayList<>();

    public SimpleCover(int peopleRequired, Collection<String> days, Date begin, Date end, Collection<String> requiredLanguages) {
        _peopleRequired = peopleRequired;
        _days = days;
        _shift = new SimpleShift(begin, end);
        _requiredLangs.addAll(requiredLanguages);
    }

    public void addLanguage(String lang) {
        _requiredLangs.add(lang);
    }
}
