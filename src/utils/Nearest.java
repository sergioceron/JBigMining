package utils;

import core.Dataset;
import core.PRObject;
import core.interfaces.IDissimilarity;

/**
 * Created by sergio on 17/05/15.
 */
public class Nearest {

    public static PRObject findNearest(Dataset dataset, PRObject source, boolean includeSelf) {
        IDissimilarity<PRObject> dissimilarity = dataset.getMetaObject().getDissimilarity();
        double minDistance = Double.MAX_VALUE;
        PRObject result = null;
        for (PRObject obj : dataset) {
            double currentDistance = dissimilarity.compare(source, obj);
            if (currentDistance < minDistance && (includeSelf || source != obj)) {
                minDistance = currentDistance;
                result = obj;
            }
        }
        return result;
    }

    public static Dataset findAllNearest(Dataset dataset, PRObject source) {
        IDissimilarity<PRObject> dissimilarity = dataset.getMetaObject().getDissimilarity();
        double minDistance = Double.MAX_VALUE;
        Dataset result = new Dataset(dataset.getMetaObject());
        for (PRObject obj : dataset) {
            double currentDistance = dissimilarity.compare(source, obj);
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                result.clear();
                result.add(obj);
            } else if (currentDistance == minDistance)
                result.add(obj);
        }
        return result;
    }

    public static int findIndexNearest(Dataset dataset, PRObject source, boolean includeSelf, IDissimilarity<PRObject> dissimilarity) {
        if (dissimilarity == null)
            dissimilarity = dataset.getMetaObject().getDissimilarity();
        double minDistance = Double.MAX_VALUE;
        int result = -1;
        for (int i = 0; i < dataset.size(); i++) {
            PRObject obj = dataset.get(i);
            double currentDistance = dissimilarity.compare(source, obj);
            if (currentDistance < minDistance && (includeSelf || source != obj)) {
                minDistance = currentDistance;
                result = i;
            }
        }
        return result;
    }
}
