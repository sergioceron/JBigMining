package utils;

import core.Dataset;
import core.interfaces.IClassifier;
import core.interfaces.IErrorMeasure;

/**
 * Created by sergio on 19/05/15.
 */
public class ClassifyDataset {
    public static String[] Classify(Dataset source, IClassifier classifier)
    {
        String[] result = new String[source.size()];
        for (int i = 0; i < source.size(); i++)
            result[i] = classifier.classify(source.get(i));
        return result;
    }
    public static double ErrorInClassification(Dataset source, IClassifier classifier, IErrorMeasure errorMeasure)
    {
        String[] classes = Classify(source, classifier);
        return errorMeasure.measure(source, classes);
    }
    public static boolean[] GetWellClassifiedVector(Dataset source, IClassifier classifier)
    {
        boolean[] result = new boolean[source.size()];
        for (int i = 0; i < source.size(); i++)
            result[i] = classifier.classify(source.get(i)) == source.get(i).getClassLabel();
        return result;
    }
    public static Dataset GetWellClassifiedObjects(Dataset source, IClassifier classifier)
    {
        Dataset result = new Dataset(source.getMetaObject());
        for (int i = 0; i < source.size(); i++)
            if (classifier.classify(source.get(i)) == source.get(i).getClassLabel())
                result.add(source.get(i));
        return result;
    }
    public static Dataset GetBadClassifiedObjects(Dataset source, IClassifier classifier)
    {
        Dataset result = new Dataset(source.getMetaObject());
        for (int i = 0; i < source.size(); i++)
            if (classifier.classify(source.get(i)) != source.get(i).getClassLabel())
                result.add(source.get(i));
        return result;
    }
    public static void AddWellClassifiedObjects(Dataset source, IClassifier classifier, Dataset result)
    {
        for (int i = 0; i < source.size(); i++)
            if (classifier.classify(source.get(i)) == source.get(i).getClassLabel())
                result.add(source.get(i));
    }
}
