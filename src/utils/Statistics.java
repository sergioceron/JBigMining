package utils;

import core.Dataset;
import core.FeatureDescription;
import core.PRObject;
import exceptions.ArgumentNullException;
import exceptions.ArgumentOutOfRangeException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sergio on 15/05/15.
 */
public class Statistics {
    public static double mean(int featureIndex, Dataset dataSet) throws ArgumentOutOfRangeException, ArgumentNullException {
        if (dataSet == null)
            throw new ArgumentNullException("dataSet", "Could not calculate statistics: Null data set reference.");
        if (featureIndex > dataSet.getMetaObject().getFeatureDescriptions().length - 1)
            throw new ArgumentOutOfRangeException("featureIndex", featureIndex, "Could not calculate statistics: Invalid feature index.");
        if (dataSet.size() == 0)
            throw new ArgumentOutOfRangeException("dataSet.Count", dataSet.size(), "Could not calculate statistics: Empty dataSet");
        if ((dataSet.getMetaObject().getFeatureDescriptions()[featureIndex].getType() != FeatureDescription.Type.REAL)
                && (dataSet.getMetaObject().getFeatureDescriptions()[featureIndex].getType() != FeatureDescription.Type.INTEGER))
            throw new ArgumentOutOfRangeException("dataSet.MetaObject.FeatureDescriptions[featureIndex].Type", dataSet.getMetaObject().getFeatureDescriptions()[featureIndex].getType(), "Could not calculate statistics: Invalid feature type.");

        double sum = 0, count = 0;
        for (PRObject obj : dataSet)
        if (!obj.isIncomplete(featureIndex))
        {
            sum += (Double)(obj.getFeaturesValues()[featureIndex]);
            ++count;
        }

        return (count != 0) ? sum / count : 0;
    }

    public static double max(int featureIndex, Dataset dataSet) throws ArgumentOutOfRangeException, ArgumentNullException {
        if (dataSet == null)
            throw new ArgumentNullException("dataSet", "Could not calculate statistics: Null data set reference.");
        if (featureIndex > dataSet.getMetaObject().getFeatureDescriptions().length - 1)
            throw new ArgumentOutOfRangeException("featureIndex", featureIndex, "Could not calculate statistics: Invalid feature index.");
        if (dataSet.size() == 0)
            throw new ArgumentOutOfRangeException("dataSet.Count", dataSet.size(), "Could not calculate statistics: Empty dataSet");
        if ((dataSet.getMetaObject().getFeatureDescriptions()[featureIndex].getType() != FeatureDescription.Type.REAL)
                && (dataSet.getMetaObject().getFeatureDescriptions()[featureIndex].getType() != FeatureDescription.Type.INTEGER))
            throw new ArgumentOutOfRangeException("FeatureDescriptions.Type", dataSet.getMetaObject().getFeatureDescriptions()[featureIndex].getType(), "Could not calculate statistics: Invalid feature type.");


        double max = -Double.MAX_VALUE;
        for(PRObject obj : dataSet)
        if (!obj.isIncomplete(featureIndex))
        {
            double value = (Double) obj.getFeaturesValues()[featureIndex];
            if (value > max)
                max = value;
        }

        return max;
    }

    public static double min(int featureIndex, Dataset dataSet) throws ArgumentNullException, ArgumentOutOfRangeException {
        if (dataSet == null)
            throw new ArgumentNullException("dataSet", "Could not calculate statistics: Null data set reference.");
        if (featureIndex > dataSet.getMetaObject().getFeatureDescriptions().length - 1)
            throw new ArgumentOutOfRangeException("featureIndex", featureIndex, "Could not calculate statistics: Invalid feature index.");
        if (dataSet.size() == 0)
            throw new ArgumentOutOfRangeException("dataSet.size", dataSet.size(), "Could not calculate statistics: Empty dataSet");
        if ((dataSet.getMetaObject().getFeatureDescriptions()[featureIndex].getType() != FeatureDescription.Type.REAL) && (dataSet.getMetaObject().getFeatureDescriptions()[featureIndex].getType() != FeatureDescription.Type.INTEGER))
            throw new ArgumentOutOfRangeException("FeatureDescriptions.Type", dataSet.getMetaObject().getFeatureDescriptions()[featureIndex].getType(), "Could not calculate statistics: Invalid feature type.");

        double min = Double.MAX_VALUE;
        for (PRObject obj : dataSet)
        if (!obj.isIncomplete(featureIndex))
        {
            double value = (Double)obj.getFeaturesValues()[featureIndex];
            if (value < min)
                min = value;
        }

        return min;
    }

    public static double standartDeviation(int featureIndex, Dataset dataSet) throws ArgumentOutOfRangeException, ArgumentNullException {
        double mean = mean(featureIndex, dataSet);

        double sum = 0, count = 0;
        for (PRObject obj : dataSet)
        if (!obj.isIncomplete(featureIndex))
        {
            sum += Math.pow(mean - (Double) obj.getFeaturesValues()[featureIndex], 2);
            ++count;
        }

        return (Double)Math.sqrt(1 / (count - 1) * sum);
    }

