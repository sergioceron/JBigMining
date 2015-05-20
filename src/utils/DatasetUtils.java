package utils;

import core.Dataset;
import core.PRObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by sergio on 17/05/15.
 */
public class DatasetUtils {
    private static Random random = new Random();

    public static PRObject randomSample( Dataset source ) {
        int rndIdx = random.nextInt( source.size() );
        return source.get( rndIdx );
    }

    public static Dataset randomSample( Dataset dataset, int count ) {
        Dataset result = new Dataset( dataset.getMetaObject() );
        List<Integer> idx = new ArrayList<Integer>( dataset.size() );
        int i;
        for( i = 0; i < dataset.size(); i++ )
            idx.add( i );
        int currentCount = Math.min( count, dataset.size() );
        for( i = 0; i < currentCount; i++ ) {
            int rndIdx = random.nextInt( idx.size() );
            int objIdx = idx.get( rndIdx );
            result.add( dataset.get( objIdx ) );
            idx.remove( rndIdx );
        }
        return result;
    }

    public static Dataset randomSampleFromClass( Dataset source, String classLabel, int count ) {
        Dataset result = new Dataset( source.getMetaObject() );
        List<Integer> idx = new ArrayList<Integer>( source.size() );
        int i;
        for( i = 0; i < source.size(); i++ )
            if( source.get( i ).getClassLabel().equals( classLabel ) )
                idx.add( i );
        int currentCount = Math.min( count, idx.size() );
        for( i = 0; i < currentCount; i++ ) {
            int rndIdx = random.nextInt( idx.size() );
            int objidx = idx.get( rndIdx );
            result.add( source.get( objidx ) );
            idx.remove( rndIdx );
        }
        return result;
    }

    public static PRObject randomObjectFromClass( Dataset source, String classLabel ) throws CloneNotSupportedException {
        List<Integer> idx = new ArrayList<Integer>( source.size() );
        int i;
        for( i = 0; i < source.size(); i++ )
            if( source.get( i ).getClassLabel().equals( classLabel ) )
                idx.add( i );
        if( idx.size() != 0 ) {
            int rndIdx = random.nextInt( idx.size() );
            int objIdx = idx.get( rndIdx );
            return source.get( objIdx ).clone();
        } else
            return null;
    }
}
