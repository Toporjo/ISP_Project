package com.company.web.command;



import com.company.web.command.admin.*;
import com.company.web.command.common.*;
import com.company.web.command.form.FundFromCommand;
import com.company.web.command.form.LoginFormCommand;
import com.company.web.command.form.RegistrationFormCommand;
import com.company.web.command.form.TariffFormCommand;
import com.company.web.command.user.ActivateTariffCommand;
import com.company.web.command.user.AddFundsCommand;
import com.company.web.command.user.UserTariffsCommand;

import java.util.Map;
import java.util.TreeMap;

/**
 *  Container for commands
 * @see Command
 */


public class CommandContainer {

	private static Map<String, Command> commands = new TreeMap();
	
	static {
		commands.put("login", new LoginCommand());
		commands.put("loginForm", new LoginFormCommand());
		commands.put("home", new HomeCommand());
		commands.put("noCommand", new NoCommand());
		commands.put("logout", new LogoutCommand());
		commands.put("service", new ServiceCommand());
		commands.put("tariffManager", new TariffManagerCommand());
		commands.put("tariffForm", new TariffFormCommand());
		commands.put("editTariff", new EditTariffCommand());
		commands.put("newTariff", new NewTariffCommand());
		commands.put("deleteTariff", new DeleteTariffCommand());
		commands.put("userManager", new UserManagerCommand());
		commands.put("handleUser", new HandleUserCommand());
		commands.put("registrationForm", new RegistrationFormCommand());
		commands.put("registerUser", new RegisterUserCommand());
		commands.put("activateTariff", new ActivateTariffCommand());
		commands.put("fundForm", new FundFromCommand());
		commands.put("addFunds", new AddFundsCommand());
		commands.put("triggerPayment", new TESTTriggerDailyPaymentCommand());
		commands.put("downloadTariffs", new DownloadTariffsCommand());
		commands.put("userTariffs", new UserTariffsCommand());
		commands.put("changeLanguage", new ChangeLanguageCommand());

	}


	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			return commands.get("noCommand");
		}
		
		return commands.get(commandName);
	}
	
}