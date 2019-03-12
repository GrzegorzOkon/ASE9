package okon.ASE9;

public class MessageFormatter {
    private final Message message;

    public MessageFormatter(Message message) {
        this.message = message;
    }

    public String format() {
        String output = String.format("%-20s %-30s %6s %% %11s %% %9s %% %9s %%",
                message.getServerName(), message.getThreadPool(), message.getUserBusy(), message.getSystemBusy(), message.getIoBusy(), message.getIdle());

        return output;
    }
}
