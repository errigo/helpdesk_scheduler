package model.bo;

import java.util.Collection;

public class UserSupportEng {
    private String name;
    private Collection<String> languages;
    private int hoursPerDay;
    private int daysPerWeek;

    public int getHoursPerDay() {
        return hoursPerDay;
    }

    public int getDaysPerWeek() {
        return daysPerWeek;
    }

    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }

    public Collection<String> getLanguages() {
        return languages;
    }

    public void setLanguages(Collection<String> lingue) {
        this.languages = lingue;
    }

    public void addLanguage(String lang) {
        languages.add(lang);
    }

    public void setHoursPerDay(int oreGiorno) {
        this.hoursPerDay = oreGiorno;
    }

    public void setDaysPerWeek(int giorniSettimana) {
        this.daysPerWeek = giorniSettimana;
    }
}
