package utils;

import core.Dataset;
import core.PRObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by sergio on 16/05/15.
 */
public class ClassInfo {
    public static String[] classes( Dataset source ) {
        Set<String> classLabelSet = new HashSet<String>();
        for( PRObject obj : source )
            if( !classLabelSet.contains( obj.getClassLabel() ) )
                classLabelSet.add( obj.getClassLabel() );
        String[] classLabelArr = classLabelSet.toArray( new String[classLabelSet.size()] );
        return classLabelArr;
    }

    public static boolean isHomogeneous( Dataset source ) {
        boolean Result = true;
        if( source.size() >= 1 ) {
            String ClassLabel = source.get( 0 ).getClassLabel();
            for( int i = 1; Result && i < source.size(); i++ )
                Result = ClassLabel == source.get( i ).getClassLabel();
        }
        return Result;
    }

    public static Map<String, Integer> classValuesCount( Dataset source ) {
        Map<String, Integer> dic = new HashMap<String, Integer>();
        for( PRObject obj : source )
            if( !dic.containsKey( obj.getClassLabel() ) )
                dic.put( obj.getClassLabel(), 1 );
            else
                dic.put( obj.getClassLabel(), dic.get( obj.getClassLabel() ) + 1 );
        return dic;
    }

    public static double iR( Dataset Source ) {
        Map<String, Integer> dic = classValuesCount( Source );
        int max = 0;
        int min = Integer.MAX_VALUE;
        for( String cl : dic.keySet() ) {
            int amount = dic.get( cl );
            if( amount > max )
                max = amount;
            if( amount < min )
                min = amount;
        }
        return max * 1.0 / min * 1.0;
    }

    public static String majorityClass( Dataset ds ) {
        Map<String, Integer> dic = classValuesCount( ds );
        int max = 0;
        String clas = "";
        for( String cl : dic.keySet() )
            if( dic.get( cl ) > max ) {
                max = dic.get( cl );
                clas = cl;
            }
        return clas;
    }

    public static String minorityClass( Dataset ds ) {
        Map<String, Integer> dic = classValuesCount( ds );
        int min = Integer.MAX_VALUE;
        String clas = "";
        for( String cl : dic.keySet() )
            if( dic.get( cl ) < min ) {
                min = dic.get( cl );
                clas = cl;
            }
        return clas;
    }

    public static int majorityClassCount( Dataset ds ) {
        Map<String, Integer> dic = classValuesCount( ds );
        int max = 0;
        for( String cl : dic.keySet() )
            if( dic.get( cl ) > max ) {
                max = dic.get( cl );
            }
        return max;
    }

    public static int minorityClassCount( Dataset ds ) {
        Map<String, Integer> dic = classValuesCount( ds );
        int min = Integer.MAX_VALUE;
        for( String cl : dic.keySet() )
            if( dic.get( cl ) < min ) {
                min = dic.get( cl );
            }
        return min;
    }

    public static int amountOf( Dataset ds, String classLabel ) {
        Map<String, Integer> dic = classValuesCount( ds );
        if( !dic.containsKey( classLabel ) )
            return 0;
        else
            return dic.get( classLabel );
    }

    public static Dataset objectsOf( Dataset ds, String classLabel ) {
        Dataset result = new Dataset( ds.getMetaObject() );
        for( PRObject obj : ds )
            if( obj.getClassLabel().equals( classLabel ) )
                result.add( obj );
        return result;
    }

    public static Dataset objectsNotOf( Dataset ds, String classLabel ) {
        Dataset result = new Dataset( ds.getMetaObject() );
        for( PRObject obj : ds )
            if( !obj.getClassLabel().equals( classLabel ) )
                result.add( obj );
        return result;
    }

    public static boolean isIncomplete( Dataset ds ) {
        boolean found = false;
        int w = 0;
        while( ( !found ) && ( w < ds.size() ) ) {
            int k = 0;
            while( ( !found ) && ( k < ds.getMetaObject().getFeatureDescriptions().length ) ) {
                if( ds.get( w ).isIncomplete( k ) )
                    found = true;
                k++;
            }
            w++;
        }

        if( !found )
            return false;
        else
            return true;
    }
}
