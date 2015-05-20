package core;

import core.interfaces.IDissimilarity;
import core.interfaces.ISimilarity;

/**
 * Created by sergio on 15/05/15.
 */
public class MetaObject {
    private FeatureDescription[] featureDescriptions;
    private ISimilarity<PRObject> similarity;
    private IDissimilarity<PRObject> dissimilarity;

    public MetaObject( FeatureDescription[] featureDescriptions ) {
        if( featureDescriptions == null )
            throw new NullPointerException( "Unable to create a core.MetaObject from a null core.FeatureDescription array." );
        this.featureDescriptions = featureDescriptions;
    }

    public FeatureDescription[] getFeatureDescriptions() {
        return featureDescriptions;
    }

    public void setFeatureDescriptions( FeatureDescription[] featureDescriptions ) {
        this.featureDescriptions = featureDescriptions;
    }

    public ISimilarity<PRObject> getSimilarity() {
        return similarity;
    }

    public void setSimilarity( ISimilarity<PRObject> similarity ) {
        this.similarity = similarity;
    }

    public IDissimilarity<PRObject> getDissimilarity() {
        return dissimilarity;
    }

    public void setDissimilarity( IDissimilarity<PRObject> dissimilarity ) {
        this.dissimilarity = dissimilarity;
    }
}
