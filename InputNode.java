
public class InputNode {
	public int handwritten_number;
	public int[] pixel_numbers;
}
/**
for (int j = 0; j < NUMBER_OF_INPUT_NEURONS; j++) {
	System.out.print(inputs[j] + "\t");
}

for (int j = 0; j < 3; j++) {
	System.out.print(inputs[j] + "\t");
}
 */
//private static final int FIRST_VALUE = 1;
//private static final int INPUT_NEURONS = 784;
//private static final int HIDDEN_NEURONS = 100;
//private static final int OUTPUT_NEURONS = 10;
////private static final int MAX_SAMPLES = 60000;
//private static final int MAX_SAMPLES = 100;
//
//private static final double LEARN_RATE = 0.3;
//private static final double NOISE_FACTOR = 0.45;
////private static final int TRAINING_REPS = 10000;
////private static final int TRAINING_REPS = 34832;
////private static final int TRAINING_REPS = 60000;
//private static final int TRAINING_REPS = 100*10;
//private static final String FILE_NAME = "train.csv";
//
//// Input to Hidden Weights (with Biases).
//private static double wih[][] = new double[INPUT_NEURONS + 1][HIDDEN_NEURONS];
//// Hidden to Output Weights (with Biases).
//private static double who[][] = new double[HIDDEN_NEURONS + 1][OUTPUT_NEURONS];
//
//// Activations.
//private static double inputs[] = new double[INPUT_NEURONS];
//private static double hidden[] = new double[HIDDEN_NEURONS];
//private static double target[] = new double[OUTPUT_NEURONS];
//private static double actual[] = new double[OUTPUT_NEURONS];
//
//// Unit errors.
//private static double erro[] = new double[OUTPUT_NEURONS];
//private static double errh[] = new double[HIDDEN_NEURONS];
//
//// 784 input nodes
//private static double[][] trainInputs = new double[MAX_SAMPLES][INPUT_NEURONS+FIRST_VALUE];
//// 10 output nodes
//private static int trainOutput[][] = new int[MAX_SAMPLES][OUTPUT_NEURONS];
//
//private static void readFile() {
//	String csvFile = FILE_NAME;
//	String line = "";
//	String cvsSplitBy = ",";
//	try {
//		BufferedReader br = new BufferedReader(new FileReader(csvFile));
//		int row = 0;
//		while ((line = br.readLine()) != null) {
//			// use comma as separator
//            String[] numbers = line.split(cvsSplitBy);
//            int actual_digit = Integer.parseInt(numbers[0]);
//            trainInputs[row][0] = actual_digit;
//            for (int i=1; i<numbers.length; i++) {
//            	trainInputs[row][i] = Double.parseDouble(numbers[i])/(double)255 * 0.99 + 0.01;
//            }
//            row++;
//		}
//	}
//	catch (FileNotFoundException e) {
//		e.printStackTrace();
//	}
//	catch (IOException e) {
//		e.printStackTrace();
//	}
//}
//private static void assignRandomWeights() {
//	Random rand = new Random(0);
//	double min = -(double)1/Math.sqrt(INPUT_NEURONS);
//	double max = (double)1/Math.sqrt(INPUT_NEURONS);
//	for(int inp = 0; inp <= INPUT_NEURONS; inp++) {
//		for(int hid = 0; hid < HIDDEN_NEURONS; hid++) {
//			// Assign a random weight value between -0.5 and 0.5
//			// Input to Hidden Weights: 5 x 6
////			wih[inp][hid] = new Random().nextDouble() - 0.5;
////			wih[inp][hid] = rand.nextDouble() - 0.5;
//			wih[inp][hid] = (max - min)*rand.nextDouble() + min;
//		}
//	}
//	// Do not subtract 1 here.
//	for(int hid = 0; hid <= HIDDEN_NEURONS; hid++) {
//		for(int out = 0; out < OUTPUT_NEURONS; out++) {
//			// Assign a random weight value between -0.5 and 0.5
//			// Hidden to Output Weights: 7 x 14
////			who[hid][out] = new Random().nextDouble() - 0.5;
////			who[hid][out] = rand.nextDouble() - 0.5;
//			who[hid][out] = (max - min)*rand.nextDouble() + min;
//		}
//	}
//}
//private static void NeuralNetwork() {
//	readFile();
//	// establish trainOutput
//	for (int sample = 0; sample < MAX_SAMPLES; sample++) {
//		for (int i = 0; i < OUTPUT_NEURONS; i++) {
//			if (trainInputs[sample][0] == i) {
//				trainOutput[sample][i] = 1; 
//			}
//			else {
//				trainOutput[sample][i] = 0;
//			}
//		}
//	}
//	/**
//	for (int i = 0; i < MAX_SAMPLES; i++) {
//		for (int j = 0; j < OUTPUT_NEURONS; j++) {
//			System.out.print(trainOutput[i][j] + " ");
//		}
//		System.out.println();
//	}
//	*/
//	int sample = 0;
//	assignRandomWeights();
//	// Train the network.
//	for (int epoch = 0; epoch < TRAINING_REPS; epoch++) {
//		if (sample == MAX_SAMPLES){
//            sample = 0;
//        }
//		
//		for (int i = 0; i < INPUT_NEURONS; i++) {
//			inputs[i] = trainInputs[sample][i+FIRST_VALUE];
//		}
//		
//		int signal = (int)trainInputs[sample][0];
//		for(int i = 0; i < OUTPUT_NEURONS; i++) {
//			target[i] = trainOutput[sample][i];
//		}
//		feedForward();
//		backPropagate();
//		sample += 1;
//	}
//	getTrainingStats();
////	System.out.println("\nTest network against original input:");
//	testNetworkTraining();
//}
//private static void testNetworkTraining() {
//	// This function simply tests the training vectors against network.
//	for (int i = 0; i < MAX_SAMPLES; i++) {
//		for (int j = 0; j < INPUT_NEURONS; j++) {
//			inputs[j] = trainInputs[i][j+FIRST_VALUE];
//		}
//		feedForward();
//		/**
//		for (int j = 0; j < INPUT_NEURONS; j++) {
//			System.out.print(inputs[j] + "\t");
//		}
//		*/
//		/**
//		for (int j = 0; j < 3; j++) {
//			System.out.print(inputs[j] + "\t");
//		}
//		*/
//		if (i > MAX_SAMPLES-100)
//			System.out.print("Output: " + maximum(actual) + "\n");
//	}
//}
//private static void getTrainingStats() {
//	double sum = 0.0;
//	for (int i = 0; i < MAX_SAMPLES; i++) {
//		for (int j = 0; j < INPUT_NEURONS; j++) {
//			inputs[j] = trainInputs[i][j+FIRST_VALUE];
//		}
//		for (int j = 0; j < OUTPUT_NEURONS; j++) {
//			target[j] = trainOutput[i][j];
//		}
//		feedForward();
//		if (maximum(actual) == maximum(target)) {
//			sum += 1;
//		}
//		else {
////			System.out.println(inputs[0] + "\t" + inputs[1] + "\t" + inputs[2] + "\t" + inputs[3]);
////			System.out.println(maximum(actual) + "\t" + maximum(target));
//		}
//	}
//	System.out.println("Network is " + ((double)sum / (double)MAX_SAMPLES * 100.0) + "% correct.");
//}
//private static int maximum(final double[] vector) {
//	// This function returns the index of the maximum of vector().
//	int sel = 0;
//	double max = vector[sel];
//	for(int index = 0; index < OUTPUT_NEURONS; index++) {
//		if(vector[index] > max) {
//			max = vector[index];
//			sel = index;
//		}
//	}
//	return sel;
//}
//private static void feedForward() {
//	double sum = 0.0;
//	// Calculate input to hidden layer.
//	for (int hid = 0; hid < HIDDEN_NEURONS; hid++) {
//		sum = 0.0;
//		for(int inp = 0; inp < INPUT_NEURONS; inp++) {
//			sum += inputs[inp] * wih[inp][hid];
//		}
//		// Add in bias.
//		/**
//		 * Why do we need to add bias?
//		 * bias = wih[INPUT_NEURONS][hid];
//		 */
//		sum += wih[INPUT_NEURONS][hid];
//		hidden[hid] = sigmoid(sum);
//	}
//	// Calculate the hidden to output layer.
//	for(int out = 0; out < OUTPUT_NEURONS; out++) {
//		sum = 0.0;
//		for(int hid = 0; hid < HIDDEN_NEURONS; hid++) {
//			sum += hidden[hid] * who[hid][out];
//		}
//		// Add in bias.
//		sum += who[HIDDEN_NEURONS][out];
//		actual[out] = sigmoid(sum);
//	}
//}
//private static double sigmoid(final double val) {
//	return (1.0 / (1.0 + Math.exp(-val)));
//}
//private static void backPropagate() {
//	// Calculate the output layer error (step 3 for output cell).
//	for (int out = 0; out < OUTPUT_NEURONS; out++) {
//		erro[out] = (target[out] - actual[out]) * sigmoidDerivative(actual[out]);
//	}
//	// Calculate the hidden layer error (step 3 for hidden cell).
//	for (int hid = 0; hid < HIDDEN_NEURONS; hid++) {
//		errh[hid] = 0.0;
//		for (int out = 0; out < OUTPUT_NEURONS; out++) {
//			errh[hid] += erro[out] * who[hid][out];
//		}
//		errh[hid] *= sigmoidDerivative(hidden[hid]);
//	}
//	// Update the weights for the output layer (step 4).
//	for(int out = 0; out < OUTPUT_NEURONS; out++) {
//		for(int hid = 0; hid < HIDDEN_NEURONS; hid++) {
//			who[hid][out] += (LEARN_RATE * erro[out] * hidden[hid]);
//		}
//		// Update the bias.
//		who[HIDDEN_NEURONS][out] += (LEARN_RATE * erro[out]);
//	}
//	// Update the weights for the hidden layer (step 4).
//	for(int hid = 0; hid < HIDDEN_NEURONS; hid++) {
//		for(int inp = 0; inp < INPUT_NEURONS; inp++) {
//			wih[inp][hid] += (LEARN_RATE * errh[hid] * inputs[inp]);
//		}
//		// Update the bias.
//		wih[INPUT_NEURONS][hid] += (LEARN_RATE * errh[hid]);
//	}
//}
//private static double sigmoidDerivative(final double val) {
//    return (val * (1.0 - val));
//}