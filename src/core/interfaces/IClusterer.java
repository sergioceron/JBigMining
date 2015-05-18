package core.interfaces;

import core.Dataset;
import core.Structure;

/**
 * Created by sergio on 15/05/15.
 */
public interface IClusterer {
    Structure findClusters(Dataset dataset);
}
