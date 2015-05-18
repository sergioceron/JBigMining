package dissimilarities;

import core.PRObject;
import core.interfaces.IDissimilarity;

/**
 * Created by sergio on 16/05/15.
 */
public class Euclidean implements IDissimilarity<PRObject> {
    public double compare(PRObject source, PRObject compareTo) {
        double sum = 0;
        for (int i = 0; i < source.getFeaturesValues().length; i++) {
            double v1 = (Double) source.getFeaturesValues()[i];
            double v2 = (Double) compareTo.getFeaturesValues()[i];
            sum += Math.pow(v1 - v2, 2);
        }
        return Math.sqrt(sum);
    }
}