    public static int frequencyOfValue(int featureIndex, String value, Dataset dataSet) throws ArgumentNullException, ArgumentOutOfRangeException {
        if (dataSet == null)
            throw new ArgumentNullException("dataSet", "Could not calculate statistics: Null data set reference.");
        if (featureIndex > dataSet.getMetaObject().getFeatureDescriptions().length - 1)
            throw new ArgumentOutOfRangeException("featureIndex", featureIndex, "Could not calculate statistics: Invalid feature index.");
        if (dataSet.size() == 0)
            throw new ArgumentOutOfRangeException("dataSet.Count", dataSet.size(), "Could not calculate statistics: Empty dataSet");


        int freq = 0;
        for (int i = 0; i < dataSet.size(); i++)
        {
            String current = (String) dataSet.get(i).getFeaturesValues()[featureIndex];
            if (current.equals(value))
                freq++;
        }
        return freq;
    }

    public static int relativeFrequencyOfValue(int featureIndex, String value, Dataset dataSet) throws ArgumentNullException, ArgumentOutOfRangeException {
        if (dataSet == null)
            throw new ArgumentNullException("dataSet", "Could not calculate statistics: Null data set reference.");
        if (featureIndex > dataSet.getMetaObject().getFeatureDescriptions().length - 1)
            throw new ArgumentOutOfRangeException("featureIndex", featureIndex, "Could not calculate statistics: Invalid feature index.");
        if (dataSet.size() == 0)
            throw new ArgumentOutOfRangeException("dataSet.Count", dataSet.size(), "Could not calculate statistics: Empty dataSet");

        return (frequencyOfValue(featureIndex, value, dataSet)/dataSet.size());
    }

    public static double probabilityOfValueGivenClass(int feat_idx, String class_label, String value, Dataset ds) throws ArgumentNullException, ArgumentOutOfRangeException {
        if (ds == null)
            throw new ArgumentNullException("dataSet", "Could not calculate statistics: Null data set reference.");
        if (feat_idx > ds.getMetaObject().getFeatureDescriptions().length - 1)
            throw new ArgumentOutOfRangeException("featureIndex", feat_idx, "Could not calculate statistics: Invalid feature index.");
        if (ds.size() == 0)
            throw new ArgumentOutOfRangeException("dataSet.Count", ds.size(), "Could not calculate statistics: Empty dataSet");

        int frequency = 0; int count = 0;
        for (int i = 0; i < ds.size(); i++)
            if (ds.get(i).getClassLabel().equals(class_label))
            {
                count++;
                String current = (String) ds.get(i).getFeaturesValues()[feat_idx];
                if (current.equals(value))
                    frequency++;
            }
        return (frequency*1.0)/(count*1.0);
    }

    public static double probabilityOfValuesGivenTwoClasses(int feat_idx, String first_label, String second_label, Dataset ds) throws ArgumentNullException, ArgumentOutOfRangeException {
        if (ds == null)
            throw new ArgumentNullException("dataSet", "Could not calculate statistics: Null data set reference.");
        if (feat_idx > ds.getMetaObject().getFeatureDescriptions().length - 1)
            throw new ArgumentOutOfRangeException("featureIndex", feat_idx, "Could not calculate statistics: Invalid feature index.");
        if (ds.size() == 0)
            throw new ArgumentOutOfRangeException("dataSet.Count", ds.size(), "Could not calculate statistics: Empty dataSet");

        double sum = 0;
        List<String> values = FeatureValuesExtractor.extract(feat_idx, ds);
        for (int i = 0; i < values.size(); i++)
            sum += (probabilityOfValueGivenClass(feat_idx, values.get(i), first_label, ds) * probabilityOfValueGivenClass(feat_idx, values.get(i), second_label, ds));
        return sum;
    }

    public static Object mostFrequentValue(int featIdx, Dataset dataSet) throws ArgumentNullException, ArgumentOutOfRangeException {
        if (dataSet == null)
            throw new ArgumentNullException("dataSet", "Could not calculate statistics: Null data set reference.");
        if (featIdx > dataSet.getMetaObject().getFeatureDescriptions().length - 1)
            throw new ArgumentOutOfRangeException("featureIndex", featIdx, "Could not calculate statistics: Invalid feature index.");
        if (dataSet.size() == 0)
            throw new ArgumentOutOfRangeException("dataSet.Count", dataSet.size(), "Could not calculate statistics: Empty dataSet");

        Map<Object, Integer> feat_values = new HashMap<Object, Integer>();

        for (PRObject obj : dataSet)
        {
            // esto es un parche, porque las claves de los diccionarios no pueden ser null
            String aux = "?";
            Object key;
            if (obj.getFeaturesValues()[featIdx] == null)
                key = aux;
            else
                key = obj.getFeaturesValues()[featIdx];

            if (!feat_values.containsKey(key))
                feat_values.put(key, 1);
            else
                feat_values.put(key, feat_values.get(key)+1);

        }
        int mod = 0;
        Object feat = null;
        List laux = new ArrayList();
        for (Object f : feat_values.keySet())
        laux.add(f);
        for (int i = 0; i < laux.size(); i++)
        {
            if (feat_values.get(laux.get(i)) > mod)
            {
                mod = feat_values.get(laux.get(i));
                feat = laux.get(i);
            }
        }

        return feat;
    }
}
