package com.eventoapp; // Classe de configuração do Banco de Dados

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration // Mostra para o Spring que é uma classe de configuração
public class DataConfiguration {
	// Faz a conexão com o Banco:
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/eventoapp?allowPublicKeyRetrieval=true&rewriteBatchedStatements=true&useSSL=false&useUnicode=yes&characterEncoding=UTF-8&useLegacyDatetimeCode=true&createDatabaseIfNotExist=true&useTimezone=true&serverTimezone=UTC"); // eventoapp é o nome do banco (database) que vamos previamente criar. Manualmente no MySQL (Pego mais atual da internet)
        // allowPublicKeyRetrieval=true --> permitir que o cliente solicite automaticamente a chave pública do servidor.
        // rewriteBatchedStatements=true --> o JDBC empacota o maior número possível de consultas em um único pacote de rede, diminuindo assim a sobrecarga da rede.
        // useSSL=false --> o SSL não faz a verificação de certificado e host (quando está trabalhando em um ambiente de não-produção, sem dados reais)
        // useUnicode=yes&characterEncoding=UTF-8 --> Se estivermos passando dados com caracteres UTF-8 em consultas de banco de dados, precisamos garantir que qualquer conexão de banco de dados feita seja compatível com a codificação UTF-8.
        // createDatabaseIfNotExist=true --> Cria o banco de dados se ele não existir
        dataSource.setUsername("Projeto");  // username de conexão no banco
        dataSource.setPassword("123"); // senha de conexão no Banco
        return dataSource; // Esse Bean retorna um dataSource porque o método retorno dele é dataSource
    }
    // Faz a configuração do hibernate:
    @Bean
    public JpaVendorAdapter jpaVendorAdapter(){
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL); // Define qual databese estamos utilizando. No caso MySQL
        adapter.setShowSql(true); // true = mostra todas as etapas (passo a passo) aparecendo no nosso console
        adapter.setGenerateDdl(true); // true = Permite que o hibernate crie as tabelas automaticamente
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQL8Dialect"); // Dialeto que será utilizado (Pego mais atual da internet)
        adapter.setPrepareConnection(true); // true = Hibernate se prepara e faz a conexão automaticamente
        return adapter; // Esse Bean retorna um objeto Adapter
    }
}
