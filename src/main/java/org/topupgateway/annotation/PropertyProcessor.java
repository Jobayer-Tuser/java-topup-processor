package org.topupgateway.annotation;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.*;
import java.util.Properties;
import java.util.Set;

public class PropertyProcessor extends AbstractProcessor {
    private Properties properties;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(Property.class)) {
            if (element.getKind() == ElementKind.FIELD) {
                VariableElement variableElement = (VariableElement) element;
                String key = variableElement.getAnnotation(Property.class).value();
                String value = properties.getProperty(key);
            } else if (element.getKind() == ElementKind.METHOD) {
                ExecutableElement executableElement = (ExecutableElement) element;
                String key = executableElement.getAnnotation(Property.class).value();
                String value = properties.getProperty(key);
            }
        }
        return true;
    }
}
