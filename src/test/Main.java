package test;

import model.bo.Shift;
import model.structures.FordFulkerson;
import model.structures.Pair;
import model.bo.UserSupportEng;
import model.dal.xml.Fetcher;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<UserSupportEng> tecnici   = Fetcher.getTecnici();
        List<String> lingue     = Fetcher.getLingue();
        List<Shift> turni       = Fetcher.getTurni();
        FordFulkerson ff        = new FordFulkerson(tecnici, lingue, turni);
        Pair<Boolean, List<Pair<UserSupportEng, Shift>>> abbinamenti = ff.getAbbinamenti();

        if(!abbinamenti._left)
            System.out.println("Abbinamento non completo.");

        for(Pair<UserSupportEng, Shift> c: abbinamenti._right)
            System.out.println(c._left.getName() + ": " + c._right.getInfo() + "\n");
    }


}
