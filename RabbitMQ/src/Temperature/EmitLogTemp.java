package Temperature;
import java.util.Scanner;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLogTemp {

	private static final String EXCHANGE_NAME = "logs";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");

		try {
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
			
			Scanner input = new Scanner(System.in);
			int degrees=0;
			System.out.println("Temperatura: ");
			while(degrees != -1)
			{
				degrees = input.nextInt();


				channel.basicPublish(EXCHANGE_NAME, "", null, Integer.toString(degrees).getBytes() );
				System.out.println(" [x] Sent '" + degrees + "'");
			}
			input.close();
		} catch (Exception e) {

		}
	}
}