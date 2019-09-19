package socialnetwork.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubEntity {
    private String name;
    private String type;
    private String description;
    private Object value;
    
    public SubEntity() {}
    
    public SubEntity(String name, String type, String description, Object value) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.value = value;
    }
}
