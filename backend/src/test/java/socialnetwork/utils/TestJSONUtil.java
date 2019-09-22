package socialnetwork.utils;

import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.core.IsNot;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class TestJSONUtil {
    @Test
    public void testGetEntityList() {
        SubEntity firstNameSubEntity = new SubEntity("firstName", "string", "", null);
        SubEntity lastNameSubEntity = new SubEntity("lastName", "string", "", null);
        Entity personEntity = new Entity();
        personEntity.setName("Person");
        personEntity.setCategory("Vertex");
        personEntity.getAttributes().add(firstNameSubEntity);
        personEntity.getAttributes().add(lastNameSubEntity);

        JSONUtil jsonUtil = new JSONUtil();
        List<Entity> entities = jsonUtil.getEntityList();
        assertNotNull(entities);
        assertThat(entities, IsNot.not(IsEmptyCollection.empty()));
        assertThat(entities, hasSize(5));
    }
}
