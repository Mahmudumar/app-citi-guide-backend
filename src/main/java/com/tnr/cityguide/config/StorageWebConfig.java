package com.tnr.cityguide.config;
import java.nio.file.Path;import org.springframework.context.annotation.Configuration;import org.springframework.web.servlet.config.annotation.*;
@Configuration public class StorageWebConfig implements WebMvcConfigurer {
 private final AppProperties props;public StorageWebConfig(AppProperties props){this.props=props;}
 @Override public void addResourceHandlers(ResourceHandlerRegistry registry){String location=Path.of(props.storage().localDir()).toAbsolutePath().normalize().toUri().toString();registry.addResourceHandler("/uploads/**").addResourceLocations(location);}
}
