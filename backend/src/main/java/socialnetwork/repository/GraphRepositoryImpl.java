package socialnetwork.repository;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import com.tinkerpop.blueprints.impls.orient.OrientVertexType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import socialnetwork.OrientDBTools;
import socialnetwork.utils.Util;
import socialnetwork.view.model.*;
import socialnetwork.view.visjs.Edge;
import socialnetwork.view.visjs.Node;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static socialnetwork.utils.Util.convertOrientVertexPersonToModelPerson;

@Component
public class GraphRepositoryImpl implements GraphRepository {

    @Autowired
    private OrientGraphFactory factory;

    @Override
    public List<Node> getNodes() {
        OrientGraphNoTx graph =  factory.getNoTx();

        List<Node> result = new ArrayList<>();
        for(Vertex vertex : graph.getVertices()) {
            String label = vertex.getId().toString();
            OrientVertexType vertexType = ((OrientVertex)vertex).getType();
            if(vertexType.getName().equals("Person")) {
                String firstName = vertex.getProperty("firstName");
                String lastName = vertex.getProperty("lastName");
                Date dateOfBirth = vertex.getProperty("dateOfBirth");
                label = firstName + " " + lastName + " (" + Util.getStringForDate(dateOfBirth) + ")";
            }
            result.add(new Node(vertex.getId().toString(), label));
        }

        graph.shutdown();
        return result;
    }

    @Override
    public List<Edge> getEdges() {
        OrientGraphNoTx graph =  factory.getNoTx();

        List<Edge> result = new ArrayList<>();
        for(com.tinkerpop.blueprints.Edge edge : graph.getEdges()) {
            result.add(new Edge(edge.getVertex(Direction.OUT).getId().toString(), edge.getVertex(Direction.IN).getId().toString()));
        }

        graph.shutdown();
        return result;
    }

    @Override
    public List<Node> getNodesForNode(String vertexId) {
        List<Node> result = new ArrayList<>();

        OrientGraphNoTx graph =  factory.getNoTx();
        OrientVertex vertex = OrientDBTools.GetVertexById(graph, vertexId);
        Person model = convertOrientVertexPersonToModelPerson(vertex);

        result.add(new Node(vertexId, model.toString()));

        for(com.tinkerpop.blueprints.Edge personToEdge : vertex.getEdges(Direction.OUT, "lives-at")) {
            OrientVertex childVertex = (OrientVertex) personToEdge.getVertex(Direction.IN);
            result.add(new Node(childVertex.getId().toString(), "Address"));
        }

        for(com.tinkerpop.blueprints.Edge personToEdge : vertex.getEdges(Direction.OUT, "connected-to")) {
            OrientVertex childVertex = (OrientVertex) personToEdge.getVertex(Direction.IN);
            result.add(new Node(childVertex.getId().toString(), "Event"));
        }

        for(com.tinkerpop.blueprints.Edge personToEdge : vertex.getEdges(Direction.OUT, "communication")) {
            OrientVertex childVertex = (OrientVertex) personToEdge.getVertex(Direction.IN);
            result.add(new Node(childVertex.getId().toString(), "Correspondence"));
        }

        for(com.tinkerpop.blueprints.Edge personToEdge : vertex.getEdges(Direction.OUT, "worked-or-studied")) {
            OrientVertex childVertex = (OrientVertex) personToEdge.getVertex(Direction.IN);
            result.add(new Node(childVertex.getId().toString(), "WorkStudy"));
        }

        graph.shutdown();
        return result;
    }

    @Override
    public List<Edge> getEdgesForNode(String vertexId) {
        List<Edge> result = new ArrayList<>();

        OrientGraphNoTx graph =  factory.getNoTx();
        OrientVertex vertex = OrientDBTools.GetVertexById(graph, vertexId);

        for(com.tinkerpop.blueprints.Edge personToEdge : vertex.getEdges(Direction.OUT, "lives-at")) {
            result.add(new Edge(vertex.getId().toString(), personToEdge.getVertex(Direction.IN).getId().toString()));
        }

        for(com.tinkerpop.blueprints.Edge personToEdge : vertex.getEdges(Direction.OUT, "connected-to")) {
            result.add(new Edge(vertex.getId().toString(), personToEdge.getVertex(Direction.IN).getId().toString()));
        }

        for(com.tinkerpop.blueprints.Edge personToEdge : vertex.getEdges(Direction.OUT, "communication")) {
            result.add(new Edge(vertex.getId().toString(), personToEdge.getVertex(Direction.IN).getId().toString()));
        }

        for(com.tinkerpop.blueprints.Edge personToEdge : vertex.getEdges(Direction.OUT, "worked-or-studied")) {
            result.add(new Edge(vertex.getId().toString(), personToEdge.getVertex(Direction.IN).getId().toString()));
        }

        graph.shutdown();
        return result;
    }
}
