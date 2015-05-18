package classifiers;

import core.Dataset;
import core.PRObject;
import core.interfaces.IClassifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergio on 17/05/15.
 */
public class KNN implements IClassifier {

    private int k;

    private Dataset training;

    public KNN() {
        this.k = 1;
    }

    public KNN(int k) {
        this.k = k;
    }

    public void train(Dataset training) {
        this.training = training;
    }

    public String classify(PRObject obj) {
        if (training.size() > 0) {
            Dataset nearest = KNearest.findNeighbors(training, obj, k);
            // TODO: change to hashmap, cuz ArrayList's are not ordered list
            List<String> classLabels = new ArrayList<String>();
            List<Integer> counts = new ArrayList<Integer>();
            for (PRObject neighbor : nearest) {
                int idx = classLabels.indexOf(neighbor.getClassLabel());
                if (idx == -1) {
                    classLabels.add(neighbor.getClassLabel());
                    idx = classLabels.indexOf(neighbor.getClassLabel());
                    counts.add(1);
                } else {
                    counts.set(idx, counts.get(idx) + 1);
                }
            }
            int maxIndex = 0;
            for (int i = 1; i < counts.size(); i++)
                if (counts.get(i) > counts.get(maxIndex)) {
                    maxIndex = i;
                }
            return classLabels.get(maxIndex);
        } else
            return "";
    }

}
