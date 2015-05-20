package utils;

import core.Dataset;
import core.PRObject;
import core.Structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergio on 17/05/15.
 */
public class StructByClasses {
    public static Structure structuralize( Dataset source ) {
        Structure result = new Structure();
        List<String> classLabels = new ArrayList<String>();
        for( PRObject obj : source ) {
            int idx = classLabels.indexOf( obj.getClassLabel() );
            if( idx >= 0 )
                result.get( idx ).getDataset().add( obj );
            else {
                classLabels.add( obj.getClassLabel() );
                idx = classLabels.indexOf( obj.getClassLabel() );
                Structure.Item item = result.add( obj.getClassLabel(), new Dataset( source.getMetaObject() ) );
                item.getDataset().add( obj );
            }
        }
        return result;
    }
}
