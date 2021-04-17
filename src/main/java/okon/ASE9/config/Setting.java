package okon.ASE9.config;

public class Setting {
    private final String type;
    private final String value;

    public Setting(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
