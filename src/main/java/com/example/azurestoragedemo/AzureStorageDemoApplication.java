package com.example.azurestoragedemo;

import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.content.azure.config.EnableAzureStorage;
import org.springframework.content.azure.store.AzureStorageContentStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@SpringBootApplication
public class AzureStorageDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AzureStorageDemoApplication.class, args);
	}

	@Configuration
	@EnableJpaRepositories(considerNestedRepositories = true)
	@EnableAzureStorage
	public class ApplicationConfigAzure {

		@Value("#{environment.AZURE_STORAGE_ENDPOINT}")
		private String endpoint;

		@Value("#{environment.AZURE_STORAGE_CONNECTION_STRING}")
		private String connString;

		@Bean
		public BlobServiceClientBuilder storage() {
			return new BlobServiceClientBuilder()
					.endpoint(endpoint)
					.connectionString(connString);
		}

		@Bean
		public DataSource dataSource() {
			EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
			return builder.setType(EmbeddedDatabaseType.HSQL).build();
		}

		@Bean
		public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

			HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
			vendorAdapter.setDatabase(Database.HSQL);
			vendorAdapter.setGenerateDdl(true);

			LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
			factory.setJpaVendorAdapter(vendorAdapter);
			factory.setPackagesToScan("com.example.azurestoragedemo");
			factory.setDataSource(dataSource());

			return factory;
		}

		@Bean
		public PlatformTransactionManager transactionManager() {

			JpaTransactionManager txManager = new JpaTransactionManager();
			txManager.setEntityManagerFactory(entityManagerFactory().getObject());
			return txManager;
		}
	}

	public interface PromoRepository extends JpaRepository<Promo, Long> {}
	public interface PromoContentStore extends AzureStorageContentStore<Promo, String> {}
}
