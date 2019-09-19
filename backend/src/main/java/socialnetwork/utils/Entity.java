package socialnetwork.utils;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Entity {
    private String name;
    private String category;
    private String icon;
    private List<SubEntity> attributes = new ArrayList<>();
}
