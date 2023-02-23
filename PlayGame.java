import javax.swing.JFrame;
import java.awt.Color;

import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.File;

import java.util.Scanner;

public class PlayGame
{
	private static RandomQuestions qA;
	private boolean wrongChoice = true;
	Scanner input = new Scanner(System.in);

	public static void main(String[] args)
	{
		/*AppAnimations sponge = new AppAnimations("C:\\Users\\user\\Desktop\\"
				+ "spongebob2.png", 50, 50);*/
		qA = new RandomQuestions();
 		qA.play();
	}
	
	public void play()
	{
		System.out.println("Hi, do you want to play SpongeBob Trivia?");
		String yesOrNo = input.next();
		if(yesOrNo.equals("yes") || yesOrNo.equals("y"))
			qA.game();
		else
			return;
		play();
	}
	
	public void game()
	{
		int num = 0;
		while(wrongChoice)
		{
			qA.getQuestion();
			qA.getAnswers();
			System.out.println("Do you think the answer is A B C or D?");
			int usersChoice = input.nextInt();
			if(qA.correctAnswer(usersChoice))
				wrongChoice = qA.correctAnswer(usersChoice -1, num);
			if(!wrongChoice)
			{
				System.out.println("Good Game! You got " + num + " out of "
						+ qA.getSize() + " correct");
				return;
			}
			else if(qA.allQuestionsAsked(num+1))
			{
				System.out.println("Yaaaa your the winner!");
				return;
			}
			num++;
		}
	}
}
