package utils;

import core.Dataset;
import core.MetaObject;
import core.PRObject;
import core.interfaces.IDissimilarity;

/**
 * Created by sergio on 17/05/15.
 */
public class DatasetCenter {

    public static PRObject getCenter( Dataset source ) {
        MetaObject metaObject = source.getMetaObject();
        PRObject result = new PRObject( metaObject );
        for( int i = 0; i < metaObject.getFeatureDescriptions().length; i++ ) {
            double sum = 0;
            for( int j = 0; j < source.size(); j++ )
                sum += (Double) source.get( j ).getFeaturesValues()[i];
            result.getFeaturesValues()[i] = sum / source.size();
        }
        return result;
    }

    public static PRObject getCenter( PRObject[] source ) {
        MetaObject metaObject = source[0].getMetaObject();
        PRObject result = new PRObject( metaObject );
        for( int i = 0; i < metaObject.getFeatureDescriptions().length; i++ ) {
            double sum = 0;
            for( int j = 0; j < source.length; j++ )
                sum += (Double) source[j].getFeaturesValues()[i];
            result.getFeaturesValues()[i] = sum / source.length;
        }
        return result;
    }

    public static PRObject getHolotype( Dataset dataset ) {
        IDissimilarity<PRObject> dissimilarity = dataset.getMetaObject().getDissimilarity();
        double minSum = Double.MAX_VALUE;
        PRObject result = null;
        for( PRObject obj : dataset ) {
            double currentSum = 0;
            for( PRObject obj2 : dataset )
                if( obj != obj2 )
                    currentSum += dissimilarity.compare( obj, obj2 );
            if( currentSum < minSum ) {
                minSum = currentSum;
                result = obj;
            }
        }
        return result;
    }
}
