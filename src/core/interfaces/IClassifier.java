package core.interfaces;

import core.Dataset;
import core.PRObject;

/**
 * Created by sergio on 15/05/15.
 */
public interface IClassifier {
    void train( Dataset dataset );

    String classify( PRObject prObject );
}
