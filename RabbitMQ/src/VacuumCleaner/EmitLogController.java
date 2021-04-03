package VacuumCleaner;
import java.util.Scanner;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLogController {

	private static final String EXCHANGE_NAME = "logs";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");

		try {
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
			
			Scanner input = new Scanner(System.in);
			String command="";
			System.out.println("Comandos: ");
			while(command != "-1")
			{
				command = input.nextLine();
				
				channel.basicPublish(EXCHANGE_NAME, "", null, command.getBytes() );
				System.out.println(" [x] Sent '" + command + "'");
			}
			input.close();

		} catch (Exception e) {

		}
	}
}