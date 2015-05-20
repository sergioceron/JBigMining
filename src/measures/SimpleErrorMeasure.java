package measures;

import core.Dataset;
import core.interfaces.IErrorMeasure;

/**
 * Created by sergio on 19/05/15.
 */
public class SimpleErrorMeasure implements IErrorMeasure {
    public double measure(Dataset dataset, String[] classLabels) {
        if (dataset.size() > 0) {
            int ErrorCount = 0;
            for (int i = 0; i < dataset.size(); i++)
                if (!dataset.get(i).getClassLabel().equals(classLabels[i]))
                    ErrorCount++;
            return ErrorCount * 1.0f / dataset.size();
        } else
            return 0;
    }

}
