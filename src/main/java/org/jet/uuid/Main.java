package org.jet.uuid;


public class Main {
    public static void main(String[] args) throws Exception {
        SnowflakeGenerator snowflakeGenerator = new SnowflakeGenerator();
        System.out.println(snowflakeGenerator.generate());
    }
}
