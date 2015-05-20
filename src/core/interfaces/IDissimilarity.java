package core.interfaces;

/**
 * Created by sergio on 15/05/15.
 */
public interface IDissimilarity<T> {
    double compare( T source, T compareTo );
}
