package weChat.core.web;

import org.springframework.boot.context.embedded.*;
import org.springframework.stereotype.Component;

@Component
public class ServletContainerCustomizerBean implements EmbeddedServletContainerCustomizer {

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
       // container.setContextPath("/weChat");
    }

}
