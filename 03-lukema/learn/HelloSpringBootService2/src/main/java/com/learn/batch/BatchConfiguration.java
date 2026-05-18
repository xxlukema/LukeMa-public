package com.learn.batch;


import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;


@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    // tag::readerwriterprocessor[]
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public FlatFileItemReader<PersonIn> inputItemReader() {

        LOG.debug("Called.");

        FlatFileItemReader<PersonIn> flatFileItemReader = new FlatFileItemReader<PersonIn>();

        flatFileItemReader.setResource(new ClassPathResource("batch-people-data.csv"));

        flatFileItemReader.setLineMapper(new DefaultLineMapper<PersonIn>() {

            /**
             * Non static initialization block
             * 
             * Initialization blocks are executed whenever the class is initialized and before constructors 
             * are invoked. They are typically placed above the constructors within braces. 
             */
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    /**
                     * Non static initialization block
                     */
                    {
                        setNames(new String[] { "firstName", "lastName" });
                    }
                });

                setFieldSetMapper(new BeanWrapperFieldSetMapper<PersonIn>() {
                    /**
                     * Non static initialization block
                     */
                    {
                        setTargetType(PersonIn.class);
                    }
                });
            }
        });

        return flatFileItemReader;
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public PersonItemProcessor itemProcessor() {
        LOG.debug("Called.");

        return new PersonItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<PersonOut> outputItemWriter() {

        LOG.debug("Called.");

        JdbcBatchItemWriter<PersonOut> jdbcBatchItemWriter = new JdbcBatchItemWriter<PersonOut>();
        jdbcBatchItemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<PersonOut>());
        jdbcBatchItemWriter.setSql("INSERT INTO people (person_id, first_name, last_name) VALUES (:personId, :firstName, :lastName)");
        jdbcBatchItemWriter.setDataSource(dataSource);

        return jdbcBatchItemWriter;
    }
    // end::readerwriterprocessor[]

    // @formatter:off
    // tag::jobstep[]
    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener) {
        
        LOG.debug("Called.");
        
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer() {
                    /**
                     * TODO: How to map this idIncrementor to person_id?
                     */
                    {
                        this.setKey("person_id");
                    }
                })
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }
    // @formatter:on

    // @formatter:off
    @Bean
    public Step step1() {
        
        LOG.debug("Called.");
        
        return stepBuilderFactory.get("step1")
                .<PersonIn, PersonOut> chunk(10)
                .reader(inputItemReader())
                .processor(itemProcessor())
                .writer(outputItemWriter())
                .build();
    }
    // end::jobstep[]
    // @formatter:on
}
