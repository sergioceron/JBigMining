package core;

/**
 * Created by sergio on 15/05/15.
 */
public class FeatureDescription {
    private String name;
    private Type type;

    public FeatureDescription(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        NOMINAL,
        BOOLEAN,
        ORDINAL,
        REAL,
        INTEGER
    }
}
