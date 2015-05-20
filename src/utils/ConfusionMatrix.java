package utils;

import core.Dataset;
import core.PRObject;
import core.interfaces.IClassifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sergio on 16/05/15.
 */
public class ConfusionMatrix {
    public static int[][] compute( List<String> classLabels, Dataset dataset, IClassifier classifier ) throws Exception {
        Map<String, Integer> labelsIdx = new HashMap<String, Integer>( classLabels.size() );
        for( int i = 0; i < classLabels.size(); i++ )
            labelsIdx.put( classLabels.get( i ), i );

        int[][] confusionMatrix = new int[classLabels.size()][classLabels.size()];
        for( PRObject obj : dataset ) {
            if( labelsIdx.containsKey( obj.getClassLabel() ) ) {
                String label = classifier.classify( obj );
                if( labelsIdx.containsKey( label ) ) {
                    int column = labelsIdx.get( label );
                    int line = labelsIdx.get( obj.getClassLabel() );
                    confusionMatrix[line][column]++;
                } else throw new Exception();
            } else throw new Exception();
        }
        return confusionMatrix;
    }

    public static int[][] compute( List<String> classLabels, Dataset dataset, String[] assignedLabels ) throws Exception {
        Map<String, Integer> labelsIdx = new HashMap<String, Integer>( classLabels.size() );
        int i;
        for( i = 0; i < classLabels.size(); i++ )
            labelsIdx.put( classLabels.get( i ), i );

        int[][] confusionMatrix = new int[classLabels.size()][classLabels.size()];
        for( i = 0; i < dataset.size(); i++ ) {
            PRObject obj = dataset.get( i );
            if( labelsIdx.containsKey( obj.getClassLabel() ) ) {
                if( labelsIdx.containsKey( assignedLabels[i] ) ) {
                    int column = labelsIdx.get( assignedLabels[i] );
                    int line = labelsIdx.get( obj.getClassLabel() );
                    confusionMatrix[line][column]++;
                } else throw new Exception();
            } else throw new Exception();
        }
        return confusionMatrix;
    }
}
