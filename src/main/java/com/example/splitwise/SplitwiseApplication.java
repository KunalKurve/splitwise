package com.example.splitwise;

import com.example.splitwise.commands.CommandExecutor;
import com.example.splitwise.commands.SettleUpGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

@SpringBootApplication
@EnableJpaAuditing
public class SplitwiseApplication implements CommandLineRunner {

	@Autowired
	CommandExecutor commandExecutor;

	public static void main(String[] args) {
		SpringApplication.run(SplitwiseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Files.lines(Paths.get("src/main/resources/test_commands.txt"))
				.filter(line -> !line.isBlank())
				.filter(line -> !line.startsWith("#"))
				.forEach(command -> {
					try{
						System.out.println("\nExecuting: " + command);

						commandExecutor.execute(command);

					}
					catch(Exception e){

						System.out.println(
								"FAILED -> " + command
						);

						e.printStackTrace();
					}
				});

		/*
 			Console based application or Menu based Application
				String input = "register email password";
				String[] words = input.split(" ");
				if(words[0].equalsIgnoreCase("register")){
					//userController.register(words[1]);
				} else if (words[0].equalsIgnoreCase("add expense")) {
					//expenseController.addExpense(words[1])
				}
			 Not good design because main function - SRP and OCP is getting violated.
			 checking and executing both are taken care by main method class
			 which it should not be doing.
			 Command Design is better
			 CommandExecutor -  responsible for managing all commands.
		*/

//		Scanner scanner = new Scanner(System.in);
//
//		while(true){
//
//			try{
//
//				System.out.print("\nEnter command: ");
//
//				String input =
//						scanner.nextLine();
//
//				if(input.equalsIgnoreCase("exit")){
//					break;
//				}
//
//				commandExecutor.execute(input);
//
//			}
//            catch(Exception e){
//				System.out.println(e.getMessage());
//			}
//		}
//
//        scanner.close();
	}


			// Don't use scanner.next() because stops taking input after a space
			// scanner.nextLine() stops taking input after Enter
			/*
			CommandExecutor commandExecutor = new CommandExecutor(
					new RegisterUser(),
					new SettleUp()
			);
			// or
			commandExecutor.addCommand(new RegisterUser());
			commandExecutor.addCommand(new SettleUpGroup()));
			*/

}
