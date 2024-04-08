package alantheknight.lab6.common.managers;


import alantheknight.lab6.common.models.Worker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The CollectionManager class is responsible for managing the collection of
 * workers.
 *
 * @author AlanTheKnight
 */
public class CollectionManager {
    /**
     * The DumpManager used for reading and writing the collection.
     */
    private final DumpManager dumpManager;

    /**
     * The collection of workers.
     */
    private Map<Integer, Worker> workers = new TreeMap<>();
    /**
     * The next free id.
     */
    private Integer freeId = 0;
    /**
     * The last initialization time.
     */
    private LocalDateTime lastInitTime = null;

    /**
     * Creates a new CollectionManager.
     *
     * @param dumpManager the DumpManager used for reading and writing the collection
     */
    public CollectionManager(DumpManager dumpManager) {
        this.dumpManager = dumpManager;
    }

    /**
     * Returns the last initialization time.
     *
     * @return the last initialization time
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * Loads the collection from the file.
     *
     * @throws IllegalArgumentException          if the path to the file is not specified
     * @throws DumpManager.DocumentReadException if the collection could not be read
     */
    public void loadCollection() throws IllegalArgumentException, DumpManager.DocumentReadException {
        workers = dumpManager.readCollection();
        lastInitTime = LocalDateTime.now();
    }

    /**
     * Returns the collection of workers.
     *
     * @return the collection of workers
     */
    public Map<Integer, Worker> getWorkers() {
        return workers;
    }

    /**
     * Adds a worker to the collection with the specified id.
     *
     * @param id     Worker id.
     * @param worker Worker to add.
     */
    public void insertWorker(Worker worker) {
        workers.put(worker.getId(), worker);
        freeId = Math.max(freeId, worker.getId() + 1);
    }

    /**
     * Saves the collection to the file.
     *
     * @throws DumpManager.DocumentWriteException if the collection could not be saved
     */
    public void saveCollection() throws DumpManager.DocumentWriteException {
        dumpManager.writeDocument(workers.values());
    }

    /**
     * Removes the worker with the specified id from the collection.
     *
     * @param id Worker id.
     * @return Removed worker.
     */
    public Worker removeWorker(int id) {
        return workers.remove(id);
    }

    /**
     * Clears the collection.
     */
    public void clearCollection() {
        workers.clear();
    }

    /**
     * Returns the collection as a collection of workers.
     *
     * @return the collection of workers
     */
    public Collection<Worker> getCollection() {
        return workers.values();
    }

    /**
     * Remove all workers with the key greater than the specified key.
     *
     * @param key Key to compare with.
     * @return Number of removed workers.
     */
    public int removeGreaterKey(int key) {
        AtomicInteger removed = new AtomicInteger(0);
        workers.entrySet().stream()
                .filter(worker -> worker.getKey() > key)
                .forEach(worker -> {
                    workers.remove(worker.getKey());
                    removed.getAndIncrement();
                });
        return removed.get();
    }

    /**
     * Remove all workers with the end date equal to the specified end date.
     *
     * @param endDate End date to compare with.
     * @return Removed worker, or null if no worker was removed.
     */
    public Worker removeWorkerByEndDate(LocalDate endDate) {
        var w = workers.entrySet().stream()
                .filter(worker -> worker.getValue().getEndDate().equals(endDate))
                .findFirst();
        if (w.isEmpty()) return null;
        Worker worker = w.get().getValue();
        workers.remove(w.get().getKey());
        return worker;
    }

    /**
     * Remove all workers with the end date greater than the specified end date.
     *
     * @param endDate End date to compare with.
     * @return Number of removed workers.
     */
    public int removeGreaterEndDate(LocalDate endDate) {
        if (endDate == null) {
            return 0;
        }
        int removed = 0;
        for (var worker : workers.entrySet()) {
            if (worker.getValue().getEndDate().isAfter(endDate)) {
                workers.remove(worker.getKey());
                removed++;
            }
        }
        return removed;
    }
}
