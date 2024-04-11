package alantheknight.lab6.common.models;

import alantheknight.lab6.common.fields.CollectionElement;
import alantheknight.lab6.common.fields.ElementConvertor;
import alantheknight.lab6.common.fields.Field;
import alantheknight.lab6.common.utils.ElementConversionException;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.w3c.dom.Element;

import java.time.LocalDate;

/**
 * Worker element of the collection.
 */
public class Worker extends CollectionElement {
    /**
     * Name of the worker (not null).
     */
    public final Field<String> name = new Field<>("name", "Имя", "", true);
    /**
     * Coordinates of the worker (not null).
     */
    public final Field<Coordinates> coordinates = new Field<>("coordinates", "Координаты", new Coordinates(0, 0.0F), true);
    /**
     * Salary of the worker (not null, greater than 0).
     */
    public final Field<Long> salary = new Field<>("salary", "Зарплата", 0L, true, value -> {
        if (value <= 0) {
            return new ImmutablePair<>(false, "Поле worker.salary должно быть больше 0");
        }
        return new ImmutablePair<>(true, null);
    });
    /**
     * End date of the worker's contract (not null).
     */
    public final Field<LocalDate> endDate = new Field<>("endDate", "Дата окончания контракта", LocalDate.now(), true);
    /**
     * Position of the worker (can be null).
     */
    public final Field<Position> position = new Field<>("position", "Должность", Position.DEVELOPER, false);
    /**
     * Status of the worker (not null).
     */
    public final Field<Status> status = new Field<>("status", "Статус", Status.RECOMMENDED_FOR_PROMOTION, true);
    /**
     * Personal data of the worker (not null).
     */
    public final Field<Person> person = new Field<>("person", "Персональные данные", new Person(0.0, 0, Color.RED, Country.NORTH_KOREA), true);

    /**
     * Creation date of the worker record (generated automatically).
     */
    public final Field<LocalDate> creationDate = new Field<>("creationDate", "Дата создания", LocalDate.now(), true, false);

    /**
     * Creates a new worker.
     *
     * @param id          id
     * @param name        name
     * @param coordinates coordinates
     * @param salary      salary
     * @param endDate     end date
     * @param position    position
     * @param status      status
     * @param person      person
     */
    public Worker(Integer id, String name, Coordinates coordinates, Long salary,
                  LocalDate endDate, Position position, Status status, Person person) {
        super(id);
        this.name.setValue(name);
        this.coordinates.setValue(coordinates);
        this.salary.setValue(salary);
        this.endDate.setValue(endDate);
        this.position.setValue(position);
        this.status.setValue(status);
        this.person.setValue(person);
        this.creationDate.setAskForValue(false); // This field is generated automatically
    }

    public Worker() {
        super();
        this.creationDate.setAskForValue(false); // This field is generated automatically
    }

    /**
     * Creates a new worker.
     *
     * @param id           id
     * @param name         name
     * @param coordinates  coordinates
     * @param salary       salary
     * @param creationDate creation date
     * @param endDate      end date
     * @param position     position
     * @param status       status
     * @param person       person
     */
    public Worker(int id, String name, Coordinates coordinates, Long salary, LocalDate creationDate,
                  LocalDate endDate, Position position, Status status, Person person) {
        this(id, name, coordinates, salary, endDate, position, status, person);
        this.creationDate.setValue(creationDate);
    }

    /**
     * Creates a new Worker object from the given XML element.
     *
     * @param element XML element
     * @return new Worker object
     * @throws ElementConversionException if the element is invalid
     */
    public static Worker fromElement(Element element) throws ElementConversionException {
        try {
            return new Worker(
                    ElementConvertor.numberConvertor(Integer.class).fromElement(element.getElementsByTagName("id").item(0)),
                    ElementConvertor.stringConvertor.fromElement(element.getElementsByTagName("name").item(0)),
                    ElementConvertor.coordinatesConvertor.fromElement(element.getElementsByTagName("coordinates").item(0)),
                    ElementConvertor.numberConvertor(Long.class).fromElement(element.getElementsByTagName("salary").item(0)),
                    ElementConvertor.dateConvertor.fromElement(element.getElementsByTagName("creationDate").item(0)),
                    ElementConvertor.dateConvertor.fromElement(element.getElementsByTagName("endDate").item(0)),
                    ElementConvertor.enumConvertor(Position.class).fromElement(element.getElementsByTagName("position").item(0)),
                    ElementConvertor.enumConvertor(Status.class).fromElement(element.getElementsByTagName("status").item(0)),
                    ElementConvertor.personConvertor.fromElement(element.getElementsByTagName("person").item(0))
            );
        } catch (ElementConversionException | NullPointerException e) {
            throw new ElementConversionException(e.getMessage());
        }
    }
}
