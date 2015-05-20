package core.interfaces;

import core.Dataset;

/**
 * Created by sergio on 15/05/15.
 */
public interface ISampler {
    void makeSampling( Dataset dataset, Dataset Training, Dataset Testing );
}
