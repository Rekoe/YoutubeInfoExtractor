package ytie.extractor.exceptions;

/**
 * Created by Jacob on 4/12/2015.
 */
public class PlayerMatchException extends Exception {
    public PlayerMatchException() {
    }

    public PlayerMatchException(String message) {
        super(message);
    }

    public PlayerMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlayerMatchException(Throwable cause) {
        super(cause);
    }

    public PlayerMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
