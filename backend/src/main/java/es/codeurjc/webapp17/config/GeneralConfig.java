package es.codeurjc.webapp17.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Mustache.Collector;
import com.samskivert.mustache.Mustache.Formatter;
import com.samskivert.mustache.Mustache.TemplateLoader;

@Configuration
public class GeneralConfig {
    @Bean
    public Mustache.Compiler mustacheCompiler(TemplateLoader mustacheTemplateLoader,
        Environment environment) {
      return Mustache.compiler()
          .withLoader(mustacheTemplateLoader)
          .withFormatter(customDateFormatter());
    }
    private Formatter customDateFormatter() {
        return new Formatter() {
          public String format(Object value) {
            if (value instanceof Date) {
              return dateFormat.format((Date) value);
            }
            return String.valueOf(value);
          }
  
          protected final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        };
    }
}
