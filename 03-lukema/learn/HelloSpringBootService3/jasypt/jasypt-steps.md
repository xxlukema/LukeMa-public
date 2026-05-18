# `jasypt` step

- <http://www.devglan.com/online-tools/jasypt-online-encryption-decryption>
- <https://www.youtube.com/watch?v=i8elzsN9dAQ>
- <https://github.com/ulisesbocchio/jasypt-spring-boot>

## 1. Set environment variables (Note: PBEWithMD5AndTripleDES is advance algorithm. It needs to install more software)

    # JASYPT_ENCRYPTOR_PASSWORD = luke-key
    # JASYPT_ENCRYPTOR_ALGORITHM = PBEWithMD5AndDES

## 2. Encrype password

    # 1.A Do     use <jasypt.version>3.0.2</jasypt.version>
    # 1.B Do not use <jasypt.version>2.1.1</jasypt.version>
    # 2.A Do not use /c/Users/lma/.m2/repository/com/melloware/jasypt/1.9.4
    # 2.B Do     use /c/Users/lma/.m2/repository/org/jasypt/jasypt/1.9.3

    cd C:\Users\lma\.m2\repository\org\jasypt\jasypt\1.9.3
    
    # Date: 2020-06-10
    set passwd=kesD:1kesD:5
    java -cp jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input=%passwd% password=%JASYPT_ENCRYPTOR_PASSWORD% algorithm=%JASYPT_ENCRYPTOR_ALGORITHM%
    # cd /c/Users/lma/.m2/repository/org/jasypt/jasypt/1.9.3
    # passwd=luke
    # java -cp jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input=${passwd} password=${JASYPT_ENCRYPTOR_PASSWORD} algorithm=${JASYPT_ENCRYPTOR_ALGORITHM}
    Output: wOgbJDaFvr6ziqUPhnWHEvVRHFp2su3d

    oracle.password=ENC(wOgbJDaFvr6ziqUPhnWHEvVRHFp2su3d)
    jasypt.encryptor.iv-generator-classname=org.jasypt.iv.NoIvGenerator

## 3. To decrypt a password

    set input=wOgbJDaFvr6ziqUPhnWHEvVRHFp2su3d
    java -cp jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringDecryptionCLI input=%input% password=%JASYPT_ENCRYPTOR_PASSWORD% algorithm=%JASYPT_ENCRYPTOR_ALGORITHM%
    # input=C9Ch76Ch5AefuLAJ1BYkPYpGt6UD9MeVskbI1a8A0mMVrUyPrkb5Pw==
    # java -cp jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringDecryptionCLI input="${input}" password=${JASYPT_ENCRYPTOR_PASSWORD} algorithm=${JASYPT_ENCRYPTOR_ALGORITHM}

## 4. Spring boot application configure

### 4.1 Add to pom.xml

    <dependencies>
      <dependency>
        <groupId>com.github.ulisesbocchio</groupId>
        <artifactId>jasypt-spring-boot-starter</artifactId>
        <version>2.1.1</version>
      </dependency>
    </dependencies>

### 4.2 application.properties

    spring.datasource.url=jdbc:postgresql://localhost:5432/test
    spring.datasource.username=luke
    spring.datasource.password=ENC(P3Tt8KdEzfU5+YWwHatbvA==)
    oracle.password=ENC(wOgbJDaFvr6ziqUPhnWHEvVRHFp2su3d)
    # environment var: JASYPT_ENCRYPTOR_PASSWORD = luke-key
    jasypt.encryptor.password=${JASYPT_ENCRYPTOR_PASSWORD:}
    
    # environment var: JASYPT_ENCRYPTOR_ALGORITHM = PBEWithMD5AndDES
    jasypt.encryptor.algorithm=${JASYPT_ENCRYPTOR_ALGORITHM:}
    jasypt.encryptor.iv-generator-classname=org.jasypt.iv.NoIvGenerator
  
### 4.3 (Optional) Add annotation @EnableEncryptableProperties to Boot Configure java file

    (Optional) import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
  
    (Optional) @EnableEncryptableProperties

## 5. Database url and username can also be encrypted
