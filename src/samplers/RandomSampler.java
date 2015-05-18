package samplers;

import core.Dataset;
import core.PRObject;
import core.interfaces.ISampler;
import exceptions.ArgumentOutOfRangeException;
import utils.Permuter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by sergio on 16/05/15.
 */
public class RandomSampler implements ISampler {

    private int seed = -1;
    private double trainingRatio;
    private Random rand;

    public void makeSampling(Dataset dataset, Dataset training, Dataset testing) {
        if (rand == null) {
            if (seed == -1)
                rand = new Random();
            else
                rand = new Random(seed);
        }
        training = new Dataset(dataset.getMetaObject());
        testing = new Dataset(dataset.getMetaObject());
        for (PRObject obj : dataset) {
            if (rand.nextDouble() < trainingRatio)
                training.add(obj);
            else
                testing.add(obj);
        }
    }

    public List<Dataset> makeSampling(Dataset dataset, int samples) {
        List<Dataset> sampledDatasets = new ArrayList<Dataset>(samples);
        int i;
        for (i = 0; i < samples; i++)
            sampledDatasets.add(new Dataset(dataset.getMetaObject()));
        Dataset unordered = Permuter.getPermuted(dataset);
        for (i = 0; i < unordered.size(); i++) {
            // Errrrrooooooorrrrr
            int idx = i - i / samples * samples;
            sampledDatasets.get(idx).add(unordered.get(i));
        }
        return sampledDatasets;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public double getTrainingRatio() {
        return trainingRatio;
    }

    public void setTrainingRatio(double trainingRatio) throws ArgumentOutOfRangeException {
        if (trainingRatio >= 0 && trainingRatio <= 1)
            this.trainingRatio = trainingRatio;
        else
            throw new ArgumentOutOfRangeException("TrainingRatio must be a number between 0 and 1");
    }

}
