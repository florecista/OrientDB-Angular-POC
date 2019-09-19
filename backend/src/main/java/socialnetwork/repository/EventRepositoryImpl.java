package socialnetwork.repository;

import com.orientechnologies.orient.core.id.ORecordId;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import socialnetwork.OrientDBTools;
import socialnetwork.view.model.Event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class EventRepositoryImpl implements EventRepository {

    @Autowired
    private OrientGraphFactory factory;

    @Override
    public <S extends Event> S save(S entity) {
        OrientGraphNoTx graph =  factory.getNoTx();
        if(entity.getId() != null && !entity.getId().isEmpty()) {
            String vId = "#" + entity.getId();
            OrientVertex vertex = OrientDBTools.GetVertexById(graph, vId);
            if(vertex != null) {
                String query = "UPDATE Event SET type = '" + entity.getType().toString() + "',"
                        + " title = '" + entity.getTitle() + "',"
                        + " notes = '" + entity.getNotes() + "',"
                        + "WHERE @rid =" + vId;
                OrientDBTools.executeQuery(graph, query);
            }
        }
        else {
            OrientVertex vertex = OrientDBTools.CreateEventVertex(graph, entity);
            int id = ((ORecordId) vertex.getId()).getClusterId();
            long pos = ((ORecordId) vertex.getId()).getClusterPosition();
            String modelId = Integer.toString(id) + ":" + Long.toString(pos);
            entity.setId(modelId);
        }

        graph.shutdown();
        return entity;
    }

    @Override
    public <S extends Event> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Event> findById(String id) {
        OrientGraphNoTx graph =  factory.getNoTx();
        OrientVertex vertex = OrientDBTools.GetVertexById(graph, "#" + id);
        Event event = domainObjectFromVertex(vertex);
        graph.shutdown();
        return Optional.of(event);
    }

    @Override
    public boolean existsById(String id) {
        if (OrientDBTools.GetVertexById(factory.getNoTx(), "#" + id) == null) {
            return false;
        }
        return true;
    }

    @Override
    public Iterable<Event> findAll() {
        List<Event> result = new ArrayList<>();
        Iterable<OrientVertex> events = OrientDBTools.GetAllVerticesForType(factory.getNoTx(), "Event");
        for(OrientVertex vertex : events) {
            String typeString = vertex.getProperty("type");
            Event.EventType eventType = Event.EventType.valueOf(typeString.toUpperCase());
            String title = vertex.getProperty("title");
            String notes = vertex.getProperty("notes");
            Date startDatetime = vertex.getProperty("startDatetime");
            Date endDatetime = vertex.getProperty("endDatetime");
            Event event = new Event(eventType, title, notes, startDatetime, endDatetime);
            event.setId(getVertexIdForWeb(vertex));
            result.add(event);
        }
        return result;
    }

    @Override
    public Iterable<Event> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String id) {
        OrientVertex vertex = OrientDBTools.GetVertexById(factory.getNoTx(), "#" + id);
        OrientDBTools.delete(factory.getNoTx(), vertex);
    }

    @Override
    public void delete(Event entity) {
        OrientVertex vertex = OrientDBTools.GetVertexById(factory.getNoTx(), "#" + entity.getId());
        OrientDBTools.delete(factory.getNoTx(), vertex);
    }

    @Override
    public void deleteAll(Iterable<? extends Event> entities) {

    }

    @Override
    public void deleteAll() {

    }

    private Event domainObjectFromVertex(OrientVertex vertex) {
        int id = ((ORecordId) vertex.getId()).getClusterId();
        long pos = ((ORecordId) vertex.getId()).getClusterPosition();
        String vId = Integer.toString(id) + ":" + Long.toString(pos);

        Event event = new Event();
        event.setId(vId);

        return event;
    }

    private String getVertexIdForWeb(Vertex vertex) {
        int id = ((ORecordId)vertex.getId()).getClusterId();
        long pos = ((ORecordId)vertex.getId()).getClusterPosition();
        return Integer.toString(id) + ":" + Long.toString(pos);
    }
}
