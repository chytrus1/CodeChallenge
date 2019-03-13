public class Main {

	public static void main(String[] args) {
		String filePath2 = "resources/logs_example.txt";
		LoggerProcessor loggerProcessor = new LoggerProcessor();
		loggerProcessor.processLogFile(filePath2);
		// getting file path as 1 argument		
		if(args.length>0){
			String filePath = args[0];
		} else {
			System.out.println("\nNo path to input file\nType path as first parameter");
		}
	}
}