package core.interfaces;

import core.Dataset;
import core.Solution;

/**
 * Created by sergio on 15/05/15.
 */
public interface IFitnessFunction {
    double fitness(Solution Sol, Dataset training, Dataset validation);
}
