package utils;

import core.Dataset;
import core.FeatureDescription;

/**
 * Created by sergio on 17/05/15.
 */
public class NormalizeNumericalAtt {

    public static Dataset normalize( Dataset source ) throws CloneNotSupportedException {
        Dataset objDataset = source.cloneDeep();
        int min = 0;
        int max = 0;
        //normalizando en [0,1] los atributos num'ericos
        for( int i = 0; i < objDataset.getMetaObject().getFeatureDescriptions().length; i++ ) {
            if( ( objDataset.getMetaObject().getFeatureDescriptions()[i].getType() == FeatureDescription.Type.INTEGER ) ||
                    ( objDataset.getMetaObject().getFeatureDescriptions()[i].getType() == FeatureDescription.Type.REAL ) ) {
                min = min( objDataset, i );
                max = max( objDataset, i );
                for( int j = i; j < objDataset.size(); j++ ) {
                    int temp = (Integer) ( objDataset.get( j ).getFeaturesValues()[i] ) / ( max - min );
                    objDataset.get( j ).getFeaturesValues()[i] = temp;

                }
            }
        }
        return objDataset;
    }

    private static int max( Dataset dataset, int column ) {
        int max = (Integer) dataset.get( 0 ).getFeaturesValues()[column];

        for( int j = 1; j < dataset.size(); j++ ) {
            if( max < (Integer) dataset.get( j ).getFeaturesValues()[column] )
                max = (Integer) dataset.get( j ).getFeaturesValues()[column];
        }
        return max;
    }

    private static int min( Dataset dataset, int column ) {
        int min = (Integer) dataset.get( 0 ).getFeaturesValues()[column];
        for( int i = 1; i < dataset.size(); i++ ) {
            if( min > (Integer) dataset.get( i ).getFeaturesValues()[column] )
                min = (Integer) dataset.get( i ).getFeaturesValues()[column];
        }

        return min;
    }
}
