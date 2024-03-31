import java.io.*;
import java.util.Random;
public class Main {
	/*
	 * train the neural network and save it to neural_network.txt file
	 */
	private static void trainNeuralNetwork() {
		// serialization
		NeuralNetwork NN = new NeuralNetwork();
//		try
//		{
//			FileOutputStream fileOut = new FileOutputStream("neural_network.txt");
//			ObjectOutputStream out = new ObjectOutputStream(fileOut);
//			out.writeObject(NN);
//			out.close();
//			fileOut.close();
//			System.out.println("Serialized neural network is saved.");
//		}
//		catch (IOException ex)
//		{
//			ex.printStackTrace();
//		}
	}
	/*
	 * load the neural network from neural_network.txt file and test it
	 */
	private static void testNeuralNetwork() {
		// deserialization
		NeuralNetwork trained_NN = null;
		try
		{
			FileInputStream fileIn = new FileInputStream("neural_network.txt");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			trained_NN = (NeuralNetwork) in.readObject();
			in.close();
			fileIn.close();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			return;
		}
		catch(ClassNotFoundException c)
		{
			c.printStackTrace();
			return;
		}
		
		
		int max_samples = 10000;
		// This code is for testing purpose
		/**
		int max_samples = 10;
		double[][] testInputs = trained_NN.readTestFile("test.csv", max_samples);
		*/
		
		double[][] testInputs = trained_NN.readTestFile("mnist_test.csv", max_samples);
		trained_NN.statistic(testInputs, max_samples);
		trained_NN.testNeuralNetworkWithTestSet(testInputs, max_samples);
	}
	/*
	 * First run: keep trainNeuralNetwork() and comment out testNeuralNetwork()
	 * Second run: keep testNeuralNetwork() and comment out trainNeuralNetwork()
	 * Because the trainNeuralNetwork() function will take a while to train the ANN, 
	 * I also include the serialized file in my submission, so you don't need to
	 * train the network. The main method only need a function call of 
	 * testNeuralNetwork().
	 */
	public static void main(String[] args) throws IOException {
		trainNeuralNetwork();
//		testNeuralNetwork();
	}
}
