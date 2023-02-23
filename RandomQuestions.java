import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
import java.util.Iterator;

public class RandomQuestions extends PlayGame
{
	public String questionsFile, answersFile;
	public static ArrayList<String> questions;
	public static ArrayList<String[]> answers;
	
	private static int questionsCounter = 0;
	
	private static int rightAnswer;
	private static int[] correctAnswers;
	
	public RandomQuestions()
	{
		questions = new ArrayList<String>();
		answers = new ArrayList<String[]>();
		
		questionsFile = "D:\\Users\\Liebm\\IntroJava\\SpongebobTrivia\\src\\Questions";
		answersFile ="D:\\Users\\Liebm\\IntroJava\\SpongebobTrivia\\src\\Answers";
		String questionContents = accessFolder(questionsFile);
		
		String answerContents = accessFolder(answersFile);
		
		questions = questionsToArrayList(questionContents);
		answers = answersToArrayList(answerContents);
		
		correctAnswers = new int[questions.size()];
		java.util.Arrays.fill(correctAnswers, 0);
		
		shuffle(questions, answers);
		
		shuffleAnswers();

	}
	
	private String accessFolder(String selectedFile)
	{
		String fileContents = "";

		try{
			Scanner files = new Scanner(new File(selectedFile));
		while(files.hasNextLine())
		{
			fileContents = fileContents.concat(files.nextLine() + "\n");
		}} catch (IOException exc){
		
		exc.printStackTrace();}
		
		return fileContents;
	}
	
	private ArrayList<String> questionsToArrayList(String fileContents)
	{
		for(int i = 0; i < fileContents.length(); i++)
		{
			int questionMark = fileContents.indexOf('?');
			if(fileContents.charAt(questionMark) == '?')
			{
				String savedQuestion = fileContents.substring(0, questionMark + 1);

				questions.add(savedQuestion);

				fileContents = fileContents.substring(questionMark + 2, fileContents.length());

				questionsCounter++;
				questionsToArrayList(fileContents);
				
				String noNumberQ = questions.get(--questionsCounter).substring(3, questionMark + 1);

				questions.set(questionsCounter, noNumberQ);
				
				return questions;
			}
		}
		return questions;
	}
	

	private ArrayList<String[]> answersToArrayList(String fileContents)
	{
		String[][] answersIn2D = answersTo2DArray(fileContents);
		
		for(int i = 0; i < questions.size(); i++)
		{
			answers.add(answersIn2D[i]);
		}
		return answers;
	}
	
	private static String[][] answersTo2DArray(String fileContents)
	{
		int numOfQs = questions.size();
		int abcd = 4;
		String[][] answersIn2D = new String[numOfQs][abcd];
		Scanner answerLine = new Scanner(fileContents);
		for(int i = 0; i < numOfQs; i++)
		{
			for(int j = 0; j < abcd; j++)
			{
				while(answerLine.hasNextLine())
				{
					if(j == 0)
					{
						String answer = answerLine.nextLine();
						answer = answer.substring(3, answer.length());
						answersIn2D[i][j] = answer;
						break;
					}
					else {
					String answer = answerLine.nextLine();
					answer = answer.substring(1, answer.length());
					answersIn2D[i][j] = answer;
					break;	}
				}
			}
		}
		return answersIn2D;
	}

	private static void shuffle(ArrayList<String> shuffledQ,
			ArrayList<String[]> shuffledA)
	{
		int chosenIndex;
		int questionsSize = questions.size();
		ArrayList<String[]> copy = new ArrayList<>();
		
		for(String[] copy2 : answers)
			copy.add(copy2);
		int[] emptyAnswers = new int[questions.size()];

		ArrayList<Integer> ans = new ArrayList<>();
		for(int i = 0; i < questionsSize; i++)
		{
			System.out.println(correctAnswers[i]);
			rightAnswer = correctAnswers[i];
			
			for(int j = 0; j < answers.get(i).length; j++)
			{
				int answerIndex = (int)(Math.random() * answers.get(i).length);

				if(j == rightAnswer || answerIndex == rightAnswer)
				{
					if (answerIndex == rightAnswer)
					{
						rightAnswer = j;
						
					}
					else if(j == rightAnswer)
						rightAnswer = answerIndex;
				}
				System.out.println(" - " + rightAnswer);
				swapAnswers(i, j, answerIndex, copy);
			}
			emptyAnswers[i] = rightAnswer;

			if((questionsSize-1) == i) {
				correctAnswers = emptyAnswers;
				for(int m = 0; m < questionsSize; m++)
				{
					chosenIndex = (int)(Math.random() * questionsSize);
					swap(m, chosenIndex, emptyAnswers);
				}
			}
		}

		for(int h : correctAnswers)
			System.out.print(h + ", ");
		System.out.println("\n");
	}
	
	private static void swap(int i, int j, int[] arr)
		String temp = questions.get(i);
		String[] temp2 = answers.get(i);

		questions.set(i, questions.get(j));
		answers.set(i, answers.get(j));

		questions.set(j, temp);
		answers.set(j, temp2);

		 arr[i] = (arr[i] + arr[j]) - (arr[j] = arr[i]);
	}
	
	private static void swapAnswers(int i, int j, int a, ArrayList<String[]> copy)
	{
		String[] row = copy.get(i);
		
		String currentColIndexCopy = row[j];
		String randomIndexToSwitch = row[a];

		row[j] = randomIndexToSwitch;
		row[a] = currentColIndexCopy;

	}
	
	public static int getSize()
	{
		return questions.size();
	}
	
	public static void getQuestion()
	{
		System.out.println(questions.get(0));
	}
	
	public static void getAnswers()
	{
		for(String abcd : answers.get(0))
			System.out.println(abcd);
	}
	
	//"default" modifier
	boolean correctAnswer(int usersChoice, int num)
	{	
		int x = correctAnswers[num];
		if(usersChoice == x)
		{
			restarting();
			return true;
		}
		return false;
	}
	
	private static void restarting()
	{
		questions.add(questions.get(0));
		questions.remove(0);
		answers.add(answers.get(0));
		answers.remove(0);
	}
	
	public boolean allQuestionsAsked(int num)
	{
		if(num == questions.size())
		{
			newShuffle();
			return true;
		}
		return false;
	}
	
	public void newShuffle()
	{
		shuffle(questions, answers);
	}

	public void print()
	{
		for(int i = 0; i < questions.size(); i++)
		{
			System.out.println(questions.get(i));
			for(String abcd : answers.get(i))
				System.out.println(abcd);
			System.out.println(correctAnswers[i]);
		}
	}
}
