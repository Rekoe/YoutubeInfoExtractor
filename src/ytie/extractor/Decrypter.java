//Help from https://github.com/flagbug/YoutubeExtractor with extracting

package ytie.extractor;

import ytie.extractor.exceptions.PlayerMatchException;
import ytie.util.HTTPUtility;
import ytie.extractor.exceptions.ParseSignatureException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jacob on 4/10/2015.
 */
class Decrypter {
    private static final Pattern html5PlayerPattern = Pattern.compile("html5player-(.+)/html5player\\.([a-z]+)");
    private static final Pattern signatureFuncPattern = Pattern.compile("\\.sig\\|\\|([a-zA-Z0-9$]+)\\(");
    private static final Pattern functionPattern = Pattern.compile("\\w+\\.(\\w+)\\(");
    private static final Pattern charSwapSignaturePattern = Pattern.compile("\\(\\w+,(\\d+)\\)");
    private static final Pattern sliceSignaturePattern = Pattern.compile("\\(\\w+,(\\d+)\\)");
    private static final String reverseRegex = ":\\bfunction\\b\\(\\w+\\)";
    private static final String funcBodyRegex = "(\\{([^\\{\\}]|)*\\})";
    private static final String sliceRegex = ":\\bfunction\\b\\([a],b\\).(\\breturn\\b)?.?\\w+\\.";
    private static final String swapRegex = ":\\bfunction\\b\\(\\w+\\,\\w\\).\\bvar\\b.\\bc=a\\b";
    private static final String funcNameRegex = "%s\\(\\w+\\)%s";

    private Map<String, List<Operation>> operationMap = new HashMap<String, List<Operation>>();

    public String decryptSignature(String encryptedSig, String playerUrl) throws PlayerMatchException, IOException, ParseSignatureException {
        if (playerUrl.startsWith("//")) {
            playerUrl = "https:" + playerUrl;
        }

        Matcher html5PlayerVersionMatcher = html5PlayerPattern.matcher(playerUrl);
        if (!html5PlayerVersionMatcher.find()) {
            throw new PlayerMatchException("Unable to find html5player URL");
        }

        String playerId = html5PlayerVersionMatcher.group(1);
        String playerType = html5PlayerVersionMatcher.group(2);
        if (!playerType.equals("js")) {
            throw new UnsupportedOperationException("Not able to support non-Javascript players");
        }

        List<Operation> operations;
        if (!operationMap.containsKey(playerId)) {
            String source = HTTPUtility.downloadPageSource(playerUrl);
            operations = parseSignatureJavascript(source);
            this.operationMap.put(playerId, operations);
        }

        operations = this.operationMap.get(playerId);
        return applyOperations(encryptedSig, operations);
    }

    private static List<Operation> parseSignatureJavascript(String source) throws ParseSignatureException {
        Matcher match = signatureFuncPattern.matcher(Pattern.quote(source));
        if (!match.find()) {
            throw new ParseSignatureException("Unable to find function signature in Javascript source");
        }

        List<Operation> operations = new ArrayList<Operation>();
        String funcName = match.group(1);
        Pattern funcPattern = Pattern.compile(String.format(funcNameRegex, funcName, funcBodyRegex));
        Matcher funcBodyMatcher = funcPattern.matcher(source);
        if (!funcBodyMatcher.find()) {
            throw new ParseSignatureException("Unable to match for function body in Javascript source");
        }

        String funcBody = funcBodyMatcher.group(1);
        String[] lines = funcBody.split(";");
        String line;
        String idReverse = null;
        String idSlice = null;
        String idCharSwap = null;
        String funcIdentifier;

        //first we loop to find the function identifiers
        for (int i = 1; i <= lines.length - 2 && (idCharSwap == null || idReverse == null || idSlice == null); i++) {
            line = lines[i];
            funcIdentifier = getFunctionFromLine(line);
            Matcher reverseMatcher = Pattern.compile(funcIdentifier + reverseRegex).matcher(source);
            Matcher sliceMatcher = Pattern.compile(funcIdentifier + sliceRegex).matcher(source);
            Matcher swapMatcher = Pattern.compile(funcIdentifier + swapRegex).matcher(source);
            if (reverseMatcher.find() && idReverse == null) {
                idReverse = funcIdentifier;
            } else if (sliceMatcher.find() && idSlice == null) {
                idSlice = funcIdentifier;
            } else if (swapMatcher.find() && idCharSwap == null) {
                idCharSwap = funcIdentifier;
            }
        }

        for (int i = 1; i <= lines.length - 2; i++) {
            line = lines[i];
            funcIdentifier = getFunctionFromLine(line);
            Matcher matcher;
            Operation op = new Operation();

            if ((matcher = charSwapSignaturePattern.matcher(line)).find() && funcIdentifier.equals(idCharSwap)) {
                op.setOperationType(OperationType.SWAP);
                op.setIndex(Integer.parseInt(matcher.group(1)));
            } else if ((matcher = sliceSignaturePattern.matcher(line)).find() && funcIdentifier.equals(idSlice)) {
                op.setOperationType(OperationType.SLICE);
                op.setIndex(Integer.parseInt(matcher.group(1)));
            } else if (funcIdentifier == idReverse) {
                op.setOperationType(OperationType.REVERSE);
            } else {
                throw new UnsupportedOperationException(String.format("Unexpected operation detected: %s", line));
            }

            op.setIdentifier(funcIdentifier);
            op.setIndex(Integer.parseInt(matcher.group(1)));
            operations.add(op);
        }

        return operations;
    }

    private static String getFunctionFromLine(String line) throws ParseSignatureException {
        Matcher functionMatcher = functionPattern.matcher(line);
        if (functionMatcher.find()) {
            return functionMatcher.group(1).trim();
        } else {
            throw new ParseSignatureException(String.format("Unable to match for function name in line: %s", line));
        }
    }

    private static String applyOperations(String encryptedSignature, List<Operation> operations) {
        String signature = encryptedSignature;
        for (Operation op : operations) {
            signature = op.apply(signature);
        }
        return signature;
    }
}
