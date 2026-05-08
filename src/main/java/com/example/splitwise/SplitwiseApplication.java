package com.example.splitwise;

import com.example.splitwise.commands.CommandExecutor;
import com.example.splitwise.commands.RegisterUser;
import com.example.splitwise.commands.SettleUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class SplitwiseApplication implements CommandLineRunner {

	@Autowired
	CommandExecutor commandExecutor;

	public static void main(String[] args) {
		SpringApplication.run(SplitwiseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// Console bases application or Menu based Application
//		String input = "register email password";
//		String[] words = input.split(" ");
//		if(words[0].equalsIgnoreCase("register")){
//			//userController.register(words[1]);
//		} else if (words[0].equalsIgnoreCase("add expense")) {
//			//expenseController.addExpense(words[1])
//		}
		// Not good design because main function - SRP and OCP is getting violated.
		// checking and executing both are taken care by main
		// which it should not be doing.

		// Command Design is better
		// CommandExecutor -  responsible for managing all commands.
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter the command");
		String input = scanner.next();
//		CommandExecutor commandExecutor = new CommandExecutor(
////				new RegisterUser(),
////				new SettleUp()
//		);
		commandExecutor.addCommand(new RegisterUser());
		commandExecutor.addCommand(new SettleUp());
		commandExecutor.execute(input);

	}
}
