package NBProject;

public class Operation {
    static final int numberOfTrainFile = 11269;
    static final int numberOfTrainData = 1467345;
    static final int numberOfTestFile = 7505;
    static final int numberOfTestData = 967874;
    static final int numberOFTotalWords = 61188;
    static final int numberOfClasses = 20;

    public static void main(String[] args) {
        // Table needed for the calculation
        int[] numOfWordsPerClass = new int[numberOfClasses];
        int[][] freqOfWordsPerClass = new int[numberOFTotalWords][numberOfClasses];
        double[][] MLETable = new double[numberOFTotalWords][numberOfClasses];
        double[][] BETable = new double[numberOFTotalWords][numberOfClasses];
        double[] prior = new double[numberOfClasses];

        // Load all the raw tables
        FileReaderTool load = new FileReaderTool();
        String[][] mapTable = load.doubleOutPut("20newsgroups/map.csv",numberOfClasses);
        String[] vocabulary = load.singleOutPutS("20newsgroups/vocabulary.txt",numberOFTotalWords);
        int[] testLabel = load.singleOutPut("20newsgroups/test_label.csv",numberOfTestFile);
        int[][] testData = load.tripleOutPut("20newsgroups/test_data.csv",numberOfTestData);
        int[] trainLabel = load.singleOutPut("20newsgroups/train_label.csv",numberOfTrainFile);
        int[][] trainData = load.tripleOutPut("20newsgroups/train_data.csv",numberOfTrainData);

        // Calculate the prior per class
        CreateTable tableLoad = new CreateTable();
        prior = tableLoad.calcPrio(trainLabel);

        //Calculate the number of words per class
        numOfWordsPerClass = tableLoad.calcWordPerClass(trainData,trainLabel,numberOfTrainData);

        //Calculate the frequency of words per class
        freqOfWordsPerClass = tableLoad.calcFreqPerWordPerClass(trainData,trainLabel,numberOfTrainData);

        //Calculate the MLE Table (Ln)
        for(int i=0; i<numberOFTotalWords; i++) {
            for(int j=0; j<numberOfClasses; j++) {
                MLETable[i][j] = Math.log((double)freqOfWordsPerClass[i][j]/(double)numOfWordsPerClass[j]);
            }
        }

        //Calculate the BE Table (Ln)
        for(int i=0; i<numberOFTotalWords; i++) {
            for(int j=0; j<numberOfClasses; j++) {
                BETable[i][j] = Math.log((double)freqOfWordsPerClass[i][j]/(double)numOfWordsPerClass[j]);
                if (freqOfWordsPerClass[i][j] == 0) {
                    BETable[i][j] = Math.log((double)(freqOfWordsPerClass[i][j] + 1)/(double)(numOfWordsPerClass[j] + numberOFTotalWords));
                }
            }
        }

        //Take the Ln of prior
        for(int i=0; i<numberOfClasses;i++){
            prior[i] = Math.log(prior[i]);
        }

        //==========================================================================
        //Classification
        int[] trainMLE = tableLoad.classifier(MLETable,prior,trainData,numberOfTrainFile);
        int[] trainBE = tableLoad.classifier(BETable,prior,trainData,numberOfTrainFile);
        int[] testMLE = tableLoad.classifier(MLETable,prior,testData,numberOfTestFile);
        int[] testBE = tableLoad.classifier(BETable,prior,testData,numberOfTestFile);

        //===========================================================================
        //Evaluation
        System.out.println("Class priors:");
        for(int i=0; i<numberOfClasses; i++) {
            System.out.printf(mapTable[i][0] + "." + mapTable[i][1] + " : %.4f%n", Math.exp(prior[i]));
        }
        System.out.println();

        System.out.printf("MLE overall accuracy for training data: %.4f%n", + tableLoad.overallAccuracy(trainMLE,trainLabel));
        System.out.printf("BE overall accuracy for training data: %.4f%n", + tableLoad.overallAccuracy(trainBE,trainLabel));
        System.out.printf("MLE overall accuracy for testing data: %.4f%n", + tableLoad.overallAccuracy(testMLE,testLabel));
        System.out.printf("BE overall accuracy for testing data: %.4f%n", + tableLoad.overallAccuracy(testBE,testLabel));
        System.out.println();

        double[] MLEClassAccTrain;
        MLEClassAccTrain = tableLoad.classAccuracy(trainMLE,trainLabel);
        System.out.println("MLE Class accuracy for training data:");
        for(int i=0; i< numberOfClasses; i++) {
            System.out.printf(mapTable[i][0] + "." + mapTable[i][1] + " : %.4f%n", MLEClassAccTrain[i]);
        }
        System.out.println();

        double[] BEClassAccTrain;
        BEClassAccTrain = tableLoad.classAccuracy(trainBE,trainLabel);
        System.out.println("BE Class accuracy for training data:");
        for(int i=0; i< numberOfClasses; i++) {
            System.out.printf(mapTable[i][0] + "." + mapTable[i][1] + " : %.4f%n", BEClassAccTrain[i]);
        }
        System.out.println();

        double[] MLEClassAccTest;
        MLEClassAccTest = tableLoad.classAccuracy(testMLE,testLabel);
        System.out.println("MLE Class accuracy for testing data:");
        for(int i=0; i< numberOfClasses; i++) {
            System.out.printf(mapTable[i][0] + "." + mapTable[i][1] + " : %.4f%n", MLEClassAccTest[i]);
        }
        System.out.println();

        double[] BEClassAccTest;
        BEClassAccTest = tableLoad.classAccuracy(testBE,testLabel);
        System.out.println("BE Class accuracy for testing data:");
        for(int i=0; i< numberOfClasses; i++) {
            System.out.printf(mapTable[i][0] + "." + mapTable[i][1] + " : %.4f%n", BEClassAccTest[i]);
        }
        System.out.println();

        System.out.println("Confusion Matrix of MLE for training data: ");
        tableLoad.confusion(trainMLE,trainLabel);
        System.out.println();

        System.out.println("Confusion Matrix of BE for training data: ");
        tableLoad.confusion(trainBE,trainLabel);
        System.out.println();

        System.out.println("Confusion Matrix of MLE for testing data: ");
        tableLoad.confusion(testMLE,testLabel);
        System.out.println();

        System.out.println("Confusion Matrix of BE for testing data: ");
        tableLoad.confusion(testBE,testLabel);
    }
}
