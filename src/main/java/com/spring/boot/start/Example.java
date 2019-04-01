package com.spring.boot.start;


import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;

@RestController
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableScheduling
@ComponentScan(basePackages="com.spring.boot.start")
public class Example {

    @Autowired
    private Scheduled scheduled;


    @RequestMapping("/{beanName}")
    String home(@PathVariable("beanName") String beanName) {
        scheduled.addTask(beanName);
        return "Hello World!";
    }

    //@org.springframework.scheduling.annotation.Scheduled(cron="0/3 * * * * ?")
    public void print(){
        //org.springframework.scheduling.concurrent.ReschedulingRunnable 具体实现
        //计算下次要执行的时间丢掉线程池中  循环以上操作
        System.out.println("test ...");

    }
    public static void main(String[] args) throws  Exception{

        Enumeration<URL> es  = Example.class.getClassLoader().getResources("META-INF/spring.factories");
        while(es.hasMoreElements()){
            URL url = es.nextElement();
            InputStream in = url.openStream();
            Properties ps = new Properties();
            ps.load(in);
            for(String key : ps.stringPropertyNames()){
                System.out.println(key+"="+ps.getProperty(key));
            }
        }

       SpringApplication.run(Example.class, args);




    }


    /**
     *
     * SpringApplicationRunListener 作为ApplicationListener 组织者
     *
     *
     *
     * 初始化  Environment 环境配置
     *
     * ApplicationListener（使用监听者listener模式）
     *
     * ConfigFileApplicationListener 触发   EnvironmentPostProcessor
     *
     * 创建上下文 ConfigurableApplicationContext
     *
     * 初始化上下文 ApplicationContextInitializer
     *
     * ApplicationListener（两种方式添加1）使用文件 /META-INF/spring.factories 2)使用 api ）
     *
     * xml 时期的配置的扩展是 使用元素的命名空间进行的扩展的  在spring-boot 时代扩展是使用 ApplicationListener 进行扩展的
     *
     * 使用的模式 事件和监听者的模式 在spring-boot启动的不同时间节点 通知出不同的事件event  通知到不同的模块进行集成
     *
     *
     *
     *
     *
     *
     * org.springframework.boot.SpringApplicationRunListener=org.springframework.boot.context.event.EventPublishingRunListener
     * org.springframework.boot.env.EnvironmentPostProcessor=org.springframework.boot.cloud.CloudFoundryVcapEnvironmentPostProcessor,org.springframework.boot.env.SpringApplicationJsonEnvironmentPostProcessor,org.springframework.boot.env.SystemEnvironmentPropertySourceEnvironmentPostProcessor
     * org.springframework.boot.diagnostics.FailureAnalyzer=org.springframework.boot.diagnostics.analyzer.BeanCurrentlyInCreationFailureAnalyzer,org.springframework.boot.diagnostics.analyzer.BeanDefinitionOverrideFailureAnalyzer,org.springframework.boot.diagnostics.analyzer.BeanNotOfRequiredTypeFailureAnalyzer,org.springframework.boot.diagnostics.analyzer.BindFailureAnalyzer,org.springframework.boot.diagnostics.analyzer.BindValidationFailureAnalyzer,org.springframework.boot.diagnostics.analyzer.UnboundConfigurationPropertyFailureAnalyzer,org.springframework.boot.diagnostics.analyzer.ConnectorStartFailureAnalyzer,org.springframework.boot.diagnostics.analyzer.NoSuchMethodFailureAnalyzer,org.springframework.boot.diagnostics.analyzer.NoUniqueBeanDefinitionFailureAnalyzer,org.springframework.boot.diagnostics.analyzer.PortInUseFailureAnalyzer,org.springframework.boot.diagnostics.analyzer.ValidationExceptionFailureAnalyzer,org.springframework.boot.diagnostics.analyzer.InvalidConfigurationPropertyNameFailureAnalyzer,org.springframework.boot.diagnostics.analyzer.InvalidConfigurationPropertyValueFailureAnalyzer
     * org.springframework.context.ApplicationContextInitializer=org.springframework.boot.context.ConfigurationWarningsApplicationContextInitializer,org.springframework.boot.context.ContextIdApplicationContextInitializer,org.springframework.boot.context.config.DelegatingApplicationContextInitializer,org.springframework.boot.web.context.ServerPortInfoApplicationContextInitializer
     * org.springframework.boot.env.PropertySourceLoader=org.springframework.boot.env.PropertiesPropertySourceLoader,org.springframework.boot.env.YamlPropertySourceLoader
     * org.springframework.context.ApplicationListener=org.springframework.boot.ClearCachesApplicationListener,org.springframework.boot.builder.ParentContextCloserApplicationListener,org.springframework.boot.context.FileEncodingApplicationListener,org.springframework.boot.context.config.AnsiOutputApplicationListener,org.springframework.boot.context.config.ConfigFileApplicationListener,org.springframework.boot.context.config.DelegatingApplicationListener,org.springframework.boot.context.logging.ClasspathLoggingApplicationListener,org.springframework.boot.context.logging.LoggingApplicationListener,org.springframework.boot.liquibase.LiquibaseServiceLocatorApplicationListener
     * org.springframework.context.ApplicationListener=org.springframework.boot.autoconfigure.BackgroundPreinitializer
     * org.springframework.boot.SpringBootExceptionReporter=org.springframework.boot.diagnostics.FailureAnalyzers
     * org.springframework.boot.diagnostics.FailureAnalysisReporter=org.springframework.boot.diagnostics.LoggingFailureAnalysisReporter
     * org.springframework.boot.autoconfigure.AutoConfigurationImportFilter=org.springframework.boot.autoconfigure.condition.OnBeanCondition,org.springframework.boot.autoconfigure.condition.OnClassCondition,org.springframework.boot.autoconfigure.condition.OnWebApplicationCondition
     * org.springframework.boot.autoconfigure.AutoConfigurationImportListener=org.springframework.boot.autoconfigure.condition.ConditionEvaluationReportAutoConfigurationImportListener
     * org.springframework.boot.diagnostics.FailureAnalyzer=org.springframework.boot.autoconfigure.diagnostics.analyzer.NoSuchBeanDefinitionFailureAnalyzer,org.springframework.boot.autoconfigure.jdbc.DataSourceBeanCreationFailureAnalyzer,org.springframework.boot.autoconfigure.jdbc.HikariDriverConfigurationFailureAnalyzer,org.springframework.boot.autoconfigure.session.NonUniqueSessionRepositoryFailureAnalyzer
     * org.springframework.context.ApplicationContextInitializer=org.springframework.boot.autoconfigure.SharedMetadataReaderFactoryContextInitializer,org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLoggingListener
     * org.springframework.boot.autoconfigure.template.TemplateAvailabilityProvider=org.springframework.boot.autoconfigure.freemarker.FreeMarkerTemplateAvailabilityProvider,org.springframework.boot.autoconfigure.mustache.MustacheTemplateAvailabilityProvider,org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAvailabilityProvider,org.springframework.boot.autoconfigure.thymeleaf.ThymeleafTemplateAvailabilityProvider,org.springframework.boot.autoconfigure.web.servlet.JspTemplateAvailabilityProvider
     * org.springframework.boot.autoconfigure.EnableAutoConfiguration=org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration,org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration,org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration,org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration,org.springframework.boot.autoconfigure.cloud.CloudServiceConnectorsAutoConfiguration,org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration,org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration,org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration,org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration,org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration,org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration,org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveDataAutoConfiguration,org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveRepositoriesAutoConfiguration,org.springframework.boot.autoconfigure.data.cassandra.CassandraRepositoriesAutoConfiguration,org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataAutoConfiguration,org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveDataAutoConfiguration,org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveRepositoriesAutoConfiguration,org.springframework.boot.autoconfigure.data.couchbase.CouchbaseRepositoriesAutoConfiguration,org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration,org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration,org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration,org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration,org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration,org.springframework.boot.autoconfigure.data.ldap.LdapRepositoriesAutoConfiguration,org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration,org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration,org.springframework.boot.autoconfigure.data.mongo.MongoReactiveRepositoriesAutoConfiguration,org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration,org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration,org.springframework.boot.autoconfigure.data.neo4j.Neo4jRepositoriesAutoConfiguration,org.springframework.boot.autoconfigure.data.solr.SolrRepositoriesAutoConfiguration,org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration,org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration,org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration,org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration,org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration,org.springframework.boot.autoconfigure.elasticsearch.jest.JestAutoConfiguration,org.springframework.boot.autoconfigure.elasticsearch.rest.RestClientAutoConfiguration,org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration,org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration,org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration,org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration,org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration,org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration,org.springframework.boot.autoconfigure.hazelcast.HazelcastJpaDependencyAutoConfiguration,org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration,org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration,org.springframework.boot.autoconfigure.influx.InfluxDbAutoConfiguration,org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration,org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration,org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration,org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration,org.springframework.boot.autoconfigure.jdbc.JndiDataSourceAutoConfiguration,org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration,org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration,org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration,org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration,org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration,org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration,org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration,org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration,org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration,org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration,org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration,org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration,org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapAutoConfiguration,org.springframework.boot.autoconfigure.ldap.LdapAutoConfiguration,org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration,org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration,org.springframework.boot.autoconfigure.mail.MailSenderValidatorAutoConfiguration,org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration,org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration,org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration,org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration,org.springframework.boot.autoconfigure.reactor.core.ReactorCoreAutoConfiguration,org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration,org.springframework.boot.autoconfigure.security.servlet.SecurityRequestMatcherProviderAutoConfiguration,org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration,org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration,org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration,org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration,org.springframework.boot.autoconfigure.sendgrid.SendGridAutoConfiguration,org.springframework.boot.autoconfigure.session.SessionAutoConfiguration,org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration,org.springframework.boot.autoconfigure.security.oauth2.client.reactive.ReactiveOAuth2ClientAutoConfiguration,org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration,org.springframework.boot.autoconfigure.security.oauth2.resource.reactive.ReactiveOAuth2ResourceServerAutoConfiguration,org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration,org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration,org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration,org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration,org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration,org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration,org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration,org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration,org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration,org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration,org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration,org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration,org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration,org.springframework.boot.autoconfigure.web.reactive.function.client.ClientHttpConnectorAutoConfiguration,org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration,org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration,org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration,org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration,org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration,org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration,org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration,org.springframework.boot.autoconfigure.websocket.reactive.WebSocketReactiveAutoConfiguration,org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration,org.springframework.boot.autoconfigure.websocket.servlet.WebSocketMessagingAutoConfiguration,org.springframework.boot.autoconfigure.webservices.WebServicesAutoConfiguration,org.springframework.boot.autoconfigure.webservices.client.WebServiceTemplateAutoConfiguration
     * org.springframework.beans.BeanInfoFactory=org.springframework.beans.ExtendedBeanInfoFactory
     *
     *
     *
     * spring 事件的时期
     *
     * Application events are sent in the following order, as your application runs:
     * 1. An ApplicationStartingEvent is sent at the start of a run but before any processing, except
     * for the registration of listeners and initializers.
     * 2. An ApplicationEnvironmentPreparedEvent is sent when the Environment to be used in the
     * context is known but before the context is created.
     * 3. An ApplicationPreparedEvent is sent just before the refresh is started but after bean definitions
     * have been loaded.
     * 4. An ApplicationStartedEvent is sent after the context has been refreshed but before any
     * application and command-line runners have been called.
     * 5. An ApplicationReadyEvent is sent after any application and command-line runners have been
     * called. It indicates that the application is ready to service requests.
     * 6. An ApplicationFailedEvent is sent if there is an exception on startup.
     *
     *
     */


    /**
     * 环境配置
     *
     * MutablePropertySources  中 List<PropertySource<?>> 多个  PropertySource 中 key,value
     *
     * 配置的处理使用 Binder 统一使用完成 可以解析PropertySource 中的泛型
     *
     *
     * 配置的解析
     * ConfigurationClass  初始化传入bean的注解元数据和beanName
     *
     *
     *
     */

}
