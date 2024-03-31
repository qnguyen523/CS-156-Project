import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class NeuralNetwork implements java.io.Serializable {
	public  final int FIRST_VALUE = 1;
	public  final int NUMBER_OF_INPUT_NEURONS = 784;
	public  final int NUMBER_OF_HIDDEN_NEURONS = 100;
	public  final int NUMBER_OF_OUTPUT_NEURONS = 10;

//	public  final int MAX_SAMPLES = 60000;
	
	// This code is for testing purpose
	public  final int MAX_SAMPLES = 50;
	
	public  final double LEARNING_RATE = 0.3;
//	public final int TRAINING_REPETITIONS = MAX_SAMPLES;
	
	// This code is for testing purpose
	public  final int TRAINING_REPETITIONS = MAX_SAMPLES*10;
	
//	public  final String FILE_NAME = "mnist_train.csv";
	// This code is for testing purpose
	public  final String FILE_NAME = "train.csv";
	
	// Input to Hidden Weights
	// + 1 is for a bias
	public  double input_hidden_weights[][] = new double[NUMBER_OF_INPUT_NEURONS + 1][NUMBER_OF_HIDDEN_NEURONS];
	// Hidden to Output Weights
	// + 1 is for a bias
	public  double hiden_output_weights[][] = new double[NUMBER_OF_HIDDEN_NEURONS + 1][NUMBER_OF_OUTPUT_NEURONS];

	// activation
	public  double inputs[] = new double[NUMBER_OF_INPUT_NEURONS];
	public  double hidden[] = new double[NUMBER_OF_HIDDEN_NEURONS];
	public  double target[] = new double[NUMBER_OF_OUTPUT_NEURONS];
	public  double actual[] = new double[NUMBER_OF_OUTPUT_NEURONS];

	// errors
	public  double output_errors[] = new double[NUMBER_OF_OUTPUT_NEURONS];
	public  double hidden_errors[] = new double[NUMBER_OF_HIDDEN_NEURONS];
	
	// 784 input nodes + 1 label node
	public  double[][] trainInputs = new double[MAX_SAMPLES][NUMBER_OF_INPUT_NEURONS+FIRST_VALUE];
    // 10 output nodes
	public  int trainOutput[][] = new int[MAX_SAMPLES][NUMBER_OF_OUTPUT_NEURONS];
	/*
	 * Read CSV file
	 * Re-scale the input color values to the much smaller range 0.01 - 1.0
	 */
	public  void readTrainFile(String file_name) {
		String csvFile = file_name;
		String line = "";
		String cvsSplitBy = ",";
		try {
			BufferedReader br = new BufferedReader(new FileReader(csvFile));
			int row = 0;
			while ((line = br.readLine()) != null) {
				// use comma as separator
                String[] numbers = line.split(cvsSplitBy);
                int actual_digit = Integer.parseInt(numbers[0]);
                trainInputs[row][0] = actual_digit;
                for (int i=1; i<numbers.length; i++) {
                	trainInputs[row][i] = Double.parseDouble(numbers[i])/(double)255 * 0.99 + 0.01;
                }
                row++;
//                System.out.println(row);
			}
			// This code is for testing purpose
//			System.out.println(row);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public  double[][] readTestFile(String file_name, int max_samples) {
		double[][] testInputs = new double[max_samples][NUMBER_OF_INPUT_NEURONS+FIRST_VALUE];
		String csvFile = file_name;
		String line = "";
		String cvsSplitBy = ",";
		try {
			BufferedReader br = new BufferedReader(new FileReader(csvFile));
			int row = 0;
			while ((line = br.readLine()) != null) {
				// use comma as separator
                String[] numbers = line.split(cvsSplitBy);
                int actual_digit = Integer.parseInt(numbers[0]);
                testInputs[row][0] = actual_digit;
                for (int i=1; i<numbers.length; i++) {
                	testInputs[row][i] = Double.parseDouble(numbers[i])/(double)255 * 0.99 + 0.01;
                }
                row++;
			}
			// This code is for testing purpose
//			System.out.println(row);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return testInputs;
	}
	/*
	 * Assign random weights to input_hidden_weights and hiden_output_weights
	 */
	public  void assignRandomInitialWeights() {
		// This code is for testing purpose
		Random rand = new Random(0);
		// Random rand = new Random();
		double min = -(double)1/Math.sqrt(NUMBER_OF_HIDDEN_NEURONS);
		double max = (double)1/Math.sqrt(NUMBER_OF_HIDDEN_NEURONS);
		for(int inp = 0; inp <= NUMBER_OF_INPUT_NEURONS; inp++) {
			for(int hid = 0; hid < NUMBER_OF_HIDDEN_NEURONS; hid++) {
				// Assign a random weight value between -1/10 to 1/10
				input_hidden_weights[inp][hid] = (max - min)*rand.nextDouble() + min;
			}
		}

		for(int hid = 0; hid <= NUMBER_OF_HIDDEN_NEURONS; hid++) {
			for(int out = 0; out < NUMBER_OF_OUTPUT_NEURONS; out++) {
				// Assign a random weight value between -1/10 to 1/10
				hiden_output_weights[hid][out] = (max - min)*rand.nextDouble() + min;
			}
		}
	}
	/*
	 * Constructor with no parameters
	 */
	public NeuralNetwork() {
		readTrainFile(FILE_NAME);
		// establish trainOutput
		for (int sample = 0; sample < MAX_SAMPLES; sample++) {
			for (int i = 0; i < NUMBER_OF_OUTPUT_NEURONS; i++) {
				if (trainInputs[sample][0] == i) {
					trainOutput[sample][i] = 1; 
				}
				else {
					trainOutput[sample][i] = 0;
				}
			}
		}
		int sample = 0;
		assignRandomInitialWeights();
		// Train the neural network
		for (int epoch = 0; epoch < TRAINING_REPETITIONS; epoch++) {
			if (sample == MAX_SAMPLES){
                sample = 0;
            }
			
			for (int i = 0; i < NUMBER_OF_INPUT_NEURONS; i++) {
				inputs[i] = trainInputs[sample][i+FIRST_VALUE];
			}
			
			int signal = (int)trainInputs[sample][0];
			for(int i = 0; i < NUMBER_OF_OUTPUT_NEURONS; i++) {
				target[i] = trainOutput[sample][i];
			}
			propagateSignalsForward();
			propagateErrorsBackward();
			sample += 1;
		}
		System.out.println("Done training");
		
		// These code is for testing purpose
		// test training set
		statistic(trainInputs, MAX_SAMPLES);
		testNeuralNetworkWithTrainingSet(MAX_SAMPLES);
	}
	/*
	 * test the neural network with training set
	 */
	public  void testNeuralNetworkWithTrainingSet(int max_samples) {
		System.out.println("Length of trainInputs: " + trainInputs.length);
		System.out.println("The last 100 records' prediction:");
		for (int i = 0; i < max_samples; i++) {
			for (int j = 0; j < NUMBER_OF_INPUT_NEURONS; j++) {
				inputs[j] = trainInputs[i][j+FIRST_VALUE];
			}
			propagateSignalsForward();
			
			// print out the prediction about the last 100 records
			if (i > max_samples-100)
				System.out.print("Predicted handwritten number: " + maximumIndex(actual) + "\n");
		}
	}
	/*
	 * test the neural network with test set
	 */
	public  void testNeuralNetworkWithTestSet(double[][] testInputs, int max_samples) {
		System.out.println("Length of testInputs: " + testInputs.length);
		System.out.println("The last 100 records' prediction:");
		for (int i = 0; i < max_samples; i++) {
			for (int j = 0; j < NUMBER_OF_INPUT_NEURONS; j++) {
				inputs[j] = testInputs[i][j+FIRST_VALUE];
			}
			propagateSignalsForward();
			
			// print out the prediction about the last 100 records
			if (i > max_samples-100)
				System.out.print("Predicted handwritten number: " + maximumIndex(actual) + "\n");
		}
	}
	/*
	 * get testing statistics
	 */
	public  void statistic(double[][] testInputs, int max_samples) {
		int testOutput[][] = new int[max_samples][NUMBER_OF_OUTPUT_NEURONS];
		// establish testOutput
		for (int sample = 0; sample < max_samples; sample++) {
			for (int i = 0; i < NUMBER_OF_OUTPUT_NEURONS; i++) {
				if (testInputs[sample][0] == i) {
					testOutput[sample][i] = 1; 
				}
				else {
					testOutput[sample][i] = 0;
				}
			}
		}
		
		double sum = 0.0;
		for (int i = 0; i < max_samples; i++) {
			for (int j = 0; j < NUMBER_OF_INPUT_NEURONS; j++) {
				inputs[j] = testInputs[i][j+FIRST_VALUE];
			}
			for (int j = 0; j < NUMBER_OF_OUTPUT_NEURONS; j++) {
				target[j] = testOutput[i][j];
			}
			propagateSignalsForward();
			if (maximumIndex(actual) == maximumIndex(target)) {
				// This code is for testing purpose
//				System.out.println(maximumIndex(actual) + "\t" + maximumIndex(target));
				sum += 1;
			}
			else {
				// This code is for testing purpose
//				System.out.println(inputs[0] + "\t" + inputs[1] + "\t" + inputs[10] + "\t" + inputs[20]);
//				System.out.println(maximumIndex(actual) + "\t" + maximumIndex(target));
			}
		}
		System.out.println("The accuracy of this neural network is " + ((double)sum / (double)max_samples * 100.0) + " %");
	}
	/*
	 * get the index of the maximum element of an array
	 */
	public  int maximumIndex(double[] array) {
		int rtn = 0;
		double max = array[rtn];
		for(int index = 0; index < NUMBER_OF_OUTPUT_NEURONS; index++) {
			if(array[index] > max) {
				max = array[index];
				rtn = index;
			}
		}
		return rtn;
	}
	/*
	 * use the initial assigned weights to propagate signals forward from
	 * the input layer to the output layer in the neural network
	 */
	public  void propagateSignalsForward() {
		double sum = 0.0;
		// the input to hidden layer
		for (int hid = 0; hid < NUMBER_OF_HIDDEN_NEURONS; hid++) {
			sum = 0.0;
			for(int inp = 0; inp < NUMBER_OF_INPUT_NEURONS; inp++) {
				sum += inputs[inp] * input_hidden_weights[inp][hid];
			}
			/**
			 * Why do we need to add bias?
			 * Because we want to improve our neural network
			 */
			// Add bias.
			sum += input_hidden_weights[NUMBER_OF_INPUT_NEURONS][hid];
			hidden[hid] = sigmoid(sum);
		}
		// the hidden to output layer.
		for(int out = 0; out < NUMBER_OF_OUTPUT_NEURONS; out++) {
			sum = 0.0;
			for(int hid = 0; hid < NUMBER_OF_HIDDEN_NEURONS; hid++) {
				sum += hidden[hid] * hiden_output_weights[hid][out];
			}
			// Add bias.
			sum += hiden_output_weights[NUMBER_OF_HIDDEN_NEURONS][out];
			actual[out] = sigmoid(sum);
		}
	}
	/*
	 * calculate sigmoid for a value
	 */
	public  double sigmoid(double value) {
		return (1.0 / (1.0 + Math.exp(-value)));
	}
	/*
	 * use the initial assigned weights to propagate errors backward from
	 * the output layer to the input layer. Then, we update the weights to
	 * improve the learning ability of our neural network
	 */
	public  void propagateErrorsBackward() {
		// output layer error
		for (int out = 0; out < NUMBER_OF_OUTPUT_NEURONS; out++) {
			output_errors[out] = (target[out] - actual[out]) * derivativeOfSigmoid(actual[out]);
		}
		// hidden layer error
		for (int hid = 0; hid < NUMBER_OF_HIDDEN_NEURONS; hid++) {
			hidden_errors[hid] = 0.0;
			for (int out = 0; out < NUMBER_OF_OUTPUT_NEURONS; out++) {
				hidden_errors[hid] += output_errors[out] * hiden_output_weights[hid][out];
			}
			hidden_errors[hid] *= derivativeOfSigmoid(hidden[hid]);
		}
		// update the weights for hiden_output_weights
		for(int out = 0; out < NUMBER_OF_OUTPUT_NEURONS; out++) {
			for(int hid = 0; hid < NUMBER_OF_HIDDEN_NEURONS; hid++) {
				hiden_output_weights[hid][out] += (LEARNING_RATE * output_errors[out] * hidden[hid]);
			}
			// update the bias
			hiden_output_weights[NUMBER_OF_HIDDEN_NEURONS][out] += (LEARNING_RATE * output_errors[out]);
			
//			if (out == 0)
//				System.out.println("1: "+ hiden_output_weights[NUMBER_OF_HIDDEN_NEURONS][out]);
		}
		// update the weights for input_hidden_weights
		for(int hid = 0; hid < NUMBER_OF_HIDDEN_NEURONS; hid++) {
			for(int inp = 0; inp < NUMBER_OF_INPUT_NEURONS; inp++) {
				input_hidden_weights[inp][hid] += (LEARNING_RATE * hidden_errors[hid] * inputs[inp]);
			}
			// update the bias
			input_hidden_weights[NUMBER_OF_INPUT_NEURONS][hid] += (LEARNING_RATE * hidden_errors[hid]);
		
//			if (hid == 0)
//				System.out.println("2: "+ input_hidden_weights[NUMBER_OF_INPUT_NEURONS][hid]);
		}
	}
	/*
	 * calculate derivative of sigmoid function
	 */
	public  double derivativeOfSigmoid(double value) {
        return (value * (1.0 - value));
    }
}
