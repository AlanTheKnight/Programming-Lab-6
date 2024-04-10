package alantheknight.lab6.server.managers;

import alantheknight.lab6.common.models.Worker;
import alantheknight.lab6.common.utils.ElementConversionException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.TreeMap;

/**
 * The DumpManager class is responsible for reading and writing the collection
 * from/to the file.
 */
public class DumpManager {
    /**
     * The path to the file used for reading and writing the collection.
     */
    private final String filename;

    /**
     * Creates a new DumpManager.
     *
     * @param filename the path to the file used for reading and writing the collection
     */
    public DumpManager(String filename) {
        this.filename = filename;
    }

    /**
     * Reads the collection from the file.
     *
     * @return the collection of workers
     * @throws IllegalArgumentException if the path to the file is not specified
     * @throws DocumentReadException    if the collection could not be read
     */
    public TreeMap<Integer, Worker> readCollection() throws IllegalArgumentException, DocumentReadException {
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("Не указан путь к файлу данных.");
        }

        TreeMap<Integer, Worker> workers = new TreeMap<>();

        try {
            Document document = parseDocument(filename);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getChildNodes().item(0).getChildNodes();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    Worker worker = createWorkerFromElement(element);
                    validateWorker(worker);
                    workers.put(worker.getId(), worker);
                }
            }

        } catch (DocumentReadException e) {
            throw e;
        } catch (Exception e) {
            throw new DocumentReadException("Ошибка при чтении XML: " + e.getMessage());
        }

        return workers;
    }

    /**
     * Parses the document from the file.
     *
     * @param filename filename
     * @return the document
     * @throws DocumentReadException if the document could not be parsed
     */
    private Document parseDocument(String filename) throws DocumentReadException {
        try (FileInputStream fileInputStream = new FileInputStream(filename);
             InputStreamReader reader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8)) {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            return documentBuilder.parse(new InputSource(reader));
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new DocumentReadException("Ошибка при чтении XML: " + e.getMessage());
        }
    }

    /**
     * Creates a worker from XML element.
     *
     * @param element XML element
     * @return the worker
     * @throws DocumentReadException if the worker could not be created
     */
    private Worker createWorkerFromElement(Element element) throws DocumentReadException {
        try {
            return Worker.fromElement(element);
        } catch (ElementConversionException e) {
            throw new DocumentReadException(
                    "Ошибка чтения из файла (элемент " + element.getTagName() + "): " + e.getMessage());
        }
    }

    /**
     * Validates the worker.
     *
     * @param worker the worker
     * @throws DocumentReadException if the worker is not valid
     */
    private void validateWorker(Worker worker) throws DocumentReadException {
        if (!worker.validate()) {
            throw new DocumentReadException("Ошибка чтения из файла: Объект с id " + worker.getId() + " не прошел валидацию");
        }
    }

    /**
     * Generates a document from the collection.
     *
     * @param workers the collection of workers
     * @return the document
     * @throws ParserConfigurationException if the document builder could not be created
     */
    public Document generateDocument(Collection<Worker> workers) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.newDocument();
        Element root = document.createElement("collection");

        for (Worker worker : workers) {
            root.appendChild(worker.getElement(document));
        }

        document.appendChild(root);
        return document;
    }

    /**
     * Writes the collection to the file.
     *
     * @param workers the collection of workers
     * @throws DocumentWriteException   if the collection could not be written
     * @throws IllegalArgumentException if the path to the file is not specified
     */
    public void writeDocument(Collection<Worker> workers) throws DocumentWriteException, IllegalArgumentException {
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("Не указан путь к файлу данных.");
        }

        try (
                FileOutputStream fileOutputStream = new FileOutputStream(filename);
                Writer writer = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8)
        ) {

            Document document = generateDocument(workers);
            document.getDocumentElement().normalize();

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(writer);

            transformer.transform(source, result);

        } catch (ParserConfigurationException | TransformerException | IOException e) {
            throw new DocumentWriteException("Ошибка при записи XML: " + e.getMessage());
        }
    }

    /**
     * The exception thrown when an error occurs while reading the collection from the file.
     */
    public static class DocumentReadException extends Exception {
        /**
         * Creates a new DocumentReadException.
         *
         * @param message message
         */
        public DocumentReadException(String message) {
            super(message);
        }
    }

    /**
     * The exception thrown when an error occurs while writing the collection to the file.
     */
    public static class DocumentWriteException extends Exception {
        /**
         * Creates a new DocumentWriteException.
         *
         * @param message message
         */
        public DocumentWriteException(String message) {
            super(message);
        }
    }
}
