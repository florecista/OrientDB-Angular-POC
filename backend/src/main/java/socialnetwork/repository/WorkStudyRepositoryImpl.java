package socialnetwork.repository;

import com.orientechnologies.orient.core.id.ORecordId;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import socialnetwork.OrientDBTools;
import socialnetwork.view.model.WorkStudy;

import java.util.Optional;

@Component
public class WorkStudyRepositoryImpl implements WorkStudyRepository {

    @Autowired
    private OrientGraphFactory factory;

    @Override
    public <S extends WorkStudy> S save(S entity) {
        OrientGraphNoTx graph =  factory.getNoTx();
        if(entity.getId() != null && !entity.getId().isEmpty()) {
            String vId = "#" + entity.getId();
            OrientVertex vertex = OrientDBTools.GetVertexById(graph, vId);
            if(vertex != null) {
                String query = "UPDATE WorkStudy SET type = '" + entity.getType().toString() + "',"
                        + " title = '" + entity.getTitle() + "',"
                        + " position = '" + entity.getPosition() + "',"
                        + "WHERE @rid =" + vId;
                OrientDBTools.executeQuery(graph, query);
            }
        }
        else {
            OrientVertex vertex = OrientDBTools.CreateWorkStudyVertex(graph, entity);
            int id = ((ORecordId) vertex.getId()).getClusterId();
            long pos = ((ORecordId) vertex.getId()).getClusterPosition();
            String modelId = Integer.toString(id) + ":" + Long.toString(pos);
            entity.setId(modelId);
        }

        graph.shutdown();
        return entity;
    }

    @Override
    public <S extends WorkStudy> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<WorkStudy> findById(String id) {
        OrientVertex vertex = OrientDBTools.GetVertexById(factory.getNoTx(), "#" + id);
        WorkStudy event = domainObjectFromVertex(vertex);
        return Optional.of(event);
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<WorkStudy> findAll() {
        return null;
    }

    @Override
    public Iterable<WorkStudy> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(WorkStudy entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends WorkStudy> entities) {

    }

    @Override
    public void deleteAll() {

    }

    private WorkStudy domainObjectFromVertex(OrientVertex vertex) {
        int id = ((ORecordId) vertex.getId()).getClusterId();
        long pos = ((ORecordId) vertex.getId()).getClusterPosition();
        String vId = Integer.toString(id) + ":" + Long.toString(pos);

        WorkStudy workStudy = new WorkStudy();
        workStudy.setId(vId);

        return workStudy;
    }

}
