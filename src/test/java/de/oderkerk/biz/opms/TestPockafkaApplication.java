package de.oderkerk.biz.opms;

import org.springframework.boot.SpringApplication;

public class TestPockafkaApplication {

  public static void main(String[] args) {
    SpringApplication.from(PockafkaApplication::main).with(TestcontainersConfiguration.class).run(args);
  }

}

