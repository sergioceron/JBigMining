package dissimilarities;

import core.Dataset;
import core.FeatureDescription;
import core.interfaces.IGroupDissimilarity;
import exceptions.ArgumentNullException;
import exceptions.ArgumentOutOfRangeException;
import utils.FeatureValuesExtractor;
import utils.Statistics;

import java.util.List;

/**
 * Created by sergio on 17/05/15.
 */
public class HIMICDissimilarity implements IGroupDissimilarity<Dataset, Dataset> {

    public double compare( Dataset source, Dataset compareTo ) {
        double dissTotal = 0;
        double currentDiss = 0;
        for( int k = 0; k < source.getMetaObject().getFeatureDescriptions().length; k++ ) {
            FeatureDescription desc = source.getMetaObject().getFeatureDescriptions()[k];
            if( ( desc.getType() == FeatureDescription.Type.INTEGER ) || ( desc.getType() == FeatureDescription.Type.REAL ) )
                try {
                    currentDiss = 1 - Math.abs( Statistics.mean( k, source ) - Statistics.mean( k, compareTo ) );
                } catch( ArgumentOutOfRangeException e ) {
                    e.printStackTrace();
                } catch( ArgumentNullException e ) {
                    e.printStackTrace();
                }
            else {
                List<String> featValuesSrc = FeatureValuesExtractor.extract( k, source );
                List<String> featValuesCpr = FeatureValuesExtractor.extract( k, compareTo );
                // para unir los valores en una sola lista
                for( int i = 0; i < featValuesSrc.size(); i++ )
                    if( !featValuesCpr.contains( featValuesSrc.get( i ) ) )
                        featValuesCpr.add( featValuesSrc.get( i ) );
                for( int i = 0; i < featValuesCpr.size(); i++ )
                    try {
                        currentDiss = Statistics.relativeFrequencyOfValue( k, featValuesCpr.get( i ), source ) * Statistics.relativeFrequencyOfValue( k, featValuesCpr.get( i ), compareTo );
                    } catch( ArgumentNullException e ) {
                        e.printStackTrace();
                    } catch( ArgumentOutOfRangeException e ) {
                        e.printStackTrace();
                    }
            }
            dissTotal += currentDiss;
        }
        return 1 - dissTotal; // esto es para que sea una disimilaridad
    }
}
