package ytie.extractor.exceptions;

/**
 * Created by Jacob on 4/12/2015.
 */
public class ParseSignatureException extends Exception {
    public ParseSignatureException() {
    }

    public ParseSignatureException(String message) {
        super(message);
    }

    public ParseSignatureException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseSignatureException(Throwable cause) {
        super(cause);
    }

    public ParseSignatureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
