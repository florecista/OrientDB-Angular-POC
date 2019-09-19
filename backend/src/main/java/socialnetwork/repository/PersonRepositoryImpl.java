package socialnetwork.repository;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import socialnetwork.utils.Util;
import socialnetwork.view.model.*;
import socialnetwork.OrientDBTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static socialnetwork.utils.Util.convertOrientVertexPersonToModelPerson;
import static socialnetwork.utils.Util.personFromVertex;

@Component
public class PersonRepositoryImpl implements PersonRepository {

    @Autowired
    private OrientGraphFactory factory;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CorrespondenceRepository correspondenceRepository;

    @Autowired
    private WorkStudyRepository workStudyRepository;

    // TODO : Is this the best way to do this??? Maybe not.
    @Override
    public void create(Person person) {
        OrientGraphNoTx graph =  factory.getNoTx();

        OrientVertex personVertex = OrientDBTools.CreatePersonVertex(graph, person);
        if(person.getAddress() != null) {
            Address savedAddress = addressRepository.save(person.getAddress());
            String savedAddressId = savedAddress.getId();
            OrientVertex savedAddressVertex = OrientDBTools.GetVertexById(graph, "#" + savedAddressId);
            OrientDBTools.CreateEdgeIfNotExists(graph, personVertex, savedAddressVertex, GraphConstants.LIVES_AT);
        }
        if(person.getEvents().size() > 0) {
            for(Event event : person.getEvents()) {
                Event savedEvent = eventRepository.save(event);
                String saveEventId = savedEvent.getId();
                OrientVertex savedEventVertex = OrientDBTools.GetVertexById(graph, "#" + saveEventId);
                OrientDBTools.CreateEdgeIfNotExists(graph, personVertex, savedEventVertex, GraphConstants.CONNECTED_TO);
            }
        }
        if(person.getCorrespondences().size() > 0) {
            for(Correspondence correspondence : person.getCorrespondences()) {
                Correspondence savedCorrespondence = correspondenceRepository.save(correspondence);
                String savedCorrespondenceId = savedCorrespondence.getId();
                OrientVertex SavedCorrespondenceVertex = OrientDBTools.GetVertexById(graph, "#" + savedCorrespondenceId);
                OrientDBTools.CreateEdgeIfNotExists(graph, personVertex, SavedCorrespondenceVertex, GraphConstants.COMMUNICATION);
            }
        }
        if(person.getWorkStudies().size() > 0) {
            for(WorkStudy workStudy : person.getWorkStudies()) {
                OrientVertex workStudyVertex = OrientDBTools.CreateWorkStudyVertex(graph, workStudy);
                OrientDBTools.CreateEdgeIfNotExists(graph, personVertex, workStudyVertex, GraphConstants.WORKED_OR_STUDIED);
                if(workStudy.getAddress() != null) {
                    OrientVertex addressVertex = OrientDBTools.CreateAddressVertex(graph, workStudy.getAddress());
                    OrientDBTools.CreateEdgeIfNotExists(graph, personVertex, addressVertex, GraphConstants.LOCATED_AT);
                }
            }
        }

        graph.shutdown();
    }

    @Override
    public Optional<Person> readById(String id) {
        OrientGraphNoTx graph =  factory.getNoTx();

        OrientVertex vertex = OrientDBTools.GetVertexById(graph, "#" + id);
        Person model = convertOrientVertexPersonToModelPerson(vertex);

        graph.shutdown();
        return Optional.of(model);
    }

    @Override
    public Optional<Person> readByName(String firstNameParam) {
        List<Person> result = new ArrayList<>();
        Iterable<Vertex> persons = OrientDBTools.findPersonByFirstName(factory.getNoTx(), firstNameParam);
        for(Vertex vertex : persons) {
            Person person = new Person();
            personFromVertex(person, (OrientVertex)vertex, vertex.getId().toString());

            result.add(person);
        }
        return Optional.of(result.get(0));
    }

    @Override
    public List<Person> readAll() {
        OrientGraphNoTx graph =  factory.getNoTx();

        List<Person> result = new ArrayList<>();
        Iterable<OrientVertex> persons = OrientDBTools.GetAllVerticesForType(graph, "Person");
        for(OrientVertex vertex : persons) {
            result.add(convertOrientVertexPersonToModelPerson(vertex));
        }

        graph.shutdown();
        return result;
    }

    // TODO : Got to do this
    @Override
    public void update(Person person) {

    }

    @Override
    public void saveNewPersonEvent(Event event, String personId) {
        OrientGraphNoTx graph =  factory.getNoTx();
        OrientVertex personVertex = OrientDBTools.GetVertexById(graph, "#" + personId);

        Event savedEvent = eventRepository.save(event);
        String saveEventId = savedEvent.getId();
        OrientVertex savedEventVertex = OrientDBTools.GetVertexById(graph, "#" + saveEventId);
        OrientDBTools.CreateEdgeIfNotExists(graph, personVertex, savedEventVertex, GraphConstants.CONNECTED_TO);
        graph.shutdown();
    }

    @Override
    public void updatePersonEvent(Event event) {
        OrientGraphNoTx graph =  factory.getNoTx();
        if(event.getId() != null && !event.getId().isEmpty()) {
            String vId = "#" + event.getId();
            String startDate = Util.getStringForDate(event.getStartDatetime(), "yyyy-MM-dd HH:mm:ss");
            String endDate = Util.getStringForDate(event.getEndDatetime(), "yyyy-MM-dd HH:mm:ss");
            String query = "UPDATE Event SET type = '" + event.getType().toString() + "',"
                    + " title = '" + event.getTitle() + "',"
                    + " notes = '" + event.getNotes() + "',"
                    + " startDatetime = '" + startDate + "',"
                    + " endDatetime = '" + endDate + "'"
                    + " WHERE @rid ='" + vId + "'";
            OrientDBTools.executeQuery(graph, query);
            graph.shutdown();
        }
    }

