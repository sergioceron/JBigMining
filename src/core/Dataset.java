package core;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by sergio on 15/05/15.
 */
public class Dataset extends ArrayList<PRObject> implements Cloneable {
    private MetaObject metaObject;
    private double[][] dissimilarityMatrix;

    public Dataset(MetaObject metaObject) {
        this.metaObject = metaObject;
    }

    public Dataset(Collection<? extends PRObject> c, MetaObject metaObject) {
        super(c);
        this.metaObject = metaObject;
    }

    public Dataset(int initialCapacity, MetaObject metaObject) {
        super(initialCapacity);
        this.metaObject = metaObject;
    }

    public int find(PRObject query) {
        int found = -1;
        int i = 0;
        while ((i < size()) && (found == -1)) {
            boolean equal = true;
            if (query.getClassLabel() == this.get(i).getClassLabel()) {
                int j = 0;
                while (equal && (j < query.getFeaturesValues().length)) {
                    if (query.getFeaturesValues()[j].toString() == this.get(i).getFeaturesValues()[j].toString())
                        equal = false;
                    j++;
                }
            } else
                equal = false;
            if (equal)
                found = i;
            i++;
        }
        return found;
    }

    @Override
    public Dataset clone() {
        Dataset theClone = new Dataset(size(), metaObject);
        for (PRObject obj : this)
            theClone.add(obj);
        return theClone;
    }

    public Dataset cloneDeep() throws CloneNotSupportedException {
        Dataset theClone = new Dataset(size(), metaObject);
        FeatureDescription[] desc = new FeatureDescription[metaObject.getFeatureDescriptions().length];
        for (int i = 0; i < desc.length; i++) {
            FeatureDescription current = new FeatureDescription(metaObject.getFeatureDescriptions()[i].getName(),
                    metaObject.getFeatureDescriptions()[i].getType());
            desc[i] = current;
        }
        MetaObject meta = new MetaObject(desc);
        meta.setDissimilarity(this.metaObject.getDissimilarity());
        for (PRObject obj : this)
            theClone.add(obj.clone());
        return theClone;
    }

    public MetaObject getMetaObject() {
        return metaObject;
    }

    public void setMetaObject(MetaObject metaObject) {
        this.metaObject = metaObject;
    }

    public double[][] getDissimilarityMatrix() {
        return dissimilarityMatrix;
    }

    public void setDissimilarityMatrix(double[][] dissimilarityMatrix) {
        this.dissimilarityMatrix = dissimilarityMatrix;
    }
}
