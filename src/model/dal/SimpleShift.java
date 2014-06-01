package model.dal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Shift that only contains begin and end times.
 * Used to generate Shift.
 * Just transportation of the JSON "shift".
 */
public class SimpleShift {
    private Date _begin;
    private Date _end;

    public SimpleShift(Date begin, Date end) {
        _begin = begin;
        _end = end;
    }

    public SimpleShift(String begin, String end) {
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        try {
            _begin = formatter.parse(begin);
            _end = formatter.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getBegin() {
        return _begin;
    }

    public Date getEnd() {
        return _end;
    }
}
