package com.razor.photo.organizer.console;

import org.junit.Test;

import java.io.File;
import static org.mockito.Mockito.mock;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: phemmings
 * Date: 7/16/13
 * Time: 10:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProducerTest {

    private ProductionLine productionLine = new ProductionLine();

    @Test
    public void setConsumerFlagTrueSuccessfully() {
        Producer producer = new Producer(this.productionLine);
        producer.startProduction();
        assertFalse(producer.productionStopped());
    }

    @Test
    public void setConsumerFlagFalseSuccessfully() {
        Producer producer = new Producer(this.productionLine);
        producer.stopProduction();
        assertTrue(producer.productionStopped());
    }

    @Test
    public void addProductToLineSuccessfully() {

        this.productionLine.getWorkItems().clear();
        File imageFile = mock(File.class);

        Producer producer = new Producer(this.productionLine);
        producer.startProduction();

        try {
            producer.processFile(imageFile);
        } catch (InterruptedException ie) {

        }

        assertTrue(this.productionLine.hasWorkItems());
    }

    @Test
    public void doNotAddProductIfProductionComplete() {

        this.productionLine.getWorkItems().clear();
        File imageFile = mock(File.class);

        Producer producer = new Producer(this.productionLine);
        producer.stopProduction();

        try {
            producer.processFile(imageFile);
        } catch (InterruptedException ie) {

        }

        assertFalse(this.productionLine.hasWorkItems());
    }
}
