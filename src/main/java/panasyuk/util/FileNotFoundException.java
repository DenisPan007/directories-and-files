package panasyuk.util;

import org.springframework.data.util.Pair;

public class FileNotFoundException extends RuntimeException {

    private String localizedMessage;

    public FileNotFoundException(Pair<String, String> localizedMessages) {
        super(localizedMessages.getFirst());
        localizedMessage = localizedMessages.getSecond();
    }

    @Override
    public String getLocalizedMessage() {
        return localizedMessage;
    }
}
