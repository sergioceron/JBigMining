package exceptions;

/**
 * Created by sergio on 15/05/15.
 */
public class ArgumentNullException extends Throwable {
    public ArgumentNullException( String dataSet, String s ) {
        super( s + " [dataset=" + dataSet + "]" );
    }

    public ArgumentNullException( String s ) {
        super( s );
    }
}
