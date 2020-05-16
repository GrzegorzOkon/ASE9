package okon.ASE9;

import java.util.List;

public abstract class LoadService {
    public abstract List<Message> calculateDatabaseLoad(int seconds);
}
