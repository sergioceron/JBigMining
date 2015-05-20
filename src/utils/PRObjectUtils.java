package utils;

import core.FeatureDescription;
import core.PRObject;
import exceptions.ArgumentOutOfRangeException;

/**
 * Created by sergio on 17/05/15.
 */
public class PRObjectUtils {

    public static boolean equalsAccordingRange( PRObject source, PRObject compare, double range ) throws ArgumentOutOfRangeException {
        if( source.getFeaturesValues().length != compare.getFeaturesValues().length )
            throw new ArgumentOutOfRangeException( "Los dos objetos deben tener la misma cantidad de rasgos" );
        boolean eq = false;
        for( int i = 0; i < source.getFeaturesValues().length; i++ ) {
            if( ( ( source.getMetaObject().getFeatureDescriptions()[i].getType() == FeatureDescription.Type.INTEGER ) ||
                    ( source.getMetaObject().getFeatureDescriptions()[i].getType() == FeatureDescription.Type.REAL ) ) &&
                    ( ( compare.getMetaObject().getFeatureDescriptions()[i].getType() == FeatureDescription.Type.INTEGER ) ||
                            ( compare.getMetaObject().getFeatureDescriptions()[i].getType() == FeatureDescription.Type.REAL ) ) ) {
                if( (Double) source.getFeaturesValues()[i] - (Double) compare.getFeaturesValues()[i] > range )
                    eq = true;
            } else if( !source.getFeaturesValues()[i].equals( compare.getFeaturesValues()[i] ) )
                eq = true;
        }
        return eq;
    }
}
