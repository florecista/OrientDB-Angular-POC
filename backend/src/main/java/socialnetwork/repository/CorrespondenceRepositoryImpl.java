package socialnetwork.repository;

import com.orientechnologies.orient.core.id.ORecordId;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import socialnetwork.OrientDBTools;
import socialnetwork.view.model.Correspondence;

import java.util.Optional;

@Component
public class CorrespondenceRepositoryImpl implements CorrespondenceRepository {

    @Autowired
    private OrientGraphFactory factory;

    @Override
    public <S extends Correspondence> S save(S entity) {
        OrientGraphNoTx graph =  factory.getNoTx();
        if(entity.getId() != null && !entity.getId().isEmpty()) {
            String vId = "#" + entity.getId();
            OrientVertex vertex = OrientDBTools.GetVertexById(graph, vId);
            if(vertex != null) {
                String query = "UPDATE Correspondence SET type = '" + entity.getType().toString() + "',"
                        + " title = '" + entity.getTitle() + "',"
                        + " position = '" + entity.getNotes() + "',"
                        + "WHERE @rid =" + vId;
                OrientDBTools.executeQuery(graph, query);
            }
        }
        else {
            OrientVertex vertex = OrientDBTools.CreateCorrespondenceVertex(graph, entity);
            int id = ((ORecordId) vertex.getId()).getClusterId();
            long pos = ((ORecordId) vertex.getId()).getClusterPosition();
            String modelId = Integer.toString(id) + ":" + Long.toString(pos);
            entity.setId(modelId);
        }

        graph.shutdown();
        return entity;
    }

    @Override
    public <S extends Correspondence> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Correspondence> findById(String id) {
        OrientVertex vertex = OrientDBTools.GetVertexById(factory.getNoTx(), "#" + id);
        Correspondence correspondence = domainObjectFromVertex(vertex);
        return Optional.of(correspondence);
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<Correspondence> findAll() {
        return null;
    }

    @Override
    public Iterable<Correspondence> findAllById(Iterable<String> strings) {
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
    public void delete(Correspondence entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends Correspondence> entities) {

    }

    @Override
    public void deleteAll() {

    }

    private Correspondence domainObjectFromVertex(OrientVertex vertex) {
        int id = ((ORecordId) vertex.getId()).getClusterId();
        long pos = ((ORecordId) vertex.getId()).getClusterPosition();
        String vId = Integer.toString(id) + ":" + Long.toString(pos);

        Correspondence correspondence = new Correspondence();
        correspondence.setId(vId);

        return correspondence;
    }
}
