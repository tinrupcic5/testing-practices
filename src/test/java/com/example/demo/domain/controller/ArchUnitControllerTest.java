package com.example.demo.domain.controller;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.GeneralCodingRules;
import jakarta.persistence.Entity;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ArchUnitControllerTest {

    JavaClasses importedClasses = new ClassFileImporter()
            .importPackages("com.example.demo.domain");



    @Test
    void controllersShouldBeSuffixedWithController() {
        classes()
                .that().resideInAPackage("..controller..")
                .and()
                .areAnnotatedWith(RestController.class)
                .should().haveSimpleNameEndingWith("Controller")
                .check(importedClasses);
    }

    /**
     * Architecture Violation [Priority: MEDIUM]
     * Payment is @Entity
     */
    @Test
    void controllersShouldNotRelyOnEntities () {
        noClasses()
                .that().areMetaAnnotatedWith(RestController.class)
                .should().dependOnClassesThat().areMetaAnnotatedWith(Entity.class)
                .because("controllers should use DTOs in stead of entities")
                .check(importedClasses);
    }




}
