package sia6;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;

import java.io.File;


@Configuration
public class FileWriterIntegrationConfig {

  @Bean
  public IntegrationFlow fileWriterFlow() {
    return IntegrationFlows
        .from(MessageChannels.direct("textInChannel"))
        .<String, String>transform(String::toUpperCase)
        .channel(MessageChannels.direct("fileWriterChannel"))
        .handle(Files.outboundAdapter(new File("/tmp/sia6/files"))
            .fileExistsMode(FileExistsMode.APPEND)
            .appendNewLine(true)
        ).get();
  }
//  @Bean
//  @Transformer(inputChannel = "textInChannel",
//      outputChannel = "fileWriterChannel")
//  public GenericTransformer<String, String> upperCaseTransformer() {
//    return String::toUpperCase;
//  }
//
//  @Bean
//  @ServiceActivator(inputChannel = "fileWriterChannel")
//  public FileWritingMessageHandler fileWriter() {
//    var handler = new FileWritingMessageHandler(new File("/tmp/sia6/files"));
//    handler.setExpectReply(false);
//    handler.setFileExistsMode(FileExistsMode.APPEND);
//    handler.setAppendNewLine(true);
//    return handler;
//  }

//  @Bean
//  public MessageChannel textInChannel() {
//    return new DirectChannel();
//  }
//
//  @Bean
//  public MessageChannel fileWriterChannel() {
//    return new DirectChannel();
//  }
}
