package socialnetwork.view.model;

public abstract class DomainObject {
    private String id;
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public abstract boolean validForSaving();
}
