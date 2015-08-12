package ytie.extractor.exceptions;

/**
 * Created by Jacob on 4/7/2015.
 */
public class InfoExtractorException extends Exception {
    public InfoExtractorException() {
        super();
    }

    public InfoExtractorException(String message) {
        super(message);
    }

    public InfoExtractorException(String message, Throwable cause) {
        super(message, cause);
    }

    public InfoExtractorException(Throwable cause) {
        super(cause);
    }
}
