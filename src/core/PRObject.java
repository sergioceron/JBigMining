package core;

import java.util.Arrays;

/**
 * Created by sergio on 15/05/15.
 */
public class PRObject implements Cloneable, Comparable<PRObject> {
    private MetaObject metaObject;
    private String classLabel;
    private String groupLabel;
    private Object[] featuresValues;
    private int index;
    private double order;

    public PRObject( MetaObject metaObject ) {
        this.metaObject = metaObject;
        this.featuresValues = new Object[metaObject.getFeatureDescriptions().length];
    }


    public boolean isIncomplete( int thisFeature ) {
        return ( featuresValues[thisFeature] == null ) ? true : false;
    }

    @Override
    public PRObject clone() throws CloneNotSupportedException {
        PRObject theClone = new PRObject( metaObject );
        for( int i = 0; i < featuresValues.length; i++ ) {
            theClone.featuresValues[i] = featuresValues[i];
        }
        theClone.index = index;
        theClone.classLabel = classLabel;
        return theClone;
    }

    @Override
    public boolean equals( Object o ) {
        if( this == o ) return true;
        if( o == null || getClass() != o.getClass() ) return false;

        PRObject prObject = (PRObject) o;

        if( classLabel != null ? !classLabel.equals( prObject.classLabel ) : prObject.classLabel != null ) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals( featuresValues, prObject.featuresValues );

    }

    public boolean equalsInFeatures( PRObject prObject ) {
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals( featuresValues, prObject.featuresValues );

    }

    @Override
    public String toString() {
        return "core.PRObject{" +
                "featuresValues=" + Arrays.toString( featuresValues ) +
                ", classLabel='" + classLabel + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return featuresValues != null ? Arrays.hashCode( featuresValues ) : 0;
    }

    @Override
    public int compareTo( PRObject o ) {
        if( this.order > o.getOrder() )
            return -1;
        else if( this.order == o.getOrder() )
            return 0;
        else return 1;
    }

    public MetaObject getMetaObject() {
        return metaObject;
    }

    public void setMetaObject( MetaObject metaObject ) {
        this.metaObject = metaObject;
    }

    public String getClassLabel() {
        return classLabel;
    }

    public void setClassLabel( String classLabel ) {
        this.classLabel = classLabel;
    }

    public String getGroupLabel() {
        return groupLabel;
    }

    public void setGroupLabel( String groupLabel ) {
        this.groupLabel = groupLabel;
    }

    public Object[] getFeaturesValues() {
        return featuresValues;
    }

    public void setFeaturesValues( Object[] featuresValues ) {
        this.featuresValues = featuresValues;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex( int index ) {
        this.index = index;
    }

    public double getOrder() {
        return order;
    }

    public void setOrder( double order ) {
        this.order = order;
    }
}
