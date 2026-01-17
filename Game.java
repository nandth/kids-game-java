public class Game {
    public int score;
    public String summary = "";

    private String currentQuestionText;
    private double currentAnswer;
    private boolean isDoubleAnswer;

    public Game() {
        this.score = 0;
        this.summary = "";
    }

    public void generateNextQuestion() {
        int op = (int) (Math.random() * 4);
        switch (op) {
            case 0 -> generateAddQuestions();
            case 1 -> generateSubQuestions();
            case 2 -> generateMulQuestions();
            case 3 -> generateDivQuestions();
        }
    }

    private void generateAddQuestions() {
        int num1 = (int)(Math.random() * 20);
        int num2 = (int)(Math.random() * 20);
        currentAnswer = num1 + num2;
        currentQuestionText = "What is " + num1 + " + " + num2 + " ?";
        isDoubleAnswer = false;
    }

    private void generateSubQuestions() {
        int num1 = (int)(Math.random() * 20);
        int num2 = (int)(Math.random() * 20);
        int big = Math.max(num1, num2);
        int small = Math.min(num1, num2);
        currentAnswer = big - small;
        currentQuestionText = "What is " + big + " - " + small + " ?";
        isDoubleAnswer = false;
    }

    private void generateMulQuestions() {
        int num1 = (int)(Math.random() * 12);
        int num2 = (int)(Math.random() * 12);
        currentAnswer = num1 * num2;
        currentQuestionText = "What is " + num1 + " * " + num2 + " ?";
        isDoubleAnswer = false;
    }

    private void generateDivQuestions() {
        int num1 = (int)(Math.random() * 90) + 10;
        int num2 = (int)(Math.random() * 9) + 1;

        double exact = (double) num1 / num2;
        currentAnswer = Math.round(exact * 100.0) / 100.0;
        currentQuestionText = "What is " + num1 + " / " + num2 + " ? (2 decimals)";
        isDoubleAnswer = true;
    }

    public boolean checkAnswer(String input) {
        boolean correct = false;
        double userVal = 0;

        try {
            userVal = Double.parseDouble(input);
            if (isDoubleAnswer) {
                correct = Math.abs(userVal - currentAnswer) < 0.01;
            } else {
                correct = (int)userVal == (int)currentAnswer;
            }
        } catch (NumberFormatException e) {
            correct = false;
        }

        summary += "\n" + currentQuestionText + " Your Ans: " + input + " [" + (correct ? "Correct" : "Wrong") + "]";
        if (correct) ++score;

        return correct;
    }

    public String getCurrentQuestionText() {
        return currentQuestionText;
    }
}