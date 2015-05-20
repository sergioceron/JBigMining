package core.interfaces;

import core.PRObject;

import java.util.Set;

/**
 * Created by sergio on 15/05/15.
 */
public interface IPartialSimilarity {
    double Compare( Set<Integer> featureSet, PRObject source, PRObject compareTo );
}
