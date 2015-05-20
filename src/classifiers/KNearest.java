package classifiers;

import core.Dataset;
import core.PRObject;
import core.interfaces.IDissimilarity;

/**
 * Created by sergio on 17/05/15.
 */
public class KNearest {

    public static Dataset findNeighbors( Dataset dataset, PRObject source, int k ) {
        IDissimilarity<PRObject> dissimilarity = dataset.getMetaObject().getDissimilarity();
        int currentCount = Math.min( dataset.size(), k );
        PRObject[] result = new PRObject[currentCount];
        double[] resultDistances = new double[currentCount];
        int i;

        for( i = 0; i < currentCount; i++ ) {
            result[i] = dataset.get( i );
            resultDistances[i] = dissimilarity.compare( source, dataset.get( i ) );
        }
        for( i = currentCount; i < dataset.size(); i++ ) {
            double currentDist = dissimilarity.compare( source, dataset.get( i ) );
            int maxIndex = 0;
            double maxValue = resultDistances[0];
            for( int j = 1; j < currentCount; j++ ) {
                if( resultDistances[j] > maxValue ) {
                    maxIndex = j;
                    maxValue = resultDistances[j];
                }
            }
            if( currentDist < resultDistances[maxIndex] ) {
                result[maxIndex] = dataset.get( i );
                resultDistances[maxIndex] = currentDist;
            }
        }
        Dataset resultDS = new Dataset( dataset.getMetaObject() );
        for( PRObject obj : result )
            resultDS.add( obj );

        return resultDS;
    }
}
