package com.example.demo.domain.repository;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class ArchUnitRepositoryTest {


    JavaClasses importedClasses = new ClassFileImporter()
            .importPackages("com.example.demo.domain");


    @Test
    void repositoriesShouldBeSuffixedWithRepository() {
        classes()
                .that().resideInAPackage("..repository..")
                .and()
                .areAnnotatedWith(Repository.class)
                .should().haveSimpleNameEndingWith("RepositoryImpl")
                .check(importedClasses);
    }

    @Test
    void repositoriesShouldOnlyDependOnInterfaces() {
        classes()
                .that().haveSimpleNameEndingWith("Repository")
                .should().beInterfaces()
                .because("repository contracts should be public interfaces and implementations should be hidden")
                .check(importedClasses);

    }
}
