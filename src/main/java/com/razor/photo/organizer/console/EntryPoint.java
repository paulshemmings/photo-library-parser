package com.razor.photo.organizer.console;


public class EntryPoint {

    /*
     * Build the production line
     */

    private static ProductionLine buildLineFromArgs(String[] args) {
        ProductionLineBuilder productionLineBuilder = new ProductionLineBuilder();

        if (args == null || args.length == 0) {
            System.out.println("[source folder], [destination folder], [unsorted folder], [max consumers], [max line size]");
            // return null;
        }

        if (args.length > 0) {
            productionLineBuilder.withSourceFolder(args[0]);
        }
        if (args.length > 1) {
            productionLineBuilder.withDestinationFolder(args[1]);
        }
        if (args.length > 2) {
            productionLineBuilder.withUnsortedFolder(args[2]);
        }
        if (args.length > 3) {
            productionLineBuilder.withMaximumConsumers(Integer.parseInt(args[3]));
        }
        if (args.length > 4) {
            productionLineBuilder.withMaximumLineSize(Integer.parseInt(args[4]));
        }

        return productionLineBuilder.buildProductionLine();
    }

    /*
     * Main entry point
     */
	
	public static void main(String[] args){

		try {
			// set the logging verbosity

			Log.setLoggingVerbosity(false,false,true);

			// create the production line

			ProductionLine productionLine = buildLineFromArgs(args);

            // start the producer

            Thread producerThread = new Thread(productionLine.getProducer());
            producerThread.start();

            // ensure we always have consumers while the producer is working

            while (!productionLine.getProducer().productionStopped()){
                while (productionLine.getConsumerCount() < productionLine.getMaxConsumers()) {
                    Log.a("create a new consumer");
                    Thread consumerThread = new Thread(productionLine.addConsumer());
                    consumerThread.start();
                }
                Thread.sleep(1000);
            }

            // ensure we always have consumers while the line has items

            while(productionLine.hasWorkItems()){
                while (productionLine.getConsumerCount() < productionLine.getMaxConsumers()) {
                    Log.a("create a new consumer");
                    Thread consumerThread = new Thread(productionLine.addConsumer());
                    consumerThread.start();
                }
                Thread.sleep(2000);
            }

			// tell us you've finished

            Log.a("process completed with %d on the line", productionLine.workItemsOnLine());
			
		} catch (InterruptedException e) {			
			Log.e("EntryPoint fell over with %s", ExceptionHelper.getStackTrace(e));
		}
		
	}

}
