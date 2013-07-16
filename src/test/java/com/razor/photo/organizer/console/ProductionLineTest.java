package com.razor.photo.organizer.console;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: phemmings
 * Date: 7/16/13
 * Time: 9:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProductionLineTest {

    private ProductionLine productionLine = new ProductionLine();

    @Test
    public void addConsumerSuccessfully() {
        productionLine.getConsumers().clear();
        productionLine.addConsumer();
        assertTrue (productionLine.getConsumerCount() == 1);
    }

    @Test
    public void removeConsumerSuccessfully() {
        productionLine.getConsumers().clear();
        Consumer consumer = productionLine.addConsumer();
        productionLine.removeConsumer(consumer);
        assertTrue (productionLine.getConsumerCount() == 0);
    }

    @Test
    public void stoppingConsumptionRemovesConsumerFromLineSuccessfully() {
        productionLine.getConsumers().clear();
        Consumer consumer = productionLine.addConsumer();
        consumer.stopConsuming();
        assertTrue (productionLine.getConsumerCount() == 0);
    }

    @Test
    public void settingMaximumConsumersSuccessfully() {
        productionLine.setMaxConsumers(100);
        assertTrue (productionLine.getMaxConsumers() == 100);
    }


}
