package core.interfaces;

import core.Dataset;

/**
 * Created by sergio on 15/05/15.
 */
public interface IErrorMeasure {
    double measure( Dataset dataset, String[] classLabels );
}
