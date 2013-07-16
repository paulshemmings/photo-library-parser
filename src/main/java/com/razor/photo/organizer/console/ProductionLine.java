package com.razor.photo.organizer.console;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ProductionLine {

	private String photoPathFolder = null;
	private String rootDestinationFolder = null;
	private String unsortedDestinationFolder = null;
    private int maxLineSize = 0;
    private int maxConsumers = 0;

	private List<Product> workItems = new LinkedList<Product>();
    private List<Consumer> consumers = new ArrayList<Consumer>();
    private Producer producer = null;

    /*
     * Constructor
     */

    public ProductionLine(){
    }

    /*
     * Consumers
     */

    public int getMaxConsumers() {
        return maxConsumers;
    }

    public void setMaxConsumers(int maxConsumers) {
        this.maxConsumers = maxConsumers;
    }

    public Consumer addConsumer(){
        Consumer newConsumer = new Consumer(this);
        this.consumers.add(newConsumer);
        return newConsumer;
    }

    public void removeConsumer(Consumer consumer) {
        this.consumers.remove(consumer);
    }

    public int getConsumerCount() {
        return this.consumers.size();
    }

    public List<Consumer> getConsumers() {
        return this.consumers;
    }

    /*
     * Producers
     */

    public Producer getProducer() {
        if (this.producer == null) {
            this.producer = new Producer(this);
        }
        return this.producer;
    }

    /*
     * Work Items
     */

    public List<Product> getWorkItems() {
        return this.workItems;
    }

	public void addWorkItem(Product product){
		this.workItems.add(product);
	}

	public Product removeWorkItem(){
		Product removedProduct = null;
		if(workItems.size()>0){
			removedProduct = workItems.get(0);
			workItems.remove(0);
		}
		return removedProduct;
	}

	public boolean hasWorkItems(){
		return this.workItems.size()>0;
	}

	public int workItemsOnLine(){
		return this.workItems.size();
	}

    /*
     * Configuration Properties
     */

	public void setMaxLineSize(int maxLineSize) {
		this.maxLineSize = maxLineSize;
	}

	public int getMaxLineSize() {
		return maxLineSize;
	}

	public String getPhotoPathFolder() {
		return photoPathFolder;
	}

	public void setPhotoPathFolder(String photoPathFolder) {
		this.photoPathFolder = photoPathFolder;
	}

	public String getRootDestinationFolder() {
		return rootDestinationFolder;
	}

	public void setRootDestinationFolder(String rootDestinationFolder) {
		this.rootDestinationFolder = rootDestinationFolder;
	}

	public String getUnsortedDestinationFolder() {
		return unsortedDestinationFolder;
	}

	public void setUnsortedDestinationFolder(String unsortedDestinationFolder) {
		this.unsortedDestinationFolder = unsortedDestinationFolder;
	}
}
