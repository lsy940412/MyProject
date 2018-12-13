package com.itany.orderservice.conf;

import com.alipay.api.DefaultAlipayClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlipayConfig {

    @Bean(name="alipayClient")
    public DefaultAlipayClient alipayClient(){
         DefaultAlipayClient alipayClient =
                 new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do"
                 ,"2016091100489179"
                 ,"MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC1R7h+rYVW3xRNbgJ3cDtK0zx4AiHBa/s+DwD0i4Dqkv5MkaPUdydutbV4+n9IX5HkTrP3znW4XemQNtH5BxeqRaz/iXDyOJtuYbBrq+mUTPyWW5An1Pc6qZVdJV0XJhQ5CWsI0wk9U6HVjP2cU3aYYlJNvWq4IqZxMA8GRs6EEfrCK/JnlZMBiXj3fNulgxCUfk+HYPU4dfSIKSwVYVCw9oIFCC8PjWXahrGnLRSB3fbx7wCBslxH+im8s+CtNsg5e0Pwnvgeekh2z8gTErx6Ou05fKx7RDd9PRNF2iP4BFNITwPw2/AZRXici9NJwR++8xnOFGsfzGiL/z/YP8u7AgMBAAECggEAX+tMJtM/y4CMKJA+A97BsMzmoQVXwRWWwfaEZeDu3K/cmL0zN7e4iK7jD9lTwgK3mls+hK+cwGoIrMMlBSSIlBL7/9ZEihYDwDs2czJqjIrT1+co9rqL+mFC5B9iC16dsrRWCzxHFkkLR4irpKzRAr57mCDSuA21jgyE1qhem6hXdGPo9YxIloJZ3Wp3qt8dK7ZxJU3XYvp+TtA6yG4wp/KSj1+/+K3W7wPzsPJDwh9yCXSYw9B4k0xCHPKLqsM8yCDfxA7a3CmAqQSWxgUVv9U7dZnrT0ZYdgH3HOyJh1lWaWDSqPVzzFzoZNRRgJhUK7iu6jwYv7nmEG9yCZzXUQKBgQDj/C+pCnfK3EL5xHlFChYFmbnii3W4O6cJQeGOzUfHWriIAFLMhkbHCtet+NQkgsRJRJNfx8/FxdrkhpPKbNJIpfWAkyTJ3pIkXi12gGWZuOdlh++78VtlkZMSnG4FIwM+J87izWrZujte/UeV8HcJXiGxqWQuatZIjcoi6JZniQKBgQDLjlHD1al4iuUPnaSmW9P7o+LyF0NyE2gfym+OZNFKoS/KkItAO/+F7wAtxr0+mgEHAVA+Fi2mQbY5Er5zL70Ri8nM3eeRBrZqE+KkpnCpJTA3wgwliwMRAhEJvjTYTwWSozJxqt0q8KUeKgz61XhiX1XQJ56BM8WSh1jJ66WEIwKBgGouWxg0uuQtP7hKLFzePLFxDmUXpMUga0Rhfabfrs2HUChvViDRbQdo4lixakRy7u5c1PKoAfnPk4oZr514n1uch8HRRjIKyi/L+QqW9DrrJkJgVGRJHdMpQMFLP+Z+jxUUv0NcfqZXGS29cui/bQFc6P5wsSr5MUHrX7hwcRRZAoGAG2EOMGgZvmP4QWxzzAiH783wZZ2pbbucgsIo1R9MY1xg4D8m68HmMa6e+IlFxRjT0IxlraQwCa/HAdA6m6WtKaICx7KFRiV//a6lCn50otukq6tHFPmotO3xOr1BwGKInlujGlTgILlzjCYdztNNuPWXxkK9+CTtizL3R1mRzr8CgYB872rFiTEVH0I7mEoEKkxn20ItziaxoBSiiPmRIhJbHaDwaITk7bqDRATaF4I5WIFQQdWDPHOHBBn3ZKrQM245BxjJR7tnzXXyBlhNNtMWJaW9zfey+ZXybcZOEBg4RzGbzdeSmWErSkZZqVXHyTIelWiLW3hhsOHJRb2kCND/Dw=="
                 ,"json"
                 ,"utf-8"
                 ,"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAx7k9t3V8ShvDBffY+SIM2yBRM4GXrK5TGQRWJKLxNAaVuMLWDGLHtQdXKCRJX9S/BzMjXd3xG8aWD28TEylFwKkcODuaGpG1N2sMbA46N3xgutWMDj2H2uipCS5d1ayJY7rwH9PD8OHHlWu4Q28OpMERGLQTkPIX7xtAgSwyhOVAGx6wlLZT+MvVkPYf1qi8TMnwnf6gEuD+7LP9/TcCdVn3GH8pF4ZbiviOyE08xTxzNEsr7/ROAWmq3svoAoXq2zt6Nhn7bJsmAm4TRHCH+TvXkRGpLh8XTFtn01xKG8yAC3ShL7bA0jWxRyKdpsfrFMnvF42zoiLS3rIKHxEoUwIDAQAB"
                 ,"RSA2");
         return alipayClient;
    }


}
