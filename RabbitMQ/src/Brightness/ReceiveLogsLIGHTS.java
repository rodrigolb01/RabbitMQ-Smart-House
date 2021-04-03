package Brightness;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ReceiveLogsLIGHTS {
	private static final String EXCHANGE_NAME = "logs";

	static boolean start = false;
	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, EXCHANGE_NAME, "");

		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String str = new String(delivery.getBody(), "UTF-8");
			int brightness = Integer.parseInt(str);
			
			System.out.println(" [x] Received Brightness Data: " + brightness);	
            if(brightness > 25)
            {
            	start = true;
            }
            
            if(start)
            {
            	if(brightness > 250)
            		System.out.println("Lights ON");
    			else
    				System.out.println("Lights OFF");
            }
		};
		channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
		});
	}
}