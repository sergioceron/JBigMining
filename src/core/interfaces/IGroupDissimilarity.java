package core.interfaces;

/**
 * Created by sergio on 17/05/15.
 */
public interface IGroupDissimilarity<T, K> {
    double compare( T source, K compareTo );
}
