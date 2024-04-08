package alantheknight.lab6.common.fields;

/**
 * CollectionElement is a subclass of FieldsElement with an ID field.
 */
public abstract class CollectionElement extends Model implements Comparable<CollectionElement> {
    public final Field<Integer> id = new Field<>("id", "ID", null, true);

    protected CollectionElement() {
        super();
        id.setAskForValue(false); // Automatically generated value
    }

    protected CollectionElement(int id) {
        this();
        this.id.setValue(id);
    }

    public int getId() {
        return id.getValue();
    }

    @Override
    public int compareTo(CollectionElement arg0) {
        return id.getValue() - arg0.getId();
    }
}
