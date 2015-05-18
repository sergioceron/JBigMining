package exceptions;

/**
 * Created by sergio on 16/05/15.
 */
public class ArgumentOutOfRangeException extends Throwable {
    public ArgumentOutOfRangeException(String variable, Object value, String s) {
        super( s + " [" + variable + "=" + value + "]" );
    }

    public ArgumentOutOfRangeException(String s) {
        super(s);
    }
}
