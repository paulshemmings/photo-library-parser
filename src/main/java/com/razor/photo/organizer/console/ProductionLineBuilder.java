package com.razor.photo.organizer.console;

/**
 * Created with IntelliJ IDEA.
 * User: phemmings
 * Date: 7/16/13
 * Time: 10:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProductionLineBuilder {

    private ProductionLine productionLine = null;

    /*
     * Constructor
     * Sets the default values that can be changed via the builder
     * These default values should come from an application.properties file of some sort.
     */

    public  ProductionLineBuilder() {

        int maxConsumers = 10;
        int maxLineSize = 100;

        String sourceImageFolder = "/Users/phemmings/Pictures/LG";
        String destinationImageFolder = "/Users/phemmings/Pictures/Sorted";
        String unsortedFolder = "/Users/phemmings/Pictures/Unsorted";

        // create the production line

        this.productionLine = new ProductionLine();
        this.productionLine.setMaxLineSize(maxLineSize);
        this.productionLine.setMaxConsumers(maxConsumers);
        this.productionLine.setPhotoPathFolder(sourceImageFolder);
        this.productionLine.setRootDestinationFolder(destinationImageFolder);
        this.productionLine.setUnsortedDestinationFolder(unsortedFolder);
    }

    public ProductionLineBuilder withMaximumConsumers (int maximumConsumers) {
        this.productionLine.setMaxConsumers(maximumConsumers);
        return this;
    }

    public ProductionLineBuilder withMaximumLineSize (int maximumLineSize) {
        this.productionLine.setMaxLineSize(maximumLineSize);
        return this;
    }

    public ProductionLineBuilder withSourceFolder (String sourceFolder) {
        this.productionLine.setPhotoPathFolder(sourceFolder);
        return this;
    }

    public ProductionLineBuilder withDestinationFolder (String destinationFolder) {
        this.productionLine.setRootDestinationFolder(destinationFolder);
        return this;
    }

    public ProductionLineBuilder withUnsortedFolder (String unsortedFolder) {
        this.productionLine.setUnsortedDestinationFolder(unsortedFolder);
        return this;
    }

    public ProductionLine buildProductionLine() {
        return this.productionLine;
    }
}
