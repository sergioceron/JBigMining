package utils;

import core.Dataset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by sergio on 16/05/15.
 */
public class Permuter {
    private static Random random = new Random();

    public static void permute( Dataset Source ) {
        List<Integer> Idxs = new ArrayList<Integer>( Source.size() );
        Dataset clonnedList = Source.clone();
        Source.clear();
        int i;
        for( i = 0; i < clonnedList.size(); i++ )
            Idxs.add( i );
        for( i = 0; i < clonnedList.size(); i++ ) {
            int RndIdx = random.nextInt( Idxs.size() );
            int ObjIdx = Idxs.get( RndIdx );
            Source.add( clonnedList.get( ObjIdx ) );
            Idxs.remove( RndIdx );
        }
    }

    public static Dataset getPermuted( Dataset Source ) {
        Dataset result = new Dataset( Source.getMetaObject() );
        Dataset ds = Source.clone();
        while( ds.size() > 0 ) {
            int idx = random.nextInt( ds.size() );
            result.add( ds.get( idx ) );
            ds.remove( idx );
        }
        return result;
    }
}
