package socialnetwork;

import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import com.tinkerpop.blueprints.impls.orient.OrientVertexType;
import socialnetwork.utils.Entity;
import socialnetwork.utils.JSONUtil;
import socialnetwork.utils.SubEntity;
import socialnetwork.view.model.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OrientDBTools {

    public static void safeVertexPropertySet(OrientVertex vertex, String propertyName, String propertyValue) {
        if (propertyValue != null) {
            vertex.setProperty(propertyName, propertyValue);
        }
    }

    public static void safeVertexPropertySet(OrientVertex vertex, String propertyName, Object propertyValue) {
        if (propertyValue != null) {
            vertex.setProperty(propertyName, propertyValue);
        }
    }

    public static OrientVertex CreatePersonVertex(OrientGraphNoTx graph, Person person) {
        OrientVertexType personClass = graph.getVertexType("Person");
        if (personClass == null)
            createPersonSchema(graph);

        // create the item in the graph database.
        OrientVertex vPerson = graph.addVertex("class:Person");
        ORecordId recordId = (ORecordId) vPerson.getId();

        // Might get rid of this
        vPerson.setProperty("key", recordId.toString());

        safeVertexPropertySet(vPerson, "firstName", person.getFirstName());
        safeVertexPropertySet(vPerson, "lastName", person.getLastName());
        safeVertexPropertySet(vPerson, "dateOfBirth", person.getDateOfBirth());
        safeVertexPropertySet(vPerson, "email", person.getEmail());
        safeVertexPropertySet(vPerson, "profileImagePath", person.getProfileImagePath());

        return vPerson;
    }

    public static OrientVertex CreateAddressVertex(OrientGraphNoTx graph, Address address) {
        OrientVertexType addressClass = graph.getVertexType("Address");
        if (addressClass == null)
            createAddressSchema(graph);

        // create the item in the graph database.
        OrientVertex vertex = graph.addVertex("class:Address");
        //ORecordId recordId = (ORecordId) vertex.getId();

        safeVertexPropertySet(vertex, "streetNumber", address.getStreetNumber());
        safeVertexPropertySet(vertex, "streetName", address.getStreetName());
        safeVertexPropertySet(vertex, "streetType", address.getStreetType().toString());
        safeVertexPropertySet(vertex,"suburb", address.getSuburb());
        safeVertexPropertySet(vertex,"state", address.getState());
        safeVertexPropertySet(vertex,"postcode", address.getPostcode());
        safeVertexPropertySet(vertex,"country", address.getCountry());
        safeVertexPropertySet(vertex, "fromDate", address.getFromDate());
        safeVertexPropertySet(vertex, "toDate", address.getToDate());

        return vertex;
    }

    public static OrientVertex CreateEventVertex(OrientGraphNoTx graph, Event event) {
        OrientVertexType eventClass = graph.getVertexType("Event");
        if (eventClass == null)
            createEventSchema(graph);

        // create the item in the graph database.
        OrientVertex vertex = graph.addVertex("class:Event");
        //ORecordId recordId = (ORecordId) vertex.getId();

        safeVertexPropertySet(vertex, "type", event.getType().toString());
        safeVertexPropertySet(vertex, "title", event.getTitle());
        safeVertexPropertySet(vertex, "notes", event.getNotes());
        safeVertexPropertySet(vertex, "startDatetime", event.getStartDatetime());
        safeVertexPropertySet(vertex, "endDatetime", event.getEndDatetime());

        return vertex;
    }

    public static void delete(OrientGraphNoTx graph, OrientVertex vertex) {
        graph.removeVertex(vertex);
    }

    public static OrientVertex CreateCorrespondenceVertex(OrientGraphNoTx graph, Correspondence correspondence) {
        OrientVertexType vertexType = graph.getVertexType("Correspondence");
        if (vertexType == null)
            createCorrespondenceSchema(graph);

        // create the item in the graph database.
        OrientVertex vertex = graph.addVertex("class:Correspondence");
        //ORecordId recordId = (ORecordId) vertex.getId();

        safeVertexPropertySet(vertex, "type", correspondence.getType().toString());
        safeVertexPropertySet(vertex, "title", correspondence.getTitle());
        safeVertexPropertySet(vertex, "notes", correspondence.getNotes());
        safeVertexPropertySet(vertex, "startDate", correspondence.getStartDate());
        safeVertexPropertySet(vertex, "endDate", correspondence.getEndDate());

        return vertex;
    }

    public static OrientVertex CreateWorkStudyVertex(OrientGraphNoTx graph, WorkStudy workStudy) {
        OrientVertexType vertexType = graph.getVertexType("WorkStudy");
        if (vertexType == null)
            createWorkStudySchema(graph);

        // create the item in the graph database.
        OrientVertex vertex = graph.addVertex("class:WorkStudy");
        //ORecordId recordId = (ORecordId) vertex.getId();

        safeVertexPropertySet(vertex, "type", workStudy.getType().toString());
        safeVertexPropertySet(vertex, "title", workStudy.getTitle());
        safeVertexPropertySet(vertex, "position", workStudy.getPosition());
        safeVertexPropertySet(vertex, "startDate", workStudy.getStartDate());
        safeVertexPropertySet(vertex, "endDate", workStudy.getEndDate());

        return vertex;
    }

    public static void CreateVertexTypeIfNotExists(OrientGraphNoTx graph, String name) {
        if (graph.getVertexType(name) == null) {
            graph.createVertexType(name);
        }
    }

    public static OrientVertex CreateVertexIfNotExists(OrientGraphNoTx graph, String vertexType, String key) {
        OrientVertex result = null;
        // lookup the Component.
        Iterable<Vertex> Components = graph.getVertices(vertexType + ".key", key);
        if (Components != null && Components.iterator().hasNext()) {
            result = (OrientVertex) Components.iterator().next();
        } else {
            result = graph.addVertex("class:" + vertexType);
            result.setProperty("key", key);
        }
        return result;
    }

    public static Iterable<Vertex> findPersonByFirstName(OrientGraphNoTx graph, String firstName) {
        return graph.command(new OCommandSQL("SELECT FROM Person WHERE firstName like '%" + firstName + "%'")).execute();
    }

    public static void executeQuery(OrientGraphNoTx graph, String query) {
        graph.command(new OCommandSQL(query)).execute();
    }

    public static void CreateEdgeIfNotExists(OrientGraphNoTx graph, OrientVertex vSource, OrientVertex vDestination, String edgeLabel) {
        // ensure there is an edge between the ExecutionEnvironment and the property.
        Iterable<com.tinkerpop.blueprints.Edge> edges = vSource.getEdges(vDestination, Direction.BOTH, edgeLabel);
        if (edges == null || !edges.iterator().hasNext()) {
            graph.addEdge(null, vSource, vDestination, edgeLabel);
        }
    }

    public static List<OrientVertex> GetAllVerticesForType(OrientGraphNoTx graph, String type) {
        List<OrientVertex> result = new ArrayList<>();
        // lookup the Component.
        Iterable<Vertex> Components = graph.getVerticesOfClass(type);
        if (Components != null) {
            Iterator<Vertex> iter = Components.iterator();
            while (iter.hasNext()) {
                result.add((OrientVertex) iter.next());
            }
        }
        return result;
    }

    public static OrientVertex GetVertex(OrientGraphNoTx graph, String vertexType, String key) {
        OrientVertex result = null;
        // lookup the Component.
        Iterable<Vertex> Components = graph.getVertices(vertexType + ".key", key);
        if (Components != null && Components.iterator().hasNext()) {
            result = (OrientVertex) Components.iterator().next();
        }
        return result;
    }

    public static OrientVertex GetVertexById(OrientGraphNoTx graph, String id) {
        ORecordId recordId = new ORecordId(id);
        return graph.getVertex(recordId);
    }

    public static void createPersonSchema(OrientGraphNoTx graph) {
        OrientVertexType vertexType = graph.getVertexType("Person");
        if (vertexType == null) {
            vertexType = graph.createVertexType("Person");
            vertexType.createProperty("id", OType.STRING);
            vertexType.createProperty("firstName", OType.STRING);
            vertexType.createProperty("lastName", OType.STRING);
            vertexType.createProperty("dateOfBirth", OType.DATETIME);
            vertexType.createProperty("profileImagePath", OType.STRING);
            vertexType.createProperty("email", OType.STRING);
            vertexType.createProperty("createdAt", OType.DATETIME);
            vertexType.createProperty("modifiedAt", OType.DATETIME);

            //personClass.createIndex("Person.firstName", OClass.INDEX_TYPE.UNIQUE, "firstName");
        }
    }

    public static void createAddressSchema(OrientGraphNoTx graph) {
        OrientVertexType vertexType = graph.getVertexType("Address");
        if (vertexType == null) {
            vertexType = graph.createVertexType("Address");
            //addressClass.setSuperClass(v);
            vertexType.createProperty("streetNumber", OType.STRING);
            vertexType.createProperty("streetName", OType.STRING);
            vertexType.createProperty("streetType", OType.STRING);
            vertexType.createProperty("suburb", OType.STRING);
            vertexType.createProperty("postcode", OType.STRING);
            vertexType.createProperty("state", OType.STRING);
            vertexType.createProperty("country", OType.STRING);
            vertexType.createProperty("fromDate", OType.DATETIME);
            vertexType.createProperty("toDate", OType.DATETIME);
        }
    }

    public static void createEventSchema(OrientGraphNoTx graph) {
        OrientVertexType vertexType = graph.getVertexType("Event");
        if (vertexType == null) {
            vertexType = graph.createVertexType("Event");
            //eventClass.setSuperClass(v);
            vertexType.createProperty("type", OType.STRING);
            vertexType.createProperty("title", OType.STRING);
            vertexType.createProperty("notes", OType.STRING);
            vertexType.createProperty("startDatetime", OType.DATETIME);
            vertexType.createProperty("endDatetime", OType.DATETIME);
        }
    }

    public static void createCorrespondenceSchema(OrientGraphNoTx graph) {
        OrientVertexType vertexType = graph.getVertexType("Correspondence");
        if (vertexType == null) {
            vertexType = graph.createVertexType("Correspondence");
            //eventClass.setSuperClass(v);
            vertexType.createProperty("type", OType.STRING);
            vertexType.createProperty("title", OType.STRING);
            vertexType.createProperty("notes", OType.STRING);
            vertexType.createProperty("startDate", OType.DATETIME);
            vertexType.createProperty("endDate", OType.DATETIME);
        }
    }

    public static void createWorkStudySchema(OrientGraphNoTx graph) {
        OrientVertexType vertexType = graph.getVertexType("WorkStudy");
        if (vertexType == null) {
            vertexType = graph.createVertexType("WorkStudy");
            //eventClass.setSuperClass(v);
            vertexType.createProperty("type", OType.STRING);
            vertexType.createProperty("title", OType.STRING);
            vertexType.createProperty("position", OType.STRING);
            vertexType.createProperty("startDate", OType.DATETIME);
            vertexType.createProperty("endDate", OType.DATETIME);
        }
    }

    /**
     * TODO : There might be a way to read all types from a JSON configuration file.
     *
     * @param graph
     */
    public static void buildSchemas(OrientGraphNoTx graph) {
        JSONUtil util = new JSONUtil();
        List<Entity> entityList = util.getEntityList();
        for (Entity entity : entityList) {
            if ("Vertex".equals(entity.getCategory())) {
                OrientVertexType vertexType = graph.getVertexType(entity.getName());
                if (vertexType == null) {
                    vertexType = graph.createVertexType(entity.getName());
                    for (SubEntity subEntity : entity.getAttributes()) {
                        String subEntityType = subEntity.getType();
                        if (subEntityType.equals("string")) {
                            vertexType.createProperty(subEntity.getName(), OType.STRING);
                        } else if (subEntityType.equals("date")) {
                            vertexType.createProperty(subEntity.getName(), OType.DATETIME);
                        }
                    }
                }
            }
            else if ("Edge".equals(entity.getCategory())) {
                // TODO : Do we want to read types of Edges from configuration? Or are there just Edges, no types?
            }
        }
    }

    public static Edge findEdge(final Vertex outVertex, final Vertex inVertex) {
        for (final Edge edge : outVertex.getEdges(Direction.OUT)) {
            if (edge.getVertex(Direction.IN).equals(inVertex)) {
                return edge;
            }
        }
        return null;
    }

}