    @Override
    public void saveNewPersonCorrespondence(Correspondence correspondence, String personId) {
        OrientGraphNoTx graph =  factory.getNoTx();
        OrientVertex personVertex = OrientDBTools.GetVertexById(graph, "#" + personId);

        Correspondence savedCorrespondence = correspondenceRepository.save(correspondence);
        String saveCorrespondenceId = savedCorrespondence.getId();
        OrientVertex savedCorrespondenceVertex = OrientDBTools.GetVertexById(graph, "#" + saveCorrespondenceId);
        OrientDBTools.CreateEdgeIfNotExists(graph, personVertex, savedCorrespondenceVertex, GraphConstants.COMMUNICATION);
        graph.shutdown();
    }

    @Override
    public void updatePersonCorrespondence(Correspondence correspondence) {
        if(correspondence.getId() != null && !correspondence.getId().isEmpty()) {
            OrientGraphNoTx graph =  factory.getNoTx();
            String vId = "#" + correspondence.getId();
            String query = "UPDATE Correspondence SET type = '" + correspondence.getType().toString() + "',"
                    + " title = '" + correspondence.getTitle() + "',"
                    + " notes = '" + correspondence.getNotes() + "'"
                    + " WHERE @rid ='" + vId + "'";
            OrientDBTools.executeQuery(graph, query);
            graph.shutdown();
        }
    }

    @Override
    public void saveNewPersonWorkStudy(WorkStudy workStudy, String personId) {
        OrientGraphNoTx graph =  factory.getNoTx();
        OrientVertex personVertex = OrientDBTools.GetVertexById(graph, "#" + personId);
        WorkStudy savedWorkStudy = workStudyRepository.save(workStudy);
        String saveWorkStudyId = savedWorkStudy.getId();
        OrientVertex savedWorkStudyVertex = OrientDBTools.GetVertexById(graph, "#" + saveWorkStudyId);
        OrientDBTools.CreateEdgeIfNotExists(graph, personVertex, savedWorkStudyVertex, GraphConstants.WORKED_OR_STUDIED);
        if(workStudy.getAddress() != null && workStudy.getAddress().validForSaving()) {
            OrientVertex addressVertex = OrientDBTools.CreateAddressVertex(graph, workStudy.getAddress());
            OrientDBTools.CreateEdgeIfNotExists(graph, personVertex, addressVertex, GraphConstants.LOCATED_AT);
        }
        graph.shutdown();
    }

    @Override
    public void updatePersonWorkStudy(WorkStudy workStudy) {
        if(workStudy.getId() != null && !workStudy.getId().isEmpty()) {
            OrientGraphNoTx graph =  factory.getNoTx();
            String vId = "#" + workStudy.getId();
            String startDate = Util.getStringForDate(workStudy.getStartDate(), "yyyy-MM-dd");
            String endDate = Util.getStringForDate(workStudy.getEndDate(), "yyyy-MM-dd");
            String query = "UPDATE WorkStudy SET type = '" + workStudy.getType().toString() + "',"
                    + " title = '" + workStudy.getTitle() + "',"
                    + " position = '" + workStudy.getPosition() + "',"
                    + " startDate = '" + startDate + "',"
                    + " endDate = '" + endDate + "'"
                    + " WHERE @rid ='" + vId + "'";
            OrientDBTools.executeQuery(graph, query);
            graph.shutdown();
        }
    }

    @Override
    public void saveNewPersonAddress(Address address, String personId) {
        OrientGraphNoTx graph =  factory.getNoTx();
        OrientVertex personVertex = OrientDBTools.GetVertexById(graph, "#" + personId);

        Address savedAddress = addressRepository.save(address);
        String saveAddressId = savedAddress.getId();
        OrientVertex savedAddressVertex = OrientDBTools.GetVertexById(graph, "#" + saveAddressId);
        OrientDBTools.CreateEdgeIfNotExists(graph, personVertex, savedAddressVertex, GraphConstants.LIVES_AT);
        graph.shutdown();
    }

    @Override
    public void updatePersonAddress(Address address) {
        if(address.getId() != null && !address.getId().isEmpty()) {
            OrientGraphNoTx graph =  factory.getNoTx();
            String vId = "#" + address.getId();
            String fromDate = Util.getStringForDate(address.getFromDate(), "yyyy-MM-dd HH:mm:ss");
            String toDate = Util.getStringForDate(address.getToDate(), "yyyy-MM-dd HH:mm:ss");
            String query = "UPDATE Address SET streetNumber = '" + address.getStreetNumber() + "',"
                    + " streetName = '" + address.getStreetName() + "',"
                    + " streetType = '" + address.getStreetType().toString() + "',"
                    + " suburb = '" + address.getSuburb() + "',"
                    + " postcode = '" + address.getPostcode() + "',"
                    + " state = '" + address.getState() + "',"
                    + " country = '" + address.getCountry() + "',"
                    + " fromDate = '" + fromDate + "',"
                    + " toDate = '" + toDate + "'"
                    + " WHERE @rid = '" + vId + "'";
            OrientDBTools.executeQuery(graph, query);
            graph.shutdown();
        }
    }

    // TODO : Need a way to delete Vertices
    @Override
    public void delete(String id) {
        OrientGraphNoTx graph =  factory.getNoTx();
        OrientVertex vertex = OrientDBTools.GetVertexById(graph, "#" + id);
        if(vertex != null) {
            OrientDBTools.delete(graph, vertex);
        }
        graph.shutdown();
    }

}
