package ytie.extractor.exceptions;

/**
 * Created by Jacob on 4/9/2015.
 */
public class DecryptException extends Exception {
    public DecryptException() {
        super();
    }

    public DecryptException(String message) {
        super(message);
    }

    public DecryptException(String message, Throwable cause) {
        super(message, cause);
    }

    public DecryptException(Throwable cause) {
        super(cause);
    }
}
