package dev.amerida.jbooks_graphql;

import dev.amerida.jbooks_graphql.author.AuthorEntity;
import dev.amerida.jbooks_graphql.book.BookEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.execution.ClassNameTypeResolver;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@SpringBootApplication
public class JbooksGraphqlApplication {

	static void main(String[] args) {
		SpringApplication.run(JbooksGraphqlApplication.class, args);
	}

	@Bean
	RuntimeWiringConfigurer searchItemTypeResolver() {
		var resolver = new ClassNameTypeResolver();
		resolver.addMapping(AuthorEntity.class, "Author");
		resolver.addMapping(BookEntity.class, "Book");
		return builder -> builder.type("SearchItem", t -> t.typeResolver(resolver));
	}

}
