package model;

import java.io.*;

public final class Util {

    public static int dowToInt(String day) {
        if(day.equals("lun")) return 0;
        if(day.equals("mar")) return 1;
        if(day.equals("mer")) return 2;
        if(day.equals("gio")) return 3;
        if(day.equals("ven")) return 4;
        if(day.equals("sab")) return 5;
        if(day.equals("dom")) return 6;
        throw new IllegalArgumentException("Day of week inesistente");
    }

    public static String intToDow(int dow) {
        if(dow == 0) return "lun";
        if(dow == 1) return "mar";
        if(dow == 2) return "mer";
        if(dow == 3) return "gio";
        if(dow == 4) return "ven";
        if(dow == 5) return "sab";
        if(dow == 6) return "dom";
        throw new IllegalArgumentException("Day of week out of range [0..6]");
    }

    public static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE)
            throw new IllegalArgumentException(l + " cannot be cast to int without changing its value.");
        return (int) l;
    }

    public static String readFileToString(String inputFilePath) {
        File file = new File(inputFilePath);
        return readFileToString(file);
    }

    public static String readFileToString(File inputFile) {
        FileReader reader = null;
        String s = "";
        char[] charBuffer = new char[1024];

        try {
            reader = new FileReader(inputFile);
            while(reader.read(charBuffer) != -1)
                s += new String(charBuffer);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
}
