package utils;

import core.Dataset;
import core.PRObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sergio on 16/05/15.
 */
public class FeatureValuesExtractor {
    public static List<String> extract(int featIdx, Dataset ds) {
        Map<String, Boolean> values = new HashMap<String, Boolean>();
        for (PRObject d : ds) {
            String val = (String) d.getFeaturesValues()[featIdx];
            if (!values.containsKey(val))
                values.put(val, false);
        }
        List<String> result = new ArrayList<String>();
        for (String s : values.keySet())
            result.add(s);
        return result;
    }
}
