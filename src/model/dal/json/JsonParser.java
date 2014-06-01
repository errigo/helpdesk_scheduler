package model.dal.json;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Util;
import model.dal.SimpleShift;
import model.logger.Logger;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 *
 */
public class JsonParser {

    Logger logger;
    String json;
    Gson gson;

    public JsonParser(String path) {
        logger = new Logger(getClass().getName());
        json = Util.readFileToString(path);
        gson = new Gson();
    }

    public getShift()  {
        return gson.fromJson(json, SimpleShift.class);
    }

    private example() {
        // primitives
        int one = gson.fromJson("1", int.class);
        Integer one1 = gson.fromJson("1", Integer.class);
        Long one11 = gson.fromJson("1", Long.class);
        Boolean false1 = gson.fromJson("false", Boolean.class);
        String str = gson.fromJson("\"abc\"", String.class);
        String anotherStr = gson.fromJson("[\"abc\"]", String.class);

        // arrays
        int[] ints2 = gson.fromJson("[1,2,3,4,5]", int[].class);

        // classses
        BagOfPrimitives obj2 = gson.fromJson(json, BagOfPrimitives.class);

        // collections
        Type collectionType = new TypeToken<Collection<Integer>>() {
        }.getType();
        Collection<Integer> ints2 = gson.fromJson(json, collectionType)
    }
}
