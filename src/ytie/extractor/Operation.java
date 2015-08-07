package ytie.extractor;

/**
 * Created by Jacob on 4/10/2015.
 */
class Operation {
    private int index;
    private String identifier;
    private OperationType type;

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public OperationType getOperationType() {
        return this.type;
    }

    public void setOperationType(OperationType type) {
        this.type = type;
    }

    public String apply(String signature) {
        switch (this.type) {
            case REVERSE:
                signature = new StringBuilder(signature).reverse().toString();
                break;
            case SWAP:
                StringBuilder swapString = new StringBuilder(signature);
                swapString.setCharAt(0, signature.charAt(this.index));
                swapString.setCharAt(this.index, signature.charAt(0));
                signature = swapString.toString();
                break;
            case SLICE:
                signature = signature.substring(index);
                break;
            default:
                throw new UnsupportedOperationException("Unknown operation type");
        }
        return signature;
    }
}
