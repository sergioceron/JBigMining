package samplers;

import core.Dataset;
import core.Structure;
import core.interfaces.IClusterer;
import core.interfaces.ISampler;
import exceptions.ArgumentOutOfRangeException;
import utils.DatasetUtils;
import utils.StructByClasses;

/**
 * Created by sergio on 17/05/15.
 */
public class SubclassSampler implements ISampler {

    private double trainingRatio = 0.9;

    private IClusterer clusteringAlgorithm;

    public void makeSampling( Dataset dataset, Dataset training, Dataset testing ) {
        training = new Dataset( dataset.getMetaObject() );
        testing = new Dataset( dataset.getMetaObject() );

        Structure classes = StructByClasses.structuralize( dataset );
        for( Structure.Item c : classes ) {
            Structure currentSubClasses = clusteringAlgorithm.findClusters( c.getDataset() );
            for( Structure.Item sc : currentSubClasses )
                if( sc.getDataset().size() > 1 ) {
                    int objCount = (int) Math.round( ( 1 - trainingRatio ) * sc.getDataset().size() );
                    Dataset objs = DatasetUtils.randomSample( sc.getDataset(), objCount );
                    sc.getDataset().remove( objs );
                    training.addAll( sc.getDataset() );
                    testing.addAll( objs );
                }

        }
    }

    public SubclassSampler() {
        // TODO: where is this algorithm?
        //clusteringAlgorithm = new HiSCC();
    }

    public double getTrainingRatio() {
        return trainingRatio;
    }

    public void setTrainingRatio( double trainingRatio ) throws ArgumentOutOfRangeException {
        if( trainingRatio > 0 && trainingRatio < 1 )
            this.trainingRatio = trainingRatio;
        else
            throw new ArgumentOutOfRangeException( "value", trainingRatio, "Unable to set training ratio: Value out of range (0,1)" );
    }

    public IClusterer getClusteringAlgorithm() {
        return clusteringAlgorithm;
    }

    public void setClusteringAlgorithm( IClusterer clusteringAlgorithm ) {
        this.clusteringAlgorithm = clusteringAlgorithm;
    }
}
