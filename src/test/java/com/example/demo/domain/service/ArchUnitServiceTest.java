package com.example.demo.domain.service;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ArchUnitServiceTest {


    JavaClasses importedClasses = new ClassFileImporter()
            .importPackages("com.example.demo.domain");


    @Test
    void servicesShouldBeSuffixedWithService() {
        classes()
                .that().resideInAPackage("..service..")
                .and()
                .areAnnotatedWith(Service.class)
                .should().haveSimpleNameEndingWith("ServiceImpl")
                .check(importedClasses);
    }

    @Test
    void servicesShouldOnlyDependOnInterfaces() {
        classes()
                .that().haveSimpleNameEndingWith("Service")
                .should().beInterfaces()
                .because("service contracts should be public interfaces and implementations should be hidden")
                .check(importedClasses);

    }

}
