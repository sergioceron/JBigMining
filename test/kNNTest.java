import classifiers.KNN;
import core.Dataset;
import core.interfaces.IClassifier;
import core.interfaces.IParser;
import core.parsers.ARFFParser;
import dissimilarities.Euclidean;
import measures.SimpleErrorMeasure;
import samplers.CrossValidation;
import utils.ClassifyDataset;

import java.util.List;

/**
 * Created by sergio on 19/05/15.
 */
public class kNNTest {
    public static void main( String[] args ) {
        IParser parser = new ARFFParser();
        IClassifier kNN = new KNN();
        Dataset test = parser.parse( "./datasets/iris/Split Data/Testing_0.arff" );
        Dataset train = parser.parse( "./datasets/iris/Split Data/Training_0.arff" );
        train.getMetaObject().setDissimilarity( new Euclidean() );
        kNN.train( train );
        String[] labels = ClassifyDataset.classify( test, kNN );
        for( int i = 0; i < test.size(); i++ )
            System.out.println( test.get( i ) + " -> " + labels[i] );


        try {
            Dataset source = parser.parse( "./datasets/iris/iris.arff" );
            source.getMetaObject().setDissimilarity( new Euclidean() );
            SimpleErrorMeasure errorMeasure = new SimpleErrorMeasure();
            CrossValidation sampler = new CrossValidation();
            List<Dataset> allParts = sampler.makeSampling( source, 10 );

            double sum = 0;
            for( int i = 0; i < 10; i++ ) {
                Dataset testing = allParts.get( i );
                Dataset training = new Dataset( allParts.get( 0 ).getMetaObject() );
                for( int j = 0; j < allParts.size(); j++ ) {
                    if( j != i ) {
                        Dataset current = allParts.get( j );
                        for( int k = 0; k < current.size(); k++ ) {
                            training.add( current.get( k ) );
                        }
                    }
                }
                kNN.train( training );
                double error = ClassifyDataset.errorInClassification( testing, kNN, errorMeasure );
                sum = sum + error;
            }
            System.out.println( 1 - ( sum / 10.0 ) );
        } catch( Throwable e ) {
            e.printStackTrace();
        }

    }
}
