
# HelloBootServerless

- [Serverless Core Java](https://www.youtube.com/watch?v=JeJ46YlpPqw)
- [Serverless Spring Boot]()
- <https://cloud.spring.io/spring-cloud-function/reference/html/aws.html>

- <https://mydeveloperplanet.com/2020/11/04/how-to-deploy-a-spring-cloud-function-on-aws-lambda/>
- <https://docs.spring.io/spring-cloud-function/docs/current/reference/html/spring-cloud-function.html>

    # Deprecated
    General purpose handler: com.learn.serverless.handler.MyRequestHandler::handleRequest

    # Need to define spring.cloud.function.definition in appliation.properties file or java command line args.
    General purpose handler: org.springframework.cloud.function.adapter.aws.FunctionInvoker::handleRequest
    
    # Put these in appliation.properties file or java command line args.
    # Or, in lambda function environment variable, define FUNCTION_NAME=doRequestResponse
    #
    --spring.cloud.function.definition=foo|bar;baz
    --spring.cloud.function.location=target/it/simplestjar/target/simplestjar-1.0.0.RELEASE.jar
    --spring.cloud.function.function-class=function.example.UpperCaseFunction
    --spring.cloud.function.function-class=function.example.UpperCaseFunction;function.example.ReverseFunction
    --spring.cloud.function.definition=doRequestResponse
    
    # s3 url:
    s3://luke-serverless-bucket/HelloBootServerless-1.0.0-aws.jar
    

## Test

    curl -H "Content-Type: text/plain" localhost:8080/reverse -d "Reverse me!"
    
    https://console.aws.amazon.com/apigateway
    
    time curl -i -H "Content-Type: application/json" -X POST https://h1zt9do2ge.execute-api.us-east-1.amazonaws.com/prod/transform -d '{"id" : "1", "request": "This is request"}'
    
    
    