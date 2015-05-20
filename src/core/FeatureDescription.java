package core;

import java.util.List;

/**
 * Created by sergio on 15/05/15.
 */
public class FeatureDescription {
    private String name;
    private Type type;
    private List<String> values;

    public FeatureDescription( String name ) {
        this.name = name;
    }

    public FeatureDescription( String name, Type type ) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType( Type type ) {
        this.type = type;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues( List<String> values ) {
        this.values = values;
    }

    public void putValue( String value ) {
        if( !values.contains( value ) )
            values.add( value );
    }

    public enum Type {
        NOMINAL,
        BOOLEAN,
        ORDINAL,
        REAL,
        INTEGER,
        NUMERIC
    }
}
