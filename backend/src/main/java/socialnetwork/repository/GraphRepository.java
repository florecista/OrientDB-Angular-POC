package socialnetwork.repository;

import socialnetwork.view.visjs.Edge;
import socialnetwork.view.visjs.Node;

import java.util.List;

public interface GraphRepository {
    List<Node> getNodes();
    List<Edge> getEdges();
    List<Node> getNodesForNode(String vertexId);
    List<Edge> getEdgesForNode(String vertexId);
}
