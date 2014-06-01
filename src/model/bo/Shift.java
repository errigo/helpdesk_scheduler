package model.bo;

import model.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Shift {

    private Date _begin;
    private Date _end;
    private int _duration;
    private String _dow;
    private Collection<String> _languages = new ArrayList<>();

    public Shift(String begin, String end, String dayOfWeek, LinkedList<String> requiredLanguages) {
        SimpleDateFormat f = new SimpleDateFormat("HH:mm");
        try {
            _begin = f.parse(begin);
            _end = f.parse(end);
        } catch (ParseException e){
            System.out.print("Turni.java: Errore parsing ore dei turni.");
        }

        _dow = dayOfWeek;
        _languages = requiredLanguages;
        _duration = Util.safeLongToInt(this._end.getTime() - this._begin.getTime());
    }

    public int getDuration() {
        return _duration;
    }

    public Collection<String> getRequiredLanguages() {
        return _languages;
    }

    public String getInfo() {
        return _dow + " " + _begin.toString() + " " + _end.toString();
    }

}
