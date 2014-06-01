package model.dal.json;

import model.Util;
import model.bo.UserSupportEng;
import model.dal.SimpleCover;
import model.dal.SimpleScope;
import model.dal.SimpleShift;
import model.logger.Logger;
import model.structures.Pair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class JsonParser2 {
    private static final String TAG_LANG = "lang";
    private static final String TAG_USER_SUPPORT_ENG = "level1";
    private static final String TAG_SHIFT = "shift";
    private static final String TAG_COVER = "cover";
    private static final String TAG_DAYS_PER_WEEK = "daysPerWeek";
    private static final String TAG_HOURS_PER_DAY = "daysPerWeek";
    private static final String TAG_NAME = "name";
    private static final String TAG_REQUIRED_PEOPLE = "peopleNr";
    private static final String TAG_DAYS = "day";
    private static final String TAG_SCOPES = "scope";

    private Logger _logger = new Logger(this.getClass().getName());
    private JSONObject _root;

    /**
     * Parses the file and extracts the root for the other methods
     * @param filePath path to JSON file
     * @throws JSONException
     * @throws IOException
     */
    public JsonParser2(String filePath) throws JSONException, IOException {
        String jsonString = Util.readFileToString(new File(filePath));
        JSONTokener tokener = new JSONTokener(jsonString);
        _root = new JSONObject(tokener);
    }

    /**
     *
     * @return Collection of Engineers (LinkedList)
     * @throws JSONException
     */
    public Collection<UserSupportEng> getUserSupportEngs() throws JSONException {

        Collection<UserSupportEng> listEngs = new LinkedList<UserSupportEng>();

        JSONArray jsonEngsList = _root.getJSONArray(TAG_USER_SUPPORT_ENG);

        final int LEN = jsonEngsList.length();
        for(int i = 0; i < LEN; i++) {
            UserSupportEng eng = getUserSupportEng((JSONObject) jsonEngsList.get(i));
            listEngs.add(eng);
        }

        return listEngs;
    }

    public Collection<SimpleShift> getSimpleShifts() throws JSONException {

        JSONArray jsonShifts = _root.getJSONArray(TAG_SHIFT);
        final int LEN = jsonShifts.length();

        Collection<SimpleShift> result = new ArrayList<>(LEN);

        for(int i = 0; i < LEN; i++) {
            result.add(getSimpleShift((JSONObject) jsonShifts.get(i)));
        }

        return result;
    }

    public Collection<SimpleCover> getCovers() throws JSONException {
        JSONArray jsonCovers = _root.getJSONArray(TAG_COVER);
        final int LEN = jsonCovers.length();

        Collection<SimpleCover> covers = new ArrayList<>(LEN);
        for(int i = 0; i < LEN; i++) {
            JSONObject jCurrent = (JSONObject) jsonCovers.get(i);
            covers.add(getCover(jCurrent));
        }
        return covers;
    }

    private SimpleCover getCover(JSONObject jo) throws  JSONException {
        int people = jo.getInt(TAG_REQUIRED_PEOPLE);
        Collection<String> days = JSONArrayToStringArray(jo.getJSONArray(TAG_DAYS));
        JSONArray jScope = jo.getJSONArray(TAG_SCOPES);
        final int LEN = jScope.length();
        for(int i = 0; i < LEN; i++)
            String begin = jo.
                    covers.add(new SimpleCover())
    }

    private Collection<SimpleScope> getScopes() {

    }

    private SimpleScope getScope(JSONObject jScope) {
        SimpleShift shift = new SimpleShift()
    }

    private SimpleShift getSimpleShift(JSONObject jo) throws JSONException {
        String begin = jo.getString("begin");
        String end = jo.getString("end");
        return new SimpleShift(begin, end);
    }

    private UserSupportEng getUserSupportEng(JSONObject jsonEng) throws JSONException {
        // id name dayPerWeek hoursPerDay lang

        UserSupportEng eng = new UserSupportEng();

        // get single values
        eng.setDaysPerWeek(jsonEng.getInt(TAG_DAYS_PER_WEEK));
        eng.setDaysPerWeek(jsonEng.getInt(TAG_HOURS_PER_DAY));
        eng.setName(jsonEng.getString(TAG_NAME));

        // get array
        JSONArray jsonLangs = jsonEng.getJSONArray(TAG_LANG);
        Collection<String> langs = JSONArrayToStringArray(jsonLangs);
        eng.setLanguages(langs);

        return eng;
    }

    private static Collection<String> JSONArrayToStringArray(JSONArray jsonArray) throws JSONException {
        final int LEN = jsonArray.length();
        Collection<String> result = new ArrayList<>(LEN);
        for(int i = 0; i < LEN; i++) {
            String lang = JSONObject.valueToString(jsonArray.get(i));
            result.add(lang);
        }
        return result;
    }

}
