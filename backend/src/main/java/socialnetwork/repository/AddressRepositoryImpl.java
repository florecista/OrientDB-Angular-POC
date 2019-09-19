package socialnetwork.repository;

import com.orientechnologies.orient.core.id.ORecordId;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import socialnetwork.OrientDBTools;
import socialnetwork.view.model.Address;

import java.util.Date;
import java.util.Optional;

@Component
public class AddressRepositoryImpl implements AddressRepository {

    @Autowired
    private OrientGraphFactory factory;

    @Override
    public <S extends Address> S save(S entity) {
        if(entity.getId() != null && !entity.getId().isEmpty()) {
            OrientVertex vertex = OrientDBTools.GetVertexById(factory.getNoTx(), "#" + entity.getId());
            if(vertex != null) {
                String query = "UPDATE Address SET streetNumber = '" + entity.getStreetNumber() + "',"
                        + " streetName = '" + entity.getStreetName() + "',"
                        + " streetType = '" + entity.getStreetType().toString() + "',"
                        + " suburb = '" + entity.getSuburb() + "',"
                        + " postcode = '" + entity.getPostcode() + "',"
                        + " state = '" + entity.getState() + "',"
                        + " country = '" + entity.getCountry() + "'"
                        + " WHERE @rid = '#" + entity.getId() + "'";
                OrientDBTools.executeQuery(factory.getNoTx(), query);
            }
        }
        else {
            OrientVertex vertex = OrientDBTools.CreateAddressVertex(factory.getNoTx(), entity);
            int id = ((ORecordId)vertex.getId()).getClusterId();
            long pos = ((ORecordId)vertex.getId()).getClusterPosition();
            String modelId = Integer.toString(id) + ":" + Long.toString(pos);
            entity.setId(modelId);
        }
        return entity;
    }

    @Override
    public <S extends Address> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Address> findById(String id) {
        OrientVertex savedAddressVertex = OrientDBTools.GetVertexById(factory.getNoTx(), "#" + id);
        Address address = domainObjectFromVertex(savedAddressVertex);
        return Optional.of(address);
    }

    @Override
    public boolean existsById(String id) {
        return false;
    }

    @Override
    public Iterable<Address> findAll() {
        return null;
    }

    @Override
    public Iterable<Address> findAllById(Iterable<String> ids) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public void delete(Address entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends Address> entities) {

    }

    @Override
    public void deleteAll() {

    }

    private Address domainObjectFromVertex(OrientVertex vertex) {
        int id = ((ORecordId)vertex.getId()).getClusterId();
        long pos = ((ORecordId)vertex.getId()).getClusterPosition();
        String vId = Integer.toString(id) + ":" + Long.toString(pos);

        Address address = new Address();
        address.setId(vId);

        String streetNumber = vertex.getProperty("streetNumber");
        String streetName = vertex.getProperty("streetName");
        String streetTypeString = vertex.getProperty("streetType");
        Address.StreetType streetType = Address.StreetType.getByName(streetTypeString);
        String suburb = vertex.getProperty("suburb");
        String postcode = vertex.getProperty("postcode");
        String state = vertex.getProperty("state");
        String country = vertex.getProperty("country");
        Date fromDate = vertex.getProperty("fromDate");
        Date toDate = vertex.getProperty("toDate");

        address.setStreetNumber(streetNumber);
        address.setStreetName(streetName);
        address.setStreetType(streetType);
        address.setSuburb(suburb);
        address.setPostcode(postcode);
        address.setState(state);
        address.setCountry(country);
        address.setFromDate(fromDate);
        address.setToDate(toDate);

        return address;
    }
}
