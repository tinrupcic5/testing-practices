package com.example.demo.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.GeneralCodingRules;
import org.junit.jupiter.api.Test;

class ArchitectureTest {


    JavaClasses importedClasses = new ClassFileImporter()
            .importPackages("com.example.demo.domain");


    @Test
    void noClassesShouldUseFieldInjection () {
        GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION.check(importedClasses);
    }



}
