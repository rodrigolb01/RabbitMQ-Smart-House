package Location;
import java.util.Scanner;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLogLoc {

	private static final String EXCHANGE_NAME = "logs";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");

		try {
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
			
			Scanner input = new Scanner(System.in);
			String location="";
			System.out.println("Digite aqui sua localizacao: ");
			while(location != "-1")
			{
				location = input.nextLine();
				
				channel.basicPublish(EXCHANGE_NAME, "", null, location.getBytes() );
				System.out.println(" [x] Sent '" + location + "'");
			}
			input.close();

		} catch (Exception e) {

		}
	}
}