package okon.ASE9;

import java.util.ArrayList;
import java.util.List;

public class MessageFormatter {
    private final Message message;

    public MessageFormatter(Message message) {
        this.message = message;
    }

    public List<byte[]> format() {
        List<byte[]> text = new ArrayList<>();

        text.add(message.getTestText().getBytes());
        //text.add(message.getDescription().getBytes());
        //text.add((message.getUrl() + " ****** " + (int) (((float) message.getCorrectChecks() / (float) message.getAllChecks()) * 100) + " %").getBytes());

        return text;
    }
}
