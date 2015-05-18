package samplers;

import core.Dataset;
import core.interfaces.ISampler;
import exceptions.ArgumentNullException;
import utils.Permuter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergio on 16/05/15.
 */
public class CrossValidation implements ISampler {
    public void makeSampling(Dataset dataset, Dataset training, Dataset testing) {
        testing = new Dataset(dataset.getMetaObject());
        training = new Dataset(dataset.getMetaObject());
    }

    public List<Dataset> makeSampling(Dataset dataset, int Samples) throws ArgumentNullException {
        if (dataset.size() < Samples) {
            throw new ArgumentNullException("Hay menos objetos que partes. no es posible hacer validación cruzada.");
        }

        List<Dataset> SampledDatasets = new ArrayList<Dataset>(Samples);
        int i, j;
        for (i = 0; i < Samples; i++)
            SampledDatasets.add(new Dataset(dataset.getMetaObject()));
        Dataset unordered = Permuter.getPermuted(dataset);
        j = 0;
        for (i = 0; i < unordered.size(); i++) {
            SampledDatasets.get(j).add(unordered.get(i));
            j++;
            if (j >= Samples) {
                j = 0;
            }
        }
        return SampledDatasets;
    }
}
