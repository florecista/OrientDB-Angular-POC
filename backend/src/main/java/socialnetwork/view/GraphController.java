package socialnetwork.view;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import socialnetwork.repository.GraphRepository;
import socialnetwork.view.visjs.Edge;
import socialnetwork.view.visjs.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
public class GraphController {

    private final GraphRepository graphRepository;

    public GraphController(GraphRepository graphRepository) {
        this.graphRepository = graphRepository;
    }

    @GetMapping("/graph")
    public Map<String, Set<?>> graph() {
        Set nodes = new HashSet<Node>();
        Set edges = new HashSet<Edge>();

        fillNodeAndEdges(nodes, edges);

        Map<String, Set<?>> map = new HashMap();
        map.put("nodes", nodes);
        map.put("edges", edges);

        return map;
    }

    private void fillNodeAndEdges(Set<Node> nodes, Set<Edge> edges) {
        nodes.addAll(graphRepository.getNodes());
//        for(Node node : graphRepository.getNodes()) {
//            nodes.add(node);
//        }

        edges.addAll(graphRepository.getEdges());
//        for(Edge edge : graphRepository.getEdges()) {
//            edges.add(edge);
//        }
    }
}