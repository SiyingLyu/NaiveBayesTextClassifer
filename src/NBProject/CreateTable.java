package NBProject;

public class CreateTable {
    int[] label = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
    final int numberOfClasses = 20;
    final int numberOFTotalWords = 61188;

    public double[] calcPrio(int[] train_lable) {
        double[] prior = new double[numberOfClasses];
        int[] count = new int[numberOfClasses];
        for (int i = 0; i < train_lable.length; i++) {
            for (int j = 0; j< label.length; j++){
                if (train_lable[i] == label[j]){
                    count[j]++;
                }
            }
        }

        for (int i = 0; i < label.length; i++) {
            prior[i] = (double) count[i]/train_lable.length;
//            System.out.println(prior[i]);
        }
        return prior;
    }

    public int[] calcWordPerClass(int[][] train_data, int[] train_label, int numberOfTrainData) {
        int[] wordsCount = new int[numberOfClasses];
        for (int i = 0; i < numberOfTrainData; i++) {
            wordsCount[train_label[train_data[i][0] - 1] - 1] += train_data[i][2];
        }
        return wordsCount;
    }

    public int[][] calcFreqPerWordPerClass(int[][] train_data, int[] train_label, int numOfTrainData) {
        int[][] freqCount = new int[numberOFTotalWords][numberOfClasses];
        for (int i=0; i<numOfTrainData; i++) {
            freqCount[train_data[i][1]-1][train_label[train_data[i][0] - 1] - 1] += train_data[i][2];
        }
        return freqCount;
    }

    public int[] classifier(double[][] lnTable, double[] prior, int[][] targetData, int numOfTargetFile) {
        double[][] result = new double[numOfTargetFile][numberOfClasses];
        int[] classify = new int[numOfTargetFile];
        for (int i=0; i<targetData.length; i++) {
            for (int j=0; j<numberOfClasses; j++) {
                result[targetData[i][0]-1][j] += targetData[i][2]*lnTable[targetData[i][1]-1][j]+prior[j];
            }
        }

        for(int m=0; m<numOfTargetFile; m++){
            int index = 0; double max = result[m][0];
            for (int k=1; k<numberOfClasses; k++) {
                if(result[m][k] > max) {
                    max = result[m][k];
                    index = k;
                }
                classify[m] = index+1;
            }
        }
        return classify;
    }

    public double[] classAccuracy(int[] classifyResult, int[] original) {
        double[] result = new double[numberOfClasses];
        int[] countClass = new int[numberOfClasses];
        int[] countOrigin = new int[numberOfClasses];
        for(int i = 0; i < classifyResult.length; i++) {
            for(int j = 0; j < numberOfClasses; j++) {
                if (label[j] == original[i]) {
                    countOrigin[j] ++;
                    if (classifyResult[i] == original[i]) {
                        countClass[j] ++;
                    }
                }
            }
        }

        for(int i = 0; i< numberOfClasses; i++) {
            result[i] = (double)countClass[i]/(double)countOrigin[i];
        }

        return result;
    }

    public double overallAccuracy(int[] classifyResult, int[] original) {
        double trainMLEAccu;
        int countClass = 0;
        for(int i = 0; i<classifyResult.length;i++) {
            if(classifyResult[i] == original[i]) {
                countClass ++;
            }
        }
        trainMLEAccu = (double) countClass/ (double) classifyResult.length;
        return  trainMLEAccu;
    }

    public void confusion(int[] classifyResult, int[] original) {
        int[][] result = new int[numberOfClasses][numberOfClasses];
        for (int m = 0; m<original.length; m++) {
            result[original[m]-1][classifyResult[m]-1]++;
        }

        for (int i=0; i<numberOfClasses; i++) {
            for(int j =0; j< numberOfClasses;j++) {
                System.out.printf(" %3d ", result[i][j]);
            }
            System.out.println();
        }
    }
}
