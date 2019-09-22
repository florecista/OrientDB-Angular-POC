package socialnetwork.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Entity {
    private String name;
    private String category;
    private String icon;
    private List<SubEntity> attributes = new ArrayList<>();

    //Test equal, override equals() and hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity ent = (Entity) o;
        return Objects.equals(name, ent.name) && Objects.equals(category, ent.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category);
    }
}
